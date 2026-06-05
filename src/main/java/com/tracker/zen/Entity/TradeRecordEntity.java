package com.tracker.zen.Entity;

import java.util.Map;
import java.util.Objects;

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
		record.setTradeDate(Objects.toString(row.get("TRADE_DATE"), null));
		record.setSettlementDate(Objects.toString(row.get("SETTLEMENT_DATE"), null));
		record.setTickerCode(Objects.toString(row.get("TICKER_CODE"), null));
		record.setStockName(Objects.toString(row.get("STOCK_NAME"), null));
		record.setAccountType(Objects.toString(row.get("ACCOUNT_TYPE"), null));
		record.setMarket(Objects.toString(row.get("MARKET"), null));
		record.setTradeType(Objects.toString(row.get("TRADE_TYPE"), null));
		record.setCreditType(Objects.toString(row.get("CREDIT_TYPE"), null));
		record.setRealizedProfitLoss(Objects.toString(row.get("REALIZED_PROFIT_LOSS"), null));
		record.setRealizedProfitLossForeign(Objects.toString(row.get("REALIZED_PROFIT_LOSS_FOREIGN"), null));
		record.setPricePerUnit(Objects.toString(row.get("PRICE_PER_UNIT"), null));
		record.setQuantity(Objects.toString(row.get("QUANTITY"), null));
		record.setSellSettlementAmount(Objects.toString(row.get("SELL_SETTLEMENT_AMOUNT"), null));
		record.setSellSettlementAmountForeign(Objects.toString(row.get("SELL_SETTLEMENT_AMOUNT_FOREIGN"), null));
		record.setAvgAcquisitionPrice(Objects.toString(row.get("AVG_ACQUISITION_PRICE"), null));
		record.setAvgAcquisitionPriceForeign(Objects.toString(row.get("AVG_ACQUISITION_PRICE_FOREIGN"), null));
		record.setAcquisitionDate(Objects.toString(row.get("ACQUISITION_DATE"), null));
		record.setAcquisitionQuantity(Objects.toString(row.get("ACQUISITION_QUANTITY"), null));
		record.setSettlementAmount(Objects.toString(row.get("SETTLEMENT_AMOUNT"), null));
		record.setSettlementAmountForeign(Objects.toString(row.get("SETTLEMENT_AMOUNT_FOREIGN"), null));
		record.setInvestmentAmountForeign(Objects.toString(row.get("INVESTMENT_AMOUNT_FOREIGN"), null));
		record.setCfdAccount(Objects.toString(row.get("CFD_ACCOUNT"), null));
		record.setCurrency(Objects.toString(row.get("CURRENCY"), null));
		record.setExchangeRate(Objects.toString(row.get("EXCHANGE_RATE"), null));
		return record;
	}

}
