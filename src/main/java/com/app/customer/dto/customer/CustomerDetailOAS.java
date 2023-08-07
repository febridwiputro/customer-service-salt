package com.app.customer.dto.customer;

import com.app.customer.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerDetailOAS {
    @Schema(name = "CustomerDetailOAS.Response")
    public static class Response {
        public String id;
        public String name;
        public String address;
        public String city;
        public String province;
        public String registrationDate;
        public StatusEnum status;
    }

    @Schema(name = "CustomerDetailOAS.BadRequest")
    public static class BadRequest{
        @Schema(example = "BAD_REQUEST", allowableValues = {"BAD_REQUEST", "DATA_NOT_EXISTS"})
        public String message;
    }
}