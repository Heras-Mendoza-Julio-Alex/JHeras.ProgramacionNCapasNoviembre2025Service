package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.RolJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rol")
public class RolRestController {

    @Autowired
    private RolJPADAOImplementation rolJPADAOImplementation;

    @GetMapping
    public ResponseEntity GetallRol() {
        Result result = rolJPADAOImplementation.getAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}

