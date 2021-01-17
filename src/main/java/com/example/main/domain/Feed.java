package com.example.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "category")
public class Feed {

    private int id;
    @NotBlank
    private String name;
    @Pattern(regexp = "https?:\\/\\/.+")
    private String url;

    private Category category;

}
