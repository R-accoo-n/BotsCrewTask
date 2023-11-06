package com.bohdan.botscrewtask.services;

import com.bohdan.botscrewtask.models.Degree;
import com.bohdan.botscrewtask.models.Department;
import com.bohdan.botscrewtask.models.Lector;
import com.bohdan.botscrewtask.repos.DepartmentRepository;
import com.bohdan.botscrewtask.repos.LectorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {

    private final DepartmentRepository departmentRepository;

    private final LectorRepository lectorRepository;

    @Autowired
    public UniversityService(DepartmentRepository departmentRepository,
                             LectorRepository lectorRepository) {
        this.departmentRepository = departmentRepository;
        this.lectorRepository = lectorRepository;
    }



    public String findHeadOfDepartment(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            Lector head = department.getHead();
            return "Head of " + departmentName + " department is " + head.getName();
        }
        return "Department not found.";
    }

    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            long assistantsCount = department.getLectors().stream()
                .filter(lector -> lector.getDegree().equals(Degree.ASSISTANT))
                .count();
            long associateProfessorsCount = department.getLectors().stream()
                .filter(lector -> lector.getDegree().equals(Degree.ASSOCIATE_PROFESSOR))
                .count();
            long professorsCount = department.getLectors().stream()
                .filter(lector -> lector.getDegree().equals(Degree.PROFESSOR))
                .count();

            return "assistants - " + assistantsCount + ".\n"
                + "associate professors - " + associateProfessorsCount + ".\n"
                + "professors - " + professorsCount + ".";
        } else {
            return "Department not found.";
        }
    }

    public double getAverageSalary(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            double totalSalary = department.getLectors()
                .stream()
                .mapToDouble(Lector::getSalary)
                .sum();
            int lectorCount = department.getLectors().size();
            if (lectorCount > 0) {
                return totalSalary / lectorCount;
            }
            else {
                return -1;
            }
        }else {
            return -1;
        }
    }

    public int getEmployeeCount(String departmentName) {
        Department department = departmentRepository.findByName(departmentName);
        if (department != null) {
            return department.getLectors().size();
        }else {
            return -1;
        }
    }

    public List<Lector> globalSearch(String template) {
        return lectorRepository.findByNameContaining(template);
    }

    public void saveLector(Lector lector){
        lectorRepository.save(lector);
    }
    public void saveDepartment(Department department){
        departmentRepository.save(department);
    }

    public void deleteAllLectors(){
        lectorRepository.deleteAll();
    }

    public void deleteAllDepartments(){
        departmentRepository.deleteAll();
    }

}
