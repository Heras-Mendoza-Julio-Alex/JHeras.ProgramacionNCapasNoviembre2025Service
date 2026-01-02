package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Estado;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class EstadoJPADAOImplementation implements IEstadoJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Estado> queryEstado = entityManager.createQuery("FROM Estado", Estado.class);
            List<Estado> estados = queryEstado.getResultList();

            result.object = estados;
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
    public Result getByID(int IdPais) {
        Result result = new Result();

        try {

            String jpql = "SELECT e FROM Estado e WHERE e.Pais.idPais = :idParam";

            List<Estado> listaEstados = entityManager
                    .createQuery(jpql, Estado.class)
                    .setParameter("idParam", IdPais)
                    .getResultList();

            if (!listaEstados.isEmpty()) {
                result.object = listaEstados;
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
