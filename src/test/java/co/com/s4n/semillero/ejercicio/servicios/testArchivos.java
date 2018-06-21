package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.files.LeerEscribir;
import io.vavr.control.Try;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class testArchivos {

    @Test
    public void run(){
        io.vavr.collection.List<String> rutas = LeerEscribir.read();
        List<Dron> resultados = ServicioDron.ejecutarDron(rutas);
        Try<String> retorno = LeerEscribir.write(resultados);
        assertTrue(retorno.isSuccess());
    }
}
