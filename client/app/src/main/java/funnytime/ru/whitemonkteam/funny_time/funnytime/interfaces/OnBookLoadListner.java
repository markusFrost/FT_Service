package funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces;

import org.json.JSONObject;

/**
 * Created by Андрей on 23.05.2015.
 */
public interface OnBookLoadListner
{
    void onBooksLoaded(JSONObject js);
    void onBooksUpdated(JSONObject js);
    void onBooksDeleted(JSONObject js);
}
