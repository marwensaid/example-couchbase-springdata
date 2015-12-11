package com.orange.couchbase.service;

import com.orange.couchbase.metrics.TwitterUpdate;
import com.orange.couchbase.metrics.TwitterUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by msaidi on 12/11/15.
 */

@Service
public class TwitterService {

    private final TwitterUpdateRepository twitterUpdateRepository;
    private final Twitter twitter;
    private String key;

    @Autowired
    TwitterService(Twitter twitter, TwitterUpdateRepository twitterUpdateRepository) {
        this.twitter = twitter;
        this.twitterUpdateRepository = twitterUpdateRepository;
    }

    public void updateAccount(String twitterAccount) {
        TwitterProfile twitterProfile = twitter.userOperations().getUserProfile(twitterAccount);

        int followersCount = twitterProfile.getFollowersCount();
        int favoritesCount = twitterProfile.getFavoritesCount();
        int friendsCount = twitterProfile.getFriendsCount();
        int statusesCount = twitterProfile.getStatusesCount();

        long timeCreate = new Date().getTime();

        String format = String.format(key, twitterAccount, timeCreate);
        TwitterUpdate twitterUpdate = new TwitterUpdate(key, timeCreate, twitterAccount, favoritesCount, followersCount, friendsCount, statusesCount);

        TwitterUpdateRepository.save(twitterUpdate);
    }
}
