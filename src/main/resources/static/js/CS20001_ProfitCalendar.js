let currentDate = new Date();
let profitLossData = {};

async function fetchProfitLossData() {
	const response = await fetch("/profit-calculation"); // Spring Boot API からデータ取得
	profitLossData = await response.json();
	renderCalendar(currentDate);
}

function renderCalendar(date) {
	const year = date.getFullYear();
	const month = date.getMonth();
	document.getElementById('currentMonth').innerText = `${year}年 ${month + 1}月`;

	const firstDay = new Date(year, month, 1).getDay();
	const lastDate = new Date(year, month + 1, 0).getDate();
	let dayCount = 1;

	const tbody = document.querySelector("#calendar tbody");
	tbody.innerHTML = "";

	// 今日の日付を取得（YYYY-MM-DD 形式）
	const today = new Date();
	const todayStr = `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`;

	for (let row = 0; row < 6; row++) {
		let tr = document.createElement("tr");

		for (let col = 0; col < 7; col++) {
			let td = document.createElement("td");

			// 該当月初日より前の曜日
			if (row === 0 && col < firstDay) {
				td.innerText = "";
			}
			// 該当月末日より前の曜日
			else if (dayCount > lastDate) {
				td.innerText = "";
			}
			// 日付ごとに処理を行う
			else {
				const fullDate = `${year}-${String(month + 1).padStart(2, '0')}-${String(dayCount).padStart(2, '0')}`;

				// 今日の日付ならクラスを追加
				if (fullDate === todayStr) {
					td.classList.add("today");
				}

				// 日付をURLにする
				td.innerHTML = `<a href="profit-detail?date=${fullDate}" class="calendar-link">${dayCount}</a>`;

				// 該当日に取引履歴がある場合
				if (profitLossData[fullDate] !== undefined) {
					const amount = profitLossData[fullDate];
					td.innerHTML += `<br><span class="${amount > 0 ? 'profit' : 'loss'}">${amount.toLocaleString()}円</span>`;
				}
				// 該当日に取引履歴がない場合
				else {
					if (fullDate <= todayStr) {
						td.innerHTML += `<br><span class="no-trade">取引なし</span>`;
					}
				}

				dayCount++;
			}
			tr.appendChild(td);
		}

		tbody.appendChild(tr);
		if (dayCount > lastDate) break;
	}
}

function prevMonth() {
	currentDate.setMonth(currentDate.getMonth() - 1);
	renderCalendar(currentDate);
}

function nextMonth() {
	currentDate.setMonth(currentDate.getMonth() + 1);
	renderCalendar(currentDate);
}

fetchProfitLossData();
