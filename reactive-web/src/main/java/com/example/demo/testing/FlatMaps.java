package com.example.demo.testing;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class FlatMaps {
    public Flux<Emp> getData() {
        return Flux.just(
                new Emp(1, "name", "data"),
                new Emp(1, "name", "data"),
                new Emp(2, "name", "data"),
                new Emp(3, "name", "data"),
                new Emp(4, "name", "data")
        ).delayElements(Duration.ofSeconds(3));
    }

    @Test
    void tryMap() throws InterruptedException {
        System.out.println("Starting test");
        Flux<Emp> data = getData();
        data.log("test.data.")
                .map(emp -> {
            emp.setNum(111);
            return emp;
        }).flatMap(emp -> {
            return Flux.just(emp,new Emp(12,"nama","datee"));
        }).subscribe(emp -> {
            System.out.println(String.format("emp : %s", emp));
        });
        System.out.println("End of testing");
        Thread.sleep(5000);
    }
}
