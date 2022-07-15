/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.legacy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

public class BookService {

    @Setter
    @Getter
    @Autowired
    BookRepository bookRepository;
}
