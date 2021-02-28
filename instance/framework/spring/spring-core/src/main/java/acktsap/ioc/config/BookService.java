/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.config;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.Setter;

public class BookService {

    @Setter
    @Getter
    @Autowired
    BookRepository bookRepository;
}
