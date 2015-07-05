package funnytime.ru.whitemonkteam.funny_time.funnytime.utils;

import android.content.Context;

import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.CasheDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;

/**
 * Created by Андрей on 22.05.2015.
 */
public class CasheItemsUtils
{
    public static void Add( Context context, Object obj, int type)
    {
        if ( ShPrefUtils.getUserId(context) == 0) { return;} // пока не подключились нечего кэшировать

        CasheDto item = new CasheDto();
        if ( obj instanceof Film)
        {
            Film f = (Film)obj;

            item.Type = Constants.TYPE_FILM;
            item.NoteId = f.Id;
            item.Action = type;

            if ( type == Constants.DELETE)
            {
                item.NoteId = f.NoteId;
            }

            AppContext.dbAdapter.Add(item);
        }
        else if ( obj instanceof Serial)
        {
            Serial s = (Serial) obj;

            item.Type = Constants.TYPE_SERIAL;
            item.NoteId = s.Id;
            item.Action = type;

            if ( type == Constants.DELETE)
            {
                item.NoteId = s.NoteId;
            }

            AppContext.dbAdapter.Add(item);
        }
        else if ( obj instanceof Book)
        {
            Book b = (Book) obj;

            item.Type = Constants.TYPE_BOOK;
            item.NoteId = ((Book)obj).Id;
            item.Action = type;

            if ( type == Constants.DELETE)
            {
                item.NoteId = b.NoteId;
            }

            AppContext.dbAdapter.Add(item);
        }
    }
}
