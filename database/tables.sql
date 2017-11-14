CREATE TABLE Assets (
 cik text NOT NULL,
 confirmation_period DATE NOT NULL,
 name text NOT NULL,
 title text NOT NULL,
 cusip text NOT NULL,
 excel_cusip text NOT NULL,
 cash_value integer NOT NULL,
 num_shares integer NOT NULL,
 type text NOT NULL,
 discretion text NOT NULL,
 PRIMARY KEY (cik, confirmation_period)
);


SELECT name FROM sqlite_master WHERE type='table' AND name='Assets';

CREATE TABLE IF NOT EXISTS Assets (
 cik text NOT NULL,
 confirmation_period DATE NOT NULL,
 name text,
 title text,
 cusip text NOT NULL,
 excel_cusip text,
 cash_value integer,
 num_shares integer,
 type text,
 discretion,
 PRIMARY KEY (cik, confirmation_period, cusip)
);


