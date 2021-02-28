package acktsap.jpa.pattern.service.tx;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManagerFactory;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import lombok.extern.slf4j.Slf4j;

/*
  custom tx manager로 해서 REQUIRES_NEW 로 되어 있는 것을 test method에서 확인할 수 있게 하려고 했음
  But 잘 안됨... commit을 안해버리면 ThreadLocal에 있는 tx context가 안바뀌어서 이상하게 됨
  setRollbackOnly로 해도 다른 tx라서 안보임.. 나중에 다시 시도해보자

 */
@Slf4j
public class RollbackOnlyTransactionManager implements PlatformTransactionManager {

    protected final PlatformTransactionManager delegate;

    protected final Set<TransactionStatus> statuses = ConcurrentHashMap.newKeySet();

    public RollbackOnlyTransactionManager(EntityManagerFactory entityManagerFactory) {
        this.delegate = new JpaTransactionManager(entityManagerFactory);
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition)
            throws TransactionException {
        log.info("getTransaction (current object: {}, definition: {})", this, definition.getClass());
        TransactionStatus transactionStatus = delegate.getTransaction(definition);
        transactionStatus.setRollbackOnly();
        return transactionStatus;
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        log.info("[Commit]");
        delegate.commit(status);
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        log.info("[Rollback]");
        delegate.rollback(status);
    }
}
