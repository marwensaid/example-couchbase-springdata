package com.orange.couchbase.metrics;

import org.springframework.data.repository.CrudRepository;
import com.couchbase.client.protocol.views.Query;
import java.util.Collection;

/**
 * Created by msaidi on 12/11/15.
 */
public interface TwitterUpdateRepository extends CrudRepository<TwitterUpdate, String> {

    Collection<TwitterUpdate> findByDate(Query query);

    Collection<TwitterUpdate> findFollowersByDate(Query query);

}
