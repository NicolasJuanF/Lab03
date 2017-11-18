package frsf.isi.grupojf.lab03.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class WorkFromHomeOpenHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_CATEGORIA = "CREATE TABLE "+Constants.TABLA_CATEGORIA +"(" +
            Constants.ID_CATEGORIA +"primary key autoincrement," +
            Constants.DESCRIPCION_CATEGORIA+ "text);";


    private static final String SQL_CREATE_TRABAJO= "CREATE TABLE "+ Constants.TABLA_TRABAJO+"(" +
            Constants.ID_TRABAJO +"integer primary key autoincrement," +
            Constants.DESCRIPCION_TRABAJO + "text," +
            Constants.HORAS_PRESUPUESTADAS + "integer, " +
            Constants.FECHA_ENTREGA + "text, " +
            Constants.PRECIO_MAXIMO + "precio real," +
            Constants.MONEDA +"integer, " +
            Constants.REQUIERE_INGLES + "integer," +
            Constants.CATEGORIA_FK + "integer," +
            "FOREIGN KEY ("+ Constants.CATEGORIA_FK+") REFERENCES "+Constants.TABLA_CATEGORIA+"("+Constants.ID_CATEGORIA+"));";


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

