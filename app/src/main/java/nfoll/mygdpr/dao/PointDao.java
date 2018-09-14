package nfoll.mygdpr.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import nfoll.mygdpr.data.Point;

@Dao
public interface PointDao {
    @Insert
    public Long createPoint(Point point);

    @Insert
    void insertAll(Point... points);

    @Update
    public void updatePoint(Point point);

    @Delete
    public void deletePoint(Point point);

    @Query("DELETE FROM elenco_voci")
    public void deleteAll();

    @Query("SELECT * FROM elenco_voci WHERE id = :id")
    public Point findPointById(int id);

    @Query("SELECT * FROM elenco_voci ORDER BY checked ASC, date ASC, intPriority DESC")
    public List<Point> viewAll();

    @Query("SELECT COUNT (*) FROM elenco_voci WHERE checked = 0")
    public int countUnChecked();

}
