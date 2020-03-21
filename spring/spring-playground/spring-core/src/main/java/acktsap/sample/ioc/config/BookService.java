/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.ioc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class BookService {

  @Setter
  @Getter
  @Autowired
  BookRepository bookRepository;
}
