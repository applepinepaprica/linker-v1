package com.example.linker.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.linker.model.File;
import com.example.linker.model.Note;
import com.example.linker.repository.FileRepository;
import com.example.linker.repository.NoteRepository;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private FileRepository fileRepository;
	
	public void save(Note note, MultipartFile file)  {
		note.setUrl(UUID.randomUUID().toString());
		
		noteRepository.save(note);
		
		File f = new File();
		f.setName(file.getOriginalFilename());
		f.setNote_id(note.getId());
		
		try {
			f.setData(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fileRepository.save(f);
	}
	
	public Note showNoteByUrl(String url) {
		Note note = noteRepository.findByUrl(url);
		
		note.setNumberOfViews(note.getNumberOfViews() + 1);

		if (note.getNumberOfViews() == note.getMaxNumberOfViews()) {
			noteRepository.delete(note);
		} else {
			noteRepository.save(note);
		}
		
		return note;
	}
	
	public byte[] getFileDataByUrl(String url, String fileName) {
		Note note = noteRepository.findByUrl(url);
		
		if (fileName.equals(note.getFile().getName())) {
			return note.getFile().getData();
		}
		return null;
	}
}
