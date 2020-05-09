package com.example.demo.testing;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.stream.Stream;

@RestController
public class FileToFlux {
    @GetMapping("file")
    public Flux<String> fileToFlux() throws IOException {
        Stream<String> linesStream = Files.lines(Path.of("to file path"));
        Flux<String> stringFlux = Flux.fromStream(linesStream)
                .map(s -> s + "\r\n")
                .delayElements(Duration.ofMillis(500));
        return stringFlux;
    }
}
