create package body test is

function test() return number is
	
begin
  nl_test := 'test';
  sl_test := 'test' || 4;
  sl_test := 'test' || 'john' || 'doe';
end test;

end test;