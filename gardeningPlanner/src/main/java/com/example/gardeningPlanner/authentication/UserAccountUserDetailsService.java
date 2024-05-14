package com.example.gardeningPlanner.authentication;

import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.gardeningPlanner.Repositories.IUserRepository;

// Retrieves a user account from the database by username and converts it to a UserDetails object

class UserAccountUserDetailsService implements UserDetailsService{
    
    private IUserRepository iUserRepository;

    UserAccountUserDetailsService(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userAccount = iUserRepository.findByUsername(username).orElseThrow(usernameNotFoundException(username));
        return new UserAccountUserDetails(userAccount);
    }

    private static Supplier<UsernameNotFoundException> usernameNotFoundException(String username) {
        return () -> new UsernameNotFoundException("Cannot find user account: " + username);
    }
}
