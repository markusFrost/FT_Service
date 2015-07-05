package funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto;

import com.google.gson.annotations.SerializedName;

import funnytime.ru.whitemonkteam.funny_time.funnytime.models.BaseEntity;

/**
 * Created by Андрей on 22.05.2015.
 */
public class AnswerDto extends BaseEntity
{
    @SerializedName("success")
    public int Success;

    @SerializedName("message")
    public String Message;

    @SerializedName("item_id")
    public long ItemId;
}
