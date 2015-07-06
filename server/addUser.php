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
		echo "<br/>Ќе могу соединиться с сервером баз данных.<br/>";
		exit();
	}
	//echo "<br/>Соединение с сервером баз данных произошло успешно.<br/>";
	if (!mysql_select_db($db_name,$link))
	{
		echo "<br/>Не могу выбрать базу данных.<br/>";
		exit();
	}
	

	
    $name = urldecode( $_POST['name']);
	$lastName =  urldecode( $_POST['lastName']);

	$user_id =   urldecode( $_POST['user_id']);
	$image_url = urldecode(  $_POST['image_url']);
	
	//$name =  $_POST['name'];
	/*$name = urldecode( $_POST['name']);
	$lastName =   $_POST['lastName'];

	$user_id =    $_POST['user_id'];
	$image_url =   $_POST['image_url'];*/
	
	$user_id = intval($user_id."");
	
//echo " codirovka : ".mb_detect_encoding($name)."\n";	 
	//echo "name = ".$name."\n"."lastName = ".$lastName."\n";
		
	$result = mysql_query("Insert into users (name, lastName, user_id, image_url) values ('$name', '$lastName', '$user_id', '$image_url')");
	//echo "id = ".mysql_insert_id();
	if ($result) 
	{
		$response["success"] = 1;
		$response["message"] = "User successfully created.";
		$response["_id"] = mysql_insert_id();
		$response["name"] = $name;
		$response["lastName"] = $lastName;
	
		echo json_encode($response);
	} else
	{
		$response["success"] = 0;
		$response["message"] = "Oops! An error occurred.";
		$response["_id"] = -1;
	
		echo json_encode($response);
	}
	
	mysql_close();


?>