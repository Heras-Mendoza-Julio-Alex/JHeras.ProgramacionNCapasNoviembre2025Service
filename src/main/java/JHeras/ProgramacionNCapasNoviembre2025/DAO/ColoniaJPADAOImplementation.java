package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Colonia;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPA{
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Colonia> queryColonia = entityManager.createQuery("FROM Colonia", Colonia.class);
            List<Colonia> colonias = queryColonia.getResultList();

            result.object = colonias;
            result.Correct = true;
            result.StatusCode = 200;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 400;
        }

        return result;
    }

    @Transactional
    @Override
    public Result getByID(int IdMunicipio) {
         Result result = new Result();

        try {
            String jpql = "SELECT e FROM Colonia e WHERE e.Municipio.idMunicipio = :idParam";

            List<Colonia> lista = entityManager
                    .createQuery(jpql, Colonia.class)
                    .setParameter("idParam", IdMunicipio)
                    .getResultList();

            if (!lista.isEmpty()) {
                result.object = lista;
                result.StatusCode = 200;
            }else{
                result.StatusCode=404;
            }                      
            result.Correct = true;

        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 400;
        }

        return result;
    }

}
