package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.load;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.SerialAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.serials.GetAllSerialsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnSerialLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 23.05.2015.
 */
public class LoadDataSerialsFragment extends ListFragment implements OnSerialLoadListner, SearchView.OnQueryTextListener
{


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        long user_id = ShPrefUtils.getUserId(getActivity());

        new GetAllSerialsTask(getActivity(), user_id, this).execute();


    }

    private MenuItem searchMenuItem;
    private SearchView mSearchView;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_main, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mSearchView.setOnQueryTextListener(this);

        MenuItem menuAdd = menu.findItem(R.id.menu_add);
        menuAdd.setVisible(false);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private SerialAdapter adapter;
    @Override
    public void onSerialsLoad(JSONObject js)
    {
        String json = null;
        try {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS).toString();

            ArrayList<Serial> list = new Gson().fromJson(json, new TypeToken<ArrayList<Serial>>() {
            }.getType());

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeSerial(list.get(i)));
            }

            adapter = new SerialAdapter(getActivity(), list);

            setListAdapter(adapter);

            // когда кликнули - обнулили ser_id note_id

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSerialsUpdated(JSONObject js) {

    }

    @Override
    public void onSerialsDeleted(JSONObject js) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.Search(s);
        adapter.notifyDataSetChanged();
        return false;
    }
}
