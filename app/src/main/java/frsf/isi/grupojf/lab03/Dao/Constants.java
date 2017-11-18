package frsf.isi.grupojf.lab03.Dao;

/**
 * Created by usuario on 17/11/2017.
 */

public class Constants {

    public static final String DATABASE_NAME = "workfromhome";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLA_CATEGORIA = "categoria";
    public static final String ID_CATEGORIA = "_id";
    public static final String DESCRIPCION_CATEGORIA = "descripcion";

    public static final String TABLA_TRABAJO = "trabajos";
    public static final String ID_TRABAJO = "_id";
    public static final String DESCRIPCION_TRABAJO= "descripcion";
    public static final String HORAS_PRESUPUESTADAS= "horas_presupuestadas";
    public static final String PRECIO_MAXIMO= "precio_max_hora";
    public static final String CATEGORIA_FK= "categoria";
    public static final String FECHA_ENTREGA= "fecha_entrega";
    public static final String MONEDA= "moneda_pago";
    public static final String REQUIERE_INGLES= "requiere_ingles";
}
