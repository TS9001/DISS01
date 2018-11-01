package org.crawl.services;

import org.springframework.stereotype.Service;
import twitter4j.Status;

@Service
public interface AxivCrawlerService {
    void crawl(Status status, String expandedURL, String targetPath);
}


