
document.addEventListener("DOMContentLoaded", function() {
	const searchBox = document.getElementById("symbol");
	const resultsList = document.getElementById("results");

	var stockList = document.getElementById("allTickList").value;
	
	try {
	        // Java の HashMap.toString() 形式を正しい JSON に変換
	        var jsonString = stockList
	            .replace(/=/g, '":"')   // = を ":" に変換
	            .replace(/, /g, '", "') // , の後のスペースを '", "' に変換
	            .replace(/{/g, '{"')    // { を '{"' に変換
	            .replace(/}/g, '"}');   // } を '"}' に変換

	        console.log("Converted JSON String:", jsonString);

	        // JSON に変換
	        var rawStockList = JSON.parse(jsonString);
	        console.log("Parsed Data:", rawStockList);

	        var newStockList = Object.entries(rawStockList).map(([code, name]) => ({ code, name }));

	        console.log("Converted Data:", newStockList);
	    } catch (error) {
	        console.error("Parse Error:", error.message);
	        alert("データの形式が間違っています！");
	        return;
	    }

	const stockData = newStockList;
	
	// 入力時のイベントリスナー
	searchBox.addEventListener("input", function() {
		const query = this.value.trim();
		resultsList.innerHTML = "";

		if (query.length === 0) {
			return; // 何も入力されていなければ表示しない
		}

		// 検索結果のフィルタリング
		const filteredStocks = stockData.filter(stock =>
			stock.code.includes(query) || stock.name.includes(query)
		);

		// 結果の表示
		filteredStocks.forEach(stock => {
			const li = document.createElement("li");
			li.textContent = `${stock.name} (${stock.code})`;
			li.addEventListener("click", function() {
				searchBox.value = stock.name + ":" + stock.code;
				resultsList.innerHTML = "";
			});
			resultsList.appendChild(li);
		});
	});
});

