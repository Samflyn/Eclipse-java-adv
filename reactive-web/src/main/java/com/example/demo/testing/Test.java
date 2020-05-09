package com.example.demo.testing;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class Test {
    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Emp> getData() {
        return Flux.just(
                new Emp(1, "name", "data"),
                new Emp(1, "name", "data"),
                new Emp(1, "name", "data"),
                new Emp(1, "name", "data")
        ).delayElements(Duration.ofSeconds(3));
    }

    @GetMapping(value = "test", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Emp> sendDate() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
        Flux<Emp> empFlux = webClient.get().uri("/").retrieve().bodyToFlux(Emp.class);
        empFlux.subscribe(flu ->
                System.out.println(flu.toString()));
        return empFlux;
    }
}
