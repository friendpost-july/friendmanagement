package com.friendpost.friends.friendmanagement.repository;

import com.friendpost.friends.friendmanagement.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public interface FriendsRepository extends JpaRepository<Friends, Long> {
    Friends save(Friends friends);
    List<Friends> findByReceiverIdAndStatus(String userId, Friends.Status status);
    List<Friends> findBySenderIdAndStatus(String userId, Friends.Status status);
    @Query(value = "SELECT * FROM friends f WHERE (f.sender_id = :userId OR f.receiver_id = :userId) AND f.status = :status", nativeQuery = true)
    List<Friends> findBySenderIdAndStatusOrReceiverIdAndStatus(@Param("userId") String userId, @Param("status") String string);

    Optional<Friends> findBySenderIdAndReceiverId(String userId, String friendId);

    long deleteBySenderIdAndReceiverId(String userId, String friendId);


}
