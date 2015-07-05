package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.URL;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.ArraysDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by ������ on 18.05.2015.
 */
public class SyncNewDataTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
    private ProgressDialog pDialog;
    private OnItemsLoadListner listner;
    private String jsonValue;


    public SyncNewDataTask(Activity context,String jsonValue,  OnItemsLoadListner listner)
    {
       this.context = context;
        this.listner = listner;
        this.jsonValue = jsonValue;
        //String url = URL.MAIN_URL + URL.ADD_ITEMS;

        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_autorisation));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        //pDialog.show();

    }



    @Override
    protected JSONObject doInBackground(Void... p)
    {
       // AppContext.dbAdapter.clearCashTable();

        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair(ServerConstants.JSON, jsonValue));



        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.ADD_ITEMS, "POST", params);



        return jsonObject;
    }

    @Override
    protected void onPostExecute(final JSONObject json)
    {
        super.onPostExecute(json);

        new Thread()
        {
            @Override
            public void run()
            {
                parseDataFromJson(json);
                context.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {

                    }
                });
            }
        }.start();
        listner.onItemsLoaded(json);
        pDialog.dismiss();



    }




    public static String formData()
    {
        // AppContext.dbAdapter.clearCashTable();


        ArrayList<CasheDto> casheDtos = AppContext.dbAdapter.getListCasheDto();
            ArraysDto item = new ArraysDto();

            item.FilmsAdd = AppContext.dbAdapter.getCashFilmList(Constants.ADD);
            item.FilmsUpdate = AppContext.dbAdapter.getCashFilmList(Constants.UPDATE);
            ArrayList<Film> films = AppContext.dbAdapter.getCashFilmList(Constants.DELETE);


            for (Film it : films)
            {
                AnswerDto answerDto = new AnswerDto();
                answerDto.ItemId = it.NoteId;
                item.FilmsDelete.add(answerDto);
            }

            item.SerialsAdd = AppContext.dbAdapter.getCashSerialList(Constants.ADD);
            item.SerialsUpdate = AppContext.dbAdapter.getCashSerialList(Constants.UPDATE);
            ArrayList<Serial> serials = AppContext.dbAdapter.getCashSerialList(Constants.DELETE);
            for (Serial it : serials)
            {
                AnswerDto answerDto = new AnswerDto();
                answerDto.ItemId = it.NoteId;
                item.SerialsDelete.add(answerDto);
            }

            item.BooksAdd = AppContext.dbAdapter.getCashBookList(Constants.ADD);
            item.BooksUpdate = AppContext.dbAdapter.getCashBookList(Constants.UPDATE);
            ArrayList<Book> books = AppContext.dbAdapter.getCashBookList(Constants.DELETE);
            for (Book it : books)
            {
                AnswerDto answerDto = new AnswerDto();
                answerDto.ItemId = it.NoteId;
                item.BooksDelete.add(answerDto);
            }

        String json = null;

        if ( item.FilmsAdd.size() == 0 && item.FilmsUpdate.size() == 0 && item.FilmsDelete.size() == 0
            && item.SerialsAdd.size() == 0 && item.SerialsUpdate.size() == 0 && item.SerialsDelete.size() == 0
            && item.BooksAdd.size() == 0 && item.BooksUpdate.size() == 0 && item.BooksDelete.size() == 0
                )
        {
            json =  null;
        }
        else
        {
           json =  new Gson().toJson(item);
        }

        return json;

    }

    private void parseDataFromJson(JSONObject js)
    {
     try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());


            for ( AnswerDto item : list)
            {
                AppContext.dbAdapter.updateFilmByNoteId(item.Id, item.ItemId);
            }

            ArrayList<Film> films = AppContext.dbAdapter.getFilms(ShPrefUtils.getUserId(context));
            films.size();

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());


            for ( AnswerDto item : list)
            {
                AppContext.dbAdapter.updateSerialByNoteId(item.Id, item.ItemId);
            }

            ArrayList<Serial> serials = AppContext.dbAdapter.getSerials(ShPrefUtils.getUserId(context));
            serials.size();

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());


            for ( AnswerDto item : list)
            {
                AppContext.dbAdapter.updateBookByNoteId(item.Id, item.ItemId);
            }

            ArrayList<Book> books = AppContext.dbAdapter.getBooks(ShPrefUtils.getUserId(context));
            books.size();

        }catch (Exception e){}

        AppContext.dbAdapter.clearCashTable();
   }
}
