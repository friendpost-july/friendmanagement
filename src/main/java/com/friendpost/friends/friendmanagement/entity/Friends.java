package com.friendpost.friends.friendmanagement.entity;

import jakarta.persistence.*;


@Entity
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private String senderId;

    private String sendername;

    private String receiverId;
    private String receivername;
    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getReceivername() {
        return receivername;
    }

    public void setReceivername(String receivername) {
        this.receivername = receivername;
    }

    public Userstatus getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(Userstatus userstatus) {
        this.userstatus = userstatus;
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Userstatus userstatus;

    public enum Userstatus {
        VERIFIED,
        ONHOLD
    }
    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    // Constructors, getters, and setters
    public Friends() {}

    public Friends(String senderId, String receiverId, Status status) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
