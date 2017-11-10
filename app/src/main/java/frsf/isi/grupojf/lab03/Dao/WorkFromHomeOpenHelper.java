package frsf.isi.grupojf.lab03.Dao;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;


public class WorkFromHomeOpenHelper extends SQLiteOpenHelper {

    //correctamente seria hacerlo con constantes, en una clase java distinta
    private static final String SQL_CREATE_CATEGORIA = "CREATE TABLE categoria (_id integer primary key autoincrement, descripcion text);";

    private static final String SQL_CREATE_TRABAJO= "CREATE TABLE trabajo (" +
            "_id integer primary key autoincrement," +
            "descripcion text, " +
            "horas_presupuestadas integer, " +
            "fecha_entrega text, " +
            "precio real," +
            "moneda integer, " +
            "requiere_ingles integer," +
            "id_categoria integer," +
            "FOREIGN KEY (id_categoria) REFERENCES categoria(_id));";

    private static WorkFromHomeOpenHelper _INSTANCE;

    private WorkFromHomeOpenHelper(Context ctx){
        super(ctx,"WORK_FROM_HOME",null,1);
    }

    public static WorkFromHomeOpenHelper getInstance(Context ctx){
        if(_INSTANCE==null) _INSTANCE = new WorkFromHomeOpenHelper(ctx);
        return _INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_CATEGORIA);
        sqLiteDatabase.execSQL(SQL_CREATE_TRABAJO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

