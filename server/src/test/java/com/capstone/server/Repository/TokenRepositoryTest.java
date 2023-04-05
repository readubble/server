package com.capstone.server.Repository;

import com.capstone.server.Domain.Token;
import com.capstone.server.Domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource("classpath:application.yml")
class TokenRepositoryTest {
    @Autowired
    TokenRepository tokenRepository;
    static Token token;

    @BeforeAll
    static void setup(){
        token = Token.builder()
                .userId("1234")
                .token("token")
                .status(true).build();
    }
    @Test
    void save_test(){
        Token dbtoken = tokenRepository.save(token);
        assertThat(dbtoken.getUserId()).isEqualTo(token.getUserId());
    }

    @Test
    void existsByToken_test(){
        tokenRepository.save(token);

        boolean result_one = tokenRepository.existsByToken("token");
        boolean result_two = tokenRepository.existsByToken("token2");

        assertTrue(result_one);
        assertFalse(result_two);
    }
}