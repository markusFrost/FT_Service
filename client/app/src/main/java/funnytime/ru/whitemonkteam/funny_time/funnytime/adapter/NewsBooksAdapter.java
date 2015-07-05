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
import java.util.List;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Book;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;

/**
 * Created by Андрей on 23.05.2015.
 */
public class NewsBooksAdapter extends BaseAdapter
{
    Activity context;
    List<Book> list;

    public NewsBooksAdapter(Activity context, ArrayList<Book> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Book getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return getItem(position).Id;
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
        Book item = getItem(position);

        UserDto user = AppContext.dbAdapter.getUserById(item.UserId);

        if ( HelpUtils.isOnline(context) && user.photo_200 != null )
        {
            Picasso.with(context)
                    .load( user.photo_200)
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(holder.iv);
        }
        else if ( user.imagePath != null)
        {
            Picasso.with(context)
                    .load(Constants.FILE + user.imagePath)
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(holder.iv);
        }

        String value = user.first_name + " " + user.last_name + "  \n" ;
        long time = 0;
        if ( item.Action == Constants.TYPE_ALREADY_SEE)
        {
            time = item.DateChange;
            value += context.getResources().getString(R.string.book) + " " + item.Author + " - " + item.Name + ":\n" +
                     context.getResources().getString(R.string.page)  + " " + item.Page + ".";
        }
        else if ( item.Action == Constants.TYPE_SAW)
        {
            time = item.DateChange;
              value += context.getResources().getString(R.string.set_mark) +" " +  item.Mark + " " +  context.getResources().getString(R.string.to_book) + " " + item.Author + " - "+ item.Name + ".";
        }
        else  if ( item.Action == Constants.TYPE_DO_NOT_LIKE)
        {
            time = item.DateChange;
            value += " : " +  context.getResources().getString(R.string.do_not_like_book) + " " + item.Author + " - " +  item.Name + ".";
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

        if ( item.Comment != null && !item.Comment.equals("") && item.Comment.length() > 1)
        {
            holder.btnShowComent.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.btnShowComent.setVisibility(View.GONE);
        }



        return convertView;
    }

    static class ViewHolder
    {
        TextView tvInfo;
        ImageView iv;
        Button btnShowComent;

    }
}
