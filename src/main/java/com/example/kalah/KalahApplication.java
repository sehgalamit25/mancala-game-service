package com.example.kalah;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * The type Kalah application.
 */
@SpringBootApplication
@Slf4j
public class KalahApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(KalahApplication.class, args);
        for (String bean : ctx.getBeanDefinitionNames()) {
            log.debug(bean);
        }
    }

}
