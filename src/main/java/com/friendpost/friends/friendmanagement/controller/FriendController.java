package com.friendpost.friends.friendmanagement.controller;

import com.friendpost.friends.friendmanagement.controller.exception.FriendRequestNotFoundException;
import com.friendpost.friends.friendmanagement.controller.exception.InvalidStatusException;
import com.friendpost.friends.friendmanagement.entity.Friends;
import com.friendpost.friends.friendmanagement.service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/friends")
@Validated
public class FriendController {

    @Autowired
    private FriendsService friendRequestService;

    @GetMapping("/requests")
    public ResponseEntity<List<Friends>> getReceivedFriendRequests(@RequestParam String userId) {
        List<Friends> friends = friendRequestService.getReceivedFriendRequests(userId, Friends.Status.PENDING);
        return ResponseEntity.ok(friends);
    }
    @PostMapping
    public ResponseEntity<String> sendFriendRequest( @RequestBody @Validated Friends friendsDto) {
        try {
            friendRequestService.sendFriendRequest(friendsDto);
            return new ResponseEntity<>("Friend request sent successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<String> updateFriendRequestStatus(
            @PathVariable String requestId,
            @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        if (status == null || (!"accepted".equals(status) && !"rejected".equals(status))) {
            return new ResponseEntity<>("Invalid status value", HttpStatus.BAD_REQUEST);
        }
        try {
            friendRequestService.updateFriendRequestStatus(requestId, status.equals("accepted")? Friends.Status.ACCEPTED : Friends.Status.REJECTED);
            return new ResponseEntity<>("Friend request updated successfully", HttpStatus.OK);
        } catch (FriendRequestNotFoundException e) {
            return new ResponseEntity<>("Friend request not found", HttpStatus.NOT_FOUND);
        } catch (InvalidStatusException e) {
            return new ResponseEntity<>("Invalid status value", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Friends>> getFriends(@RequestParam String userId) {
        List<Friends> friends = friendRequestService.getFriends(userId);
        return ResponseEntity.ok(friends);
    }

    @DeleteMapping("/{userId}/unfriend/{friendId}")
    public ResponseEntity<String> unfriend(
            @PathVariable String userId,
            @PathVariable String friendId) {
        try {
            friendRequestService.unfriend(userId, friendId);
            return new ResponseEntity<>("Unfriended successfully", HttpStatus.OK);
        } catch (FriendRequestNotFoundException e) {
            return new ResponseEntity<>("Friend not found", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/hello/{Username}")
    public String hello(@PathVariable String Username) {
        return "Hello, " + Username;
    }

    // Add a new endpoint which displays date and time to the user
    @GetMapping("/date")
    public String date() {
        return java.time.LocalDateTime.now().toString();
    }

}
