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
	$lastName =  urldecode( $_POST['lastName']);

	$user_id =   urldecode( $_POST['user_id']);
	$image_url = urldecode(  $_POST['image_url']);
	
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
			 // $query = "select * from my_books where user_id = 0 order by dateChange desc";
		  
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
		$result = mysql_query("Insert into users (name, lastName, user_id, image_url) values ('$name', '$lastName', '$user_id', '$image_url')");
		if ($result) 
			{
				$response["success"] = 1;
				$response["message"] = "User successfully created.";
				$response["_id"] = mysql_insert_id();	
			}  
	}
	
	$value_json = $_POST['value_json'];
	
	$data = json_decode($value_json);
foreach ($data as $key_name => $value) 
{
    
	
	if ( $key_name == 'films_add')
	{		
        $response[$key_name] = array();
		foreach ($value as $item) 
			{
				
				//echo '  ' . $item->name . '<br/>';
				//echo '  ' .  $item['name'] . '<br/>';
				$name = $item->name;
				$mark = $item->mark;
				$action =  $item->action;
				$dateTime = $item->dateTime;
				$dateRemember =  $item->dateRemember;
				$image_url =  $item->image_url;
				$comment = $item->comment;
				$image_path =  $item->image_path;
				$is_private =  $item->is_private;
				$user_id =  $item->user_id;
				$note_id =  $item->note_id;
				$is_saw_today =  $item->is_saw_today;
				$dateChange =  $item->dateChange;
				$item_id = $item->_id;
				
				$action = intval($action."");
				$dateTime = intval($dateTime."");
				$dateRemember = intval($dateRemember."");
				$is_private = intval($is_private."");
				$user_id = intval($user_id."");
				$note_id = intval($note_id."");
				$is_saw_today = intval($is_saw_today."");
				$dateChange = intval($dateChange."");
				$mark = floatval($mark."");
				$user_id = intval($user_id."");
				
				$query = "Insert into films (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange')";
				$result = mysql_query($query);
				$response1 = array();
				if ($result) 
					{
						$response1["success"] = 1;
						$response1["message"] = "Item successfully created.";
						$response1["_id"] = mysql_insert_id();
						$response1["item_id"] = $item_id;
	
		
					} else
					{
						$response1["success"] = 0;
						$response1["message"] = "Oops! An error occurred.";
						$response1["_id"] = -1;

					}
	
					array_push($response[$key_name], $response1);					
	        }
    }
	 if ( $key_name == 'serials_add')
	{
		 $response[$key_name] = array();
		 foreach ($value as $item) 
			{
				$name = $item->name;
				$mark = $item->mark;
				$action =  $item->action;
				$dateTime = $item->dateTime;
				$dateRemember =  $item->dateRemember;
				$image_url =  $item->image_url;
				$comment = $item->comment;
				$image_path =  $item->image_path;
				$is_private =  $item->is_private;
				$user_id =  $item->user_id;
				$note_id =  $item->note_id;
				$is_saw_today =  $item->is_saw_today;
				$dateChange =  $item->dateChange;
				$item_id = $item->_id;
				$season = $item->season;
				$series = $item->series;
				
				$action = intval($action."");
				$dateTime = intval($dateTime."");
				$dateRemember = intval($dateRemember."");
				$is_private = intval($is_private."");
				$user_id = intval($user_id."");
				$note_id = intval($note_id."");
				$is_saw_today = intval($is_saw_today."");
				$dateChange = intval($dateChange."");
				$mark = floatval($mark."");
                $user_id = intval($user_id."");
				$season = intval($season."");
				$series = intval($series."");
				
				$query = "Insert into serials (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange, season, series) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange', '$season', '$series')";
			
				$result = mysql_query($query);
	
				$response1 = array();
				if ($result) 
					{
						$response1["success"] = 1;
						$response1["message"] = "Item successfully created.";
						$response1["_id"] = mysql_insert_id();
						$response1["item_id"] = $item_id;	
					} else
					{
						$response1["success"] = 0;
						$response1["message"] = "Oops! An error occurred.";
						$response1["_id"] = -1;

					}
	
				array_push($response[$key_name], $response1);
			}
	}
	 if ( $key_name == 'books_add')
	{
		 $response[$key_name] = array();
		 foreach ($value as $item) 
			{
				$name = $item->name;
				$mark = $item->mark;
				$action =  $item->action;
				$dateTime = $item->dateTime;
				$dateRemember =  $item->dateRemember;
				$image_url =  $item->image_url;
				$comment = $item->comment;
				$image_path =  $item->image_path;
				$is_private =  $item->is_private;
				$user_id =  $item->user_id;
				$note_id =  $item->note_id;
				$is_saw_today =  $item->is_saw_today;
				$dateChange =  $item->dateChange;
				$item_id = $item->_id;
				$autor = $item->autor;
				$page = $item->page;
				
				$action = intval($action."");
				$dateTime = intval($dateTime."");
				$dateRemember = intval($dateRemember."");
				$is_private = intval($is_private."");
				$user_id = intval($user_id."");
				$note_id = intval($note_id."");
				$is_saw_today = intval($is_saw_today."");
				$dateChange = intval($dateChange."");
				$mark = floatval($mark."");
				$user_id = intval($user_id."");
				$page = intval($page."");
				
				$query = "Insert into books (name, mark, action, dateTime, dateRemember, image_url, comment, image_path, is_private, user_id, note_id, is_saw_today, dateChange, autor, page) values ('$name', '$mark', '$action', '$dateTime', '$dateRemember', '$image_url', '$comment', '$image_path', '$is_private', '$user_id', '$note_id', '$is_saw_today', '$dateChange', '$autor', '$page')";
		
				$result = mysql_query($query);
	
				$response1 = array();
				if ($result) 
				{
					$response1["success"] = 1;
					$response1["message"] = "Item successfully created.";
					$response1["_id"] = mysql_insert_id();
					$response1["item_id"] = $item_id;	
				} else
				{
					$response1["success"] = 0;
					$response1["message"] = "Oops! An error occurred.";
					$response1["_id"] = -1;

				}
	
				array_push($response[$key_name], $response1);
			}
	}
	
	
}
	
	
	
	 echo json_encode($response);
	mysql_close();


?>