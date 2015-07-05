package funnytime.ru.whitemonkteam.funny_time.funnytime;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListBookFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListFilmsFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.ListSerialsFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.UserProfileFragment;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;


public class MainActivity extends ActionBarActivity
{

    private Drawer.Result drawerResult;

    /*
    1) db = 0 server = 0 Приходит уведолмдеие что нет никких данных заполните что нибудь
     */

    /*
    2) db = num server = 0
    Сначала создаются соответствующе заметки, запоминается их ид, далее создаётся последняя заметка со списком этих ид
     */
    /*
    3) db = 0 server = num
    Сначала ищется главная заметка. Далее по эим ид вытаскиваются остальные а после они уже добавляются в БД
     */
    /*
    4) db = num server = num

    Два вариента
    1) Данные обновлены только на текущем устройстве. Изменяются текущие заметки и добавлются новые
    2) Даные на сервер поступили с другого устройства. В этом случае сначала считываются заметки с сервера и добавляются в бд а затем уже из бд на сервер
     */

    // в бд нужно добавить поле note_id

    /*
    сюжет
    игра актеров
    художественное оформление

     */

    // интерактивная подсистема сбора хранения и отображения информации

   static Fragment fragment;
     FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        fragmentManager = getFragmentManager();

       drawerResult =  new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_films).withIcon(FontAwesome.Icon.faw_film).withIdentifier(0),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_serials).withIcon(FontAwesome.Icon.faw_file_movie_o).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_books).withIcon(FontAwesome.Icon.faw_book).withIdentifier(2),

                        new SectionDrawerItem().withName(R.string.drawer_item_statistic),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_statistic_saw).withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_statistic_raiting).withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_statistic_duration).withIcon(FontAwesome.Icon.faw_clock_o).withIdentifier(5),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_my_accounts).withIcon(FontAwesome.Icon.faw_user).withIdentifier(6)

                      /*  new PrimaryDrawerItem().withName(R.string.drawer_item_films).withIcon(FontAwesome.Icon.faw_film).withIdentifier(0),
                        new PrimaryDrawerItem().withName("Serials").withIcon(FontAwesome.Icon.faw_file_movie_o).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Books").withIcon(FontAwesome.Icon.faw_book).withIdentifier(2),

                        new SectionDrawerItem().withName("Statistic"),
                        new SecondaryDrawerItem().withName("Watched").withIcon(FontAwesome.Icon.faw_eye).withIdentifier(3),
                        new SecondaryDrawerItem().withName("Raiting").withIcon(FontAwesome.Icon.faw_bar_chart).withIdentifier(4),
                        new SecondaryDrawerItem().withName("Duration").withIcon(FontAwesome.Icon.faw_clock_o).withIdentifier(5),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("My account").withIcon(FontAwesome.Icon.faw_user).withIdentifier(6)*/
                        )
                                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int id, long l, IDrawerItem iDrawerItem) {

                                        if (id == 1) {
                                            fragment = new ListFilmsFragment();
                                            ShPrefUtils.setLastPage(MainActivity.this, 1);
                                            fragmentManager.beginTransaction()
                                                    .replace(R.id.container, fragment).commit();

                                            getSupportActionBar().setTitle(R.string.drawer_item_films);
                                        } else if (id == 2) {
                                            fragment = new ListSerialsFragment();
                                            ShPrefUtils.setLastPage(MainActivity.this, 2);
                                            fragmentManager.beginTransaction()
                                                    .replace(R.id.container, fragment).commit();
                                            getSupportActionBar().setTitle(R.string.drawer_item_serials);
                                        } else if (id == 3) {
                                            fragment = new ListBookFragment();
                                            ShPrefUtils.setLastPage(MainActivity.this, 3);
                                            fragmentManager.beginTransaction()
                                                    .replace(R.id.container, fragment).commit();
                                            getSupportActionBar().setTitle(R.string.drawer_item_books);
                                        } else if (id == 5) {
                                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                                            intent.setAction(Constants.ACTION_TO_SECOND_ACTIVITY_DISPLAY_STATISTIC);
                                            startActivity(intent);

                                        } else if (id == 9) {
                          /* Intent intent = new Intent(MainActivity.this, LoadDataActivity.class);
                           intent.putExtra(Constants.EXTRA_ITEM, ShPrefUtils.getUserId(MainActivity.this));
                           startActivityForResult(intent, Constants.CODE_SHOW_PROFILE);*/
                                           if (ShPrefUtils.getUserId(MainActivity.this) <= 0)
                                            {
                                                if (HelpUtils.isOnline(MainActivity.this))
                                                {
                                                    Intent intent = new Intent(MainActivity.this, LoginVKActivity.class);
                                                    startActivityForResult(intent, Constants.CODE_ADD_USER);
                                                }
                                            } else
                                            {

                                                Bundle b = new Bundle();
                                                b.putLong(Constants.EXTRA_USER, ShPrefUtils.getUserId(MainActivity.this));

                                                fragment = new UserProfileFragment();

                                                fragment.setArguments(b);
                                                fragmentManager.beginTransaction()
                                                        .replace(R.id.container, fragment).commit();

                                                UserDto user = AppContext.dbAdapter.getUserById(ShPrefUtils.getUserId(MainActivity.this));

                                                getSupportActionBar().setTitle(user.first_name + " " + user.last_name);
                                            }
                                        }
                                        //else {
//
//                          /* fragmentManager.beginTransaction()
//                                   .replace(R.id.container, new ListFilmsFragment()).commit();*/
//
//                           getSupportActionBar().setTitle("");
//                           Intent intent = new Intent(MainActivity.this, CUActivity.class);
//                           intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_SERIAL);
//
//                           startActivity(intent);
//                       }
                                    }
                                })
                                .build();

       /* if ( ShPrefUtils.getLastPage(this) <= 3)
        {
            drawerResult.setSelection(ShPrefUtils.getLastPage(this));
        }
        else
        {

            fragmentManager.beginTransaction()
                    .replace(R.id.container, new ListFilmsFragment()).commit();
            getSupportActionBar().setTitle(R.string.drawer_item_films);
        }*/

      /*  Intent intent = new Intent(this, CUActivity.class);
        intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_FILM);

        startActivity(intent);*/

      if ( ShPrefUtils.getLastPage(this) == 1)
        {
            fragment = new ListFilmsFragment();
            ShPrefUtils.setLastPage(this, 1);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            getSupportActionBar().setTitle(R.string.drawer_item_films);
        }
        else  if (ShPrefUtils.getLastPage(this) == 2)
        {
            fragment = new ListSerialsFragment();
            ShPrefUtils.setLastPage(this, 2);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
            getSupportActionBar().setTitle(R.string.drawer_item_serials);
        }
        else  if (ShPrefUtils.getLastPage(this) == 3)
        {
            fragment = new ListBookFragment();
            ShPrefUtils.setLastPage(this, 3);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
            getSupportActionBar().setTitle(R.string.drawer_item_books);
        }
        else
        {
            fragment = new ListFilmsFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();
            getSupportActionBar().setTitle(R.string.drawer_item_films);
        }

       /* fragment = new StatisticFriendsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

      //  startActivity(new Intent(this, LoadDataActivity.class));

       /* android.support.v4.app.Fragment fragment1 = new LoginVkFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment1).commit();*/

     /*   Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(Constants.TYPE_ITEM, Constants.TYPE_NEWS);
        startActivity(intent);*/

       /* fragment = new ListNewsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

    /*   fragment = new LoadDataBooksFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

     /*   fragment = new LoadDataSerialsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

     /*   fragment = new LoadDataFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

     /*  fragment = new TestFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

   /*  fragment = new UserProfileFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment).commit();*/

        /*android.support.v4.app.FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new AccountsFragment()).commit();*/

     //  readFiles();

      /*  StringCrypter crypter=new StringCrypter(new byte[]{1,4,5,6,8,9,7,8});

        Film f = AppContext.dbAdapter.getFilms(0).get(0);
        String json = new Gson().toJson(f);

        String res =  crypter.encrypt(json);

        String out = crypter.decrypt(res);

        Film item =  new Gson().fromJson(out,
                new TypeToken<Film>() {
                }.getType()  );

        json += "";*/

       /* ArrayList<Film> list = AppContext.dbAdapter.getFilms();


        for ( Film f : list)
        {
            f.UserId = 0;
            AppContext.dbAdapter.Update(f);
        }

        ArrayList<Serial> lists = AppContext.dbAdapter.getSerials();


        for ( Serial s  : lists)
        {
            s.UserId = 0;
            AppContext.dbAdapter.Update(s);
        }

        ArrayList<Book> listb = AppContext.dbAdapter.getBooks();


        for (Book b  : listb)
        {
            b.UserId = 0;
            AppContext.dbAdapter.Update(b);
        }*/

      /*  Film f = new Film();
        f = list.get(0);

        ChangeItem item = new ChangeItem();
        item.UserId =  ShPrefUtils.getUserId(this);
        item.NoteId = f.NoteId;
        item.MainNoteId = ShPrefUtils.getMainFilmNoteId(this, item.UserId);
        item.Action = Constants.ADD;

        AppContext.dbAdapter.Add(item);

        f = new Film();
        f = list.get(1);

        item = new ChangeItem();
        item.UserId =  ShPrefUtils.getUserId(this);
        item.NoteId = f.NoteId;
        item.MainNoteId = ShPrefUtils.getMainFilmNoteId(this, item.UserId);
        item.Action = Constants.ADD;

        AppContext.dbAdapter.Add(item);

       // Toast.makeText(this, "id = " + id , Toast.LENGTH_SHORT).show();

        ArrayList<ChangeItem> array = AppContext.dbAdapter.getChangeItems(ShPrefUtils.getMainFilmNoteId(this, ShPrefUtils.getUserId(this)), Constants.ADD);

*/

      //  readFiles();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ( requestCode == Constants.CODE_ADD_USER && resultCode == RESULT_OK && data!= null)
        {
            ArrayList<Film> films = AppContext.dbAdapter.getFilms(ShPrefUtils.getUserId(MainActivity.this));
            ArrayList<Serial> serials = AppContext.dbAdapter.getSerials(ShPrefUtils.getUserId(MainActivity.this));
            ArrayList<Book> books = AppContext.dbAdapter.getBooks(ShPrefUtils.getUserId(MainActivity.this));

            long usert_id = data.getLongExtra(Constants.VK_USER_ID, 0);
            String token = data.getStringExtra(Constants.VK_TOKEN);

            ShPrefUtils.setUserId(MainActivity.this, usert_id);
            ShPrefUtils.setAccesToken(MainActivity.this, token);

            for ( int i = 0; i < films.size(); i++)
            {
                films.get(i).UserId = usert_id;
                AppContext.dbAdapter.Update( films.get(i));
            }

            for ( int i = 0; i < serials.size(); i++)
            {
                serials.get(i).UserId = usert_id;
                AppContext.dbAdapter.Update( serials.get(i));
            }

            for ( int i = 0; i < books.size(); i++)
            {
                books.get(i).UserId = usert_id;
                AppContext.dbAdapter.Update(books.get(i));
            }

            Intent intent = new Intent(MainActivity.this, LoadDataActivity.class);
            intent.putExtra(Constants.EXTRA_ITEM, usert_id);
            startActivityForResult(intent, Constants.CODE_SHOW_PROFILE);


        }
        if ( requestCode == Constants.CODE_SHOW_PROFILE && resultCode == RESULT_OK)
        {
            Bundle b = new Bundle();
            b.putLong(Constants.EXTRA_USER,ShPrefUtils.getUserId(MainActivity.this) );

            fragment = new UserProfileFragment();

            fragment.setArguments(b);
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            UserDto user = AppContext.dbAdapter.getUserById(ShPrefUtils.getUserId(MainActivity.this));

            getSupportActionBar().setTitle(user.first_name + " " + user.last_name);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readFiles()
    {
        String json = "";

        json = loadData("films.txt");

        ArrayList<Film> films = new Gson().fromJson(json,
                new TypeToken<ArrayList<Film>>() {
                }.getType()  );

        for ( Film f : films)
        {
            AppContext.dbAdapter.Add(f);
        }

        json = loadData("films1.txt");

         films = new Gson().fromJson(json,
                new TypeToken<ArrayList<Film>>() {
                }.getType()  );

        for ( Film f : films)
        {
            AppContext.dbAdapter.Add(f);
        }

        //-----------------------------

        json = loadData("ser.txt");
        ArrayList<Serial> serials = new Gson().fromJson(json,
                new TypeToken<ArrayList<Serial>>() {
                }.getType()  );

        for ( Serial s : serials)
        {
            AppContext.dbAdapter.Add(s);
        }

        json = loadData("ser1.txt");
       serials = new Gson().fromJson(json,
                new TypeToken<ArrayList<Serial>>() {
                }.getType()  );

        for ( Serial s : serials)
        {
            AppContext.dbAdapter.Add(s);
        }

        //---------------------------------------

        json = loadData("books.txt");
        ArrayList<Book> books = new Gson().fromJson(json,
                new TypeToken<ArrayList<Book>>() {
                }.getType()  );

        for ( Book b : books)
        {
            AppContext.dbAdapter.Add(b);
        }

        json = loadData("books1.txt");
         books = new Gson().fromJson(json,
                new TypeToken<ArrayList<Book>>() {
                }.getType()  );

        for ( Book b : books)
        {
            AppContext.dbAdapter.Add(b);
        }
    }

    private  String loadData(String inFile)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try
        {

            InputStreamReader istream = new InputStreamReader(MainActivity.this.getAssets().open(inFile));
            BufferedReader br = new BufferedReader(istream);

            String line = null;
            while ((line = br.readLine()) != null) {
                //variable line does NOT have new-line-character at the end
                // System.out.println( line );
                stringBuilder.append(line);
            }
            br.close();
        }catch (Exception e)
        {

        }
        return stringBuilder.toString();
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    }*/


    @Override
    public void onBackPressed()
    {
        boolean fl = false;
       /* if ( fragment instanceof ListFilmsFragment)
        {
            ListFilmsFragment frag = (ListFilmsFragment) fragment;
           fl = frag.closeSearch();
        }*/


        if ( !fl ) // если закрытия не было
        {
            if (drawerResult.isDrawerOpen())
            {
                drawerResult.closeDrawer();
            } else
            {
                super.onBackPressed();
            }
        }



    }
}
