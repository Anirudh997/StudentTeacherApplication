package com.dreamypirates.StudentTeacherApplication.service;

import com.dreamypirates.StudentTeacherApplication.Entity.Marks;
import com.dreamypirates.StudentTeacherApplication.Entity.User;
import com.dreamypirates.StudentTeacherApplication.Model.MarksModel;
import com.dreamypirates.StudentTeacherApplication.Model.UserMarksModel;
import com.dreamypirates.StudentTeacherApplication.Model.UserModel;
import com.dreamypirates.StudentTeacherApplication.repository.MarksRepository;
import com.dreamypirates.StudentTeacherApplication.repository.UserRepository;
import org.hibernate.persister.entity.Loadable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${AppUrl}")
    private String url;


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

    public List<UserModel> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> userModelList = new ArrayList<>();
        for(User u : users){
            LOGGER.info("Inside get users thread " + Thread.currentThread().getName());
            userModelList.add(UserModel.builder().role(u.getRole()).firstName(u.getFirstName())
            .lastName(u.getLastName()).emailId(u.getEmailId()).build());
        }
        return userModelList;
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<UserMarksModel>> getUserMarks() {
        LOGGER.info("Inside getUserMarks under thread " + Thread.currentThread().getName());
        WebClient webClient = WebClient.create(url);
        Flux<UserModel> userModelFlux = webClient.get().retrieve().bodyToFlux(UserModel.class);
        List<UserModel> usersList = userModelFlux.collectList().block();
        List<UserMarksModel> userMarksList = new ArrayList<>();

        for(UserModel user : usersList){
            LOGGER.info("Inside get marks under thread " + Thread.currentThread().getName());
            WebClient webClient1 = WebClient.create(url + user.getEmailId());
            List<MarksModel> userMarks = webClient1.get().retrieve().bodyToFlux(MarksModel.class).collectList().block();
            userMarksList.add(UserMarksModel.builder().firstName(user.getFirstName()).lastName(user.getLastName())
            .emailAddress(user.getEmailId()).marks(userMarks).build());
            LOGGER.info("Getting out from getMarks under thread " + Thread.currentThread().getName());
        }
        return CompletableFuture.completedFuture(userMarksList);
    }
}
