CREATE TABLE IF NOT EXISTS Assets(
cik text NOT NULL,
confirmation_period DATE NOT NULL,
name text,
title text,
cusip text NOT NULL,
excel_cusip text,
cash_value integer,
num_shares integer,
type text,
discretion text,
submit_date date);


SELECT name FROM sqlite_master WHERE type='table' AND name='Assets';

