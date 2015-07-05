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
	//mysql_set_charset('utf8', $link);
	//mysql_set_charset('cp1251', $link);
	//mysql_query('SET names utf8');
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
	
    $result_array["serials"] = array();
	$value_json = $_POST['value_json'];
	
	$myArray = json_decode($value_json, true);
	
	foreach ( $myArray as $item)
	{		
	$note_id =  $item['item_id'];
	$note_id = intval($note_id."");



	//$query = "Insert into films (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange')";
		
		
		//$query = "Update films set name = '$name', mark = '$mark', action = '$action', dateTime = '$dateTime', dateRemember = '$dateRemember', image_url = '$image_url', comment = '$comment', image_path = '$image_path', is_private = '$is_private', user_id = '$user_id', note_id = '$note_id', is_saw_today = '$is_saw_today', dateChange = '$dateChange' where _id = '$note_id'"
	
	$query = "delete from serials where _id = '$note_id'";
	
	$result = mysql_query($query);
	
	$response = array();
	if ($result) 
	{
		$response["success"] = 1;
		$response["message"] = "Item successfully deleted.";	
	} else
	{
		$response["success"] = 0;
		$response["message"] = "Oops! An error occurred.";
	}
	
	array_push($result_array["serials"], $response);
	
	}
	
	echo json_encode($result_array);
	
	mysql_close();


?>