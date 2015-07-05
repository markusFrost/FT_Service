package funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.ArraysDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.JSONParser;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StringCrypter;

/**
 * Created by Андрей on 18.05.2015.
 */
public class CreateNewUserTask extends AsyncTask<Void, Void, JSONObject>
{
    private Activity context;
   private UserDto user;
    private ProgressDialog pDialog;
    private OnUserLoadListner listner;


    public CreateNewUserTask(Activity context, UserDto user, OnUserLoadListner listner)
    {
       this.context = context;
        this.listner = listner;
        this.user = user;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(context.getResources().getString(R.string.go_autorisation));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
       // pDialog.show();
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
        params.add(new BasicNameValuePair(ServerConstants.JSON, formData()));



        JSONParser jsonParser = new JSONParser();
        JSONObject json = jsonParser.makeHttpRequest(URL.MAIN_URL + URL.ADD_USER, "POST", params);

        return json;
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
        listner.onUserLoaded(json);
        pDialog.dismiss();



    }




    private String formData()
    {
        ArrayList<Film> list1 = AppContext.dbAdapter.getFilms(user.uid);
        ArrayList<Serial> list2 = AppContext.dbAdapter.getSerials(user.uid);
        ArrayList<Book> list3 = AppContext.dbAdapter.getBooks(user.uid);



        ArrayList<Film> listF = new ArrayList<>();

        for ( int i = 0; i < list1.size(); i++)
        {
            listF.add(ConvertUtils.encyptFilm(list1.get(i)));
        }

        ArrayList<Serial> listS = new ArrayList<>();
        for ( int i = 0; i < list2.size(); i++)
        {
            listS.add(ConvertUtils.encyptSerial(list2.get(i)));
        }

        ArrayList<Book> listB = new ArrayList<>();
        for ( int i = 0; i < list3.size(); i++)
        {
            listB.add(ConvertUtils.encyptBook(list3.get(i)));
        }

        ArraysDto item = new ArraysDto();
        item.FilmsAdd = listF;
        item.SerialsAdd = listS;
        item.BooksAdd = listB;

       return new Gson().toJson(item);
    }

    private void parseDataFromJson(JSONObject js)
    {

       // AppContext.dbAdapter.Delete(user);
        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_USERS).toString();

            ArrayList<UserDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<UserDto>>() {
            }.getType());

            // if we become - users - then we have to become friends ids
            if ( list.size() == 0)
            {
                AppContext.dbAdapter.Add(user);
            }
            else
            {
                AppContext.dbAdapter.Add(ConvertUtils.decodeUserDto(list.get(0)));
            }

        }catch (Exception e)
        {
            AppContext.dbAdapter.Add(user);
        }
        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

            ArrayList<Film> list = new Gson().fromJson(json, new TypeToken<ArrayList<Film>>() {
            }.getType());

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeFilm(list.get(i)));
            }

            for ( Film item : list)
            {
                AppContext.dbAdapter.Add(item);
                //AppContext.dbAdapter.updateFilmByNoteId(item.Id, item.ItemId);
            }

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS).toString();

            ArrayList<Serial> list = new Gson().fromJson(json, new TypeToken<ArrayList<Serial>>() {
            }.getType());

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeSerial(list.get(i)));
            }

            for ( Serial item : list)
            {
                AppContext.dbAdapter.Add(item);
            }

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<Book> list = new Gson().fromJson(json, new TypeToken<ArrayList<Book>>() {
            }.getType());
            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeBook(list.get(i)));
            }

            for ( Book item : list)
            {
               // item.UserId = user.uid;
                AppContext.dbAdapter.Add(item);
            }
        }catch (Exception e){}

        // update data

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());


            for ( AnswerDto item : list)
            {
                AppContext.dbAdapter.updateFilmByNoteId(item.Id, item.ItemId);
            }

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
        }catch (Exception e){}
   }
}
