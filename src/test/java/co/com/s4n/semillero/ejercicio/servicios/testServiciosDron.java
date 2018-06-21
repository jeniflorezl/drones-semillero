package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import co.com.s4n.semillero.ejercicio.files.LeerEscribir;
import io.vavr.control.Try;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@RunWith(PowerMockRunner.class)
//@PrepareForTestI(fullQualityfiedNames = "co.com.s4n.semillero.ejercicio.servicios")
public class testServiciosDron {

    @Test
    public void testAhead(){
        Dron d = new Dron(1,1,Direccion.NORTE);
        Dron res = ServicioDron.ahead(d);
        assertEquals(new Integer(1), res.getX());
        assertEquals(new Integer(2), res.getY());
        assertEquals(Direccion.NORTE, res.getDireccion());

        res = ServicioDron.right(res);
        assertEquals(new Integer(1), res.getX());
        assertEquals(new Integer(2), res.getY());
        assertEquals(Direccion.ESTE, res.getDireccion());

        res =ServicioDron.ahead(res);
        assertEquals(new Integer(2), res.getX());
        assertEquals(new Integer(2), res.getY());
        assertEquals(Direccion.ESTE, res.getDireccion());
    }

    @Test
    public void testRight(){
        Dron d = new Dron(1,1,Direccion.NORTE);
        Dron res = ServicioDron.right(d);
        assertEquals(new Integer(1), res.getX());
        assertEquals(new Integer(1), res.getY());
        assertEquals(Direccion.ESTE, res.getDireccion());
    }

    @Test
    public void testLeft(){
        Dron d = new Dron(1,1,Direccion.NORTE);
        Dron res = ServicioDron.left(d);
        assertEquals(new Integer(1), res.getX());
        assertEquals(new Integer(1), res.getY());
        assertEquals(Direccion.OESTE, res.getDireccion());
    }

    @Test
    public void testEjecucionDron(){
        List<Dron> resultados = Arrays.asList();
        resultados = EjecucionFunciones.ejecutarDron();

        for (int i = 0; i<resultados.size();i++){
            System.out.println("("+resultados.get(i).getX()+","+resultados.get(i).getY()+")"
                    +resultados.get(i).getDireccion());
        }

    }

    @Test
    public void testLeer(){
        List<String> almuerzos = LeerEscribir.read();
        assertTrue(almuerzos.size()>0);

    }

    @Test
    public void testEscribir(){
        List<Dron> listaAlmuerzos = Arrays.asList(
                new Dron(1,1,Direccion.NORTE),
                new Dron(2,2,Direccion.ESTE),
                new Dron(3,2, Direccion.SUR)
        );
        Try<String> escribir = LeerEscribir.write(listaAlmuerzos);
        assertEquals("archivo generado correctamente.",escribir.get());

    }
}
