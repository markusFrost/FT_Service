package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.load;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film.CreateNewFilmsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film.DeleteFilmsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film.UpdateFilmsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnFilmLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by ������ on 22.05.2015.
 */
public class ConnectFilmsFragment extends Fragment implements OnFilmLoadListner
{
    private ProgressDialog pDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.activity_load_data, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getActivity().getResources().getString(R.string.go_sendFilms));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        Bundle b = getArguments();

        int type = b.getInt(Constants.EXTRA_ITEM);

        if ( type == Constants.ADD)
        {
            ArrayList<Film> list = AppContext.dbAdapter.getPublicFilms(ShPrefUtils.getUserId(getActivity()));
            new CreateNewFilmsTask(getActivity(), list, this).execute();
        }
        else  if ( type == Constants.UPDATE)
        {
            ArrayList<Film> list = AppContext.dbAdapter.getPublicFilms(ShPrefUtils.getUserId(getActivity()));

            for ( int i = 0; i < list.size(); i++)
            {
                Film item = list.get(i);
                item.UserId = -123;
                item.NoteId = 1038 - i;
                list.set(i, item);
            }
            new UpdateFilmsTask(getActivity(), list, this).execute();
        }
        else  if ( type == Constants.DELETE)
        {
            ArrayList<Film> list = AppContext.dbAdapter.getPublicFilms(ShPrefUtils.getUserId(getActivity()));

            for ( int i = 0; i < list.size(); i++)
            {
                Film item = list.get(i);
                item.UserId = -123;
                item.NoteId = 1038 - i;
                list.set(i, item);
            }


            new DeleteFilmsTask(getActivity(), list, this).execute();
        }



       ArrayList<Film> list = AppContext.dbAdapter.getPublicFilms(ShPrefUtils.getUserId(getActivity()));

        for ( int i = 0; i < list.size(); i++)
        {
            Film item = list.get(i);
            item.UserId = -123;
            item.NoteId = 1038 - i;
            list.set(i, item);
        }


        new DeleteFilmsTask(getActivity(), list, this).execute();
      // new UpdateFilmsTask(getActivity(), list, this).execute();
        //new CreateNewFilmsTask(getActivity(), list, this).execute();


    }



    @Override
    public void onFilmLoad(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>()
            {
            }.getType());

            json +="";
            // � ��� ������ ��������� note_id

            pDialog.dismiss();
            getActivity().finish();

        } catch (JSONException e)
        {
            pDialog.dismiss();
          //  Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onFilmsLoad(JSONObject js) {

    }

    @Override
    public void onFilmsUpdated(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>()
            {
            }.getType());

            json +="";
            // � ��� ������ ��������� note_id

            pDialog.dismiss();
            getActivity().finish();

        } catch (JSONException e)
        {
            pDialog.dismiss();
            //  Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onFilmsDeleted(JSONObject js)
    {
        pDialog.dismiss();
        getActivity().finish();
    }
}
