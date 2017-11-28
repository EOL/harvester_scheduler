package org.bibalex.eol.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class SchedulerAPI extends SpringBootServletInitializer {
//    public class SchedulerAPI {
//
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SchedulerAPI.class);
    }

    public static void main(String [] args){
        SpringApplication.run(SchedulerAPI.class, args);
    }
}
