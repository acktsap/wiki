package jpa.hibernate.relation;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/*
  @MappedSuperclass
    - 자식 클래스에 Mapping 정보만 제공
    - 참고 : @Entity는 @Entity이거나 @MappedSuperclass 로 지정한 클래스만 상속가능
    - 여기서는 CREATED_DATE, LAST_MODIFIED_DATE가 BaseEntity를 상속받은 녀석들한테 추가
 */
@MappedSuperclass
public class BaseEntity {

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

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
}
