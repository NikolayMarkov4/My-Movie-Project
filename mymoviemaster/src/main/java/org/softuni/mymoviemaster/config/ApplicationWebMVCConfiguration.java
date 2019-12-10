package org.softuni.mymoviemaster.config;

import org.softuni.mymoviemaster.web.interceptors.TitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationWebMVCConfiguration implements WebMvcConfigurer {
    private final TitleInterceptor titleInterceptor;

    @Autowired
    public ApplicationWebMVCConfiguration(TitleInterceptor titleInterceptor) {
        this.titleInterceptor = titleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.titleInterceptor);
    }
}
