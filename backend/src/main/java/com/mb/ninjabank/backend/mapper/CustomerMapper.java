package com.mb.ninjabank.backend.mapper;


import com.mb.ninjabank.shared.common.dto.CustomerRequest;
import com.mb.ninjabank.shared.common.dto.CustomerResponse;
import com.mb.ninjabank.shared.common.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    @Mapping(source = "customer.createdDate", target = "createdAt")
    @Mapping(source = "customer.modifiedDate", target = "modifiedAt")
    @Mapping(source = "customer.account.id", target = "accountId")
    CustomerResponse customerToCustomerResponse(Customer customer);

    Customer customerRequestToCustomer(CustomerRequest request);
}
