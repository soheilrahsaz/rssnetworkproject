package com.example.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "feeds")
public class Category {
    private int id;
    @NotBlank
    private String name;
    private String color;

    public Category (int id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    @JsonIgnore
    private List<Feed> feeds = new ArrayList<>();

    public Category addFeed(Feed feed)
    {
        if(feeds == null)
        {
            feeds = new ArrayList<>();
        }
        feeds.add(feed);
        return this;
    }

}
