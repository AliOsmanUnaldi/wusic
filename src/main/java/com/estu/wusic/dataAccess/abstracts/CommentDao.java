package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Integer> {

    public List<Comment> getAllByCommentsRecieverId(int commentRecieverId);
}
