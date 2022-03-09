package com.revature.foundation.services;

import com.revature.foundation.daos.ReimbursementDAO;
import com.revature.foundation.daos.UserDAO;
import com.revature.foundation.dtos.requests.ReimbursementRequest;
import com.revature.foundation.models.Reimbursement;
import com.revature.foundation.models.ReimbursementStatus;
import com.revature.foundation.models.ReimbursementType;
import com.revature.foundation.util.exceptions.AuthenticationException;
import com.revature.foundation.util.exceptions.DataSourceException;
import com.revature.foundation.util.exceptions.InvalidRequestException;
import org.junit.*;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ReimbursementServiceTest {

    private ReimbursementService sut;
    private ReimbursementDAO mockReimbursementDao = mock(ReimbursementDAO.class);

    @Before
    public void setup() {
        sut = new ReimbursementService(mockReimbursementDao);
    }

    @After
    public void cleanUp() {
        reset(mockReimbursementDao);
    }



}
