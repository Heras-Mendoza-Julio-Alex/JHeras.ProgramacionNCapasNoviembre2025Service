package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Pais;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Pais> queryPais = entityManager.createQuery("FROM Pais", Pais.class);
            List<Pais> Paises = queryPais.getResultList();

            result.object = Paises;
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

}
