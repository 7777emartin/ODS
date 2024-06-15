package es.uji.ei1027.sdg;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Logger;

@SpringBootApplication
public class ODSApplication {
    private  static final Logger log = Logger.getLogger(ODSApplication.class.getName());

    public static void main(String [] args ) {
        //Autoconfiguration of the application
        new SpringApplicationBuilder(ODSApplication.class).run(args);
    }

}
