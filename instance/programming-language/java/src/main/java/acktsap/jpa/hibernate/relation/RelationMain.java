package acktsap.jpa.hibernate.relation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RelationMain {

    public static void main(String[] args) {
        //엔티티 매니저 팩토리 생성
        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("jpa-relation");
        EntityManager entityManager = entityManagerFactory.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = entityManager.getTransaction(); //트랜잭션 기능 획득

        try {

            tx.begin(); //트랜잭션 시작
            //TODO 비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            entityManager.close(); //엔티티 매니저 종료
        }

        entityManagerFactory.close(); //엔티티 매니저 팩토리 종료
    }

}
