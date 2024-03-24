package org.example.restful;

import org.apache.tomcat.util.descriptor.LocalResolver;
import org.example.restful.controller.HelloWorldController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestfulApplication {


	public static void main(String[] args) {

		//ApplicationContext ac =
		SpringApplication.run(RestfulApplication.class, args);
//		String[] allBeanNames = ac.getBeanDefinitionNames();
//		for(String beanName : allBeanNames){
//			System.out.println(beanName);
		}

	@Bean
	public LocaleResolver localResolver(){
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}


}


