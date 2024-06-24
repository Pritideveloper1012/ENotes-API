package com.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.notes.entity.User;
import com.notes.repository.UserRepository;

import jakarta.servlet.http.HttpSession;



@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User saveUser(User user) {
		user.setRole("Role_User");
		
		User newUser=userRepository.save(user);
		return newUser;
	}

	@Override
	public boolean existEmailCheck(String email) {
		
		return userRepository.existsByEmail(email) ;
	}
	public void removeSessionMessage() {
	HttpSession session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		session.removeAttribute("msg");
	}

	@Override
	public User authenticate(String email, String password) {
		User users = userRepository.findByEmail(email);
	    if (users  != null && users.getPassword().equals(password)) {
	        return users; // Authentication successful
	    } else {
	        return null; // Authentication failed
	    }
	}

	
	

	

	
	

}
