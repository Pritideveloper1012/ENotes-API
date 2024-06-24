package com.notes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.notes.entity.Note;
import com.notes.entity.User;
import com.notes.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

	 @Autowired
	    private NoteRepository noteRepository;
	
	@Override
	public void save(Note note) {
		
		 noteRepository.save(note);
	}

	@Override
	public List<Note> getAllNotes() {
	
		return noteRepository.findAll();
	}

	@Override
	public List<Note> getNotesByUserId(int userId) {
		
		return null;
	}

	@Override
	public Note getNoteById(int noteId) {
		
		return noteRepository.findById(noteId).orElse(null);
	}

	@Override
	public void update(Note note) {
		
		if (noteRepository.existsById(note.getId())) {
           noteRepository.save(note);
        } else {
            // Note does not exist, return null or handle accordingly
        	throw new IllegalArgumentException("Note with ID " + note.getId() + " does not exist");
        } 
	}

	@Override
	public void deleteById(int noteId) {
		noteRepository.deleteAllById(noteId);
		
	}

	@Override
	public Page<Note> getAllNotesByUser(User user,Pageable pageable) {
		
		return noteRepository.findByUser(user,pageable);
	}

	@Override
	public void delete(int noteId) {
		noteRepository.deleteById(noteId);
		
	}
	
	@Override
    public Page<Note> searchNotesByTitle(User user, String title, Pageable pageable) {
        return noteRepository.findByUserAndTitleContainingIgnoreCase(user, title, pageable);
    }

	

	

	

}
