/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.spel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

@Component
public class SpELRunner implements ApplicationRunner {

    /* expressions */

    @Value("#{1 + 1}")
    int onePlusOne;

    @Value("#{'Hello ' + 'world'}")
    String helloPlusWorld;

    @Value("#{1 eq 1}")
    boolean oneEqOne;

    @Value("hello")
    String hello;


    /* property */

    @Value("${server.port}")
    String port;

    @Value("#{systemProperties['server.port']}")
    String systemPort;


    /* expression + property */

    @Value("#{${server.port} eq 9000}")
    boolean portIs9000;


    /* beans */

    @Value("#{sample}")
    Sample sample;

    @Value("#{sample.data}")
    int data;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("#{1 + 1} : " + onePlusOne);
        System.out.println("#{'hello' + 'World'} : " + helloPlusWorld);
        System.out.println("#{1 eq 1} : " + oneEqOne);
        System.out.println("\"hello\" : " + hello);
        System.out.println("${server.port} : " + port);
        System.out.println("#{systemProperties['server.port']} : " + systemPort); // ????
        System.out.println("#{${server.port} eq 9000} : " + portIs9000);
        System.out.println("#{sample} : " + sample);
        System.out.println("#{sample.data} : " + data);
        System.out.println("================");

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("100 + 44");
        Integer value = expression.getValue(Integer.class); // uses ConversionService
        System.out.println("100 + 44: " + value);
    }

}

