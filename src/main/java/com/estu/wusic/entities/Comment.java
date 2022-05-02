package com.estu.wusic.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User commentsOwner;

    @ManyToOne
    @JoinColumn(name = "reciever_id", referencedColumnName = "id")
    private User commentsReciever;

    @Column(name = "comment_text")
    private String commentText;

}
