<?php




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
		echo "<br/>�� ���� ����������� � �������� ��� ������.<br/>";
		exit();
	}
	//echo "<br/>���������� � �������� ��� ������ ��������� �������.<br/>";
	if (!mysql_select_db($db_name,$link))
	{
		echo "<br/>�� ���� ������� ���� ������.<br/>";
		exit();
	}
	
    $result_array["serials"] = array();
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
	$season = $item['season'];
	$series = $item['series'];
	

	$action = intval($action."");
	$dateTime = intval($dateTime."");
	$dateRemember = intval($dateRemember."");
	$is_private = intval($is_private."");
	$user_id = intval($user_id."");
	$note_id = intval($note_id."");
	$is_saw_today = intval($is_saw_today."");
	$dateChange = intval($dateChange."");
	$mark = floatval($mark."");
	$season = intval($season."");
	$series = intval($series."");


	$query = "Insert into serials (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange, season, series) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange', '$season', '$series')";
		
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
	
	array_push($result_array["serials"], $response);
	
	}
	
	echo json_encode($result_array);
	
	mysql_close();


?>