package com.estu.wusic.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "roomJoined")
    private List<User> participants;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "genre")
    private String genre;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "location")
    private String location;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String description;
}
