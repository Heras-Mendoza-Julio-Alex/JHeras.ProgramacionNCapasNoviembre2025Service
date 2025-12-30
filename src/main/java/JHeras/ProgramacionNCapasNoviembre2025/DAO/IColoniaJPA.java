package JHeras.ProgramacionNCapasNoviembre2025.DAO;

import JHeras.ProgramacionNCapasNoviembre2025.JPA.Result;


public interface IColoniaJPA {
    
    public Result getAll();
    
    public Result getByID(int IdMunicipio);

}
