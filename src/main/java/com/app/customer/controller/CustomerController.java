package com.app.customer.controller;

import com.app.customer.constant.HttpResponseConstant;
import com.app.customer.dto.customer.CustomerAddOAS;
import com.app.customer.dto.customer.CustomerDetailOAS;
import com.app.customer.dto.customer.CustomerListOAS;
import com.app.customer.dto.customer.CustomerUpdateOAS;
import com.app.customer.exception.DataNotFoundException;
import com.app.customer.exception.ErrorMessage;
import com.app.customer.exception.SuccessMessage;
import com.app.customer.exception.ValidationException;
import com.app.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerAddOAS.Request customerRequest) throws ValidationException {
        CustomerAddOAS.Response customerResponse = customerService.createCustomer(customerRequest);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable(value = "id") String id) throws JsonProcessingException {
        try {
            Object customerResponse = customerService.getCustomerById(id);

            if (customerResponse instanceof CustomerDetailOAS.Response) {
                return new ResponseEntity<>(customerResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
            }
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCustomerByName/{name}")
    public ResponseEntity<?> getCustomerByName(@PathVariable("name") String name) {
        List<CustomerListOAS.CustomerResponse> customerResponses = customerService.getCustomerByName(name);

        if (customerResponses.isEmpty()) {
            return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }

    @GetMapping("/getFindAll")
    public ResponseEntity<?> getFindAll() {
        CustomerListOAS.Response customerResponses = customerService.findAllCustomers();

        if (customerResponses.customers.isEmpty()) {
            return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customerResponses, HttpStatus.OK);
    }

    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable(value = "customerId") String customerId, @RequestBody CustomerUpdateOAS.Request customerRequest) {
        try {
            Object customerResponse = customerService.updateCustomer(customerId, customerRequest);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        } catch (ValidationException e) {
            return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteCustomerById/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomerById(@PathVariable("id") String id) {
        try {
            customerService.deleteCustomerById(id);
            return new ResponseEntity<>(SuccessMessage.createSuccessResponse(HttpResponseConstant.DATA_DELETED_SUCCESSFULLY), HttpStatus.OK);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(ErrorMessage.createErrorResponse(HttpResponseConstant.DATA_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
    }

}