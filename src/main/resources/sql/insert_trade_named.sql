INSERT INTO trade_diary (
    trade_date, symbol, side, entry_reason, rule_observance,
    emotion, profit_loss, comment, created_at
) VALUES (
    :tradeDate, :symbol, :side, :entryReason, :ruleObservance,
    :emotion, :profitLoss, :comment, :createdAt
);
