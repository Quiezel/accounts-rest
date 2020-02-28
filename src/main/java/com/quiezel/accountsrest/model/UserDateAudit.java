/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

/**
 *
 * @author Chalio
 */
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
@Data
public abstract class UserDateAudit extends DateAudit{
    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;
    
    @LastModifiedBy
    private long updatedBy;
    
}
