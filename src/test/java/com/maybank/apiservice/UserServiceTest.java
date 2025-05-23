package com.maybank.apiservice;

import com.maybank.apiservice.dto.UserRequest;
import com.maybank.apiservice.exception.PaymentException;
import com.maybank.apiservice.model.User;
import com.maybank.apiservice.repository.UserRepository;
import com.maybank.apiservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setId(1L);
    }

    @Test
    void testCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setEmail("john.doe@example.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(userRequest);

        verify(userRepository).save(any(User.class));
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
    }

    @Test
    void testGetUser() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        User foundUser = userService.getUser(1L);

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetUserNotFound() {
        when(userRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        PaymentException exception = assertThrows(PaymentException.class, () -> userService.getUser(999L));

        assertEquals("User not found", exception.getMessage());
    }

}
