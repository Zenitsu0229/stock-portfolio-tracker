package com.tracker.zen.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tracker.zen.Entity.TradeRecordEntity;

/**
 * 損益データを取得するサービスクラス。
 */
@Service
public class ProfitLossService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 日付事に全ての取引の損益結果を取得する
	 * @return Map<String, Integer> 日付をキーとし、損益額を値とするマップ
	 */
	public Map<String, Integer> getProfitLossData() {
		String sql = "SELECT TRADE_DATE AS 取引日, " +
			"SUM(CAST(REPLACE(REALIZED_PROFIT_LOSS, ',', '') AS SIGNED)) AS 総実現損益 " +
			"FROM TRADE_RECORDS " +
			"GROUP BY TRADE_DATE " +
			"ORDER BY 取引日 DESC";

		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		Map<String, Integer> profitLossData = new HashMap<>();

		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd"); // SQL の日付フォーマット
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd"); // JavaScript 用フォーマット

		for (Map<String, Object> row : results) {
			try {
				String tradeDate = row.get("取引日").toString();
				String formattedDate = outputFormat.format(inputFormat.parse(tradeDate)); // フォーマット変換
				int profitLoss = ((Number) row.get("総実現損益")).intValue(); // 損益額を取得
				profitLossData.put(formattedDate, profitLoss);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return profitLossData;
	}

	/**
	 * 該当日付のやり取りをすべて取得する
	 * @throws DataAccessException 
	 */
	public List<TradeRecordEntity> getTradeRecordsByDate(String tradeDate) {

		String day = tradeDate.replace("-", "/");

		String sql = """
			    SELECT
			        TRADE_DATE,
			        SETTLEMENT_DATE,
			        TICKER_CODE,
			        STOCK_NAME,
			        ACCOUNT_TYPE,
			        MARKET,
			        TRADE_TYPE,
			        CREDIT_TYPE,
			        REALIZED_PROFIT_LOSS,
			        REALIZED_PROFIT_LOSS_FOREIGN,
			        PRICE_PER_UNIT,
			        QUANTITY,
			        SELL_SETTLEMENT_AMOUNT,
			        SELL_SETTLEMENT_AMOUNT_FOREIGN,
			        AVG_ACQUISITION_PRICE,
			        AVG_ACQUISITION_PRICE_FOREIGN,
			        ACQUISITION_DATE,
			        ACQUISITION_QUANTITY,
			        SETTLEMENT_AMOUNT,
			        SETTLEMENT_AMOUNT_FOREIGN,
			        INVESTMENT_AMOUNT_FOREIGN,
			        CFD_ACCOUNT,
			        CURRENCY,
			        EXCHANGE_RATE
			    FROM TRADE_RECORDS
			    WHERE TRADE_DATE = ?
			    ORDER BY TICKER_CODE,
			    REALIZED_PROFIT_LOSS
			""";

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, day);

		return rows.stream()
			.map(row -> TradeRecordEntity.mapRowToTradeRecord(row))
			.collect(Collectors.toList());
	}

	/**
	 * 取引銘柄一覧を表示
	 * @throws DataAccessException 
	 */
	public List<TradeRecordEntity> selectTickerListSearch(String startDate, String endDate) {

		String sql = "SELECT STOCK_NAME, "
			+ "TICKER_CODE, "
			+ "SUM(CAST(REPLACE(REALIZED_PROFIT_LOSS, ',', '') AS SIGNED)) AS TOTAL_PROFIT_LOSS "
			+ "FROM trade_records";
		List<Object> params = new ArrayList<>();
		List<TradeRecordEntity> selectTickerList = new ArrayList<TradeRecordEntity>();

		// 開始日
		if (startDate != null) {
			sql += " WHERE TRADE_DATE > ?";
			params.add(startDate);
		}

		// 終了日
		if (endDate != null) {
			sql += (params.isEmpty() ? " WHERE " : " AND ") + "TRADE_DATE < ?";
			params.add(endDate);
		}

		sql += " GROUP BY STOCK_NAME,TICKER_CODE"
			+ " ORDER BY TOTAL_PROFIT_LOSS DESC";

		// SQL実行
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params.toArray());

		// 結果を selectTickerList に格納
		for (Map<String, Object> row : rows) {
			TradeRecordEntity entity = new TradeRecordEntity();
			entity.setStockName((String) row.get("STOCK_NAME"));
			entity.setTickerCode((String) row.get("TICKER_CODE"));
			entity.setRealizedProfitLoss(row.get("TOTAL_PROFIT_LOSS").toString());
			selectTickerList.add(entity);
		}

		return selectTickerList;

	}
}
