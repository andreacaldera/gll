package com.acal.gll.booking.config;

import com.acal.gll.booking.web.BrowserDriver;
import com.acal.gll.booking.web.BrowserFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.acal.gll.booking"})
public class GllBookingConfiguration {

    @Bean
    public BrowserDriver browserDriver() {
        return BrowserFactory.firefox();
    }

}
