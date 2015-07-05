package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.load;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.api.Api;
import funnytime.ru.whitemonkteam.funny_time.funnytime.api.KException;
import funnytime.ru.whitemonkteam.funny_time.funnytime.api.User;
import funnytime.ru.whitemonkteam.funny_time.funnytime.async_tasks.user.CreateNewUserTask;
import funnytime.ru.whitemonkteam.funny_time.funnytime.interfaces.OnUserLoadListner;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.BitmapUtils;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.Constants;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by ������ on 25.05.2015.
 */
public class RedactProfileFragment extends Fragment implements OnUserLoadListner
{

    @Override
    public void onUserLoaded(JSONObject js)
    {
        changeState(true);
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();

        // prof online, users, fbs
    }

    ProgressBar pb;
    Button btnOk;
    ImageView iv;
    private long id;
    final UserDto item = new UserDto();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.redact_profile_layout, container, false);

      final  long user_id = getArguments().getLong(Constants.EXTRA_ITEM);
        this.id = user_id;

        final EditText editName = (EditText) v.findViewById(R.id.reductEditName);
        final EditText editLastName = (EditText) v.findViewById(R.id.reductEditLastName);

        iv = (ImageView) v.findViewById(R.id.reductIv);


        pb = (ProgressBar) v.findViewById(R.id.reductProgressBar);

        btnOk = (Button) v.findViewById(R.id.reductBtnOk);

        changeState(true);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AppContext.dbAdapter.Add(item);
                changeState(false);
                new CreateNewUserTask(getActivity(), item, RedactProfileFragment.this).execute();

            }
        });

        new Thread()
        {
            @Override
            public void run()
            {
                Api api = new Api(ShPrefUtils.getAccesToken(getActivity()), getActivity().getResources().getString(R.string.vk_id));


                ArrayList<Long> uids = new ArrayList<Long>();
                uids.add(user_id);
                String fields = "photo_200, first_name, last_name";
                try
                {
                    ArrayList<User> list = api.getProfiles(uids,fields);

                    if ( list.size() > 0)
                    {
                        User u = list.get(0);

                        item.first_name = u.first_name;
                        item.last_name = u.last_name;
                        item.uid = user_id;
                        item.photo_200 = u.photo_200;

                        getActivity().runOnUiThread(
                                new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                editName.setText(item.first_name);
                                editLastName.setText(item.last_name);

                                Picasso.with(getActivity())
                                        .load(item.photo_200)
                                        .placeholder(R.drawable.icon_stop)
                                        .error(R.drawable.icon_stop)
                                        .into(target);

                            }
                        });

                    }
                    int k = list.size();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (KException e) {
                    e.printStackTrace();
                }



                getActivity().runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Toast.makeText(getActivity().getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.start();



        return v;
    }

    private void changeState( boolean fl)
    {
        if ( fl) // start
        {
            btnOk.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
        }
        else
        {
            btnOk.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);
        }
    }

    private Target target = new Target()
    {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from)
        {
            iv.setImageBitmap(bitmap);

            try
            {
                item.imagePath =   BitmapUtils.saveAsImage(bitmap, Constants.TYPE_USERS, id, id);

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



    @Override
    public void onUsersLoaded(JSONObject js) {

    }



}
