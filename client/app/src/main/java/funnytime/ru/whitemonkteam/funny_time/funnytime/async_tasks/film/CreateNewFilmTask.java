package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film;

import android.app.Activity;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.DbAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnFilmLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StringCrypter;

/**
 * Created by ������ on 19.05.2015.
 */
public class CreateNewFilmTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private Film item;
    //private ProgressDialog pDialog;
    private OnFilmLoadListner listner;

    public CreateNewFilmTask(Activity context, Film item, OnFilmLoadListner  listner)
    {
        this.context = context;
        this.listner = listner;
        this.item = item;
       /* pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_autorisation));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();*/
    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {

        if ( item.Name == null)
        {
            item.Name = "";
        }
        if ( item.ImageURL == null)
        {
            item.ImageURL = "";
        }
        if ( item.Comment == null)
        {
            item.Comment = "";
        }
        if ( item.ImagePath == null)
        {
            item.ImagePath = "";
        }

        StringCrypter crypter = new StringCrypter();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try
        {
            params.add(new BasicNameValuePair(DbAdapter.NAME, URLEncoder.encode(crypter.encrypt(item.Name), "UTF-8")));
            params.add(new BasicNameValuePair(DbAdapter.IMAGE_URL, URLEncoder.encode(crypter.encrypt(item.ImageURL), "UTF-8")));
            params.add(new BasicNameValuePair(DbAdapter.COMMENT, URLEncoder.encode(crypter.encrypt(item.Comment), "UTF-8")));
            params.add(new BasicNameValuePair(DbAdapter.IMAGE_PATH, URLEncoder.encode(crypter.encrypt(item.ImagePath), "UTF-8")));
            //params.add(new BasicNameValuePair(ServerConstants., URLEncoder.encode(crypter.encrypt(item.), "UTF-8")));



        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        params.add(new BasicNameValuePair(DbAdapter.ACTION, item.Action + "" ));
        params.add(new BasicNameValuePair(DbAdapter.DATE_TIME, item.DateTime + "" ));
        params.add(new BasicNameValuePair(DbAdapter.DATE_REMEMBER, item.DateRemember + "" ));
        params.add(new BasicNameValuePair(DbAdapter.IS_PRIVATE, item.IsPrivate + "" ));
        params.add(new BasicNameValuePair(DbAdapter.USER_ID, item.UserId + "" ));
        params.add(new BasicNameValuePair(DbAdapter.NOTE_ID, item.NoteId + "" ));
        params.add(new BasicNameValuePair(DbAdapter.IS_SAW_TODAY, item.IsSawToday + "" ));
        params.add(new BasicNameValuePair(DbAdapter.DATE_CHANGE, item.DateChange + "" ));
        params.add(new BasicNameValuePair(DbAdapter.MARK, item.Mark + "" ));
       // params.add(new BasicNameValuePair(ServerConstants., item + "" ));

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.ADD_FILM, "POST", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json)
    {
        super.onPostExecute(json);

        listner.onFilmLoad(json);
       // pDialog.dismiss();
    }
}
