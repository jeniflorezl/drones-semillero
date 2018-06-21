package co.com.s4n.semillero.ejercicio.files;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import io.vavr.control.Try;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeerEscribir {
    public static List<String> read(){
        String fileName = "/home/s4n/Documents/drones-semillero/src/resources/int.txt";
        List<String> listAlmuerzos = new ArrayList<>();

        Try<Stream<String>> almuerzos = Try.of(() -> Files.lines(Paths.get(fileName)));

        if (almuerzos.isSuccess()){
            listAlmuerzos = almuerzos.get().collect(Collectors.toList());
            //listAlmuerzos = almuerzos.get().collect(Collectors.toList());
        }else{
            System.out.println("Error");
        }
        return listAlmuerzos;

    }

    public static Try<String> write(List<Dron> results){
        Try<String> escribir = Try.of(()->{
            FileWriter fileWriter = new FileWriter("/home/s4n/Documents/drones-semillero/src/resources/out.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
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
