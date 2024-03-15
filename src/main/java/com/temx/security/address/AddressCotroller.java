package com.temx.security.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/v1/addresses")
@RestController
public class AddressCotroller {
    @Autowired
    private final AddressService addressService;

    public AddressCotroller(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping()
    List<Address> getAddresses(){
        return addressService.getAddresses();
    }
}
