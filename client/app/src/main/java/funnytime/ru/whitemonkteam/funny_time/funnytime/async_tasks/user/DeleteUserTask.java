package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StringCrypter;

/**
 * Created by Андрей on 19.05.2015.
 */
public class DeleteUserTask  extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private UserDto user;
    private ProgressDialog pDialog;
    private OnUserLoadListner listner;
    public  DeleteUserTask(Activity context, UserDto user, OnUserLoadListner listner)
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
        StringCrypter crypter = new StringCrypter();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try
        {
            params.add(new BasicNameValuePair(ServerConstants.NAME, URLEncoder.encode(crypter.encrypt(user.first_name), "UTF-8")));
            params.add(new BasicNameValuePair(ServerConstants.LAST_NAME, URLEncoder.encode( crypter.encrypt(user.last_name),"UTF-8")));
            params.add(new BasicNameValuePair(ServerConstants.IMAGE_URL, URLEncoder.encode( crypter.encrypt(user.photo_200),"UTF-8")));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.add(new BasicNameValuePair(ServerConstants.USER_ID, user.uid + ""));



        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.DELETE_USER, "POST", params);
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json)
    {
        super.onPostExecute(json);
        listner.onUserLoaded(json);
        pDialog.dismiss();
    }
}
