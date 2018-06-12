package com.example.linker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value = { "/result" }, method = RequestMethod.POST)
	public String result(Note note, Model model) {
		noteService.save(note);
		model.addAttribute(note);
		return "result";
	}
	
	@RequestMapping(value = { "/url/{url}" }, method = RequestMethod.GET)
	public String result(@PathVariable String url, Model model) {
		Note note = noteService.showByUrl(url);
		model.addAttribute(note);
		return "note";
	}
}
