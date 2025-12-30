package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.EstadoJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/estado")
public class EstadoRestController {

    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetallEstado() {
        Result result = estadoJPADAOImplementation.getAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    @GetMapping("/getEstadosByPais/{IdPais}")
    public ResponseEntity getById(@PathVariable("IdPais") int IdPais) {
        Result result = estadoJPADAOImplementation.getByID(IdPais);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
