//package com.zifangdt.ch.product;
//
//import com.zifangdt.ch.base.dto.product.entity.Product;
//import com.zifangdt.ch.product.mapper.ProductMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@TestPropertySource("classpath:application-test.yaml")
//public class ProductTest {
//
//    @Autowired
//    ProductMapper productMapper;
//
//    @Test
//    public void testMapperSmoke(){
//        List<Product> products = productMapper.selectAll();
//        assertThat(products).isNotNull();
//    }
//}
