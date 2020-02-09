/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.sample.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

/**
 * SpEL : Spring Expression Language
 *
 * #{"표현식"}
 *
 * ${"프로퍼티"}
 *
 * 표현식은 프로퍼티를 가질 수 있지만, 반대는 안 됨. #{${my.data} + 1}
 *
 * eg. @PreAuthorize, @PostAuthorize, @Query, ...
 *
 * See also
 *
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions-language-ref
 */
@Component
public class SpELRunner implements ApplicationRunner {

  /* expressions */

  @Value("#{1 + 1}")
  int value;

  @Value("#{'Hello ' + 'world'}")
  String greeting;

  @Value("#{1 eq 1}")
  boolean trueOrFalse;

  @Value("hello")
  String hello;


  /* property */

  @Value("${server.port}")
  String myValue;


  /* expression + property */

  @Value("#{${server.port} eq 8080}")
  boolean isMyValue;


  /* beans */

  @Value("#{sample}")
  Sample sample;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("================");
    System.out.println(value);
    System.out.println(greeting);
    System.out.println(trueOrFalse);
    System.out.println(hello);
    System.out.println(myValue);
    System.out.println(isMyValue);
    System.out.println(sample);

    ExpressionParser parser = new SpelExpressionParser();
    Expression expression = parser.parseExpression("100 + 44");
    Integer value = expression.getValue(Integer.class); // uses ConversionService
    System.out.println(value);
  }

}

