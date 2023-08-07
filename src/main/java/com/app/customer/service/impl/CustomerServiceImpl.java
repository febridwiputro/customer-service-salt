package com.app.customer.service.impl;

import com.app.customer.constant.HttpResponseConstant;
import com.app.customer.dto.customer.CustomerDetailOAS;
import com.app.customer.dto.customer.CustomerListOAS;
import com.app.customer.enums.StatusEnum;
import com.app.customer.dto.customer.CustomerAddOAS;
import com.app.customer.dto.customer.CustomerUpdateOAS;
import com.app.customer.exception.DataNotFoundException;
import com.app.customer.exception.ValidationException;
import com.app.customer.model.CustomerEntity;
import com.app.customer.repository.CustomerRepository;
import com.app.customer.service.CustomerService;
import com.app.customer.util.DateUtil;
import com.app.customer.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;

public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerAddOAS.Response createCustomer(CustomerAddOAS.Request request) throws ValidationException {
        if (isInvalidCustomerRequest(request)) {
            throw new ValidationException(HttpResponseConstant.BAD_REQUEST);
        }

        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(request.name);
        customerEntity.setAddress(request.address);
        customerEntity.setCity(request.city);
        customerEntity.setProvince(request.province);
        customerEntity.setStatus(StatusEnum.ACTIVE);

        customerEntity = customerRepository.saveAndFlush(customerEntity);

        return mapToResponse(customerEntity);
    }


public Object getCustomerById(String id) throws JsonProcessingException {
    CustomerEntity customerEntity = customerRepository.findActiveCustomerById(id);

    if (customerEntity != null) {
        return mapToDetailResponse(customerEntity);
    } else {
        throw new DataNotFoundException(HttpResponseConstant.DATA_NOT_EXISTS);
    }
}

    @Override
    public List<CustomerListOAS.CustomerResponse> getCustomerByName(String name) {
        List<CustomerEntity> customerEntities = customerRepository.findActiveCustomersByNameContaining(name);
        return customerEntities.stream().map(this::mapToCustomerListResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerListOAS.Response findAllCustomers() {
        CustomerListOAS.Response response = new CustomerListOAS.Response();
        List<CustomerEntity> customerEntities = customerRepository.findAllActiveCustomers();
        response.customers = customerEntities.stream().map(this::mapToCustomerListResponse).collect(Collectors.toList());
        return response;
    }

    @Override
    public Object updateCustomer(String customerId, CustomerUpdateOAS.Request customerRequest) throws ValidationException {
        if (customerId == null || isInvalidCustomerUpdateRequest(customerRequest)) {
            throw new ValidationException(HttpResponseConstant.BAD_REQUEST);
        }

        CustomerEntity customerEntity = customerRepository.findActiveCustomerById(customerId);

        if (customerEntity != null) {
            customerEntity.setName(customerRequest.name);
            customerEntity.setAddress(customerRequest.address);
            customerEntity.setCity(customerRequest.city);
            customerEntity.setProvince(customerRequest.province);

            customerRepository.save(customerEntity);

            return mapToUpdateResponse(customerEntity);
        } else {
            throw new DataNotFoundException(HttpResponseConstant.DATA_NOT_EXISTS);
        }
    }

    @Transactional
    @Override
    public void deleteCustomerById(String id) {
        CustomerEntity customerEntity = customerRepository.findActiveCustomerById(id);

        if (customerEntity != null && customerEntity.getStatus() == StatusEnum.ACTIVE) {
            customerRepository.deleteCustomerById(id);
        } else {
            throw new DataNotFoundException("Data not found");
        }
    }

    private CustomerAddOAS.Response mapToResponse(CustomerEntity customerEntity) {
        CustomerAddOAS.Response response = new CustomerAddOAS.Response();
        response.id = customerEntity.getId();
        response.name = customerEntity.getName();
        response.address = customerEntity.getAddress();
        response.city = customerEntity.getCity();
        response.province = customerEntity.getProvince();
        response.registrationDate = StringUtil.isNullOrEmpty(String.valueOf(customerEntity.getRegistrationDate()))
                ? ""
                : DateUtil.formatDate(customerEntity.getRegistrationDate());
        response.status = customerEntity.getStatus();
        return response;
    }

    private CustomerDetailOAS.Response mapToDetailResponse(CustomerEntity customerEntity) {
        CustomerDetailOAS.Response response = new CustomerDetailOAS.Response();
        response.id = customerEntity.getId();
        response.name = customerEntity.getName();
        response.address = customerEntity.getAddress();
        response.city = customerEntity.getCity();
        response.province = customerEntity.getProvince();
        response.registrationDate = StringUtil.isNullOrEmpty(String.valueOf(customerEntity.getRegistrationDate()))
                ? ""
                : DateUtil.formatDate(customerEntity.getRegistrationDate());
        response.status = customerEntity.getStatus();
        return response;
    }

    private CustomerListOAS.CustomerResponse mapToCustomerListResponse(CustomerEntity customerEntity) {
        CustomerListOAS.CustomerResponse response = new CustomerListOAS.CustomerResponse();
        response.id = customerEntity.getId();
        response.name = customerEntity.getName();
        response.address = customerEntity.getAddress();
        response.city = customerEntity.getCity();
        response.province = customerEntity.getProvince();
        response.registrationDate = StringUtil.isNullOrEmpty(String.valueOf(customerEntity.getRegistrationDate()))
                ? ""
                : DateUtil.formatDate(customerEntity.getRegistrationDate());
        response.status = customerEntity.getStatus();
        return response;
    }

    private CustomerAddOAS.Request mapToRequest(CustomerEntity customerEntity) {
        CustomerAddOAS.Request request = new CustomerAddOAS.Request();
        request.name = customerEntity.getName();
        request.address = customerEntity.getAddress();
        request.city = customerEntity.getCity();
        request.province = customerEntity.getProvince();
        request.status = customerEntity.getStatus();
        return request;
    }

    private CustomerUpdateOAS.Response mapToUpdateResponse(CustomerEntity customerEntity) {
        CustomerUpdateOAS.Response response = new CustomerUpdateOAS.Response();
        response.id = customerEntity.getId();
        response.name = customerEntity.getName();
        response.address = customerEntity.getAddress();
        response.city = customerEntity.getCity();
        response.province = customerEntity.getProvince();
        response.registrationDate = StringUtil.isNullOrEmpty(String.valueOf(customerEntity.getRegistrationDate()))
                ? ""
                : DateUtil.formatDate(customerEntity.getRegistrationDate());
        response.status = customerEntity.getStatus();
        return response;
    }

    private CustomerUpdateOAS.Request mapToUpdateRequest(CustomerEntity customerEntity) {
        CustomerUpdateOAS.Request request = new CustomerUpdateOAS.Request();
        request.name = customerEntity.getName();
        request.address = customerEntity.getAddress();
        request.city = customerEntity.getCity();
        request.province = customerEntity.getProvince();
        request.status = customerEntity.getStatus();
        return request;
    }

    private boolean isInvalidCustomerRequest(CustomerAddOAS.Request request) {
        return StringUtil.isNullOrEmpty(request.name)
                || StringUtil.isNullOrEmpty(request.address)
                || StringUtil.isNullOrEmpty(request.city)
                || StringUtil.isNullOrEmpty(request.province)
                || request.status == null;
    }

    private boolean isInvalidCustomerUpdateRequest(CustomerUpdateOAS.Request request) {
        return StringUtil.isNullOrEmpty(request.name)
                || StringUtil.isNullOrEmpty(request.address)
                || StringUtil.isNullOrEmpty(request.city)
                || StringUtil.isNullOrEmpty(request.province)
                || request.status == null;
    }
}
