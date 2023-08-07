package com.app.customer.service;

import com.app.customer.dto.customer.CustomerAddOAS;
import com.app.customer.dto.customer.CustomerListOAS;
import com.app.customer.dto.customer.CustomerUpdateOAS;
import com.app.customer.exception.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerAddOAS.Response createCustomer(CustomerAddOAS.Request request) throws ValidationException;

    Object getCustomerById(String id) throws JsonProcessingException;

    List<CustomerListOAS.CustomerResponse> getCustomerByName(String name);

    CustomerListOAS.Response findAllCustomers();

    Object updateCustomer(String customerId, CustomerUpdateOAS.Request customerRequest) throws ValidationException;

    void deleteCustomerById(String id);
}