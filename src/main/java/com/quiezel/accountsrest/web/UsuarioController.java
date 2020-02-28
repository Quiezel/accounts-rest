/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.web;

import com.quiezel.accountsrest.model.Usuario;
import com.quiezel.accountsrest.repos.UsuarioRepo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Chalio
 */
@RestController
@RequestMapping("/api")
public class UsuarioController {
    
    private final Logger log = LoggerFactory.getLogger(UsuarioController.class);
    private UsuarioRepo usuarioRepo;

    public UsuarioController(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }
    
    @GetMapping("/usuarios")
    Collection<Usuario> usuarios(){
        return usuarioRepo.findAll();
    }
    
    @GetMapping("/usuario/{id}")
    ResponseEntity<?> getUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioRepo.findById(id);
        return usuario.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/usuario")
    ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody Usuario usuario)throws URISyntaxException{
        log.info("Peticion para crear usuario: {}", usuario);
        Usuario result = usuarioRepo.save(usuario);
        return ResponseEntity.created(new URI("/api/group" + result.getId()))
                .body(result);
    }
    
    @PutMapping("/usuario/{id}")
    ResponseEntity<Usuario> actualizarUsuario(@Valid @RequestBody Usuario usuario){
        log.info("Peticion para actualizar usuario: {}", usuario);
        Usuario result = usuarioRepo.save(usuario);
        return ResponseEntity.ok().body(result);
    }
    
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> borrarUsuario(@PathVariable Long id){
        log.info("Peticion para borrar usuario: {}", id);
        usuarioRepo.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
}
