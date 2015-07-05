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

	
	$user_id =   urldecode( $_GET['user_id']);
	$user_id = intval($user_id."");
	
	$query = "select * from users where user_id = $user_id";
	
	$result = mysql_query($query) or die(mysql_error());

	if (mysql_num_rows($result) > 0) 
   {
      $response["users"] = array();
	   while ($row = mysql_fetch_array($result)) 
	      {
				$item = array();
				
				$item["_id"] = $row["_id"];
				$item["name"] = $row["name"];
				$item["lastName"] = $row["lastName"];
				$item["user_id"] = $row["user_id"];
				$item["friends"] = $row["friends"];
				$item["image_url"] = $row["image_url"];
				
				array_push($response["users"], $item);
		  }
		  
		  $query = "select * from films where user_id = $user_id order by dateChange desc";
		  
		  $result = mysql_query($query) or die(mysql_error());
		  
		  if (mysql_num_rows($result) > 0) 
			{
				$response["films"] = array();
 
				while ($row = mysql_fetch_array($result)) 
					{
						$item = array();
				
						$item["_id"] = $row["_id"];
						$item["name"] = $row["name"];
						$item["mark"] = $row["mark"];
						$item["action"] = $row["action"];
						$item["dateTime"] = $row["dateTime"];
						$item["dateRemember"] = $row["dateRemember"];
						$item["image_url"] = $row["image_url"];
						$item["comment"] = $row["comment"];
						$item["image_path"] = $row["image_path"];
						$item["is_private"] = $row["is_private"];
						$item["user_id"] = $row["user_id"];
						$item["note_id"] = $row["note_id"];
						$item["is_saw_today"] = $row["is_saw_today"];
						$item["dateChange"] = $row["dateChange"];
				
						array_push($response["films"], $item);
					}
			} 
			else
			{
				$response["success"] = 0;
				$response["message"] = "No films found";
			}
			
			 $query = "select * from serials where user_id = $user_id order by dateChange desc";
		  
		     $result = mysql_query($query) or die(mysql_error());
			 
			  if (mysql_num_rows($result) > 0) 
				{
					$response["serials"] = array();
 
					while ($row = mysql_fetch_array($result)) 
						{
							$item = array();
				
							$item["_id"] = $row["_id"];
							$item["name"] = $row["name"];
							$item["mark"] = $row["mark"];
							$item["action"] = $row["action"];
							$item["dateTime"] = $row["dateTime"];
							$item["dateRemember"] = $row["dateRemember"];
							$item["image_url"] = $row["image_url"];
							$item["comment"] = $row["comment"];
							$item["image_path"] = $row["image_path"];
							$item["is_private"] = $row["is_private"];
							$item["user_id"] = $row["user_id"];
							$item["note_id"] = $row["note_id"];
							$item["is_saw_today"] = $row["is_saw_today"];
							$item["dateChange"] = $row["dateChange"];
							$item["season"] = $row["season"];
							$item["series"] = $row["series"];
				
							array_push($response["serials"], $item);
						}
				} 
				else
				{
					$response["success"] = 0;
					$response["message"] = "No serials found";
				}
				
			 $query = "select * from books where user_id = $user_id order by dateChange desc";
		  
		     $result = mysql_query($query) or die(mysql_error());
			 
			 if (mysql_num_rows($result) > 0) 
				{
					$response["books"] = array();
 
					while ($row = mysql_fetch_array($result)) 
						{
							$item = array();
				
							$item["_id"] = $row["_id"];
							$item["name"] = $row["name"];
							$item["mark"] = $row["mark"];
							$item["action"] = $row["action"];
							$item["dateTime"] = $row["dateTime"];
							$item["dateRemember"] = $row["dateRemember"];
							$item["image_url"] = $row["image_url"];
							$item["comment"] = $row["comment"];
							$item["image_path"] = $row["image_path"];
							$item["is_private"] = $row["is_private"];
							$item["user_id"] = $row["user_id"];
							$item["note_id"] = $row["note_id"];
							$item["is_saw_today"] = $row["is_saw_today"];
							$item["dateChange"] = $row["dateChange"];
							$item["autor"] = $row["autor"];
							$item["page"] = $row["page"];

							array_push($response["books"], $item);
						}
				} 
				else
				{
					$response["success"] = 0;
					$response["message"] = "No books found";
				}
   } 
   else
 {
    $response["success"] = 0;
    $response["message"] = "No users found";
   
  }
  
  
  
	 echo json_encode($response);
	mysql_close();


?>