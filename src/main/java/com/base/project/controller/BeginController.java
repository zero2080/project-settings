package com.base.project.controller;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeginController {
  @GetMapping("/hi")
  public Map<String,String> hi(){
    return Map.of("message","hello");
  }
  @PostMapping("/hello/{id}")
  public Map<String,String> hello(@PathVariable String id) throws IllegalArgumentException{
    if(!id.equals("developer")){
      throw new IllegalArgumentException();
    }
    return Map.of("message","hi");
  }
}
