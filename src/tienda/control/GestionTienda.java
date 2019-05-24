
package tienda.control;

import empleado.control.GestionaEmpleados;
import empleado.dominio.Empleado;
import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import java.util.ArrayList;
import java.util.List;
import pedido_factura.Pedido_Factura;
import producto.control.GestionaProductos;
import producto.dominio.Producto;
import tienda.vista.VistaTienda;
import utilidades.Color;


public class GestionTienda{
    
    private Empleado empleadoAutenticado;
    private List<Producto> cesta;
    private GestionaEmpleados gestionaEmpleados;
    private GestionaProductos gestionaProductos;
    
    
    public GestionTienda() {
        
        empleadoAutenticado = null;
        cesta = new ArrayList<>();
        gestionaEmpleados = new GestionaEmpleados(empleadoAutenticado);
    }

    
    

    public void iniciar()throws userIncorrectException, passwordIncorrectException {
        
        boolean esLoginCorrecto = false;
        while (!esLoginCorrecto) {
            try {
                gestionaEmpleados.login();
                esLoginCorrecto = true;
            } catch (userIncorrectException e) {
                System.err.println(e.getMessage());
                System.err.println("Código de error: " + e.getCodigoError());
            } catch (passwordIncorrectException p) {
                System.err.println(p.getMessage());
                System.err.println("Código de error: " + p.getCodigoError());
            }
            }
        empleadoAutenticado = gestionaEmpleados.getEmpleadoAutenticado();
        
            
        System.out.println("  Has iniciado sesión como " + empleadoAutenticado.getNombre() + " " + empleadoAutenticado.getApellidos() + "\n");

        menuPrincipal(empleadoAutenticado);
        
    } 
    
        
  public void menuPrincipal(Empleado empleadoValido) throws userIncorrectException, passwordIncorrectException{
        switch (VistaTienda.opcionDesdeMenuPrincipal()) {
            case HACER_PEDIDO:
                
                System.out.println(Color.RED+ "Ha seleccinado la opción 1,HACER PEDIDO\n"+ Color.DEFAULT);
                Pedido_Factura pedido = new Pedido_Factura(empleadoValido);
                pedido.hacerPedido();
                break;
            case MODIFICAR_PRODUCTO:
                
                System.out.println(Color.RED+"Ha seleccinado la opción 2, MODIFICAR PRODUCTO\n"+ Color.DEFAULT);
                VistaTienda v = new VistaTienda(empleadoValido);
                v.modificarProducto(empleadoValido);
                break;
            case CAMBIAR_PASSWORD:
                
                System.out.println(Color.RED+"Ha seleccinado la opción 3, CAMBIAR PASSWORD\n"+ Color.DEFAULT);
                GestionaEmpleados gestiona = new GestionaEmpleados(empleadoValido);
                gestiona.modificarContraseña(empleadoValido);
                break;
            case CERRAR_SESION:
                
                System.out.println(Color.RED+"Ha seleccinado la opción 4, CERRAR SESION\n"+ Color.DEFAULT);
                GestionaEmpleados gest = new GestionaEmpleados(empleadoValido);
                gest.cerrarSesion(empleadoValido);
                break;
        }

    }
  public void menuModificarProducto(Empleado empleadoValido) throws userIncorrectException, passwordIncorrectException{
        switch (VistaTienda.modificarProducto(empleadoValido)) {
            case MODIFICAR_NOMBRE_PRODUCTO:
                
                System.out.println(Color.RED+"Ha seleccinado la opción 1, MODIFICAR NOMBRE PRODUCTO\n"+ Color.DEFAULT);
                GestionaProductos gestion = new GestionaProductos(empleadoValido);
                gestion.cambiarNombre();
                break;
            case MODIFICAR_PRECIO_PRODUCTO:
                
                System.out.println(Color.RED+"Ha seleccinado la opción 2, MODIFICAR PRECIO PRODUCTO\n"+ Color.DEFAULT);
                GestionaProductos gest = new GestionaProductos(empleadoValido);
                gest.cambiarPrecio();
                break;
            case MODIFICAR_CODIGO_PRODUCTO:
                
                System.out.println(Color.RED+"Ha seleccinado la opción 3, MODIFICAR CODIGO PRODUCTO\n"+ Color.DEFAULT);
                GestionaProductos gestiona = new GestionaProductos(empleadoValido);
                gestiona.cambiarCodigo();
                break;
                
            case TERMINAR_PROCESO:
                System.out.println(Color.RED+"Ha seleccinado la opción 4, TERMINAR PROCESO\n"+ Color.DEFAULT);
                GestionTienda end = new GestionTienda();
                end.menuPrincipal(empleadoValido);
            
        }

    }
  
  
}






