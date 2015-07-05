package funnytime.ru.whitemonkteam.funny_time.funnytime.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item extends BaseEntity implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 3744419562647669654L;
    @SerializedName("name")
	public String Name;

    @SerializedName("action")
    public int Action;
  //  public Boolean WasSeen;

    @SerializedName("mark")
    public float Mark ;

    @SerializedName("dateTime")
    public long DateTime;

    @SerializedName("dateChange")
    public long DateChange;

    @SerializedName("dateRemember")
    public long DateRemember;

    @SerializedName("comment")
    public String Comment;

    @SerializedName("image_url")
    public String ImageURL;

    @SerializedName("image_path")
    public String ImagePath ;

    @SerializedName("is_private")
    public boolean IsPrivate;

    @SerializedName("is_saw_today")
    public boolean IsSawToday;

    @SerializedName("user_id")
    public long UserId;

    @SerializedName("note_id")
    public long NoteId;
}
