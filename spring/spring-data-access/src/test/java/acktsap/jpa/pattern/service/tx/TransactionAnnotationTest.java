package acktsap.jpa.pattern.service.tx;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @see org.springframework.transaction.annotation.Transactional
 * @see org.springframework.transaction.support.TransactionSynchronizationManager
 * @see org.springframework.transaction.interceptor.TransactionAspectSupport
 */
@Transactional
@SpringBootTest
public class TransactionAnnotationTest {


  @TestConfiguration
  static class TestConfig {

    @Bean
    public TestClass testBean() {
      return new TestClass();
    }

  }

  /* @Transactional annotation은 public으로만 써야 한다는걸 테스트 */
  @RequiredArgsConstructor
  static class TestClass {

    public void callsInternally() {
      TestClass proxy = (TestClass) AopContext.currentProxy();

      proxy.publicTxCall();

      proxy.packageTxCall();

      proxy.protectedTxCall();
    }

    @Transactional
    public void publicTxCall() {
      // public이라서 Transactional 적용 되서 안던짐
      assertThatCode(TransactionAspectSupport::currentTransactionStatus).doesNotThrowAnyException();
    }

    @Transactional
    void packageTxCall() {
      // package라서 Transactional 적용 안되서 NoTransactionException 던짐
      assertThatThrownBy(TransactionAspectSupport::currentTransactionStatus)
          .isInstanceOf(NoTransactionException.class);
    }

    @Transactional
    protected void protectedTxCall() {
      // protected라서 Transactional 적용 안되서 NoTransactionException 던짐
      assertThatThrownBy(TransactionAspectSupport::currentTransactionStatus)
          .isInstanceOf(NoTransactionException.class);
    }

    @Transactional
    public void callsInternallyWithAnnotated() {
      TestClass proxy = (TestClass) AopContext.currentProxy();

      TransactionStatus current = TransactionAspectSupport.currentTransactionStatus();

      proxy.publicTxCallRequired(current);
      proxy.publicTxCallRequireNew(current);

      proxy.packageTxCallRequired(current);
      proxy.packageTxCallRequireNew(current);

      proxy.protectedTxCallRequired(current);
      proxy.protectedTxCallRequireNew(current);
    }

    @Transactional
    public void publicTxCallRequired(TransactionStatus calledTxStatus) {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      assertThat(status).isNotEqualTo(calledTxStatus);  // public이라서 Transactional이 적용이 되어서 객체가 다름
      assertThat(status.isNewTransaction()).isFalse();  // but REQUIRED기 때문에 new tx가 아님
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publicTxCallRequireNew(TransactionStatus calledTxStatus) {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      assertThat(status).isNotEqualTo(calledTxStatus);  // public이라서 Transactional이 적용이 되어서 객체가 다름
      assertThat(status.isNewTransaction()).isTrue();   // REQUIRES_NEW기 때문에 new tx임
    }

    @Transactional
    void packageTxCallRequired(TransactionStatus calledTxStatus) {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      assertThat(status).isEqualTo(calledTxStatus); // package라서 Transactional이 적용이 안되서서 이전하고 객체가 같음
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void packageTxCallRequireNew(TransactionStatus calledTxStatus) {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      assertThat(status).isEqualTo(calledTxStatus); // package라서 Transactional이 적용이 안되서서 이전하고 객체가 같음
    }

    @Transactional
    protected void protectedTxCallRequired(TransactionStatus calledTxStatus) {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      assertThat(status)
          .isEqualTo(calledTxStatus); // protected라서 Transactional이 적용이 안되서서 이전하고 객체가 같음
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void protectedTxCallRequireNew(TransactionStatus calledTxStatus) {
      TransactionStatus status = TransactionAspectSupport.currentTransactionStatus();
      assertThat(status)
          .isEqualTo(calledTxStatus); // protected라서 Transactional이 적용이 안되서서 이전하고 객체가 같음
    }

  }

  @Autowired
  TestClass testClass;

  @Test
  void transactionalValidityTest() {
    testClass.callsInternally();

    testClass.callsInternallyWithAnnotated();
  }

}
