package com.pc1.backendrupay.configs.security;

import com.pc1.backendrupay.domain.UserModel;
import com.pc1.backendrupay.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new User(userModel.getName(), userModel.getPassword(), true, true, true, true, userModel.getAuthorities());
    }
}
