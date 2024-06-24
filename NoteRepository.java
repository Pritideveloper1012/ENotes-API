package com.notes.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.notes.entity.Note;
import com.notes.entity.User;

public interface NoteRepository extends JpaRepository<Note, Integer> {

	void deleteAllById(int noteId);

//	 List<Note> findByUser(User user); 

	Page<Note> findByUser(User user, Pageable pageable);
	Page<Note> findByUserAndTitleContainingIgnoreCase(User user, String title, Pageable pageable);

}
