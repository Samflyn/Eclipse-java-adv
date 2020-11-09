package com.example.demo.wrapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@Setter
public class PageableResponse<T> extends PageImpl {
    private boolean first;
    private boolean last;
    private int totalPages;
    private int numberOfElements;

    // since there is no default constructor for PageImpl
    // jackson wont be able to map the json to pojo
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageableResponse(List content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
