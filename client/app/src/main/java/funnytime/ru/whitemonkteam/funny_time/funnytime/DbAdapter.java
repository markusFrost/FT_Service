package funnytime.ru.whitemonkteam.funny_time.funnytime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.ChangeItem;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.StatisticBook;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.StatisticGeneralItem;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.StatisticSerial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ConvertUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 21.03.2015.
 */
public class DbAdapter
{
    public static final String DB_NAME = "FunnyTime";
    public static final int DB_VERSION = 1;



    public static final String TABLE_FILM_NAME = "films";
    public static final String TABLE_SERIAL_NAME = "serials";
    public static final String TABLE_BOOK_NAME = "books";
    public static final String TABLE_STATISTIC_BOOK_NAME = "statistic_books";
    public static final String TABLE_STATISTIC_SERIAL_NAME = "statistic_serials";

    public static final String TABLE_MAIN_CHANGES = "main_changes_table1";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String MARK = "mark";
    public static final String ACTION = "action";
    public static final String PAGE = "page";
    public static final String PAGE_START = "pageStart";
    public static final String PAGE_COUNT = "pageCount";
    public static final String SER_START = "serStart";
    public static final String SER_COUNT = "serCount";
    public static final String TYPE = "tipe";
    public static final String COMMENT = "comment";
    public static final String ITEM_ID = "item_id";
    public static final String SEASON = "season";
    public static final String SERIES = "series";
    public static final String AUTHOR = "autor";
    public static final String DATE_TIME = "dateTime";
    public static final String DATE_CHANGE = "dateChange";
    public static final String DATE_REMEMBER = "dateRemember";
    public static final String IMAGE_URL = "image_url";
    public static final String IMAGE_PATH = "image_path";
    public static final String IS_PRIVATE = "is_private";
    public static final String IS_SAW_TODAY = "is_saw_today";

    public static final String USER_ID = "user_id";
    public static final String NOTE_ID = "note_id";
    public static final String MAIN_NOTE_ID = "main_note_id";

    public static final String TABLE_USERS = "user";
    public static final String LAST_NAME = "lastName";
    public static final String FRIENDS = "friends";

    Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DbAdapter ( Context context)
    {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getReadableDatabase();

      //  clearCashTable();

      /*  String drop = " drop table ";
        String sql =  drop + TABLE_FILM_NAME;
        db.execSQL(sql);
        sql =  drop + TABLE_SERIAL_NAME;
        db.execSQL(sql);
        sql =  drop + TABLE_BOOK_NAME;
        db.execSQL(sql);*/



       /* String sql = " delete from " + TABLE_BOOK_NAME + " where " + USER_ID + " = " + ShPrefUtils.getUserId(context);
        db.execSQL(sql);*/

      //  ShPrefUtils.setUserId(context, 0);

      // String sql = "" ;
     /*  sql = " drop table " + TABLE_FILM_NAME + "1";
        db.execSQL(sql);

        sql = " drop table " + TABLE_SERIAL_NAME+ "1";
        db.execSQL(sql);

        sql = " drop table " + TABLE_BOOK_NAME+ "1";
        db.execSQL(sql);*/

    /*   sql = " drop table " + TABLE_FILM_NAME ;
        db.execSQL(sql);

        sql = " drop table " + TABLE_SERIAL_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_BOOK_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_STATISTIC_BOOK_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_STATISTIC_SERIAL_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_MAIN_CHANGES;
        db.execSQL(sql);
*/

      /* ArrayList<Film> listf = getFilms();

        for ( Film f : listf)
        {
            if ( f.Action == Constants.TYPE_SAW)
            {
                    f.DateTime = HelpUtils.getTimeMills(f.DateTime);
                Update(f);
            }
        }*/

      /*  ArrayList<Serial> listS = getSerials();

        for ( Serial s : listS)
        {
            if ( s.Action == Constants.TYPE_SAW)
            {
                s.DateTime = HelpUtils.getTimeMills(s.DateTime);
                Update(s);
            }
        }

        ArrayList<Book> listB = getBooks();

        for ( Book b : listB)
        {
            if ( b.Action == Constants.TYPE_SAW)
            {
                b.DateTime = HelpUtils.getTimeMills(b.DateTime);
                Update(b);
            }
        }*/

       /* ArrayList<Serial> lists = getSerials();
        ArrayList<Book> listb = getBooks();
        ArrayList<StatisticSerial> listStatS = getStatisticSerialsAll();
        ArrayList<StatisticBook> listStatB = getStatisticBooksAll();

        int count = listb.size();

        sql = " drop table " + TABLE_FILM_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_SERIAL_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_BOOK_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_STATISTIC_BOOK_NAME;
        db.execSQL(sql);

        sql = " drop table " + TABLE_STATISTIC_SERIAL_NAME;
        db.execSQL(sql);*/

        createFilmTable();
        createSerialTable();

        createBookTable();

        createBookStatisticTable();

        createSerialStatisticTable();

        createCashItemsTable();

        createUsersTable();

      /*  for ( Film f : listf)
        {
            Add(f);

        }

        for ( Serial s : lists)
        {
            Add(s);
            StatisticSerial item = searchStatSerial(listStatS, Constants.TYPE_SERIAL,s.Id);
            if ( item != null)
            {
                 Add(item);
            }
        }

        for ( Book b : listb)
        {
            Add(b);
            StatisticBook item = searchStatBook(listStatB, Constants.TYPE_BOOK,b.Id);
            if ( item != null)
            {
                Add(item);
            }
        }*/


       // db.delete(TABLE_FILM_NAME, ID + " < " + 1000, null);

    }

    private StatisticSerial searchStatSerial(ArrayList<StatisticSerial> list, int type, long id)
    {
       for ( StatisticSerial s : list)
       {
           if ( s.Type == type && s.ItemId == id)
           {
               return s;
           }
       }
        return null;
    }

    private StatisticBook searchStatBook(ArrayList<StatisticBook> list, int type, long id)
    {
        for ( StatisticBook b : list)
        {
            if ( b.Type == type && b.ItemId == id)
            {
                return b;
            }
        }
        return null;
    }

    public long Add(Object obj)
    {
        if (obj instanceof Film)
        {
            Film item = (Film) obj;
           // CasheItemsUtils.Add(context, item, Constants.ADD);
            return	addFilm(item);
        }
        else if (obj instanceof Serial)
        {
            Serial item = (Serial) obj;
          //  CasheItemsUtils.Add(context, item, Constants.ADD);
             return addSerial(item);
        }
        else if (obj instanceof Book)
        {
            Book item = (Book) obj;
          //  CasheItemsUtils.Add(context, item, Constants.ADD);
            return addBook(item);
        }
        else if ( obj instanceof StatisticBook)
        {
            StatisticBook item = (StatisticBook) obj;
            return addStatisticBook(item);
        }
        else if ( obj instanceof StatisticSerial)
        {
            StatisticSerial item = (StatisticSerial) obj;
            return addStatisticSerial(item);
        }
        else if ( obj instanceof CasheDto)
        {
          CasheDto item = (CasheDto) obj;
            return addCacheItem(item);
        }
        else if ( obj instanceof UserDto)
        {
            UserDto item = (UserDto) obj;
            return addUser(item);
        }
        return 0;

    }




    public void Update(Object o)
    {
        if ( o instanceof Film)
        {
            updateFilm(o);
        }
        else if ( o instanceof Serial)
        {
           updateSerial(o);
        }
        else if ( o instanceof Book)
        {
           updateBook(o);
        }
        else if ( o instanceof StatisticBook)
        {
            StatisticBook item = (StatisticBook) o;
             updateStatisticBook(item);
        }
        else if ( o instanceof StatisticSerial)
        {
            StatisticSerial item = (StatisticSerial) o;
            updateStatisticSerial(item);
        }
        else if ( o instanceof UserDto)
        {
            UserDto item = (UserDto) o;
             updateUser(item);
        }
    }




    public void Delete(Object o)
    {
        if ( o instanceof Film)
        {
            deleteFilm(o);
        }
        else if ( o instanceof Serial)
        {
            deleteSerial(o);
        }
        else if ( o instanceof Book)
        {
            deleteBook(o);
        }
        else if ( o instanceof UserDto)
        {
            deleteUser(o);
        }
    }



    private long addFilm(Film item)
    {
        //1092598954   1426935574009
        int is_private = 0;
        if ( item.IsPrivate)
        {
            is_private = 1;
        }
        int is_saw_today = 0;
        if ( item.IsSawToday)
        {
            is_saw_today = 1;
        }

        ContentValues cv = new ContentValues();
        cv.put(NAME, item.Name);
        cv.put(MARK, item.Mark);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(ACTION, item.Action);
        cv.put(COMMENT, item.Comment);
        cv.put(DATE_CHANGE, item.DateChange);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(DATE_REMEMBER, item.DateRemember);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(IS_PRIVATE, is_private);
        cv.put(IS_SAW_TODAY, is_saw_today);
        cv.put(USER_ID, item.UserId);
        cv.put(NOTE_ID, item.NoteId);



        long id = db.insert(TABLE_FILM_NAME   , null, cv);
        System.out.println("ID = " + id);
        return id;
    }

    //------------------------------------------

    private long addSerial(Serial item)
    {
        int is_private = 0;
        if ( item.IsPrivate)
        {
            is_private = 1;
        }
        int is_saw_today = 0;
        if ( item.IsSawToday)
        {
            is_saw_today = 1;
        }

        ContentValues cv = new ContentValues();
        cv.put(NAME, item.Name);
        cv.put(MARK, item.Mark);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(ACTION, item.Action);
        cv.put(DATE_CHANGE, item.DateChange);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(COMMENT, item.Comment);
        cv.put(DATE_REMEMBER, item.DateRemember);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(IS_PRIVATE, is_private);
        cv.put(IS_SAW_TODAY, is_saw_today);
        cv.put(SEASON, item.Season);
        cv.put(SERIES, item.Series);
        cv.put(USER_ID, item.UserId);
        cv.put(NOTE_ID, item.NoteId);


        long id = db.insert(TABLE_SERIAL_NAME   , null, cv);
        System.out.println("ID = " + id);
        return id;
    }

    private long addBook(Book item)
    {
        int is_private = 0;
        if ( item.IsPrivate)
        {
            is_private = 1;
        }
        int is_saw_today = 0;
        if ( item.IsSawToday)
        {
            is_saw_today = 1;
        }

        ContentValues cv = new ContentValues();

        cv.put(NAME, item.Name);
        cv.put(AUTHOR, item.Author);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(MARK, item.Mark);
        cv.put(ACTION, item.Action);
        cv.put(DATE_CHANGE, item.DateChange);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(COMMENT, item.Comment);
        cv.put(DATE_REMEMBER, item.DateRemember);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(IS_PRIVATE, is_private);
        cv.put(IS_SAW_TODAY, is_saw_today);
        cv.put(PAGE, item.Page);
        cv.put(USER_ID, item.UserId);
        cv.put(NOTE_ID, item.NoteId);



        long id = db.insert(TABLE_BOOK_NAME   , null, cv);
        System.out.println("ID = " + id);
        return id;
    }

  private long addStatisticBook (StatisticBook item)
   {
       ContentValues cv = new ContentValues();
       cv.put(TYPE, item.Type);
       cv.put(ITEM_ID, item.ItemId);
       cv.put(DATE_TIME, item.DateTime);
       cv.put(PAGE_COUNT, item.PageCount);
       cv.put(PAGE_START, item.PageStart);

       long id = db.insert(TABLE_STATISTIC_BOOK_NAME, null, cv);

       return id;
   }

    private long addStatisticSerial(StatisticSerial item)
    {
        ContentValues cv = new ContentValues();
        cv.put(TYPE, item.Type);
        cv.put(ITEM_ID, item.ItemId);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(SER_COUNT, item.SerCount);
        cv.put(SER_START, item.SerStart);
        cv.put(SEASON, item.Season);
        cv.put(SERIES, item.Series);



        long id = db.insert(TABLE_STATISTIC_SERIAL_NAME, null, cv);

        return id;
    }

    private long addCacheItem(CasheDto item)
    {
        ContentValues cv = new ContentValues();
        cv.put(NOTE_ID, item.NoteId);
        cv.put(ACTION, item.Action);
        cv.put(TYPE, item.Type);

        long id = db.insert(TABLE_MAIN_CHANGES, null, cv);

        return id;


    }

    private long addUser(UserDto item)
    {
        UserDto user = getUserById(item.uid);
        if (  user != null)
        {
            return -1;
        } // if user already exists
        ContentValues cv = new ContentValues();
        cv.put(NAME, item.first_name);
        cv.put(LAST_NAME, item.last_name);
        cv.put(IMAGE_PATH, item.imagePath);
        cv.put(IMAGE_URL, item.photo_200);
        cv.put(USER_ID, item.uid);
        cv.put(FRIENDS, item.friends);

        long id = db.insert(TABLE_USERS, null, cv);

        return id;


    }

    //--------------------------------------------

    private void updateFilm(Object o)
    {
        Film item = (Film) o;
      //  CasheItemsUtils.Add(context, item, Constants.UPDATE);

        int is_private = 0;
        if ( item.IsPrivate)
        {
            is_private = 1;
        }
        int is_saw_today = 0;
        if ( item.IsSawToday)
        {
            is_saw_today = 1;
        }

        ContentValues cv = new ContentValues();
        cv.put(NAME, item.Name);
        cv.put(MARK, item.Mark);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(ACTION, item.Action);
        cv.put(DATE_CHANGE, item.DateChange);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(DATE_REMEMBER, item.DateRemember);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(IS_PRIVATE, is_private);
        cv.put(IS_SAW_TODAY, is_saw_today);
        cv.put(USER_ID, item.UserId);
        cv.put(NOTE_ID, item.NoteId);
        long count = db.update(TABLE_FILM_NAME, cv, ID + " = ?",
                new String[] { item.Id + "" });
       // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    private void updateSerial(Object obj)
    {
        Serial item = (Serial) obj;
        //CasheItemsUtils.Add(context, item, Constants.UPDATE);
        int is_private = 0;
        if ( item.IsPrivate)
        {
            is_private = 1;
        }
        int is_saw_today = 0;
        if ( item.IsSawToday)
        {
            is_saw_today = 1;
        }

        ContentValues cv = new ContentValues();
        cv.put(NAME, item.Name);
        cv.put(MARK, item.Mark);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(ACTION, item.Action);
        cv.put(DATE_CHANGE, item.DateChange);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(DATE_REMEMBER, item.DateRemember);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(IS_PRIVATE, is_private);
        cv.put(IS_SAW_TODAY, is_saw_today);
        cv.put(SEASON, item.Season);
        cv.put(SERIES, item.Series);
        cv.put(USER_ID, item.UserId);
        cv.put(NOTE_ID, item.NoteId);

        long count = db.update(TABLE_SERIAL_NAME, cv, ID + " = ?",
                new String[] { item.Id + "" });
       // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    private void updateBook(Object obj)
    {
        Book item = (Book) obj;
        //CasheItemsUtils.Add(context, item, Constants.UPDATE);
        int is_private = 0;
        if ( item.IsPrivate)
        {
            is_private = 1;
        }
        int is_saw_today = 0;
        if ( item.IsSawToday)
        {
            is_saw_today = 1;
        }

        ContentValues cv = new ContentValues();
        cv.put(NAME, item.Name);
        cv.put(AUTHOR, item.Author);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(MARK, item.Mark);
        cv.put(ACTION, item.Action);
        cv.put(DATE_CHANGE, item.DateChange);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(DATE_REMEMBER, item.DateRemember);
        cv.put(IMAGE_PATH, item.ImagePath);
        cv.put(IMAGE_URL, item.ImageURL);
        cv.put(IS_PRIVATE, is_private);
        cv.put(IS_SAW_TODAY, is_saw_today);
        cv.put(PAGE, item.Page);
        cv.put(USER_ID, item.UserId);
        cv.put(NOTE_ID, item.NoteId);


        long count = db.update(TABLE_BOOK_NAME, cv, ID + " = ?",
                new String[] { item.Id + "" });
      //  Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

  private void updateStatisticBook (StatisticBook item)
   {
       ContentValues cv = new ContentValues();
       cv.put(TYPE, item.Type);
       cv.put(ITEM_ID, item.ItemId);
       cv.put(DATE_TIME, item.DateTime);
       cv.put(PAGE_COUNT, item.PageCount);
       cv.put(PAGE_START, item.PageStart);

       long count = db.update(TABLE_STATISTIC_BOOK_NAME, cv, ID + " = ?",
               new String[]{item.Id + ""});
       //Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
   }

    private long updateUser(UserDto item)
    {
        ContentValues cv = new ContentValues();
        cv.put(NAME, item.first_name);
        cv.put(LAST_NAME, item.last_name);
        cv.put(IMAGE_PATH, item.imagePath);
        cv.put(IMAGE_URL, item.photo_200);
        cv.put(USER_ID, item.uid);
        cv.put(FRIENDS, item.friends);

        long count = db.update(TABLE_USERS, cv, USER_ID + " = ?",
                new String[]{item.uid + ""});

        return count;


    }

    private void updateStatisticSerial(StatisticSerial item)
    {
        ContentValues cv = new ContentValues();
        cv.put(TYPE, item.Type);
        cv.put(ITEM_ID, item.ItemId);
        cv.put(DATE_TIME, item.DateTime);
        cv.put(SER_COUNT, item.SerCount);
        cv.put(SER_START, item.SerStart);
        cv.put(SEASON, item.Season);
        cv.put(SERIES, item.Series);

        long count = db.update(TABLE_STATISTIC_SERIAL_NAME, cv, ID + " = ?",
                new String[]{item.Id + ""});
      //  Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();

    }

    public void updateFilmByNoteId(long serverId, long clentId) // id in server, id in client
    {
        ContentValues cv = new ContentValues();

        cv.put(NOTE_ID, serverId);
        long count = db.update(TABLE_FILM_NAME, cv, ID + " = ?",
                new String[]{clentId + ""});
        // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    public void updateSerialByNoteId(long serverId, long clentId) // id in server, id in client
    {
        ContentValues cv = new ContentValues();

        cv.put(NOTE_ID, serverId);
        long count = db.update(TABLE_SERIAL_NAME, cv, ID + " = ?",
                new String[]{clentId + ""});
        // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    public void updateBookByNoteId(long serverId, long clentId) // id in server, id in client
    {
        ContentValues cv = new ContentValues();

        cv.put(NOTE_ID, serverId);
        long count = db.update(TABLE_BOOK_NAME, cv, ID + " = ?",
                new String[]{clentId + ""});
        // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------------------------------



    private void deleteFilm(Object o)
    {
        Film f = (Film) o;
        //CasheItemsUtils.Add(context, f, Constants.DELETE);
        long count = db.delete(TABLE_FILM_NAME, ID + " = " + f.Id, null);
        File file = new File (f.ImagePath);
        if (file.exists ())
        {
            file.delete ();
        }
        //
       // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    private void deleteSerial(Object obj)
    {
        Serial item = (Serial) obj;
        //CasheItemsUtils.Add(context, item, Constants.DELETE);
        long count = db.delete(TABLE_SERIAL_NAME, ID + " = " + item.Id, null);
        File file = new File (item.ImagePath);
        if (file.exists ())
        {
            file.delete ();
        }

      //  Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();

        deleteStatisticSerial(item);
    }

    private void deleteBook(Object obj)
    {
        Book item = (Book) obj;
        //CasheItemsUtils.Add(context, item, Constants.DELETE);
        long count = db.delete(TABLE_BOOK_NAME, ID + " = " + item.Id, null);
        //Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
        File file = new File (item.ImagePath);
        if (file.exists ())
        {
            file.delete ();
        }

        deleteStatisticBook(item);

    }

    private void deleteUser(Object obj)
    {
        UserDto item = (UserDto) obj;

        long count = db.delete(TABLE_BOOK_NAME, USER_ID + " = " + item.uid, null);


    }

    public void clearFriendsData(long user_id)
    {
        if ( user_id != ShPrefUtils.getUserId(context))
        {
            db.delete(TABLE_FILM_NAME, USER_ID + " = " + user_id, null);
            db.delete(TABLE_SERIAL_NAME, USER_ID + " = " + user_id, null);
            db.delete(TABLE_BOOK_NAME, USER_ID + " = " + user_id, null);
        }
    }
    public void clearFriendsData(long user_id, int type)
    {
        if ( user_id != ShPrefUtils.getUserId(context))
        {
        if ( type == Constants.TYPE_FILM)
        {
            db.delete(TABLE_FILM_NAME, USER_ID + " = " + user_id, null);
        }
        else  if ( type == Constants.TYPE_SERIAL)
        {
            db.delete(TABLE_SERIAL_NAME, USER_ID + " = " + user_id, null);
        }
        else  if ( type == Constants.TYPE_BOOK)
        {
            db.delete(TABLE_BOOK_NAME, USER_ID + " = " + user_id, null);
        }

        }
    }

    private void deleteStatisticBook(Book b)
    {
        long count = db.delete(TABLE_STATISTIC_BOOK_NAME, ITEM_ID + " = " + b.Id + " and " + TYPE + " = " + Constants.TYPE_BOOK, null);
        //Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    private void deleteStatisticSerial(Serial s)
    {
        long count = db.delete(TABLE_STATISTIC_SERIAL_NAME, ITEM_ID + " = " + s.Id + " and " + TYPE + " = " + Constants.TYPE_SERIAL, null);
       // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

   public void deleteCasheItems(int type)
    {

        long count = db.delete(TABLE_FILM_NAME, TYPE + " = " + type, null);
        //
        // Toast.makeText(context, "count = " + count, Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------------------------------

    public ArrayList<Film> getStatisticFilm(ArrayList<AnswerDto> arrayList)
    {
        ArrayList<Film> list = new ArrayList<>();

        String sql = " select * from " + TABLE_FILM_NAME + " where " + " ( " + ACTION + " <> " + Constants.TYPE_WANT_TO_SEE + " ) ";
        for ( int i = 0; i < arrayList.size(); i++ )
        {
            if ( i == 0)
            {
                sql += " and " + " ( ( " + USER_ID + " = " + arrayList.get(i).ItemId + " ) " ;
            }
            else
            {
                sql += " or  ( " + USER_ID + " = " + arrayList.get(i).ItemId + " ) " ;
            }
        }

        if ( arrayList.size() > 0)
        {
            sql += " ) ";
        }

        sql +=  " order by " + DATE_CHANGE + " desc ";

        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Film item = new Film();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));
                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Serial> getStatisticSerials(ArrayList<AnswerDto> arrayList)
    {
        ArrayList<Serial> list = new ArrayList<>();

        String sql = " select * from " + TABLE_SERIAL_NAME + " where " + " ( " + ACTION + " <> " + Constants.TYPE_WANT_TO_SEE + " ) " ;
        for ( int i = 0; i < arrayList.size(); i++ )
        {
            if ( i == 0)
            {
                sql += " and " + " ( ( " + USER_ID + " = " + arrayList.get(i).ItemId + " ) " ;
            }
            else
            {
                sql += " or  ( " + USER_ID + " = " + arrayList.get(i).ItemId + " ) " ;
            }
        }

        if ( arrayList.size() > 0)
        {
            sql += " ) ";
        }


        sql +=   " order by " + DATE_CHANGE + " desc ";

        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Serial item = new Serial();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));

                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;

    }

    public ArrayList<Book> getStatisticBook(ArrayList<AnswerDto> arrayList)
    {
        ArrayList<Book> list = new ArrayList<>();

        String sql = " select * from " + TABLE_BOOK_NAME + " where " + " ( " + ACTION + " <> " + Constants.TYPE_WANT_TO_SEE + " ) ";
        for ( int i = 0; i < arrayList.size(); i++ )
        {
            if ( i == 0)
            {
                sql += " and " + " ( ( " + USER_ID + " = " + arrayList.get(i).ItemId + " ) " ;
            }
            else
            {
                sql += " or  ( " + USER_ID + " = " + arrayList.get(i).ItemId + " ) " ;
            }
        }

        if ( arrayList.size() > 0)
        {
            sql += " ) ";
        }

        sql +=  " order by " + DATE_CHANGE + " desc ";

        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));

                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<AnswerDto> getListFriends(long user_id)
    {
        UserDto user = getUserById(user_id);
        String json = user.friends;

        if ( json == null || json.equals(""))
        {
            return new ArrayList<AnswerDto>();
        }

       return  new Gson().fromJson(user.friends, new TypeToken<ArrayList<AnswerDto>>() {
        }.getType());

    }



    public UserDto getUserById(long user_id)
    {
        String sql = "select * from " + TABLE_USERS+ " where " + USER_ID + " = " + user_id;
        Cursor c = db.rawQuery(sql, null);
        UserDto item = null;
        if ( c.moveToFirst() )
        {
            item = new UserDto();
            item.first_name = c.getString(c.getColumnIndex(NAME));
            item.last_name = c.getString(c.getColumnIndex(LAST_NAME));
            item.uid = user_id;
            item.imagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
            item.photo_200 = c.getString(c.getColumnIndex(IMAGE_URL));
            item.friends = c.getString(c.getColumnIndex(FRIENDS));
        }
        else
        {
            item = null;
        }

        return item;
    }

    public ArrayList<UserDto> getUsers()
    {
        String sql = "select * from " + TABLE_USERS;
        Cursor c = db.rawQuery(sql, null);
       ArrayList<UserDto> list = new ArrayList<>();
        if ( c.moveToFirst() )
        {
            do {

                UserDto item = new UserDto();
                item.first_name = c.getString(c.getColumnIndex(NAME));
                item.last_name = c.getString(c.getColumnIndex(LAST_NAME));
                item.uid = c.getLong(c.getColumnIndex(USER_ID));
                item.imagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.photo_200 = c.getString(c.getColumnIndex(IMAGE_URL));
                item.friends = c.getString(c.getColumnIndex(FRIENDS));

                if (item.uid != ShPrefUtils.getUserId(context)) {
                    list.add(item);
                } else {
                    // list.add(item);
                }
            }while (c.moveToNext());
        }
       return list;
    }

    public ArrayList<AnswerDto> getUsersIds()
    {
        String sql = "select * from " + TABLE_USERS;
        Cursor c = db.rawQuery(sql, null);
        ArrayList<AnswerDto> list = new ArrayList<>();
        if ( c.moveToFirst() )
        {
            do
            {

                long uid = c.getLong(c.getColumnIndex(USER_ID));
                if (uid != ShPrefUtils.getUserId(context)) {
                    AnswerDto item = new AnswerDto();
                    item.ItemId = uid;
                    list.add(item);
                }
            }while (c.moveToNext());
        }
        return list;
    }

    public  boolean isFriend( long user_id)
    {
        if ( user_id == ShPrefUtils.getUserId(context)) { return false; }
        String sql = "select * from " + TABLE_USERS+ " where " + USER_ID + " = " + user_id;
        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst() )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public ArrayList<CasheDto > getListCasheDto()
    {
        ArrayList<CasheDto> list = new ArrayList<>();
        String sql = " select * from " + TABLE_MAIN_CHANGES;

        Cursor c = db.rawQuery(sql,null);
        if ( c.moveToFirst())
        {
            do
            {
                CasheDto item = new CasheDto();
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.Type = c.getInt(c.getColumnIndex(TYPE));
                list.add(item);
            }while (c.moveToNext());
        }

        return list;
    }

    public  ArrayList<Film> getCashFilmList( int action)
    {
        int type = Constants.TYPE_FILM;

        ArrayList<Film> array = new ArrayList<>();

        String sql =  "select * from " + TABLE_MAIN_CHANGES + " where " + " ( " + ACTION + " = " + action + " ) " + " and " + " ( " + TYPE + " = " + type + " ) ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                long note_id =  c.getLong(c.getColumnIndex(NOTE_ID));
                if ( action == Constants.DELETE)
                {
                    Film item = new Film();
                    item.NoteId = note_id;
                    array.add(item);

                }
                else
                {
                    Film item = getFilmByNoteId(ShPrefUtils.getUserId(context), note_id);
                    if ( item != null) {
                        item = ConvertUtils.encyptFilm(item);
                        array.add(item);
                    }
                }

            } while (c.moveToNext());
        }
        return array;

    }

    public void clearCashTable()
    {
        String sql = " drop table "+ TABLE_MAIN_CHANGES;
        db.execSQL(sql);

        createCashItemsTable();
    }

    public  ArrayList<Serial> getCashSerialList( int action)
    {
        int type = Constants.TYPE_SERIAL;

        ArrayList<Serial> array = new ArrayList<>();

        String sql =  "select * from " + TABLE_MAIN_CHANGES + " where " + " ( " + ACTION + " = " + action + " ) " + " and " + " ( " + TYPE + " = " + type + " ) ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                long note_id =  c.getLong(c.getColumnIndex(NOTE_ID));
                if ( action == Constants.DELETE)
                {
                    Serial item = new Serial();
                    item.NoteId = note_id;
                    array.add(item);
                }
                else
                {
                    Serial item = getSerialByNoteId(ShPrefUtils.getUserId(context), note_id);
                    if ( item != null) {
                        item = ConvertUtils.encyptSerial(item);
                        array.add(item);
                    }
                }

            } while (c.moveToNext());
        }
        return array;

    }

    public  ArrayList<Book> getCashBookList( int action)
    {
        int type = Constants.TYPE_BOOK;

        ArrayList<Book> array = new ArrayList<>();

        String sql =  "select * from " + TABLE_MAIN_CHANGES + " where " + " ( " + ACTION + " = " + action + " ) " + " and " + " ( " + TYPE + " = " + type + " ) ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                long note_id =  c.getLong(c.getColumnIndex(NOTE_ID));
                if ( action == Constants.DELETE)
                {
                    Book item = new Book();
                    item.NoteId = note_id;
                    array.add(item);
                }
                else
                {
                    Book item = getBookByNoteId(ShPrefUtils.getUserId(context), note_id);
                    if ( item != null) {
                        item = ConvertUtils.encyptBook(item);
                        array.add(item);
                    }
                }

            } while (c.moveToNext());
        }
        return array;

    }



    public ArrayList<Film> getFilms(long user_id)
    {
        ArrayList<Film> list = new ArrayList<Film>();
        String sql = "select * from " + TABLE_FILM_NAME + " where " + USER_ID + " = " + user_id + " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Film item = new Film();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));
                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }


    public ArrayList<Film> getFilmsForNews(long user_id)
    {
        ArrayList<Film> list = new ArrayList<Film>();
        String sql = "select * from " + TABLE_FILM_NAME + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " +
                " and " + " ( " + ACTION + " <> " + Constants.TYPE_WANT_TO_SEE + " ) " +
                " Order by " + DATE_CHANGE + " desc " +
                " limit 0, 30 ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Film item = new Film();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Film> getPublicFilms(long user_id)
    {
        ArrayList<Film> list = new ArrayList<Film>();
        String sql = "select * from " + TABLE_FILM_NAME + " where "  + " ( " +  USER_ID + " = " + user_id +  " ) " + " and " +
                " ( " + IS_PRIVATE + " = " + " 0 " + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Film item = new Film();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<String> getFilmsNames(long user_id)
    {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select " + USER_ID + " , " + NAME + " from " + TABLE_FILM_NAME + " where " + USER_ID + " = " + user_id ;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {

               String name = c.getString(c.getColumnIndex(NAME));

                list.add(name);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Film> getFilmsByDate(long user_id, long time)
    {

        ArrayList<Film> list = new ArrayList<Film>();
        String sql = "select * from " + TABLE_FILM_NAME +
                " where " +  " ( " +  USER_ID + " = " + user_id + " ) " + " and " + " ( " + DATE_TIME + " = " + time + " ) " +
                 " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Film item = new Film();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }


    public ArrayList<Film> getFilms()
    {
        ArrayList<Film> list = new ArrayList<Film>();
        String sql = "select * from " + TABLE_FILM_NAME + " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Film item = new Film();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public Film getFilm(long user_id, long note_id)
    {
        String sql = "select * from " + TABLE_FILM_NAME + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " + " and " +
                " ( " + NOTE_ID + " = " + note_id + " ) ";
        Cursor c = db.rawQuery(sql, null);
        Film item = new Film();
        if (c.moveToFirst())
        {

                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
               item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }

        }

        return item;
    }

    public Film getFilmByNoteId(long user_id, long note_id)
    {
        String sql = "select * from " + TABLE_FILM_NAME + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " + " and " +
                " ( " + ID + " = " + note_id + " ) ";
        Cursor c = db.rawQuery(sql, null);
        Film item = new Film();
        if (c.moveToFirst())
        {

            item.Id = c.getLong(c.getColumnIndex(ID));
            item.Name = c.getString(c.getColumnIndex(NAME));
            item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
            item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
            item.Mark = c.getFloat(c.getColumnIndex(MARK));
            item.Action = c.getInt(c.getColumnIndex(ACTION));
            item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
            item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
            int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

            if ( is_private == 0)
            {
                item.IsPrivate = false;
            }
            else
            {
                item.IsPrivate = true;
            }

            if ( is_saw_today == 0)
            {
                item.IsSawToday = false;
            }
            else
            {
                item.IsSawToday = true;
            }

        }

        return item;
    }

    public Film getFilmById(long user_id, long _id)
    {
        String sql = "select * from " + TABLE_FILM_NAME + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " + " and " +
                " ( " + ID + " = " + _id + " ) ";
        Cursor c = db.rawQuery(sql, null);
        Film item = new Film();
        if (c.moveToFirst())
        {

            item.Id = c.getLong(c.getColumnIndex(ID));
            item.Name = c.getString(c.getColumnIndex(NAME));
            item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
            item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
            item.Mark = c.getFloat(c.getColumnIndex(MARK));
            item.Action = c.getInt(c.getColumnIndex(ACTION));
            item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
            item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
            int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

            if ( is_private == 0)
            {
                item.IsPrivate = false;
            }
            else
            {
                item.IsPrivate = true;
            }

            if ( is_saw_today == 0)
            {
                item.IsSawToday = false;
            }
            else
            {
                item.IsSawToday = true;
            }

        }

        return item;
    }

        public ArrayList<Serial> getSerials(long user_id)
    {
        ArrayList<Serial> list = new ArrayList<Serial>();
        String sql = "select * from " + TABLE_SERIAL_NAME  + " where " + USER_ID + " = " + user_id +  " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Serial item = new Serial();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));

                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }


    public ArrayList<Serial> getPublicSerials(long user_id)
    {
        ArrayList<Serial> list = new ArrayList<Serial>();
        String sql = "select * from " + TABLE_SERIAL_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id  + " ) " +
                " and " + " ( " + IS_PRIVATE + " = " + " 0 " + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Serial item = new Serial();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));

                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public Serial getSerialById(long user_id, long _id)
    {

        String sql = "select * from " + TABLE_SERIAL_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id  + " ) " +
                " and " + " ( " + ID + " = " + _id + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {

            Serial item = new Serial();
            item.Id = c.getLong(c.getColumnIndex(ID));
            item.Name = c.getString(c.getColumnIndex(NAME));
            item.Mark = c.getFloat(c.getColumnIndex(MARK));
            item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
            item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
            item.Action = c.getInt(c.getColumnIndex(ACTION));
            item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
            item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            item.Season = c.getInt(c.getColumnIndex(SEASON));
            item.Series = c.getInt(c.getColumnIndex(SERIES));

            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
            int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

            if (is_private == 0) {
                item.IsPrivate = false;
            } else {
                item.IsPrivate = true;
            }

            if (is_saw_today == 0) {
                item.IsSawToday = false;
            } else {
                item.IsSawToday = true;
            }

            return item;
        }
        else
        {
            return  null;
        }



    }

    public Serial getSerialByNoteId(long user_id, long note_id)
    {

        String sql = "select * from " + TABLE_SERIAL_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id  + " ) " +
                " and " + " ( " + ID + " = " + note_id + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {

            Serial item = new Serial();
            item.Id = c.getLong(c.getColumnIndex(ID));
            item.Name = c.getString(c.getColumnIndex(NAME));
            item.Mark = c.getFloat(c.getColumnIndex(MARK));
            item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
            item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
            item.Action = c.getInt(c.getColumnIndex(ACTION));
            item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
            item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            item.Season = c.getInt(c.getColumnIndex(SEASON));
            item.Series = c.getInt(c.getColumnIndex(SERIES));

            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
            int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

            if (is_private == 0) {
                item.IsPrivate = false;
            } else {
                item.IsPrivate = true;
            }

            if (is_saw_today == 0) {
                item.IsSawToday = false;
            } else {
                item.IsSawToday = true;
            }

            return item;
        }
        else
        {
            return  null;
        }



    }


    public ArrayList<Serial> getSerialsForNews(long user_id)
    {
        ArrayList<Serial> list = new ArrayList<Serial>();
        String sql = "select * from " + TABLE_SERIAL_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " +
                " and " + " ( " + ACTION + " <> " + Constants.TYPE_WANT_TO_SEE + " ) " +
                " Order by " + DATE_CHANGE + " desc " +
                " limit 0, 30 ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Serial item = new Serial();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }


    public ArrayList<String> geterialsNames(long user_id)
    {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select " + USER_ID + " , " + NAME + " from " + TABLE_SERIAL_NAME + " where " + USER_ID + " = " + user_id ;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {

                String name = c.getString(c.getColumnIndex(NAME));

                list.add(name);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Serial> getSerialsByDate(long user_id, long time)
    {
        ArrayList<Serial> list = new ArrayList<Serial>();
        String sql = "select * from " + TABLE_SERIAL_NAME  +
                " where " +  " ( " +  USER_ID + " = " + user_id + " ) " + " and " + " ( " + DATE_TIME + " = " + time + " ) " +
                " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Serial item = new Serial();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }




    public ArrayList<Serial> getSerials()
    {
        ArrayList<Serial> list = new ArrayList<Serial>();
        String sql = "select * from " + TABLE_SERIAL_NAME  +   " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Serial item = new Serial();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
              item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Book> getBooks(long user_id)
    {
        ArrayList<Book> list = new ArrayList<Book>();
        String sql = "select * from " + TABLE_BOOK_NAME  + " where " + USER_ID + " = " + user_id + " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
               item.Comment = c.getString(c.getColumnIndex(COMMENT));

                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));

                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Book> getPublicBooks(long user_id)
    {
        ArrayList<Book> list = new ArrayList<Book>();
        String sql = "select * from " + TABLE_BOOK_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " +
                " and " + " ( " + IS_PRIVATE + " = " + " 0 " + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public Book getBookById(long user_id, long _id)
    {

        String sql = "select * from " + TABLE_BOOK_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " +
                " and " + " ( " + ID + " = " + _id + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }
            return item;
        }
        else
        {
            return null;
        }
    }

    public Book getBookByNoteId(long user_id, long note_id)
    {

        String sql = "select * from " + TABLE_BOOK_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " +
                " and " + " ( " + ID + " = " + note_id + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            Book item = new Book();
            item.Id = c.getLong(c.getColumnIndex(ID));
            item.Name = c.getString(c.getColumnIndex(NAME));
            item.Mark = c.getFloat(c.getColumnIndex(MARK));
            item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
            item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
            item.Action = c.getInt(c.getColumnIndex(ACTION));
            item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
            item.Page = c.getInt(c.getColumnIndex(PAGE));
            item.Author = c.getString(c.getColumnIndex(AUTHOR));
            item.Comment = c.getString(c.getColumnIndex(COMMENT));
            item.UserId = c.getLong(c.getColumnIndex(USER_ID));
            item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


            int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
            int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

            if ( is_private == 0)
            {
                item.IsPrivate = false;
            }
            else
            {
                item.IsPrivate = true;
            }

            if ( is_saw_today == 0)
            {
                item.IsSawToday = false;
            }
            else
            {
                item.IsSawToday = true;
            }
            return item;
        }
        else
        {
            return null;
        }
    }
    public ArrayList<String> getBooksNames(long user_id)
    {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select " + USER_ID + " , " + NAME + " from " + TABLE_BOOK_NAME + " where " + USER_ID + " = " + user_id ;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {

                String name = c.getString(c.getColumnIndex(NAME));

                list.add(name);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Book> getBooksByDate(long user_id, long time)
    {
        ArrayList<Book> list = new ArrayList<Book>();
        String sql = "select * from " + TABLE_BOOK_NAME  +
                " where " +  " ( " +  USER_ID + " = " + user_id + " ) " + " and " + " ( " + DATE_TIME + " = " + time + " ) " +
                " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
               item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Book> getBooksByAuthor(long user_id, String author)
    {
        ArrayList<Book> list = new ArrayList<Book>();
        String sql = "select * from " + TABLE_BOOK_NAME  +
                " where " +  " ( " +  USER_ID + " = " + user_id + " ) " + " and " + " ( " + AUTHOR + " = '" + author + "' ) " +
                " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW  + " ) " +
                " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Book> getBooksForNews(long user_id)
    {
        ArrayList<Book> list = new ArrayList<Book>();
        String sql = "select * from " + TABLE_BOOK_NAME  + " where " +
                " ( " + USER_ID + " = " + user_id + " ) " +
                " and " + " ( " + ACTION + " <> " + Constants.TYPE_WANT_TO_SEE + " ) " +
                " Order by " + DATE_CHANGE + " desc " +
                " limit 0, 30 ";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
                item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<Book> getBooks()
    {
        ArrayList<Book> list = new ArrayList<Book>();
        String sql = "select * from " + TABLE_BOOK_NAME  + " Order by " + DATE_CHANGE + " desc";
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                Book item = new Book();
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Name = c.getString(c.getColumnIndex(NAME));
                item.Mark = c.getFloat(c.getColumnIndex(MARK));
                item.ImagePath = c.getString(c.getColumnIndex(IMAGE_PATH));
                item.ImageURL = c.getString(c.getColumnIndex(IMAGE_URL));
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.DateChange = c.getLong(c.getColumnIndex(DATE_CHANGE));
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.DateRemember = c.getLong(c.getColumnIndex(DATE_REMEMBER));
                item.Page = c.getInt(c.getColumnIndex(PAGE));
                item.Author = c.getString(c.getColumnIndex(AUTHOR));
               item.Comment = c.getString(c.getColumnIndex(COMMENT));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));


                int is_private = c.getInt(c.getColumnIndex(IS_PRIVATE));
                int is_saw_today = c.getInt(c.getColumnIndex(IS_SAW_TODAY));

                if ( is_private == 0)
                {
                    item.IsPrivate = false;
                }
                else
                {
                    item.IsPrivate = true;
                }

                if ( is_saw_today == 0)
                {
                    item.IsSawToday = false;
                }
                else
                {
                    item.IsSawToday = true;
                }


                list.add(item);
            }while(c.moveToNext());
        }

        return list;
    }

    public ArrayList<StatisticGeneralItem> getStatisticFilmItems(long uid)
    {
        ArrayList<StatisticGeneralItem> list = new ArrayList<StatisticGeneralItem>();
        StatisticGeneralItem item;

        String sql = "SELECT DISTINCT "  + DATE_TIME + " , " + ACTION + " from " + TABLE_FILM_NAME + " WHERE " +
                " ( " + USER_ID + " = " + uid + " )  " + " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW + " ) " +
                " ORDER BY " + DATE_TIME +  " DESC";

        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {

            do
            {
                long time = c.getLong(c.getColumnIndex(DATE_TIME));
                int count = getFilmsByDate(uid, time).size();
                item = new StatisticGeneralItem();
                item.Count = count;
                item.Name = HelpUtils.getTimeString(time);
                item.Time = time;
                //1419886800000
                //1419886800000

                list.add(item);
            }while ( c.moveToNext());
        }

        return list;


    }

    public ArrayList<StatisticGeneralItem> getStatisticSerialItems(long uid)
    {
        ArrayList<StatisticGeneralItem> list = new ArrayList<StatisticGeneralItem>();
        StatisticGeneralItem item;

        String sql = "SELECT DISTINCT "  + DATE_TIME + " , " + ACTION + " from " + TABLE_SERIAL_NAME + " WHERE " +
                " ( " + USER_ID + " = " + uid + " )  " + " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW + " ) " +
                " ORDER BY " + DATE_TIME +  " DESC";

        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {

            do
            {
                long time = c.getLong(c.getColumnIndex(DATE_TIME));
                int count = getSerialsByDate(uid, time).size();
                item = new StatisticGeneralItem();
                item.Count = count;
                item.Name = HelpUtils.getTimeString(time);
                item.Time = time;
                list.add(item);
            }while ( c.moveToNext());
        }

        return list;
    }

    public ArrayList<StatisticGeneralItem> getStatisticBookItems(long uid)
    {
        ArrayList<StatisticGeneralItem> list = new ArrayList<StatisticGeneralItem>();
        StatisticGeneralItem item;

        String sql = "SELECT DISTINCT "  + DATE_TIME + " , " + ACTION + " from " + TABLE_BOOK_NAME + " WHERE " +
                " ( " + USER_ID + " = " + uid + " )  " + " and " + " ( " + ACTION + " = " + Constants.TYPE_SAW + " ) " +
                " ORDER BY " + DATE_TIME +  " DESC";

        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {

            do
            {
                long time = c.getLong(c.getColumnIndex(DATE_TIME));
                int count = getBooksByDate(uid, time).size();
                item = new StatisticGeneralItem();
                item.Count = count;
                item.Name = HelpUtils.getTimeString(time);
                item.Time = time;
                list.add(item);
            }while ( c.moveToNext());
        }

        return list;
    }

    public ArrayList<StatisticGeneralItem> getFavoriteAutors(long uid)
    {
        ArrayList<StatisticGeneralItem> list = new ArrayList<StatisticGeneralItem>();
        StatisticGeneralItem item;

        String sql = "SELECT DISTINCT "  + AUTHOR + " from " + TABLE_BOOK_NAME + " WHERE " + USER_ID + " = " + uid ;

        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {

            do
            {
               String name = c.getString(c.getColumnIndex(AUTHOR));
                int count = getBooksByAuthor(uid, name).size();
                item = new StatisticGeneralItem();
                item.Count = count;
                item.Name = name;
                item.Time = -10;

                if ( count > 0)
                {
                    list.add(item);
                }
            }while ( c.moveToNext());


            if (list.size() > 0)
            {
                Collections.sort(list, new Comparator<StatisticGeneralItem>()
                {
                    @Override
                    public int compare(final StatisticGeneralItem s1, final StatisticGeneralItem s2) {

                        if (s1.Count > s2.Count) {
                            return -1;
                        } else if (s1.Count < s2.Count) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }
        }

        return list;
    }

    public ArrayList<ChangeItem> getChangeItems(long main_note_id, int action)
    {
        ArrayList<ChangeItem> list = new ArrayList<>();
        ChangeItem item;
        String sql = "select * from " + TABLE_MAIN_CHANGES + " where " +
                 " ( " +  MAIN_NOTE_ID + " = " + main_note_id + " ) " + " and " +
                 "( " + ACTION + " = " + action + " ) ";
        Cursor c = db.rawQuery(sql, null);

        if ( c.moveToFirst())
        {
            do
            {
                item = new ChangeItem();
                item.Action = c.getInt(c.getColumnIndex(ACTION));
                item.MainNoteId = c.getLong(c.getColumnIndex(MAIN_NOTE_ID));
                item.NoteId = c.getLong(c.getColumnIndex(NOTE_ID));
                item.UserId = c.getLong(c.getColumnIndex(USER_ID));
                list.add(item);
            }while (c.moveToNext());
        }

        return list;
    }


    //------------------------------------------------


    private void createFilmTable()
    {
       /* String sql = "create table if not exists " + TABLE_FILM_NAME + "( " + ID +
                " integer primary key autoincrement" + " , " + NAME + " text " + " , " +
                MARK + " real " + " , " + ACTION + " integer "  +  " , " +
                DATE_TIME  + " integer" + " , " +
                DATE_REMEMBER  + " integer" + " , " +
                IMAGE_URL + " text" + " , " +
                IMAGE_PATH + " text" + " , " +
                IS_PRIVATE  + " integer" + " , " +

                IS_SAW_TODAY  + " integer" + " , " +
                DATE_CHANGE + " integer" +
                ");";

        db.execSQL(sql);*/

        String sql = "create table if not exists " + TABLE_FILM_NAME  + "( " + ID +
                " integer primary key autoincrement" + " , " + NAME + " text " + " , " +
                MARK + " real " + " , " + ACTION + " integer "  +  " , " +
                DATE_TIME  + " integer" + " , " +
                DATE_REMEMBER  + " integer" + " , " +
                IMAGE_URL + " text" + " , " +
                COMMENT + " text" + " , " +
                IMAGE_PATH + " text" + " , " +
                IS_PRIVATE  + " integer" + " , " +
                USER_ID  + " integer" + " , " +
                NOTE_ID  + " integer" + " , " +
                IS_SAW_TODAY  + " integer" + " , " +
                DATE_CHANGE + " integer" +
                ");";

        db.execSQL(sql);






    }

    private void createSerialTable()
    {
        /*String sql = "create table if not exists " + TABLE_SERIAL_NAME + "( " + ID +
                " integer primary key autoincrement" + " , " + NAME + " text " + " , " +
                MARK + " real " + " , " + ACTION + " integer "  +  " , " +
                DATE_TIME  + " integer" + " , " +
                DATE_REMEMBER  + " integer" + " , " +
                IMAGE_URL + " text" + " , " +
                IMAGE_PATH + " text" + " , " +
                IS_PRIVATE  + " integer" + " , " +
                IS_SAW_TODAY  + " integer" + " , " +
                SEASON + " integer" + " , " +
                SERIES + " integer "  +  " , " +
                DATE_CHANGE + " integer" +
                ");";

        db.execSQL(sql);*/
        String sql = "create table if not exists " + TABLE_SERIAL_NAME  + "( " + ID +
                " integer primary key autoincrement" + " , " + NAME + " text " + " , " +
                MARK + " real " + " , " + ACTION + " integer "  +  " , " +
                DATE_TIME  + " integer" + " , " +
                DATE_REMEMBER  + " integer" + " , " +
                IMAGE_URL + " text" + " , " +
                IMAGE_PATH + " text" + " , " +
                IS_PRIVATE  + " integer" + " , " +
                COMMENT + " text" + " , " +
                IS_SAW_TODAY  + " integer" + " , " +
                USER_ID  + " integer" + " , " +
                NOTE_ID  + " integer" + " , " +
                SEASON + " integer" + " , " +
                SERIES + " integer "  +  " , " +
                DATE_CHANGE + " integer" +
                ");";

        db.execSQL(sql);


    }

    private void createBookTable()
    {
        /*String sql = "create table if not exists " + TABLE_BOOK_NAME+ "( " + ID +
                " integer primary key autoincrement" + " , " + NAME + " text " + " , " +
                AUTHOR + " text " + " , " +
                MARK + " real " + " , " + ACTION + " integer "  +  " , " +
                DATE_TIME  + " integer" + " , " +
                DATE_REMEMBER  + " integer" + " , " +
                IMAGE_URL + " text" + " , " +
                IMAGE_PATH + " text" + " , " +
                IS_PRIVATE  + " integer" + " , " +
                IS_SAW_TODAY  + " integer" + " , " +

                 PAGE + " integer "  +  " , " +
                DATE_CHANGE + " integer" +
                ");";

        db.execSQL(sql);*/
        String sql = "create table if not exists " + TABLE_BOOK_NAME  + "( " + ID +
                " integer primary key autoincrement" + " , " + NAME + " text " + " , " +
                AUTHOR + " text " + " , " +
                MARK + " real " + " , " + ACTION + " integer "  +  " , " +
                DATE_TIME  + " integer" + " , " +
                DATE_REMEMBER  + " integer" + " , " +
                IMAGE_URL + " text" + " , " +
                IMAGE_PATH + " text" + " , " +
                IS_PRIVATE  + " integer" + " , " +
                COMMENT + " text" + " , " +
                IS_SAW_TODAY  + " integer" + " , " +
                USER_ID  + " integer" + " , " +
                NOTE_ID  + " integer" + " , " +
                PAGE + " integer "  +  " , " +
                DATE_CHANGE + " integer" +
                ");";

        db.execSQL(sql);


    }

    private void createBookStatisticTable()
    {
        String sql = "create table if not exists " + TABLE_STATISTIC_BOOK_NAME + "( " +
                ID + " integer primary key autoincrement" + " , " +
                TYPE + " integer" + " , " +
                ITEM_ID + " integer" + " , " +
                DATE_TIME  + " integer" + " , " +
                PAGE_START + " integer" + " , " +
                PAGE_COUNT + " integer" +
                ");";
        db.execSQL(sql);
    }

    private void  createSerialStatisticTable()
    {


        String sql = "create table if not exists " + TABLE_STATISTIC_SERIAL_NAME + "( " +
                ID + " integer primary key autoincrement" + " , " +
                TYPE + " integer" + " , " +
                ITEM_ID + " integer" + " , " +
                DATE_TIME  + " integer" + " , " +
                SER_START + " integer" + " , " +
                SER_COUNT + " integer" + " , " +
                SEASON + " integer" + " , " +
                SERIES + " integer" +
                ");";
        db.execSQL(sql);
    }

    private void createCashItemsTable()
    {
        /*
        id, note_id, user_id, main_note_id, action

         */



        String sql = "create table if not exists " + TABLE_MAIN_CHANGES + "( " +
                ID + " integer primary key autoincrement" + " , " +
                NOTE_ID + " integer" + " , " + // по которому будем менять
                ACTION  + " integer" + " , " + // что будем делать
                TYPE  + " integer " + // фильм книга илис сериал
                ");";
        db.execSQL(sql);



    }

    private void createUsersTable()
    {
        String sql = "create table if not exists " + TABLE_USERS + " ( " +
                ID +  " integer primary key autoincrement " + " , " +
                NAME + " text " + " , " +
                LAST_NAME + " text " + " , " +
                USER_ID + " integer " + " , " +
                FRIENDS + " text " + " , "  +
                IMAGE_PATH + " text " + " , "  +
                IMAGE_URL + " text " + " ) ";

        db.execSQL(sql);

    }

    public ArrayList<String> getAuthors(long user_id)
    {
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select  DISTINCT " + AUTHOR + " from " + TABLE_BOOK_NAME + " where " + USER_ID + " = " + user_id;
        Cursor c = db.rawQuery(sql, null);
        if (c.moveToFirst())
        {
            do
            {
                list.add(
                        c.getString(c.getColumnIndex(AUTHOR))
                );
            }while(c.moveToNext());
        }
        return list;
    }

    public boolean isStatisticBookExists(Book book)
    {
        String sql = "select " + TYPE + " , " + ITEM_ID + " from " + TABLE_STATISTIC_BOOK_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_BOOK + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + book.Id + " ) ";

        Cursor c = db.rawQuery(sql, null);
        if ( c.getCount() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public boolean isStatisticSerialExists(Serial s)
    {
        String sql = "select " + TYPE + " , " + ITEM_ID + " from " + TABLE_STATISTIC_SERIAL_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_SERIAL + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + s.Id + " ) ";

        Cursor c = db.rawQuery(sql, null);
        if ( c.getCount() == 0)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public StatisticBook getStatisticBookByDateTime(Book book, long time)
    {
        String sql = "select * from " + TABLE_STATISTIC_BOOK_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_BOOK + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + book.Id + " ) " + " and " +
                " ( " + DATE_TIME + " = " + time + " ) ";

        Cursor c = db.rawQuery(sql, null);
        if ( c.getCount() == 0)
        {
            return  null;
        }
        else
        {
            c.moveToFirst();




            StatisticBook item = new StatisticBook();
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
            item.Type = c.getInt(c.getColumnIndex(TYPE));
            item.PageCount = c.getInt(c.getColumnIndex(PAGE_COUNT));
            item.PageStart = c.getInt(c.getColumnIndex(PAGE_START));
            item.Id = c.getLong(c.getColumnIndex(ID));

            return item;
        }

    }

    public StatisticSerial getStatisticSerialByDateTime(Serial s, long time)
    {
        String sql = "select * from " + TABLE_STATISTIC_SERIAL_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_SERIAL + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + s.Id + " ) " + " and " +
                " ( " + DATE_TIME + " = " + time + " ) ";

        Cursor c = db.rawQuery(sql, null);
        if ( c.getCount() == 0)
        {
            return  null;
        }
        else
        {
            c.moveToFirst();

            StatisticSerial item = new StatisticSerial();
            item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
            item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
            item.Type = c.getInt(c.getColumnIndex(TYPE));
            item.SerCount = c.getInt(c.getColumnIndex(SER_COUNT));
            item.SerStart = c.getInt(c.getColumnIndex(SER_START));
            item.Id = c.getLong(c.getColumnIndex(ID));
            item.Season =  c.getInt(c.getColumnIndex(SEASON));
            item.Series =  c.getInt(c.getColumnIndex(SERIES));

            return item;
        }

    }

    public ArrayList<StatisticBook> getStatisticBooksAll(Book book)
    {

        ArrayList<StatisticBook> list = new ArrayList<>();
        String sql = "select * from " + TABLE_STATISTIC_BOOK_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_BOOK + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + book.Id + " ) " +
                " Order by " + DATE_TIME;


        Cursor c = db.rawQuery(sql, null);
         if ( c.moveToFirst())
         {
              do
              {
                  StatisticBook item = new StatisticBook();
                  item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                  item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
                  item.Type = c.getInt(c.getColumnIndex(TYPE));
                  item.PageCount = c.getInt(c.getColumnIndex(PAGE_COUNT));
                  item.PageStart = c.getInt(c.getColumnIndex(PAGE_START));
                  item.Id = c.getLong(c.getColumnIndex(ID));

                  list.add(item);
              }while (c.moveToNext());
         }



            return list;


    }

        public StatisticBook getStatisticBookWithPrevious(Book book)
    {
        String sql = "select * from " + TABLE_STATISTIC_BOOK_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_BOOK + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + book.Id + " ) "  +
                " Order by " + DATE_TIME + " desc " ;


        Cursor c = db.rawQuery(sql, null);

        c.moveToFirst();

        StatisticBook item = new StatisticBook();
        item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
        item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
        item.Id = c.getLong(c.getColumnIndex(ID));
        item.Type = c.getInt(c.getColumnIndex(TYPE));
        item.PageCount = c.getInt(c.getColumnIndex(PAGE_COUNT));
        item.PageStart = c.getInt(c.getColumnIndex(PAGE_START));

        return item;


    }

    public StatisticSerial getStatisticSerialWithPrevious(Serial s)
    {
        String sql = "select * from " + TABLE_STATISTIC_SERIAL_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_SERIAL + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + s.Id + " ) "  +
                " Order by " + DATE_TIME + " desc " ;


        Cursor c = db.rawQuery(sql, null);

        c.moveToFirst();

        StatisticSerial item = new StatisticSerial();
        item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
        item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
        item.Type = c.getInt(c.getColumnIndex(TYPE));
        item.SerCount = c.getInt(c.getColumnIndex(SER_COUNT));
        item.SerStart = c.getInt(c.getColumnIndex(SER_START));
        item.Id = c.getLong(c.getColumnIndex(ID));
        item.Season =  c.getInt(c.getColumnIndex(SEASON));
        item.Series =  c.getInt(c.getColumnIndex(SERIES));

        return item;


    }

    public ArrayList<Integer> getStatisticBookForGraph(Book book)
    {
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select * from " + TABLE_STATISTIC_BOOK_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_BOOK + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + book.Id + " ) " +
                " Order by " + DATE_TIME;


        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {
            do
            {
                list.add(c.getInt(c.getColumnIndex(PAGE_COUNT)));
            }while (c.moveToNext());
        }

        return list;
    }

    public ArrayList<StatisticBook> getStatisticBooksAll()
    {
        ArrayList<StatisticBook> list = new ArrayList<>();
        String sql = "select * from " + TABLE_STATISTIC_BOOK_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_BOOK + " ) "  ;


        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {
            do
            {
                StatisticBook item = new StatisticBook();
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Type = c.getInt(c.getColumnIndex(TYPE));
                item.PageCount = c.getInt(c.getColumnIndex(PAGE_COUNT));
                item.PageStart = c.getInt(c.getColumnIndex(PAGE_START));

                list.add(item);
            }while (c.moveToNext());
        }

        return list;
    }

    public ArrayList<Integer> getStatisticSerialForGraph(Serial s)
    {
        ArrayList<Integer> list = new ArrayList<>();
        String sql = "select * from " + TABLE_STATISTIC_SERIAL_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_SERIAL + " ) "  + " and " +
                " ( " + ITEM_ID + " = " + s.Id + " ) " +
                " Order by " + DATE_TIME;

        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst())
        {
            do
            {
                list.add(c.getInt(c.getColumnIndex(SER_COUNT)));
            }while (c.moveToNext());
        }

        return list;
    }

    public ArrayList<StatisticSerial> getStatisticSerialsAll()
    {
        ArrayList<StatisticSerial> list = new ArrayList<>();
        String sql = "select * from " + TABLE_STATISTIC_SERIAL_NAME +
                " where " + " ( " + TYPE + " = " + Constants.TYPE_SERIAL + " ) " ;


        Cursor c = db.rawQuery(sql, null);

        if ( c.moveToFirst())
        {
            do
            {
                StatisticSerial item = new StatisticSerial();
                item.DateTime = c.getLong(c.getColumnIndex(DATE_TIME));
                item.ItemId = c.getLong(c.getColumnIndex(ITEM_ID));
                item.Type = c.getInt(c.getColumnIndex(TYPE));
                item.SerCount = c.getInt(c.getColumnIndex(SER_COUNT));
                item.SerStart = c.getInt(c.getColumnIndex(SER_START));
                item.Id = c.getLong(c.getColumnIndex(ID));
                item.Season =  c.getInt(c.getColumnIndex(SEASON));
                item.Series =  c.getInt(c.getColumnIndex(SERIES));

                list.add(item);
            }while ( c.moveToNext());
        }

        return list;
    }


    public long getLastId(int type)
    {
        String sql = "";
        if (type == Constants.TYPE_FILM)
        {
            sql = "select " + ID +  " from " + TABLE_FILM_NAME ;
        }
        else if (type == Constants.TYPE_SERIAL)
        {
            sql = "select " + ID +  " from " + TABLE_SERIAL_NAME;

        }
        else if (type == Constants.TYPE_BOOK)
        {
            sql = "select " + ID +  " from " + TABLE_BOOK_NAME;

        }
        else
        {
           return -1;
        }

        Cursor c = db.rawQuery(sql, null);

        if ( c.moveToFirst())
        {
           c.moveToLast();
            return c.getLong(c.getColumnIndex(ID));
        }
        else
        {
            return -1;
        }
    }


    public ArrayList<AnswerDto> getNewsByUserId(long user_id)
    {
        ArrayList<Film> films = getFilmsForNews(user_id);
        ArrayList<Serial> serials = getSerialsForNews(user_id);
        ArrayList<Book> books = getBooksForNews(user_id);

        ArrayList<AnswerDto> list = new ArrayList<>();
        AnswerDto answerDto;

        for ( Film item : films)
        {
            answerDto = new AnswerDto();
            answerDto.ItemId = item.DateChange;
            answerDto.Id = item.Id;
            answerDto.Success = Constants.TYPE_FILM;
            answerDto.Message = item.Action + "";

            list.add(answerDto);
        }

        for ( Serial item : serials)
        {
            answerDto = new AnswerDto();
            answerDto.ItemId = item.DateChange;
            answerDto.Id = item.Id;
            answerDto.Success = Constants.TYPE_SERIAL;
            answerDto.Message = item.Action + "";

            list.add(answerDto);
        }

        for ( Book item : books)
        {
            answerDto = new AnswerDto();
            answerDto.ItemId = item.DateChange;
            answerDto.Id = item.Id;
            answerDto.Success = Constants.TYPE_BOOK;
            answerDto.Message = item.Action + "";

            list.add(answerDto);
        }

        if (list.size() > 0)
        {
            Collections.sort(list, new Comparator<AnswerDto>()
            {
                @Override
                public int compare(final AnswerDto s1, final AnswerDto s2) {

                    if (s1.ItemId > s2.ItemId) {
                        return -1;
                    } else if (s1.ItemId < s2.ItemId)
                    {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        return list;
    }

    private static class DbHelper extends SQLiteOpenHelper
    {
        public DbHelper(Context context)
        {
            super(context, DB_NAME, null, DB_VERSION);



        }




        @Override
        public void onCreate(SQLiteDatabase db)
        {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {

        }
    }


}
