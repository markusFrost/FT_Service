package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.CUActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.SecondActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.UserNewsAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.GetUserByIdTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.SyncNewDataTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.constants.ServerConstants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnItemsLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.BitmapUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.CasheItemsUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.StatisticUtils;

/**
 * Created by Андрей on 22.05.2015.
 */
public class UserProfileFragment extends ListFragment implements OnItemsLoadListner, OnUserLoadListner
{

    private UserNewsAdapter adapter;
    private View header;
    private long user_id;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // me - 0, friend - 4, user - 4

         user_id = ShPrefUtils.getUserId(getActivity());

        Bundle b = getArguments();
        if ( b != null)
        {
            user_id = b.getLong(Constants.EXTRA_USER);
        }

        if ( user_id == ShPrefUtils.getUserId(getActivity())) // if it is my profile - load data from db
        {
            loadUserProfileFromDatabase();
        }
//        else if ( AppContext.dbAdapter.isFriend(user_id))
//        {
//            if (HelpUtils.isOnline(getActivity())) // load data from server
//            {
//                loadUserProfileFromInternet();
//            }
//            else // load data from db
//            {
//                loadUserProfileFromDatabase();
//            }
//        }
//        else if ( HelpUtils.isOnline(getActivity())) // load users data from server
//        {
//            loadUserProfileFromInternet();
//        }

        else
        {
            if (HelpUtils.isOnline(getActivity())) // load data from server
            {
                loadUserProfileFromInternet();
            } else // load data from db
            {
                loadUserProfileFromDatabase();
            }
        }



        // user_id = getArguments().getLong(Constants.EXTRA_USER);



       // load();
    }
   private  ImageView iv ;
    private ProgressBar pb ;
   private UserDto user;
    private OnClickListner listner = new OnClickListner();

    private void loadUserProfileFromDatabase()
    {
        Button btnFilm, btnSerial, btnBook, btnState;

         user = AppContext.dbAdapter.getUserById(user_id);

        CUActivity activity = null;
        if ( getActivity() instanceof  CUActivity)
        {
            activity = ((CUActivity)getActivity());
        }

        if ( activity != null && activity.getSupportActionBar() != null)
        {
            activity.getSupportActionBar().setTitle(user.first_name + " " + user.last_name);
        }

        adapter = new UserNewsAdapter(getActivity(), user_id);

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        header  = layoutInflater.inflate(R.layout.user_profile_layout, null, false);
        pb = (ProgressBar) header.findViewById(R.id.pbLoadCash);
        pb.setVisibility(View.GONE);

        btnFilm = (Button) header.findViewById(R.id.btnProfFilms);
        btnSerial = (Button) header.findViewById(R.id.btnProfSerials);
        btnBook = (Button) header.findViewById(R.id.btnProfBooks);
        btnState = (Button) header.findViewById(R.id.btnProfConnection);

        btnFilm.setOnClickListener(listner);
        btnSerial.setOnClickListener(listner);
        btnBook.setOnClickListener(listner);
        btnState.setOnClickListener(listner);


        if ( user_id == ShPrefUtils.getUserId(getActivity()))
        {
            header.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, dp2px(260)));
        }
        else if ( AppContext.dbAdapter.isFriend(user_id))
        {
            // delete or add
        }
        else
        {

        }

        getListView().addHeaderView(header);

        //getListView().setAdapter(adapter);

        setListAdapter(adapter);



        iv = (ImageView) header.findViewById(R.id.ivProfileAvatar);

        if ( HelpUtils.isOnline(getActivity())) // load users data from server
        {
            Picasso.with(getActivity()).load( user.photo_200).into(target);
        }
        else
        {
            Picasso.with(getActivity()).load(Constants.FILE + user.imagePath).into(iv);
        }

        TextView tv = (TextView) header.findViewById(R.id.tvProfileName);

        tv.setText(user.first_name + " " + user.last_name);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                position--;
                AnswerDto answerDto = ((UserNewsAdapter)getListAdapter()).getItem(position);
                if (answerDto.Success == Constants.TYPE_FILM)
                {
                    Film item = AppContext.dbAdapter.getFilmById(user_id, answerDto.Id);
                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);
                    intent.putExtra(Constants.EXTRA_ITEM, item);

                    startActivityForResult(intent, Constants.CODE_ADD_FILM);

                }else  if ( answerDto.Success == Constants.TYPE_SERIAL)
                {
                    Serial item = AppContext.dbAdapter.getSerialById(user_id, answerDto.Id);

                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);
                    intent.putExtra(Constants.EXTRA_ITEM, item);

                    startActivityForResult(intent, Constants.CODE_ADD_SERIAL);

                } else  if ( answerDto.Success == Constants.TYPE_BOOK)
                {
                    Book item = AppContext.dbAdapter.getBookById(user_id, answerDto.Id);

                    Intent intent = new Intent(getActivity(), CUActivity.class);
                    intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_BOOK);
                    intent.putExtra(Constants.EXTRA_ITEM, item);

                    startActivityForResult(intent, Constants.CODE_ADD_BOOK);
                }
            }
        });

        // analize buttons
    }

    private void loadUserProfileFromInternet()
    {
        new GetUserByIdTask(getActivity(), user_id, this).execute();
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

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


   /* FilmAdapter filmAdapter;
    SerialAdapter serialAdapter;
    BookAdapter bookAdapter;*/
    @Override
    public void onUserLoaded(JSONObject js)
    {

        boolean fl = false;
        if ( user_id != ShPrefUtils.getUserId(getActivity()))//  if ( AppContext.dbAdapter.isFriend(user_id) || user_id != ShPrefUtils.getUserId(getActivity()))
        {
            fl = true;
            AppContext.dbAdapter.clearFriendsData(user_id);
        }

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_USERS).toString();

            ArrayList<UserDto> list = new Gson().fromJson(json, new TypeToken<ArrayList<UserDto>>() {
            }.getType());

            // if we become - users - then we have to become friends ids
            if ( list.size() > 0)
            {
                UserDto user = ConvertUtils.decodeUserDto(list.get(0));
                if ( fl)//if ( AppContext.dbAdapter.isFriend(user_id))
                {
                    AppContext.dbAdapter.Add(user);
                    AppContext.dbAdapter.Update(user);
                }
            }

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_FILMS).toString();

            ArrayList<Film> list = new Gson().fromJson(json, new TypeToken<ArrayList<Film>>() {
            }.getType());

            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeFilm(list.get(i)));
                if ( fl)
                {
                    AppContext.dbAdapter.Add(list.get(i));
                }
            }

        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_SERIALS).toString();

            ArrayList<Serial> list = new Gson().fromJson(json, new TypeToken<ArrayList<Serial>>() {
            }.getType());



            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeSerial(list.get(i)));
                if ( fl)
                {
                    AppContext.dbAdapter.Add(list.get(i));
                }
            }


        }catch (Exception e){}

        try
        {
            String json = js.getJSONArray(ServerConstants.TAG_ARRAY_BOOKS).toString();

            ArrayList<Book> list = new Gson().fromJson(json, new TypeToken<ArrayList<Book>>() {
            }.getType());
            for ( int i = 0; i < list.size(); i++)
            {
                list.set(i, ConvertUtils.decodeBook(list.get(i)));
                if ( fl)
                {
                    AppContext.dbAdapter.Add(list.get(i));
                }
            }


        }catch (Exception e){}

        loadUserProfileFromDatabase();
    }

    @Override
    public void onUsersLoaded(JSONObject js)
    {

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


    private class OnClickListner implements Button.OnClickListener
    {
        @Override
        public void onClick(View v)
        {

            if ( v.getId() == R.id.btnProfFilms)
            {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra(Constants.EXTRA_USER, user_id);
               intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SHOW_USERS_FILMS);
                startActivity(intent);
            }
            else if ( v.getId() == R.id.btnProfSerials)
            {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra(Constants.EXTRA_USER, user_id);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SHOW_USERS_SERIALS);
                startActivity(intent);
            }
            else if ( v.getId() == R.id.btnProfBooks)
            {
                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra(Constants.EXTRA_USER, user_id);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SHOW_USERS_BOOKS);
                startActivity(intent);
            }
            else if ( v.getId() == R.id.btnProfConnection)
            {

            }
        }
    }

    private void loadData()
    {
        ArrayList<CasheDto> arrayList = AppContext.dbAdapter.getListCasheDto();
        arrayList.size();
        String json = SyncNewDataTask.formData();
        if ( json != null)
        {
            pb.setVisibility(View.VISIBLE);
            new SyncNewDataTask(getActivity(), json, UserProfileFragment.this).execute();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_second, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if ( item.getItemId() == R.id.menuShowNews)
        {

                Intent intent = new Intent(getActivity(), SecondActivity.class);
                intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_NEWS);
                startActivity(intent);

        }
        else
        {
            Intent intent = new Intent(getActivity(), CUActivity.class);
            intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_USERS);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private Target target = new Target()
    {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
        {
            iv.setImageBitmap(bitmap);

            try
            {
                if ( user_id != ShPrefUtils.getUserId(getActivity()))
                {
                    user.imagePath = BitmapUtils.saveAsImage(bitmap, Constants.TYPE_USERS, user_id, user_id);
                    AppContext.dbAdapter.Update(user);
                }

            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable)
        {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }


    };
}
