package com.app.customer.dto.customer;

import com.app.customer.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerAddOAS {
    @Schema(name = "CustomerAddOAS.Request")
    public static class Request {
        @Schema(required = true, example = "Febri Dwi Putro", description = "Customer Name")
        public String name;

        @Schema(required = true, example = "Praja Dalam E No. 40", description = "Customer Address")
        public String address;

        @Schema(required = true, example = "Jakarta Selatan", description = "City")
        public String city;

        @Schema(required = true, example = "DKI Jakarta", description = "Province")
        public String province;

        @Schema(required = true, example = "ACTIVE", description = "Status")
        public StatusEnum status;
    }

    @Schema(name = "CustomerAddOAS.Response")
    public static class Response {
        public String id;
        public String name;
        public String address;
        public String city;
        public String province;
        public StatusEnum status;
        public String registrationDate;
    }

    @Schema(name = "CustomerAddOAS.BadRequest")
    public static class BadRequest {
        @Schema(example = "BAD_REQUEST", allowableValues = {"BAD_REQUEST", "DATA_NOT_EXISTS"})
        public String message;
    }
}
