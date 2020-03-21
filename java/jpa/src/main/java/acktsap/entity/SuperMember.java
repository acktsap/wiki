package acktsap.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/*
  @Entity
   - 기본생성자 필수 (그냥 이렇게 처리하는게 편한 듯)
   - no final field (값 채워야 하니까)
   - no final class (extends해서 뭔가 처리해야함?)

  @Table이 별도로 없으면 Entity class name을 그대로 사용
 */
@Entity
@Table(name = "SUPER_MEMBER",
    // DDL에만 영향줌
    // alter table SUPER_MEMBER
    //   add constraint NAME_AGE_UNIQUE unique (NAME, AGE)
    uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"})}
)
public class SuperMember {

  public enum RoleType {
    ADMIN, USER
  }

  /*
    @Id: private key mapping
    @GeneratedValue
      - @TABLE : sequence 생성용 테이블에서 식별자 값을 획득 후 persistant context에 저장
      - @SEQUENCE : database sequence에서 식별자 값을 획득 후 persistant context에 저장
      - @IDENTITY : db에 entity를 저장해서 identity값을 획득 후 persistant context에 저장
      - @AUTO (default) : DB 방언에 따라 알아서 설정해줌
      Primary Key는 임의의 값 (sequence or uuid 같은거)로 하는 것을 권장함. 자연 키 ㄴㄴ (eg. 주번)
   */
  // id varchar(255) not null,
  // primary key (id)
  @Id
  @Column(name = "id")
  private String id;

  // name varchar(10) not null,
  // 이런거는 DDL에만 영향주고 실행에는 영향 없음
  @Column(name = "name", nullable = false, length = 10)
  private String username;

  // age integer
  @Column(name = "age")
  private Integer age;

  /*
    @Enumerated : Java의 enum type을 mapping
      - EnumType.ORDINAL : enum 순서를 db에 저장 (eg. 0, 1, ...)
      - EnumType.STRING : enum 이름을 db에 저장
   */
  // role_type varchar(255),
  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  /*
    @Temporal : 날짜 타입 (java.util.Date, java.util.Calander)를 mapping할 때 사용
      - DATE : Map as java.sql.Date (eg. 2013-10-11)
      - TIME : Map as java.sql.Time (eg. 11:11:11)
      - TIMESTAMP : Map as java.sql.Timestamp (eg. 2013-10-11 11:11:11)
   */
  // created_date timestamp,
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  // hibernate.ejb.naming_strategy 때문에 lastModifiedDate -> last_modified_date 으로 mapping됨
  // eg. last_modified_date varchar(255)
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  /*
    @Lob : db BLOG, CLOB와 mapping
      - String, char[] : Map as ajva.sql.CLOB
      - byte[] : Map as java.sql.BLOB
   */
  // description clob,
  @Lob
  private String description;

  /*
    @Transient : db에 mapping하지 않음. 객체에 임의로 값을 저장하고 싶을 때 사용
   */
  @Transient
  private String temp;

  //Getter, Setter

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }
}
