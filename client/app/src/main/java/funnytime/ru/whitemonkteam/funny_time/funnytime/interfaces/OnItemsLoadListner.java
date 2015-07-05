package funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces;

import org.json.JSONObject;

/**
 * Created by Андрей on 24.05.2015.
 */
public interface OnItemsLoadListner
{
    void onItemsLoaded(JSONObject js);
    void onItemsUpdated(JSONObject js);
    void onItemsDeleted(JSONObject js);
}
