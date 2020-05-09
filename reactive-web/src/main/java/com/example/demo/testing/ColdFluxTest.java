package com.example.demo.testing;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
public class ColdFluxTest {
    @GetMapping("coldFlux")
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

    @Test
    void testFlux() throws InterruptedException {
        System.out.println("Test starting");
        Flux<Emp> empFlux = getData();
        empFlux.subscribe(new FluxSubscriberAlt<>());
        empFlux.subscribe(
                emp -> System.out.println(String.format("emp data is %S", emp.toString())),
                error -> System.out.println(String.format("error %s", error.getMessage())),
                () -> System.out.println("end")
        );
        Thread.sleep(20000);
        System.out.println("Test ended");
    }

}
