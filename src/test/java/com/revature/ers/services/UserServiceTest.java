package com.revature.ers.services;

// Test Suite
// A class that encapsulates one or more test methods (cases)
import com.revature.ers.dtos.requests.LoginRequest;
import com.revature.ers.dtos.requests.NewUserRequest;
import com.revature.ers.dtos.responses.Principal;
import com.revature.ers.models.AppUser;
import com.revature.ers.models.UserRole;
import com.revature.ers.repos.UserRepository;
import com.revature.ers.util.exceptions.AuthenticationException;
import com.revature.ers.util.exceptions.DataSourceException;
import com.revature.ers.util.exceptions.InvalidRequestException;
import org.apache.catalina.connector.Response;
import org.apache.catalina.filters.AddDefaultCharsetFilter;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// Because the package structure of our test directory mirrors the src directory,
// Java's compiler and JVM look at this class as if it is in the same package as
// UserService.java
public class UserServiceTest {

    /*
        Common JUnit annotations:
            - @Test (marks a method as a test case)
            - @Ignore (tells JUnit to skip this test case)
            - @Before (logic that runs once before every test case)
            - @After (logic that runs once after every test case)
            - @BeforeClass (logic that runs only once before all test cases)
            - @AfterClass (logic that runs only once after all test cases)
     */

    private UserService sut; // sut = System Under Test
    private final UserRepository mockUserRepo = mock(UserRepository.class);
    private final TokenService mockTokenService = mock(TokenService.class);

    @Before
    public void setup() {
        sut = new UserService(mockUserRepo, mockTokenService);
    }

    @After
    public void cleanUp() {
        reset(mockUserRepo, mockTokenService);
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

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidUsername() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("no", "p4$$W0RD");
        HttpServletResponse mockResponse = new Response();

        // Act
        try {
            sut.login(loginRequest, mockResponse);
        } finally {
            verify(mockUserRepo, times(0)).findAppUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidPassword() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("tester99", "invalid");
        HttpServletResponse mockResponse = new Response();

        // Act
        try {
            sut.login(loginRequest, mockResponse);
        } finally {
            verify(mockUserRepo, times(0)).findAppUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = InvalidRequestException.class)
    public void test_login_throwsInvalidRequestExceptionAndDoesNotInvokeUserDao_givenInvalidUsernameAndPassword() {

        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalid", "invalid");
        HttpServletResponse mockResponse = new Response();

        // Act
        try {
            sut.login(loginRequest, mockResponse);
        } finally {
            verify(mockUserRepo, times(0)).findAppUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        }

    }

    @Test(expected = AuthenticationException.class)
    public void test_login_throwsAuthenticationException_givenUnknownUserCredentials() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);

        LoginRequest loginRequest = new LoginRequest("unknownuser", "p4$$W0RD");
        HttpServletResponse mockResponse = new Response();

        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
        when(mockUserRepo.findAppUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(null);

        // Act
        sut.login(loginRequest, mockResponse);

    }

//    @Test
//    public void test_login_returnsNonNullAppUser_givenValidAndKnownCredentials() {
//
//        // Arrange
//        UserService spiedSut = Mockito.spy(sut);
//
//        LoginRequest loginRequest = new LoginRequest("tester99", "p4$$W0RD");
//
//        when(spiedSut.isUsernameValid(loginRequest.getUsername())).thenReturn(true);
//        when(spiedSut.isPasswordValid(loginRequest.getPassword())).thenReturn(true);
//        when(mockUserRepo.findAppUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())).thenReturn(new AppUser());
//
//        // Act
//        AppUser loginResult = spiedSut.login(loginRequest);
//
//        // Assert
//        assertNotNull(loginResult);
//        verify(mockUserRepo, times(1)).findAppUserByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
//        verify(spiedSut, times(1)).isUsernameValid(loginRequest.getUsername());
//        verify(spiedSut, times(1)).isPasswordValid(loginRequest.getPassword());
//
//    }

//    // register tests
//    // - confirm the positive case (valid user provided, no conflicts)
//    // - given invalid user data (empty strings/null values)
//    // - given valid user, but has conflict in datasource
//
//    @Test
//    public void test_register_returnsPersistedAppUser_givenValidNewUserDataWithNoConflicts() {
//
//        // Arrange
//        UserService spiedSut = Mockito.spy(sut);
//        NewUserRequest stubbedRequest = new NewUserRequest("Tester", "McTesterson", "tester@revature.com", "tester99", "p4$$WORD");
//        UserRole expectedRole = new UserRole("EMPLOYEE");
//        doReturn(true).when(spiedSut).isUserValid(any());
//        doReturn(true).when(spiedSut).isUsernameAvailable(anyString());
//        doReturn(true).when(spiedSut).isEmailAvailable(anyString());
//        doReturn(null).when(mockUserRepo).save(any());
//
//        // Act
//        AppUser registerResult = spiedSut.register(stubbedRequest);
//
//        // Assert
//        assertNotNull(registerResult);
//        assertNotNull(registerResult.getId());
//        assertEquals(expectedRole, registerResult.getRole());
//        verify(spiedSut, times(1)).isUserValid(any());
//        verify(spiedSut, times(1)).isUsernameAvailable(anyString());
//        verify(spiedSut, times(1)).isEmailAvailable(anyString());
//        verify(mockUserRepo, times(1)).save(any());
//
//    }
//
    @Test(expected = InvalidRequestException.class)
    public void test_register_throwsInvalidRequestException_givenInvalidNewUserData() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest stubbedRequest = new NewUserRequest();
        doReturn(false).when(spiedSut).isUserValid(any());

        // Act
        try {
            spiedSut.register(stubbedRequest);
        } finally {
            // Assert
            verify(spiedSut, times(1)).isUserValid(any());
            verify(spiedSut, times(0)).isUsernameAvailable(anyString());
            verify(spiedSut, times(0)).isEmailAvailable(anyString());
            verify(mockUserRepo, times(0)).save(any());
        }

    }

    @Test(expected = DataSourceException.class)
    public void test_register_propagatesDataSourceException_givenDaoThrows() {

        // Arrange
        UserService spiedSut = Mockito.spy(sut);
        NewUserRequest stubbedRequest = new NewUserRequest("Tester", "McTesterson", "tester@revature.com", "tester99", "p4$$WORD");
        AppUser extractedUser = stubbedRequest.extractUser();
        UserRole expectedRole = new UserRole("EMPLOYEE");
        doReturn(true).when(spiedSut).isUserValid(extractedUser);
        doReturn(true).when(spiedSut).isUsernameAvailable(anyString());
        doReturn(true).when(spiedSut).isEmailAvailable(anyString());
        doThrow(new DataSourceException(new SQLException("stubbedSQLException"))).when(mockUserRepo).save(any());

        // Act
        try {
            spiedSut.register(stubbedRequest);
        } finally {
            verify(spiedSut, times(1)).isUserValid(any());
            verify(spiedSut, times(1)).isUsernameAvailable(anyString());
            verify(spiedSut, times(1)).isEmailAvailable(anyString());
            verify(mockUserRepo, times(1)).save(any());
        }
    }

}
