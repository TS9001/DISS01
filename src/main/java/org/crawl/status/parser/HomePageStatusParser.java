package org.crawl.status.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class HomePageStatusParser implements StatusParser {
    @Autowired
    private Twitter twitter;

    @Override
    public List<Status> parseStates(Optional<List<String>> querryOptions) {
        try {
            return twitter.getHomeTimeline(new Paging(1, 300));
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
