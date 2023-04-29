package com.mb.ninjabank.transaction.repositories;

import com.mb.ninjabank.shared.common.model.AccountStatement;
import com.mb.ninjabank.shared.common.model.StatementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AccountStatementRepository extends JpaRepository<AccountStatement, UUID> {

    List<AccountStatement> findAccountStatementsByModifiedDateAfterAndStatementStatus(LocalDateTime date, StatementStatus statementStatus);

    @Query("""
        SELECT acst FROM AccountStatement  acst
        WHERE acst.modifiedDate > :modifiedDate AND acst.statementStatus = :statementStatus
    """)
    List<AccountStatement> findModifiedDateAndStatementStatus(
            @Param("modifiedDate") LocalDateTime modifiedDate, @Param("statementStatus") StatementStatus statementStatus);
}