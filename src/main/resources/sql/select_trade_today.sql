SELECT
    id,
    trade_date,
    symbol,
    side,
    entry_reason,
    rule_observance,
    emotion,
    profit_loss,
    comment,
    created_at
FROM
    trade_diary
WHERE
    trade_date >= CURDATE()
  AND trade_date < DATE_ADD(CURDATE(), INTERVAL 1 DAY)
ORDER BY
    trade_date DESC
