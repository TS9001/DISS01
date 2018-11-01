package org.crawl.status.parser;

import org.crawl.SocialStreamParserApp;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserTimelineParser implements StatusParser {
    @Autowired
    private Twitter twitter;

    org.slf4j.Logger log = LoggerFactory.getLogger(SocialStreamParserApp.class);

    @Override
    public List<Status> parseStates(Optional<List<String>> querryOptions) {
        log.debug("Starting USER_TIMELINE_PARSER");
        List<Status> states = new ArrayList<>();
        try {
            for (String userId : querryOptions.orElse(new ArrayList<>())) {
                log.debug("Parsing user " + userId + " !");
                final ResponseList<Status> userTimeline = twitter.getUserTimeline(userId, new Paging(1, 50));
                log.debug("Parsed " + userTimeline.size() + " states!");
                states.addAll(userTimeline);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return states;
    }
}
