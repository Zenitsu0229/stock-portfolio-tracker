package com.tracker.zen.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeOrdersEnitity {
	/** 訂正 */
	private String correction;

	/** 取消 */
	private String cancellation;

	/** 受付No */
	private String orderNumber;

	/** 通常注文状況 */
	private String normalOrderStatus;

	/** 逆指値注文状況 */
	private String stopOrderStatus;

	/** アルゴ注文状況 */
	private String algoOrderStatus;

	/** コード */
	private String stockCode;

	/** 銘柄名 */
	private String stockName;

	/** 口座区分 */
	private String accountType;

	/** 市場 */
	private String market;

	/** 信用区分 */
	private String creditType;

	/** 弁済期限 */
	private String repaymentPeriod;

	/** 発注/受注日時 */
	private String orderDatetime;

	/** 売買 */
	private String buySell;

	/** 取引 */
	private String tradeType;

	/** 執行条件 */
	private String executionCondition;

	/** 注文期限 */
	private String orderExpiry;

	/** 注文数量(株/口) */
	private String orderQuantity;

	/** 約定数量(株/口) */
	private String contractQuantity;

	/** 注文単価(円) */
	private String orderPrice;

	/** 現在値 */
	private String currentPrice;

	/** 高値 */
	private String highPrice;

	/** 安値 */
	private String lowPrice;

	/** 出来高 */
	private String tradingVolume;

	/** 注文区分 */
	private String orderType;

	/** 逆指値条件 */
	private String stopOrderCondition;

	/** セット注文 */
	private String setOrder;

	/** セット注文条件 */
	private String setOrderCondition;

	/** 税区分 */
	private String taxCategory;

	/** 注文失効日時 */
	private String orderExpiryDatetime;

	/** 注文失効理由 */
	private String orderExpiryReason;

	/** 入力経路 */
	private String inputSource;

	/** アルゴ明細 */
	private String algoDetail;

	/** アルゴ注文条件 */
	private String algoOrderCondition;

	/** 価格判定時刻 */
	private String priceJudgmentTime;

	/** 価格判定情報/対象外理由 */
	private String priceJudgmentInfo;
}
