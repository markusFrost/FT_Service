package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;

/**
 * Created by Андрей on 19.05.2015.
 */
public class GetAllUsersTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private ProgressDialog pDialog;
    private OnUserLoadListner listner;

    public  GetAllUsersTask(Activity context, OnUserLoadListner listner)
    {
        this.context = context;
        this.listner = listner;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_loadUsers));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.GET_USERS, "GET", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject)
    {
        super.onPostExecute(jsonObject);

        listner.onUsersLoaded(jsonObject);
        pDialog.dismiss();
    }
}
