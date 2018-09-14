package nfoll.mygdpr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Objects;

import nfoll.mygdpr.data.Point;
import nfoll.mygdpr.db.PointDatabase;

public class EditPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_point);

        final int id = Objects.requireNonNull(getIntent().getExtras()).getInt("idPoint");
        final String title = Objects.requireNonNull(getIntent().getExtras()).getString("title");
        final String person = Objects.requireNonNull(getIntent().getExtras()).getString("person");
        final String date = Objects.requireNonNull(getIntent().getExtras()).getString("date");
        final int article = Objects.requireNonNull(getIntent().getExtras()).getInt("article");
        TextView mytitle = findViewById(R.id.title);
        TextView mydate = findViewById(R.id.editText3);
        TextView myperson = findViewById(R.id.person);
        TextView myarticle = findViewById(R.id.article);
        mytitle.setText(title);
        mydate.setText(date);
        String articleStr=Integer.toString(article);
        myarticle.setText(articleStr);
        myperson.setText(person);

        Log.d("ho recuperato il punto", "l'id è " + id);


        configurePrioritySpinner();
        configureEditPointButton(id);
        configureEndEditActivityButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void configureEndEditActivityButton(){
        Button btn = (Button) findViewById(R.id.CancelButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Log.d("finish", "ho finito");
            }
        });
    }

    public void configureEditPointButton(final int id) {
        View editButton = findViewById(R.id.editPointButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                /* recuperaro i dati dal form e aggiorno il Point */

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Point point = PointDatabase.getInstance(getApplicationContext()).getPointDao().findPointById(id);
                        Log.i("newPoint", "ho creato un nuovo punto");
                        point.setTitle(((EditText)findViewById(R.id.title)).getText().toString());
                        Log.i("setTitle", "ho settato il titolo con " + point.getTitle());
                        String priority = (((Spinner)findViewById(R.id.priorityspinner)).getSelectedItem().toString());
                        point.setPriority(priority);
                        Log.d("setPriority", "la priority è" + priority);
                        if(priority.equals("Bassa"))
                            point.setIntPriority(1);
                        if(priority.equals("Media"))
                            point.setIntPriority(2);
                        if(priority.equals("Alta"))
                            point.setIntPriority(3);
                        Log.i("setIntPriority", "ho settato  la IntPriorità con " + point.getIntPriority());

                        String article = ((EditText)findViewById(R.id.article)).getText().toString();
                        if(article.equals(""))
                            point.setArticle(0);
                        else {
                            int myarticle = Integer.parseInt(article);
                            point.setArticle(myarticle);
                        }
                        Log.i("setArticle", "ho settato articolo con " + point.getArticle());
                        point.setChecked(0);
                        Log.i("setChecked", "ho settato checked con " + point.getChecked());
                        String person =  ((EditText)findViewById(R.id.person)).getText().toString();
                        point.setPersonInCharge(person);
                        Log.i("setpersonInCharge", "ho settato person " + point.getPersonInCharge());

                        PointDatabase.getInstance(getApplicationContext()).getPointDao().updatePoint(point);

                        return null;
                    }
                }.execute();
                Intent i = new Intent(getApplicationContext(), PointListActivity.class);
                startActivity(i);
            }
        });
    }

    public void configurePrioritySpinner(){
        Spinner priorityspinner = findViewById(R.id.priorityspinner);

        ArrayAdapter<CharSequence> elements = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.priority,
                android.R.layout.simple_spinner_item);

        elements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        priorityspinner.setAdapter(elements);

    }


}
