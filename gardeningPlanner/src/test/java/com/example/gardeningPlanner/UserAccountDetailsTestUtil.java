package com.example.gardeningPlanner;

import com.example.gardeningPlanner.Tables.UserAccount;
import com.example.gardeningPlanner.authentication.UserAccountDetails;

public class UserAccountDetailsTestUtil {
    
    private UserAccountDetailsTestUtil() {
    }

    public static UserAccountDetails createMockUser(String username, String email_address) {
        return new UserAccountDetails(new UserAccount(username, null, email_address));
    }
}
