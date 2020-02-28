/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author Chalio
 */

@Data
public class RegistroRequest {
    @NotBlank
    @Size(min = 3, max = 15)
    private String username;
    
    @NotBlank
    @Size(max = 80)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
}
