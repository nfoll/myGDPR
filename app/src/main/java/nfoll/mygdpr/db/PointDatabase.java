package nfoll.mygdpr.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.concurrent.Executors;

import nfoll.mygdpr.Converters;
import nfoll.mygdpr.dao.PointDao;
import nfoll.mygdpr.data.Point;


@Database(
        entities = {Point.class},
        version = 10,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class PointDatabase extends RoomDatabase {
    private static PointDatabase database;

    public abstract PointDao getPointDao();

    public static PointDatabase getInstance(final Context context) {
        if(database == null) {
            database = Room.databaseBuilder(
                    context,
                    PointDatabase.class,
                    "database-voci.db"
            ).addCallback(new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            getInstance(context).getPointDao().insertAll(Point.populateData());
                        }
                    });
                }
            })
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }
}
