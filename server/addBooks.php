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
	
    $result_array["books"] = array();
	$value_json = $_POST['value_json'];
	
	$myArray = json_decode($value_json, true);
	
	foreach ( $myArray as $item)
	{
		$item_id = $item['_id'];
		$name = $item['name'];
	$mark = $item['mark'];
	$action =  $item['action'];
	$dateTime = $item['dateTime'];
	$dateRemember =  $item['dateRemember'];
	$image_url =  $item['image_url'];
	$comment = $item['comment'];
	$image_path =  $item['image_path'];
	$is_private =  $item['is_private'];
	$user_id =  $item['user_id'];
	$note_id =  $item['note_id'];
	$is_saw_today =  $item['is_saw_today'];
	$dateChange =  $item['dateChange'];
	$autor = $item['autor'];
	$page = $item['page'];
	

	$action = intval($action."");
	$dateTime = intval($dateTime."");
	$dateRemember = intval($dateRemember."");
	$is_private = intval($is_private."");
	$user_id = intval($user_id."");
	$note_id = intval($note_id."");
	$is_saw_today = intval($is_saw_today."");
	$dateChange = intval($dateChange."");
	$mark = floatval($mark."");
	$page = intval($page."");
	


	$query = "Insert into books (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange, autor, page) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange', '$autor', '$page')";
		
	$result = mysql_query($query);
	
	$response = array();
	if ($result) 
	{
		$response["success"] = 1;
		$response["message"] = "Item successfully created.";
		$response["_id"] = mysql_insert_id();
		$response["item_id"] = $item_id;	
	} else
	{
		$response["success"] = 0;
		$response["message"] = "Oops! An error occurred.";
		$response["_id"] = -1;

	}
	
	array_push($result_array["books"], $response);
	
	}
	
	echo json_encode($result_array);
	
	mysql_close();


?>