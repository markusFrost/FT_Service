package funnytime.ru.whitemonkteam.funny_time.funnytime.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Item;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Serial;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.AnswerDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;

/**
 * Created by Андрей on 24.05.2015.
 */
public class UserNewsAdapter extends BaseAdapter
{
    ArrayList<AnswerDto> list;
    Activity context;
    long user_id;

    public  UserNewsAdapter(Activity context, long user_id )
    {
        this.context = context;
        this.list = AppContext.dbAdapter.getNewsByUserId(user_id);
        this.user_id = user_id;


    }
    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public AnswerDto getItem(int position) {
        return list.get((position));
    }

    @Override
    public long getItemId(int position)
    {
        return list.get((position)).Id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.news_layout,
                    parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvInfo = (TextView) convertView
                    .findViewById(R.id.newsTvInfo);

            viewHolder.iv = (ImageView) convertView
                    .findViewById(R.id.newsIv);

            viewHolder.btnShowComent = (Button) convertView
                    .findViewById(R.id.newsBtnShowComent);

            convertView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
       AnswerDto answerDto = getItem(position);

       // holder.iv.setVisibility(View.GONE);



// get information from db
      /*  UserDto user = new UserDto(); // in real get from DB by user_id
        user.first_name = context.getResources().getString(R.string.my_name);
        user.last_name = context.getResources().getString(R.string.my_lastName);
       // user.id = item.UserId;
        user.photo_200 =  Constants.PATH_USERS_IMAGES + File.separator +  53074293 +".jpg";*/

        UserDto user = AppContext.dbAdapter.getUserById(user_id);

        String value = user.first_name + " " + user.last_name + "\n" ;
        long time = 0;
        //-------------------------------------------------
        if (answerDto.Success == Constants.TYPE_FILM)
        {
            Film item = AppContext.dbAdapter.getFilmById(user_id, answerDto.Id);
            if ( answerDto.Message.equals(Constants.TYPE_SAW + ""))
            {
                time = item.DateChange;
                value += context.getResources().getString(R.string.set_mark) +" " +  item.Mark + " " +  context.getResources().getString(R.string.to_film) + " " + item.Name + ".";
            }
            else  if ( answerDto.Message.equals(Constants.TYPE_DO_NOT_LIKE + ""))
            {
                time = item.DateChange;
                value += " : " +  context.getResources().getString(R.string.do_not_like_film) + " " + item.Name + ".";
            }

            if ( item.Comment != null && !item.Comment.equals("") && item.Comment.length() > 1)
            {
                holder.btnShowComent.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.btnShowComent.setVisibility(View.GONE);
            }

           setImage(holder.iv, item);

        }
        //-------------------------------------------------
        else  if ( answerDto.Success == Constants.TYPE_SERIAL)
        {
            Serial item = AppContext.dbAdapter.getSerialById(user_id, answerDto.Id);
            if ( answerDto.Message.equals(Constants.TYPE_ALREADY_SEE + ""))
            {
                time = item.DateChange;
                value += context.getResources().getString(R.string.serial) + " " + item.Name + ":\n" +
                        item.Season + " " +  context.getResources().getString(R.string.strEnterSeason) + " " +
                        item.Series + " " + context.getResources().getString(R.string.strEnterSeries) + ".";
            }
            else if ( answerDto.Message.equals(Constants.TYPE_SAW + ""))
            {
                time = item.DateChange;
                value += context.getResources().getString(R.string.set_mark) +" " +  item.Mark + " " +  context.getResources().getString(R.string.to_serial) + " " + item.Name + ".";
            }
            else if ( answerDto.Message.equals(Constants.TYPE_DO_NOT_LIKE + ""))
            {
                time = item.DateChange;
                value += " : " +  context.getResources().getString(R.string.do_not_like_serial) + " " + item.Name + ".";
            }

            if ( item.Comment != null && !item.Comment.equals("") && item.Comment.length() > 1)
            {
                holder.btnShowComent.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.btnShowComent.setVisibility(View.GONE);
            }

            setImage(holder.iv, item);
        }
        //-------------------------------------------------
        else  if ( answerDto.Success == Constants.TYPE_BOOK)
        {
            Book item = AppContext.dbAdapter.getBookById(user_id, answerDto.Id);
            if ( answerDto.Message.equals(Constants.TYPE_ALREADY_SEE + ""))
            {
                time = item.DateChange;
                value += context.getResources().getString(R.string.book) + " " + item.Author + " - " + item.Name + ":\n" +
                        context.getResources().getString(R.string.page)  + " " + item.Page + ".";
            }
            else if ( answerDto.Message.equals(Constants.TYPE_SAW + ""))
            {
                time = item.DateChange;
                value += context.getResources().getString(R.string.set_mark) +" " +  item.Mark + " " +  context.getResources().getString(R.string.to_book) + " " + item.Author + " - "+ item.Name + ".";
            }
            else if ( answerDto.Message.equals(Constants.TYPE_DO_NOT_LIKE + ""))
            {
                time = item.DateChange;
                value += " : " +  context.getResources().getString(R.string.do_not_like_book) + " " + item.Author + " - " +  item.Name + ".";
            }

            if ( item.Comment != null && !item.Comment.equals("") && item.Comment.length() > 1)
            {
                holder.btnShowComent.setVisibility(View.VISIBLE);
            }
            else
            {
                holder.btnShowComent.setVisibility(View.GONE);
            }

            setImage(holder.iv, item);
        }

        if ( HelpUtils.isToday(time))
        {
            value += "\n" + context.getResources().getString(R.string.today) + " " +  HelpUtils.getTimeHourByMills(time);
        }
        else  if ( HelpUtils.isYerstoday(time))
        {
            value += "\n" + context.getResources().getString(R.string.yerstaday) + " " +  HelpUtils.getTimeHourByMills(time);
        }
        else
        {
            value += "\n" + HelpUtils.getTimeByMills(time);
        }



        holder.tvInfo.setText(value);




        return convertView;
    }

    private void setImage(ImageView iv, Item item)
    {
        if ( HelpUtils.isOnline(context) && item.ImageURL != null && !item.ImageURL.equals("") )
        {
            Picasso.with(context)
                    .load( item.ImageURL)
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(iv);
        }
        else
        {
            Picasso.with(context)
                    .load( Constants.FILE + item.ImagePath)
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(iv);
        }
    }

    static class ViewHolder
    {
        TextView tvInfo;
        ImageView iv;
        Button btnShowComent;

    }
}
