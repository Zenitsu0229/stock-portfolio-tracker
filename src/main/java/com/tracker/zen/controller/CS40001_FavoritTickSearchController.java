package com.tracker.zen.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracker.zen.Entity.KarauriNetJsonFileEntity;
import com.tracker.zen.component.ApiCommonExtends;
import com.tracker.zen.component.ScrapingKarauriNet;
import com.tracker.zen.constats.ScreanConstats;

@Controller
public class CS40001_FavoritTickSearchController {

	@GetMapping("/favorit-Ticker-search")
	public String showTopMenu(Model model) {

		// 全銘柄を取得する
		ApiCommonExtends apiExtend = new ApiCommonExtends();
		model.addAttribute("allTickList", apiExtend.getAllTickList());

		return ScreanConstats.CSID_40001_FavoriteTickerSearch;
	}

	/*
	 *  銘柄検索時
	 *  @param searchTickName 検索パラメーター
	 *  
	 */
	@GetMapping("/searchResultTick")
	public String searchTick(@RequestParam(name = "searchBox", required = false) String searchTickName,
		@RequestParam(name = "startDate", required = false) String startDate,
		Model model) {

		// 入力値から数字のみを抽出し、銘柄番号を取得する
		String tickerCode = searchTickName.replaceAll("\\D+", ""); // 数字以外を削除

		if (tickerCode.length() != 4) {
			ApiCommonExtends apiExtend = new ApiCommonExtends();
			model.addAttribute("allTickList", apiExtend.getAllTickList());
			return ScreanConstats.CSID_40001_FavoriteTickerSearch;
		}

		// 空売りネット表
		List<KarauriNetJsonFileEntity> karauriNetJsonFileEntityList = new ArrayList<>();

		// 空売りネットAPI
		ScrapingKarauriNet scrapingKarauriNet = new ScrapingKarauriNet();

		// 空売りネットAPIから空売りネット表を取得する
		karauriNetJsonFileEntityList = scrapingKarauriNet.getScrapeShortBalance(tickerCode);

		// 空売りチャートを表示
		String karauriChart = scrapingKarauriNet.getChart(tickerCode, startDate);

		// 全銘柄を取得する
		ApiCommonExtends apiExtend = new ApiCommonExtends();
		model.addAttribute("allTickList", apiExtend.getAllTickList());

		// 空売り状況をセットする
		model.addAttribute("karauriNetJsonFileEntityList", karauriNetJsonFileEntityList);

		// 空売りチャートをセットする
		model.addAttribute("karauriChart", karauriChart);

		// 空売りチャートをセットする
		model.addAttribute("tickerCode", tickerCode);

		// 検索名を表示
		model.addAttribute("searchResult", searchTickName);

		return ScreanConstats.CSID_40001_FavoriteTickerSearch;
	}
}
