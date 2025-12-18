package com.tracker.zen.component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import com.tracker.zen.constats.CommonConstats;

@Component
public class CommonExtends {

	@Autowired
	private UrlPropertiesConfig urlPropertiesConfig;

	/**
	 * 現在のスレッドのスタックトレースから呼び出し元のクラス名とメソッド名を取得します。
	 * 
	 * @return インデックス0がクラス名、インデックス1がメソッド名です。
	 */
	public String[] getCallerClassNameAndMethodName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return new String[] {
			stackTrace[3].getClassName(), stackTrace[3].getMethodName() };
	}

	// SQLファイルをクラスパスから読み込むメソッド
	public String loadSqlFromResource(String fileName) {
		try {
			// SQLファイルのパスを指定してClassPathResourceを作成
			Resource resource = new ClassPathResource("static/sql/" + fileName + ".sql");

			// InputStreamReaderを使ってリソースを読み込み
			try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
				// FileCopyUtilsを使ってReaderから文字列に変換
				return FileCopyUtils.copyToString(reader);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read SQL file: " + fileName, e);
		}
	}

	// プロパティファイルからURLを取得して返す。
	public String getTransitionURL(HttpServletRequest request, String scId) {
		String fullPath = request.getRequestURI();
		String path = fullPath.substring(fullPath.lastIndexOf("/") + 1);
		String finalpath = scId + CommonConstats.URL_PERIOD + path;
		String transitionURL = urlPropertiesConfig.getUrl(finalpath);
		return transitionURL;
	}

	// 接続先を指定
	public String getServerReq(HttpServletRequest request) {

		// リクエストからホスト部分を取得
		String scheme = request.getScheme(); // "https"
		String serverName = request.getServerName(); // "kuyu0903.com"
		int serverPort = request.getServerPort(); // ポートが指定されていなければ -1 が返る

		// URLを構成（デフォルトポートの場合、ポート番号は省略）
		String baseUrl;
		if (serverPort == 80 || serverPort == 443 || serverPort == -1) {
			baseUrl = scheme + "://" + serverName + "/";
		} else {
			baseUrl = scheme + "://" + serverName + ":" + serverPort + "/";
		}
		return baseUrl;

	}

}
