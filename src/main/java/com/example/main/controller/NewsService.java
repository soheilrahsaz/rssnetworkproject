package com.example.main.controller;

import com.example.main.domain.Feed;
import com.example.main.domain.News;
import com.example.main.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NewsService {

    private int newsId = 1;
    private final Hashtable<Integer, List<News>> newsCache = new Hashtable<>();

    public List<News> getNews(Feed feed)
    {
        if(!newsCache.containsKey(feed.getId()))
        {
            List<News> newsList = new ArrayList<>();
            try {
                 JSONObject newsObs = XML.toJSONObject(HttpUtil.get(feed.getUrl()));
                 JSONArray newsArr = newsObs.optJSONObject("rss").optJSONObject("channel").optJSONArray("item");
                 for (int i = 0 ; i < newsArr.length() ; i++)
                 {
                     JSONObject newsOb = newsArr.optJSONObject(i);
                     News news = new News();
                     news.setId(newsId++);
                     news.setDescription(newsOb.optString("description"));
                     news.setTitle(newsOb.optString("title"));
                     news.setPubDate(newsOb.optString("pubDate"));
                     news.setLink(newsOb.optString("link"));

                     newsList.add(news);
                 }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            newsCache.put(feed.getId(), newsList);
        }

        return newsCache.get(feed.getId());
    }

    public List<News> getNews(List<Feed> feeds)
    {
        return feeds.stream().map(this::getNews).flatMap(List::stream).collect(Collectors.toList());
    }
}
