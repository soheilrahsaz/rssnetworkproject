package com.example.main.controller;

import com.example.main.domain.Feed;
import com.example.main.domain.News;
import com.example.main.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NewsService {

    private AtomicInteger newsId = new AtomicInteger(1);
    private final Hashtable<Integer, List<News>> newsCache = new Hashtable<>();
    private final int MAX_IMAGE_SERACH = 4;

    public List<News> getNews(Feed feed, String search)
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
                     news.setId(newsId.getAndAdd(1));
                     news.setDescription(newsOb.optString("description"));
                     news.setTitle(newsOb.optString("title"));
                     news.setPubDate(newsOb.optString("pubDate"));
                     try {
                         if(newsOb.has("media:content"))
                         {//it has picture url in rss feed itself
                             news.setPictureUrl(newsOb.optJSONObject("media:content").optString("url"));
                         }
                     }catch (Exception e){}

                     try {
                         news.setDateMillies(new Date(news.getPubDate()).getTime());
                     }catch (Exception e){
                         try {
                             news.setDateMillies(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(news.getPubDate()).getTime());
                         }catch (Exception e1){}
                     }
                     news.setLink(newsOb.optString("link"));
                     news.setCategory(feed.getCategory());
                     newsList.add(news);
                 }
            }catch (Exception e)
            {

            }
            newsCache.put(feed.getId(), newsList);
        }

        return newsCache.get(feed.getId());
    }

    public String getImage(String link) throws Exception
    {
        String content = HttpUtil.get(link);
        Matcher m = Pattern.compile("<img.+?src=\"(.*?)\"").matcher(content);
        String bestMatch = "";
        long bestMatchLengh = 0l;

        int in = 0;
        while (m.find() && in < MAX_IMAGE_SERACH)
        {
            String pictureUrl = m.group(1);
            in++;
            try {
                long pictureLength = HttpUtil.getImageSize(pictureUrl);
                if(pictureLength > bestMatchLengh)
                {
                    bestMatchLengh = pictureLength;
                    bestMatch = pictureUrl;
                }
            }catch (Exception e)
            {

            }
        }

        return bestMatch;
    }

    private String getCuttedText(String str, int length)
    {
        String parts[] = str.split("\\s");
        String cuttedText = "";
        for(String part : parts)
        {
            if(cuttedText.length() + part.length() > length)
            {
                return cuttedText + "...";
            }
            cuttedText += part + " ";
        }
        return cuttedText;
    }


    public List<News> getNews(List<Feed> feeds)
    {
        return getNews(feeds, null, null, null, null, null);
    }

    public List<News> getNews(List<Feed> feeds, String search, Integer limitPerGroup, Integer totalLimit, String dateFrom, String dateTo)
    {
        List<News> allnews = feeds.stream().parallel().map(feed -> getNews(feed,search)).flatMap(List::stream).collect(Collectors.toList());
        if(search != null)
        {
            allnews = allnews.stream()
                    .filter(news -> news.getDescription().toLowerCase().contains(search)
                    || news.getTitle().toLowerCase().contains(search))
                    .collect(Collectors.toList());
        }

        if(limitPerGroup != null)
        {
            allnews = allnews.stream().collect(Collectors.groupingBy(news -> news.getCategory().getId()))
                    .values().stream().flatMap(group -> group.stream().limit(limitPerGroup)).collect(Collectors.toList());
        }

        if(totalLimit != null)
        {
            allnews = allnews.stream().limit(totalLimit).collect(Collectors.toList());
        }

        if(dateFrom != null)
        {
            try {
                final long dateFromMilies = new SimpleDateFormat("yyyy-MM-dd").parse(dateFrom).getTime();
                allnews = allnews.stream().filter(news -> news.getDateMillies() >= dateFromMilies ).collect(Collectors.toList());
            }catch (Exception e){}
        }

        if(dateTo != null)
        {
            try {
                final long dateToMilies = new SimpleDateFormat("yyyy-MM-dd").parse(dateTo).getTime();
                allnews = allnews.stream().filter(news -> news.getDateMillies() <= dateToMilies ).collect(Collectors.toList());
            }catch (Exception e){}
        }

        allnews.stream().parallel().forEach(news -> {
            if(news.getPictureUrl() == null)
            {
                try {
                    news.setPictureUrl(getImage(news.getLink()));
                }catch (Exception e){}
            }
        });

        return allnews.stream().sorted((o1, o2) -> o1.getDateMillies() > o2.getDateMillies() ? -1 : 1).collect(Collectors.toList());
    }
}
