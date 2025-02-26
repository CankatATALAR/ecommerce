package com.ecommerce.spring_ecommerce.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity {

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @PrePersist
    protected void onCreate(){
        creationDate = LocalDateTime.now();
        if(status == null){
            status = Status.ACTIVE;
        }
    }
    @PreUpdate
    protected void onUpdate(){
        updateDate = LocalDateTime.now();
    }

    public boolean isActive(){
        return status != null && status == Status.ACTIVE;
    }

    public enum Status {
        INACTIVE(0), ACTIVE(1);
        private final int code;
        Status(int code){
            this.code = code;
        }
        public boolean isActive(){
            return this == ACTIVE;
        }

    }
}
