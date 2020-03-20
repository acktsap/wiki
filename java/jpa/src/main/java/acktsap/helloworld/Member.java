package acktsap.helloworld;

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

@Entity
@Table(name = "MEMBER",
    // DDL 생성시 Unique 제약조건
    uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"})}
)
public class Member {

  public enum RoleType {
    ADMIN, USER
  }

  @Id
  @Column(name = "ID")
  private String id;

  // 자동 생성되는 DDL에 nullable의 false, 길이를 10
  // 이런거는 DDL에만 영향주고 실행에는 영향 없음
  @Column(name = "NAME", nullable = false, length = 10)
  private String username;

  @Column(name = "AGE")
  private Integer age;

  //=== 추가
  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  @Lob
  private String description;

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
