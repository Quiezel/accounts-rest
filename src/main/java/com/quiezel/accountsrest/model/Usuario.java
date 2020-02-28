/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NaturalId;

/**
 *
 * @author Chalio
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "userName"
    })
    ,
    @UniqueConstraint(columnNames = {
        "email"
    })
})
public class Usuario extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String userName;
    @NaturalId
    @NonNull
    private String email;
    @NonNull
    private String password;
    @OneToMany(mappedBy = "usuario",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnoreProperties("usuario")
    private List<Cuenta> cuentas = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name =     "rol_id"))
    private Set<Rol> roles = new HashSet<>();

    public void addRol(Rol rol) {
        roles.add(rol);
        rol.getUsuarios().add(this);
    }

    public void removeRol(Rol rol) {
        roles.remove(rol);
        rol.getUsuarios().remove(this);
    }
}
