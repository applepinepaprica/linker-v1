package com.example.linker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.linker.model.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{
	
	Note findByUrl(String url);
}