package com.app.customer.repository;

import com.app.customer.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    @Query(value = "SELECT * FROM customer_schema.customer WHERE status = 'ACTIVE'", nativeQuery = true)
    List<CustomerEntity> findAllActiveCustomers();

    @Query(value = "SELECT * FROM customer_schema.customer WHERE status = 'ACTIVE' AND id = :id", nativeQuery = true)
    CustomerEntity findActiveCustomerById(@Param("id") String id);

    @Query(value = "SELECT * FROM customer_schema.customer WHERE name LIKE %:name% AND status = 'ACTIVE'", nativeQuery = true)
    List<CustomerEntity> findActiveCustomersByNameContaining(@Param("name") String name);

    @Modifying
    @Query(value = "UPDATE customer_schema.customer SET status = 'INACTIVE' WHERE id = :id AND status = 'ACTIVE'", nativeQuery = true)
    void deleteCustomerById(@Param("id") String id);

}