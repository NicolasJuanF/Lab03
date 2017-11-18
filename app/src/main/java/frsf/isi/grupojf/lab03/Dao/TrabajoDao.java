package frsf.isi.grupojf.lab03.Dao;

/**
 * Created by usuario on 8/11/2017.
 */

        import java.text.ParseException;
        import java.util.List;

        import frsf.isi.grupojf.lab03.Categoria;
        import frsf.isi.grupojf.lab03.Trabajo;


public interface TrabajoDao {
    /**
     *  Lista de las categorias predefinidas
     * @return List<Categoria>
     */
    List<Categoria> listaCategoria();

    /**
     * Crea un nuevo trabajo
     * @param p Trabajo a crear
     */
    void crearOferta(Trabajo p);

    /**
     * Retorna la lista de trabajos
     * @return List<Trabajo>
     */
    List<Trabajo> listaTrabajos() throws ParseException;
}
