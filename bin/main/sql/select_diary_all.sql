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
ORDER BY 
symbol,
trade_date,
id