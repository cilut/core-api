package com.cilut.coreapi.entity;

import com.cilut.coreapi.annotations.NotEmptyNotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Getter
@Setter
@Entity
@DynamicUpdate
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date date;
    private String extension;
    private boolean deleted;
    @ManyToOne
    private User user;

}
