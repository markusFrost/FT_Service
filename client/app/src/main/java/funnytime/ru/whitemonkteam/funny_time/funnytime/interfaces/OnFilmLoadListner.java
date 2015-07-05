package funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces;

import org.json.JSONObject;

/**
 * Created by Андрей on 19.05.2015.
 */
public interface OnFilmLoadListner
{
    void onFilmLoad(JSONObject js);
    void onFilmsLoad(JSONObject js);
    void onFilmsUpdated(JSONObject js);
    void onFilmsDeleted(JSONObject js);
}
