package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnFilmLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;

/**
 * Created by ������ on 19.05.2015.
 */
public class DeleteFilmsTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private ArrayList<Film> list;
    private OnFilmLoadListner listner;


    public DeleteFilmsTask(Activity context, ArrayList<Film> list, OnFilmLoadListner listner)
    {
        this.context = context;
        this.listner = listner;
        this.list = list;

    }

    @Override
    protected JSONObject doInBackground(Void... p)
    {

        ArrayList<AnswerDto> arrayList = new ArrayList<>();
        AnswerDto item;
        for ( Film f : list)
        {
            item = new AnswerDto();
            item.ItemId = f.NoteId;
            arrayList.add(item);
        }


        String js = new Gson().toJson(arrayList);
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair(ServerConstants.JSON, js ));
       // params.add(new BasicNameValuePair(ServerConstants., item + "" ));

        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.DELETE_FILMS, "POST", params);

        return json;
    }

    @Override
    protected void onPostExecute(JSONObject json)
    {
        super.onPostExecute(json);

        listner.onFilmsDeleted(json);

    }
}
