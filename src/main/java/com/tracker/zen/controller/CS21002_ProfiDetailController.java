package com.tracker.zen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracker.zen.Entity.TradeRecordEntity;
import com.tracker.zen.component.CS21001_ProfiDetailComponent;
import com.tracker.zen.constats.ScreanConstats;
import com.tracker.zen.service.ProfitLossService;

/*
 * ProfiDetailController
 * 損益詳細画面
 * 
 */
@Controller
public class CS21002_ProfiDetailController {

	@Autowired
	private ProfitLossService profitLossService;

	@Autowired
	private CS21001_ProfiDetailComponent profiDetailComponent;

	@GetMapping("/profit-detail")
	public String getProfitDetail(@RequestParam("date") String date, Model model) {

		// 指定された日付の取引履歴を取得
		List<TradeRecordEntity> transactions = profitLossService.getTradeRecordsByDate(date);

		// 企業ごとに損益を集計する
		List<TradeRecordEntity> profitLossByCompany = profiDetailComponent.calculateCompanyProfitLoss(transactions);
		model.addAttribute("profitLossByCompany", profitLossByCompany);

		// 取得したデータをテンプレートに渡す
		model.addAttribute("date", date);
		model.addAttribute("transactions", transactions);

		// profit-detail.html（Thymeleaf）を表示
		return ScreanConstats.CSID_20002_TradeDetails;
	}
}
