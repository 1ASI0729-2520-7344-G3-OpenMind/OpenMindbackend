package com.openmind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.awt.*;
import java.net.URI;

@SpringBootApplication
public class OpenmindApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OpenmindApplication.class);
        app.addListeners((ApplicationReadyEvent event) -> {
            try {
                Desktop.getDesktop().browse(new URI("http://localhost:8080"));
            } catch (Exception ignored) {}
        });
        app.run(args);
    }

}
