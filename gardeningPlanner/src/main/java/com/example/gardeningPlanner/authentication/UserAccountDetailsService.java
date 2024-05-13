package com.example.gardeningPlanner.authentication;

import java.util.function.Supplier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.gardeningPlanner.Repositories.IUserRepository;

class UserAccountDetailsService implements UserDetailsService{
    
    private IUserRepository iUserRepository;

    UserAccountDetailsService(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userAccount = iUserRepository.findByUsername(username).orElseThrow(usernameNotFoundException(username));
        return new UserAccountDetails(userAccount);
    }

    private static Supplier<UsernameNotFoundException> usernameNotFoundException(String username) {
        return () -> new UsernameNotFoundException("Cannot find user account: " + username);
    }
}
