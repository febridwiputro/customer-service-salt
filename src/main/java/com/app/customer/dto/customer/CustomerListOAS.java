package com.app.customer.dto.customer;

import com.app.customer.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CustomerListOAS {
    @Schema(name = "CustomerListOAS.Response")
    public static class Response {
        @Schema(description = "List of customers")
        public List<CustomerResponse> customers;
    }

    @Schema(name = "CustomerResponse")
    public static class CustomerResponse {
        public String id;
        public String name;
        public String address;
        public String city;
        public String province;
        public String registrationDate;
        public StatusEnum status;
    }

    @Schema(name = "CustomerListOAS.BadRequest")
    public static class BadRequest {
        @Schema(example = "BAD_REQUEST", allowableValues = {"BAD_REQUEST", "DATA_NOT_EXISTS"})
        public String message;
    }
}