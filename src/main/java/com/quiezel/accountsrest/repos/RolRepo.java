/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.repos;

import com.quiezel.accountsrest.model.Rol;
import com.quiezel.accountsrest.model.RolName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Chalio
 */
public interface RolRepo extends JpaRepository<Rol, Long>{
    Optional<Rol> findByName(RolName roleName);
    
}
