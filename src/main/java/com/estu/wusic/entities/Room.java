package com.estu.wusic.entities;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Room {

    private long roomId;
    private long ownerId;
    private List<User> participants;
    private String roomName;
    private String genre;
    private Date creationDate;
    private Time creationTime;
    private String location;
    private String password;
}
