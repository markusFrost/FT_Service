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
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.Film;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;

/**
 * Created by Андрей on 23.05.2015.
 */
public class NewsFilmsAdapter extends BaseAdapter
{
    Activity context;
    List<Film> list;
    //ArrayList<UserDto> arrayList = new ArrayList<>();
    public NewsFilmsAdapter(Activity context, ArrayList<Film> list)
    {
        this.context = context;
        this.list = list;



      /*  UserDto user;

        user = new UserDto();
        user.first_name = "Nikita";
        user.last_name = "Antonov";
        user.photo_200 = "http://cs413819.vk.me/v413819793/1e6/hkKfhsrX8F0.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Vasily";
        user.last_name = "Melnikov";
        user.photo_200 = "http://cs409817.vk.me/v409817986/52cd/P8Z358uWAQg.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Eugene";
        user.last_name = "El'tsov";
        user.photo_200 = "http://cs617731.vk.me/v617731259/256ec/pC1ZyV3hTvY.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Ilya";
        user.last_name = " Hripko";
        user.photo_200 = "http://cs623419.vk.me/v623419275/297bd/CV3VVLQ5-FY.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Valentin";
        user.last_name = "Boev";
        user.photo_200 = "http://cs623331.vk.me/v623331777/18b1b/Fg4gnxwY7YQ.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Paul";
        user.last_name = "Keane";
        user.photo_200 = "http://cs621126.vk.me/v621126080/4556/Km6haT-iiuA.jpg";

        arrayList.add(user);*/
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Film getItem(int position)
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
        Film item = getItem(position);

        UserDto user = AppContext.dbAdapter.getUserById(item.UserId);

       /* String [] names = {"Lobos de Arga","The Avengers 2","The Avengers","Fast & Furious 7","The Big Lebowski"};

        if ( position < arrayList.size())
        {
            user = arrayList.get(position);
            if (position < names.length)
            {
                item.Name = names[position];
            }

        }*/

        if ( HelpUtils.isOnline(context))
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

        String value = user.first_name + " " + user.last_name + "  " ;
        long time = 0;
        if ( item.Action == Constants.TYPE_SAW)
        {
            time = item.DateChange;
              value += context.getResources().getString(R.string.set_mark) +" " +  item.Mark + " " +  context.getResources().getString(R.string.to_film) + " " + item.Name + ".";
        }
        else  if ( item.Action == Constants.TYPE_DO_NOT_LIKE)
        {
            time = item.DateChange;
            value += " : " +  context.getResources().getString(R.string.do_not_like_film) + " " + item.Name + ".";
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
