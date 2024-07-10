package com.friendpost.friends.friendmanagement.repository;

import com.friendpost.friends.friendmanagement.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    Friends save(Friends friends);
    List<Friends> findByReceiverIdAndStatus(String userId, Friends.Status status);

    List<Friends> findBySenderIdOrReceiverIdAndStatus(String userId, String id, Friends.Status status);


    Optional<Friends> findBySenderIdAndReceiverId(String userId, String friendId);

    long deleteBySenderIdAndReceiverId(String userId, String friendId);
}
