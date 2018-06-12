package com.example.linker.service;

import com.example.linker.model.Note;

public interface NoteService {
	
	void save(Note note);
	
	Note showByUrl(String url);
}
