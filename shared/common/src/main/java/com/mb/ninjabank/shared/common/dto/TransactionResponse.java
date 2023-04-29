package com.mb.ninjabank.shared.common.dto;

import java.math.BigDecimal;

public record TransactionResponse(String accountNumber, BigDecimal balance) {
}
