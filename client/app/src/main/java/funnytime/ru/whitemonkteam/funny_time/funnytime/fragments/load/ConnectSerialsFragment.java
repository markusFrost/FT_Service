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
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.serials.CreateNewSerialsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.serials.DeleteSerialsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.serials.UpdateSerialsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnSerialLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 23.05.2015.
 */
public class ConnectSerialsFragment extends Fragment implements OnSerialLoadListner
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
        pDialog.setMessage(getActivity().getResources().getString(R.string.go_sendSerials));
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        Bundle b = getArguments();

        int type = b.getInt(Constants.EXTRA_ITEM);

        if ( type == Constants.ADD)
        {
            ArrayList<Serial> list = AppContext.dbAdapter.getPublicSerials(ShPrefUtils.getUserId(getActivity()));
            new CreateNewSerialsTask(getActivity(), list, this).execute();
        }
        else if ( type == Constants.UPDATE)
        {
            ArrayList<Serial> list = AppContext.dbAdapter.getPublicSerials(ShPrefUtils.getUserId(getActivity()));

            for ( int i = 0; i < list.size(); i++)
            {
                Serial item = list.get(i);
                item.UserId = -123;
                item.NoteId = 65 - i;
                list.set(i,item);
            }

            new UpdateSerialsTask(getActivity(), list, this).execute();
        }
        else if ( type == Constants.DELETE)
        {
            ArrayList<Serial> list = AppContext.dbAdapter.getPublicSerials(ShPrefUtils.getUserId(getActivity()));

            for ( int i = 0; i < list.size(); i++)
            {
                Serial item = list.get(i);
                item.UserId = -123;
                item.NoteId = 65 - i;
                list.set(i,item);
            }

            new DeleteSerialsTask(getActivity(), list, this).execute();
        }
    }

    @Override
    public void onSerialsLoad(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS).toString();

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
    public void onSerialsUpdated(JSONObject js)
    {
        String json = null;
        try
        {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS).toString();

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
    public void onSerialsDeleted(JSONObject js)
    {
        pDialog.dismiss();
        getActivity().finish();
    }
}
