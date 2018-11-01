package org.crawl.status.parser;

import twitter4j.Status;

import java.util.List;
import java.util.Optional;

public interface StatusParser {
    List<Status> parseStates(Optional<List<String>> querryOptions);
}
