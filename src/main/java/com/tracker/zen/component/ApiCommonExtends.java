package com.tracker.zen.component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ApiCommonExtends {

	private static final String EXCEL_URL = "https://www.jpx.co.jp/markets/statistics-equities/misc/tvdivq0000001vg2-att/data_j.xls";

	// プライム市場
	private final String JAPAN_TICK = "（内国株式）";

	public HashMap<String, String> getAllTickList() {
		HashMap<String, String> tickList = new HashMap<>();
		try (InputStream inputStream = new URL(EXCEL_URL).openStream();
			Workbook workbook = WorkbookFactory.create(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0); // 最初のシートを取得

			for (Row row : sheet) {

				if (row.getRowNum() == 0)
					continue; // ヘッダーをスキップ

				Cell codeCell = row.getCell(1); // B列（銘柄コード）
				Cell nameCell = row.getCell(2); // C列（銘柄名）
				Cell kbn = row.getCell(3); // C列（銘柄名

				if (codeCell != null && nameCell != null) {
					String tickKbn = getCellValue(kbn);

					if (tickKbn.contains(JAPAN_TICK))
						tickList.put(getCellValue(codeCell), getCellValue(nameCell));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tickList;
	}

	private String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case NUMERIC:
			return String.valueOf((int) cell.getNumericCellValue());
		default:
			return "";
		}
	}
}
