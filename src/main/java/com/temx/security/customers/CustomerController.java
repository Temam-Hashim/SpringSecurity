package com.temx.security.customers;

import com.temx.security.address.Address;
import com.temx.security.address.AddressRepository;
import com.temx.security.exception.ApiRequestException;
import com.temx.security.exception.DeletedException;
import com.temx.security.exception.NotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/customers")
@RestController
//@Deprecated
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    private final AddressRepository addressRepository;


    @GetMapping()
     List<Customer> getCustomers(){
        return customerService.getCustomers();
    }


    @GetMapping(path="{customerId}")
    Customer getCustomer(@PathVariable("customerId") long id){
        return customerService.getCustomer(id);
    }

    @GetMapping(path="{customerId}/exception")
    Customer getCustomerException(@PathVariable("customerId") long id){
       throw new ApiRequestException("ApiRequestException for Customer with id "+id);
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer){
        Customer newCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCustomer);
    }

    @PutMapping(path="{customerId}")
    public Customer updateCustomer(@PathVariable("customerId") long customerId,@RequestBody Customer customer){
        // Encrypt the password
        //        customer.setPassword(BCryptPasswordEncoder.encode(customer.getPassword()));

        Customer newCustomer = customerService.updateCustomer( customerId,customer);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(newCustomer).getBody();
    }

    @DeleteMapping(path = "{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") long customerId) {
        Customer deletedCustomer = customerService.deleteCustomer(customerId);
        if (deletedCustomer != null) {
            throw new DeletedException("Customer with Id "+customerId+" deleted");
        } else {
            throw new NotFoundException("Customer Not found");
        }
    }






}
