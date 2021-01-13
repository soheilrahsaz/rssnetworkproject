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

        Category other = addCategory("Other");
        Category social = addCategory("Social");
        Category economic = addCategory("Economic");
        Category international = addCategory("international");
        Category political = addCategory("political");

        addFeed("Latest FarsNews News", "https://www.farsnews.ir/rss",  other);
        addFeed("Most Viewed FarsNews News", "https://www.farsnews.ir/rss/mostvisitednews",  other);

        addFeed("Latest Social News", "https://www.farsnews.ir/rss/social",  social);
        addFeed("Most Viewed Social News", "https://www.farsnews.ir/rss/social/mostvisitednews",  social);

        addFeed("Latest Economic News", "https://www.farsnews.ir/rss/economy",  economic);
        addFeed("Most Viewed Social News", "https://www.farsnews.ir/rss/economy/mostvisitednews",  economic);

        addFeed("Latest international News", "https://www.farsnews.ir/rss/world",  international);
        addFeed("Most international Social News", "https://www.farsnews.ir/rss/world/mostvisitednews",  international);

        addFeed("Latest political News", "https://www.farsnews.ir/rss/politics",  political);
        addFeed("Most Viewed political News", "https://www.farsnews.ir/rss/politics/mostvisitednews",  political);
    }

    


}
