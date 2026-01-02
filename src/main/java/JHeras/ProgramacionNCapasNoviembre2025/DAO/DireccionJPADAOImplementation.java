package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result getById(int IdDireccion) {
        Result result = new Result();

        try {
            Direccion direccionBD = entityManager.find(Direccion.class, IdDireccion);

            if (direccionBD != null) {
                result.object = direccionBD;
                result.StatusCode = 200;
            } else {
                result.StatusCode = 404;
            }

            result.Correct = true;

        } catch (Exception ex) {
            result.ErrorMessage = ex.getLocalizedMessage();
            result.Correct = false;
            result.StatusCode = 500;
        }

        return result;
    }

    @Transactional
    @Override
    public Result edit(Direccion direccion, int IdUsuario) {
        Result result = new Result();

        try {
            TypedQuery<Direccion> queryDireccion = entityManager.createQuery(
                    "FROM Direccion WHERE idDireccion = :idDireccion",
                    Direccion.class
            );

            queryDireccion.setParameter("idDireccion", direccion.getIdDireccion());

            Direccion direccionJPA = queryDireccion.getSingleResult();

            if (direccionJPA != null) {
                direccion.Usuario = direccionJPA.Usuario;
                entityManager.merge(direccion);

            } else {
                result.StatusCode = 404;
                result.Correct = false;
            }
            result.Correct = true;
            result.StatusCode = 201;

        } catch (Exception ex) {
            result.ErrorMessage = ex.getLocalizedMessage();
            result.Correct = false;
            result.ex = ex;
            result.StatusCode = 500;
        }

        return result;

    }

    @Transactional
    @Override
    public Result add(Direccion direccion, int IdUsuario) {
        Result result = new Result();

        direccion.Usuario = new Usuario();
        direccion.Usuario.setIdUsuario(IdUsuario);
                

        try {

            entityManager.persist(direccion);
            result.Correct = true;
            result.StatusCode = 200;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }

        return result;

    }

    @Transactional
    @Override
    public Result delete(int idDireccion) {
        Result result = new Result();

        try {
            Direccion direccion = entityManager.find(Direccion.class, idDireccion);

            if (direccion != null) {
                entityManager.remove(direccion);
                result.StatusCode = 200;
            }else{
                result.StatusCode=404;
            }

            result.Correct = true;
        } catch (Exception ex) {
            result.Correct = false;
            result.ErrorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.StatusCode = 500;
        }

        return result;

    }

}
