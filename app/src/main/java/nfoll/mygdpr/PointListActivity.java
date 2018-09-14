package nfoll.mygdpr;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import nfoll.mygdpr.data.Point;
import nfoll.mygdpr.db.PointDatabase;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class PointListActivity extends AppCompatActivity {

    List<Point> points;
    RVAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        setAddButton();
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.myrv);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        new AsyncTask<Void, Void, Void>() {
            @Override
            @TargetApi(Build.VERSION_CODES.M)
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                points = PointDatabase.getInstance(context).getPointDao().viewAll();
                Log.d("dimensione points", " points ha dimensione " + points.size());
                adapter = new RVAdapter(points);
                mRecyclerView.setAdapter(adapter);
                return null;
            }
        }.execute();


        super.onResume();
    }

    public void setAddButton() {
        View addButton = findViewById(R.id.addPointButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddPointActivity.class);
                startActivity(i);
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);

        return true;
    }

    /*menu di pagina*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuCrea:
                Intent i = new Intent(getApplicationContext(), AddPointActivity.class);
                i.putExtra("varA", "PARAMETRO PASSATO!!");

                startActivity(i);
                break;

            case R.id.menuEliminaTutto:
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Context context = getApplicationContext();
                        PointDatabase.getInstance(context).getPointDao().deleteAll();
                        return null;
                    }
                }.execute();
                points.clear();
                adapter.notifyDataSetChanged();

                break;

        }
        return true;
    }
}
