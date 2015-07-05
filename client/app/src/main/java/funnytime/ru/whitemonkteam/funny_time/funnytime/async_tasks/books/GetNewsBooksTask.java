package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.books;

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
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnBookLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;

/**
 * Created by Андрей on 19.05.2015.
 */
public class GetNewsBooksTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private ProgressDialog pDialog;
    private OnBookLoadListner listner;
   private ArrayList<AnswerDto> list;

    public GetNewsBooksTask(Activity context, ArrayList<AnswerDto> list, OnBookLoadListner listner)
    {
        this.context = context;
        this.listner = listner;
        this.list = list;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_loadNews));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
       // pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        String js = new Gson().toJson(list);

        params.add(new BasicNameValuePair(ServerConstants.JSON, js ));

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.GET_NEWS_BOOKS, "GET", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject)
    {
        super.onPostExecute(jsonObject);

        listner.onBooksLoaded(jsonObject);
        pDialog.dismiss();
    }
}
