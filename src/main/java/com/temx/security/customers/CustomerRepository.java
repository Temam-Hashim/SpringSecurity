package com.temx.security.customers;

import com.temx.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT u FROM Customer u WHERE u.email = ?1")
    Customer findByEmail(String email);
}
