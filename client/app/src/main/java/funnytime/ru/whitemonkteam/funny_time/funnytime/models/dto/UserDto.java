package funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Андрей on 18.05.2015.
 */
public class UserDto
{
    @SerializedName("_id")
    public long id;

    @SerializedName("user_id")
    public long uid;

    @SerializedName("name")
    public String first_name;

    @SerializedName("lastName")
    public String last_name;

    @SerializedName("image_url")
    public String photo_200;

    @SerializedName("image_path")
    public String imagePath;

    @SerializedName("friends")
    public String friends;
}
