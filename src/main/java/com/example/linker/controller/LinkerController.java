package com.example.linker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.linker.model.Note;
import com.example.linker.model.User;
import com.example.linker.service.NoteService;
import com.example.linker.service.UserService;

@Controller
public class LinkerController {

	@Autowired
	UserService userService;

	@Autowired
	NoteService noteService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationGet(Model model) {
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registrationPost(User user, Model model) {
		userService.save(user);
		return "login";
	}

	@RequestMapping(value = { "/", "/home", "/result" }, method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute(new Note());
		return "home";
	}

	@RequestMapping(value = { "/result" }, method = RequestMethod.POST)
	public String result(@Valid Note note, 
			BindingResult bindingResult, 
			@RequestParam("fileUpload") MultipartFile file,
			Model model) {
		
		if (bindingResult.hasErrors()) {
            return "home";
        }
		
		noteService.save(note, file);
		model.addAttribute(note);
		return "result";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("notes", noteService.getUsersNotes());
		return "list";
	}

	@RequestMapping(value = { "/url/{url}" }, method = RequestMethod.GET)
	public String result(@PathVariable String url, Model model) {
		Note note = noteService.showNoteByUrl(url);
		model.addAttribute(note);
		return "note";
	}

	@RequestMapping(value = {
			"/url/{url}/{fileName}" }, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE, method = RequestMethod.GET)
	public @ResponseBody byte[] file(@PathVariable String url, @PathVariable String fileName, Model model) {
		return noteService.getFileDataByUrl(url, fileName);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}
