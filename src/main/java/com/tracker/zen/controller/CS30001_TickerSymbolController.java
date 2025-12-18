package com.tracker.zen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracker.zen.Entity.TradeRecordEntity;
import com.tracker.zen.constats.ScreanConstats;
import com.tracker.zen.service.ProfitLossService;

@Controller
public class CS30001_TickerSymbolController {

	@Autowired
	private ProfitLossService profitLossService;

	@GetMapping("/Ticker-Symbol-List")
	public String TickerListController(Model model) {

		List<TradeRecordEntity> tickerList = new ArrayList<TradeRecordEntity>();

		// 全ての取引結果を取得する
		tickerList = profitLossService.selectTickerListSearch(null, null);
		model.addAttribute("tickerList", tickerList);

		return ScreanConstats.CSID_30001_TickerSymbolList;
	}

	@GetMapping("/stocks/list")
	public String getStockList(
		@RequestParam(required = false) String startDate,
		@RequestParam(required = false) String endDate,
		Model model) {

		if (startDate.equals("")) {
			startDate = null;
		} else {
			startDate.replace("-", "/");
		}
		if (endDate.equals("")) {
			endDate = null;
		} else {
			endDate.replace("-", "/");
		}

		// 取引銘柄の取得（期間指定可能）
		List<TradeRecordEntity> tickerList = profitLossService.selectTickerListSearch(startDate, endDate);

		model.addAttribute("tickerList", tickerList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return ScreanConstats.CSID_30001_TickerSymbolList; // Thymeleafのテンプレート名
	}

	@GetMapping("/stocks-detail")
	public String dsada(
		@RequestParam(required = false) String startDate,
		@RequestParam(required = false) String endDate,
		Model model) {

		// 取引銘柄の取得（期間指定可能）
		List<TradeRecordEntity> tickerList = profitLossService.selectTickerListSearch(startDate, endDate);

		model.addAttribute("tickerList", tickerList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);

		return ScreanConstats.CSID_30001_TickerSymbolList; // Thymeleafのテンプレート名
	}
}
