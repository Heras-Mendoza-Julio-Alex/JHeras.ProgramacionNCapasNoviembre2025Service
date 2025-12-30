package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Usuario;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;

public interface IUsuarioJPA {
    
    public Result getall();
    
    public Result add(Usuario usuario);
    
    public Result edit(Usuario usuario,int IdUsuario);
    
    public Result delete(int IdUsuario);
    
    public Result getById(int IdUsuario);
    
    public Result bajalogica(int IdUsuario, Usuario usuario);
    
    public Result busqueda(Usuario usuario);
    
    public Result Imagen(int IdUsuario, String imagenCadena);

}
