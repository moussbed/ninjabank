package com.mb.ninjabank.httpclients;

import com.mb.ninjabank.httpclients.config.FeignConfiguration;

import com.mb.ninjabank.shared.common.dto.AccountStatementRequest;
import com.mb.ninjabank.shared.common.dto.CustomerNotification;
import com.mb.ninjabank.shared.common.dto.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name= "customer-service", configuration = {FeignConfiguration.class})
public interface CustomerClient {

    @PostMapping("api/v0/customers/operation")
    TransactionResponse operation(@RequestBody AccountStatementRequest request);

    @GetMapping("api/v0/customers/account/{accountNumber}")
    ResponseEntity<CustomerNotification> findCustomerByAccountNumer(@PathVariable("accountNumber") String accountNumber);


}
