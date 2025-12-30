package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.PaisJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
public class PaisRestController {
      
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetallPais() {
        Result result = paisJPADAOImplementation.getAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}

