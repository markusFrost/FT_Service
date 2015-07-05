package funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces;

import org.json.JSONObject;

/**
 * Created by Андрей on 23.05.2015.
 */
public interface OnSerialLoadListner
{
    void onSerialsLoad(JSONObject js);
    void onSerialsUpdated(JSONObject js);
    void onSerialsDeleted(JSONObject js);
}
