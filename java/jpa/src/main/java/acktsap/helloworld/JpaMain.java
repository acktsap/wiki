package acktsap.helloworld;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

  public static void logic(EntityManager entityManager) {
    // 생성
    String id = "id1";
    Member member = new Member();
    member.setId(id);
    member.setUsername("지한");
    member.setAge(2);

    // 등록 및 조회
    entityManager.persist(member);
    Member member1 = entityManager.find(Member.class, id);
    System.out.println("findMember=" + member1.getUsername() + ", age=" + member1.getAge());

    // 수정
    member.setAge(20);

    // 한 건 조회
    Member member2 = entityManager.find(Member.class, id);
    System.out.println("findMember=" + member2.getUsername() + ", age=" + member2.getAge());

    // 목록 조회
    List<Member> members = entityManager.createQuery("select m from Member m", Member.class)
        .getResultList();
    System.out.println("members.size=" + members.size());

    // 삭제
    entityManager.remove(member);
  }

  public static void main(String[] args) {
    // 엔티티 매니저 팩토리 생성
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
    EntityManager entityManager = entityManagerFactory.createEntityManager(); // 엔티티 매니저 생성

    EntityTransaction tx = entityManager.getTransaction(); // 트랜잭션 기능 획득

    try {
      tx.begin(); // 트랜잭션 시작
      logic(entityManager);  // 비즈니스 로직
      tx.commit();// 트랜잭션 커밋
    } catch (Exception e) {
      e.printStackTrace();
      tx.rollback(); // 트랜잭션 롤백
    } finally {
      entityManager.close(); // 엔티티 매니저 종료
    }

    entityManagerFactory.close(); // 엔티티 매니저 팩토리 종료
  }

}