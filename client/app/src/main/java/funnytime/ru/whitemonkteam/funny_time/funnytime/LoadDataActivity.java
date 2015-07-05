package funnytime.ru.whitemonkteam.funny_time.funnytime;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.load.RedactProfileFragment;


public class LoadDataActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cu);

       // getActionBar().setTitle("Wait");
        // тут расписать типы что и когда дочерняя активность

       /* final FragmentManager fragmentManager = getFragmentManager();
       Fragment fragment = new ConnectFilmsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.content_cuframe, fragment).commit();*/





      final FragmentManager fragmentManager = getFragmentManager();


        Fragment fragment = new RedactProfileFragment();

        fragment.setArguments(getIntent().getExtras());

        fragmentManager.beginTransaction()
                .replace(R.id.content_cuframe, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_load_data, menu);
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
    }
}
