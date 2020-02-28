/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest;

import com.quiezel.accountsrest.model.Cuenta;
import com.quiezel.accountsrest.model.Movimiento;
import com.quiezel.accountsrest.model.Rol;
import com.quiezel.accountsrest.model.RolName;
import com.quiezel.accountsrest.model.Usuario;
import com.quiezel.accountsrest.repos.CuentaRepo;
import com.quiezel.accountsrest.repos.MovimientoRepo;
import com.quiezel.accountsrest.repos.RolRepo;
import com.quiezel.accountsrest.repos.UsuarioRepo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Chalio
 */
@Configuration
@Slf4j
public class devData {
    
    @Bean
    CommandLineRunner initData(UsuarioRepo ur, CuentaRepo cr, MovimientoRepo mr, RolRepo rr){
        return (args) -> {
            log.info("creando datos");
            rr.save(new Rol(RolName.ROLE_USER));
            rr.save(new Rol(RolName.ROLE_ADMIN));
            Usuario yo = ur.save(new Usuario("Chalio","reveles.rosalio@gmail.com","123456"));
            log.info(yo.toString());
            Cuenta mia = new Cuenta("General", yo);
            Movimiento uno = new Movimiento(BigDecimal.valueOf(5000.0), LocalDate.now(), LocalTime.now().now(), "Cree", "Ingreso", mia);
            Movimiento dos = new Movimiento(BigDecimal.valueOf(-100.0), LocalDate.now(), LocalTime.now(), "Soriana", "Mandado", mia);
            Cuenta bano = new Cuenta("Banorte", yo);
            Movimiento cuatro = new Movimiento(BigDecimal.valueOf(-1000.0), LocalDate.now(), LocalTime.now(), "Amazon", "Compra", bano);
            Movimiento tres = new Movimiento(BigDecimal.valueOf(1300.0), LocalDate.now(), LocalTime.now().now(), "Chalio", "Pago", bano);
            cr.save(mia);
            mr.save(uno);
            mr.save(dos);
            cr.save(bano);
            mr.save(cuatro);
            mr.save(tres);
            System.out.println(mia);
            System.out.println(uno);
            System.out.println(dos);
            ur.save(new Usuario("Dulce","dulce@gmail.com","654321"));
            Cuenta fam = new Cuenta("Familiar", ur.findById(2l).get());
            fam.setTipo("Familiar");
           cr.save(fam);
        };
    }
}
