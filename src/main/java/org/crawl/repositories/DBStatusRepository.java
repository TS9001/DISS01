package org.crawl.repositories;

import org.crawl.entity.DBStatus;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DBStatusRepository extends PagingAndSortingRepository<DBStatus, String> {
    Optional<DBStatus> findByStatusID(long statusID);
}
