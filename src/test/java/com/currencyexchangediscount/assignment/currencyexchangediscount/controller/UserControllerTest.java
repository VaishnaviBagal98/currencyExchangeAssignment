package com.currencyexchangediscount.assignment.currencyexchangediscount.controller;

import com.currencyexchangediscount.assignment.currencyexchangediscount.entity.User;
import com.currencyexchangediscount.assignment.currencyexchangediscount.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void testRegisterUser() throws Exception {
        // Prepare test data
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password123");

        // Mock the service method
        when(userService.register(any(User.class))).thenReturn(user);

        // Perform the request and verify the response
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\"}"))
                .andExpect(status().isOk());

    }

    @Test
    void testLoginSuccess() throws Exception {
        // Prepare test data
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("password123");

        // Mock the service method for successful login
        when(userService.verify(any(User.class))).thenReturn("success");

        // Perform the request and verify the response
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("success"));
    }

    @Test
    void testLoginFailure() throws Exception {
        // Prepare test data
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("wrongpassword");

        // Mock the service method for failed login
        when(userService.verify(any(User.class))).thenReturn("failure");

        // Perform the request and verify the response
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"wrongpassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("failure"));
    }
}
