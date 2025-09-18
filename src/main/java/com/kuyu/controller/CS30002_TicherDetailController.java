package com.kuyu.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kuyu.Entity.KarauriNetJsonFileEntity;
import com.kuyu.Entity.TradeOrdersEnitity;
import com.kuyu.component.ScrapingKarauriNet;
import com.kuyu.constats.ScreanConstats;
import com.kuyu.service.TradeOrdersService;

@Controller
public class CS30002_TicherDetailController {

	@Autowired
	private TradeOrdersService stockService;

	@Autowired
	private ScrapingKarauriNet scrapingKarauriNet;

	// 初期画面表示
	@GetMapping("/Ticker-Detail")
	public String stockDetail(@RequestParam String ticker, Model model) {

		// 対象株の取引履歴をすべて取得する
		List<TradeOrdersEnitity> tradeOrdersHistoryList = new ArrayList<TradeOrdersEnitity>();

		// 取引履歴を取得
		tradeOrdersHistoryList = stockService.getExecutedTradeHistory(ticker);

		List<KarauriNetJsonFileEntity> karauriNetJsonFileEntityList = new ArrayList<>();

		// 空売り履歴を取得
		karauriNetJsonFileEntityList = scrapingKarauriNet.getScrapeShortBalance(ticker);

		// 取引銘柄
		model.addAttribute("tickName", tradeOrdersHistoryList.get(0).getStockName());
		model.addAttribute("tickerCode", tradeOrdersHistoryList.get(0).getStockCode());
		model.addAttribute("tickerCode", ticker);
		model.addAttribute("tradeHistory", tradeOrdersHistoryList);
		model.addAttribute("karauriNetJsonFileEntityList", karauriNetJsonFileEntityList);
		return ScreanConstats.CSID_30002_TickerDetail;
	}
}
