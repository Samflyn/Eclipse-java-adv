package com.example.demo.service;

import com.example.demo.model.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(value = "mock", fallback = MockFallback.class)
//public interface MockFeign {
//    @GetMapping("mock/{id}/user")
//    public User getUser(@PathVariable String id);
//}
//
//@Component
//class MockFallback implements MockFeign {
//    @Override
//    public User getUser(String id) {
//        return new User();
//    }
//}

@FeignClient(value = "mock", fallbackFactory = MockFallbackFactory.class)
public interface MockFeign {
    @GetMapping("mock/{id}/user")
    public User getUser(@PathVariable String id);
}

@Component
@Slf4j
class MockFallbackFactory implements FallbackFactory<MockFeign> {
    @Override
    public MockFeign create(Throwable throwable) {
        log.error(throwable.getLocalizedMessage());
        return id -> new User();
    }
}