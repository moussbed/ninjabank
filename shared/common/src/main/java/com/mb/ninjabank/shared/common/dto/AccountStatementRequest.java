package com.mb.ninjabank.shared.common.dto;

import com.mb.ninjabank.shared.common.model.AccountOperation;

import java.math.BigDecimal;

public record AccountStatementRequest(String accountNumber,
                                      BigDecimal amount,
                                      AccountOperation accountOperation
) {
}
