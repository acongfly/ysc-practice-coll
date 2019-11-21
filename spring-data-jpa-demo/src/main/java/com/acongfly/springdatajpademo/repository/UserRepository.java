package com.acongfly.springdatajpademo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acongfly.springdatajpademo.entity.User;

/**
 * description:
 * <p>
 * param:
 * <p>
 * return:
 * <p>
 * author: shicong yang
 * <p>
 * date: 2019-11-19
 * <p>
 * 
 * @author shicongyang
 */

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String name);
}