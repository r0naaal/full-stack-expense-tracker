package com.example.expense_tracker_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.expense_tracker_server.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
