package com.thowl.vocabulary.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.thowl.vocabulary.entity.Users;
import com.thowl.vocabulary.exception.UserException;
import com.thowl.vocabulary.repository.UsersRepository;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private LoginService loginService;

    private Users existingUser;

    @Before
    public void setUp() {
        existingUser = new Users();
        existingUser.setUsername("existingUser");
        existingUser.setEmail("existing@example.com");
        existingUser.setPassword("password");
    }

    @Test
    public void testRegisterUser_SuccessfulRegistration() throws UserException {
        // Given
        String email = "new@example.com";
        String username = "newUser";
        String password = "password";
        when(usersRepository.existsByUsername(username)).thenReturn(false);
        when(usersRepository.existsByEmail(email)).thenReturn(false);
        when(usersRepository.save(any(Users.class))).thenReturn(new Users());

        // When
        Users registeredUser = loginService.registerUser(email, username, password);

        // Then
        assertTrue(registeredUser.isOnline());
        verify(usersRepository).existsByUsername(username);
        verify(usersRepository).existsByEmail(email);
        verify(usersRepository).save(any(Users.class));
    }

    @Test(expected = UserException.class)
    public void testRegisterUser_UsernameAlreadyExists() throws UserException {
        // Given
        String email = "new@example.com";
        String username = "existingUser"; // Already exists
        String password = "password";
        when(usersRepository.existsByUsername(username)).thenReturn(true);

        // When
        loginService.registerUser(email, username, password);

        // Then
        // Exception expected
    }

    // Similarly, write tests for other scenarios...

}