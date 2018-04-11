package com.zifangdt.ch.base.dao;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 袁兵 on 2018/3/27.
 */
@Component
public class DynamicDataSource implements DataSource {

    private DataSource defaultDataSource;
    private static final Pattern PATTERN = Pattern.compile("/([a-z_]+)\\?");

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.tomcat.max-age}")
    private long maxAge;

    @PostConstruct
    public void init() {
        PoolProperties properties = new PoolProperties();
        properties.setUrl(url);
        properties.setUsername(username);
        properties.setPassword(password);
        properties.setDriverClassName(driverClassName);
        properties.setMaxAge(maxAge);
        defaultDataSource = new org.apache.tomcat.jdbc.pool.DataSource(properties);
    }

    @Autowired
    private ApplicationContext applicationContext;

    private ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public DataSource getDataSource() {
        return getDataSource(THREAD_LOCAL.get());
    }

    public DataSource getDataSource(String key) {
        if (StringUtils.isEmpty(key)) {
            return defaultDataSource;
        }
        return (DataSource) applicationContext.getBean(key);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDataSource().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getDataSource().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getDataSource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getDataSource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getDataSource().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getDataSource().getParentLogger();
    }

    public void switchDataSource(String tenantId, String dbAddress) {
        if (!applicationContext.containsBean(tenantId)) {
            Matcher matcher = PATTERN.matcher(url);
            String dbName = "";
            if (matcher.find()) {
                dbName = matcher.group(1);
            }
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(org.apache.tomcat.jdbc.pool.DataSource.class);
            builder.addPropertyValue("url", "jdbc:mysql://" + dbAddress + "/" + dbName + "?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8");
            builder.addPropertyValue("driverClassName", driverClassName);
            builder.addPropertyValue("username", username);
            builder.addPropertyValue("password", password);
            builder.addPropertyValue("maxAge", maxAge);

            beanFactory.registerBeanDefinition(tenantId, builder.getBeanDefinition());
        }
        THREAD_LOCAL.set(tenantId);
    }

    public void clear() {
        THREAD_LOCAL.remove();
    }
}
