package nfoll.mygdpr.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nfoll.mygdpr.Converters;


@Entity(tableName = "elenco_voci")
public class Point {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String priority;
    @TypeConverters(Converters.class) private Date date=null;
    //il campo dateStr semplifica la visione del campo data nel recyclerview
    String dateStr;
    private int article;
    private int checked;
    //il campo intPriority semplifica l'ordinamento dei dati durante la query: ==1 se priorità bassa, ==2 se priorità media, ==3 se priorità alta
    private int intPriority;
    private String personInCharge= "nessuno";

    public Point(){}


    public Point(String title, String priority, int article, int checked, int intPriority, Date date, String dateStr) {
        this.title = title;
        this.priority = priority;
        this.article = article;
        this.checked = checked;
        this.date = date;
        this.intPriority = intPriority;
        this.dateStr = dateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getChecked(){
        return checked;
    }

   public void setChecked(int checked){
        if(checked==1)
            this.intPriority=1;
        this.checked=checked;
    }

    public void setIntPriority(int intPriority){
        this.intPriority=intPriority;
    }

    public int getIntPriority(){
        return intPriority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getPersonInCharge(){
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge){
        this.personInCharge = personInCharge;
    }

    /*aggiungere Point qui per popolare il db al primo accesso*/
    public static Point[] populateData(){

        return new Point[]{
                new Point("I dati devono essere trattati in modo lecito, corretto e trasparente", "Bassa", 5, 0, 1, null, "25/05/2018" ),
                new Point("Le finalità devono essere determinate, esplicite e legittime", "Bassa", 5, 0, 1, null, "25/05/2018" ),
                new Point("I dati devono essere adeguati, pertinenti, esatti ed aggiornati", "Bassa", 5, 0, 1, null, "25/05/2018" ),
                new Point("I minori possono prestare il consenso se età>16 anni", "Bassa", 8, 0, 1, null, "25/05/2018" ),
                new Point("Dati sensibili richiedono un consenso apposito", "Bassa", 9, 0, 1, null, "25/05/2018" ),
                new Point("Dati 'giudiziari' richiedono il controllo dell'autorità pubblica", "Bassa", 10, 0, 1, null, "25/05/2018" ),
                new Point("Il Titolare deve fornire in modo chiaro e trasparente info sul trattamento dei dati", "Bassa", 12, 0, 1, null, "25/05/2018" ),
                new Point("All'interessato deve essere fornita un'informativa trasparente sul trattamento dei propri dati", "Bassa", 13, 0, 1, null, "25/05/2018" ),
                new Point("L'interessato può accedere ai propri dati, modificarli e cancellarli...", "Bassa", 15, 0, 1, null, "25/05/2018" ),
                new Point("Il Titolare deve garantire la conformità del trattamento al Regolamento", "Bassa", 24, 0, 1, null, "25/05/2018" ),
                new Point("Nel caso di cotitolarità, l'accordo tra responsabilità deve essere a disposizione degli utenti", "Bassa", 26, 0, 1, null, "25/05/2018" )
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
                return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (id != other.id)
            return false;

        return true;
    }


}
