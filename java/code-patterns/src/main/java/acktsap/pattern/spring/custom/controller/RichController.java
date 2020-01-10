/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.pattern.spring.custom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rich")
public class RichController {

  @GetMapping
  public String get() {
    return "I'm rich!!";
  }

}
