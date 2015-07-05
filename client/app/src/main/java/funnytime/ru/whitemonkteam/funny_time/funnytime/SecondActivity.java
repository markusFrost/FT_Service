package funnytime.ru.whitemonkteam.funny_time.funnytime;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.SyncNewDataTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.FriendVkProfileFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.FriendsVKFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListBookFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListFavoriteAutorsFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListFilmsFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListSerialsFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListUserDataFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ViewPagerFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ViewPagerNewsFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.CasheItemsUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StatisticUtils;


public class SecondActivity extends FragmentActivity implements OnItemsLoadListner
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        if ( getIntent() != null && getIntent().getAction() != null)
        {
            if ( getIntent().getAction().equals(Constants.ACTION_TO_SECOND_ACTIVITY_SHOW_PROFILE))
            {
                FragmentManager fragmentManager = getFragmentManager();

                FriendVkProfileFragment fragment = new FriendVkProfileFragment();
                fragment.setArguments(getIntent().getExtras());

                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment ).commit();
            }
            else  if ( getIntent().getAction().equals(Constants.ACTION_TO_SECOND_ACTIVITY_SHOW_FRIENDS))
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new FriendsVKFragment()).commit();
            }
            else  if ( getIntent().getAction().equals(Constants.ACTION_TO_SECOND_ACTIVITY_DISPLAY_STATISTIC))
            {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ViewPagerFragment()).commit();
            }
            else  if ( getIntent().getAction().equals(Constants.ACTION_TO_SECOND_ACTIVITY_DISPLAY_ITEMS_BY_DATE))
            {

                if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_FILM)
                {
                    ListFilmsFragment fragment = new ListFilmsFragment();
                    fragment.setArguments(getIntent().getExtras());

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).commit();
                }
               else  if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_SERIAL)
                {
                    ListSerialsFragment fragment = new ListSerialsFragment();
                    fragment.setArguments(getIntent().getExtras());

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).commit();
                }
                else  if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_BOOK)
                {
                    ListBookFragment fragment = new ListBookFragment();
                    fragment.setArguments(getIntent().getExtras());

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).commit();
                }
                else if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_STATISTIC)
                {
                    ListFavoriteAutorsFragment fragment = new ListFavoriteAutorsFragment();
                    fragment.setArguments(getIntent().getExtras());

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, fragment).commit();
                }

            }

        }
        else if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_NEWS)
        {
            Fragment fragment;
            fragment = new ViewPagerNewsFragment();
            FragmentManager fragmentmanager = getFragmentManager();
            fragmentmanager.beginTransaction().
                    replace(R.id.container, fragment).commit();

        }
        else if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_SHOW_USERS_FILMS
                ||   getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_SHOW_USERS_SERIALS
                ||  getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_SHOW_USERS_BOOKS
                )
        {
            Fragment fragment;
            fragment = new ListUserDataFragment();
            fragment.setArguments(getIntent().getExtras());
            FragmentManager fragmentmanager = getFragmentManager();
            fragmentmanager.beginTransaction().
                    replace(R.id.container, fragment).commit();

        }
       /* else if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.CODE_SHOW_PROFILE)
        {
            Fragment fragment;
            fragment = new UserProfileFragment();
            fragment.setArguments(getIntent().getExtras());
            FragmentManager fragmentmanager = getFragmentManager();
            fragmentmanager.beginTransaction().
                    replace(R.id.container, fragment).commit();
        }
        else if ( getIntent().getIntExtra(Constants.TYPE_ITEM,0) == Constants.TYPE_USERS)
        {
            Fragment fragment;
            fragment = new UsersFragment();

            FragmentManager fragmentmanager = getFragmentManager();
            fragmentmanager.beginTransaction().
                    replace(R.id.container, fragment).commit();
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == Constants.CODE_ADD_FILM && resultCode == RESULT_OK && data != null)
        {
            Film item = (Film) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(SecondActivity.this);
            long id = AppContext.dbAdapter.Add(item);

            item.Id = id;
            item.UserId = ShPrefUtils.getUserId(SecondActivity.this);
            CasheItemsUtils.Add(SecondActivity.this, item, Constants.ADD);

            //Toast.makeText(getActivity(), "id = " + id, Toast.LENGTH_SHORT).show();

            if (HelpUtils.isOnline(SecondActivity.this))
            {
                loadData();
            }

        }
        else if ( requestCode == Constants.CODE_ADD_SERIAL && resultCode == RESULT_OK && data != null)
        {
            Serial item = (Serial) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(SecondActivity.this);
            long id = AppContext.dbAdapter.Add(item);


            item.Id = id;
            CasheItemsUtils.Add(SecondActivity.this, item, Constants.ADD);

            // Toast.makeText(getActivity(), "id = " + item.Id, Toast.LENGTH_SHORT).show();

            //item.DateChange = HelpUtils.getYerstaday(0);
            StatisticUtils.Save(item);


            if (HelpUtils.isOnline(SecondActivity.this))
            {
                loadData();
            }

        }
        else  if ( requestCode == Constants.CODE_ADD_BOOK && resultCode == RESULT_OK && data != null)
        {
            Book item = (Book) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(SecondActivity.this);
            long id = AppContext.dbAdapter.Add(item);

            item.Id = id;
            CasheItemsUtils.Add(SecondActivity.this, item, Constants.ADD);

            // Toast.makeText(getActivity(), "id = " + item.Id, Toast.LENGTH_SHORT).show();

            // item.DateChange = HelpUtils.getYerstaday(0);
            StatisticUtils.Save(item);

            if (HelpUtils.isOnline(SecondActivity.this))
            {
                loadData();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void loadData()
    {
        ArrayList<CasheDto> arrayList = AppContext.dbAdapter.getListCasheDto();
        arrayList.size();
        String json = SyncNewDataTask.formData();
        if ( json != null)
        {
           // pb.setVisibility(View.VISIBLE);
            new SyncNewDataTask(SecondActivity.this, json, SecondActivity.this).execute();
        }
    }

    @Override
    public void onItemsLoaded(JSONObject js)
    {
       // pb.setVisibility(View.GONE);
    }

    @Override
    public void onItemsUpdated(JSONObject js) {

    }

    @Override
    public void onItemsDeleted(JSONObject js) {

    }
}
