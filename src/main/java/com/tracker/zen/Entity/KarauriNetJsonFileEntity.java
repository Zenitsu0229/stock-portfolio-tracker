package com.tracker.zen.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KarauriNetJsonFileEntity {

	// 計算日
	private String calculationDate;

	// 空売り者
	private String shortSeller;

	// 残高割合
	private String balanceRatio;

	// 増減率
	private String changeRate;

	// 残高数量
	private String balanceQuantity;

	// 増減量
	private String changeAmount;

	// 備考
	private String remarks;

	// 当日高値
	private String toDayBuyHigh;

	// 当日安値
	private String toDayBuyLow;

	// 当日平均値
	private String toDayBuyAve;

	public KarauriNetJsonFileEntity(String calculationDate, String shortSeller, String balanceRatio,
		String changeRate, String balanceQuantity, String changeAmount, String remarks) {
		this.calculationDate = calculationDate;
		this.shortSeller = shortSeller;
		this.balanceRatio = balanceRatio;
		this.changeRate = changeRate;
		this.balanceQuantity = balanceQuantity;
		this.changeAmount = changeAmount;
		this.remarks = remarks;
	}
}
