package com.tracker.zen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tracker.zen.constats.ScreanConstats;

/*
 * TopMenu
 * 初期表示メニュー画面を表示する
 * 
 */
@Controller
public class CS10001_TopMenuController {

	@GetMapping("/TopMenu")
	public String showTopMenu(Model model) {

		// メニュー画面返却
		return ScreanConstats.CSID_10001_TopMenu;
	}
}
