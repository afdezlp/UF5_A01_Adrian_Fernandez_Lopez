
package app.principal;

import excepciones.passwordIncorrectException;
import excepciones.userIncorrectException;
import tienda.control.GestionTienda;


public class Main {

    
    public static void main(String[] args) throws userIncorrectException, passwordIncorrectException {
        
        GestionTienda gestionTienda = new GestionTienda();
        gestionTienda.iniciar();
        
        
        
        
    }
    
    
      
   
    
    
    
}
