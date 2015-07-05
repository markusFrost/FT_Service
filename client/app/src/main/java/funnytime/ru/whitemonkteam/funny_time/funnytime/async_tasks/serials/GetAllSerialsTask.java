package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.serials;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.DbAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnSerialLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;

/**
 * Created by Андрей on 19.05.2015.
 */
public class GetAllSerialsTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private ProgressDialog pDialog;
    private OnSerialLoadListner listner;
    private long user_id;

    public GetAllSerialsTask(Activity context, long user_id, OnSerialLoadListner listner)
    {
        this.context = context;
        this.listner = listner;
        this.user_id = user_id;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_loadSerials));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(DbAdapter.USER_ID, user_id + "" ));

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.GET_SERIALS, "GET", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject)
    {
        super.onPostExecute(jsonObject);

        listner.onSerialsLoad(jsonObject);
        pDialog.dismiss();
    }
}
