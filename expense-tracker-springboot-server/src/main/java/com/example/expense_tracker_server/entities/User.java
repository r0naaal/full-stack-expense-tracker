package com.example.expense_tracker_server.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
@Entity // marks class as JPA entity which means that it will be mapped to the user table in the db
@Table(name = "user") // labels this entity to the proper name

public class User {
    @Id // marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrementing primary key
    private Integer id;

    @JsonProperty("first_name")
    @Column(name = "first_name") // maps this field to the name column in the user table
    private String firstName;

    @JsonProperty("last_name")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email") // maps this field to the email column in the user table
    private String email;

    @Column(name = "password") // maps this field to the password column in the user table
    private String password;

    @Column(name = "created_at") // maps this field to the created_at column in the user table
    private LocalDateTime createdAt;

    // Getters
    public Integer getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

}
