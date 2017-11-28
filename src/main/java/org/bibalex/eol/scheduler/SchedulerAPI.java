package org.bibalex.eol.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/**
 *
 * Created by sara.mustafa on 4/10/17.
 */
// mvn install:install-file -Dfile=/home/hduser/eol/workspace/Harvester_Java/target/harvester-1.0-SNAPSHOT.jar -DgroupId=org.eol.harvester -DartifactId=harvester -Dversion=1.0-SNAPSHOT -Dpackaging=jar
// java -jar target/Scheduler-1.0-SNAPSHOT.jar

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
