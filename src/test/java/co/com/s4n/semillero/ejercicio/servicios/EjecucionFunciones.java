package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Movimientos;
import co.com.s4n.semillero.ejercicio.files.LeerEscribir;
import io.vavr.collection.Iterator;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EjecucionFunciones {

    static List<Dron> resultados = new ArrayList<>();
    static int cont = 0;
    static int cantidadAlmuerzos = 3;
    static int limite = 10;


    public static List<Dron> ejecutarDron(){

        List<String> almuerzos = LeerEscribir.read();
        io.vavr.collection.List<String> listaAlmuerzos = io.vavr.collection.List.ofAll(almuerzos);

        Iterator<io.vavr.collection.List<String>> almuerzosAgru = iteradorLista(listaAlmuerzos,cantidadAlmuerzos);
        almuerzosAgru.forEach(s ->{
            realizarEntrega(s);
        });

        for (int j = 0; j < resultados.size(); j++){
            System.out.println("resultados "+resultados.get(j));
        }

        LeerEscribir.write(resultados);

        return resultados;
    }

    public static void realizarEntrega(io.vavr.collection.List<String> ruta){

        //Future<Dron> inicio = Future.of(() -> new Dron(0,0, Direccion.NORTE));

        /*Future<String> ruta1 = Future.of(()->ruta.get(0));
        Future<String> ruta2 = Future.of(()->ruta.get(1));
        Future<String> ruta3 = Future.of(()->ruta.get(2));*/

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
        //System.out.println(dronMoved.get());
        Dron dronInicio = new Dron(0,0,Direccion.NORTE);

        if (dronMoved.isSuccess()){
            //System.out.println(validarPosicion().get()));
            resultados.add(dronNuevo);
        }else{
            dronNuevo = dronInicio;
        }
        return dronNuevo.toString();
    }

    public static Dron ejecutarFuncion(Dron dron, String ruta){
        Dron dronR = dron;
        boolean ban = true;
        for (int j = 0; j<ruta.length();j++){
            char c = ruta.charAt(j);
            //Movimientos mover = Movimientos.valueOf(String.valueOf(c));
            switch (c){
                case 'A':
                    dronR = ServicioDron.ahead(dronR);
                    break;
                case 'D':
                    dronR = ServicioDron.right(dronR);
                    break;
                case 'I':
                    dronR = ServicioDron.left(dronR);
                    break;
                default:
                    System.out.println("Error, ruta invalida!");
                    ban = false;
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