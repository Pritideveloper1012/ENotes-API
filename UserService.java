package com.notes.service;




import com.notes.entity.User;


public interface UserService  {
	
	
	public User saveUser(User user); 
	public boolean existEmailCheck(String email); 
	public User authenticate(String email, String password);
	
	
	
	
	
	
		

}
