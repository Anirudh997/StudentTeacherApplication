package com.dreamypirates.StudentTeacherApplication.controller;

import com.dreamypirates.StudentTeacherApplication.Entity.Marks;
import com.dreamypirates.StudentTeacherApplication.Entity.User;
import com.dreamypirates.StudentTeacherApplication.Model.MarksModel;
import com.dreamypirates.StudentTeacherApplication.Model.UserMarksModel;
import com.dreamypirates.StudentTeacherApplication.Model.UserModel;
import com.dreamypirates.StudentTeacherApplication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users/")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public String saveUser(@RequestBody UserModel userModel){
        LOGGER.info("Inside saveUser Method");
        if(userService.findUserByEmail(userModel)){
            return "Welcome to the Portal";
        }
        userService.saveUser(userModel);
        return "Thanks for registering. Welcome to the Portal";
    }

    @PostMapping("marks")
    public String addMarks(@RequestBody MarksModel marksModel){
        LOGGER.info("Inside addMarks Method");
        return userService.saveMarks(marksModel);
    }

    @GetMapping("{email}")
    public ResponseEntity<List<Marks>> getMarks(@PathVariable("email") String email){

        try {
            List<Marks> marks = userService.getMarks(email);
            return new ResponseEntity<List<Marks>>(marks,HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() throws InterruptedException {
        List<UserModel> users = userService.getUsers();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/students/marks")
    public ResponseEntity<CompletableFuture<List<UserMarksModel>>> getUserMarks(){

        CompletableFuture<List<UserMarksModel>> userMarks = userService.getUserMarks();
        return new ResponseEntity<>(userMarks,HttpStatus.OK);
    }
}
