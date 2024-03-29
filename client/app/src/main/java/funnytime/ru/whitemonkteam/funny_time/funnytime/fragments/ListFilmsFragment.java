package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.CUActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.FilmAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.SyncNewDataTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenu;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuCreator;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuItem;
import funnytime.ru.whitemonkteam.funny_time.funnytime.swipemenulistview.SwipeMenuListView;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.CasheItemsUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;


public class ListFilmsFragment extends Fragment implements SearchView.OnQueryTextListener, OnItemsLoadListner
{

    // создаём метод заполнения
    // методы закрыия и открытия

    FilmAdapter adapter;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

      /*  String json = "{\"books\":[{\"autor\":\"8LQsGAzsT9zc9VuGY4R9Yio8LwLcguhhk7FHUr7Hs38=\\n\",\"dateTime\":\"0\",\"mark\":\"0\",\"is_saw_today\":\"0\",\"note_id\":\"730\",\"_id\":\"908\",\"page\":\"68\",\"image_url\":\"xsvvIjxfAb7Ug5KDQV72bpOMS\\/ffu1YYgRdkkHf2P4QVN5NAF1taM8pHDH2c6NepZwj0P426pXX1\\n7XHCcZHqWFuOrfHDZOIUxQyFY5hNqDrLR\\/qYXdgiGmLHo7+fRtSE+YRhoD1htIk=\\n\",\"dateRemember\":\"0\",\"name\":\"093z3LeeuJfgV3At9Dd6ydTudm8tW2E6tseMCWTQN\\/Y=\\n\",\"action\":\"13\",\"is_private\":\"0\",\"user_id\":\"53074293\",\"image_path\":\"SUhzfZUWgdDvl3n\\/9TDQkO435J0EW8v5QXVcs8+RljAyoxN1BZgwkRTBu2cs9DVWZwhMLJzD5Dg=\\n\",\"comment\":\"ggfqXT4Zpf0=\\n\",\"dateChange\":\"1432648566392\"}]}";


        try {
            JSONObject jsonObject = new JSONObject(json);

            json = jsonObject.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<Book> list = new Gson().fromJson(json, new TypeToken<ArrayList<Book>>() {
            }.getType());

            json += "";
        }catch (Exception e){}*/

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);



       /* setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(), CUActivity.class);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);
                intent.putExtra(Constants.EXTRA_ITEM, (Film)adapter.getItem(position));

                startActivityForResult(intent, Constants.CODE_UPDATE_FILM);
            }
        });


        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                createDeleteAlert((Film)adapter.getItem(position));
                return true;
            }
        });*/

    }

    private SwipeMenuListView mListView;
    private ProgressBar pb ;


/*
1 создаем метод
 */
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

        fillSearchView();

        Bundle b = getArguments();
        if ( b == null)
        {
            adapter = new FilmAdapter(getActivity());
        }
        else
        {
            long time = b.getLong(Constants.EXTRA_ITEM);
            ArrayList<Film> list = AppContext.dbAdapter.getFilmsByDate(
                    ShPrefUtils.getUserId(getActivity()), time
            );

            adapter = new FilmAdapter(getActivity(), list);
        }



        mListView.setAdapter(adapter);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x3F,
                        0x25, 0xF9)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

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
                        Intent intent = new Intent(getActivity(), CUActivity.class);
                        intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);
                        intent.putExtra(Constants.EXTRA_ITEM, (Film)adapter.getItem(position));

                        startActivityForResult(intent, Constants.CODE_UPDATE_FILM);
                        break;
                    case 1:
                        // delete
//					delete(item);
                        createDeleteAlert((Film)adapter.getItem(position));

                        break;
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getActivity(), CUActivity.class);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);
                intent.putExtra(Constants.EXTRA_ITEM, (Film)adapter.getItem(position));

                startActivityForResult(intent, Constants.CODE_UPDATE_FILM);
            }
        });

        return  v;
    }

    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> searchAdapter;
    View v;
    private void fillSearchView()
    {
        list = AppContext.dbAdapter.getFilmsNames(ShPrefUtils.getUserId(getActivity()));
        searchAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),   R.layout.simple_list_item_1, list);


       /* LayoutInflater inflator = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflator.inflate(R.layout.actionbar, null);
        AutoCompleteTextView textView = (AutoCompleteTextView) v
                .findViewById(R.id.editMessage);
        textView.setAdapter(searchAdapter);

        textView.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s)
            {

                String name = s.toString();
               // updateAdapters(name);

            }
        });*/
    }


    MenuItem menuAdd, menuSearch;

    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        super.onPrepareOptionsMenu(menu);
        menuAdd = menu.findItem(R.id.menu_add);
        menuSearch = menu.findItem(R.id.action_search);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menuSearch.getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
    }



    public void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            int a = 0;
            //use the query to search
        }
    }
   /* private void openSearch()
   {
        list = AppContext.dbAdapter.getFilmsNames(ShPrefUtils.getUserId(getActivity()));
        searchAdapter.notifyDataSetChanged();
        showKeyboard();
        fillSearchView();
        menuSearch.setVisible(false);
        menuAdd.setVisible(false);
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.getSupportActionBar().setCustomView(v);
    }

    public boolean closeSearch()
    {
        boolean fl = menuSearch.isVisible();
        if ( menuSearch != null && !menuSearch.isVisible())
        {
           // updateAdapters(null);
            menuSearch.setVisible(true);
            menuAdd.setVisible(true);
            ActionBarActivity activity = (ActionBarActivity) getActivity();
            v.setVisibility(View.GONE);
            activity.getSupportActionBar().setCustomView(null);

            hideKeyboard();
            return true;
        }
        else
        {
            return false;
        }
    }



    private void hideKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void showKeyboard()
    {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
*/


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

       // searchMenuItem.setActionView(mSearchView)

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if ( id == R.id.menu_add)
        {
            Intent intent = new Intent(getActivity(), CUActivity.class);
            intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);

            startActivityForResult(intent, Constants.CODE_ADD_FILM);


        }
        else if ( id == R.id.action_search)
        {
           // openSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
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

            adapter.Add(item);
            adapter.notifyDataSetChanged();

            if (HelpUtils.isOnline(getActivity()))
            {
                loadData();
            }

        }
        else if ( requestCode == Constants.CODE_UPDATE_FILM && resultCode == getActivity().RESULT_OK && data != null)
        {
            Film item = (Film) data.getSerializableExtra(Constants.EXTRA_ITEM);


            item.UserId = ShPrefUtils.getUserId(getActivity());
           AppContext.dbAdapter.Update(item);
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


    private void createDeleteAlert(final Film item)
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
    public boolean onQueryTextChange(String s)
    {
        // тут все и делаеa
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
            new SyncNewDataTask(getActivity(), json, ListFilmsFragment.this).execute();
        }
    }
}
