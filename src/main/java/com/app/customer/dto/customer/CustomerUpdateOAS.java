package com.app.customer.dto.customer;

import com.app.customer.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public class CustomerUpdateOAS {
    @Schema(name = "CustomerUpdateOAS.Request")
    public static class Request {
        @Schema(required = true, example = "Febri Dwi Putro", description = "Updated Customer Name")
        public String name;

        @Schema(required = true, example = "Praja Dalam E No. 40", description = "Updated Customer Address")
        public String address;

        @Schema(required = true, example = "Jakarta Selatan", description = "Updated City")
        public String city;

        @Schema(required = true, example = "DKI Jakarta", description = "Updated Province")
        public String province;

        @Schema(required = true, example = "ACTIVE", description = "Updated Status")
        public StatusEnum status;
    }

    @Schema(name = "CustomerUpdateOAS.Response")
    public static class Response {
        public String id;
        public String name;
        public String address;
        public String city;
        public String province;
        public StatusEnum status;
        public String registrationDate;
    }

    @Schema(name = "CustomerUpdateOAS.BadRequest")
    public static class BadRequest extends Throwable {
        @Schema(example = "BAD_REQUEST", allowableValues = {"BAD_REQUEST", "DATA_NOT_EXISTS"})
        public String message;
    }
}
