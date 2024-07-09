package com.friendpost.friends.friendmanagement.repository;

import com.friendpost.friends.friendmanagement.entity.Friendship;
import org.springframework.data.repository.CrudRepository;

public interface FriendshipRepository extends CrudRepository<Friendship,Long> {
}
