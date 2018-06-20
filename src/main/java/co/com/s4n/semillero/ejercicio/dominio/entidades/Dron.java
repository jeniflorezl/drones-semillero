package co.com.s4n.semillero.ejercicio.dominio.entidades;

import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;

public class Dron {

    Integer x;
    Integer y;
    Direccion direccion;

    public Dron(Integer x, Integer y, Direccion direccion){
        this.x = x;
        this.y = y;
        this.direccion = direccion;
    }

    public Integer getX() {
        return this.x;
    }

    public Integer getY() {
        return this.y;
    }

    public Direccion getDireccion() {
        return this.direccion;
    }

    public String toString() {
        return this.x+","+this.y+","+this.direccion;
    }
}
