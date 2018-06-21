package co.com.s4n.semillero.ejercicio.files;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Movimientos;
import io.vavr.control.Try;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeerEscribir {
    public static io.vavr.collection.List<String> read(){
        String fileName = "/home/s4n/Documents/drones-semillero/src/resources/int";
        io.vavr.collection.List<String> listAlmuerzos = io.vavr.collection.List.of();

        Try<Stream<String>> almuerzos = Try.of(() -> Files.lines(Paths.get(fileName)));

        if (almuerzos.isSuccess()){
            listAlmuerzos = io.vavr.collection.List.ofAll(almuerzos.get().collect(Collectors.toList()));

        }else{
            System.out.println("Error");
        }
        return listAlmuerzos;
    }

    public static List<Movimientos> convert(String ruta){
        char c;
        List<Movimientos> movimientos = Arrays.asList();
        Movimientos mov = Movimientos.A;
        for(int i=0; i < ruta.length(); i++){
            c = ruta.charAt(i);
            switch (c){
                case 'A':
                    mov = Movimientos.A;
                    break;
                case 'D':
                    mov = Movimientos.D;
                    break;
                case 'I':
                    mov = Movimientos.I;
                    break;
            }
            movimientos.add(mov);
        }
        return movimientos;
    }



    public static Try<String> write(List<Dron> results){
        Try<String> escribir = Try.of(()->{
            FileWriter fileWriter = new FileWriter("/home/s4n/Documents/drones-semillero/src/resources/out");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println("== REPORTE DE ENTREGAS ==");
            for (int i = 0; i<results.size();i++) {
                printWriter.println("("+results.get(i).getX()+","+results.get(i).getY()+") direcciòn "
                        +results.get(i).getDireccion());
            }
            printWriter.close();
            return "ok";
        });
        if (escribir.isSuccess()){
            return Try.of(()->"archivo generado correctamente.");
        }else{
            return Try.of(()->"Error al escribir archivo");
        }
    }
}
