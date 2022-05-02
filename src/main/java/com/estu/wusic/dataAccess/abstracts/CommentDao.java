package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao extends JpaRepository<Comment,Integer> {

    public List<Comment> getAllByCommentsRecieverId_Id(int commentRecieverId);
}
