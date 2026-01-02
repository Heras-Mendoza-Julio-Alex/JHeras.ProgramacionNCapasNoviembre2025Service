package JHeras.ProgramacionNCapasNoviembre2025.Service;

import JHeras.ProgramacionNCapasNoviembre2025.DAO.UsuarioJPADAOImplementation;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Rol;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.ErrorCarga;
import java.util.Random;
import org.springframework.validation.BindingResult;

@Service
public class CargaMasivaService {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Autowired
    private ValidationService validatorService;

    public String tokenArchivo(MultipartFile archivo) {

        /*--------------------Parte del token---------------------------*/
        String token = "";
         
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(archivo.getOriginalFilename().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            token = hexString.toString();
        } catch (Exception ex) {
            return "Error al convertir en SHA-256";
        }

        /*---------------------Parte de copiar el archivo------------------*/
        String path = System.getProperty("user.dir");
        String pathArchivo = "src\\main\\resources\\archivos";
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));;

        String rutaAbsoluta = path + "/" + pathArchivo + "/" + fecha + archivo.getOriginalFilename();

        try {
            archivo.transferTo(new File(rutaAbsoluta));
        } catch (IOException ex) {
            Logger.getLogger(UsuarioJPADAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(UsuarioJPADAOImplementation.class.getName()).log(Level.SEVERE, null, ex);
        }
        String nombreFinal = fecha + archivo.getOriginalFilename();
        File archivoDestino = new File(path + "/" + pathArchivo + "/" + nombreFinal);

        /*--------------------Parte de los logs---------------------------*/
        String pathLogs = "src\\main\\resources\\logs";

        String rutaAbsolutaLog = path + "/" + pathLogs + "/" + token + fecha + archivo.getOriginalFilename();

        try {
            File logDestino = new File(rutaAbsolutaLog);
            java.nio.file.Files.copy(archivoDestino.toPath(), logDestino.toPath());
        } catch (IOException ex) {
            Logger.getLogger(CargaMasivaService.class.getName()).log(Level.SEVERE, "Error al copiar log", ex);
        }

        return token;

    }

    public List<Usuario> LecturaArchivo(File archivo) {

        List<Usuario> usuarios = new ArrayList<>();
        try (InputStream inputStream = new FileInputStream(archivo); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            bufferedReader.readLine();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] datos = line.split("\\|");

                Usuario usuario = new Usuario();
                usuario.setNombre(datos[0]);
                usuario.setApellidoPaterno(datos[1]);
                usuario.setApellidoMaterno(datos[2]);
                usuario.setTelefono(datos[3]);

                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                java.util.Date fecha = formato.parse(datos[4]);

                usuario.setFechanacimiento(fecha);

                usuario.setUsername(datos[5]);
                usuario.setEmail(datos[6]);
                usuario.setSexo(datos[7]);
                usuario.setCelular(datos[8]);
                usuario.setCurp(datos[9]);
                usuario.setPassword(datos[10]);

                usuario.Rol = new Rol();
                usuario.Rol.setIdRol(Integer.parseInt(datos[11]));

                usuario.setEstatus(Integer.parseInt(datos[12]));
                usuario.setImagen(datos[13]);

                usuarios.add(usuario);
            }

        } catch (Exception ex) {
            return usuarios;
        }

        return usuarios;
    }

    public List<Usuario> LecturaArchivoExcel(File archivo) {
        List<Usuario> usuarios = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo)) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Usuario usuario = new Usuario();
                usuario.setNombre(row.getCell(0).toString());
                usuario.setApellidoPaterno(row.getCell(1).toString());
                usuario.setApellidoMaterno(row.getCell(2).toString());
                usuario.setTelefono(row.getCell(3).toString());
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                String fecha = row.getCell(4).toString();
                Date fechaN = formato.parse(fecha);

                usuario.setFechanacimiento(fechaN);

                usuario.setUsername(row.getCell(5).toString());
                usuario.setEmail(row.getCell(6).toString());
                usuario.setSexo(row.getCell(7).toString());
                usuario.setCelular(row.getCell(8).toString());

                usuario.setCurp(row.getCell(9).toString());
                usuario.setPassword(row.getCell(10).toString());

                usuario.Rol = new Rol();
                int idr = Integer.parseInt(row.getCell(11).toString());

                usuario.Rol.setIdRol(idr);
                
                usuario.setEstatus(Integer.parseInt(row.getCell(12).toString()));
                usuario.setImagen(row.getCell(13).toString());
                
                usuarios.add(usuario);
            }
        } catch (Exception ex) {
            System.out.println("error: " + ex);
            return usuarios;
        }

        return usuarios;

    }

    public List<ErrorCarga> ValidarDatos(List<Usuario> usuarios) {

        List<ErrorCarga> erroresCarga = new ArrayList<>();
        int LineaError = 0;

        for (Usuario usuario : usuarios) {
            List<ObjectError> errors = new ArrayList<>();
            LineaError++;

            BindingResult bindingResult = validatorService.validateObjects(usuario);
            if (bindingResult.hasErrors()) {
                errors.addAll(bindingResult.getAllErrors());
            }

            if (usuario.Rol != null) {
                BindingResult bindingRol = validatorService.validateObjects(usuario.Rol);
                if (bindingRol.hasErrors()) {
                    errors.addAll(bindingRol.getAllErrors());
                }
            }

            for (ObjectError error : errors) {
                FieldError fieldError = (FieldError) error;
                ErrorCarga errorCarga = new ErrorCarga();
                errorCarga.Linea = LineaError;
                errorCarga.Campo = fieldError.getField();
                errorCarga.Descripcion = fieldError.getDefaultMessage();
                erroresCarga.add(errorCarga);
            }
        }
        return erroresCarga;
    }

    @Transactional
    public String ProcesarArchivo(String token) {
              
        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "logs";
        File directorio = new File(path);

        File[] archivos = directorio.listFiles((dir, name) -> name.startsWith(token));

        if (archivos == null || archivos.length == 0) {
            return "No se encontro el archivo";
        }

        File archivo = archivos[0];
        String nombreArchivo = archivo.getName();

        try {
            String fechaString = nombreArchivo.substring(64, 64 + 14);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDateTime fechaCarga = LocalDateTime.parse(fechaString, formatter);
            LocalDateTime ahora = LocalDateTime.now();

            if (fechaCarga.plusMinutes(2).isBefore(ahora)) {
                return "ESTATUS: INACTIVO - El tiempo de espera (2 min) ha expirado.";
            }
        } catch (Exception e) {
            return "ERROR";
        }

        String extension = archivo.getName().substring(archivo.getName().lastIndexOf('.') + 1);

        List<Usuario> usuarios;
        if (extension.equalsIgnoreCase("txt")) {
            usuarios = LecturaArchivo(archivo);
        } else if (extension.equalsIgnoreCase("xlsx")) {
            usuarios = LecturaArchivoExcel(archivo);
        } else {
            return "El formato no es txt o xlsx";
        }

        List<ErrorCarga> errores = ValidarDatos(usuarios);

        if (errores.isEmpty()) {
            try {
                for (Usuario usuario : usuarios) {
                    usuarioJPADAOImplementation.add(usuario);
                }
                return "CARGA_EXITOSA";
            } catch (Exception e) {
                return "ERROR_INSERCION: " + e.getMessage();
            }
        } else {
            return "ERRORES_ENCONTRADOS: " + errores.size() + " campos con errores.";
        }
    }
}
