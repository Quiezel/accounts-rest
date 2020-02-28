/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.repos;

import com.quiezel.accountsrest.model.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Chalio
 */
public interface CuentaRepo extends JpaRepository<Cuenta, Long>{
    
    
}
