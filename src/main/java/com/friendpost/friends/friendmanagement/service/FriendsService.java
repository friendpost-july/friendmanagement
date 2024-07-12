package com.friendpost.friends.friendmanagement.service;

import com.friendpost.friends.friendmanagement.entity.Friends;

import java.util.List;

public interface FriendsService {
    Friends sendFriendRequest(Friends friends);

    List<Friends> getReceivedFriendRequests(String userId, Friends.Status status);
    List<Friends> getSentFriendRequests(String userId, Friends.Status status);

    Friends updateFriendRequestStatus(String requestId, Friends.Status status);

    List<Friends> getFriends(String userId);

    void unfriend(String userId, String friendId);
}
