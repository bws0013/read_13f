select count(*) from Assets;

-- query to get every instance where more than one firm held a security at the same time
select *, count(*) as c from assets
   group by excel_cusip, confirmation_period having c > 1
   order by confirmation_period desc;