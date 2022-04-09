package com.dreamypirates.StudentTeacherApplication.repository;

import com.dreamypirates.StudentTeacherApplication.Entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Marks,Long> {
    Optional<List<Marks>> findMarksByStudentEmail(String email);
}
