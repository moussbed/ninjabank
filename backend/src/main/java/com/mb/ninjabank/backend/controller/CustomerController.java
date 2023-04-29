package com.mb.ninjabank.backend.controller;


import com.mb.ninjabank.backend.services.CustomerService;
import com.mb.ninjabank.shared.common.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v0/customers")
public class CustomerController {
    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<CustomerResponse>  getCustomers(){
        return customerService.getCustomers();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestBody CustomerRequest customer){
        customerService.addCustomer(customer);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public CustomerResponse getCustomer(@PathVariable("id") UUID id)  {
        return customerService.getCustomer(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public CustomerResponse modifyCustomer(@PathVariable("id") UUID id, @RequestBody CustomerRequest customer) {
        return customerService.modifyCustomer(id,customer);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "attach-account/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void attachAccount(@PathVariable("id") UUID id, @RequestBody AttachAccountRequest attachAccountRequest){
       customerService.attachAccount(id,attachAccountRequest);
    }

    @PostMapping("/operation")
    public TransactionResponse operation(@RequestBody AccountStatementRequest request){
        return customerService.operation(request);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<CustomerNotification> findCustomerByAccountNumer(@PathVariable("accountNumber") String accountNumber){
        return ResponseEntity.ok(customerService.findCustomerByAccountNumer(accountNumber));

    }

}
