package com.zifangdt.ch.gateway.config;

import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.constant.Permissions;
import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.gateway.security.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.*;

/**
 * Created by 袁兵 on 2017/8/24.
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomAuthenticationProcessingFilter customAuthenticationProcessingFilter;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UaaServerApi uaaServerApi;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Autowired
    private Md5PasswordEncoder passwordEncoder;
    @Autowired
    private CustomEntryPoint customEntryPoint;


    //    FilterInvocationSecurityMetadataSource
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<Permission> permissions = uaaServerApi.findPermissions();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();

        registry.antMatchers("/**/self/**", "/**/mine/**").authenticated();

        Map<String, Set<String>> map = new LinkedHashMap<>();
        permissions.forEach(permission -> {
            String urls = permission.getUrl();
            if (StringUtils.isNotEmpty(urls)) {
                for (String url : urls.split(",")) {
                    if (StringUtils.isEmpty(url)) {
                        continue;
                    }
                    if (!map.containsKey(url)) {
                        map.put(url, new HashSet<>());
                    }
                    map.get(url).add(permission.getSn());
                }
            }
        });
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String url = entry.getKey();
            Set<String> sns = entry.getValue();
            int index = url.indexOf(":");
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl authorizedUrl = index == -1 ?
                    registry.antMatchers(url) :
                    registry.antMatchers(HttpMethod.valueOf(url.substring(0, index)), url.substring(index + 1));
            entry.getValue().add(Permissions.ADMIN);
            authorizedUrl.hasAnyAuthority(sns.toArray(new String[sns.size()]));
        }
        registry.anyRequest().authenticated();
        http
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler).authenticationEntryPoint(customEntryPoint).and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(customAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
                .headers().cacheControl()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(jwtAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                //忽略CORS请求（非简单请求）时第一次发出的“预检”请求
                .antMatchers(HttpMethod.OPTIONS)
                .antMatchers("/**/*.html", "/**/*.ico", "/**/*.png", "/**/*.jpg", "/**/*.js", "/**/*.css")
                .antMatchers("/loggers/**")
                .antMatchers(HttpMethod.GET, "/common/files/**", "/c/customers/import", "/u/users/import", "/error", "/p/product/import", "/c/customer/public/import","/pm/project/import")
                .antMatchers(HttpMethod.POST, "/session/**");
        // 活动报名不需要认证
        web.ignoring()
                .antMatchers("/m/promotion/{promotionId}/{userId}/join")
                .antMatchers("/m/promotion/{id}/{userId}");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
