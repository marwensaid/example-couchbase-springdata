package com.orange.couchbase.metrics;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.data.couchbase.core.mapping.Document;

/**
 * Created by msaidi on 12/11/15.
 */

@Document(expiry = 0)
public class TwitterUpdate {
    @Id
    @Field("id")
    private String key;
    @Field
    private long timeCreate;
    @Field
    private final java.lang.String type = "twitterUpdate";
    @Field
    private String twitterAccount;
    @Field
    private int favoritesCount;
    @Field
    private int followersCount;
    @Field
    private int friendsCount;
    @Field
    private int statusesCount;

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

    public void setKey(String key) {
        this.key = key;
    }

    public long getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(int friendsCount) {
        this.friendsCount = friendsCount;
    }

    public int getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(int statusesCount) {
        this.statusesCount = statusesCount;
    }

    @Override
    public java.lang.String toString() {
        return "TwitterUpdate{" +
                "key='" + key + '\'' +
                ", createdAt=" + timeCreate +
                ", type='" + type + '\'' +
                ", account='" + twitterAccount + '\'' +
                ", favorites=" + favoritesCount +
                ", followers=" + followersCount +
                ", friends=" + friendsCount +
                ", statuses=" + statusesCount +
                '}';
    }
}
