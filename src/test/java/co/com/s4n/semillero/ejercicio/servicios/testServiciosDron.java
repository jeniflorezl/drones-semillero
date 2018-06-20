package co.com.s4n.semillero.ejercicio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.files.ReadAndFile;
import co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron;
import co.com.s4n.semillero.ejercicio.dominio.vo.Direccion;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
    public void test3(){
        List<Dron> resultados = Arrays.asList();
        resultados = EjecucionFunciones.testDron();

        //assertEquals(new Integer(0), resultados.get(0).getX());
        //assertEquals(new Integer(3), resultados.get(0).getY());
        //assertEquals(Direccion.NORTE, resultados.get(0).getDireccion());

        for (int i = 0; i<resultados.size();i++){
            System.out.println("("+resultados.get(i).getX()+","+resultados.get(i).getY()+")"
                    +resultados.get(i).getDireccion());
        }
        /*
        List<String> almuerzos = ReadAndFile.read();
        for (int i = 0; i< almuerzos.size(); i++){
            System.out.println(almuerzos.get(i));
        }*/
    }

    @Test
    public void test4(){

        List<String> almuerzos = ReadAndFile.read();
        for (int i = 0; i< almuerzos.size(); i++){
            System.out.println(almuerzos.get(i));
        }
    }

    @Test
    public void test5(){
        List<Dron> almuerzos = Arrays.asList(
                new Dron(1,1,Direccion.NORTE),
                new Dron(2,3,Direccion.ESTE)
                );
        ReadAndFile.write(almuerzos);

    }
}
