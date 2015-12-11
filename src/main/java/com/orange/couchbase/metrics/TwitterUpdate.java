package com.orange.couchbase.metrics;

/**
 * Created by msaidi on 12/11/15.
 */
public class TwitterUpdate {

    private final String key;
    private final long timeCreate;
    private final String twitterAccount;
    private final int favoritesCount;
    private final int followersCount;
    private final int friendsCount;
    private final int statusesCount;

    public TwitterUpdate(String key, long timeCreate, String twitterAccount, int favoritesCount, int followersCount, int friendsCount, int statusesCount) {

        this.key = key;
        this.timeCreate = timeCreate;
        this.twitterAccount = twitterAccount;
        this.favoritesCount = favoritesCount;
        this.followersCount = followersCount;
        this.friendsCount = friendsCount;
        this.statusesCount = statusesCount;
    }

    public String getKey() {
        return key;
    }

    public long getTimeCreate() {
        return timeCreate;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }
}
