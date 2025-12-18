package com.tracker.zen.Entity;

import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRecordEntity {

	@Id
	private String tradeDate; // 約定日

	private String settlementDate; // 受渡日

	private String tickerCode; // コード/ティッカー

	private String stockName; // 銘柄名

	private String accountType; // 口座区分

	private String market; // 市場

	private String tradeType; // 取引（売埋/買埋）

	private String creditType; // 信用区分（制度/一般）

	private String realizedProfitLoss; // 実現損益(円)

	private String realizedProfitLossForeign; // 実現損益(ドル/外貨)

	private String pricePerUnit; // 単価(円/ドル/PT/外貨)

	private String quantity; // 数量(株/口/枚)

	private String sellSettlementAmount; // 売却/決済額(円)

	private String sellSettlementAmountForeign; // 売却/決済額(ドル)

	private String avgAcquisitionPrice; // 平均取得価額(円/PT)

	private String avgAcquisitionPriceForeign; // 平均取得価額(ドル)

	private String acquisitionDate; // 取得日/建日

	private String acquisitionQuantity; // 取得/建数量（株/口）

	private String settlementAmount; // 受渡金額(円)

	private String settlementAmountForeign; // 受渡金額(ドル)

	private String investmentAmountForeign; // 投資額(ドル)

	private String cfdAccount; // CFD口座

	private String currency; // 通貨

	private String exchangeRate; // 円換算レート

	public static TradeRecordEntity mapRowToTradeRecord(Map<String, Object> row) {
		TradeRecordEntity record = new TradeRecordEntity();
		record.setTradeDate((String) row.get("TRADE_DATE"));
		record.setSettlementDate((String) row.get("SETTLEMENT_DATE"));
		record.setTickerCode((String) row.get("TICKER_CODE"));
		record.setStockName((String) row.get("STOCK_NAME"));
		record.setAccountType((String) row.get("ACCOUNT_TYPE"));
		record.setMarket((String) row.get("MARKET"));
		record.setTradeType((String) row.get("TRADE_TYPE"));
		record.setCreditType((String) row.get("CREDIT_TYPE"));
		record.setRealizedProfitLoss((String) row.get("REALIZED_PROFIT_LOSS"));
		record.setRealizedProfitLossForeign((String) row.get("REALIZED_PROFIT_LOSS_FOREIGN"));
		record.setPricePerUnit((String) row.get("PRICE_PER_UNIT"));
		record.setQuantity((String) row.get("QUANTITY"));
		record.setSellSettlementAmount((String) row.get("SELL_SETTLEMENT_AMOUNT"));
		record.setSellSettlementAmountForeign((String) row.get("SELL_SETTLEMENT_AMOUNT_FOREIGN"));
		record.setAvgAcquisitionPrice((String) row.get("AVG_ACQUISITION_PRICE"));
		record.setAvgAcquisitionPriceForeign((String) row.get("AVG_ACQUISITION_PRICE_FOREIGN"));
		record.setAcquisitionDate((String) row.get("ACQUISITION_DATE"));
		record.setAcquisitionQuantity((String) row.get("ACQUISITION_QUANTITY"));
		record.setSettlementAmount((String) row.get("SETTLEMENT_AMOUNT"));
		record.setSettlementAmountForeign((String) row.get("SETTLEMENT_AMOUNT_FOREIGN"));
		record.setInvestmentAmountForeign((String) row.get("INVESTMENT_AMOUNT_FOREIGN"));
		record.setCfdAccount((String) row.get("CFD_ACCOUNT"));
		record.setCurrency((String) row.get("CURRENCY"));
		record.setExchangeRate((String) row.get("EXCHANGE_RATE"));
		return record;
	}

}
