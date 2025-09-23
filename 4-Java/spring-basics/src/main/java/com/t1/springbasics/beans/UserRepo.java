package com.t1.springbasics.beans;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    public boolean exists(String id) { return true; }
}
