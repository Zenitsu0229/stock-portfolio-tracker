package com.tracker.zen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tracker.zen.constats.ScreanConstats;
import com.tracker.zen.service.ProfitLossService;

/*
 * ProfitCalendarController
 * 損益カレンダークラス
 * 
 */
@Controller
public class CS20001_ProfitCalendarController {

	@Autowired
	private ProfitLossService profitLossService;

	/*
	 * 初期表示
	 */
	@GetMapping("/profit-calendar")
	public String profitCalendar() {
		return ScreanConstats.CSID_20001_ProfitCalendar;
	}

	/*
	 * 各日付の損益表示を行う
	 */
	@GetMapping("/profit-calculation")
	@ResponseBody
	public Map<String, Integer> getProfitLossData() {
		return profitLossService.getProfitLossData();
	}
}
