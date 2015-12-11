package com.orange.couchbase;

import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.Stale;
import com.couchbase.client.protocol.views.ViewResponse;
import com.orange.couchbase.metrics.TwitterUpdate;
import com.orange.couchbase.metrics.TwitterUpdateRepository;
import com.orange.couchbase.service.TwitterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CouchbaseSprigDataApplication extends AbstractCouchbaseConfiguration {

    private final static Log LOG = LogFactory.getLog(CouchbaseSprigDataApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CouchbaseSprigDataApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(TwitterUpdateRepository twitterUpdateRepository, TwitterService twitterService) {
        return args -> {
            twitterUpdateRepository.deleteAll();

            TwitterUpdate twitterUpdate = new TwitterUpdate("key", 1, "msaidi", 1, 1, 1, 1);
            twitterUpdateRepository.save(twitterUpdate);
            TwitterUpdate twitterUpdateCouchbase = twitterUpdateRepository.findOne(twitterUpdate.getKey());

            TwitterUpdate twitterUpdate1 = new TwitterUpdate("key1", 1, "ppensa", 1, 1, 1, 1);
            TwitterUpdate twitterUpdate2 = new TwitterUpdate("key2", 1, "croux", 1, 1, 1, 1);
            List<TwitterUpdate> twitterUpdateList = Arrays.asList(twitterUpdate1, twitterUpdate2);

            twitterUpdateRepository.save(twitterUpdateList);

            for (int i = 0; i < 1000; i++) {
//                twitterService.updateAccount("msaidi");
                twitterUpdateRepository.save(new TwitterUpdate("key" + i, i, "msaid", i, i, i, i));
            }
            Query query = new Query();
            query.setIncludeDocs(true);
            query.setStale(Stale.FALSE);
            query.setRange("998", "999");
            query.setReduce(true);

            ViewResponse viewResponse = couchbaseTemplate().queryView("twitterUpdate", "followersByDate", query);

        };
    }

    @Override
    protected List<String> getBootstrapHosts() {
        return null;
    }

    @Override
    protected String getBucketName() {
        return null;
    }

    @Override
    protected String getBucketPassword() {
        return null;
    }
}
