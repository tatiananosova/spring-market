package com.geekbrains.ru.springmarket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.nio.file.Paths;

@Configuration
@EnableAspectJAutoProxy
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "data";
        String staticPath = Paths.get(System.getProperty("user.dir"), dirName).toFile().getAbsolutePath();

        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/"+  staticPath + "/");
    }
    @Bean(name = "categories")
    public DefaultWsdl11Definition categoriesWsdl11Definition(XsdSchema categoriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CategoriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.geekbrains.com/springmarket/ws/categories");
        wsdl11Definition.setSchema(categoriesSchema);
        return wsdl11Definition;
    }

    @Bean(name = "products")
    public DefaultWsdl11Definition productsWsdl11Definition(XsdSchema productsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.geekbrains.com/springmarket/ws/products");
        wsdl11Definition.setSchema(productsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema categoriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("categories.xsd"));
    }

    @Bean
    public XsdSchema productsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("products.xsd"));
    }

}
