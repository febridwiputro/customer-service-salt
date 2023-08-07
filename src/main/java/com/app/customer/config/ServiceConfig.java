package com.app.customer.config;

import com.app.customer.repository.CustomerRepository;
import com.app.customer.service.CustomerService;
import com.app.customer.service.impl.CustomerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public CustomerService customerService(CustomerRepository customerRepository) {
        return new CustomerServiceImpl(customerRepository);
    }
}
