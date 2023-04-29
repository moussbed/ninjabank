package com.mb.ninjabank.transaction.services.impl;


import com.mb.ninjabank.httpclients.CustomerClient;
import com.mb.ninjabank.messaging.rabbit.RabbitMQMessageProducer;
import com.mb.ninjabank.shared.common.dto.AccountStatementRequest;
import com.mb.ninjabank.shared.common.dto.CustomerNotification;
import com.mb.ninjabank.shared.common.dto.TransactionResponse;
import com.mb.ninjabank.shared.common.model.AccountOperation;
import com.mb.ninjabank.shared.common.model.AccountStatement;
import com.mb.ninjabank.shared.common.model.StatementStatus;
import com.mb.ninjabank.transaction.config.ConfigStatement;
import com.mb.ninjabank.transaction.repositories.AccountStatementRepository;
import com.mb.ninjabank.transaction.services.AccountStatementService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static java.lang.System.Logger.Level.INFO;

@Service
public class AccountStatementServiceImpl implements AccountStatementService {

    private static final System.Logger LOGGER = System.getLogger(AccountStatementServiceImpl.class.getName());

    private final AccountStatementRepository accountStatementRepository;

    private final CustomerClient customerClient;

    @Value("${direct.withdrawal.amount}")
    private String directWithdrawalAmount;

    private final ConfigStatement configStatement;

    private  final RabbitMQMessageProducer rabbitMQMessageProducer;

    public AccountStatementServiceImpl(AccountStatementRepository accountStatementRepository, CustomerClient customerClient,
                                       ConfigStatement configStatement,
                                       RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.accountStatementRepository = accountStatementRepository;
        this.customerClient = customerClient;
        this.configStatement = configStatement;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    @Override
    @Transactional
    public void create(AccountStatementRequest request) {
        LOGGER.log(INFO, String.format("Request %s", request));
        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setAccountNumber(request.accountNumber());
        accountStatement.setAccountOperation(request.accountOperation());
        accountStatement.setAmount(request.amount());

        if(request.amount().compareTo(new BigDecimal(directWithdrawalAmount)) < 0 ||
                request.accountOperation()== AccountOperation.CREDIT){
            TransactionResponse transactionResponse = performedTransaction(accountStatement);
            accountStatement.setBalance(transactionResponse.balance());
            accountStatement.setStatementStatus(StatementStatus.ACCEPTED);
            accountStatementRepository.save(accountStatement);
            return;
        }

        CustomerNotification customer = customerClient.findCustomerByAccountNumer(request.accountNumber()).getBody();
        rabbitMQMessageProducer.publish(customer, "notification-exchange", "notification-email-key");
        accountStatementRepository.save(accountStatement);

    }

    @Override
    public void validate(UUID accountStatementId) {
        performedValidation(accountStatementId);
    }

    @Scheduled(cron = "0 * * * * ?")
    public void accepteAllPendingTransaction(){
        Duration duration = Duration.of(configStatement.getDuration(), ChronoUnit.valueOf(configStatement.getTemporalUnit()));
        accountStatementRepository.findModifiedDateAndStatementStatus(LocalDateTime.now().minus(duration),
                StatementStatus.PENDING)
                .forEach(accountStatement -> performedValidation(accountStatement.getId())) ;
    }

    private void performedValidation(UUID accountStatementId) {
        AccountStatement accountStatement = accountStatementRepository.findById(accountStatementId)
                .orElseThrow(() ->
                        new RuntimeException(String.format("This transaction (%s) not exist ", accountStatementId)));
        TransactionResponse transactionResponse = performedTransaction(accountStatement);
        accountStatement.setBalance(transactionResponse.balance());
        accountStatement.setStatementStatus(StatementStatus.ACCEPTED);
        accountStatementRepository.save(accountStatement);
    }

    private TransactionResponse performedTransaction(AccountStatement accountStatement) {
        AccountStatementRequest accountStatementRequest = getAccountStatementRequest(accountStatement);
        return customerClient.operation(accountStatementRequest);
    }

    private AccountStatementRequest getAccountStatementRequest(AccountStatement accountStatement) {
        return new AccountStatementRequest(accountStatement.getAccountNumber(),
                accountStatement.getAmount(),
                accountStatement.getAccountOperation()
        );
    }

}
