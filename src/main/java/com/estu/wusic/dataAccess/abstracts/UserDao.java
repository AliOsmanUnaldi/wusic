package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {
    public User getUserByUserName(String userName);
}
