package com.thowl.vocabulary.UsersRepository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.repository.UsersRepository;


@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void whenUserIsRegistered_thenItShouldBeStoredInDatabase() {
        // Given
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("testPassword");

        // When
        usersRepository.save(user);

        // Then
        Users savedUser = usersRepository.findByUsername("testUser");
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testUser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("testPassword");
    }
}

