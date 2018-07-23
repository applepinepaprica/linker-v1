package com.example.linker.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.linker.model.File;
import com.example.linker.model.Note;
import com.example.linker.model.User;
import com.example.linker.repository.NoteRepository;
import com.example.linker.repository.UserRepository;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void save(Note note, MultipartFile file)  {
		
		if (!file.isEmpty()) {
			File f = new File();
			f.setName(file.getOriginalFilename());
			
			try {
				f.setData(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			note.setFile(f);
		}
		
		save(note);
	}
	
	public void save(Note note) {
		note.setUrl(UUID.randomUUID().toString());
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			User user = userRepository.findByUsername(auth.getName());
			note.setUser(user);
		}
		catch (NullPointerException e) {	
		}
		
		noteRepository.save(note);
	}
	
	public Note showNoteByUrl(String url) {
		Note note = noteRepository.findByUrl(url);
		
		note.setNumberOfViews(note.getNumberOfViews() + 1);

		if (note.getFile() == null && note.getNumberOfViews() == note.getMaxNumberOfViews()) {
			noteRepository.delete(note);
		} else {
			noteRepository.save(note);
		}
		
		return note;
	}
	
	public byte[] getFileDataByUrl(String url, String fileName) {
		Note note = noteRepository.findByUrl(url);
		
		note.getFile().setNumberOfViews(note.getFile().getNumberOfViews() + 1);
		
		if (note.getFile().getNumberOfViews() == note.getMaxNumberOfViews()) {
			noteRepository.delete(note);
		} else {
			noteRepository.save(note);
		}
		
		return note.getFile().getData();
	}
	
	public List<Note> getUsersNotes() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByUsername(auth.getName());
		return user.getNotes();
	}
}