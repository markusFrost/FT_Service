package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.items;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.ArraysDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;

/**
 * Created by ������ on 19.05.2015.
 */
public class CreateNewItemsTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private ArraysDto item;
    private OnItemsLoadListner listner;
    private ProgressDialog pDialog;

    public CreateNewItemsTask(Activity context, ArraysDto item, OnItemsLoadListner listner)
    {
        this.context = context;
        this.listner = listner;
        this.item = item;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_loadSync));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {


        String js = new Gson().toJson(item);
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair(ServerConstants.JSON, js ));
       // params.add(new BasicNameValuePair(ServerConstants., item + "" ));

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.ADD_ITEMS, "POST", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json)
    {
        super.onPostExecute(json);

        listner.onItemsLoaded(json);

        pDialog.dismiss();

    }
}
