package com.mb.ninjabank.backend.services.impl;


import com.mb.ninjabank.backend.mapper.CustomerMapper;
import com.mb.ninjabank.backend.repositories.AccountRepository;
import com.mb.ninjabank.backend.repositories.CustomerRepository;
import com.mb.ninjabank.backend.services.CustomerService;
import com.mb.ninjabank.shared.common.dto.*;
import com.mb.ninjabank.shared.common.exceptions.NotFoundException;
import com.mb.ninjabank.shared.common.exceptions.WithdrawalException;
import com.mb.ninjabank.shared.common.model.Account;
import com.mb.ninjabank.shared.common.model.AccountOperation;
import com.mb.ninjabank.shared.common.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static java.lang.System.Logger.Level.ERROR;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private static final System.Logger LOGGER = System.getLogger(CustomerServiceImpl.class.getName());

    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper.MAPPER::customerToCustomerResponse)
                .toList();
    }

    @Override
    public void addCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.MAPPER.customerRequestToCustomer(request);
        customerRepository.save(customer);
    }

    @Override
    public CustomerResponse getCustomer(UUID id) {
        Customer customer = getCustomerById(id);
        return CustomerMapper.MAPPER.customerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse modifyCustomer(UUID id, CustomerRequest customerRequest){
        Customer customer = getCustomerById(id);
        customer.setFirstName(customerRequest.firstName());
        customer.setLastName(customerRequest.lastName());
        customer = customerRepository.save(customer);

        return CustomerMapper.MAPPER.customerToCustomerResponse(customer);
    }

    @Override
    @Transactional
    public void attachAccount(UUID id, AttachAccountRequest attachAccountRequest) {
        Customer customer = getCustomerById(id);
        Account account = accountRepository.findById(attachAccountRequest.accountId()).orElseThrow(() -> {
            LOGGER.log(ERROR, String.format("Account %s not found ", id));
            return new NotFoundException(String.format("Account %s not found ", id));
        });
        customer.setAccount(account);
    }

    @Override
    @Transactional
    public TransactionResponse operation(AccountStatementRequest request) {
        String accountNumber = request.accountNumber();
        Account account = getAccountByAccountNumber(accountNumber);
        if (Objects.isNull(account)){
            LOGGER.log(ERROR, String.format("Account %s not exist", accountNumber));
            throw new NotFoundException(String.format("Account %s not exist", accountNumber));
        }

        if(request.accountOperation()== AccountOperation.DEBIT) {
            if (account.getBalance().compareTo(request.amount()) < 0){
                LOGGER.log(ERROR, String.format("Account %s balance is not sufficient ", accountNumber));
                throw new WithdrawalException("Your balance is not sufficient");
            }

            account.setBalance(account.getBalance().subtract(request.amount()));
        }
        else if (request.accountOperation() == AccountOperation.CREDIT)
            account.setBalance(account.getBalance().add(request.amount()));

        return new TransactionResponse(account.getAccountNumber(), account.getBalance());

    }

    @Override
    public CustomerNotification findCustomerByAccountNumer(String accountNumber)  {
        Account account = getAccountByAccountNumber(accountNumber);
        Customer customer = customerRepository.findCustomerByAccount_Id(account.getId());
        if(customer==null){
            LOGGER.log(ERROR, String.format("Customer with %s account number not exist",accountNumber));
            throw new NotFoundException(String.format("Customer with %s account number not exist",accountNumber));
        }
        return new CustomerNotification(customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail());
    }

    private Account getAccountByAccountNumber(String accountNumber){
        return accountRepository
                .findByNaturalId(Map.of("accountNumber", accountNumber))
                .orElseThrow(()->  {
                    LOGGER.log(ERROR, String.format("Account number %s not exist",accountNumber));
                    return new NotFoundException(String.format("Account number %s not exist",accountNumber));
                });

    }

    private Customer getCustomerById(UUID id)  {
        return customerRepository.findById(id).orElseThrow(()-> {
            LOGGER.log(ERROR, String.format("Customer %s not found ",id));
            return new NotFoundException(String.format("Customer %s not found ",id));
        });
    }

}
