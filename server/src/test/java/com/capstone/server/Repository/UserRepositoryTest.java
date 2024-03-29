package com.capstone.server.Repository;

import com.capstone.server.Domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    static User user;

    @BeforeAll
    static void setup(){
        user = User.builder()
                .id("12345")
                .userNm("tester")
                .userPw("1234")
                .role("ROLE_USER")
                .build();
    }

    @Test
    void save_test(){
        User dbuser = userRepository.save(user);
        assertThat(dbuser.getId()).isEqualTo(user.getId());
    }

    @Test
    void findById_test(){
        userRepository.save(user);

        Optional<User> user_one = userRepository.findById("12345");
        Optional<User> user_two = userRepository.findById("123");

        assertThat(user_one).isNotEmpty();
        assertThat(user_one.get().getUserNm().equals("tester"));

        assertThat(user_two).isEmpty();
    }


    @Test
    void existsByNickname_test(){
        userRepository.save(user);

        boolean result_one = userRepository.existsByUserNm("tester");
        boolean result_two = userRepository.existsByUserNm("test2");

        assertTrue(result_one);
        assertFalse(result_two);
    }

    @Test
    void existsById_test(){
        userRepository.save(user);

        boolean result_one = userRepository.existsById("12345");
        boolean result_two = userRepository.existsById("123");

        assertTrue(result_one);
        assertFalse(result_two);
    }
}