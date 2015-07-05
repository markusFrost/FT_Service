package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.load;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.CUActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.NewsBooksAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.NewsFilmsAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.NewsSerialsAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.books.GetNewsBooksTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.film.GetNewsFilmsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.serials.GetNewsSerialsTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnBookLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnFilmLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnSerialLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by ������ on 23.05.2015.
 */
public class ListNewsFragment extends Fragment implements OnFilmLoadListner, OnSerialLoadListner, OnBookLoadListner,SwipeRefreshLayout.OnRefreshListener
{


    public static ListNewsFragment newInstanse(int position)
    {
        Bundle b = new Bundle();
        b.putInt(funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants.EXTRA_ITEM, position);

        ListNewsFragment fragment = new ListNewsFragment();
        fragment.setArguments(b);

        return fragment;

    }

    private SwipeRefreshLayout mSwipeRefresh;
    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.refresh_layout, container, false);

        lv = (ListView) v.findViewById(R.id.lvMain);

        mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setColorSchemeResources
                (R.color.light_blue, R.color.middle_blue, R.color.deep_blue);

        return v;
    }
    int position;
     ArrayList<AnswerDto> list;
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);



         position = getArguments().getInt(funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants.EXTRA_ITEM);

       // final ArrayList<AnswerDto> list = AppContext.dbAdapter.getListFriends(ShPrefUtils.getUserId(getActivity()));




      /*  ArrayList<Long> arrayList = new ArrayList<>();

        for ( AnswerDto item : list)
        {
            arrayList.add(item.ItemId);
        }*/
      /*  AnswerDto item1 = new AnswerDto();
        item1.ItemId = ShPrefUtils.getUserId(getActivity());
        list.add(item1);*/
         list = AppContext.dbAdapter.getUsersIds();

        if ( list.size()  > 0) {
            if (position == Constants.TYPE_FILM) {
                if (HelpUtils.isOnline(getActivity())) {
                    new GetNewsFilmsTask(getActivity(), list, this).execute();
//                    for (AnswerDto item : list) {
//                        AppContext.dbAdapter.clearFriendsData(item.ItemId);
//                    }
                    setRefreshing();

                } else {
                    setRefreshing();
                    new Thread() {
                        @Override
                        public void run() {
                            final ArrayList<Film> arrayList = AppContext.dbAdapter.getStatisticFilm(list);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    filmsAdapter = new NewsFilmsAdapter(getActivity(), arrayList);
                                    lv.setAdapter(filmsAdapter);
                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }.start();
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        createAlert(position, Constants.TYPE_SHOW_USERS_FILMS);
                    }
                });
            } else if (position == Constants.TYPE_SERIAL) {
                if (HelpUtils.isOnline(getActivity())) {
                    new GetNewsSerialsTask(getActivity(), list, this).execute();
//                    for (AnswerDto item : list) {
//                        AppContext.dbAdapter.clearFriendsData(item.ItemId);
//                    }
                    setRefreshing();

                } else {
                    setRefreshing();
                    new Thread() {
                        @Override
                        public void run() {
                            final ArrayList<Serial> arrayList = AppContext.dbAdapter.getStatisticSerials(list);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    serialsAdapter = new NewsSerialsAdapter(getActivity(), arrayList);
                                    lv.setAdapter(serialsAdapter);
                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }.start();

                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        createAlert(position, Constants.TYPE_SHOW_USERS_SERIALS);
                    }
                });

            } else if (position == Constants.TYPE_BOOK) {
                if (HelpUtils.isOnline(getActivity())) {
                    new GetNewsBooksTask(getActivity(), list, this).execute();
//                    for (AnswerDto item : list) {
//                        AppContext.dbAdapter.clearFriendsData(item.ItemId);
//                    }
                    setRefreshing();

                } else {
                    setRefreshing();
                    new Thread() {
                        @Override
                        public void run() {
                            final ArrayList<Book> arrayList = AppContext.dbAdapter.getStatisticBook(list);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    booksAdapter = new NewsBooksAdapter(getActivity(), arrayList);
                                    lv.setAdapter(booksAdapter);
                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }.start();


                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        createAlert(position, Constants.TYPE_SHOW_USERS_BOOKS);
                    }
                });
            }
        }


    }

    private void createAlert (final  int position, final int type)
    {

                if (type == Constants.TYPE_SHOW_USERS_FILMS) {
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);
                    intent.putExtra(Constants.EXTRA_ITEM, filmsAdapter.getItem(position));

                    getActivity().startActivityForResult(intent, Constants.CODE_ADD_FILM);
                } else if (type == Constants.TYPE_SHOW_USERS_SERIALS) {
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);
                    intent.putExtra(Constants.EXTRA_ITEM,  serialsAdapter.getItem(position));

                    getActivity().startActivityForResult(intent, Constants.CODE_ADD_SERIAL);
                } else if (type == Constants.TYPE_SHOW_USERS_BOOKS) {
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_BOOK);
                    intent.putExtra(Constants.EXTRA_ITEM,  booksAdapter.getItem(position));

                    getActivity().startActivityForResult(intent, Constants.CODE_ADD_BOOK);
                }


    }

    @Override
    public void onRefresh() {

       /* if (HelpUtils.isOnline(getActivity()))
        {

        ArrayList<AnswerDto> list = new ArrayList<>();

        AnswerDto item = new AnswerDto();
        item.ItemId = ShPrefUtils.getUserId(getActivity());
        list.add(item);


        if (position == Constants.TYPE_FILM) {
            new GetNewsFilmsTask(getActivity(), list, this).execute();
            //setRefreshing();
        } else if (position == Constants.TYPE_SERIAL) {
            new GetNewsSerialsTask(getActivity(), list, this).execute();
            //setRefreshing();
        } else if (position == Constants.TYPE_BOOK) {
            new GetNewsBooksTask(getActivity(), list, this).execute();
            // setRefreshing();
        }*/

        final ArrayList<AnswerDto> list = AppContext.dbAdapter.getUsersIds();

        if ( list.size()  > 0)
        {
            if (position == Constants.TYPE_FILM)
            {
                if (HelpUtils.isOnline(getActivity()))
                {
                    new GetNewsFilmsTask(getActivity(), list, this).execute();
//                    for (AnswerDto item : list)
//                    {
//                        AppContext.dbAdapter.clearFriendsData(item.ItemId, position);
//                    }
                   // setRefreshing();

                } else
                {
                    setRefreshing();
                    new Thread() {
                        @Override
                        public void run() {
                            final ArrayList<Film> arrayList = AppContext.dbAdapter.getStatisticFilm(list);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    filmsAdapter = new NewsFilmsAdapter(getActivity(), arrayList);
                                    lv.setAdapter(filmsAdapter);
                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }.start();
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        createAlert(position, Constants.TYPE_SHOW_USERS_FILMS);
                    }
                });
            } else if (position == Constants.TYPE_SERIAL) {
                if (HelpUtils.isOnline(getActivity())) {
                    new GetNewsSerialsTask(getActivity(), list, this).execute();
//                    for (AnswerDto item : list) {
//                        AppContext.dbAdapter.clearFriendsData(item.ItemId, position);
//                    }
                   // setRefreshing();

                } else {
                    setRefreshing();
                    new Thread() {
                        @Override
                        public void run() {
                            final ArrayList<Serial> arrayList = AppContext.dbAdapter.getStatisticSerials(list);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    serialsAdapter = new NewsSerialsAdapter(getActivity(), arrayList);
                                    lv.setAdapter(serialsAdapter);
                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }.start();

                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        createAlert(position, Constants.TYPE_SHOW_USERS_SERIALS);
                    }
                });

            } else if (position == Constants.TYPE_BOOK) {
                if (HelpUtils.isOnline(getActivity())) {
                    new GetNewsBooksTask(getActivity(), list, this).execute();
//                    for (AnswerDto item : list) {
//                        AppContext.dbAdapter.clearFriendsData(item.ItemId, position);
//                    }
                  //  setRefreshing();

                } else {
                    setRefreshing();
                    new Thread() {
                        @Override
                        public void run() {
                            final ArrayList<Book> arrayList = AppContext.dbAdapter.getStatisticBook(list);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    booksAdapter = new NewsBooksAdapter(getActivity(), arrayList);
                                    lv.setAdapter(booksAdapter);
                                    mSwipeRefresh.setRefreshing(false);
                                }
                            });
                        }
                    }.start();


                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        createAlert(position, Constants.TYPE_SHOW_USERS_BOOKS);
                    }
                });
            }
        }
    }




    NewsFilmsAdapter filmsAdapter ;
    @Override
    public void onFilmsLoad(JSONObject js)
    {
        String json = null;

        try
        {
            if ( js.getInt(ServerConstants.TAG_SUCCESS) == 0)
            {
                mSwipeRefresh.setRefreshing(false);
            }
        }catch (Exception e){}

        try {


            json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

            ArrayList<Film> list = new Gson().fromJson(json, new TypeToken<ArrayList<Film>>() {
            }.getType());


            for ( AnswerDto answerDto : this.list)
            {
                AppContext.dbAdapter.clearFriendsData(answerDto.ItemId, Constants.TYPE_FILM);
            }

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeFilm(list.get(i)));
                if ( list.get(i).UserId != ShPrefUtils.getUserId(getActivity()))
                {
                    AppContext.dbAdapter.Add(list.get(i));
                }
            }
            filmsAdapter = new NewsFilmsAdapter(getActivity(), list);

           lv.setAdapter(filmsAdapter);

            mSwipeRefresh.setRefreshing(false);

            // ����� �������� - �������� ser_id note_id

        } catch (JSONException e) {
            e.printStackTrace();
            mSwipeRefresh.setRefreshing(false);
        }

        }

    NewsSerialsAdapter serialsAdapter;
    @Override
    public void onSerialsLoad(JSONObject js)
    {
        String json = null;

        try
        {
            if ( js.getInt(ServerConstants.TAG_SUCCESS) == 0)
            {
                mSwipeRefresh.setRefreshing(false);
            }
        }catch (Exception e){}

        try {

            json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS).toString();

            ArrayList<Serial> list = new Gson().fromJson(json, new TypeToken<ArrayList<Serial>>() {
            }.getType());

            for ( AnswerDto answerDto : this.list)
            {
                AppContext.dbAdapter.clearFriendsData(answerDto.ItemId, Constants.TYPE_SERIAL);
            }

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeSerial(list.get(i)));
                if ( list.get(i).UserId != ShPrefUtils.getUserId(getActivity()))
                {
                    AppContext.dbAdapter.Add(list.get(i));
                }
            }
            serialsAdapter = new NewsSerialsAdapter(getActivity(), list);

            lv.setAdapter(serialsAdapter);

            mSwipeRefresh.setRefreshing(false);


            // ����� �������� - �������� ser_id note_id

        } catch (JSONException e) {
            e.printStackTrace();
            mSwipeRefresh.setRefreshing(false);
        }

    }

    NewsBooksAdapter booksAdapter;
    @Override
    public void onBooksLoaded(JSONObject js)
    {
        String json = null;
        try
        {
            if ( js.getInt(ServerConstants.TAG_SUCCESS) == 0)
            {
                mSwipeRefresh.setRefreshing(false);
            }
        }catch (Exception e){}

        try {

            json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<Book> list = new Gson().fromJson(json, new TypeToken<ArrayList<Book>>() {
            }.getType());

            for ( AnswerDto answerDto : this.list)
            {
                AppContext.dbAdapter.clearFriendsData(answerDto.ItemId, Constants.TYPE_BOOK);
            }

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeBook(list.get(i)));
                if ( list.get(i).UserId != ShPrefUtils.getUserId(getActivity()))
                {
                    AppContext.dbAdapter.Add(list.get(i));
                }
            }
            booksAdapter = new NewsBooksAdapter(getActivity(), list);

            lv.setAdapter(booksAdapter);

            mSwipeRefresh.setRefreshing(false);

            // ����� �������� - �������� ser_id note_id

        } catch (JSONException e) {
            e.printStackTrace();
            mSwipeRefresh.setRefreshing(false);
        }
    }

    //---------------------------

    @Override
    public void onFilmLoad(JSONObject js)
    {

    }



    @Override
    public void onFilmsUpdated(JSONObject js) {

    }

    @Override
    public void onFilmsDeleted(JSONObject js) {

    }



    @Override
    public void onBooksUpdated(JSONObject js) {

    }

    @Override
    public void onBooksDeleted(JSONObject js) {

    }



    @Override
    public void onSerialsUpdated(JSONObject js) {

    }

    @Override
    public void onSerialsDeleted(JSONObject js) {

    }

    private void setRefreshing()
    {
        mSwipeRefresh.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mSwipeRefresh.setRefreshing(true);
    }

}
