package com.juan.app_security.repositories;

import com.juan.app_security.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface CustomerRepository extends CrudRepository<CustomerEntity, BigInteger> {

}
