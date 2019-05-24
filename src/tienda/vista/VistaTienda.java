
package tienda.vista;

import empleado.dominio.Empleado;
import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import java.util.Scanner;
import producto.control.GestionaProductos;
import tienda.control.GestionTienda;
import utilidades.Color;
import utilidades.MenuHacerPedido;
import utilidades.MenuModificarProducto;
import utilidades.MenuPrincipal;


public class VistaTienda {
    
    Empleado empAutentificado;

    public VistaTienda(Empleado empleadoLogeado) {
        this.empAutentificado = empleadoLogeado;
    }
    
    
    
    public static MenuPrincipal opcionDesdeMenuPrincipal() {
        borrarPantalla();
        System.out.println(Color.RED+"-------Menú principal -------------------");
        System.out.println("   1. Hacer pedido");
        System.out.println("   2. Modificar producto");
        System.out.println("   3. Cambiar contraseña de empleado");
        System.out.println("   4. Cerrar sesión");
        System.out.println(Color.RED+"------------------------------------");
        
        
        int opcion = pedirOpcionEnRango(1, 4);

        MenuPrincipal menu = null;

        switch (opcion) {
            case 1:
                menu = MenuPrincipal.HACER_PEDIDO;
                break;
            case 2:
                menu = MenuPrincipal.MODIFICAR_PRODUCTO;
                break;
            case 3:
                menu = MenuPrincipal.CAMBIAR_PASSWORD;
                break;
            case 4:
                menu = MenuPrincipal.CERRAR_SESION;
                break;
        }

        return menu;
        
    }
    
    public static MenuModificarProducto modificarProducto(Empleado empleado) throws userIncorrectException, passwordIncorrectException{
        
        borrarPantalla();
        System.out.println(Color.BLUE+"------Menú Modificación Producto -------------------"+ Color.DEFAULT);
        System.out.println("   2.1 Modificar Nombre del Producto");
        System.out.println("   2.2 Modificar Precio del Producto");
        System.out.println("   2.3 Modificar Codigo del Producto");
        System.out.println("   2.4 Terminar Proceso");
        System.out.println(Color.BLUE+"------------------------------------"+ Color.DEFAULT);
        
        int opcion = pedirOpcionEnRango(1, 4);
        
        MenuModificarProducto menu = null;
        
        switch (opcion) {
            case 1:
                menu = MenuModificarProducto.MODIFICAR_NOMBRE_PRODUCTO;
                System.out.println(Color.RED+"Ha seleccinado la opción 1, MODIFICAR NOMBRE PRODUCTO"+Color.DEFAULT);
                GestionaProductos gestion = new GestionaProductos(empleado);
                gestion.cambiarNombre();
                break;
            case 2:
                menu = MenuModificarProducto.MODIFICAR_PRECIO_PRODUCTO;
                System.out.println(Color.RED+"Ha seleccinado la opción 2, MODIFICAR PRECIO PRODUCTO"+Color.DEFAULT);
                GestionaProductos gest = new GestionaProductos(empleado);
                gest.cambiarPrecio();
                break;
            case 3:
                menu = MenuModificarProducto.MODIFICAR_CODIGO_PRODUCTO;
                System.out.println(Color.RED+"Ha seleccinado la opción 3, MODIFICAR CODIGO PRODUCTO"+Color.DEFAULT);
                GestionaProductos gestiona = new GestionaProductos(empleado);
                gestiona.cambiarCodigo();
                break;
                case 4:
                menu = MenuModificarProducto.TERMINAR_PROCESO;
                System.out.println(Color.RED+"Ha seleccinado la opción 4, TERMINAR PROCESO"+Color.DEFAULT);
                GestionTienda end = new GestionTienda();
                end.menuPrincipal(empleado);
                
                break;
            
        }

        return menu; 
        
    }
    
    public static MenuHacerPedido hacerPedido(){
        
        borrarPantalla();
        System.out.println(Color.RED+"-------Menú Realizar Pedido -------------------"+ Color.DEFAULT);
        System.out.println("   1.1 Añadir Producto a la cesta");
        System.out.println("   1.2 Visualizar Precio Total de la cesta");
        System.out.println("   1.3 Imprimir Factura");
        System.out.println("   1.4 Terminar Pedido");
        System.out.println(Color.RED+"------------------------------------"+ Color.DEFAULT);
        
        int opcion = pedirOpcionEnRango(1, 4);
        
        MenuHacerPedido menu = null;
        
        switch (opcion) {
            case 1:
                menu = MenuHacerPedido.AÑADIR_PRODUCTO_CESTA;
                System.out.println(Color.RED+"Ha seleccinado la opción 1, AÑADIR PRODUCTO A LA CESTA\n"+ Color.DEFAULT);
                break;
            case 2:
                menu = MenuHacerPedido.VISUALIZAR_PRECIO_CESTA;
                System.out.println(Color.RED+"Ha seleccinado la opción 2, VISUALIZAR PRECIO CESTA\n"+ Color.DEFAULT);
                break;
            case 3:
                menu = MenuHacerPedido.IMPRIMIR_FACTURA;
                System.out.println(Color.RED+"Ha seleccinado la opción 3, IMPRIMIR FACTURA\n"+ Color.DEFAULT);
                break;
            case 4:
                menu = MenuHacerPedido.TERMINAR_PEDIDO;
                System.out.println(Color.RED+"Ha seleccinado la opción 4, TERMINAR PEDIDO\n"+ Color.DEFAULT);
                break;    
        }

        return menu; 
        
    }

    private static int pedirOpcionEnRango(int min, int max) {

        Scanner leerTeclado = new Scanner(System.in);
        int opcion = 0;
        boolean hayError = true;

        while (hayError) {
            System.out.print("Seleccione una opción: ");
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
    
    public static void mensajeBienvenida(Empleado empleado){
        muestraMensaje("Bienvenido " + empleado.getNombre());
        System.out.println();
    }

    public static void muestraMensaje(String mensaje, Color color) {
        System.out.println(color + mensaje + Color.DEFAULT);
    }

    public static void muestraMensaje(String mensaje) {
        muestraMensaje(mensaje, Color.DEFAULT);
    }

    private static void borrarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
}
