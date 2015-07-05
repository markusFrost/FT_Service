package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.CUActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.UsersAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.GetAllUsersTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 19.05.2015.
 */
public class UsersFragment extends ListFragment implements OnUserLoadListner, SearchView.OnQueryTextListener
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    UsersAdapter adapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

       // new GetAllUsersTask(getActivity(), this).execute();

       /* ArrayList<UserDto> arrayList = new ArrayList<>();
        arrayList.add(AppContext.dbAdapter.getUserById(ShPrefUtils.getUserId(getActivity())));

        UserDto user;

        user = new UserDto();
        user.first_name = "Nikita";
        user.last_name = "Antonov";
        user.photo_200 = "http://cs413819.vk.me/v413819793/1e6/hkKfhsrX8F0.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Vasily";
        user.last_name = "Melnikov";
        user.photo_200 = "http://cs409817.vk.me/v409817986/52cd/P8Z358uWAQg.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Eugene";
        user.last_name = "El'tsov";
        user.photo_200 = "http://cs617731.vk.me/v617731259/256ec/pC1ZyV3hTvY.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Ilya";
        user.last_name = " Hripko";
        user.photo_200 = "http://cs623419.vk.me/v623419275/297bd/CV3VVLQ5-FY.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Valentin";
        user.last_name = "Boev";
        user.photo_200 = "http://cs623331.vk.me/v623331777/18b1b/Fg4gnxwY7YQ.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Paul";
        user.last_name = "Keane";
        user.photo_200 = "http://cs621126.vk.me/v621126080/4556/Km6haT-iiuA.jpg";

        arrayList.add(user);*/
        if (!HelpUtils.isOnline(getActivity()))
        {
            adapter = new UsersAdapter(getActivity(), AppContext.dbAdapter.getUsers());

            setListAdapter(adapter);
        }
        else
        {
            new GetAllUsersTask(getActivity(), this).execute();
        }

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                UserDto user = ((UsersAdapter)getListView().getAdapter()).getItem(position);

               Intent intent = new Intent(getActivity(), CUActivity.class);
                intent.putExtra(Constants.TYPE_ITEM, Constants.CODE_SHOW_PROFILE);
                intent.putExtra(Constants.EXTRA_USER, user.uid);
                startActivity(intent);
            }
        });


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
    public void onUserLoaded(JSONObject js)
    {

    }

    @Override
    public void onUsersLoaded(JSONObject js)
    {
        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_USERS).toString();
            ArrayList<UserDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<UserDto>>() {
            }.getType());

            int position = 0;
            for ( int i = 0; i < list.size(); i++)
            {
                if ( list.get(i).uid == ShPrefUtils.getUserId(getActivity()))
                {
                   position = i;
                }
                else
                {
                    list.set(i, ConvertUtils.decodeUserDto(list.get(i)));
                }
            }

            list.remove(position);

             adapter = new UsersAdapter(getActivity(), list);

            setListAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
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
