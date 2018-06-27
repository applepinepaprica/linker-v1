package com.example.linker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.assertj.core.api.Assertions.*;

import java.util.Random;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.ConstraintViolation;

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
	
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Test
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
	public void registration() {
		User user = generateRandomUser();
		
		userService.save(user);
		
		User user2 = userRepository.findByUsername(user.getUsername());
		assertThat(user.getUsername()).isEqualTo(user2.getUsername());
		assertThat(user.getPassword()).isEqualTo(user2.getPassword());
		
		userRepository.delete(user);
	}
	
	@Test
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
	
	@Test
	public void validation_NullUser() {
		User user = new User();
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
        assert !violations.isEmpty();
		
		assertThrows(NullPointerException.class, () -> {
			userService.save(user);
		  });		
	}
	
	@Test
	public void validation_NullUsername() {
		User user = new User();
		user.setPassword("ghtyrhnf");
		
		assertViolationUser(user);		
	}
	
	@Test
	public void validation_BlankUsername() {
		User user = new User();
		user.setPassword("ghtyrhnf");
		user.setUsername("         ");
		
		assertViolationUser(user);			
	}
	
	@Test
	public void validation_NullPasssword() {
		User user = new User();
		user.setUsername("ghtyrhnf");
		
		assertThrows(NullPointerException.class, () -> {
			userService.save(user);
		  });		
	}
	
	/*@Test
	public void validation_BlankPasssword() {
		User user = new User();
		user.setUsername("ghtyrhnf");
		user.setPassword("          ");
		
		assertThrows(TransactionSystemException.class, () -> {
			userService.save(user);
		  });		
	}*/
	
	@Test
	public void validation_MinSizeUsername() {
		User user = new User();
		user.setPassword("ghtyrhnt");
		user.setUsername("ghtyrhn");
		
		assertViolationUser(user);				
	}
	
	@Test
	public void validation_MaxSizeUsername() {
		User user = new User();
		user.setPassword("ghtyrhnt");
		user.setUsername("ghtyrhnghtrytfhgtre92");
		
		assertViolationUser(user);				
	}
	
	@Test
	public void validation_NullNote() {
		Note note = new Note();
		
		assertViolationNote(note);		
	}
	
	@Test
	public void validation_NullName() {
		Note note = new Note();
		note.setMaxNumberOfViews(1);
		
		assertViolationNote(note);		
	}
	
	@Test
	public void validation_NullMaxNumberOfViews() {
		Note note = new Note();
		note.setName("One morning, when Gregor Samsa woke from");
		
		assertViolationNote(note);		
	}
	
	@Test
	public void validation_BlankName() {
		Note note = new Note();
		note.setMaxNumberOfViews(1);
		note.setName("          ");
		
		assertViolationNote(note);			
	}
	
	@Test
	public void validation_SizeName() {
		Note note = new Note();
		note.setMaxNumberOfViews(1);
		note.setName("One morning, when Gregor Samsa woke from.");
		
		assertViolationNote(note);		
	}
	
	@Test
	public void validation_SizeText() {
		Note note = new Note();
		note.setMaxNumberOfViews(1);
		note.setName("One morning, when Gregor Samsa woke from");
		note.setText(generateRandomString(5000));
		
		assertViolationNote(note);		
	}
	
	@Test
	public void validation_MinSizeMaxNumberOfViews() {
		Note note = new Note();
		note.setMaxNumberOfViews(0);
		note.setName("One morning, when Gregor Samsa woke from");
		
		assertViolationNote(note);		
	}
	
	@Test
	public void validation_MaxSizeMaxNumberOfViews() {
		Note note = new Note();
		note.setMaxNumberOfViews(65536);
		note.setName("One morning, when Gregor Samsa woke from");
		
		assertViolationNote(note);		
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
		
		System.out.println("============================");
		System.out.println(note.getName() + " " + 
				note.getMaxNumberOfViews() + " " + 
				note.getText() + " " + 
				note.getFile());
		System.out.println("============================");
		
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
		user.setUsername(generateRandomString(rand.nextInt(12) + 7));
		user.setPassword(generateRandomString(rand.nextInt(12) + 7));
		
		System.out.println("============================");
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println("============================");
		
		return user;
	}

	private String generateRandomString(int length) {
		length++;
		return RandomStringUtils.random(length, true, true);
	}
	
	private void assertViolationUser(User user) {
		Set<ConstraintViolation<User>> violations = validator.validate(user);
        assert !violations.isEmpty();
		
		assertThrows(TransactionSystemException.class, () -> {
			userService.save(user);
		  });
	}
	
	private void assertViolationNote(Note note) {
		Set<ConstraintViolation<Note>> violations = validator.validate(note);
        assert !violations.isEmpty();
		
		assertThrows(TransactionSystemException.class, () -> {
			noteService.save(note);
		  });
	}
}