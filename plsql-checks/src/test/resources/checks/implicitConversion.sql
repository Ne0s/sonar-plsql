CREATE OR REPLACE PACKAGE BODY test IS

function ok() return NUMBER IS

BEGIN
 nl_err := 'wrong';
 sl_right := 'right' ;
 sl_err2 := 'wrong2'||4;
 sl_err2 := 'wrong2'||'test2'||4;
 nl_err := 5 + '0';
 
 SELECT 'test' || 5 as myErr from dual
 WHERE '6' = '6' 
 AND '7' = 7;
 
 IF ui_invoice_no IS NULL OR ui_accttran IS NULL THEN
   return 5;
 end if;  

END OK;

end TEST;