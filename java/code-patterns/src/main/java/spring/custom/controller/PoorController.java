/*
 * @copyright defined in LICENSE.txt
 */

package spring.custom.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poor")
public class PoorController {

  @GetMapping
  public String get() {
    return "I'm poor..";
  }

}
