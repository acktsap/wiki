/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.databinding;

public class Event {

  Integer id;

  String title;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
  @Override
  public String toString() {
    return String.format("Event(id=%s, title=%s)", id, title);
  }

}
