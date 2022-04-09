package com.dreamypirates.StudentTeacherApplication.service;

import com.dreamypirates.StudentTeacherApplication.Entity.Marks;
import com.dreamypirates.StudentTeacherApplication.Entity.User;
import com.dreamypirates.StudentTeacherApplication.Model.MarksModel;
import com.dreamypirates.StudentTeacherApplication.Model.UserModel;
import com.dreamypirates.StudentTeacherApplication.repository.MarksRepository;
import com.dreamypirates.StudentTeacherApplication.repository.UserRepository;
import org.hibernate.persister.entity.Loadable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarksRepository marksRepository;


    public void saveUser(UserModel userModel) {
        User user = new User();
        user.setEmailId(userModel.getEmailId());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole(userModel.getRole());
        user.setPassword(userModel.getPassword());

        userRepository.save(user);
        LOGGER.info("User Saved Successfully");
    }

    public boolean findUserByEmail(UserModel userModel) {
        User user = new User();
        user.setEmailId(userModel.getEmailId());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setRole(userModel.getRole());
        user.setPassword(userModel.getPassword());

        Optional<User> byEmailId = userRepository.findUserByEmailId(userModel.getEmailId());
        if(byEmailId.isPresent()){
            LOGGER.info("User is present in Database");
            return true;
        }
        return false;
    }

    public String saveMarks(MarksModel marksModel) {
        Marks marks = new Marks();
        marks.setChemistry(marksModel.getChemistry());
        marks.setMaths(marksModel.getMaths());
        marks.setPhysics(marksModel.getPhysics());
        marks.setStudentEmail(marksModel.getStudentEmail());

        Optional<User> byEmailId = userRepository.findUserByEmailId(marksModel.getStudentEmail());

        if(!byEmailId.isPresent()){
            LOGGER.info("User is not present in Database");
            return "Incorrect mail address";
        }
        if(byEmailId.get().getRole().equalsIgnoreCase("teacher")){
            return "Enter Correct Student email address";
        }
        Marks save = marksRepository.save(marks);
        return "Marks update successfully";
    }

    public List<Marks> getMarks(String email) {

        Optional<User> userByEmailId = userRepository.findUserByEmailId(email);

        if(userByEmailId.get().getRole().equalsIgnoreCase("teacher")){
            List<Marks> all = marksRepository.findAll();
            return all;
        }
        Optional<List<Marks>> marks = marksRepository.findMarksByStudentEmail(email);
        return marks.get();
    }
}
