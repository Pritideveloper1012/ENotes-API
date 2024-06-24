package com.notes.controller;



import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.service.NoteService;
import com.notes.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
public class NoteController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private NoteService noteService;
	
	@GetMapping("/addNotes")
    public String addNotesForm(Model model) {
		model.addAttribute("note", new Note());
        return "add_notes"; 
        
    }
	
	@PostMapping("/saveNote")
    public String saveNote(@ModelAttribute("note") Note note,HttpSession session,Model model) {
		User authenticatedUser = (User) session.getAttribute("authenticatedUser");
		if (authenticatedUser == null) {
            // Redirect to login page if user is not authenticated
            return "redirect:/viewNotes";
        }
		if (note.getTitle().isEmpty() || note.getDescription().isEmpty()) {
	        model.addAttribute("errorMessage", "Please fill out the title and description.");
	        // Return the add_notes page with the error message
	        return "add_notes";
	    }
		   note.setUser(authenticatedUser);
		    note.setCreatedAt(new Date()); // Set creation date
		    noteService.save(note);
		   
	        
        model.addAttribute("title", "View All Notes");
        model.addAttribute("successMessage", "Note added successfully.");
        
        // Redirect to the view notes page
        return "redirect:/viewNotes?page=0&size=10";
    }
	
	
	@PostMapping("/saveNoteParam")
	public String saveNoteWithRequestParam(@RequestParam String title, @RequestParam String description, HttpSession session) {
		
		User authenticatedUser = (User) session.getAttribute("user");
		if (authenticatedUser == null) {
	        // Handle the case where user is not authenticated
	        // You might redirect to a login page or show an error message
	        return "redirect:/login";
	    }
		//System.out.println("Note Date: " + note.getDate());
		Note note=new Note();
		note.setTitle(title);
		note.setDescription(description);
       	note.setUser(authenticatedUser);
        note.setCreatedAt(new Date());
       
		noteService.save(note);
	
//	    System.out.println("Title: " + title);
//	    System.out.println("Description: " + description);
//	    
	    // Redirect back to the add_notes page after saving
	    return "redirect:/addNotes";
	}
	


	@GetMapping("/viewNotes")
    public String viewNotes(Model model, HttpSession session,
                            @RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "4") Integer size,
                            @RequestParam(value = "search", required = false) String search) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            return "redirect:/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Note> notesPage;

        if (search != null && !search.isEmpty()) {
            notesPage = noteService.searchNotesByTitle(authenticatedUser, search, pageable);
        } else {
            notesPage = noteService.getAllNotesByUser(authenticatedUser, pageable);
        }

        model.addAttribute("notesPage", notesPage);
        model.addAttribute("currentPage", page);
        return "view_notes";
    }

	
    
    @GetMapping("/deleteNote")
    public String deleteNote(@RequestParam("id") int noteId, HttpSession session, Model model) {
        User authenticatedUser = (User) session.getAttribute("user");
        if (authenticatedUser == null) {
            return "redirect:/login";
        }
        noteService.delete(noteId);
        
//        List<Note> notes = noteService.getAllNotesByUser(authenticatedUser);
//        model.addAttribute("notes", notes);
//        model.addAttribute("title", "View All Notes");
        
        return "redirect:/viewNotes";
    }
    
    

    @GetMapping("/editNotes")
    public String editNotesForm(@RequestParam("id") int noteId, Model model) {
    	Note note = noteService.getNoteById(noteId);
        model.addAttribute("note", note);
        return "edit_notes";
    }

    @PostMapping("/updateNote")
    public String updateNote(@ModelAttribute("note") Note updatedNote,HttpSession session) {
    	User authenticatedUser = (User) session.getAttribute("user");
        
        // Check if the user is authenticated
        if (authenticatedUser == null) {
            // Handle the case where user is not authenticated
            return "redirect:/login";
        }
        
        // Fetch the existing note from the database
        Note existingNote = noteService.getNoteById(updatedNote.getId());
        
        // Ensure that the note exists
        if (existingNote == null) {
            // Handle the case where the note does not exist
            // You might redirect to an error page or show an error message
            return "redirect:/viewNotes";
        }
        
        // Set the authenticated user as the owner of the note
        existingNote.setUser(authenticatedUser);
        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setDescription(updatedNote.getDescription());
        noteService.update(existingNote);
        
        
        // Update the note with the changes
        //noteService.update(existingNote);
        
        return "redirect:/viewNotes";
    }
    
    @PostMapping("/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("profilePicture") MultipartFile file, HttpSession session) throws IOException {
        // Validate the file
        if (!file.isEmpty()) {
            // Save the file to the database or file system
			// Example using file system: Files.write(Paths.get("/path/to/save/profile_picture.jpg"), file.getBytes());
			// Example using database: userService.saveProfilePicture(file.getBytes(), authenticatedUser.getId());
			return "redirect:/profile";
        }
        // Handle empty file
        return "redirect:/profile?error=emptyfile";
    }
    @GetMapping("/Get")
    public String get() {
    	return "Get";
		
	}


}
