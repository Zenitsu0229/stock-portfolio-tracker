package com.tracker.zen.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * トレード日記のエンティティクラス。
 * DBテーブル：trade_diary に対応。
 */
public class TradeDiaryEntity {

	/** 識別ID（主キー） */
	private Long id;

	/** 実際の取引日（日付のみ） */
	private LocalDate tradeDate;

	/** 銘柄コードやシンボル（例：7203、AAPLなど） */
	private String symbol;

	/** 売買区分（例：BUY / SELL） */
	private String side;

	/** エントリー理由（なぜこの取引をしたか） */
	private String entryReason;

	/** 売買ルール順守フラグ（例：Yes / No） */
	private String ruleObservance;

	/** 感情の記録（例：自信あり、不安、焦りなど） */
	private String emotion;

	/** 損益（プラスは利益、マイナスは損失） */
	private int profitLoss;

	/** コメント（自由記述欄） */
	private String comment;

	/** 登録日時（システムが自動付与） */
	private LocalDateTime createdAt;

	// ---- Getter / Setter ----

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getEntryReason() {
		return entryReason;
	}

	public void setEntryReason(String entryReason) {
		this.entryReason = entryReason;
	}

	public String getRuleObservance() {
		return ruleObservance;
	}

	public void setRuleObservance(String ruleObservance) {
		this.ruleObservance = ruleObservance;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public int getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(int profitLoss) {
		this.profitLoss = profitLoss;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
