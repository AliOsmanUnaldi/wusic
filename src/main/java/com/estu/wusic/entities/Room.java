package com.estu.wusic.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "city", referencedColumnName = "id")
    private City city;

    @Column(name = "password")
    private String password;

    @Column(name = "description")
    private String description;

    @Column(name = "average_point")
    private double averagePoint;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;
}
