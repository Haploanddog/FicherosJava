/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package escrituralecturatiposdatos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author davidquesadagarcia
 */
public class PruebaObjetos implements Serializable{
    //nombre, numero, fecha

    private String nombre;
    private float numero;
    private Calendar fecha;
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
    public PruebaObjetos(String nombre, float numero, Calendar fecha) {
        this.nombre = nombre;
        this.numero = numero;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getNumero() {
        return numero;
    }

    public void setNumero(float numero) {
        this.numero = numero;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "PruebaObjeto{ " + "nombre: " + nombre + ", numero: " + numero + ", fecha: " + sdf.format(fecha.getTime());
    }

    

    public static void main(String[] args) {
        Calendar cd = Calendar.getInstance();
        try {
            cd.setTime(sdf.parse("11-12-2012"));
        } catch (ParseException ex) {
            Logger.getLogger(PruebaObjetos.class.getName()).log(Level.SEVERE, null, ex);
        }
        PruebaObjetos po = new PruebaObjetos();
        insertarDatosEnFichero(po);
        
        extraerDatosFichero();
    }

    public PruebaObjetos() {
    }

    public static PruebaObjetos extraerDatosFichero() {
        PruebaObjetos objeto = null;

        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pruebaObjetos.dat"))) {
            boolean esFin = false;
            while (!esFin) {
                Object o = null;
                try {
                    o = ois.readObject();
                } catch (Throwable e) {
                    esFin = true;
                }

                if (o instanceof PruebaObjetos) {
                    objeto = (PruebaObjetos) o;
                    System.out.println(o.toString());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PruebaObjetos.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return objeto;
    }

    public static void insertarDatosEnFichero(PruebaObjetos o) {
        
        insertarDatos(o);

        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pruebaObjetos.dat"))) {
            oos.writeObject(o);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static PruebaObjetos insertarDatos(PruebaObjetos objeto) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Nombre");
        String nombre = sc.nextLine();

        System.out.println("Número con decimales");
        float numero = 0;
        boolean esFin = false;
        while (!esFin) {
            if (sc.hasNextFloat()) {
                numero = sc.nextFloat();
                sc.nextLine();
                esFin = true;
            } else {
                sc.nextLine();
                System.out.println("Formato incorrecto");
            }
        }

        System.out.println("Fecha en formato dd-mm-yyyy");
        String cadena = "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
        Calendar fecha = Calendar.getInstance();
        boolean fin = false;
        while (!fin) {
            
            try {
                //recogemos cadena de teclado
                System.out.println("Dame una fecha");
                cadena = sc.nextLine();
                fecha.setTime(sdf.parse(cadena));
                fin = true;
            } catch (ParseException e) {
                System.out.println("Formato incorrecto");
            }
        }

        objeto.setNombre(nombre);
        objeto.setNumero(numero);
        objeto.setFecha(fecha);

        return objeto;
    }
}
