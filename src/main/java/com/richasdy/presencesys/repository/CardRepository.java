package com.richasdy.presencesys.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.richasdy.presencesys.account.Account;
import com.richasdy.presencesys.domain.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer>{
}
