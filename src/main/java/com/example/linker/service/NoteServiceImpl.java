package com.example.linker.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.linker.model.Note;
import com.example.linker.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	public void save(Note note) {
		note.setUrl(UUID.randomUUID().toString());

		noteRepository.save(note);
	}
	
	public Note showByUrl(String url) {
		Note note = noteRepository.findByUrl(url);
		
		note.setNumberOfViews(note.getNumberOfViews() + 1);

		if (note.getNumberOfViews() == note.getMaxNumberOfViews()) {
			noteRepository.delete(note);
		} else {
			noteRepository.save(note);
		}
		
		return note;
	}
}
