package com.multi.demo.controller;

import com.multi.demo.entity.primary.Primary;
import com.multi.demo.entity.secondary.Secondary;
import com.multi.demo.repository.primary.PrimaryRepository;
import com.multi.demo.repository.secondary.SecondaryRepository;
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
@RequestMapping("/api/secondary")
@Slf4j
public class SecondaryController {

    @Autowired
    private SecondaryRepository secondaryRepository;

    @Autowired
    private PrimaryRepository primaryRepository;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity getAll() {

//        List<Secondary> secondaries = secondaryRepository.findAll();
//        System.out.println(secondaries.size());
        List<Primary> primaryList = primaryRepository.findAll();

        return new ResponseEntity<>( primaryList.toString(), HttpStatus.OK);
    }
}
