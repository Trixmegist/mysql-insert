package com.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TestController {
  private final TestService testService;

  @Inject
  public TestController(TestService testService) {
    this.testService = testService;
  }

  @RequestMapping(value = "/insert", method = GET)
  int insert() {
    return testService.insert();
  }
}
