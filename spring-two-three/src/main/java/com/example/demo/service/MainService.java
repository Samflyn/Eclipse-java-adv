package com.example.demo.service;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.Employee;
import com.example.demo.repository.Database;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final Database database;

    public ResponseEntity<Page<Employee>> findAll(Pageable pageable) {
        return ResponseEntity.ok(database.findAll(pageable));
    }

    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(database.findAll());
    }

    public ResponseEntity<Integer> showMe(int i) {
        Integer found = List.of(1, 2, 3, 4, 5)
                .stream()
                .filter(integer -> i == integer)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Integer not found"));
        return ResponseEntity.ok(found);
    }

    public ResponseEntity<Employee> findMe(String name) {
        List<Employee> found = database.findByName(name);
        log.info("{},{}", name, found);
        if (!found.isEmpty()) {
            return ResponseEntity.ok(found.get(0));
        }
        return new ResponseEntity<>(new Employee("id", "Sam", "sam flynn", "test@test.com"), HttpStatus.NOT_FOUND);
    }

    @Transactional
    // @Transactional(rollbackFor = Exception.class)
    // if an error occurs in method the saved data will be rolled back
    // default roll back for runtime and checked exception
    // for other exception use rollbackFor = Exception.class
    public ResponseEntity<Employee> mayBe() {
        Employee save = database.save(new Employee("sam", "sam flynn", "testing@test.com"));
        if (true) throw new RuntimeException("transactional roll backing");
        return ResponseEntity.ok(save);
    }

    public ResponseEntity<Employee> restTemp(String name) {
        ResponseEntity<Employee> entity = new RestTemplate()
                .getForEntity("http://localhost:8080/employee/" + name, Employee.class);

        new RestTemplate()
                .getForEntity("http://localhost:8080/employee/{name}", Employee.class, name);

        Employee employee = new RestTemplate()
                .getForObject("http://localhost:8080/employee/" + name, Employee.class);

        Employee[] employeeArray = new RestTemplate()
                .getForObject("http://localhost:8080/all", Employee[].class);

        ResponseEntity<List<Employee>> employeeList = new RestTemplate()
                .exchange("http://localhost:8080/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Employee>>() {
                });

        log.info("ResponseEntity : {}", entity);
        log.info("ResponseEntity Body : {}", entity.getBody());
        log.info("Object : {}", employee);
        log.info("Employee Array : {}", employeeArray);
        log.info("Employee List : {}", employeeList);

        return ResponseEntity.ok(employee);
    }

//    public ResponseEntity<Page> restTempPaging() {
//
//    }
}
