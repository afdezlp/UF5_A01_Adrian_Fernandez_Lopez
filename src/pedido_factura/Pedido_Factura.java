
package pedido_factura;

import empleado.dominio.Empleado;
import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import producto.dominio.Producto;
import tienda.control.GestionTienda;
import tienda.vista.VistaTienda;
import static tienda.vista.VistaTienda.muestraMensaje;
import utilidades.Color;


public class Pedido_Factura {
    
    Scanner leerTeclado = new Scanner(System.in);
    
    Empleado empleado;
    String archivoPorductos = "productos.txt";
    ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<Producto> listaProductos = new ArrayList<>();

    public Pedido_Factura(Empleado loginEmpleado) {
        this.empleado = loginEmpleado;
    }

    public Pedido_Factura() {
        
    }
    
    public Producto leerProductos(){
        
        String archivoProductos = "productos.txt";
        
        Producto productos = new Producto();
        NumberFormat formatoNumero = NumberFormat.getInstance(Locale.FRANCE);
        
        Number numero;
        String lineaConDatos;
        
        try (BufferedReader archivo = Files.newBufferedReader(Paths.get(archivoProductos))){
            
            while(archivo.readLine() != null){
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();   
                numero = formatoNumero.parse(lineaConDatos);
                int codigo = numero.intValue();
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String nombre = lineaConDatos;
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                String descripcion = lineaConDatos;
                
                archivo.readLine();
                lineaConDatos = archivo.readLine().trim();
                numero = formatoNumero.parse(lineaConDatos);
                double precio = numero.doubleValue();
                
                productos = new Producto(codigo, nombre,descripcion,precio);
                        
                listaProductos.add(productos);
                  
            }
        }catch (ParseException e){
            System.err.println("Error de formato en " + archivoProductos);
            System.exit(1);
            
        }catch(IOException e){
            System.err.println("Error de lectura en " + archivoProductos);
            System.exit(1);
        }
        
        return productos;     
    }
    
    public void hacerPedido() throws userIncorrectException, passwordIncorrectException {

        switch (VistaTienda.hacerPedido()) {

            case AÑADIR_PRODUCTO_CESTA:
                
                leerProductos();
                añadirProducto();
                break;

            case VISUALIZAR_PRECIO_CESTA:
                
                visualizarCesta(productos);
                break;

            case IMPRIMIR_FACTURA:
                
                imprimirFactura(productos, empleado);
                break;

            case TERMINAR_PEDIDO:
                
                finalizarSistema(empleado);
                break;

        }

    }
    
    public void añadirProducto() throws userIncorrectException, passwordIncorrectException {

        if (productos.size() > 0) {
            System.err.println("No podemos agregar más poductos");
            hacerPedido();
        }
        
        int opcion = pedirOpcionCesta(1, 3);
        int iniciar = 0;
        
        while (iniciar < opcion) {

            boolean ejecutar = true;
            while (ejecutar) {
                
                int codigoAcceso = 0;
                System.out.println(Color.BLUE + "\n************************************************" + Color.DEFAULT);
                for (Producto prod : listaProductos) {
                    System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", 
                            prod.getCodigo(),prod.getNombre(), prod.getDescripcion(), prod.getPrecio());
                }
                
                System.out.println(Color.BLUE + "************************************************\n" + Color.DEFAULT);
                System.out.print("ENTRE EL CODIGO DEL PRODUCTO A COMPRAR:  ");
                String nuevoCod = leerTeclado.next();

                try {
                    codigoAcceso = Integer.parseInt(nuevoCod);
                } catch (NumberFormatException e) {
                }

                int z = 0;
                if (validaCodigo(codigoAcceso) && productoIncCesta(codigoAcceso)) {

                    for (int i = 0; i < listaProductos.size(); i++) {

                        if (codigoAcceso == listaProductos.get(i).getCodigo()) {
                            z = z + i;
                            productos.add(listaProductos.get(z));

                            System.out.println(Color.GREEN + "Guardado  correctamente " + Color.GREEN);

                            for (int j = 0; j < productos.size(); j++) {
                                System.out.println(productos.get(j).getNombre());
                                ejecutar = false;
                            }
                            System.out.println("\n");
                        }
                    }
                } else if (!validaCodigo(codigoAcceso)) {
                    System.err.println("No existe ese codigo de producto");
                }
            }
            iniciar++;
        }
        hacerPedido();   
}
    
    private boolean validaCodigo(int code) {
        boolean ejecutar = false;
        try {
            for (int i = 0; i < listaProductos.size(); i++) {
                if (listaProductos.get(i).getCodigo() == code) {
                    ejecutar = true;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Debe introducir un valor entero");
        } catch (Exception d) {
            System.out.println("");
        }
        return ejecutar;
    }
    
    private boolean productoIncCesta(int code) {
        boolean ejecutar = true;

        for (int i = 0; i < productos.size(); i++) {
            if (code == productos.get(i).getCodigo()) {
                System.err.println("El Producto seleccionado ya se encuentra en la cesta");
                ejecutar = false;
            }
        }
        return ejecutar;
    }
    
    private void visualizarCesta(List listaProd) throws userIncorrectException, passwordIncorrectException {

        if (listaProd.size() > 0) {

            double x = 0;
            for (int i = 0; i < listaProd.size(); i++) {

                x = x + this.productos.get(i).getPrecio();

            }
            System.out.println(Color.BLUE + "\n\n------------------------------------------------" + Color.GREEN);
            System.out.println("Precio de la cesta : " + x + " € ");
            System.out.println(Color.BLUE + "------------------------------------------------\n\n" + Color.GREEN);
            
        } else {
            System.out.println(Color.RED + "LA CESTA ESTA VACIA \n\n");
        }
        hacerPedido();
    }
    
     private void imprimirFactura(List listaProd, Empleado empleado) throws userIncorrectException, passwordIncorrectException {

        if (listaProd.size() > 0) {
            double aux = 0;
            System.out.println("\n\n FACTURA ");

            System.out.println(Color.BLUE + "----------------------------------------" + Color.DEFAULT);
            for (int i = 0; i < listaProd.size(); i++) {
                System.out.printf("Codigo:\t\t%d%nNombre:\t\t%s%nDescripción:\t%s%nPrecio\t\t%.2f%n%n", this.listaProductos.get(i).getCodigo(),
                        this.productos.get(i).getNombre(), this.productos.get(i).getDescripcion(), this.listaProductos.get(i).getPrecio());
            }
            System.out.println(Color.BLUE + "----------------------------------------" + Color.DEFAULT);
            for (int i = 0; i < listaProd.size(); i++) {

                aux = aux + this.productos.get(i).getPrecio();
            }

            System.out.println("El Precio Total es: " + aux + "€");
            System.out.println("Atendido por: " + empleado.getNombre() + " " + empleado.getApellidos() + "\n\n");
        } else {
            System.out.println(Color.RED + "NO SE PUEDE IMPRIMIR POR QUE NO HAY PRODUCTOS\n\n" + Color.DEFAULT);
        }
        hacerPedido();
    }

    private void finalizarSistema(Empleado empleadoAuten) throws userIncorrectException, passwordIncorrectException {
        GestionTienda gestionTienda = new GestionTienda();
        gestionTienda.menuPrincipal(empleadoAuten);
        
    }

    public void vaciarArray() {
        listaProductos.clear();

    }
    
     private static int pedirOpcionCesta(int min, int max) {

        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;

        while (hayError) {
            System.out.println("CUANTOS PRODUCTOS,DESEA AÑADIR A LA CESTA? [1-3]");
            System.out.print("Introduzca un valor: ");
            if (leerTeclado.hasNextInt()) {
                opcion = leerTeclado.nextInt();
                hayError = opcion < min || opcion > max;
                if (hayError) {
                    muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.RED);
                }
            } else {
                muestraMensaje("Error, opción no válida. Debe ser entre [" + min + "," + max + "]", Color.RED);
                leerTeclado.next();
            }
        }
        return opcion;
    } 
}