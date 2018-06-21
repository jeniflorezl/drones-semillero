package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Movimientos;
import co.com.s4n.semillero.ejercicio.files.LeerEscribir;
import io.vavr.collection.Iterator;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;

public class ServicioDron {

    static List<Dron> resultados = new ArrayList<>();
    static int cont = 0;
    static int cantidadAlmuerzos = 3;
    static int limite = 10;


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

    public static List<Dron> ejecutarDron(io.vavr.collection.List<String> listaRutas){

        Iterator<io.vavr.collection.List<String>> almuerzosAgru = iteradorLista(listaRutas,cantidadAlmuerzos);
        almuerzosAgru.forEach(s ->{
            realizarEntrega(s);
        });

        for (int j = 0; j < resultados.size(); j++){
            System.out.println("resultados "+resultados.get(j));
        }

        return resultados;
    }

    public void generarArchivo(List<Dron> resultados){
        Try<String> writeFile = escribirDrones(resultados);
    }

    public io.vavr.collection.List<String> leerRutas(){
        return LeerEscribir.read();
    }

    public Try<String> escribirDrones(List<Dron> resultados){
        return LeerEscribir.write(resultados);
    }

    public static void realizarEntrega(io.vavr.collection.List<String> ruta){

        Dron dronInicio = new Dron(0,0,Direccion.NORTE);
        String zero = dronInicio.toString();
        String fold = ruta.fold(zero, (s1, s2) -> operar(s1, s2));
        String[] dron = fold.split("\\,");
        Dron dRes = new Dron(Integer.parseInt(dron[0]),Integer.parseInt(dron[1]), cambiarDireccion(dron[2]));

    }

    public static Direccion cambiarDireccion(String direccion){
        Direccion dir = Direccion.NORTE;
        switch (direccion){
            case "NORTE":
                dir = Direccion.NORTE;
                break;
            case "ESTE":
                dir = Direccion.ESTE;
                break;
            case "SUR":
                dir = Direccion.SUR;
                break;
            case "OESTE":
                dir = Direccion.OESTE;
                break;
            default:
                break;
        }
        return dir;
    }

    public static Iterator<io.vavr.collection.List<String>> iteradorLista(io.vavr.collection.List<String> almuerzos, int tamaño){
        return almuerzos.grouped(tamaño);
    }

    public static String operar(String posicion, String ruta){
        String[] dron1 = posicion.split("\\,");
        Dron d = new Dron(Integer.parseInt(dron1[0]),Integer.parseInt(dron1[1]), cambiarDireccion(dron1[2]));
        Dron dronNuevo = ejecutarFuncion(d,ruta);
        Try<String> dronMoved = validarPosicion(dronNuevo);
        Dron dronInicio = new Dron(0,0,Direccion.NORTE);
        if (dronMoved.isSuccess()){
            resultados.add(dronNuevo);
        }else{
            dronNuevo = d;
        }
        return dronNuevo.toString();
    }

    public static Dron ejecutarFuncion(Dron dron, String ruta){
        Dron dronR = dron;
        boolean ban = true;
        for (int j = 0; j<ruta.length();j++){
            String c = String.valueOf(ruta.charAt(j));
            Try<Movimientos> mover = Try.of(()-> Movimientos.valueOf(c));
            if (mover.isSuccess()){
                switch (mover.get()){
                    case A:
                        dronR = ServicioDron.ahead(dronR);
                        break;
                    case D:
                        dronR = ServicioDron.right(dronR);
                        break;
                    case I:
                        dronR = ServicioDron.left(dronR);
                        break;
                    default:
                        break;
                }
            }else{
                System.out.println("Error, ruta invalida!");
                ban=false;
                break;
            }
            if (ban==false){
                break;
            }
        }
        return dronR;
    }


    public static Try<String> validarPosicion(Dron dron){
        Dron d = dron;
        Integer x = dron.getX();
        Integer y = dron.getY();
        if (x<=limite && x>= -limite && y<=limite && y>= -limite){
            return Try.of(()-> "ok");
        }else{
            return Try.of(()-> { throw new Error("Error");});
        }
    }

}
