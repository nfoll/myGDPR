package nfoll.mygdpr;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import java.util.List;


import nfoll.mygdpr.data.Point;
import nfoll.mygdpr.db.PointDatabase;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PointViewHolder> {

    private List<Point> points;

    public static class PointViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView id;
        TextView title;
        TextView data;
        TextView completed;
        TextView person;
        Context context;
        final View myView;
        Button deleteButton;
        Button editButton;
        Button completedButton;


        public PointViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.titleShowed);
            id = (TextView)itemView.findViewById(R.id.id);
            data = (TextView) itemView.findViewById(R.id.dataShowed);
            completed = (TextView) itemView.findViewById(R.id.checkedShowed);
            person = (TextView) itemView.findViewById(R.id.personShowed);
            deleteButton = (Button) itemView.findViewById(R.id.delete_item);
            editButton = (Button) itemView.findViewById(R.id.edit_item);
            completedButton = (Button) itemView.findViewById(R.id.completed_item);
            context=itemView.getContext();
        }

    }


    public RVAdapter(List<Point> points){
        this.points = points;
    }


    @Override
    public RVAdapter.PointViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_layout, viewGroup, false);
        PointViewHolder pvh = new PointViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PointViewHolder pointViewHolder, final int i) {
        pointViewHolder.title.setText(points.get(i).getTitle());

        final String idStr = Integer.toString(points.get(i).getId());
        pointViewHolder.id.setText(idStr);

        String setData = "Priorità: " + points.get(i).getPriority() + " | Data: " + points.get(i).getDateStr() + " | N. Art: " + points.get(i).getArticle();
        pointViewHolder.data.setText(setData);

        String completedStr;
        if(points.get(i).getChecked()==0) {
            completedStr = "Status: non completato";
            pointViewHolder.cv.setCardBackgroundColor(Color.parseColor("#20F50000"));
        }else {
            completedStr = "Status: completato";
            pointViewHolder.cv.setCardBackgroundColor(Color.parseColor("#2050D050"));
        }
        pointViewHolder.completed.setText(completedStr);

        String setPerson = "Responsabile: " + points.get(i).getPersonInCharge();
        pointViewHolder.person.setText(setPerson);
        
        pointViewHolder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    @TargetApi(Build.VERSION_CODES.M)
                    protected Void doInBackground(Void... voids) {
                        Point point = PointDatabase.getInstance(context).getPointDao().findPointById(Integer.parseInt(idStr));
                        PointDatabase.getInstance(context).getPointDao().deletePoint(point);
                        Log.d("ho eliminato un task", " task eliminato ");
                        points.remove(point);
                        notifyItemRemoved(i);
                        return null;
                    }
                }.execute();
            }
        });

        pointViewHolder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Context context = view.getContext();
                Intent intent = new Intent(context, EditPointActivity.class);
                intent.putExtra("idPoint", points.get(i).getId());
                intent.putExtra("title", points.get(i).getTitle());
                intent.putExtra("date", points.get(i).getDateStr());
                intent.putExtra("person", points.get(i).getPersonInCharge());
                intent.putExtra("article", points.get(i).getArticle());
                context.startActivity(intent);
            }
        });

        pointViewHolder.completedButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("StaticFieldLeak")
                @Override
                public void onClick(View view) {
                final Context context = view.getContext();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    @TargetApi(Build.VERSION_CODES.M)
                    protected Void doInBackground(Void... voids) {
                        Point point = PointDatabase.getInstance(context).getPointDao().findPointById(Integer.parseInt(idStr));
                        if(points.get(i).getChecked()==0) {
                            point.setChecked(1);
                            point.setIntPriority(1);
                            point.setPriority("Bassa");
                        }else{
                            point.setChecked(0);
                        }
                        PointDatabase.getInstance(context).getPointDao().updatePoint(point);
                        Log.d("ho aggiornato un task", " task aggiornato ");
                        points.remove(i);
                        notifyItemRemoved(i);
                        points.add(i, point);
                        notifyItemInserted(i);
                        return null;
                    }
                }.execute();
            }
        });

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return this.points.size();
    }





}
