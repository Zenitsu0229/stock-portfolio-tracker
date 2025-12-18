package com.tracker.zen.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.tracker.zen.Entity.TradeDiaryEntity;
import com.tracker.zen.constats.CommonConstats;

@Service
public class TradeDiaryService {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	/*
	 * 入力画面に記載された内容をDBに登録する。
	 * 
	 */
	public void executeInsert(TradeDiaryEntity tradeDiary) {
		try {
			// SQLファイルの読み込み
			ClassPathResource resource = new ClassPathResource(CommonConstats.SQL_FOLDER + "insert_trade_named.sql");
			String sql = Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);

			// パラメータバインド
			MapSqlParameterSource params = new MapSqlParameterSource(Map.of(
				"tradeDate", tradeDiary.getTradeDate(),
				"symbol", tradeDiary.getSymbol(),
				"side", tradeDiary.getSide(),
				"entryReason", tradeDiary.getEntryReason(),
				"ruleObservance", tradeDiary.getRuleObservance(),
				"emotion", tradeDiary.getEmotion(),
				"profitLoss", tradeDiary.getProfitLoss(),
				"comment", tradeDiary.getComment(),
				"createdAt", LocalDateTime.now()));

			jdbcTemplate.update(sql, params);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 今日登録された記録を取得する
	 * 
	 */
	public List<TradeDiaryEntity> todaySearchDiary() {

		try {
			// SQLファイルの読み込み
			ClassPathResource resource = new ClassPathResource(CommonConstats.SQL_FOLDER + "select_trade_today.sql");
			String sql;
			try (InputStream is = resource.getInputStream()) {
				sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			}

			// パラメータ（LocalDateで日付のみ比較）
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("tradeDate", LocalDate.now());

			// クエリ実行（RowMapperを使ってマッピング）
			return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(TradeDiaryEntity.class));

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList(); // 空リスト返却
		}
	}

	/*
	 * 全件取得
	 * 
	 */
	public List<TradeDiaryEntity> diarySelectMonth() {

		try {
			// SQLファイルの読み込み
			ClassPathResource resource = new ClassPathResource(CommonConstats.SQL_FOLDER + "select_diary_all.sql");
			String sql;
			try (InputStream is = resource.getInputStream()) {
				sql = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			}

			// パラメータ
			MapSqlParameterSource params = new MapSqlParameterSource();

			// クエリ実行（RowMapperを使ってマッピング）
			return jdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(TradeDiaryEntity.class));

		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList(); // 空リスト返却
		}
	}

}
