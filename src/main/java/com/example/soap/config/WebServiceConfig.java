package com.example.soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

//Enable Web Service
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    //handle soap requests
    //we will pass is Application Context
    //message dispatcher servlet to map "/ws/*" url -> servlet registration
    //ServletRegistrationBean -> register
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet
            (ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext); //application context
        servlet.setTransformWsdlLocations(true); //wsdl
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "countries") //wsdl name
    //xsdSchema aşağıdaki bean'den alır
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort"); //portType
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(countriesSchema); //ws/countries.wsdl aşağıdaki method
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        //elimizdeki servisin sağladığı xsd'yi dışarıya wsdl olarak açıyoruz.
        //böylece bize gelecek olan istekleri ve geri dönecek cevaplara ilişkin
        //detaylı bilgiyi client-consume 'e iletiyoruz.
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }

}
