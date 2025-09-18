package com.kuyu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kuyu.constats.ScreanConstats;

@Controller
public class CS10001_TopMenuController {

	@GetMapping("/TopMenu")
	public String showTopMenu(Model model) {

		return ScreanConstats.CSID_10001_TopMenu;
	}
}
