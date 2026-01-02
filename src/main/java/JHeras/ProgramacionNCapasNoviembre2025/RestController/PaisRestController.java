package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.PaisJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pais")
@Tag(name = "Pais", description = "Controlador de operaciones del Pais")
public class PaisRestController {

    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;

    @GetMapping
    @Operation(summary = "Obtener paises", description = "Obtener los paises que exiten en la base de datos")
    @ApiResponse(responseCode = "200", description = "Se recuperaron los paises de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    public ResponseEntity GetallPais() {
        Result result = paisJPADAOImplementation.getAll();
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
