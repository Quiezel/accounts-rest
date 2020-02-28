/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.web;

import com.quiezel.accountsrest.exception.AppException;
import com.quiezel.accountsrest.model.Rol;
import com.quiezel.accountsrest.model.RolName;
import com.quiezel.accountsrest.model.Usuario;
import com.quiezel.accountsrest.payload.*;
import com.quiezel.accountsrest.repos.RolRepo;
import com.quiezel.accountsrest.repos.UsuarioRepo;
import com.quiezel.accountsrest.security.JwtTokenProvider;
import java.net.URI;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Chalio
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsuarioRepo usuarioRepo;
    @Autowired
    RolRepo rolRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenProvider tokenProvider;
    
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<?> authenticateUsuario(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistroRequest registroRequest){
        if (usuarioRepo.existsByUserName(registroRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Usuario ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioRepo.existsByEmail(registroRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email ya esta en uso"), HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario(registroRequest.getUsername(), registroRequest.getEmail(), registroRequest.getPassword());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        Rol usuarioRol = rolRepo.findByName(RolName.ROLE_USER)
                .orElseThrow(() -> new AppException("Rol de usuario no asignado."));
        usuario.setRoles(Collections.singleton(usuarioRol));
        
        Usuario result = usuarioRepo.save(usuario);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/usuarios/{username}")
                .buildAndExpand(result.getUserName()).toUri();
        
        return ResponseEntity.created(location).body(new ApiResponse(true, "Usuario registrado con exito"));
    }
}