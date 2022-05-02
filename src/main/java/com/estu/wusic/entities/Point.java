package com.estu.wusic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "points")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "points_owner_id",referencedColumnName = "id")
    private User pointsOwner;

    @ManyToOne
    @JoinColumn(name = "points_reciever_id", referencedColumnName = "id")
    private User pointsReciever;

    @Column(name = "given_point")
    private double givenPoint;
}
