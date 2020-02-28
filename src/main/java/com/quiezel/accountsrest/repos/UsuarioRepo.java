/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.repos;

import com.quiezel.accountsrest.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Chalio
 */
public interface UsuarioRepo extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByUserName(String userName);
    
    Optional<Usuario> findByEmail(String email);
    
    Optional<Usuario> findByUserNameOrEmail(String userName, String email);
    
    Boolean existsByUserName(String userName);
    
    Boolean existsByEmail(String email);
    
}
