<?php
// Это для сервака
/*mysql_connect("sql103.byethost7.com","b7_16207375","byethostwm16") or  die(mysql_error());
mysql_select_db("b7_16207375_funny_time");*/
/*mysql_connect("localhost","root","");
mysql_select_db("b7_16207375_funny_time")*/



	$sdb_name = "";
	$user_name = "";
	$user_password = "";
	$db_name = "b7_16207375_funny_time";
	
	$link = mysql_connect($sdb_name,$user_name,$user_password);
	
	mysql_query('SET names utf8');
	mysql_query('SET CHARACTER SET utf8');
	
	if (!$link)
	{
		echo "<br/>Не могу соединитьс€ с сервером баз данных.<br/>";
		exit();
	}
	//echo "<br/>—оединение с сервером баз данных произошло успешно.<br/>";
	if (!mysql_select_db($db_name,$link))
	{
		echo "<br/>Ќе могу выбрать базу данных.<br/>";
		exit();
	}
	

	$result = mysql_query("SELECT *FROM users") or die(mysql_error());
	
   if (mysql_num_rows($result) > 0) 
   {
        $response["users"] = array();
 
        while ($row = mysql_fetch_array($result)) 
	      {
				$users = array();
				
				$users["_id"] = $row["_id"];
				$users["name"] = $row["name"];
				$users["lastName"] = $row["lastName"];
				$users["user_id"] = $row["user_id"];
				$users["image_url"] = $row["image_url"];
				
				array_push($response["users"], $users);
		  }
 
    echo json_encode($response);
   } 
   else
 {
    $response["success"] = 0;
    $response["message"] = "No products found";
 
    echo json_encode($response);
}
	
	
	
		
	
	
	mysql_close();


?>