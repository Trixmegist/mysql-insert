package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestController {
  private final TestService testService;

  @Autowired
  public TestController(TestService testService) {
    this.testService = testService;
  }

  @RequestMapping(value = "/insert", method = GET)
  int insert() {
    return testService.insert();
  }
}
