package com.orange.couchbase;

import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.Stale;
import com.couchbase.client.protocol.views.ViewResponse;
import com.orange.couchbase.metrics.TwitterUpdate;
import com.orange.couchbase.metrics.TwitterUpdateRepository;
import com.orange.couchbase.service.TwitterService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@EnableCouchbaseRepositories
public class CouchbaseSprigDataApplication extends AbstractCouchbaseConfiguration {

    private final static Log LOG = LogFactory.getLog(CouchbaseSprigDataApplication.class);

    @Value("${couchbase.cluster.bucket:default}")
    private String bucketName;
    @Value("${couchbase.cluster.password:")
    private String password;
    @Value("${couchbase.cluster.ip:127.0.0.1")
    private String ip;
    @Value("${couchbase.social.twitter.twitterConsumerKey")
    private String twitterConsumerKey;
    @Value("${couchbase.social.twitter.twitterConsumerSecret}")
    private String twitterConsumerSecret;
    @Value("${couchbase.social.twitter.twitterAccessToken}")
    private String twitterAccessToken;
    @Value("${couchbase.social.twitter.twitterAccessTokenSecret}")
    private String twitterAccessTokenSecret;

    @Override
    protected List<String> getBootstrapHosts() {
        return Arrays.asList(ip);
    }

    @Override
    protected String getBucketName() {
        return bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return password;
    }

    @Bean
    Twitter twitter() {
        return new TwitterTemplate(twitterConsumerKey, twitterConsumerSecret, twitterAccessToken, twitterAccessTokenSecret);
    }

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

            TwitterUpdate twitterUpdate1 = new TwitterUpdate("key1", 2, "ppensa", 1, 1, 1, 1);
            TwitterUpdate twitterUpdate2 = new TwitterUpdate("key2", 3, "croux", 1, 1, 1, 1);
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
            Collection<TwitterUpdate> twitterUpdatesC = twitterUpdateRepository.findByDate(query);
            twitterUpdatesC.forEach((twitterUpdate) -> LOG.info(twitterUpdate.getFollowersCount()));
            query.setReduce(true);

            ViewResponse viewResponse = couchbaseTemplate().queryView("twitterUpdate", "followersByDate", query);
            String params = viewResponse.iterator().next().getValue();

        };
    }


}
