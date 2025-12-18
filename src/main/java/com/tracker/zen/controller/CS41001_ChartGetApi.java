package com.tracker.zen.controller;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tracker.zen.component.LogModuleInfo;
import com.tracker.zen.service.TradeOrdersService;

@RestController
@RequestMapping("/api")
public class CS41001_ChartGetApi {

	@Autowired
	private TradeOrdersService stockService;

	private final RestTemplate restTemplate = new RestTemplate();

	/** Yahoo Finance API ベースURL */
	private final String BASE_URL = "https://query2.finance.yahoo.com/v8/finance/chart/";

	/** 取得日数 */
	private static final Map<String, String> INTERVAL_TO_DAYS = Map.ofEntries(
		Map.entry("1m", "1d"),
		Map.entry("2m", "1d"),
		Map.entry("5m", "3d"),
		Map.entry("15m", "5d"),
		Map.entry("30m", "10d"),
		Map.entry("60m", "30d"),
		Map.entry("90m", "5d"),
		Map.entry("1d", "1m"),
		Map.entry("1h", "1y"),
		Map.entry("5d", "2y"),
		Map.entry("1wk", "5y"),
		Map.entry("1mo", "10y"),
		Map.entry("3mo", "10y"));

	/**
	 * 内部DBからの株価データ取得
	 */
	@GetMapping("/stock-data")
	public List<Map<String, Object>> getStockData(@RequestParam String ticker) {
		return stockService.getStockData(ticker);
	}

	/**
	 * Yahoo Finance API から指定した銘柄の株価データを取得するエンドポイント
	 * 
	 * @param ticker    取得する銘柄コード（例: "7203" → "7203.T"）
	 * @param interval  時間足の間隔（例: "1m", "5m", "1h", "1d", "1wk" など）
	 * @param startDate （オプション）取得開始日（フォーマット: "YYYY-MM-DD"）
	 * @param endDate   （オプション）取得終了日（フォーマット: "YYYY-MM-DD"）
	 * @return          Yahoo Finance API から取得した株価データの JSON レスポンス
	 */
	@GetMapping("/external-stock-data")
	public ResponseEntity<?> getExternalStockData(
		@RequestParam String ticker,
		@RequestParam String interval,
		@RequestParam(required = false) String startDate,
		@RequestParam(required = false) String endDate) {

		// 取得年数を設定
		String range = INTERVAL_TO_DAYS.getOrDefault(interval, "2y");

		// 現在の時刻（UTC）
		ZonedDateTime now = Instant.now().atZone(ZoneId.of("UTC"));

		HttpHeaders headers = new HttpHeaders();
		headers.set("User-Agent", "Mozilla/5.0");
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		// 各足事にチャートの初期値を作成
		ZonedDateTime defaultStartDate = switch (range) {
		case "1d" -> now.minusDays(1);
		case "3d" -> now.minusDays(3);
		case "5d" -> now.minusDays(5);
		case "10d" -> now.minusDays(10);
		case "30d" -> now.minusDays(30);
		case "1m" -> now.minusYears(1);
		case "1y" -> now.minusYears(1);
		case "2y" -> now.minusYears(2);
		case "5y" -> now.minusYears(5);
		case "10y" -> now.minusYears(10);
		default -> now.minusYears(2); // デフォルト2年間
		};

		// 開始日
		long period1 = defaultStartDate.toInstant().getEpochSecond();
		if (startDate != null && !startDate.isEmpty()) {
			period1 = ZonedDateTime.parse(startDate + "T00:00:00Z")
				.toInstant()
				.getEpochSecond();
		}

		// 終了日（現在時刻）
		long period2 = now.toInstant().getEpochSecond();
		if (endDate != null && !endDate.isEmpty()) {
			period2 = ZonedDateTime.parse(endDate + "T23:59:59Z")
				.toInstant()
				.getEpochSecond();
		}

		// 株式分割・配当イベントを含める
		String events = "split%7Cdiv";

		// Yahoo Finance の API URL を作成
		String apiUrl = String.format("%s%s.T?period1=%d&period2=%d&interval=%s&events=%s",
			BASE_URL, ticker, period1, period2, interval, events);

		// 表示URL
		LogModuleInfo.sendMessageLog("URL:" + apiUrl);

		try {
			int retryCount = 0;
			int maxRetries = 1; // 最大リトライ回数

			while (retryCount < maxRetries) {
				// API リクエストの送信
				ResponseEntity<Map<String, Object>> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {
				});

				System.out.println("Yahoo API Response: " + response.getBody()); // ログ出力

				// ステータスコードが 200 (OK) 以外ならエラーとして処理
				if (response.getStatusCode() != HttpStatus.OK) {
					return ResponseEntity.status(response.getStatusCode()).body("📌 API リクエスト失敗: " + response.getStatusCode());
				}

				// レスポンスが null または "chart" キーがない場合
				if (response.getBody() == null || !response.getBody().containsKey("chart")) {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("📌 データが見つかりません: " + ticker);
				}

				// レスポンス結果を送信
				return ResponseEntity.ok(response.getBody());
			}

		}

		// レスポンス回数制限
		catch (HttpClientErrorException.TooManyRequests e) {
			e.printStackTrace();
			LogModuleInfo.sendMessageLog("レスポンスの回数制限：エラーコード(429)");
		}

		// その他エラー
		catch (Exception e) {
			LogModuleInfo.sendMessageLog("------- 例外発生 ------");
			e.printStackTrace();
		}
		return null;
	}

	@GetMapping("/getChart")
	public String getChart(@RequestParam String searchTickName, @RequestParam(defaultValue = "2024-04-01") String date) {

		// 入力値から数字のみを抽出し、銘柄番号を取得する
		String tickId = searchTickName.replaceAll("\\D+", "");

		// ティックがない場合
		if (tickId.isEmpty()) {
			return null;
		}

		// `ChromeDriver` のパス設定
		String chromeDriverPath = "C:\\chromedriver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		// `ChromeDriver` の `Service` を明示的に指定 (プロセス最適化)
		ChromeDriverService service = new ChromeDriverService.Builder()
			.usingDriverExecutable(new File(chromeDriverPath))
			.build();

		// Chrome の最適化オプション
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--blink-settings=imagesEnabled=false");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-popup-blocking");
		options.addArguments("--start-maximized");
		options.addArguments("--disable-background-networking");
		options.addArguments("--disable-features=site-per-process");

		WebDriver driver = new ChromeDriver(service, options);

		try {
			String url = "https://japan-kabuka.com/chart/?id=" + tickId + "&candledate=" + date;
			driver.get(url);

			// `WebDriverWait` を使って `#chart` の要素が表示されるまで待機 (最大5秒)
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			WebElement chartDiv = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("chart")));

			return (chartDiv != null) ? chartDiv.getAttribute("outerHTML") : "<p>チャートが見つかりません</p>";

		} catch (Exception e) {
			return "<p>エラー: " + e.getMessage() + "</p>";
		} finally {
			driver.quit(); // `WebDriver` を確実に終了
		}
	}
}
