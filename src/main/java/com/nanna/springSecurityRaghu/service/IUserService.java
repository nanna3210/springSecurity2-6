package com.nanna.springSecurityRaghu.service;

import com.nanna.springSecurityRaghu.model.User;

import java.util.Optional;

public interface IUserService {
    
    
    Integer saveUser( User user );
    Optional <User> findByUsername(String username);
}
