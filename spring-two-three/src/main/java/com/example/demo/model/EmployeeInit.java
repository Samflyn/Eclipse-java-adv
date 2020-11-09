package com.example.demo.model;

import com.example.demo.repository.Database;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeInit {
    private final Database database;

    @PostConstruct
    public void databaseInit() {
        if (database.count() != 1000) {
            database.deleteAll();
            List<Employee> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                list.add(new Employee("Sam" + i, "samflynn", "test" + i + "@test.com"));
            }
            database.saveAll(list);
        }
        log.info("Data initialized");
    }
}
