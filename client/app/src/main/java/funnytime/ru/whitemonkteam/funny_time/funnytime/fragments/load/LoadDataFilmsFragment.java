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
import java.util.Collections;
import java.util.Comparator;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.FilmAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film.GetAllFilmsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnFilmLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 22.05.2015.
 */
public class LoadDataFilmsFragment extends ListFragment implements OnFilmLoadListner, SearchView.OnQueryTextListener
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

        new GetAllFilmsTask(getActivity(), user_id, this).execute();


    }

    @Override
    public void onFilmLoad(JSONObject js) {

    }

    private  FilmAdapter adapter;
    @Override
    public void onFilmsLoad(JSONObject js)
    {
        String json = null;
        try {
            json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

        ArrayList<Film> list = new Gson().fromJson(json, new TypeToken<ArrayList<Film>>() {
        }.getType());

        for ( int i = 0; i < list.size(); i++)
        {
            list.set(i, ConvertUtils.decodeFilm(list.get(i)));
        }

            if (list.size() > 0)
            {
                Collections.sort(list, new Comparator<Film>() {
                    @Override
                    public int compare(final Film f1, final Film f2) {

                        if (f1.DateChange > f2.DateChange) {
                            return -1;
                        } else if (f1.DateChange < f2.DateChange) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }

        adapter = new FilmAdapter(getActivity(), list);

        setListAdapter(adapter);

            // когда кликнули - обнулили ser_id note_id

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onFilmsUpdated(JSONObject js) {

    }

    @Override
    public void onFilmsDeleted(JSONObject js) {

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s)
    {
        adapter.Search(s);
        adapter.notifyDataSetChanged();
        return false;
    }
}
