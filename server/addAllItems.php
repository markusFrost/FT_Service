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
	
	$value_json = $_POST['value_json'];
	
		$data = json_decode($value_json);
foreach ($data as $key_name => $value) 
{
    
	
	if ( $key_name == 'films_add')
	{		
        $result_array[$key_name] = array();
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
	
					array_push($result_array[$key_name], $response);					
	        }
    }
	 if ( $key_name == 'serials_add')
	{
		 $result_array[$key_name] = array();
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
	
				array_push($result_array[$key_name], $response);
			}
	}
	 if ( $key_name == 'books_add')
	{
		 $result_array[$key_name] = array();
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
	
				array_push($result_array[$key_name], $response);
			}
	}
	if ( $key_name == 'films_update')
	{		      
		 $result_array[$key_name] = array();
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
				$item_id = intval($item_id."");
				
				$query = "Update films set name = '$name', mark = '$mark', action = '$action', dateTime = '$dateTime', dateRemember = '$dateRemember', image_url = '$image_url', comment = '$comment', image_path = '$image_path', is_private = '$is_private',  is_saw_today = '$is_saw_today', dateChange = '$dateChange' where _id = '$note_id'";
				$result = mysql_query($query);	
				$response = array();
				
				if ($result) 
				{
					$response["success"] = 1;
					$response["message"] = "Item successfully updated.";	
				} else
				{
				    $response["success"] = 0;
					$response["message"] = "Oops! An error occurred.";
				}
	
				array_push($result_array[$key_name], $response);				
	        }
    }
	if ( $key_name == 'serials_update')
	{
		 $result_array[$key_name] = array();
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
				
				$query = "Update serials set name = '$name', mark = '$mark', action = '$action', dateTime = '$dateTime', dateRemember = '$dateRemember', image_url = '$image_url', comment = '$comment', image_path = '$image_path', is_private = '$is_private',  is_saw_today = '$is_saw_today', dateChange = '$dateChange', season = '$season', series = '$series' where _id = '$note_id'";
				$result = mysql_query($query);
				
				$response = array();
				
				if ($result) 
				{
					$response["success"] = 1;
					$response["message"] = "Item successfully updated.";	
				} else
				{
				    $response["success"] = 0;
					$response["message"] = "Oops! An error occurred.";
				}
	
				array_push($result_array[$key_name], $response);
			}
	}
	if ( $key_name == 'books_update')
	{
		 $result_array[$key_name] = array();
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
				
				$query = "Update books set name = '$name', mark = '$mark', action = '$action', dateTime = '$dateTime', dateRemember = '$dateRemember', image_url = '$image_url', comment = '$comment', image_path = '$image_path', is_private = '$is_private',  is_saw_today = '$is_saw_today', dateChange = '$dateChange', autor = '$autor', page = '$page' where _id = '$note_id'";
				$result = mysql_query($query);
				
				$response = array();
				
				if ($result) 
				{
					$response["success"] = 1;
					$response["message"] = "Item successfully updated.";	
				} else
				{
				    $response["success"] = 0;
					$response["message"] = "Oops! An error occurred.";
				}
	
				array_push($result_array[$key_name], $response);
			}
	}
	//_delete
	if ( $key_name == 'films_delete')
	{		      
		 $result_array[$key_name] = array();
		foreach ($value as $item) 
			{
				
				$note_id = $item->item_id;
				$note_id = intval($note_id."");

				
				$query = "delete from films where _id = '$note_id'";
				
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
	
				array_push($result_array[$key_name], $response);				
	        }
    }
	if ( $key_name == 'serials_delete')
	{		      
		 $result_array[$key_name] = array();
		foreach ($value as $item) 
			{
				
				$note_id = $item->item_id;
				$note_id = intval($note_id."");

				
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
	
				array_push($result_array[$key_name], $response);				
	        }
    }
	if ( $key_name == 'books_delete')
	{		      
		 $result_array[$key_name] = array();
		foreach ($value as $item) 
			{
				
				$note_id = $item->item_id;
				$note_id = intval($note_id."");

				
				$query = "delete from books where _id = '$note_id'";
				
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
	
				array_push($result_array[$key_name], $response);				
	        }
    }
}

echo json_encode($result_array);
	

	
	
	mysql_close();


?>