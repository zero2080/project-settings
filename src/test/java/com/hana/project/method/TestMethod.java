package com.hana.project.method;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;


@SpringBootTest
@Profile("test")
public class TestMethod {

  @Test
  public void test() {
    String name = "test";
    System.out.println(name);
  }

}
