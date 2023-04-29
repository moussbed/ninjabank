package com.mb.ninjabank.backend.repositories;

import com.mb.ninjabank.shared.common.model.Account;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;


@RepositoryRestResource
public interface AccountRepository extends NaturalRepository<Account, UUID> {

}
