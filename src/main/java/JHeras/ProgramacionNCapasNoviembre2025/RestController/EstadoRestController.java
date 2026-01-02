package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.EstadoJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/estado")
@Tag(name = "Estado", description = "Controlador de operaciones del Estado")
public class EstadoRestController {

    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;

//    @GetMapping
//    public ResponseEntity GetallEstado() {
//        Result result = estadoJPADAOImplementation.getAll();
//        return ResponseEntity.status(result.StatusCode).body(result);
//    }
    @GetMapping("/getEstadosByPais/{IdPais}")
    @Operation(summary = "Obtener Estados según el id de Pais", description = "Obtener los Estados que exiten en la base de datos según el id del Pais")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los Estados de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No se encontraron municipios")
    
    public ResponseEntity getById(@PathVariable("IdPais") int IdPais) {
        Result result = estadoJPADAOImplementation.getByID(IdPais);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
