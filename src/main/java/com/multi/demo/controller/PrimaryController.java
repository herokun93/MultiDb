package com.multi.demo.controller;

import com.multi.demo.entity.primary.Primary;
import com.multi.demo.repository.primary.PrimaryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/primary")
@Slf4j
public class PrimaryController {

    @Autowired
    private PrimaryRepository primaryRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login() {

        List<Primary> primaryList = primaryRepository.findAll();
        System.out.println(primaryList.size());

        return new ResponseEntity<>( "dang thang", HttpStatus.OK);
    }
}
