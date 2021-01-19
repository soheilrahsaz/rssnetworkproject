package com.example.main;

import com.example.main.util.HttpUtil;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class RssNetworkProjectApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testDateFormat()throws Exception
    {
        Date date= new Date("Fri, 15 Jan 2021 00:04:11");
        System.out.println(date.getTime());

        date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse("2021-01-15T11:30:48Z");
        System.out.println(date.getTime());

        date = new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-15");
        System.out.println(date.getTime());

    }

    @Test
    void readRssBody() throws Exception {
        //System.out.println(HttpUtil.get("https://www.farsnews.ir/rss"));
        System.out.println(HttpUtil.get("http://feeds.bbci.co.uk/news/world/rss.xml"));
    }

    @Test
    void jsonRssBody() throws Exception {
        System.out.println(XML.toJSONObject(HttpUtil.get("https://www.farsnews.ir/rss")));
//        System.out.println(XML.toJSONObject(HttpUtil.get("https://finance.yahoo.com/news/rssindex")));
    }

    @Test
    void getImageSize() throws Exception
    {
        System.out.println(HttpUtil.getImageSize("https://media.farsnews.ir/Uploaded/Files/Images/1399/10/27/13991027000770_Test_PhotoJ.jpeg"));
    }

}
