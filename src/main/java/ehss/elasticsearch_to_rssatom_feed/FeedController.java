package ehss.elasticsearch_to_rssatom_feed;


import com.rometools.rome.feed.atom.*;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndPerson;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
public class FeedController {

    @Value("${elasticsearch.host}")
    private String elasticsearchHost;


    @Value("${elasticsearch.port}")
    private Integer elasticsearchPort;
    @Autowired
    ElasticSearchWrapperService elasticSearchWrapperService;

    @GetMapping(path="/{index}/rss")
    public Channel rss(@PathVariable String index){
        Channel channel = new Channel();
        String url = "http://"+elasticsearchHost+":"+elasticsearchPort;
        channel.setFeedType("rss_2.0");
        channel.setTitle(index+" RSS Feed");
        channel.setDescription(index+" RSS Feed");
        channel.setLink(url);
        channel.setUri(url);
        channel.setGenerator("FeedController");

        Date postDate = new Date();
        channel.setPubDate(postDate);

        try{
            SearchResponse searchResponse = elasticSearchWrapperService.searchAll(index);
            List<Item> itemList = new ArrayList<>();
            for(SearchHit searchHit:searchResponse.getHits()){
                Item item = new Item();

                //item.setAuthor(searchHit.getField("author").toString());
                item.setLink(url+"/"+searchHit.getIndex()+"/"+searchHit.getType()+"/"+searchHit.getId());
                item.setTitle(searchHit.getId());
                item.setUri(url+"/"+searchHit.getIndex()+"/"+searchHit.getType()+"/"+searchHit.getId());
                //item.setComments(searchHit.getField("comments").toString());
                com.rometools.rome.feed.rss.Category category = new com.rometools.rome.feed.rss.Category();
                category.setValue(searchHit.getType());
                item.setCategories(Collections.singletonList(category));

                Description descr = new Description();
                descr.setValue(searchHit.getSourceAsString());
                item.setDescription(descr);
                item.setPubDate(postDate);
                itemList.add(item);


            }
            channel.setItems(itemList);

        }catch (Exception ex){
                ex.printStackTrace();
        }


        //Like more Entries here about different new topics
        return channel;



    }


    @GetMapping(path="/{index}/atom")
    public Feed atom(@PathVariable String index){
        Feed feed = new Feed();
        feed.setFeedType("atom_1.0");
        feed.setTitle(index+" Atom Feed");
        feed.setId(index);
        String url = "http://"+elasticsearchHost+":"+elasticsearchPort;



        Date postDate = new Date();
        feed.setUpdated(postDate);
        try{
            SearchResponse searchResponse = elasticSearchWrapperService.searchAll(index);
            List<Entry> entryList = new ArrayList<>();
            for(SearchHit searchHit:searchResponse.getHits()){
                Entry entry = new Entry();
                Link link = new Link();
                link.setHref(url+"/"+searchHit.getIndex()+"/"+searchHit.getType()+"/"+searchHit.getId());
                entry.setAlternateLinks(Collections.singletonList(link));
                entry.setCreated(postDate);
                entry.setPublished(postDate);
                entry.setUpdated(postDate);

                entry.setId(url+"/"+searchHit.getIndex()+"/"+searchHit.getType()+"/"+searchHit.getId());
                entry.setTitle(searchHit.getId());

                Category category = new Category();
                category.setTerm(searchHit.getType());
                entry.setCategories(Collections.singletonList(category));




                Content summary = new Content();
                summary.setType("application/json");
                summary.setValue(searchHit.getSourceAsString());
                entry.setSummary(summary);


                entryList.add(entry);


            }
            feed.setEntries(entryList);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        //Like more Entries here about different new topics
        return feed;



    }

}
