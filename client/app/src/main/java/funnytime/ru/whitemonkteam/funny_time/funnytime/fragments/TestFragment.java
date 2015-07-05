package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.items.CreateNewItemsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnFilmLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.ArraysDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StringCrypter;

/**
 * Created by Андрей on 19.05.2015.
 */
public class TestFragment extends Fragment implements OnUserLoadListner, OnFilmLoadListner, OnItemsLoadListner
{
    TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        /*
        new Gson().fromJson(json,
                new TypeToken<ArrayList<Film>>() {
                }.getType()  );
         */

        tv = new TextView(getActivity());

        tv.setTextColor(Color.WHITE);
        tv.setText("Hello");
       /* String json = "[{\"lastName\":\"Vystavlin\",\"user_id\":\"53074293\",\"_id\":\"2\",\"image_url\":\"http:\\/\\/cs617518.vk.me\\/v617518293\\/f50e\\/nhAQFWi5ENA.jpg\",\"name\":\"Andrey\"},{\"lastName\":\"bGmiV8Foijjfyf4fA7PgPK5dU\\/DoYgHK\\n\",\"user_id\":\"53074293\",\"_id\":\"87\",\"image_url\":\"2RDRj2k9hxNadHE2nXklz8K+dVns0N8cdzZr9Y8HnZR+yeKtL1HdyoI1xXMehE7rDyw29JKzCAA=\\n\",\"name\":\"XvGHya5H8WLmyt\\/jJcyCBQ==\\n\"},{\"lastName\":\"\",\"user_id\":\"0\",\"_id\":\"88\",\"image_url\":\"\",\"name\":\"\"},{\"lastName\":\"\",\"user_id\":\"0\",\"_id\":\"89\",\"image_url\":\"\",\"name\":\"\"}]";
        ArrayList<UserDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<UserDto>>(){}.getType());

        UserDto user = ConvertUtils.decodeUserDto(list.get(1));
*/

      /*  user = new UserDto();
        user.first_name = getActivity().getResources().getString(R.string.my_name);
        user.last_name = getActivity().getResources().getString(R.string.my_lastName);
        user.uid = 53074293;
       // user.photo_200 = "http://cs624317.vk.me/v624317793/2bbcb/FUS1uH47vTs.jpg"; //Никита
      // user.photo_200 = "http://cs617731.vk.me/v617731259/256e4/RtidanJugvI.jpg"; // Женя
       //user.photo_200 = "http://cs623419.vk.me/v623419275/297bd/CV3VVLQ5-FY.jpg"; // Илья
        user.photo_200 = "http://cs617518.vk.me/v617518293/f50e/nhAQFWi5ENA.jpg"; //я*/


//        byte ptext[] = user.first_name.getBytes(ISO_8859_1);
//        String value = new String(ptext, UTF_8);
//        user.first_name = String.valueOf(Charset.forName("UTF-8").encode(user.first_name));

        //String json = new Gson().toJson(user);


        user = new UserDto();
        user.first_name = "Some Name";
        user.last_name = "Some lastName";
        user.uid = 156;
       // user.photo_200 = "http://cs617518.vk.me/v617518293/f50e/nhAQFWi5ENA.jpg";
        user.photo_200 = "f";


       // new DeleteUserTask(getActivity(), user, this).execute();
        //new UpdateUserTask(getActivity(), user, this).execute();
      // new CreateNewUserTask(getActivity(), user, this).execute();
       // new GetAllUsersTask(getActivity(), this).execute();

       /* user = new UserDto();
        user.uid = 53074290;
        new IsUserExistsTask(getActivity(), user, this).execute();*/

      //  Film item = AppContext.dbAdapter.getFilms(ShPrefUtils.getUserId(getActivity())).get(0);
      //  new CreateNewFilmTask(getActivity(), item, this).execute();

    //    ArrayList<Film> arrayList = new ArrayList<>();

      //  ArrayList<Film> list = AppContext.dbAdapter.getFilms(ShPrefUtils.getUserId(getActivity()));

     /*   for ( int i = 1; i <= 12; i++)
        {
            for ( Film f : list)
            {
                arrayList.add(f);
            }
        }*/

      //  new CreateNewFilmsTask(getActivity(), list, this).execute();


       /* String json = new Gson().toJson(arrayList);
        int l = json.length();
        int max = Integer.MAX_VALUE;*/


      /*  ArrayList<AnswerDto> list = new ArrayList<>();

        AnswerDto item = new AnswerDto();
        item.ItemId = ShPrefUtils.getUserId(getActivity());
        list.add(item);


        new GetNewsFilmsTask(getActivity(), list, this).execute();*/

        ArrayList<Film> list1 = AppContext.dbAdapter.getFilms(ShPrefUtils.getUserId(getActivity()));
        ArrayList<Serial> list2 = AppContext.dbAdapter.getSerials(ShPrefUtils.getUserId(getActivity()));
        ArrayList<Book> list3 = AppContext.dbAdapter.getBooks(ShPrefUtils.getUserId(getActivity()));



        ArrayList<Film> listF = new ArrayList<>();
        listF.add(ConvertUtils.encyptFilm(list1.get(0)));

        ArrayList<Serial> listS = new ArrayList<>();
        listS.add(ConvertUtils.encyptSerial(list2.get(0)));

        ArrayList<Book> listB = new ArrayList<>();
        listB.add(ConvertUtils.encyptBook(list3.get(0)));

        ArraysDto item = new ArraysDto();
        item.FilmsAdd = listF;
        item.SerialsAdd = listS;
        item.BooksAdd = listB;

        item.FilmsUpdate = listF;
        item.SerialsUpdate = listS;
        item.BooksUpdate = listB;

        ArrayList<AnswerDto> listInt = new ArrayList<>();
        AnswerDto answerDto = new AnswerDto();
        answerDto.ItemId = 1129;
        listInt.add(answerDto);

        item.FilmsDelete = listInt;
        item.SerialsDelete = listInt;
        item.BooksDelete= listInt;

        new CreateNewItemsTask(getActivity(), item, this).execute();


        return tv;
    }

    @Override
    public void onItemsLoaded(JSONObject js)
    {
        tv.setText(js.toString());

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());

            for ( AnswerDto item : list)
            {
               // AppContext.dbAdapter.updateFilmByNoteId(item.Id, item.ItemId);
            }
        //    AppContext.dbAdapter.deleteCasheItems(Constants.TYPE_FILM);

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());

            for ( AnswerDto item : list)
            {
                //AppContext.dbAdapter.updateFilmByNoteId(item.Id, item.ItemId);
            }
          //  AppContext.dbAdapter.deleteCasheItems(Constants.TYPE_SERIAL);

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS_ADD).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
            }.getType());

            for ( AnswerDto item : list)
            {
               //AppContext.dbAdapter.updateBookByNoteId(item.Id, item.ItemId);
            }

           // AppContext.dbAdapter.deleteCasheItems(Constants.TYPE_BOOK);

        }catch (Exception e){}
    }

    @Override
    public void onFilmsLoad(JSONObject js)
    {
        tv.setText(js.toString());


    }

    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    UserDto user;
    @Override
    public void onUserLoaded(JSONObject js)
    {
        String value = "no";
        StringCrypter crypter = new StringCrypter();
        try
        {

            tv.setText(js.toString() + "\n" +
                            crypter.decrypt(js.getString(ServerConstants.NAME) ) + "\n" +
                            crypter.decrypt(js.getString(ServerConstants.LAST_NAME) )
            );


        } catch (JSONException e) {
            e.printStackTrace();
        }

//
//
//        String res1 = crypter.decrypt("VHkr5Pd0Am83wkYaVI6g3RO1ih6AZhfL");
//        String res2 = crypter.decrypt("VHkr5Pd0Am83wkYaVI6g3YeUwjm3jRMSlLEy5Okli2M=\n");
//
//
//
//        res1 = crypter.decrypt(crypter.encrypt(user.first_name));
//        res2 = crypter.decrypt(crypter.encrypt(user.last_name));





    }

    @Override
    public void onUsersLoaded(JSONObject js)
    {
        try
        {
            String value = js.getJSONArray(ServerConstants.TAG_ARRAY_USERS).toString();

            int result = js.getInt(ServerConstants.TAG_SUCCESS);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tv.setText(js.toString());


    }

    @Override
    public void onFilmLoad(JSONObject js)
    {
        tv.setText(js.toString());

       String json = null;
        try {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

        ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>() {
        }.getType());

            json +="";

        } catch (JSONException e) {
            e.printStackTrace();
        }

      /*  try
        {
            String value = js.getString(ServerConstants.NAME);
            StringCrypter crypter = new StringCrypter();
            value = crypter.decrypt(value);
            tv.setText(js.toString() + "\n" + "name = " + value);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }



    @Override
    public void onFilmsUpdated(JSONObject js) {

    }

    @Override
    public void onFilmsDeleted(JSONObject js) {

    }



    @Override
    public void onItemsUpdated(JSONObject js) {

    }

    @Override
    public void onItemsDeleted(JSONObject js) {

    }
}
