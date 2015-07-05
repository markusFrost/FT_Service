package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;

/**
 * Created by Андрей on 19.05.2015.
 */
public class IsUserExistsTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private UserDto user;
    private ProgressDialog pDialog;
    private OnUserLoadListner listner;

    public IsUserExistsTask(Activity context, UserDto user, OnUserLoadListner listner)
    {
        this.context = context;
        this.listner = listner;
        this.user = user;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_autorisation));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(ServerConstants.USER_ID, user.uid + "" ));
        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.IS_USER_EXISTS, "GET", params);
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
