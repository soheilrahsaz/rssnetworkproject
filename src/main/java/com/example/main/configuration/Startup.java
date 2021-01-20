package com.example.main.configuration;

import com.example.main.domain.Category;
import com.example.main.domain.Feed;
import com.example.main.repository.StaticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.main.repository.StaticRepository.*;


@Slf4j
@Component
public class Startup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        fillDb();//https://www.varzesh3.com/rss/index
    }

    public void fillDb()
    {

        Category health = addCategory("health", "#9c27b0");
        Category social = addCategory("Social", "#03a9f4");
        Category sports = addCategory("Sports", "#f44336");
        Category economic = addCategory("Economic", "#e91e63");
        Category technology = addCategory("Technology", "#e91e63");
        Category international = addCategory("International", "#f44336");
        Category political = addCategory("Political", "#009688");
        Category world = addCategory("World", "#2196f3");
        Category entertainments = addCategory("Entertainments", "#00bcd4");
        Category science = addCategory("Science", "#2196f3");
        Category other = addCategory("Other", "#607d8b");

//        addFeed("MicroServices", "https://microservices.io/feed.xml", technology); //bad format
        addFeed("Yahoo ", "https://news.yahoo.com/rss/", other);
        addFeed("Yahoo Sports", "https://sports.yahoo.com/sc/rss.xml", sports);
        //commented out to be added manually in front of teacher to show the functionality of the app
//        addFeed("Yahoo Finance", "https://finance.yahoo.com/news/rssindex", economic);
//        addFeed("Yahoo Entertainment", "https://www.yahoo.com/entertainment/rss", entertainments);
        addFeed("Wired Science", "https://www.wired.com/feed/category/science/latest/rss", science);
        addFeed("Wired Culture", "https://www.wired.com/feed/category/culture/latest/rss", social);


        //bad or no pictures
//        addFeed("Nasa Earth", "https://www.nasa.gov/rss/dyn/earth.rss", science);
//        addFeed("Nasa Space Nation", "https://www.nasa.gov/rss/dyn/shuttle_station.rss", science);
//         addFeed("ComputerWeekly IT services", "https://www.computerweekly.com/rss/IT-services-and-outsourcing.xml", technology);
//        addFeed("ComputerWeekly Mobile Technology", "https://www.computerweekly.com/rss/Mobile-technology.xml", technology);

        //bbc sources, it's filtered :/, use only with vpn
        /*addFeed("BBC World news", "http://feeds.bbci.co.uk/news/world/rss.xml", world);
        addFeed("BBC Politics news", "http://feeds.bbci.co.uk/news/politics/rss.xml", political);
        addFeed("BBC Tech news", "http://feeds.bbci.co.uk/news/technology/rss.xml", technology);
        addFeed("BBC Health news", "http://feeds.bbci.co.uk/news/health/rss.xml", health);
        addFeed("BBC Entertainments news", "http://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml", entertainments);*/

        //persian sources, removed due to ugliness
        /*addFeed("Latest FarsNews News", "https://www.farsnews.ir/rss",  other);
        addFeed("Most Viewed FarsNews News", "https://www.farsnews.ir/rss/mostvisitednews",  other);
        addFeed("Latest Social News", "https://www.farsnews.ir/rss/social",  social);
        addFeed("Most Viewed Social News", "https://www.farsnews.ir/rss/social/mostvisitednews",  social);
        addFeed("Latest Economic News", "https://www.farsnews.ir/rss/economy",  economic);
        addFeed("Most Viewed Social News", "https://www.farsnews.ir/rss/economy/mostvisitednews",  economic);
        addFeed("Latest international News", "https://www.farsnews.ir/rss/world",  international);
        addFeed("Most international Social News", "https://www.farsnews.ir/rss/world/mostvisitednews",  international);
        addFeed("Latest political News", "https://www.farsnews.ir/rss/politics",  political);
        addFeed("Most Viewed political News", "https://www.farsnews.ir/rss/politics/mostvisitednews",  political);*/
    }

    


}
