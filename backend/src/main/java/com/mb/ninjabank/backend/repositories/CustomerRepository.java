package com.mb.ninjabank.backend.repositories;

import com.mb.ninjabank.shared.common.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("""
        SELECT CASE WHEN COUNT(customer) > 0 THEN TRUE
        ELSE FALSE END
        FROM Customer customer WHERE customer.email = ?1
     """)
    Boolean selectExistsEmail(String email);

    Customer findCustomerByAccount_Id(UUID accountId);
}
