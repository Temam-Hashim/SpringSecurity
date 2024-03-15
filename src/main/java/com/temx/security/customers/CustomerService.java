package com.temx.security.customers;
import com.temx.security.address.AddressRepository;
import com.temx.security.exception.AlreadyExistException;
import com.temx.security.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;


    List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    Customer getCustomer(long id){

        return customerRepository.findById(id)
                .orElseThrow(
                () ->{
                    NotFoundException notFoundException = new NotFoundException("No customer found with Id " + id);
                    LOGGER.error("Error getting customer with id {}",id,notFoundException);
                    return notFoundException;
                });


//                .stream()
//                .filter(customer -> customer.getId() == id)
//                .findFirst().
//        .orElseThrow(()->new NotFoundException("Customer not found with id "+id));
    }

    public Customer createCustomer(Customer customer){

        Optional<Customer> existingCustomer = Optional.ofNullable(customerRepository.findByEmail(customer.getEmail()));
        if(existingCustomer.isPresent()){
          throw new AlreadyExistException("Email already Exists");
        }


        return customerRepository.save(customer);
    }


    public Customer updateCustomer(long customerId, Customer updatedCustomer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customerId);

        if (existingCustomer.isPresent()) {
            Customer customerToUpdate = existingCustomer.get();
            // Check if fields are present in the request, and update them if not null or empty
            if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty()) {
                customerToUpdate.setEmail(updatedCustomer.getEmail());
            }

            if (updatedCustomer.getName() != null && !updatedCustomer.getName().isEmpty()) {
                customerToUpdate.setName(updatedCustomer.getName());
            }

            if(updatedCustomer.getAddress() != null && !updatedCustomer.getAddress().isEmpty()){
                customerToUpdate.setAddress(updatedCustomer.getAddress());
            }


            return customerRepository.save(customerToUpdate);
        } else {
            throw new NotFoundException("No Customer found with ID:" + customerId);
        }
    }


    public Customer deleteCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (!customer.isPresent()) {
            throw new NotFoundException("No Customer found with ID: " + customerId);
        } else {
            customerRepository.deleteById(customerId);
            return customer.get();
        }
    }



}
