package com.example.main.repository;

import com.example.main.domain.Category;
import com.example.main.domain.Feed;

import java.util.ArrayList;
import java.util.List;

public class StaticRepository {
    public static final List<Category> categories = new ArrayList<>();
    private static int categoryId = 1;
    private static int feedId = 1;

    public static Category getCategory(int categoryId)throws Exception
        {
        return categories.stream().filter(category -> category.getId() == categoryId)
                .findAny().orElseThrow(() -> new Exception("Category not found"));
    }

    public static Feed getFeed(int feedId) throws Exception
    {
        return categories.stream().map(Category::getFeeds).flatMap(List::stream).filter(feed -> feed.getId() == feedId)
                .findAny().orElseThrow(() -> new Exception("Feed not found"));
    }

    public static Feed addFeed(String name, String url, int categoryId) throws Exception{
        return addFeed(name, url, getCategory(categoryId));
    }

    public static Feed addFeed(String name, String url, Category category) {
        Feed feed = new Feed();
        feed.setName(name);
        feed.setUrl(url);
        feed.setId(feedId++);
        feed.setCategory(category);
        category.addFeed(feed);
        return feed;
    }

    public static Category addCategory(String name) {
        return addCategory(name, null);
    }

    public static void deleteFeed(int feedId)
    {
        categories.stream().flatMap(category -> category.getFeeds().stream())
                .filter(feed -> feed.getId() == feedId)
                .findAny().ifPresent(feed -> feed.getCategory().getFeeds().remove(feed));
    }

    public static Category addCategory(String name, String color) {
        Category category = new Category(categoryId++, name, color);
        StaticRepository.categories.add(category);
        return category;
    }

}
