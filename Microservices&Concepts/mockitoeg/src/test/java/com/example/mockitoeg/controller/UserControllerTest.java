package com.example.mockitoeg.controller;

import com.example.mockitoeg.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    void testGetUser() throws Exception {
        // Arrange
        Long userId = 1L;
        String expectedUserName = "User-1";
        when(userService.getUserName(userId)).thenReturn(expectedUserName);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Act & Assert
        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedUserName));
    }

    @Test
    void testCreateUser() throws Exception {
        // Arrange
        String userName = "John";
        String expectedResponse = "Created: John";
        when(userService.createUser(userName)).thenReturn(expectedResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userName))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }}