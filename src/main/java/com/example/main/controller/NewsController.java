package com.example.main.controller;

import com.example.main.domain.Category;
import com.example.main.domain.Feed;
import com.example.main.domain.News;
import com.example.main.repository.StaticRepository;
import org.springframework.web.bind.annotation.*;

import static com.example.main.repository.StaticRepository.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/categories")
    public List<Category> getCategories()
    {
        /*return StaticRepository.categories.stream().map(category -> {
            Category cat = new Category();
            cat.setId(category.getId());
            cat.setName(category.getName());
            return cat;
        }).collect(Collectors.toList());*/
        return categories;
    }

    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category)
    {
        return StaticRepository.addCategory(category.getName(), category.getColor());
    }

    @PostMapping("/categories/{categoryId}/feed")
    public Feed addFeed(@RequestBody Feed feed, @PathVariable Integer categoryId)throws Exception
    {
        return StaticRepository.addFeed(feed.getName(), feed.getUrl(),categoryId);
    }

    @GetMapping("/categories/{categoryId}/feed")
    public List<Feed> getFeeds(@PathVariable Integer categoryId) throws Exception
    {
        return categories.stream()
                .filter(category -> category.getId() == categoryId)
                .map(Category::getFeeds)
                .flatMap(List::stream)
                .collect(Collectors.toList());

    }

    @GetMapping("/feed/{feedId}/news")
    public List<News> getFeedNews(@PathVariable Integer feedId)throws Exception
    {
        return newsService.getNews(getFeed(feedId));
    }

    @GetMapping("/categories/{categoryId}/news")
    public List<News> getCategoryNews(@PathVariable Integer categoryId) throws Exception
    {
        return newsService.getNews(getCategory(categoryId).getFeeds());
    }

}
