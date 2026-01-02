package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Municipio;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Municipio> queryMunicipio = entityManager.createQuery("FROM Municipio", Municipio.class);
            List<Municipio> municipios = queryMunicipio.getResultList();

            result.object = municipios;
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
    public Result getByID(int IdEstado) {
        Result result = new Result();

        try {
            String jpql = "SELECT e FROM Municipio e WHERE e.Estado.idEstado = :idParam";

            List<Municipio> lista = entityManager
                    .createQuery(jpql, Municipio.class)
                    .setParameter("idParam", IdEstado)
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
