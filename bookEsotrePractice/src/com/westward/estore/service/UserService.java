package com.westward.estore.service;

import com.westward.estore.domain.User;

public interface UserService {
	void regist(User user) throws Exception;
	
	User login(String username,String password) throws Exception;
	
	void activeUser(String activecode) throws Exception;
}
