package com.example.linker.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.linker.model.Note;

public interface NoteService {
	
	void save(Note note, MultipartFile file);
	
	Note showByUrl(String url);
}
