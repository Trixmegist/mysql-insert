package com.demo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.sql.DataSource;

import static java.util.Collections.emptyList;

@Service
@Transactional
public class TestService {
  private final JdbcTemplate jdbc;

  @Inject
  public TestService(DataSource dataSource) {
    this.jdbc = new JdbcTemplate(dataSource);
  }

  public int insert() {
    PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(
        "INSERT INTO test(id, ref) SELECT NULL as id, 'ref' as ref FROM DUAL " +
            "WHERE NOT EXISTS (SELECT id FROM test WHERE ref = 'ref')");
    factory.setReturnGeneratedKeys(true);
    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

    jdbc.update(factory.newPreparedStatementCreator(emptyList()), keyHolder);
    Number key = keyHolder.getKey();
    return key != null
        ? key.intValue()
        : jdbc.queryForObject("SELECT id FROM test WHERE ref = 'ref'", Integer.class);
  }
}
