package com.friendpost.friends.friendmanagement.service;

import com.friendpost.friends.friendmanagement.controller.exception.FriendRequestNotFoundException;
import com.friendpost.friends.friendmanagement.controller.exception.InvalidStatusException;
import com.friendpost.friends.friendmanagement.entity.Friends;
import com.friendpost.friends.friendmanagement.repository.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class FriendsServiceImpl implements FriendsService{

    @Autowired
    FriendsRepository friendsRepository;

    @Override
    public void sendFriendRequest(Friends friends) {
        if (friends.getRequester() == null || friends.getTarget() == null) {
            throw new IllegalArgumentException("Sender ID and Receiver ID must not be null");
        }
        friends.setStatus(Friends.Status.PENDING);
        friendsRepository.save(friends);
    }

    @Override
    public List<Friends> getReceivedFriendRequests(String userId, Friends.Status status) {
        return friendsRepository.findByTargetAndStatus(userId,status);
    }

    @Transactional
    public void updateFriendRequestStatus(String requestId, Friends.Status status) {
        Optional<Friends> friendRequestOptional = friendsRepository.findById(Long.valueOf(requestId));
        if (!friendRequestOptional.isPresent()) {
            throw new FriendRequestNotFoundException("Friend request not found");
        }

        Friends friends = friendRequestOptional.get();

        if (status==Friends.Status.PENDING) {
            throw new InvalidStatusException("Invalid status value");
        }
        friends.setStatus(status);
        friendsRepository.save(friends);
    }

    @Override
    public List<Friends> getFriends(String userId) {
        return friendsRepository.findByRequesterOrTargetAndStatus(userId,userId,Friends.Status.ACCEPTED);
    }
    @Transactional
    public void unfriend(String userId, String friendId) {
        if (friendsRepository.findByRequesterAndTarget(userId, friendId).isPresent()) {

            friendsRepository.deleteByRequesterAndTarget(userId, friendId);
        }
        else if (friendsRepository.findByRequesterAndTarget(friendId,userId).isPresent()) {
            friendsRepository.deleteByRequesterAndTarget(friendId, userId);
        }else {
            throw new FriendRequestNotFoundException("Friend not found");
        }
    }
}
