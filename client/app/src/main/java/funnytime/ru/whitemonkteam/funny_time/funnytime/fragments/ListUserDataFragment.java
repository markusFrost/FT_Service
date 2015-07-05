package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.CUActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.BookAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.FilmAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.SerialAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.SyncNewDataTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuListView;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.CasheItemsUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StatisticUtils;

/**
 * Created by Андрей on 26.05.2015.
 */
public class ListUserDataFragment extends Fragment implements SearchView.OnQueryTextListener, OnItemsLoadListner
{
    FilmAdapter filmAdapter;
    SerialAdapter serialAdapter;
    BookAdapter bookAdapter;

    private SwipeMenuListView mListView;
    private ProgressBar pb ;

    private void createAlert (final  int position, final int type)
    {
//        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//        alert.setTitle(R.string.strAdd);
//        alert.setMessage(R.string.strShureAdd);
//        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
                if (type == Constants.TYPE_SHOW_USERS_FILMS)
                {
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);
                    intent.putExtra(Constants.EXTRA_ITEM, (Film) filmAdapter.getItem(position));

                    startActivityForResult(intent, Constants.CODE_ADD_FILM);
                } else if (type == Constants.TYPE_SHOW_USERS_SERIALS)
                {
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);
                    intent.putExtra(Constants.EXTRA_ITEM, (Serial) serialAdapter.getItem(position));

                    startActivityForResult(intent, Constants.CODE_ADD_SERIAL);
                } else if (type == Constants.TYPE_SHOW_USERS_BOOKS)
                {
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_BOOK);
                    intent.putExtra(Constants.EXTRA_ITEM, (Book) bookAdapter.getItem(position));

                    startActivityForResult(intent, Constants.CODE_ADD_BOOK);
                }
//            }
//        });
//
//        alert.setNeutralButton(android.R.string.no, null);
//
//        alert.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.swipe_list_layout, container, false);
        mListView = (SwipeMenuListView) v.findViewById(R.id.listView);
        pb = (ProgressBar) v.findViewById(R.id.pbLoadCash);
        pb.setVisibility(View.GONE);

        final int type = getArguments().getInt(Constants.TYPE_ITEM);
        long user_id = getArguments().getLong(Constants.EXTRA_USER);

        if ( type == Constants.TYPE_SHOW_USERS_FILMS)
        {
            ArrayList<Film> list = AppContext.dbAdapter.getFilms(user_id);
            filmAdapter = new FilmAdapter(getActivity(), list);

            mListView.setAdapter(filmAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   createAlert(position, type);
                }
            });
        }
        else if ( type == Constants.TYPE_SHOW_USERS_SERIALS)
        {
            ArrayList<Serial> list = AppContext.dbAdapter.getSerials(user_id);
            serialAdapter = new SerialAdapter(getActivity(), list);
            mListView.setAdapter(serialAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    createAlert(position, type);
                }
            });
        }
        else if ( type == Constants.TYPE_SHOW_USERS_BOOKS)
        {
            ArrayList<Book> list = AppContext.dbAdapter.getBooks(user_id);
            bookAdapter = new BookAdapter(getActivity(), list);
            mListView.setAdapter(bookAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    createAlert(position, type);
                }
            });
        }





        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == Constants.CODE_ADD_FILM && resultCode == getActivity().RESULT_OK && data != null)
        {
            Film item = (Film) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(getActivity());
            long id = AppContext.dbAdapter.Add(item);

            item.Id = id;
            item.UserId = ShPrefUtils.getUserId(getActivity());
            CasheItemsUtils.Add(getActivity(), item, Constants.ADD);

            //Toast.makeText(getActivity(), "id = " + id, Toast.LENGTH_SHORT).show();

            if (HelpUtils.isOnline(getActivity()))
            {
                loadData();
            }

        }
        else if ( requestCode == Constants.CODE_ADD_SERIAL && resultCode == getActivity().RESULT_OK && data != null)
        {
            Serial item = (Serial) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(getActivity());
            long id = AppContext.dbAdapter.Add(item);


            item.Id = id;
            CasheItemsUtils.Add(getActivity(), item, Constants.ADD);

            // Toast.makeText(getActivity(), "id = " + item.Id, Toast.LENGTH_SHORT).show();

            //item.DateChange = HelpUtils.getYerstaday(0);
            StatisticUtils.Save(item);


            if (HelpUtils.isOnline(getActivity()))
            {
                loadData();
            }

        }
        else  if ( requestCode == Constants.CODE_ADD_BOOK && resultCode == getActivity().RESULT_OK && data != null)
        {
            Book item = (Book) data.getSerializableExtra(Constants.EXTRA_ITEM);

            item.UserId = ShPrefUtils.getUserId(getActivity());
            long id = AppContext.dbAdapter.Add(item);

            item.Id = id;
            CasheItemsUtils.Add(getActivity(), item, Constants.ADD);

            // Toast.makeText(getActivity(), "id = " + item.Id, Toast.LENGTH_SHORT).show();

            // item.DateChange = HelpUtils.getYerstaday(0);
            StatisticUtils.Save(item);

            if (HelpUtils.isOnline(getActivity()))
            {
                loadData();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);


    }

    MenuItem menuAdd, menuSearch;

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        menuAdd = menu.findItem(R.id.menu_add);
        menuAdd.setVisible(false);
        menuSearch = menu.findItem(R.id.action_search);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menuSearch.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
    }



    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s)
    {
        if ( filmAdapter != null)
        {
            filmAdapter.Search(s);
            filmAdapter.notifyDataSetChanged();
        }
        else if ( serialAdapter != null)
        {
            serialAdapter.Search(s);
            serialAdapter.notifyDataSetChanged();
        }
        else if ( bookAdapter != null)
        {
            bookAdapter.Search(s);
            bookAdapter.notifyDataSetChanged();
        }

        return false;
    }

    private void loadData()
    {
        ArrayList<CasheDto> arrayList = AppContext.dbAdapter.getListCasheDto();
        arrayList.size();
        String json = SyncNewDataTask.formData();
        if ( json != null)
        {
            pb.setVisibility(View.VISIBLE);
            new SyncNewDataTask(getActivity(), json, ListUserDataFragment.this).execute();
        }
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
}
