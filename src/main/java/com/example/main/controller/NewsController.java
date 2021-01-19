package com.example.main.controller;

import com.example.main.domain.Category;
import com.example.main.domain.Feed;
import com.example.main.domain.News;
import com.example.main.repository.StaticRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
        return categories.stream().filter(category -> !category.getFeeds().isEmpty()).collect(Collectors.toList());
    }
    @PostMapping("/feeds")
    public Feed addFeed(@Valid @RequestBody Feed feed, BindingResult bindingResult)throws Exception
    {
        if (bindingResult != null && bindingResult.hasErrors()) {
            throw new Exception(getBindingReslutErrors(bindingResult));
        }
        Category cat = feed.getCategory();
        if(cat == null)
        {
            throw new Exception("category was not provided");
        }
        Category cat2 = categories.stream().filter(c -> c.getName().equalsIgnoreCase(cat.getName().trim()))
                .findAny().orElseGet(() -> addCategory(cat.getName(), cat.getColor()));
        return StaticRepository.addFeed(feed.getName(), feed.getUrl(), cat2);
    }

    private String getBindingReslutErrors(BindingResult bindingResult) {
        String errors = "";
        for (ObjectError objectError : bindingResult.getAllErrors()) {
            errors += objectError.getDefaultMessage() + ",";
        }
        return errors;
    }

    @DeleteMapping("/feeds/{feedId}")
    public Boolean deleteFeed(@PathVariable Integer feedId)
    {
        StaticRepository.deleteFeed(feedId);
        return true;
    }

    @GetMapping("/feeds")
    public List<Feed> getFeeds() throws Exception
    {
        return categories.stream()
                .map(Category::getFeeds)
                .flatMap(List::stream)
                .collect(Collectors.toList());

    }

//    @GetMapping("/feed/{feedId}/news")
//    public List<News> getFeedNews(@PathVariable Integer feedId)throws Exception
//    {
//        return newsService.getNews(getFeed(feedId));
//    }

    @GetMapping("/categories/{categoryId}/news")
    public List<News> getCategoryNews(@PathVariable Integer categoryId,
                                      @RequestParam(required = false) String query,
                                      @RequestParam(required = false) Integer limit,
                                      @RequestParam(required = false) String dateFrom,
                                      @RequestParam(required = false) String dateTo) throws Exception
    {
        return newsService.getNews(getCategory(categoryId).getFeeds(), query, null, limit, dateFrom, dateTo);
    }

    @GetMapping("/news")
    public List<News> getAllNews(@RequestParam(required = false) String query,
                                 @RequestParam(required = false) Integer limit,
                                 @RequestParam(required = false) Integer totalLimit,
                                 @RequestParam(required = false) String dateFrom,
                                 @RequestParam(required = false) String dateTo) throws Exception
    {
        return newsService
                .getNews(categories.stream()
                        .flatMap(category -> category.getFeeds().stream())
                        .collect(Collectors.toList()), query, limit, totalLimit, dateFrom, dateTo);
    }

}
