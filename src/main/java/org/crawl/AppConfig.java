package org.crawl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
@EnableScheduling
@PropertySource("classpath:twitter.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public Twitter twitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true).setOAuthConsumerKey(env.getProperty("twitter.oAuthConsumerKey"))
                .setOAuthConsumerSecret(env.getProperty("twitter.oAuthConsumerSecret"))
                .setOAuthAccessToken(env.getProperty("twitter.oAuthAccessToken"))
                .setOAuthAccessTokenSecret(env.getProperty("twitter.oAuthAccessTokenSecret"))
                .setTweetModeExtended(true);

        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}
