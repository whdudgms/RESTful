package org.example.restful.controller;

import org.example.restful.bean.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }



    // 우리가 만드는  RESTful 서비스에서는 화면단이 없으므로 그냥 RESTController만 활용해서 만든다.

    //GET
    //URI - /hello-world
    //@RequestMapping(method=RequestMethod.GET.path="/hello-world") >> 간단하게 해주는     @GetMapping
    @GetMapping(path = "/hello-world")
    public String helloworld(){
        return "Hello World";
    }

    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloworldBean(){
        return new HelloWorldBean("Hello World!"); // 객체로 반환하면 자동으로 스프링부트에서 JSON형식으로 변환해서 반환됨
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloworldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello World!, %s",name)); // 객체로 반환하면 자동으로 스프링부트에서 JSON형식으로 변환해서 반환됨
    }


    @GetMapping(path = "/hello-world-internationalized")
    public String helloworldInternalized(
            @RequestHeader(name="Accept-Language",required = false) Locale locale
    ) {
        return messageSource.getMessage("greeting.message", null, locale);

    }
}

