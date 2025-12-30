package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.DireccionJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/direccion")
public class DireccionRestController {

    @Autowired

    private DireccionJPADAOImplementation direccionJPADAOImplementation;

    @GetMapping("/{idDireccion}")
    public ResponseEntity getById(@PathVariable("idDireccion") int idDireccion) {
        Result result = direccionJPADAOImplementation.getById(idDireccion);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    @PutMapping("/{IdUsuario}/{idDireccion}")
    public ResponseEntity Update(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("idDireccion") int idDireccion,@RequestBody Direccion direccion) {
        direccion.setIdDireccion(idDireccion);
        Result result = direccionJPADAOImplementation.edit(direccion, IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    @PostMapping(value = "/add/{IdUsuario}", consumes = "application/json")
    public ResponseEntity addDireccion(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Direccion direccion) {
       
        Result result = direccionJPADAOImplementation.add(direccion, IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    @DeleteMapping("/{IdUsuario}/{idDireccion}")
    public ResponseEntity Delete(@PathVariable int idDireccion) {
        Result result = direccionJPADAOImplementation.delete(idDireccion);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
