package com.friendpost.friends.friendmanagement.repository;

import com.friendpost.friends.friendmanagement.entity.UserTable;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserTable,Long> {
    boolean existsByUserId(String userId);
}

