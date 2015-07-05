package funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces;

import org.json.JSONObject;

/**
 * Created by Андрей on 19.05.2015.
 */
public interface OnUserLoadListner
{
    void onUserLoaded(JSONObject js);
    void onUsersLoaded(JSONObject js);


}
