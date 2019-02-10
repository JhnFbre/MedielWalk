package team.keepBurning;

public class Comidas {

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }

    public Integer getPorcion() {
        return porcion;
    }

    public void setPorcion(Integer porcion) {
        this.porcion = porcion;
    }

    public Comidas(String nombre, int calorias, int porcion){
        this.nombre=nombre;
        this.calorias= calorias;
        this.porcion= porcion;




    }



    String nombre;
    Integer calorias;
    Integer porcion;


}
