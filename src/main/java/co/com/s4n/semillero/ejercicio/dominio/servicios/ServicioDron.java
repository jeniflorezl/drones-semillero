package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;

public class ServicioDron {

    public static Dron ahead(Dron d){
        Integer x = d.getX();
        Integer y = d.getY();
        switch (d.getDireccion()){
            case NORTE:
                y +=1;
                break;
            case ESTE:
                x+=1;
                break;
            case SUR:
                y -=1;
                break;
            case OESTE:
                x-=1;
                break;
            default:
                break;
        }
        Dron dron = new Dron(x, y, d.getDireccion());
        return dron;
    }

    public static Dron right(Dron d){
        Direccion direccion = d.getDireccion();
        switch (direccion){
            case NORTE:
                direccion = Direccion.ESTE;
                break;
            case ESTE:
                direccion = Direccion.SUR;
                break;
            case SUR:
                direccion = Direccion.OESTE;
                break;
            case OESTE:
                direccion = Direccion.NORTE;
                break;
            default:
                break;
        }
        Dron dron = new Dron(d.getX(), d.getY(), direccion);
        return dron;
    }

    public static Dron left(Dron d){
        Direccion direccion = d.getDireccion();
        switch (direccion){
            case NORTE:
                direccion = Direccion.OESTE;
                break;
            case ESTE:
                direccion = Direccion.NORTE;
                break;
            case SUR:
                direccion = Direccion.ESTE;
                break;
            case OESTE:
                direccion = Direccion.SUR;
                break;
            default:
                break;
        }
        Dron dron = new Dron(d.getX(), d.getY(), direccion);
        return dron;
    }
}
