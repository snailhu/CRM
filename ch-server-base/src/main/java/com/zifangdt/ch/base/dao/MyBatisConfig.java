package com.zifangdt.ch.base.dao;

import com.github.pagehelper.PageInterceptor;
import com.zifangdt.ch.base.enums.pair.IntVerifierEnum;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements ConfigurationCustomizer {

    public MyBatisConfig() {
        System.out.println("################MyBatisConfig");
    }

    @Override
    public void customize(org.apache.ibatis.session.Configuration configuration) {
        VFS.addImplClass(SpringBootVFS.class);

        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties props = new Properties();
        props.setProperty("helperDialect", "mysql");
        props.setProperty("rowBoundsWithCount", "true");
        props.setProperty("offsetAsPageNum", "true");
        pageInterceptor.setProperties(props);
        configuration.addInterceptor(pageInterceptor);
        configuration.getTypeAliasRegistry().registerAliases("com.zifangdt.ch.base.dto");

        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);

        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        typeHandlerRegistry.register(IntVerifierEnum.class, IntVerifierEnumTypeHandler.class);
        typeHandlerRegistry.register(ListTypeHandler.class);
        typeHandlerRegistry.register(SetTypeHandler.class);
        typeHandlerRegistry.register(BaseConfigDetailTypeHandler.class);
        typeHandlerRegistry.register(ObjectArrayTypeHandler.class);
        typeHandlerRegistry.register(MultiProductArrayTypeHandler.class);
        typeHandlerRegistry.register(JsonObjectTypeHandler.class);
        typeHandlerRegistry.register(IPTypeHandler.class);
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.zifangdt.ch.*.mapper");
        Properties props = new Properties();
        props.setProperty("mappers", BaseMapper.class.getName());
        mapperScannerConfigurer.setProperties(props);
        return mapperScannerConfigurer;
    }

}