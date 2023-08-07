CREATE TABLE customer_schema.customer (
      id varchar(36) NOT NULL,
      address varchar(255) NULL,
      city varchar(255) NULL,
      "name" varchar(255) NULL,
      province varchar(255) NULL,
      registration_date timestamp NULL,
      status varchar(255) NULL,
      CONSTRAINT customer_pkey null
);