/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.security;

import com.quiezel.accountsrest.model.Usuario;
import com.quiezel.accountsrest.repos.UsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Chalio
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    @Autowired
    UsuarioRepo usuarioRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        
        return UsuarioPrincipal.create(usuario);
    }
    
    @Transactional
    public UserDetails loadUserById(Long id){
        Usuario usuario = usuarioRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        
        return UsuarioPrincipal.create(usuario);
    }
    
}
