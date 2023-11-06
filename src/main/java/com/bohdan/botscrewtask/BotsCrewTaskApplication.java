package com.bohdan.botscrewtask;

import com.bohdan.botscrewtask.controllers.UniversityController;
import com.bohdan.botscrewtask.repos.DepartmentRepository;
import com.bohdan.botscrewtask.services.UniversityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class BotsCrewTaskApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BotsCrewTaskApplication.class, args);
        UniversityController universityController = context.getBean(UniversityController.class);
        universityController.run();

    }

}
