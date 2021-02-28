/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.validation;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

public class Event {

    Integer id;

    String title;

    // Used in LocalValidatorFactoryBean
    @Min(0)
    Integer limit;

    // Used in LocalValidatorFactoryBean
    @Email
    String email;

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

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Event [id=" + id + ", title=" + title + ", limit=" + limit + ", email=" + email + "]";
    }

}

