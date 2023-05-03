package com.multi.demo.controller;

import com.multi.demo.entity.auth.Auth;
import com.multi.demo.repository.auth.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthRepository authRepository;



    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAll() {

//        List<Secondary> secondaries = secondaryRepository.findAll();
//        System.out.println(secondaries.size());
        List<Auth> authList = authRepository.findAll();

        return new ResponseEntity<>( authList.toString(), HttpStatus.OK);
    }
}
