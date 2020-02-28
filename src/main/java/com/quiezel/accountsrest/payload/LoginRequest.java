/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.payload;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author Chalio
 */
@Data
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;
    
    @NotBlank
    private String password;
}
