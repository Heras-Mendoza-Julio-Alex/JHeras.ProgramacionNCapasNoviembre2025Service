package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.MunicipioJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/municipio")
public class MunicipioRestController {

    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetallMunicipio() {
        Result result = municipioJPADAOImplementation.getAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }
     @GetMapping("/getMunicipioByEstado/{IdEstado}")
    public ResponseEntity getById(@PathVariable("IdEstado") int IdEstado) {
        Result result = municipioJPADAOImplementation.getByID(IdEstado);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
