package acktsap.relation;

import java.util.Date;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity {

  private Date createdDate;
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