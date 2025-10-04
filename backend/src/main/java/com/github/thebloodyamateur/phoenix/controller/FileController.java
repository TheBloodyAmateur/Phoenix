package com.github.thebloodyamateur.phoenix.controller;

import org.springframework.web.bind.annotation.RestController;

import com.github.thebloodyamateur.phoenix.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("file/{id}")
    public String getFile(@PathVariable String id, @RequestParam String param) {
        return new String();
    }

    @PutMapping("file/{id}")
    public String updateFile(@PathVariable String id, @RequestBody String entity) {
        //TODO: process PUT request
        
        return entity;
    }

    @DeleteMapping("file/{id}")
    public String deleteFile(@PathVariable String id) {
        return "File with ID " + id + " deleted successfully!";
    }

    @PostMapping("file")
    public String createFile(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
}
