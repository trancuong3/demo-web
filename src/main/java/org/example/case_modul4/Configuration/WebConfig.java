package org.example.case_modul4.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đảm bảo rằng các tài nguyên tĩnh được ánh xạ đúng
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        // Thêm các ánh xạ khác nếu cần
    }
}