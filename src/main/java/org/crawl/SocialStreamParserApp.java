package org.crawl;

import org.crawl.services.AxivCrawlerService;
import org.crawl.status.parser.StatusParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import twitter4j.Status;
import twitter4j.URLEntity;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class SocialStreamParserApp {

    @Autowired
    private AxivCrawlerService axivCrawlerService;

    @Autowired
    private List<StatusParser> parsers;

    @Autowired
    private Environment env;

    @Value("#{'${twitter.targetAccounts}'.split(',')}")
    List<String> useNames;

    Logger log = LoggerFactory.getLogger(SocialStreamParserApp.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SocialStreamParserApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            boolean executeAfterStart = Boolean.valueOf(env.getProperty("twitter.executeAfterStart"));
            if (executeAfterStart == true) {
                execute();
            }
        };
    }

    @Scheduled(cron = "0 0 4,12,18,0 * * *")
    private void execute() {
        parsers.forEach(statusParser -> {
            List<Status> statuses = statusParser.parseStates(Optional.of(useNames));
            evaluateStatuses(statuses);
        });
    }

    private void evaluateStatuses(List<Status> statuses) {
        if (!statuses.isEmpty()) {
            log.debug("Showing home timeline.");

            Status lastSatus = statuses.get(statuses.size() - 1);
            Status firstStatus = statuses.get(0);

            log.debug("First status" + firstStatus.getId() + " was set at: " + firstStatus.getCreatedAt());
            log.debug("Last status with id " + lastSatus.getId() + " was set at: " + lastSatus.getCreatedAt());
            log.debug("Papers will be stored to path: " + env.getProperty("twitter.targetPath"));

            for (Status status : statuses) {
                for (URLEntity entity : status.getURLEntities()) {
                    axivCrawlerService.crawl(status, entity.getExpandedURL(), env.getProperty("twitter.targetPath"));
                }
            }
        }
    }
}


