<?php
// ��� ��� �������
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
		echo "<br/>�� ���� ����������� � �������� ��� ������.<br/>";
		exit();
	}
	//echo "<br/>���������� � �������� ��� ������ ��������� �������.<br/>";
	if (!mysql_select_db($db_name,$link))
	{
		echo "<br/>�� ���� ������� ���� ������.<br/>";
		exit();
	}
	
	
	 $query = "SELECT * FROM books where ( action <> 12) and ";
	$value_json = $_GET['value_json'];
	
	$myArray = json_decode($value_json, true);
	
	$k = 0;
	foreach ( $myArray as $item)
	{		
	$user_id =  $item['item_id'];
	$user_id = intval($user_id."");
	
	if ( $k == 0)
	{
		$query = $query." ( ( user_id = '$user_id' )  ";
	}
	else
	{
		$query = $query." or  ( user_id = '$user_id' )  ";
	}
	$k++;
	
	}
	
	$query = $query." ) order by dateChange desc";
	

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
 
    echo json_encode($response);
   } 
   else
 {
    $response["success"] = 0;
    $response["message"] = "No items found";
 
    echo json_encode($response);
}
	
	/*$response["query"] = $query;
	
		echo $response;*/
	
	
	mysql_close();


?>