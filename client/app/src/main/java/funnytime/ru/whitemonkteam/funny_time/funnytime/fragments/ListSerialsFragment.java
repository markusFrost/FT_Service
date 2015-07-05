package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.CUActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.XYChartBuilder;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.SerialAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.SyncNewDataTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenu;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuCreator;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuItem;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuListView;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.CasheItemsUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StatisticUtils;

/**
 * Created by Андрей on 21.03.2015.
 */
public class ListSerialsFragment extends Fragment implements SearchView.OnQueryTextListener, OnItemsLoadListner
{
    SerialAdapter adapter ;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        adapter = new SerialAdapter(getActivity());

        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(), CUActivity.class);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);
                intent.putExtra(Constants.EXTRA_ITEM, (Serial)adapter.getItem(position));

                startActivityForResult(intent, Constants.CODE_UPDATE_SERIAL);
            }
        });


        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                //createDeleteAlert((Serial)adapter.getItem(position));
                Intent intent = new Intent(getActivity(), XYChartBuilder.class);
                intent.putExtra(Constants.EXTRA_ITEM, (Serial)adapter.getItem(position));
                startActivity(intent);
                return true;
            }
        });

    }*/

    private SwipeMenuListView mListView;
    private ProgressBar pb ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.swipe_list_layout, container, false);
        mListView = (SwipeMenuListView) v.findViewById(R.id.listView);

        pb = (ProgressBar) v.findViewById(R.id.pbLoadCash);
        pb.setVisibility(View.GONE);

        if ( HelpUtils.isOnline(getActivity()))
        {
            loadData();
        }

        Bundle b = getArguments();
        if ( b == null)
        {
            adapter = new SerialAdapter(getActivity());
        }
        else
        {
            long time = b.getLong(Constants.EXTRA_ITEM);
            ArrayList<Serial> list = AppContext.dbAdapter.getSerialsByDate(
                    ShPrefUtils.getUserId(getActivity()), time
            );

            adapter = new SerialAdapter(getActivity(), list);
        }

        mListView.setAdapter(adapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem statItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                statItem.setBackground(new ColorDrawable(Color.rgb(0x3F,
                        0x25, 0xF9)));
                // set item width
                statItem.setWidth(dp2px(90));
                // set a icon
                statItem.setIcon(R.drawable.ic_graph);
                // add to menu
                menu.addMenuItem(statItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        // open
                        Intent intent = new Intent(getActivity(), XYChartBuilder.class);
                        intent.putExtra(Constants.EXTRA_ITEM, (Serial) adapter.getItem(position));
                        startActivity(intent);

                        break;
                    case 1:
                        // delete
//					delete(item);
                        createDeleteAlert((Serial) adapter.getItem(position));


                        break;
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CUActivity.class);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);
                intent.putExtra(Constants.EXTRA_ITEM, (Serial) adapter.getItem(position));

                startActivityForResult(intent, Constants.CODE_UPDATE_SERIAL);
            }
        });

        UserDto user = new UserDto();
        user.first_name = "Андрей";
        user.last_name = "Выставкин";
        user.uid = 53074293;
        user.photo_200 = "http://cs617518.vk.me/v617518293/f50e/nhAQFWi5ENA.jpg";

        //String json = new Gson().toJson(user);

       // new CreateNewUserTask(getActivity(), user, this).execute();


        return  v;
    }

    private MenuItem searchMenuItem;
    private SearchView mSearchView;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_main, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        mSearchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if ( id == R.id.menu_add)
        {
            Intent intent = new Intent(getActivity(), CUActivity.class);
            intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);

            startActivityForResult(intent, Constants.CODE_ADD_SERIAL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == Constants.CODE_ADD_SERIAL && resultCode == getActivity().RESULT_OK && data != null)
        {
            Serial item = (Serial) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(getActivity());
            long id = AppContext.dbAdapter.Add(item);


            item.Id = id;
            CasheItemsUtils.Add(getActivity(), item, Constants.ADD);

           // Toast.makeText(getActivity(), "id = " + item.Id, Toast.LENGTH_SHORT).show();

            //item.DateChange = HelpUtils.getYerstaday(0);
            StatisticUtils.Save(item);

            adapter.Add(item);
            adapter.notifyDataSetChanged();

            if (HelpUtils.isOnline(getActivity()))
            {
                loadData();
            }

        }
        else if ( requestCode == Constants.CODE_UPDATE_SERIAL && resultCode == getActivity().RESULT_OK && data != null)
        {
            Serial item = (Serial) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(getActivity());
            AppContext.dbAdapter.Update(item);

           // item.DateChange = HelpUtils.getYerstaday(0);
            StatisticUtils.Save(item);
            CasheItemsUtils.Add(getActivity(), item, Constants.UPDATE);

            adapter.Update(item);
            adapter.notifyDataSetChanged();

            if (HelpUtils.isOnline(getActivity()))
            {
                loadData();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createDeleteAlert(final Serial item)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(R.string.alertNotif);
        String msg = getActivity().getResources().getString(R.string.alertShure) + " " + item.Name;
        alert.setMessage(msg);

        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                AppContext.dbAdapter.Delete(item);
                CasheItemsUtils.Add(getActivity(), item, Constants.DELETE);
                adapter.Delete(item);
                adapter.notifyDataSetChanged();

                if (HelpUtils.isOnline(getActivity()))
                {
                    loadData();
                }
            }
        });

        alert.setNegativeButton(android.R.string.no, null);

        alert.show();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
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

    @Override
    public void onItemsLoaded(JSONObject js)
    {
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onItemsUpdated(JSONObject js) {

    }

    @Override
    public void onItemsDeleted(JSONObject js) {

    }



    private void loadData()
    {
        ArrayList<CasheDto> arrayList = AppContext.dbAdapter.getListCasheDto();
        arrayList.size();
        String json = SyncNewDataTask.formData();
        if ( json != null)
        {
            pb.setVisibility(View.VISIBLE);
            new SyncNewDataTask(getActivity(), json, ListSerialsFragment.this).execute();
        }
    }
}
