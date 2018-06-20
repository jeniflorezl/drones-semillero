package co.com.s4n.semillero.ejercicio.files;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import io.vavr.control.Try;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadAndFile {

    public static List<String> read(){
        String fileName = "/home/s4n/Documents/drones-semillero/src/resources/int.txt";
        List<String> listAlmuerzos = Arrays.asList();

        Try<Stream<String>> almuerzos = Try.of(() -> Files.lines(Paths.get(fileName)));

        if (almuerzos.isSuccess()){
            listAlmuerzos = almuerzos.get().collect(Collectors.toList());
        }else{
            System.out.println("Error");
        }
        return listAlmuerzos;

    }

    public static void write(List<Dron> results){
        for (int i = 0; i<results.size();i++){
            String content = "("+results.get(i).getX()+","+results.get(i).getY()+") direcciòn "
                    +results.get(i).getDireccion();
            Try<Path> path = Try.of(()->
                    Files.write(Paths.get("/home/s4n/Documents/drones-semillero/src/resources/out.txt"),
                            content.getBytes()));
        }

    }
}
