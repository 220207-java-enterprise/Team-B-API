package com.revature.ers;

import com.revature.ers.dtos.requests.PrismRegisterRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

public class Temporary {
    public static void main(String[] args) {
        System.out.println(BCrypt.hashpw("p4$$W0R17", BCrypt.gensalt(10)));
    }
}

