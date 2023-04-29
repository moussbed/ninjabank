package com.mb.ninjabank.transaction.controllers;

import com.mb.ninjabank.shared.common.dto.AccountStatementRequest;
import com.mb.ninjabank.transaction.services.AccountStatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v0/transactions")
public class AccountStatementController {

  private final AccountStatementService accountStatementService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
  public void createTransaction(@RequestBody AccountStatementRequest request){
      accountStatementService.create(request);
  }

  @RequestMapping("/validate/{accountStatementId}")
  public void validate(@PathVariable("accountStatementId") UUID accountStatementId){
    accountStatementService.validate(accountStatementId);
  }
  @RequestMapping(method = RequestMethod.GET)
  public String helloWord(){
    return "Hello world";
  }
}
