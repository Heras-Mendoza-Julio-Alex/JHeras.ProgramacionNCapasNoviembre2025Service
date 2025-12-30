package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RolJPADAOImplementation implements IRolJPA{

    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result getAll() {
        Result result = new Result();
        try {
            TypedQuery<Rol> queryRol = entityManager.createQuery("FROM Rol", Rol.class);
            List<Rol> roles = queryRol.getResultList();

            result.object = roles;
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
