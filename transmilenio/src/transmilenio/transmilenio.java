import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.util.Vector;

public class transmilenio extends MIDlet implements CommandListener {

    // display manager
    Display display = null;
    // a menu with items
    List menu_zonas = null; // main menu
    List menu_paradas= null;
    private Form screen;
        
    String      m_szZonaUbicacion;
    String      m_szZonaDestino;
    String      m_szUbicacion;
    String      m_szDestino;
    int 	m_nZona;
    int 	m_nPosicion;
    Rutas       m_pRutas;
    
    // command
    static final Command backCommand = new Command("Atras", Command.BACK, 0);
    static final Command exitCommand = new Command("Salir", Command.STOP, 2);

    // constructor.
    public transmilenio() {
    	
    	m_szZonaUbicacion="";
        m_szZonaDestino="";
        m_szUbicacion="";
        m_szDestino="";
    	m_nZona=0;
    	m_nPosicion=0;
        m_pRutas= null;
    }

    public void startApp() throws MIDletStateChangeException {

      display = Display.getDisplay(this);
      GetZonas();
    }
    
    public void GetZonas() {
       
      if(m_nPosicion==0) {
         menu_zonas = new List("zona origen", Choice.IMPLICIT);   
      } else {
         menu_zonas = new List("zona destino", Choice.IMPLICIT);
      }
      menu_zonas.append("A - Caracas", null);
      menu_zonas.append("B - AutoNorte", null);
      menu_zonas.append("C - Suba", null);
      menu_zonas.append("D - Calle 80", null);
      menu_zonas.append("E - NQS Central", null);
      menu_zonas.append("F - Americas", null);
      menu_zonas.append("G - NQS Sur", null);
      menu_zonas.append("H - Caracas Sur", null);
      menu_zonas.append("J - Eje Ambiental", null);
      
      menu_zonas.addCommand(exitCommand);
      if(m_nPosicion!=0)
        menu_zonas.addCommand(backCommand);
      menu_zonas.setCommandListener(this);
      display.setCurrent(menu_zonas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
      notifyDestroyed();
    }

    public void GetResultado() {
        
        if(m_pRutas==null)
            m_pRutas=new Rutas();
        
       display = Display.getDisplay(this);
       screen = new Form("Desde " + m_szUbicacion + " Hasta " + m_szDestino);
       Vector rutas= m_pRutas.GetRuta(m_szUbicacion, m_szDestino);
       if(rutas.size()>0) {
           StringItem mensaje;
           for(int i=0; i<rutas.size(); i++) {
                Ruta ruta= (Ruta)rutas.elementAt(i);
                if(ruta.GetParadas()==1)
                    mensaje = new StringItem(ruta.GetNombre(),ruta.GetDescripcion() + "\n" + ruta.GetParadas() + " Parada.");
                else mensaje = new StringItem(ruta.GetNombre(),ruta.GetDescripcion() + "\n" + ruta.GetParadas() + " Paradas.");
                screen.append(mensaje);
           }
       } else {
           screen.append(new StringItem("No se encontraron rutas",""));
       }
       screen.addCommand(backCommand);
       screen.addCommand(exitCommand);
       screen.setCommandListener(this);
       display.setCurrent(screen);
    }
    
    public void GetZonaA() {
    	
    	menu_paradas.append("Calle 76", null);
    	menu_paradas.append("Calle 72", null);
    	menu_paradas.append("Flores", null);
    	menu_paradas.append("Calle 63", null);
    	menu_paradas.append("Calle 57", null);
    	menu_paradas.append("Marly", null);
    	menu_paradas.append("Calle 45", null);
    	menu_paradas.append("Avenida 39", null);
    	menu_paradas.append("Profamilia", null);
    	menu_paradas.append("Calle 26", null);
    	menu_paradas.append("Calle 22", null);
    	menu_paradas.append("Calle 19", null);
    	menu_paradas.append("Avenida Jimenez", null);
    	menu_paradas.append("Tercer Milenio", null);
    }
  
    public void GetZonaB() {
    	
    	menu_paradas.append("Portal Norte", null);
    	menu_paradas.append("Toberin", null);
    	menu_paradas.append("Cardio infantil", null);
    	menu_paradas.append("Mazuren", null);
    	menu_paradas.append("Calle 146", null);
    	menu_paradas.append("Calle 142", null);
    	menu_paradas.append("Alcala", null);
    	menu_paradas.append("Prado", null);
    	menu_paradas.append("Calle 127", null);
    	menu_paradas.append("Pepe sierra", null);
    	menu_paradas.append("Calle 106", null);
    	menu_paradas.append("Calle 100", null);
    	menu_paradas.append("Virrey", null);
    	menu_paradas.append("Calle 85", null);
    	menu_paradas.append("Heroes", null);
    }
    
    public void GetZonaC() {
    	
    	menu_paradas.append("Portal Suba", null);
    	menu_paradas.append("La campina", null);
    	menu_paradas.append("Transversal 91", null);
    	menu_paradas.append("21 Angeles", null);
    	menu_paradas.append("Gratamira", null);
    	menu_paradas.append("Avenida Boyaca", null);
    	menu_paradas.append("Niza Calle 127", null);
    	menu_paradas.append("Humedal Cordoba", null);
    	menu_paradas.append("Shaio", null);
    	menu_paradas.append("Puente largo", null);
    	menu_paradas.append("Suba Calle 100", null);
    	menu_paradas.append("Suba Calle 95", null);
    	menu_paradas.append("Rionegro", null);
    	menu_paradas.append("San Martin", null);
    }
    
    public void GetZonaD() {
        
    	menu_paradas.append("Portal de la 80", null);
    	menu_paradas.append("Quirigua", null);
    	menu_paradas.append("Carrera 90", null);
    	menu_paradas.append("Avenida Cali", null);
    	menu_paradas.append("Granja", null);
    	menu_paradas.append("Carrera 77", null);
    	menu_paradas.append("Minuto de Dios", null);
    	menu_paradas.append("Boyaca", null);
    	menu_paradas.append("Ferias", null);
    	menu_paradas.append("Avenida 68", null);
    	menu_paradas.append("Carrera 53", null);
    	menu_paradas.append("Carrera 47", null);
    	menu_paradas.append("Escuela Militar", null);
    	menu_paradas.append("El Polo", null);
    }
    
    public void GetZonaE() {
    	
    	menu_paradas.append("La Castellana", null);
    	menu_paradas.append("NQS Calle 75", null);
    	menu_paradas.append("Avenida Chile", null);
    	menu_paradas.append("Simon Bolivar", null);
    	menu_paradas.append("Coliseo", null);
    	menu_paradas.append("El Campin", null);
    	menu_paradas.append("U. Nacional", null);
    	menu_paradas.append("Avenida Dorado", null);
    	menu_paradas.append("CAD", null);
    	menu_paradas.append("Paloquemao", null);
    }
    
    public void GetZonaF() {
        
    	menu_paradas.append("Portal Americas", null);
    	menu_paradas.append("Patio Bonito", null);
    	menu_paradas.append("Biblioteca el tintal", null);
    	menu_paradas.append("Transversal 86", null);
    	menu_paradas.append("Banderas", null);
    	menu_paradas.append("Mandalay", null);
    	menu_paradas.append("Mundo Aventura", null);
    	menu_paradas.append("Marsella", null);
    	menu_paradas.append("Pradera", null);
    	menu_paradas.append("Americas Carrera 53A", null);
    	menu_paradas.append("Puente Aranda", null);
    	menu_paradas.append("Carrera 43", null);
    	menu_paradas.append("Zona Industrial", null);
    	menu_paradas.append("CDS Carrera 32", null);
    	menu_paradas.append("Ricaute", null);
    	menu_paradas.append("San Facon Carrera 22", null);
    	menu_paradas.append("De la Sabana", null);
    	menu_paradas.append("Avenida Jimenez", null);
    }
    
    public void GetZonaG() {
    	
    	menu_paradas.append("Portal Sur", null);
    	menu_paradas.append("Perdomo", null);
    	menu_paradas.append("Madelena", null);
    	menu_paradas.append("Sevillana", null);
    	menu_paradas.append("Venecia", null);
    	menu_paradas.append("Alqueria", null);
    	menu_paradas.append("General Santander", null);
    	menu_paradas.append("NQS Calle 38A Sur", null);
    	menu_paradas.append("NQS Calle 30 Sur", null);
    	menu_paradas.append("SENA", null);
    	menu_paradas.append("Santa isabel", null);
    	menu_paradas.append("Comuneros", null);
    	menu_paradas.append("Ricaute", null);
    }
    
    public void GetZonaH() {

        menu_paradas.append("Portal Usme", null);
    	menu_paradas.append("Molinos", null);
    	menu_paradas.append("Consuelo", null);
    	menu_paradas.append("Socorro", null);
    	menu_paradas.append("Portal Tunal", null);
    	menu_paradas.append("Parque", null);
    	menu_paradas.append("Biblioteca", null);
    	menu_paradas.append("Santa lucia", null);
    	menu_paradas.append("Calle 40 Sur", null);
    	menu_paradas.append("Quiroga", null);
    	menu_paradas.append("Olaya", null);
    	menu_paradas.append("Restrepo", null);
    	menu_paradas.append("Fucha", null);
    	menu_paradas.append("Narino", null);
    	menu_paradas.append("Hortua", null);
    	menu_paradas.append("Hospital", null);
    	
    }
    
    public void GetZonaJ() {
    	
        menu_paradas.append("Museo del oro", null);
    }
    
    public void GetZona() {
        
        display = Display.getDisplay(this);
        if(m_nPosicion==1) {
            menu_paradas = new List("su ubicacion", Choice.IMPLICIT);
        } else {
            menu_paradas = new List("su destino", Choice.IMPLICIT); 
        }
        switch(m_nZona) {
    	  	case 0:
    	  		GetZonaA();
    	  		break;
    	  	case 1:
    	  		GetZonaB();
    	  		break;
    	  	case 2:
    	  		GetZonaC();
    	  		break;
    	  	case 3:
    	  		GetZonaD();
    	  		break;
    	  	case 4:
    	  		GetZonaE();
    	  		break;
    	  	case 5:
    	  		GetZonaF();
    	  		break;
    	  	case 6:
    	  		GetZonaG();
    	  		break;
    	  	case 7:
    	  		GetZonaH();
    	  		break;
    	  	case 8:
    	  		GetZonaJ();
    	  		break;
    	  }
    	menu_paradas.addCommand(exitCommand);
        menu_paradas.addCommand(backCommand);
    	menu_paradas.setCommandListener(this);
        display.setCurrent(menu_paradas);
    }
    
    public void commandAction(Command c, Displayable d) {
      
      String label = c.getLabel();
      if (label.equals("Salir")) {
         destroyApp(true);
      } else if (label.equals("Atras")) {
          m_nPosicion--;
          if(m_nPosicion==0 || m_nPosicion==2) {
              GetZonas();
          } else {
              GetZona();
          }
      } else if(m_nPosicion==0) { // seleccion zona ubicacion
    	  m_nPosicion++;
    	  List down = (List)display.getCurrent();
    	  m_nZona= down.getSelectedIndex();
    	  m_szZonaUbicacion= down.getString(m_nZona);
    	  GetZona();
      } else if(m_nPosicion==2) { // seleccion de zona destino
    	  m_nPosicion++;
    	  List down = (List)display.getCurrent();
    	  m_nZona= down.getSelectedIndex();
    	  m_szZonaDestino= down.getString(m_nZona);
    	  GetZona();
      } else if(m_nPosicion==1) { // seleccion ubicacion
    	  m_nPosicion++;
          List down = (List)display.getCurrent();
    	  m_szUbicacion= down.getString(down.getSelectedIndex());
          GetZonas();
      } else if(m_nPosicion==3) { // seleccion destino
          m_nPosicion++;
          List down = (List)display.getCurrent();
    	  m_szDestino= down.getString(down.getSelectedIndex());
          GetResultado();
      }
  }
}
