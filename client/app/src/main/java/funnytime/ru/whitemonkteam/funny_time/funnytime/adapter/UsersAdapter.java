package funnytime.ru.whitemonkteam.funny_time.funnytime.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.HelpUtils;

/**
 * Created by Андрей on 19.05.2015.
 */
public class UsersAdapter extends BaseAdapter
{
    Activity context;
    ArrayList<UserDto> list;
    ArrayList<UserDto> origList;

    public UsersAdapter(Activity context,ArrayList<UserDto> list )
    {
        this.context = context;
        this.list = list;
        this.origList = new ArrayList<>(list);
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public UserDto getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    public void Search(String name)
    {

        list = origList;
        if (name == null)
        {
            return;
        }
        ArrayList<UserDto> users = new ArrayList<>();

        name = name.toLowerCase();
        for ( UserDto u : list)
        {
            String value = u.first_name + " " + u.last_name;
            if (value.toLowerCase().contains(name))
            {
                users.add(u);
            }
        }
        list = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.friend_row,
                    parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvName = (TextView) convertView
                    .findViewById(R.id.id);

            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.image);

            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();

        UserDto user = getItem(position);

        holder.tvName.setText(user.first_name + "  " + user.last_name);

       setImage(holder.imageView, user);

        return  convertView;
    }


    private void setImage(ImageView iv, UserDto item)
    {
        if ( HelpUtils.isOnline(context) && item.photo_200 != null && !item.photo_200.equals("") )
        {
            Picasso.with(context)
                    .load( item.photo_200)
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(iv);
        }
        else
        {
            Picasso.with(context)
                    .load( Constants.FILE + item.imagePath)
                    .placeholder(android.R.drawable.ic_dialog_info)
                    .error(android.R.drawable.ic_dialog_info)
                    .into(iv);
        }
    }
    static class ViewHolder
    {
        TextView tvName;
        ImageView imageView;

    }
}
