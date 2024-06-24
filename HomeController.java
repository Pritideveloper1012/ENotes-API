package com.notes.controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.notes.entity.User;
import com.notes.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user,HttpSession session) {
		boolean f= userService.existEmailCheck(user.getEmail());
		if (f) {
			session.setAttribute("msg", "Email already exist");
			
		}else {
			User saveUser=userService.saveUser(user);
			if (saveUser!=null) {
				session.setAttribute("msg", "Registter Succesfully");
				
			}else {
				session.setAttribute("msg", "Something Wrong on sever");
			} 
		}
		
		
		return "redirect:/register";
	}
	
	
	@GetMapping("/login")
	public String showLoginForm(Model model, HttpSession session) {
		 if (session.getAttribute("user") != null) {
		        // User is already logged in, redirect to the home page
		        return "redirect:/";
		    } else {
		        // User is not logged in, render the login form
		        return "login"; // Assuming "login" is the name of your login page
		    }
	    }
	
	
	@PostMapping("/savelogin")
	public String login(@ModelAttribute("email") String email, @ModelAttribute("password") String password, HttpSession session) {
	    User user = userService.authenticate(email, password);
	    if (user != null) {
	        // User authenticated, store user details in session
	    	 session.setAttribute("name", user.getName()); 
	        session.setAttribute("user", user);
	        session.setAttribute("loginMessage", "Login successful!");
	        //session.removeAttribute("loginMessage");
	        
	        return "redirect:/"; // Redirect to dashboard or any other page
	    } else {
	        // Authentication failed, handle accordingly (e.g., display error message)
	        session.setAttribute("loginError", "Invalid email or password");
	        //session.removeAttribute("loginMessage");
	        return "redirect:/login"; // Redirect back to login page
	    }
	}

	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	  HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/login"; 
		 	}
	
	
	@GetMapping("/userDetails")
    public String getUserDetails(Model model, HttpSession session) {
		 User user = (User) session.getAttribute("user");
		    if (user != null) {
		        model.addAttribute("user", user);
		        return "userDetails"; // Return the UserDetails page
		    } else {
		        // Handle the case where the user is not logged in
		        return "redirect:/login"; // Redirect to the login page
		    }
    }
	
	


	
	
	
	
	
	
	
}
