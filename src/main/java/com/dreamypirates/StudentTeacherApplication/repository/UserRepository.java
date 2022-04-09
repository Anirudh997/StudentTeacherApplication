package com.dreamypirates.StudentTeacherApplication.repository;

import com.dreamypirates.StudentTeacherApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findUserByEmailId(String emailId);
}
