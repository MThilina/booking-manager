package com.thilina.booking_manager.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.File;
@Configuration
public class LogDirectoryInitializer {

    @PostConstruct
    public void init() {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdir();
        }
    }
}
