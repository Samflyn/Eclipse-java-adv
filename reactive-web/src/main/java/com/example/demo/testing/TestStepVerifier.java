package com.example.demo.testing;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.List;

@RestController
public class TestStepVerifier {

    @GetMapping("stepVerifier")
    public Flux<Emp> getData() {
        List<Emp> empList = List.of(new Emp(1, "name", "data"),
                new Emp(1, "name", "data"),
                new Emp(2, "name", "data"),
                new Emp(3, "name", "data"),
                new Emp(4, "name", "data"));
        return Flux.fromIterable(empList).delayElements(Duration.ofSeconds(3)).map(e -> {
            if (e.getNum() == 3)
                throw new RuntimeException("the num is 3");
            return e;
        });
    }

    @GetMapping("stepVerifierNoExp")
    public Flux<Emp> getDataNew() {
        List<Emp> empList = List.of(
                new Emp(1, "name", "data"),
                new Emp(1, "name", "data"),
                new Emp(2, "name", "data"),
                new Emp(3, "name", "data"),
                new Emp(4, "name", "data"));
        return Flux.fromIterable(empList).delayElements(Duration.ofSeconds(1));
    }

    @Test
    void testFlux() {
        System.out.println("Test starting");
        Flux<Emp> empFlux = getDataNew();
        empFlux.subscribe(
                emp -> System.out.println(String.format("emp data is %S", emp.toString())),
                error -> System.out.println(String.format("error %s", error.getMessage())),
                () -> System.out.println("end")
        );
        StepVerifier.create(empFlux)
                .expectNextCount(5)
                .expectError()
//                .expectComplete()
                .verify();
        System.out.println("Test ended");
    }

}
