package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.ColoniaJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/colonia")
public class ColoniaRestController {

    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetallEstado() {
        Result result = coloniaJPADAOImplementation.getAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }
    @GetMapping("/getColoniaByMunicipio/{IdMunicipio}")
    public ResponseEntity getById(@PathVariable("IdMunicipio") int IdMunicipio) {
        Result result = coloniaJPADAOImplementation.getByID(IdMunicipio);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
