package com.tracker.zen.constats;

import java.util.Map;

public class CommonConstats {

	// URL結合時のピリオド
	public static String SELECT_PERIOD = "\\.";

	// URL結合時のピリオド
	public static String SELECT_SLASH = "\\/";

	// URL結合時のピリオド
	public static String URL_PERIOD = ".";

	// URL結合時のピリオド
	public static String URL_SLASH = "/";

	// URL結合時のピリオド
	public static String SELECT_CANMA = ",";

	// エラーメッセージ
	public static String ERROR_MESSAGE = "errorText";

	// お問い合わせ種別「1」
	public static String CONTACT_ID_REVIW_REQUEST = "1";
	public static String CONTACT_ID_REV = "REV";

	// お問い合わせ種別「2」
	public static String CONTACT_ID_BUG_FIX_REQUEST = "2";
	public static String CONTACT_ID_BUG = "BUG";

	// お問い合わせ種別「3」
	public static String CONTACT_ID_OTHER_REQUEST = "3";
	public static String CONTACT_ID_OTH = "OTH";

	// Chormドライバー
	public static String DRIVER_URL = "C:\\chromedriver";

	// sqlファイル
	public static String SQL_FOLDER = "sql/";

	// 画面種別
	public static final Map<String, String> ID_TYPE = Map.of(
		"sc1000", "共通表示画面",
		"sc2000", "検索画面",
		"sc3000", "詳細画面",
		"sc4000", "未設定",
		"sc5000", "未設定",
		"sc6000", "未設定",
		"sc7000", "未設定",
		"sc8000", "未設定",
		"sc9000", "開発者用マスタ");
}
