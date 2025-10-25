package org.utl.dsm.viewmodel;

public class LibroViewModel {
    private int id;
    private String nombre;
    private String autor;
    private String genero;
    private int estatus;
    private String archivo;
    private String universidad;

    public LibroViewModel() {
    }

    public LibroViewModel(int id, String nombre, String autor, String genero, int estatus, String archivo, String universidad) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.estatus = estatus;
        this.archivo = archivo;
        this.universidad = universidad;
    }

    public LibroViewModel(String nombre, String autor, String genero, int estatus, String archivo, String universidad) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.estatus = estatus;
        this.archivo = archivo;
        this.universidad = universidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LibroViewModel{");
        sb.append("id:").append(id);
        sb.append(", nombre:").append(nombre);
        sb.append(", autor:").append(autor);
        sb.append(", genero:").append(genero);
        sb.append(", estatus:").append(estatus);
        sb.append(", archivo:").append(archivo);
        sb.append(", universidad:").append(universidad);
        sb.append('}');
        return sb.toString();
    }
}
