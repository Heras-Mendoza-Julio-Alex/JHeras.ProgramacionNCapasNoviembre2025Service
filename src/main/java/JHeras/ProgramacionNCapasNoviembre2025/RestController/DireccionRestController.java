package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.DireccionJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Direccion", description = "Controlador de operaciones de la Direccion")
public class DireccionRestController {

    @Autowired

    private DireccionJPADAOImplementation direccionJPADAOImplementation;

    @GetMapping("/{idDireccion}")
    @Operation(summary = "Obtener la direccion según su id", description = "Obtener los datos de la direccion según su ID")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe una direccion con ese id en la base de datos")
    public ResponseEntity getById(@PathVariable("idDireccion") int idDireccion) {
        Result result = direccionJPADAOImplementation.getById(idDireccion);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    @PutMapping("/{IdUsuario}/{idDireccion}")
    @Operation(summary = "Actualizar la direccion según su id", description = "Actualizar los datos de la direccion según su ID")
    @ApiResponse(responseCode = "200", description = "Se actualizaron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe una direccion con ese id en la base de datos")
    public ResponseEntity Update(@PathVariable("IdUsuario") int IdUsuario, @PathVariable("idDireccion") int idDireccion, @RequestBody Direccion direccion) {
        direccion.setIdDireccion(idDireccion);
        Result result = direccionJPADAOImplementation.edit(direccion, IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    @PostMapping(value = "/add/{IdUsuario}", consumes = "application/json")
    @Operation(summary = "Añadir una direccion", description = "Añadir una direccion a un usuario")
    @ApiResponse(responseCode = "200", description = "Se añadieron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    public ResponseEntity addDireccion(@PathVariable("IdUsuario") int IdUsuario, @RequestBody Direccion direccion) {
        Result result = direccionJPADAOImplementation.add(direccion, IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    @DeleteMapping("/{IdUsuario}/{idDireccion}")
    @Operation(summary = "Borrar una direccion según el id", description = "Borrar una direccion a un usuario")
    @ApiResponse(responseCode = "200", description = "Se Borraron los datos de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No existe una direccion con ese id en la base de datos")
    public ResponseEntity Delete(@PathVariable int idDireccion) {
        Result result = direccionJPADAOImplementation.delete(idDireccion);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
