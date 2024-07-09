package com.friendpost.friends.friendmanagement.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

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
