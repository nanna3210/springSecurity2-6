package com.nanna.springSecurityRaghu.service;

import com.nanna.springSecurityRaghu.model.User;
import com.nanna.springSecurityRaghu.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IUserServiceImpl implements  IUserService, UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public Integer saveUser ( User user ) {
        user.setPassword ( passwordEncoder.encode ( user.getPassword () ) );
        
        User savedUser = userRepository.save ( user );
        return savedUser.getId ();
    }
    
    @Override
    public Optional < User > findByUsername ( String username ) {
        return userRepository.findByUsername ( username );
    }
    
    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        Optional < User > optionalUser = findByUsername ( username );
        if(optionalUser.isEmpty ()){
            throw new UsernameNotFoundException ( "user not exist " );
        }
        User user = optionalUser.get ();
        
        return new org.springframework.security.core.userdetails.User (
//                username,
                user.getUsername (),
                user.getPassword (),
                user.getRoles ().stream ().map ( role -> new SimpleGrantedAuthority ( role ) )
                        .collect( Collectors.toList())
                
                );
    }
}
