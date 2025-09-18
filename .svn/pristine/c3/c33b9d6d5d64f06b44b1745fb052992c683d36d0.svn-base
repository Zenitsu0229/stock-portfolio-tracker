// 時間足（初期表示は1日足）
let currentInterval = '1d';
let stockChart = null; // チャートインスタンス（初期はnull）

// Yahoo API がサポートする有効な `interval` 値
const VALID_INTERVALS = ['1m', '2m', '5m', '15m', '30m', '60m', '90m', '1h', '1d', '5d', '1wk', '1mo', '3mo'];

// 分足のリスト（短期間のデータ用）
const MINUTE_INTERVALS = ['1m', '2m', '5m', '15m', '30m', '60m', '90m', '1h'];

// 期間変更時のイベント
function updateInterval(interval) {
	// Yahoo API で無効な `interval` の場合は処理を停止
	if (!VALID_INTERVALS.includes(interval)) {
		console.error(`📌 無効な interval: ${interval}`);
		alert(`無効な interval: ${interval}。\n有効な値: ${VALID_INTERVALS.join(', ')}`);
		return;
	}

	currentInterval = interval;
	fetchStockData();
}

async function fetchStockData() {

	// 陽線
	var chartUpColor = '#FFA500';
	// 陰線
	var chartDownColor = '#FFFFFF';
	// ローソク枠線
	var tickBorderColor = '#c0c0c0';
	// 出来高
	var volumeColor = '#807a4a';

	// 銘柄コード取得
	var code = document.getElementById("tickerCode").value;
	
	// コードがからの場合は処理を終了する
	if (code == null || code == "") {
		return;
	}
	
	try {
		const url = `http://localhost:8080/api/external-stock-data?ticker=${encodeURIComponent(code)}&interval=${currentInterval}`;
		const response = await fetch(url);

		// エラーの場合
		if (!response.ok) {
			const errorMessage = await response.text();
			console.error(`📌 APIエラー: ${response.status} - ${errorMessage}`);
			alert(`📌 データ取得エラー: ${response.status}`);
			return;
		}

		// 取得結果
		const data = await response.json();

		// 株価データ
		const quotes = data.chart.result[0].indicators.quote[0];

		// 出来高データの配列
		const volumes = data.chart.result[0].indicators.quote[0].volume;

		// ローソク足チャート
		const filteredData = [];

		// 出来高データチャート
		const volumeData = [];

		// 時間軸
		const labels = [];

		data.chart.result[0].timestamp.forEach((t, i) => {

			//日付
			let date = new Date(t * 1000);
			let hours = date.getHours();
			let minutes = date.getMinutes();

			// ⏰ 11:30 〜 12:30 のデータはスキップ（分足のみ）
			if (MINUTE_INTERVALS.includes(currentInterval) && (hours === 11 && minutes >= 30 || hours === 12)) {
				return; // 何もしないでスキップ
			}

			// X軸ラベルのフォーマット
			let formattedDate;

			if (MINUTE_INTERVALS.includes(currentInterval)) {
				formattedDate = new Intl.DateTimeFormat('ja-JP', {
					year: 'numeric', month: '2-digit', day: '2-digit',
					hour: '2-digit', minute: '2-digit', second: '2-digit'
				}).format(date);
			} else {
				formattedDate = new Intl.DateTimeFormat('ja-JP', { year: 'numeric', month: '2-digit', day: '2-digit' }).format(date);
			}


			// 株価チャート
			filteredData.push({
				x: formattedDate,
				o: quotes.open[i],
				h: quotes.high[i],
				l: quotes.low[i],
				c: quotes.close[i]
			});

			// 出来高チャート
			volumeData.push({
				x: formattedDate,
				y: volumes[i],
				backgroundColor: '#ffff00'
			});
			labels.push(formattedDate);
		});

		// 価格データをフィルタリングして、不正な値を除外
		const validLows = quotes.low.filter(value => value !== null && value !== undefined && value > 0);
		const validHighs = quotes.high.filter(value => value !== null && value !== undefined && value > 0);

		// フィルタ後に値があるか確認（すべてのデータが無効ならデフォルト値を設定）
		let minPrice = validLows.length > 0 ? Math.min(...validLows) : 100; // デフォルト100
		let maxPrice = validHighs.length > 0 ? Math.max(...validHighs) : 200; // デフォルト200

		// 分足の場合の調整（適切な倍率をかける）
		if (MINUTE_INTERVALS.includes(currentInterval)) {
			minPrice *= 0.98;  // 最小値を 2% 低く設定
			maxPrice *= 1.02;  // 最大値を 2% 高く設定
		} else {
			minPrice -= 50;
			maxPrice += 50;
		}

		// 出来高の最大値（少し余裕を持たせる）
		const maxVolume = Math.max(...volumes) * 1.2;


		// 既存のチャートがある場合は破棄（重複防止）
		if (stockChart !== null) {
			stockChart.destroy();
		}

		// ③ チャートの描画
		const ctx = document.getElementById('stockChart').getContext('2d');
		stockChart = new Chart(ctx, {
			type: 'candlestick',
			data: {
				labels: labels,
				datasets: [
					{
						label: `株価 (${currentInterval})`,
						data: filteredData,
						borderColor: tickBorderColor,
						borderWidth: 0.5,
						color: {
							up: chartUpColor,
							down: chartDownColor,
							unchanged: chartUpColor
						},
						yAxisID: 'price'
					},
					{
						type: 'bar',
						label: '出来高',
						data: volumeData,
						yAxisID: 'volume',
						barThickness: 'flex',
						backgroundColor: volumeColor,
						categoryPercentage: 1.0,
						barPercentage: 0.5
					}
				]
			},
			options: {
				responsive: true,
				scales: {
					x: {
						type: 'category',
						labels: labels,
						ticks: {
							color: 'white',
							maxTicksLimit: MINUTE_INTERVALS.includes(currentInterval) ? 10 : 20
						}
					},
					price: {
						position: 'left',
						ticks: {
							color: 'white',
							stepSize: 50
						},
						min: minPrice,
						max: maxPrice
					},
					volume: {
						position: 'right',
						ticks: {
							display: false,
							stepSize: maxVolume / 5
						},
						grid: { drawTicks: false, drawOnChartArea: false },
						min: 0,
						max: maxVolume
					}
				}
			},
		});

	} catch (error) {
		alert(`📌 システムエラー: ${error.message}`);
	}
}

// ④ 初期データ取得
fetchStockData();
