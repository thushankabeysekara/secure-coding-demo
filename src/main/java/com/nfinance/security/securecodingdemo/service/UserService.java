package com.nfinance.security.securecodingdemo.service;

import com.nfinance.security.securecodingdemo.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUserByUsernameAndPassword(String username, String password);

    Optional<User> getUserInfo(String userId);

}
