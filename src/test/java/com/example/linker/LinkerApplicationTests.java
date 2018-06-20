package com.example.linker;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;

import com.example.linker.model.File;
import com.example.linker.model.Note;
import com.example.linker.model.User;
import com.example.linker.repository.NoteRepository;
import com.example.linker.repository.UserRepository;
import com.example.linker.service.NoteService;
import com.example.linker.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LinkerApplicationTests {
	
	@Autowired
	private NoteService noteService;
	
	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Repeat(30)
	public void savingAndDeleting() {
		Note note = generateRandomNote(true);
		noteService.save(note);
		assert note.getUser_id() == null;
		
		Note note2 = noteService.showNoteByUrl(note.getUrl());
		
		note.setNumberOfViews(1);
		assert note.equals(note2);
		
		noteRepository.delete(note2);
		
		assertThrows(NullPointerException.class, () -> {
			noteService.showNoteByUrl(note.getUrl());
		  });		
	}
	
	@Test
	@Repeat(30)
	public void showNoteByUrl() {
		Note note = generateRandomNote(true);
		noteService.save(note);
		
		for (int i = 0; i < note.getMaxNumberOfViews(); i++) {
			Note note2 = noteService.showNoteByUrl(note.getUrl());
			assertThat(note2.getNumberOfViews()).isEqualTo(i + 1);
		}
		
		assertThrows(NullPointerException.class, () -> {
			noteService.showNoteByUrl(note.getUrl());
		  });		
	}
	
	@Test
	@Repeat(30)
	public void showFile() {
		Note note = generateRandomNote(false);
		noteService.save(note);
		
		for (int i = 0; i < note.getMaxNumberOfViews(); i++) {
			noteService.getFileDataByUrl(note.getUrl(), note.getFile().getName());
		}
		
		assertThrows(NullPointerException.class, () -> {
			noteService.getFileDataByUrl(note.getUrl(), note.getFile().getName());
		  });		
	}
	
	@Test
	@Repeat(30)
	public void registration() {
		User user = generateRandomUser();
		userService.save(user);
		
		User user2 = userRepository.findByUsername(user.getUsername());
		assertThat(user.getUsername()).isEqualTo(user2.getUsername());
		assertThat(user.getPassword()).isEqualTo(user2.getPassword());
		
		userRepository.delete(user);
	}
	
	@Test
	@Repeat(30)
	@WithMockUser("1")
	public void savingAndDeletingWithAuth() {
		Note note = generateRandomNote(true);
		noteService.save(note);
		assert note.getUser_id() != null;
		
		Note note2 = noteService.showNoteByUrl(note.getUrl());
		note.setNumberOfViews(1);
		assert note.equals(note2);
		
		User user = userRepository.findByUsername("1");
		assertThat(note2.getUser_id()).isEqualTo(user.getId());
		
		assert user.getNotes().contains(note2);
		assert noteService.getUsersNotes().contains(note2);
	
		noteRepository.delete(note2);
		
		assertThrows(NullPointerException.class, () -> {
			noteService.showNoteByUrl(note.getUrl());
		  });		
	}
	
	private Note generateRandomNote(boolean fileCanBeNull) {
		Random rand = new Random();
		Note note = new Note();
		
		if (rand.nextBoolean() || !fileCanBeNull) {
			File file = new File();
			file.setName(generateRandomString(rand.nextInt(30)));
			
			byte[] b = new byte[189];
			rand.nextBytes(b);
			file.setData(b); 
			note.setFile(file);
		}
		
		note.setMaxNumberOfViews(rand.nextInt(10) + 2);
		note.setName(generateRandomString(rand.nextInt(30)));
		
		if (rand.nextBoolean()) {
			note.setText(generateRandomString(rand.nextInt(200)));
		}
		
		System.out.println(note.getName() + " " + 
				note.getMaxNumberOfViews() + " " + 
				note.getText() + " " + 
				note.getFile());
		
		assert checkNote(note);
		
		return note;
	}
	
	private boolean checkNote(Note note) {
		if (note.getMaxNumberOfViews() < 2)
			return false;
		
		if (note.getName().isEmpty())
			return false;
		
		return true;
	}
	
	private User generateRandomUser() {
		Random rand = new Random();
		
		User user = new User();
		user.setUsername(generateRandomString(rand.nextInt(30)));
		user.setPassword(generateRandomString(rand.nextInt(30)));
		
		return user;
	}

	private String generateRandomString(int length) {
		length++;
		return RandomStringUtils.random(length, true, true);
	}
}