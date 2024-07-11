package com.friendpost.friends.friendmanagement.service;

import com.friendpost.friends.friendmanagement.controller.exception.FriendRequestNotFoundException;
import com.friendpost.friends.friendmanagement.controller.exception.InvalidStatusException;
import com.friendpost.friends.friendmanagement.entity.Friends;
import com.friendpost.friends.friendmanagement.entity.Friends.Userstatus;
import com.friendpost.friends.friendmanagement.repository.FriendsRepository;
import com.friendpost.friends.friendmanagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class FriendsServiceImpl implements FriendsService{

    @Autowired
    FriendsRepository friendsRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Friends sendFriendRequest(Friends friends) {
        if (friends.getSenderId() == null || friends.getReceiverId() == null) {
            throw new IllegalArgumentException("Sender ID and Receiver ID must not be null");
        }
        if(userRepository.existsByUserId(friends.getReceiverId())) {
            friends.setUserstatus(Userstatus.VERIFIED);
        }
        else {
            friends.setUserstatus(Userstatus.ONHOLD);
        }
        friends.setStatus(Friends.Status.PENDING);
        return friendsRepository.save(friends);
    }

    @Override
    public List<Friends> getReceivedFriendRequests(String userId, Friends.Status status) {
        return friendsRepository.findByReceiverIdAndStatus(userId,status);
    }

    @Transactional
    public Friends updateFriendRequestStatus(String requestId, Friends.Status status) {
        Optional<Friends> friendRequestOptional = friendsRepository.findById(Long.valueOf(requestId));
        if (!friendRequestOptional.isPresent()) {
            throw new FriendRequestNotFoundException("Friend request not found");
        }

        Friends friends = friendRequestOptional.get();

        if (status==Friends.Status.PENDING) {
            throw new InvalidStatusException("Invalid status value");
        }
        friends.setStatus(status);
        return friendsRepository.save(friends);
    }

    @Override
    public List<Friends> getFriends(String userId) {
        return friendsRepository.findBySenderIdAndStatusOrReceiverIdAndStatus(userId,Friends.Status.ACCEPTED.name());
    }
    @Transactional
    public void unfriend(String userId, String friendId) {
        if (friendsRepository.findBySenderIdAndReceiverId(userId, friendId).isPresent()) {

            friendsRepository.deleteBySenderIdAndReceiverId(userId, friendId);
        }
        else if (friendsRepository.findBySenderIdAndReceiverId(friendId,userId).isPresent()) {
            friendsRepository.deleteBySenderIdAndReceiverId(friendId, userId);
        }else {
            throw new FriendRequestNotFoundException("Friend not found");
        }
    }
}
