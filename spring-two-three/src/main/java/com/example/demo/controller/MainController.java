package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {
    private final MainService mainService;

    @GetMapping
    public ResponseEntity<Page<Employee>> findAll(Pageable pageable) {
        return mainService.findAll(pageable);
    }

    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Employee>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        log.info("User Details : {}", userDetails);
        return mainService.getAll();
    }

    @GetMapping("/{i}")
    public ResponseEntity<Integer> showMe(@PathVariable Integer i) {
        return mainService.showMe(i);
    }

    @GetMapping("/employee/{name}")
    public ResponseEntity<Employee> findMe(@PathVariable String name) {
        return mainService.findMe(name);
    }

    @GetMapping("/maybe")
    public ResponseEntity<Employee> mayBe() {
        return mainService.mayBe();
    }

    @GetMapping("/template/{name}")
    public ResponseEntity<Employee> restTemp(@PathVariable String name) {
        return mainService.restTemp(name);
    }

//    @GetMapping("/tempPaging")
//    public ResponseEntity<Employee> restTempPaging() {
//        return mainService.restTempPaging();
//    }
}
