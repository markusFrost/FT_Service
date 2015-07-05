package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.ListFragment;
import android.os.Bundle;

import java.util.ArrayList;

import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.UsersAdapter;
import funnytime.ru.whitemonkteam.funny_time.funnytime.models.dto.UserDto;
import funnytime.ru.whitemonkteam.funny_time.funnytime.objects.AppContext;
import funnytime.ru.whitemonkteam.funny_time.funnytime.utils.ShPrefUtils;

/**
 * Created by Андрей on 27.05.2015.
 */
public class StatisticFriendsFragment extends ListFragment
{

    UsersAdapter adapter;
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

       /* ArrayList<UserDto> list = new ArrayList<>();


        UserDto user;

        user = new UserDto();
        user.first_name = "Nikita";
        user.last_name = "Antonov ( 80 %)";
        user.photo_200 = "http://cs413819.vk.me/v413819793/1e6/hkKfhsrX8F0.jpg";

        list.add(user);

        user = new UserDto();
        user.first_name = "Vasily";
        user.last_name = "Melnikov (70 %)";
        user.photo_200 = "http://cs409817.vk.me/v409817986/52cd/P8Z358uWAQg.jpg";

        list.add(user);

        user = new UserDto();
        user.first_name = "Eugene";
        user.last_name = "Eltsov (65%)";
        user.photo_200 = "http://cs617731.vk.me/v617731259/256ec/pC1ZyV3hTvY.jpg";

        list.add(user);

        user = new UserDto();
        user.first_name = "Ilya";
        user.last_name = " Hripko (50 %)";
        user.photo_200 = "http://cs623419.vk.me/v623419275/297bd/CV3VVLQ5-FY.jpg";

        list.add(user);

        user = new UserDto();
        user.first_name = "Valentin";
        user.last_name = "Boev ( 40 %)";
        user.photo_200 = "http://cs623331.vk.me/v623331777/18b1b/Fg4gnxwY7YQ.jpg";

        list.add(user);

        user = new UserDto();
        user.first_name = "Paul";
        user.last_name = "Keane (24 %) ";
        user.photo_200 = "http://cs621126.vk.me/v621126080/4556/Km6haT-iiuA.jpg";

        list.add(user);

        adapter = new UsersAdapter(getActivity(), list);

        setListAdapter(adapter);*/

        //-------------------------------------------------

        ArrayList<UserDto> arrayList = new ArrayList<>();
        arrayList.add(AppContext.dbAdapter.getUserById(ShPrefUtils.getUserId(getActivity())));

        UserDto user;

        user = new UserDto();
        user.first_name = "Game of Thrones";
        user.last_name = "9.6 / 10";
        user.photo_200 = "http://www.trkterra.ru/sites/default/files/field/image/new_world/3538749.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "House M.D.";
        user.last_name = "8.2 / 7";
        user.photo_200 = "http://g.foolcdn.com/editorial/images/115302/showimages_house_large_large.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "House of Cards";
        user.last_name = "7.9 / 5";
        user.photo_200 = "http://img4.wikia.nocookie.net/__cb20140217231358/house-of-cards/images/a/a8/House_of_Cards_Season_1_Poster.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Boardwalk Empire";
        user.last_name = "7.8 / 7";
        user.photo_200 = "http://i344.photobucket.com/albums/p352/aweshi/tv/bepost2_zpsezcmbjsp.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Once Upon A Time";
        user.last_name = "7.7 / 9";
        user.photo_200 = "http://upload.wikimedia.org/wikipedia/en/c/c2/Once_Upon_aTime_promo_image.jpg";

        arrayList.add(user);

        user = new UserDto();
        user.first_name = "Person of Interest";
        user.last_name = "7.1 / 2";
        user.photo_200 = "http://cnfstudio.com/wp-content/uploads/2015/02/Person-of-Interest-Season-2.jpg";

        arrayList.add(user);

        adapter = new UsersAdapter(getActivity(), arrayList);

        setListAdapter(adapter);
    }
}
