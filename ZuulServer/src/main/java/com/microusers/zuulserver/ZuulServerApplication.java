package com.microusers.zuulserver;

import com.microusers.zuulserver.filter.FilterError;
import com.microusers.zuulserver.filter.PostFilter;
import com.microusers.zuulserver.filter.PreFilter;
import com.microusers.zuulserver.filter.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ZuulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class, args);
    }


    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter(){
        return new PostFilter();
    }

    @Bean
    public RouteFilter routerFilter(){
        return new RouteFilter();
    }

    @Bean
    public FilterError filterError(){
        return new FilterError();
    }




}
