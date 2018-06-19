package com.example.linker.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.linker.model.Note;

public interface NoteService {
	
	void save(Note note, MultipartFile file);
	
	void save(Note note);
	
	Note showNoteByUrl(String url);
	
	byte[] getFileDataByUrl(String url, String filename);
	
	public List<Note> getUsersNotes();
}
