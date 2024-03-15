//package com.temx.security.customers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Collections;
//import java.util.List;
//
//@RequestMapping("api/v2/customer")
//@RestController
//public class CustomerControllerV2 {
//
//    private final CustomerService customerService;
//
//    @Autowired
//    public CustomerControllerV2(CustomerService customerService){
//        this.customerService = customerService;
//    }
//
//    @GetMapping(value="/all")
//    List<Customer> getCustomer() {
//
//        return Collections.singletonList(new Customer(0L, "V2", "V2V2", "email@gmail.com",null));
//    }
//
//    @PostMapping()
//    public void createCustomer(@RequestBody Customer customer){
//        System.out.println("POST Method:");
//        System.out.println(customer);
//    }
//
//    @PutMapping()
//    public void updateCustomer(@RequestBody Customer customer){
//        System.out.println("PUT Method:");
//        System.out.println(customer);
//    }
//
//    @DeleteMapping(path="{customerId}")
//    public void deleteCustomer(@PathVariable("customerId") long id){
//        System.out.println("DELETE METHOD to Delete Customer with Id:"+id);
//    }
//
//
//}
