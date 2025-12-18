package com.tracker.zen.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.tracker.zen.Entity.TradeOrdersEnitity;

@Service
public class TradeOrdersService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<TradeOrdersEnitity> getExecutedTradeHistory(String stockCode) {
		String sql = "SELECT * FROM trade_orders WHERE stock_code = ? AND normal_order_status = '約定' ORDER BY order_datetime";

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, stockCode);
		List<TradeOrdersEnitity> tradeRecords = new ArrayList<>();

		for (Map<String, Object> row : rows) {
			TradeOrdersEnitity entity = new TradeOrdersEnitity();
			entity.setCorrection((String) row.get("correction"));
			entity.setCancellation((String) row.get("cancellation"));
			entity.setOrderNumber((String) row.get("order_number"));
			entity.setNormalOrderStatus((String) row.get("normal_order_status"));
			entity.setStopOrderStatus((String) row.get("stop_order_status"));
			entity.setAlgoOrderStatus((String) row.get("algo_order_status"));
			entity.setStockCode((String) row.get("stock_code"));
			entity.setStockName((String) row.get("stock_name"));
			entity.setAccountType((String) row.get("account_type"));
			entity.setMarket((String) row.get("market"));
			entity.setCreditType((String) row.get("credit_type"));
			entity.setRepaymentPeriod((String) row.get("repayment_period"));
			entity.setOrderDatetime((String) row.get("order_datetime"));
			entity.setBuySell((String) row.get("buy_sell"));
			entity.setTradeType((String) row.get("trade_type"));
			entity.setExecutionCondition((String) row.get("execution_condition"));
			entity.setOrderExpiry((String) row.get("order_expiry"));
			entity.setOrderQuantity((String) row.get("order_quantity"));
			entity.setContractQuantity((String) row.get("contract_quantity"));
			entity.setOrderPrice((String) row.get("order_price"));
			entity.setCurrentPrice((String) row.get("current_price"));
			entity.setHighPrice((String) row.get("high_price"));
			entity.setLowPrice((String) row.get("low_price"));
			entity.setTradingVolume((String) row.get("trading_volume"));
			entity.setOrderType((String) row.get("order_type"));
			entity.setStopOrderCondition((String) row.get("stop_order_condition"));
			entity.setSetOrder((String) row.get("set_order"));
			entity.setSetOrderCondition((String) row.get("set_order_condition"));
			entity.setTaxCategory((String) row.get("tax_category"));
			entity.setOrderExpiryDatetime((String) row.get("order_expiry_datetime"));
			entity.setOrderExpiryReason((String) row.get("order_expiry_reason"));
			entity.setInputSource((String) row.get("input_source"));
			entity.setAlgoDetail((String) row.get("algo_detail"));
			entity.setAlgoOrderCondition((String) row.get("algo_order_condition"));
			entity.setPriceJudgmentTime((String) row.get("price_judgment_time"));
			entity.setPriceJudgmentInfo((String) row.get("price_judgment_info"));
			tradeRecords.add(entity);
		}
		return tradeRecords;
	}

	public List<Map<String, Object>> getStockData(String ticker) {
		String sql = "SELECT order_datetime, current_price FROM trade_orders WHERE stock_code = ? ORDER BY order_datetime";
		return jdbcTemplate.queryForList(sql, ticker);
	}

}
