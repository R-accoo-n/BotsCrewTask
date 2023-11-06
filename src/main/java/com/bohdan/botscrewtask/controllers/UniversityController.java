package com.bohdan.botscrewtask.controllers;

import com.bohdan.botscrewtask.models.Degree;
import com.bohdan.botscrewtask.models.Department;
import com.bohdan.botscrewtask.models.Lector;
import com.bohdan.botscrewtask.services.UniversityService;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniversityController {

    private static final String INVALID_COMMAND_ERROR_TEXT = "Invalid command.";

    private final UniversityService universityService;

    @Autowired
    public UniversityController(UniversityService universityService) {
        this.universityService = universityService;
    }

    @GetMapping("/run")
    public void run() {
        clearDb();
        initializeDb();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a command: ");
            String input = scanner.nextLine();
            String[] tokens = input.split(" ");

            if (tokens.length < 3) {
                System.out.println(INVALID_COMMAND_ERROR_TEXT);
                continue;
            }

            String command = tokens[0];

            switch (command) {
                case "Who" ->
                    System.out.println(universityService.findHeadOfDepartment(tokens[5]));
                case "Show" -> {
                    switch (tokens[2]) {
                        case "statistics" -> System.out.println(
                            universityService.getDepartmentStatistics(tokens[1]));
                        case "average" ->
                            System.out.println("The average salary of " + tokens[7] + " is " +
                                universityService.getAverageSalary(tokens[7]));
                        case "of" ->
                            System.out.println(universityService.getEmployeeCount(tokens[5]));
                        default -> System.out.println(INVALID_COMMAND_ERROR_TEXT);
                    }
                }
                case "Global" -> {
                    if (tokens[1].equals("search")) {
                        System.out.println("Global search by " + tokens[3]);
                        List<Lector> lectors = universityService.globalSearch(tokens[3]);
                        for(Lector lector : lectors){
                            System.out.print(lector.getName() + " ");
                        }
                        System.out.println();
                    } else {
                        System.out.println(INVALID_COMMAND_ERROR_TEXT);
                    }
                }
                default -> System.out.println(INVALID_COMMAND_ERROR_TEXT);
            }
        }
    }

    private void initializeDb(){
        Lector lector1 = new Lector();
        lector1.setDegree(Degree.ASSISTANT);
        lector1.setName("lector1");
        lector1.setSalary(200);

        Lector lector2 = new Lector();
        lector2.setDegree(Degree.PROFESSOR);
        lector2.setName("lector2");
        lector2.setSalary(800);

        Lector lector3 = new Lector();
        lector3.setDegree(Degree.ASSOCIATE_PROFESSOR);
        lector3.setName("lector3");
        lector3.setSalary(500);

        Lector lector4 = new Lector();
        lector4.setDegree(Degree.ASSOCIATE_PROFESSOR);
        lector4.setName("lector4");
        lector4.setSalary(450);

        Lector lector5 = new Lector();
        lector5.setDegree(Degree.ASSISTANT);
        lector5.setName("lector5");
        lector5.setSalary(220);

        Lector lector6 = new Lector();
        lector6.setDegree(Degree.ASSISTANT);
        lector6.setName("lector6");
        lector6.setSalary(180);

        Set<Lector> lectorSet1 = new HashSet<>();
        lectorSet1.add(lector1);
        lectorSet1.add(lector2);
        lectorSet1.add(lector3);

        Set<Lector> lectorSet2 = new HashSet<>();
        lectorSet2.add(lector4);
        lectorSet2.add(lector5);
        lectorSet2.add(lector6);

        Department department1 = new Department();
        department1.setHead(lector2);
        department1.setName("Police");
        department1.setLectors(lectorSet1);

        Department department2 = new Department();
        department2.setHead(lector4);
        department2.setName("Fire");
        department2.setLectors(lectorSet2);

        universityService.saveDepartment(department1);
        universityService.saveDepartment(department2);
        universityService.saveLector(lector1);
        universityService.saveLector(lector2);
        universityService.saveLector(lector3);
        universityService.saveLector(lector4);
        universityService.saveLector(lector5);
        universityService.saveLector(lector6);

    }

    private void clearDb(){
        universityService.deleteAllDepartments();
        universityService.deleteAllLectors();
    }

}
