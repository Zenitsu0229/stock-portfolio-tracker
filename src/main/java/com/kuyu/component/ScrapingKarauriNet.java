package com.kuyu.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import com.kuyu.Entity.KarauriNetJsonFileEntity;

@Component
public class ScrapingKarauriNet {

	// 📌 `karauri.net` から空売り残高情報を取得
	public List<KarauriNetJsonFileEntity> getScrapeShortBalance(String ticker) {
		String url = "https://karauri.net/" + ticker + "/";

		List<KarauriNetJsonFileEntity> karauriNetList = new ArrayList<>();

		try {
			// 📌 Jsoup でページを取得
			Document doc = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
				.get();

			// 📌 空売り残高のテーブルを取得（id="sort" の table）
			Element table = doc.selectFirst("table#sort");

			// 📌 テーブルの行を取得
			Elements rows = table.select("tbody tr");

			// 📌 データをリストに格納
			for (Element row : rows) {
				Elements cols = row.select("td");
				if (cols.size() < 7)
					continue; // データ不足ならスキップ

				KarauriNetJsonFileEntity karauriNetJsonFileEntity = new KarauriNetJsonFileEntity(
					cols.get(0).text().trim(), // 計算日
					cols.get(1).text().trim(), // 空売り者
					cols.get(2).text().trim(), // 残高割合
					cols.get(3).text().trim(), // 増減率
					cols.get(4).text().trim(), // 残高数量
					cols.get(5).text().trim(), // 増減量
					cols.get(6).text().trim() // 備考
				);
				karauriNetList.add(karauriNetJsonFileEntity);
			}
		} catch (IOException e) {

			// 0件で返却
			return karauriNetList;
		}
		// 0件で返却
		return karauriNetList;
	}

	/*
	 *  銘柄検索時
	 *  @param id 銘柄コード
	 *  @param date 日時
	 *  
	 */
	public String getChart(String id, String date) {
		try {
			String url = "https://japan-kabuka.com/chart/?id=" + id + "&candledate=" + date;
			Document doc = Jsoup.connect(url).get();
			Element chartDiv = doc.getElementById("chart");

			if (chartDiv != null) {
				return chartDiv.outerHtml();
			} else {
				return "データが取得できませんでした";
			}
		} catch (Exception e) {
			return "エラー: " + e.getMessage();
		}
	}
}
