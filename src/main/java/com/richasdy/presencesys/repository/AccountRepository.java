package com.richasdy.presencesys.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer>{
}
