package com.mb.ninjabank.backend.services;




import com.mb.ninjabank.shared.common.dto.*;

import java.util.List;
import java.util.UUID;

public interface CustomerService {


    List<CustomerResponse>  getCustomers();

    void addCustomer(CustomerRequest customer);

    CustomerResponse getCustomer(UUID id) ;

    CustomerResponse modifyCustomer(UUID id, CustomerRequest customerRequest) ;

    void attachAccount(UUID id, AttachAccountRequest attachAccountRequest);

    TransactionResponse operation(AccountStatementRequest request);

    CustomerNotification findCustomerByAccountNumer(String accountNumber);

}
