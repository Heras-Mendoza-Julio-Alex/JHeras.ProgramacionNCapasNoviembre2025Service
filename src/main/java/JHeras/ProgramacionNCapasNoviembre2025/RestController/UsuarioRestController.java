package JHeras.ProgramacionNCapasNoviembre2025.RestController;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.EstadoJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.PaisJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.DAO.UsuarioJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;


    /*------------------------------- Usuario ----------------------------*/
 /*---------  Obtener los usuarios  ---------*/
    @GetMapping
    public ResponseEntity Getall() {
        Result result = usuarioJPADAOImplementation.getall();
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Obtener un usuario por su ID  ---------*/
    @GetMapping("/{idUsuario}")
    public ResponseEntity getById(@PathVariable("idUsuario") int idUsuario) {
        Result result = usuarioJPADAOImplementation.getById(idUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Añadir un usuario ---------*/
//    @PostMapping(value = "/add", consumes = "application/json")
//    public ResponseEntity addUsuario(@RequestBody Usuario usuario) {
//        Result result = usuarioJPADAOImplementation.add(usuario);
//        return ResponseEntity.status(result.StatusCode).body(result);
//
//    }

    /*     Prueba de add con imagen*/
    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity addUsuario(
            @RequestPart("usuario") Usuario usuario,
            @RequestPart(value = "Imagen", required = false) MultipartFile Imagen
    ) throws IOException {

        if (Imagen != null) {
            String extencion = Imagen.getOriginalFilename().split("\\.")[1];
            //imagen.png
            //[imagen,png]
            if (extencion.equals("png") || extencion.equals("jpg") || extencion.equals("jpeg")) {
                byte[] bytes = Imagen.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(bytes);
                usuario.setImagen(base64Image);

            }
        }
        usuario.setEstatus(1);
        Result result = usuarioJPADAOImplementation.add(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Editar usuario ---------*/
    @PutMapping("/{IdUsuario}")
    public ResponseEntity Update(@RequestBody Usuario usuario, @PathVariable int IdUsuario) {
        Result result = usuarioJPADAOImplementation.edit(usuario, IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Borrar usuario ---------*/
    @DeleteMapping("/{IdUsuario}")
    public ResponseEntity Delete(@PathVariable int IdUsuario) {
        Result result = usuarioJPADAOImplementation.delete(IdUsuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Estatus usuario (baja logica) ---------*/
    @PatchMapping("/{IdUsuario}/baja")
    public ResponseEntity bajaL(@PathVariable int IdUsuario, @RequestBody Usuario usuario) {
        Result result = usuarioJPADAOImplementation.bajalogica(IdUsuario, usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Busqueda usuario ---------*/
    @PostMapping("/getAllDinamico")
    public ResponseEntity getAllD(@RequestBody Usuario usuario) {
        Result result = usuarioJPADAOImplementation.busqueda(usuario);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

    /*---------  Imagen usuario (actualizacion) ---------*/
    @RequestMapping(value = "/{IdUsuario}/imagen", method = {RequestMethod.POST, RequestMethod.PATCH})
    public ResponseEntity Imagen(@PathVariable int IdUsuario, @RequestBody String imagenBase64) {
        // Pon tu breakpoint aquí. ¡Ahora sí debería entrar!
        Result result = usuarioJPADAOImplementation.Imagen(IdUsuario, imagenBase64);
        return ResponseEntity.status(result.StatusCode).body(result);
    }

}

//    /*---------  Imagen usuario  ---------*/
////    @PatchMapping("/{IdUsuario}")
////    public ResponseEntity Imagen(@PathVariable int IdUsuario, @RequestBody Usuario usuario) {
////        Result result = usuarioJPADAOImplementation.bajalogica(IdUsuario, usuario);
////        return ResponseEntity.status(result.StatusCode).body(result);
////    }
//    //multipartformdata
//    @RequestMapping(path = "/upload-files", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadFiles(MultipartHttpServletRequest request) {
//
//        // Extracting files as a Map<String, MultipartFile>
//        Map<String, MultipartFile> fileMap = request.getFileMap();
//        for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
//            String fileKey = entry.getKey();
//            MultipartFile file = entry.getValue();
//
//            if (!file.isEmpty()) {
//                System.out.println("File Key: " + fileKey + " | Uploaded file name: " + file.getOriginalFilename());
//            }
//        }
//        return ResponseEntity.ok("CasePaperAnswer saved successfully with files!");
//    }
