
package empleado.control;

import empleado.dao.EmpleadoDAOImp;
import empleado.dominio.Empleado;
import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import java.util.Scanner;
import pedido_factura.Pedido_Factura;
import tienda.control.GestionTienda;
import utilidades.Color;
import utilidades.codigoError;


public class GestionaEmpleados {
    
    
    Scanner leerTeclado = new Scanner(System.in);
    
    ControladorEmpleado controlador;
    Empleado empleadoAutenticado;
    
    
    public GestionaEmpleados(Empleado empleado) {
        this.controlador = new ControladorEmpleado();
        this.empleadoAutenticado = empleado;
    }
    
    
    public void login()throws userIncorrectException, passwordIncorrectException{
        
        boolean esEmpleadoValido = false;
        boolean esPasswordValido = false;

        System.out.println( Color.BLUE +"BIENVENIDO A FORTRAN S.L"+ Color.DEFAULT);
        System.out.println("*************************************************");

        System.out.print("INTRODUCE EL CÓDIGO DE TU USUARIO: ");
        
        while (!leerTeclado.hasNextInt()) {
            System.out.println(Color.RED + "DEBE ESCRIBIR UN VALOR NUMÉRICO"+ Color.DEFAULT );
            System.out.print("INTRODUCE EL CÓDIGO DE TU USUARIO: ");
            leerTeclado.next();
        }
        int codigoEntrada = leerTeclado.nextInt();

        System.out.print("INTRODUCE TU CONTRASEÑA: ");
        String passwordEntrada = leerTeclado.next();

        System.out.println("***************************************");
        System.out.println("");

        empleadoAutenticado = controlador.getEmpleadoPorCodigo(codigoEntrada);
        if (empleadoAutenticado != null) {
            esEmpleadoValido = true;
            if (passwordEntrada.equals(empleadoAutenticado.getPassword())) {
                esPasswordValido = true;
            }
        }

        if (!esEmpleadoValido) {
            throw new userIncorrectException("Usuario incorrecto", codigoError.LOGIN_INCORRECTO);
            
        } else if (!esPasswordValido) {
            throw new passwordIncorrectException("Contraseña incorrecta", codigoError.CONTRASEÑA_INCORRECTA);
        }  
    }
  
     public Empleado getEmpleadoAutenticado() {
        return empleadoAutenticado;
    }
     
     public void modificarContraseña(Empleado empleado) throws userIncorrectException, passwordIncorrectException{
         
         boolean valida = true;
         while (valida){
             System.out.println("Introduzca la contraseña actual de : " + 
                     empleado.getNombre() + empleado.getApellidos());
             
             String passwordActual = leerTeclado.next();
             
             if(passwordActual.equals(empleado.getPassword())) {
                 valida = true;
                 System.out.println("Introduzca la nueva contraseña: ");
                 String passwordNueva = leerTeclado.next();
                 var emp = new EmpleadoDAOImp();
                 emp.modificarContraseña(empleado, passwordNueva);
                 System.out.println(Color.GREEN + "Contraseña cambiada correctamente \n" + Color.DEFAULT);
             
                 GestionTienda gestionTienda = new GestionTienda();
                 empleado.setPassword(passwordNueva);
                 gestionTienda.menuPrincipal(empleado);
             }
             
         }
         System.err.println("La contraseña introducida es incorrecta");
         
     }
     public void cerrarSesion(Empleado empleado) throws userIncorrectException, passwordIncorrectException {

        boolean reset = true;
        while (reset) {
            System.out.print("Introduzca la contraseña de: " + empleado.getNombre() + " : ");

            String contraseña = leerTeclado.next();
            if (contraseñaValida(empleado, contraseña)) {

                System.out.println("La sesion de " + empleado.getNombre() + " ha finalizado");
                System.out.println(Color.RED+"GRACIAS POR SU VISITA, HASTA OTRA!!"+"\n"+ Color.DEFAULT);
                Pedido_Factura pedido = new Pedido_Factura();
                pedido.vaciarArray();
                reset = false;

            } else {
                System.err.println("Contraseña no valida");
                
            }
        }
        GestionTienda gestionTienda = new GestionTienda();
        gestionTienda.iniciar();

    }
     private boolean contraseñaValida(Empleado empleado, String password) {

        boolean ejecutar = false;
        if (password.equals(empleado.getPassword())) {

            ejecutar = true;

        }

        return ejecutar;
    }
     
}

 
    
 
   
    
   

   
    
   
