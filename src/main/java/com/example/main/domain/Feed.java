package com.example.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class Feed {

    private int id;
    private String name;
    private String url;

    @JsonIgnore
    private Category category;

}
