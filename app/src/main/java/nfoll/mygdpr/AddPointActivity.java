package nfoll.mygdpr;

import android.annotation.SuppressLint;
import android.content.Intent;
import nfoll.mygdpr.data.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import nfoll.mygdpr.R;
import nfoll.mygdpr.db.PointDatabase;

public class AddPointActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
    }

    @Override
    protected void onResume() {
        super.onResume();

        configurePrioritySpinner();
        configureAddPointButton();
        configureEndAddActivityButton();
    }

    public void configureAddPointButton() {
        View buttonInserisci = findViewById(R.id.addPointButton);
        buttonInserisci.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {

                /* recuperaro i dati dal form e popolo un'istanza di Point */
                new AsyncTask<Void, Void, Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    protected Void doInBackground(Void... voids) {
                        Point point = new Point();
                        Log.i("newPoint", "ho creato un nuovo punto");
                        point.setTitle(((EditText)findViewById(R.id.title)).getText().toString());
                        Log.i("setTitle", "ho settato il titolo con " + point.getTitle());
                        String priority = (((Spinner)findViewById(R.id.priorityspinner)).getSelectedItem().toString());
                        point.setPriority(priority);
                        Log.d("setPriority", "ho settato la priorità come " + priority);
                        if(priority.equals("Bassa"))
                            point.setIntPriority(1);
                        if(priority.equals("Media"))
                            point.setIntPriority(2);
                        if(priority.equals("Alta"))
                            point.setIntPriority(3);
                        Log.i("setIntPriority", "ho settato  la IntPriorità con " + point.getIntPriority());
                        String date= ((EditText)findViewById(R.id.editText3)).getText().toString();
                        point.setDateStr(date);
                        try {
                            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);

                            Date myDate = df.parse(date);
                            date=DateFormat.getDateInstance().format(myDate);
                            Log.i("data", "" + myDate);
                            point.setDate(myDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Log.d("setDate", "ho settato la data con : " + date);
                        String article = ((EditText)findViewById(R.id.article)).getText().toString();
                        if(article.equals(""))
                            point.setArticle(0);
                        else {
                            int myarticle = Integer.parseInt(article);
                            point.setArticle(myarticle);
                        }
                        Log.i("seArticle", "ho settato articolo con " + point.getArticle());
                        point.setChecked(0);
                        Log.i("setChecked", "ho settato checked con " + point.getChecked());
                        String person =  ((EditText)findViewById(R.id.person)).getText().toString();
                        point.setPersonInCharge(person);
                        Log.i("setpersonInCharge", "ho settato person " + point.getPersonInCharge());

                        Long idRow = PointDatabase.getInstance(getApplicationContext()).getPointDao().createPoint(point);

                        return null;
                    }
                }.execute();
                finish();

                Intent i = new Intent(getApplicationContext(), PointListActivity.class);
                startActivity(i);
            }
        });
    }

    private void configureEndAddActivityButton(){
        Button btn = (Button) findViewById(R.id.CancelButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*configura lo spinner per la selezione della priorità*/
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
