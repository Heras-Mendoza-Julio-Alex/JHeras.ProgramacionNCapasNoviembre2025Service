package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Direccion;
import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;

public interface IDireccionJPA {

    public Result getById(int IdDireccion);        
    
    public Result add(Direccion direccion,int IdUsuario);
    
    public Result edit(Direccion direccion,int IdUsuario);
    
    public Result delete(int idDireccion);
    
}
