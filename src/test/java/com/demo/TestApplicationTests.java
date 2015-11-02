package com.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.Stream.generate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebIntegrationTest("server.port=8181")
public class TestApplicationTests {

  private static void times(int count, Runnable runnable) {
    for (int i = 0; i < count; i++) {
      runnable.run();
    }
  }

  @Test
  public void concurrentInserts() throws InterruptedException, ExecutionException {
    RestTemplate rest = new RestTemplate();

    ExecutorService executor = newFixedThreadPool(100);
    allOf(generate(() ->
            runAsync(() -> times(10000, () -> rest.getForObject("http://localhost:8181/insert", Integer.class)), executor))
            .limit(8)
            .toArray(CompletableFuture[]::new)
    ).get();
  }
}
