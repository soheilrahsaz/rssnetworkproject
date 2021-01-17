package com.example.main.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

    private int id;
    private String title;
    private String link;
    private String description;
    private String pubDate;
    private Long dateMillies;

    private Category category;

    private String pictureUrl;

}
