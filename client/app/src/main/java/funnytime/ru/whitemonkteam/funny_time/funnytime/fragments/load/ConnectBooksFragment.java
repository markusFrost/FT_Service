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
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.books.CreateNewBooksTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.books.DeleteBooksTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.books.UpdateBooksTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnBookLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 23.05.2015.
 */
public class ConnectBooksFragment extends Fragment implements OnBookLoadListner
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
        pDialog.setMessage(getActivity().getResources().getString(R.string.go_sendBooks));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        Bundle b = getArguments();

        int type = b.getInt(Constants.EXTRA_ITEM);

        if ( type == Constants.ADD)
        {
            ArrayList<Book> list = AppContext.dbAdapter.getPublicBooks(ShPrefUtils.getUserId(getActivity()));
            new CreateNewBooksTask(getActivity(), list, this).execute();
        }
        else if ( type == Constants.UPDATE)
        {
            ArrayList<Book> list = AppContext.dbAdapter.getPublicBooks(ShPrefUtils.getUserId(getActivity()));

            for ( int i = 0; i < list.size(); i++)
            {
                Book item = list.get(i);
                item.UserId = -123;
                item.NoteId = 65 - i;
                list.set(i,item);
            }

            new UpdateBooksTask(getActivity(), list, this).execute();
        }
        else if ( type == Constants.DELETE)
        {
            ArrayList<Book> list = AppContext.dbAdapter.getPublicBooks(ShPrefUtils.getUserId(getActivity()));

            for ( int i = 0; i < list.size(); i++)
            {
                Book item = list.get(i);
                item.UserId = -123;
                item.NoteId = 65 - i;
                list.set(i,item);
            }

            new DeleteBooksTask(getActivity(), list, this).execute();
        }
    }

    @Override
    public void onBooksLoaded(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>()
            {
            }.getType());

            json +="";
            // ? ??? ?????? ????????? note_id

            pDialog.dismiss();
            getActivity().finish();

        } catch (JSONException e)
        {
            pDialog.dismiss();
            //  Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBooksUpdated(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>()
            {
            }.getType());

            json +="";
            // ? ??? ?????? ????????? note_id

            pDialog.dismiss();
            getActivity().finish();

        } catch (JSONException e)
        {
            pDialog.dismiss();
            //  Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBooksDeleted(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<AnswerDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<AnswerDto>>()
            {
            }.getType());

            json +="";
            // ? ??? ?????? ????????? note_id

            pDialog.dismiss();
            getActivity().finish();

        } catch (JSONException e)
        {
            pDialog.dismiss();
            //  Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

        }
    }
}
