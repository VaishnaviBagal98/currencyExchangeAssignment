package com.currencyexchangediscount.assignment.currencyexchangediscount.repository;


import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserName(String userName);
}
