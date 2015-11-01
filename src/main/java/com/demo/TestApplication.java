package com.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class TestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TestApplication.class, args);
  }

  @Bean
  public DataSource dataSource(
      @Value("${dbUrl:jdbc:mysql://localhost:3306/testDb}") String url,
      @Value("${dbUser:testUser}") String user,
      @Value("${dbPass:testPass}") String pass
  ) {
    HikariDataSource ds = new HikariDataSource();
    ds.setDriverClassName("com.mysql.jdbc.Driver");
    ds.setJdbcUrl(url);
    ds.setUsername(user);
    ds.setPassword(pass);

    ds.setMinimumIdle(4);
    ds.setMaximumPoolSize(40);
    ds.setMaxLifetime(7200);
    ds.setIdleTimeout(14000);
    ds.addDataSourceProperty("cachePrepStmts", true);
    ds.addDataSourceProperty("prepStmtCacheSize", 250);
    ds.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    ds.addDataSourceProperty("useServerPrepStmts", true);
    return ds;
  }
}
