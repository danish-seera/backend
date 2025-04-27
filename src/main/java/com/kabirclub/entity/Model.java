package com.kabirclub.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String basedIn;

    @Column
    private String gender;

    @Column
    private String primaryImageUrl;

    @Column
    private String username;

    @Column
    private String socialCount;

    @Column
    private Integer modelDotComId;
} 