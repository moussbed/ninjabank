package com.mb.ninjabank.transaction.services;



import com.mb.ninjabank.shared.common.dto.AccountStatementRequest;

import java.util.UUID;

public interface AccountStatementService {

     void create(AccountStatementRequest accountStatementRequest);
     void validate(UUID accountStatementId);

}
