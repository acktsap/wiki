/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.ioc.legacy.config.cliconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static java.util.Arrays.asList;

@Component
public class CliBeanConfigRunner implements ApplicationRunner {

    @Autowired
    protected Decrypter decrypter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Command Line Args: " + asList(args.getSourceArgs()));
        System.out.println("Decrypter password: " + decrypter.getPassword());
    }

}
