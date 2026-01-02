package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.ColoniaJPADAOImplementation;
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
@RequestMapping("api/colonia")
@Tag(name = "Colonia", description = "Controlador de operaciones de la Colonia")
public class ColoniaRestController {

    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;

//    @GetMapping
//    public ResponseEntity GetallEstado() {
//        Result result = coloniaJPADAOImplementation.getAll();
//        return ResponseEntity.status(result.StatusCode).body(result);
//    }
    @GetMapping("/getColoniaByMunicipio/{IdMunicipio}")
    @Operation(summary = "Obtener Colonia según el id de Municipio", description = "Obtener los Colonias que exiten en la base de datos según el id del Municipio")
    @ApiResponse(responseCode = "200", description = "Se recuperaron las Colonias de manera correcta")
    @ApiResponse(responseCode = "500", description = "Algo salio mal al realizar la operacion")
    @ApiResponse(responseCode = "404", description = "No se encontraron municipios")
    public ResponseEntity getById(@PathVariable("IdMunicipio") int IdMunicipio) {
        Result result = coloniaJPADAOImplementation.getByID(IdMunicipio);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}
