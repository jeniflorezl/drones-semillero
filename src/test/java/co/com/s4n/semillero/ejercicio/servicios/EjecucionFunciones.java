package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Movimientos;
import co.com.s4n.semillero.ejercicio.files.ReadAndFile;
import io.vavr.concurrent.Future;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EjecucionFunciones {

    static List<Dron> resultados = new ArrayList<>();
    static int cont = 0;
    static int cantidadAlmuerzos = 3;


    public static List<Dron> testDron(){

        /*List<String> almuerzos = Arrays.asList("AAAAIAAD", "DDAIAD", "AAIADAD", "AA", "IDAA", "AAI")
                .stream()
                .collect(Collectors.groupingBy());
*/
        List<String> almuerzos = ReadAndFile.read();

        for (int j = 0; j < almuerzos.size(); j++){
            System.out.println(almuerzos.get(j));
        }

        io.vavr.collection.List<String> ruta = io.vavr.collection.List.of();
        for (int i = cont; i<almuerzos.size();i++){
            ruta = cantidadAlmuerzos(almuerzos, cont, cantidadAlmuerzos);
        }

        //Dron dronRes = realizarEntrega(ruta);

        //resultados.add(dronRes);

        for (int j = 0; j < resultados.size(); j++){
            System.out.println(resultados.get(j));
        }

        ReadAndFile.write(resultados);

        return resultados;
    }

    public static io.vavr.collection.List cantidadAlmuerzos(List<String> almuerzos, int contador, int cantidad){
        io.vavr.collection.List<String >rutas = io.vavr.collection.List.of();
        int i=0;
        while (i < cantidadAlmuerzos && i < almuerzos.size()){
            rutas.append(almuerzos.get(contador+i));
            i++;
        }

        return rutas;
    }

    public static Dron realizarEntrega(io.vavr.collection.List<String> ruta){

        Future<Dron> inicio = Future.of(() -> new Dron(0,0, Direccion.NORTE));

        Future<String> ruta1 = Future.of(()->ruta.get(0));
        Future<String> ruta2 = Future.of(()->ruta.get(1));
        Future<String> ruta3 = Future.of(()->ruta.get(2));

        Dron d = new Dron(0,0,Direccion.NORTE);
        String inicio1 = d.toString();

        String fold = ruta.fold(inicio1, (s1, s2) -> convertir(s1, s2));
        String[] dS = fold.split("\\,");
        Dron dRes = new Dron(Integer.parseInt(dS[0]),Integer.parseInt(dS[1]), cambiar(dS[2]));

/*

        Future<Dron> finalDron = Future.fold()

        Future<Dron> finalDron = inicio.flatMap(r -> Future.of(()->
                ejecutarFuncion(r,ruta.get(0))).flatMap(r2 -> Future.of(()->
                ejecutarFuncion(r2, ruta.get(1))).flatMap(r3 -> Future.of(()->
                ejecutarFuncion(r3, ruta.get(2))))));
*/

        return dRes;

    }

    public static Direccion cambiar(String n){
        Direccion dir = Direccion.NORTE;
        switch (n){
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

    public static String convertir(String posicion, String ruta){
        String[] dS = posicion.split("\\,");
        Dron d = new Dron(Integer.parseInt(dS[0]),Integer.parseInt(dS[1]), cambiar(dS[2]));
        Dron dnue = ejecutarFuncion(d,ruta);
        resultados.add(dnue);
        return dnue.toString();
    }

    public static Dron ejecutarFuncion(Dron dron, String ruta){
        Dron dronR = dron;
        for (int j = 0; j<ruta.length();j++){
            char c = ruta.charAt(j);
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
                    System.out.println("Error, ruta invalid!");
                    break;
            }
        }
        return dronR;
    }
}
