package com.example.main.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private int id;
    private String name;
    private String color;

    @JsonIgnore
    private List<Feed> feeds;

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
