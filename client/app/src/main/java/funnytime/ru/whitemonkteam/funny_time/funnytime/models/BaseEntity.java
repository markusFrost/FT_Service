package funnytime.ru.whitemonkteam.funny_time.funnytime.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public  abstract class BaseEntity implements Serializable
{
	@SerializedName("_id")
	public long Id ;
}
