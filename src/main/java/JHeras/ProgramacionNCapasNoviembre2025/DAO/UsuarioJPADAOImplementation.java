package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result getall() {
        Result result = new Result();
        try {
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();

            result.object = usuarios;
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
    public Result getById(int IdUsuario) {
        Result result = new Result();

        try {

            Usuario usuarioBD = entityManager.find(Usuario.class, IdUsuario);

            result.object = usuarioBD;

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
    public Result add(Usuario usuario) {
        Result result = new Result();

        try {

            if (usuario.Direcciones != null && !usuario.Direcciones.isEmpty()) {
                Direccion dir = usuario.Direcciones.get(0);

                if (dir.getCalle() == null || dir.getCalle().trim().isEmpty()) {
                    usuario.Direcciones = null;
                }
            }
            entityManager.persist(usuario);

            if (usuario.Direcciones != null && !usuario.Direcciones.isEmpty()) {
                Direccion dir = usuario.Direcciones.get(0);
                dir.Usuario = usuario;
                entityManager.persist(dir);
            }

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
    public Result edit(Usuario usuario, int IdUsuario) {
        Result result = new Result();
        try {
            Usuario usuarioBD = entityManager.find(Usuario.class, IdUsuario);
            if (usuarioBD != null) {
                usuario.setIdUsuario(IdUsuario);
                usuario.setRol(usuario.getRol() == null || usuario.getRol().getIdRol() == 0 ? usuarioBD.getRol() : usuario.getRol());
                usuario.setSexo(usuario.getSexo() == null ? usuarioBD.getSexo() : usuario.getSexo());
                usuario.setPassword(usuario.getPassword() == null ? usuarioBD.getPassword() : usuario.getPassword());
                Integer estatusRecibido = usuario.getEstatus();

                if (estatusRecibido == null) {
                    usuario.setEstatus(usuarioBD.getEstatus());
                } else if (estatusRecibido.intValue() == 0) {
                    usuario.setEstatus(usuarioBD.getEstatus());
                }
                if (usuario.Direcciones != null && !usuario.Direcciones.isEmpty()) {
                    Direccion dir = usuario.Direcciones.get(0);
                    if (dir.getCalle() == null || dir.getCalle().trim().isEmpty()) {
                        usuario.Direcciones = usuarioBD.Direcciones;
                    } else {
                        dir.Usuario = usuario;
                        entityManager.merge(dir);
                    }
                } else {
                    usuario.Direcciones = usuarioBD.Direcciones;
                }
                entityManager.merge(usuario);
                result.Correct = true;
                result.StatusCode = 200;
            } else {
                result.StatusCode = 404;
                result.ErrorMessage = "Usuario no encontrado";
            }

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
    public Result delete(int IdUsuario
    ) {

        Result result = new Result();

        try {
            Usuario usuario = entityManager.find(Usuario.class, IdUsuario);

            if (usuario != null) {
                entityManager.remove(usuario);

            }
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
    public Result bajalogica(int IdUsuario, Usuario usuario
    ) {
        Result result = new Result();

        try {

            Usuario usuarioBD = entityManager.find(Usuario.class, IdUsuario);

            if (usuarioBD != null) {

                usuarioBD.setEstatus(usuario.getEstatus());

            }

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

    @Override
    public Result busqueda(Usuario usuario
    ) {
        Result result = new Result();

        StringBuilder query = new StringBuilder("FROM Usuario WHERE UPPER(Nombre) LIKE UPPER(:nombre)"
                + " AND UPPER(ApellidoPaterno) LIKE UPPER(:apellidoPaterno)"
                + " AND UPPER(ApellidoMaterno) LIKE UPPER(:apellidoMaterno)");

        if (usuario.getRol() != null && usuario.getRol().getIdRol() != 0) {
            query.append(" AND Rol.idRol = :idRol");
        }

        TypedQuery<Usuario> queryUsuarios = entityManager.createQuery(query.toString(), Usuario.class);

        queryUsuarios.setParameter("nombre", "%" + usuario.getNombre() + "%");
        queryUsuarios.setParameter("apellidoPaterno", "%" + usuario.getApellidoPaterno() + "%");
        queryUsuarios.setParameter("apellidoMaterno", "%" + usuario.getApellidoMaterno() + "%");

        if (usuario.getRol() != null && usuario.getRol().getIdRol() != 0) {
            queryUsuarios.setParameter("idRol", usuario.Rol.getIdRol());
        }

        List<Usuario> usuarios = queryUsuarios.getResultList();
        result.Objects = new ArrayList<>();

        for (Usuario item : usuarios) {
            result.Objects.add(item);
        }
        result.StatusCode = 200;
        return result;
    }

    @Override
    @Transactional
    public Result Imagen(int idUsuario, String imagenCadena) { 
        Result result = new Result();
        try {
            Usuario usuarioBD = entityManager.find(Usuario.class, idUsuario);
            if (usuarioBD != null) {                
                usuarioBD.setImagen(imagenCadena);
                entityManager.merge(usuarioBD);

                result.Correct = true;
            } else {
                result.Correct = false;
                result.ErrorMessage = "Usuario no encontrado";
            }
        } catch (Exception e) {
            result.Correct = false;
            result.ErrorMessage = e.getMessage();
        }
        return result;
    }
}
