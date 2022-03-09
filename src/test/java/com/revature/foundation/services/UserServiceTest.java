package com.revature.foundation.services;

import com.revature.foundation.daos.UserDAO;
import com.revature.foundation.dtos.requests.LoginRequest;
import com.revature.foundation.dtos.requests.NewUserRequest;
import com.revature.foundation.models.AppUser;
import com.revature.foundation.models.UserRole;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.DataSourceException;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import org.junit.*;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService sut; // sut = System Under Test
    private UserDAO mockUserDao = mock(UserDAO.class);

    @Before
    public void setup() {
        sut = new UserService(mockUserDao);
    }

    @After
    public void cleanUp() {
        reset(mockUserDao);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenEmptyString() {

        // AAA = Arrange, Act, and Assert

        // Arrange
        String username = "";

        // Act
        boolean result = sut.isUsernameValid(username);

        // Assert
        Assert.assertFalse(result);

    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenNullString() {

        // Arrange
        String username = null;
        // Act
        boolean result = sut.isUsernameValid(null);

        // Assert
        Assert.assertFalse(result);
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenShortUsername() {
        Assert.assertFalse(sut.isUsernameValid("short"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenLongUsername() {
        Assert.assertFalse(sut.isUsernameValid("waytolongofausernameforourapplication"));
    }

    @Test
    public void test_isUsernameValid_returnsFalse_givenUsernameWithIllegalCharacters() {
        Assert.assertFalse(sut.isUsernameValid("tester99!"));
    }

    @Test
    public void test_isUsernameValid_returnsTrue_givenValidUsername() {
        Assert.assertTrue(sut.isUsernameValid("tester99"));
    }


    @Test(expected = AuthenticationException.class)
    public void test_login_throwsAuthenticationException_givenUnknownUserCredentials() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("unknownuser", "p4$$W0RD");

        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
        when(mockUserDao.findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(null);

        // Act
        sut.login(loginRequest);

    }

    @Test
    public void test_login_returnsNonNullAppUser_givenValidAndKnownCredentials() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("tester99", "p4$$W0RD");

        when(mockUserDao.findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(new AppUser());

        // Act
        AppUser loginResult = spiedSut.login(loginRequest);

        // Assert
        assertNotNull(loginResult);
        verify(mockUserDao, times(1)).findUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

    }

    // register tests
    // - confirm the positive case (valid user provided, no conflicts)
    // - given invalid user data (empty strings/null values)
    // - given valid user, but has conflict in datasource


}
