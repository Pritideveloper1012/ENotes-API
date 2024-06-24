package com.notes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.notes.entity.Note;
import com.notes.entity.User;

public interface NoteService {
   
	
    void save(Note note);
    
    // Method to retrieve all notes
    List<Note> getAllNotes();
    
    // Method to retrieve notes by user ID
    List<Note> getNotesByUserId(int userId);
    
    // Method to retrieve a note by its ID
    Note getNoteById(int noteId);
    
    // Method to update an existing note
    void update(Note note);
    
    // Method to delete a note by its ID
    void deleteById(int noteId);

	Page<Note> getAllNotesByUser(User user,Pageable pageable);

	void delete(int noteId); 
	
	Page<Note> searchNotesByTitle(User user, String title, Pageable pageable);
}
