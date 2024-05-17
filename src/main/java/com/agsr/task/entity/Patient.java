package com.agsr.task.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class Patient {

    @Id
    private String id;
    private String name;
    private String gender;
    private Date birthDate;

}

