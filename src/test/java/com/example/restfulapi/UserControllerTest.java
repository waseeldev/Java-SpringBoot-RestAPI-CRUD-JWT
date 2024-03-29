package com.example.restfulapi;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.restfulapi.controller.UserController;
import com.example.restfulapi.entity.UserInfo;
import com.example.restfulapi.request.AuthRequest;
import com.example.restfulapi.service.JwtService;
import com.example.restfulapi.service.UserInfoService;

public class UserControllerTest {

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddNewUser() {
        UserInfo userInfo = new UserInfo();
        when(userInfoService.addUser(userInfo)).thenReturn("User Added Successfully");

        String response = userController.addNewUser(userInfo);

        assertEquals("User Added Successfully", response);
    }

    @Test
    public void testAuthenticateAndGetToken() {
        AuthRequest authRequest = new AuthRequest("username", "password");
        when(jwtService.generateToken("username")).thenReturn("token");

        String token = userController.authenticateAndGetToken(authRequest);

        assertEquals("token", token);
    }

    @Test
    public void testUserProfile() {
        String expectedResponse = "Welcome to User Profile";
        assertEquals(expectedResponse, userController.userProfile());
    }

    @Test
    public void testAdminProfile() {
        String expectedResponse = "Welcome to Admin Profile";
        assertEquals(expectedResponse, userController.adminProfile());
    }

    @Test
    public void testWelcome() {
        String expectedResponse = "Welcome this endpoint is not secure";
        assertEquals(expectedResponse, userController.welcome());
    }
}
