package com.nanna.springSecurityRaghu.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "usrtab")
public class User {
    
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
     private  String username ;
     private String  password;
     @ElementCollection
     @CollectionTable(
             name = "rolestab",
             joinColumns =  @JoinColumn(name="id")
     )
     @Column(name = "role")
     private Set<String> roles;
     
    
     
     
     
     
    
}
