package com.friendpost.friends.friendmanagement.repository;

import com.friendpost.friends.friendmanagement.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    List<Friends> findByTargetAndStatus(String userId, Friends.Status status);

    List<Friends> findByRequesterOrTargetAndStatus(String userId, String id, Friends.Status status);


    Optional<Friends> findByRequesterAndTarget(String userId, String friendId);

    long deleteByRequesterAndTarget(String userId, String friendId);
}
