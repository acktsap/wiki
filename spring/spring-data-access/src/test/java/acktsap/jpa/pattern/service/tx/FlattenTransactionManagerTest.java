package acktsap.jpa.pattern.service.tx;

import static org.assertj.core.api.Assertions.assertThat;

import acktsap.jpa.pattern.service.tx.FlattenTransactionManagerTest.TestConfig;
import javax.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @see org.springframework.test.context.transaction.TransactionalTestExecutionListener
 * @see org.springframework.transaction.support.TransactionSynchronizationManager
 * @see org.springframework.transaction.interceptor.TransactionAspectSupport
 */
@SpringBootTest(classes = TestConfig.class)
public class FlattenTransactionManagerTest {

  @TestConfiguration
  static class TestConfig {

    // bean 등록하면 autoconfigure 안해주고 전부 이거만 씀
    @Bean
    public PlatformTransactionManager flattenTransactionManager(
        EntityManagerFactory entityManagerFactory) {

      return new PlatformTransactionManager() {

        protected final PlatformTransactionManager delegate = new JpaTransactionManager(
            entityManagerFactory);

        @Override
        public TransactionStatus getTransaction(TransactionDefinition transactionDefinition)
            throws TransactionException {
          TransactionDefinition definition = transactionDefinition;
          try {
            TransactionAspectSupport.currentTransactionStatus();
          } catch (NoTransactionException ignored) {
            // 처음 만나는 require_new인 경우 required로 바꿔버림
            definition = new RequireNewFlattenTransactionDefinition(transactionDefinition);
          }

          TransactionStatus transaction = delegate.getTransaction(definition);
          transaction.setRollbackOnly();

          return transaction;
        }

        @Override
        public void commit(TransactionStatus status) throws TransactionException {
          delegate.commit(status);
        }

        @Override
        public void rollback(TransactionStatus status) throws TransactionException {
          delegate.rollback(status);
        }
      };
    }

    @Bean
    public TestClass testBean() {
      return new TestClass();
    }

  }

  @RequiredArgsConstructor
  static class RequireNewFlattenTransactionDefinition implements TransactionDefinition {

    private final TransactionDefinition delegate;

    @Override
    public int getPropagationBehavior() {
      int propagationBehavior = delegate.getPropagationBehavior();
      if (delegate.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW) {
        // REQUIRES_NEW를 REQUIRED로
        propagationBehavior = TransactionDefinition.PROPAGATION_REQUIRED;
      }
      return propagationBehavior;
    }

    @Override
    public int getIsolationLevel() {
      return delegate.getIsolationLevel();
    }

    @Override
    public int getTimeout() {
      return delegate.getTimeout();
    }

    @Override
    public boolean isReadOnly() {
      return delegate.isReadOnly();
    }

    @Override
    @Nullable
    public String getName() {
      return delegate.getName();
    }

  }

  static class TestClass {

    @Transactional
    public void txCall() {
      ((TestClass) AopContext.currentProxy()).indirectRequireNewCall();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void indirectRequireNewCall() {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      // indirect인 경우 처음 만나는 녀석이 아니라서 그대로 둠
      assertThat(status.isNewTransaction()).isTrue();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void directRequireNewCall() {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      // direct인 경우 처음 만나는 녀석이기 때문에 flatten
      // 정확히는 true여야 하는데 Test code에서 @Transactional을 해버려서 이미 tx가 있음
      // test 코드 말고 그냥 실행하면 true나옴
      assertThat(status.isNewTransaction()).isFalse();
    }
  }

  @Autowired
  TestClass testClass;

  @Autowired
  BeanFactory beanFactory;

  // Rollback only가 유일한 TransactionManager bean으로 등록되어서 사실 필요 없음
  // But 테스트 코드의 컨벤션을 따라서 @Transactional을 적는다
  @Transactional
  @Test
  public void test() {
    testClass.txCall();
    testClass.directRequireNewCall();
  }

}
