package com.acongfly.springdatajpademo.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.acongfly.springdatajpademo.entity.User;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByName() {
        User byName = userRepository.findByName("Eric");
        System.out.println(byName);
    }

}
