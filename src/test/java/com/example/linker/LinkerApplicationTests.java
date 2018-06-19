package com.example.linker;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

import com.example.linker.model.File;
import com.example.linker.model.Note;
import com.example.linker.repository.NoteRepository;
import com.example.linker.service.NoteService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LinkerApplicationTests {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Test
	public void savingAndDeleting() {
		Note note = generateRandomNote();
		noteService.save(note);
		
		Note note2 = noteService.showNoteByUrl(note.getUrl());
		assertThat(note.getName()).isEqualTo(note2.getName());
		assertThat(note.getText()).isEqualTo(note2.getText());
		assertThat(note.getMaxNumberOfViews()).isEqualTo(note2.getMaxNumberOfViews());
		assertThat(note.getFile().getName()).isEqualTo(note2.getFile().getName());
		assertThat(note.getFile().getData()).isEqualTo(note2.getFile().getData());
		assertThat(note2.getNumberOfViews()).isEqualTo(1);
		
		noteRepository.delete(note);
		
		assertThrows(NullPointerException.class, () -> {
			noteService.showNoteByUrl(note.getUrl());
		  });		
	}
	
	@Test
	public void showNoteByUrl() {
		Note note = generateRandomNote();
		noteService.save(note);
		
		for (int i = 0; i < note.getMaxNumberOfViews(); i++) {
			noteService.showNoteByUrl(note.getUrl());
		}
		
		assertThrows(NullPointerException.class, () -> {
			noteService.showNoteByUrl(note.getUrl());
		  });		
	}
	
	@Test
	public void showFile() {
		Note note = generateRandomNote();
		noteService.save(note);
		
		for (int i = 0; i < note.getMaxNumberOfViews(); i++) {
			noteService.getFileDataByUrl(note.getUrl(), note.getFile().getName());
		}
		
		assertThrows(NullPointerException.class, () -> {
			noteService.getFileDataByUrl(note.getUrl(), note.getFile().getName());
		  });		
	}
	
	private Note generateRandomNote() {
		Random rand = new Random();
		
		File file = new File();
		file.setName(generateRandomString(rand.nextInt(30)));
		file.setData(new byte[189]); 

		Note note = new Note();
		note.setFile(file);
		note.setMaxNumberOfViews(rand.nextInt(10));
		note.setName(generateRandomString(rand.nextInt(30)));
		note.setText(generateRandomString(rand.nextInt(200)));
		
		return note;
	}

	private String generateRandomString(int length) {
		return RandomStringUtils.random(length, true, true);
	}
}
