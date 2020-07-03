package acktsap.jpa.pattern.service.tx;

import static org.assertj.core.api.Assertions.assertThat;

import acktsap.jpa.pattern.model.Event;
import acktsap.jpa.pattern.repository.EventHistoryRepository;
import acktsap.jpa.pattern.repository.EventRepository;
import acktsap.jpa.pattern.service.tx.RollbackOnlyTxManagerTest.TestConfig;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestConfig.class)
public class RollbackOnlyTxManagerTest {

  @TestConfiguration
  @RequiredArgsConstructor
  static class TestConfig {

    private final DataSource dataSource;

    // name should be transactionManager so that service can use it
    @Bean
    public PlatformTransactionManager transactionManager(
        EntityManagerFactory entityManagerFactory) {
      return new RollbackOnlyTransactionManager(entityManagerFactory);
    }

  }

  @Autowired
  EventRepository eventRepository;

  @Autowired
  EventHistoryRepository eventHistoryRepository;

  @Autowired
  TxEventServiceConc txEventServiceConc;

  @Transactional
  @Test
  public void usingConc() {
    // given
    Event event = Event.builder()
        .id(1L)
        .build();

    // when
    txEventServiceConc.callsSuccessOperation(event);

    // then
    // 실패
//    assertThat(eventRepository.findById(event.getId())).isPresent();
  }
}
