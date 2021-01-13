package com.example.main;

import com.example.main.util.HttpUtil;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RssNetworkProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void readRssBody() throws Exception {
        System.out.println(HttpUtil.get("https://www.farsnews.ir/rss"));
    }

    @Test
    void jsonRssBody() throws Exception {
        System.out.println(XML.toJSONObject(HttpUtil.get("https://www.farsnews.ir/rss")));
    }



}
