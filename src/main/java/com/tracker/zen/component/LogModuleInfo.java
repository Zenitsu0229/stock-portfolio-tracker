package com.tracker.zen.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogModuleInfo {

	// 処理開始
	private String START_MSG001 = "開始 クラス：THIS_CLASS メソッド：THIS_METHOD";

	// SQL実施クラス・メソッド名
	private String SQL_MSG001 = "完了 件数：THIS_RESULT クラス：THIS_CLASS メソッド：THIS_METHOD";

	// URL移動
	private String URL_MOVE = "URL：THIS_URL に遷移します";

	// 下線
	private String SEN_TOOL = "-----------------------------------------------------------------------------------";

	@Autowired
	CommonExtends commonExtends = new CommonExtends();

	// 開始ログ
	public void LOGIC_START(String classNmae, String methodName) {

		// 30文字でフォーマット
		classNmae = String.format("%-30s", classNmae);

		// 30文字でフォーマット
		methodName = String.format("%-30s", methodName);

		// クラス・メソッドを置換する
		String outInfoLog = START_MSG001.replace("THIS_CLASS", classNmae);
		outInfoLog = outInfoLog.replace("THIS_METHOD", methodName);

		// 開始ログを出力
		log.info(outInfoLog);
	}

	// URL遷移
	public void URL_MOVE(String url) {

		// クラス・メソッドを置換する
		String outInfoLog = URL_MOVE.replace("THIS_URL", url);

		// 終了ログを出力
		log.info(SEN_TOOL);
		log.info(outInfoLog);
		log.info(SEN_TOOL);
	}

	// SQL実行結果
	public void SQL_RESULT(int result) {

		// クラス名を取得する
		String className = commonExtends.getCallerClassNameAndMethodName()[0];

		// 呼び出し元メソッド名を取得する
		String methodName = commonExtends.getCallerClassNameAndMethodName()[1];

		// クラス・メソッドを置換する
		String sqlResult = SQL_MSG001.replace("THIS_CLASS", className);
		sqlResult = sqlResult.replace("THIS_METHOD", methodName);
		sqlResult = sqlResult.replace("THIS_RESULT", String.valueOf(result));

		//ログ出力
		log.info(sqlResult);
	}

	// メッセージログ
	public static void sendMessageLog(String message) {
		log.info(message);
	}
}
