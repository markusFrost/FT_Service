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
	

	
    $name = urldecode( $_POST['name']);
	$mark = urldecode( $_POST['mark']);
	$action = urldecode( $_POST['action']);
	$dateTime = urldecode( $_POST['dateTime']);
	$dateRemember = urldecode( $_POST['dateRemember']);
	$image_url = urldecode( $_POST['image_url']);
	$comment = urldecode( $_POST['comment']);
	$image_path = urldecode( $_POST['image_path']);
	$is_private = urldecode( $_POST['is_private']);
	$user_id = urldecode( $_POST['user_id']);
	$note_id = urldecode( $_POST['note_id']);
	$is_saw_today = urldecode( $_POST['is_saw_today']);
	$dateChange = urldecode( $_POST['dateChange']);
	
	
	$action = intval($action."");
	$dateTime = intval($dateTime."");
	$dateRemember = intval($dateRemember."");
	$is_private = intval($is_private."");
	$user_id = intval($user_id."");
	$note_id = intval($note_id."");
	$is_saw_today = intval($is_saw_today."");
	$dateChange = intval($dateChange."");
	$mark = floatval($mark."");


	$query = "Insert into films (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange')";
		
	$result = mysql_query($query);
	
	if ($result) 
	{
		$response["success"] = 1;
		$response["message"] = "User successfully created.";
		$response["_id"] = mysql_insert_id();
		$response["name"] = $name;
		//$response["query"] = $query;
	
		echo json_encode($response);
	} else
	{
		$response["success"] = 0;
		$response["message"] = "Oops! An error occurred.";
		$response["_id"] = -1;
		//$response["query"] = $query;
	
		echo json_encode($response);
	}
	
	mysql_close();


?>