/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiezel.accountsrest.model.Usuario;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Chalio
 */
@Data
@AllArgsConstructor
public class UsuarioPrincipal implements UserDetails{
    private Long id;
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    
    private Collection<? extends GrantedAuthority> authorities;
    
    public static UsuarioPrincipal create(Usuario usuario){
        List<GrantedAuthority> authorities = usuario.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getName().name())).collect(Collectors.toList());
        return new UsuarioPrincipal(usuario.getId(), usuario.getUserName(), usuario.getEmail(), usuario.getPassword(), authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
