package com.sampleapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jfilter.EnableJsonFilter;

@Configuration
@EnableJsonFilter
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "OPTIONS", "DELETE")
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Allow-Origin",
                        "Access-Control-Request-Method", "Access-Control-Request-Headers")
                .allowCredentials(true).maxAge(3600)
                .exposedHeaders("Authorization");
    }
}
