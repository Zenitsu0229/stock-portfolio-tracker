package com.tracker.zen.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tracker.zen.Entity.TradeDiaryEntity;
import com.tracker.zen.component.ApiCommonExtends;
import com.tracker.zen.constats.ScreanConstats;
import com.tracker.zen.service.TradeDiaryService;

@Controller
public class CS50001_DiaryTradeController {

	@Autowired
	private TradeDiaryService tradeDiaryService;

	/*
	 * 初期表示
	 * 
	 */
	@GetMapping("/diary-trade-top")
	public String showTopMenu(Model model) {

		TradeDiaryEntity tradeDiary = new TradeDiaryEntity();
		tradeDiary.setTradeDate(LocalDate.now()); // ← 現在日付をセット
		tradeDiary.setProfitLoss(0); // ← 現在日付をセット
		ApiCommonExtends apiExtend = new ApiCommonExtends();
		model.addAttribute("allTickList", apiExtend.getAllTickList());
		model.addAttribute("tradeDiary", tradeDiary);
		model.addAttribute("tradeList", tradeDiaryService.todaySearchDiary());
		return ScreanConstats.CS50001_DiaryTradeTop;
	}

	/*
	 * 入力画面に記載された内容をDBに登録する。
	 * 
	 */
	@PostMapping("/diary-insert")
	public String insert(Model model, @ModelAttribute TradeDiaryEntity tradeDiary) {

		// 取引日がからの場合は当日を設定する
		if (tradeDiary.getTradeDate() == null) {
			tradeDiary.setTradeDate(LocalDate.now()); // ← 現在日付をセット
		}

		// 入力データを登録する
		tradeDiaryService.executeInsert(tradeDiary);

		// 銘柄コードを設定
		ApiCommonExtends apiExtend = new ApiCommonExtends();
		model.addAttribute("allTickList", apiExtend.getAllTickList());

		model.addAttribute("tradeList", tradeDiaryService.todaySearchDiary());

		model.addAttribute("tradeDiary", tradeDiary);

		return ScreanConstats.CS50001_DiaryTradeTop;
	}

	/*
	 * 日記一覧検索
	 * 
	 */
	@GetMapping("diary-select-month")
	public String diarySelectMonth(Model model) {

		// 銘柄コードを設定
		ApiCommonExtends apiExtend = new ApiCommonExtends();
		model.addAttribute("allTickList", apiExtend.getAllTickList());
		model.addAttribute("tradeList", tradeDiaryService.diarySelectMonth());

		return ScreanConstats.CS50003_DiaryTradeDetail;
	}
}
