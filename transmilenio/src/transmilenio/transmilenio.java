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
    	
    	menu_paradas.append("Demo Parada 018", null);
    	menu_paradas.append("Demo Parada 019", null);
    	menu_paradas.append("Demo Parada 037", null);
    	menu_paradas.append("Demo Parada 020", null);
    	menu_paradas.append("Demo Parada 021", null);
    	menu_paradas.append("Demo Parada 022", null);
    	menu_paradas.append("Demo Parada 043", null);
    	menu_paradas.append("Demo Parada 044", null);
    	menu_paradas.append("Demo Parada 045", null);
    	menu_paradas.append("Demo Parada 046", null);
    	menu_paradas.append("Demo Parada 038", null);
    	menu_paradas.append("Demo Parada 047", null);
    	menu_paradas.append("Demo Parada 030", null);
    	menu_paradas.append("Demo Parada 039", null);
    }
  
    public void GetZonaB() {
    	
    	menu_paradas.append("Demo Parada 001", null);
    	menu_paradas.append("Demo Parada 002", null);
    	menu_paradas.append("Demo Parada 035", null);
    	menu_paradas.append("Demo Parada 003", null);
    	menu_paradas.append("Demo Parada 004", null);
    	menu_paradas.append("Demo Parada 036", null);
    	menu_paradas.append("Demo Parada 005", null);
    	menu_paradas.append("Demo Parada 028", null);
    	menu_paradas.append("Demo Parada 029", null);
    	menu_paradas.append("Demo Parada 006", null);
    	menu_paradas.append("Demo Parada 050", null);
    	menu_paradas.append("Demo Parada 007", null);
    	menu_paradas.append("Demo Parada 008", null);
    	menu_paradas.append("Demo Parada 009", null);
    	menu_paradas.append("Demo Parada 017", null);
    }
    
    public void GetZonaC() {
    	
    	menu_paradas.append("Demo Parada 056", null);
    	menu_paradas.append("Demo Parada 055", null);
    	menu_paradas.append("Demo Parada 054", null);
    	menu_paradas.append("Demo Parada 053", null);
    	menu_paradas.append("Demo Parada 095", null);
    	menu_paradas.append("Demo Parada 091", null);
    	menu_paradas.append("Demo Parada 087", null);
    	menu_paradas.append("Demo Parada 088", null);
    	menu_paradas.append("Demo Parada 052", null);
    	menu_paradas.append("Demo Parada 089", null);
    	menu_paradas.append("Demo Parada 096", null);
    	menu_paradas.append("Demo Parada 101", null);
    	menu_paradas.append("Demo Parada 051", null);
    	menu_paradas.append("Demo Parada 092", null);
    }
    
    public void GetZonaD() {
        
    	menu_paradas.append("Demo Parada 016", null);
    	menu_paradas.append("Demo Parada 113", null);
    	menu_paradas.append("Demo Parada 015", null);
    	menu_paradas.append("Demo Parada 114", null);
    	menu_paradas.append("Demo Parada 013", null);
    	menu_paradas.append("Demo Parada 012", null);
    	menu_paradas.append("Demo Parada 111", null);
    	menu_paradas.append("Demo Parada 108", null);
    	menu_paradas.append("Demo Parada 109", null);
    	menu_paradas.append("Demo Parada 110", null);
    	menu_paradas.append("Demo Parada 011", null);
    	menu_paradas.append("Demo Parada 010", null);
    	menu_paradas.append("Demo Parada 066", null);
    	menu_paradas.append("Demo Parada 093", null);
    }
    
    public void GetZonaE() {
    	
    	menu_paradas.append("Demo Parada 078", null);
    	menu_paradas.append("Demo Parada 079", null);
    	menu_paradas.append("Demo Parada 102", null);
    	menu_paradas.append("Demo Parada 080", null);
    	menu_paradas.append("Demo Parada 103", null);
    	menu_paradas.append("Demo Parada 104", null);
    	menu_paradas.append("Demo Parada 068", null);
    	menu_paradas.append("Demo Parada 081", null);
    	menu_paradas.append("Demo Parada 069", null);
    	menu_paradas.append("Demo Parada 070", null);
    }
    
    public void GetZonaF() {
        
    	menu_paradas.append("Demo Parada 064", null);
    	menu_paradas.append("Demo Parada 117", null);
    	menu_paradas.append("Demo Parada 063", null);
    	menu_paradas.append("Demo Parada 062", null);
    	menu_paradas.append("Demo Parada 061", null);
    	menu_paradas.append("Demo Parada 100", null);
    	menu_paradas.append("Demo Parada 118", null);
    	menu_paradas.append("Demo Parada 065", null);
    	menu_paradas.append("Demo Parada 060", null);
    	menu_paradas.append("Demo Parada 099", null);
    	menu_paradas.append("Demo Parada 122", null);
    	menu_paradas.append("Demo Parada 123", null);
    	menu_paradas.append("Demo Parada 119", null);
    	menu_paradas.append("Demo Parada 059", null);
    	menu_paradas.append("Demo Parada 071", null);
    	menu_paradas.append("Demo Parada 120", null);
    	menu_paradas.append("Demo Parada 124", null);
    	menu_paradas.append("Demo Parada 030", null);
    }
    
    public void GetZonaG() {
    	
    	menu_paradas.append("Demo Parada 075", null);
    	menu_paradas.append("Demo Parada 085", null);
    	menu_paradas.append("Demo Parada 084", null);
    	menu_paradas.append("Demo Parada 116", null);
    	menu_paradas.append("Demo Parada 074", null);
    	menu_paradas.append("Demo Parada 073", null);
    	menu_paradas.append("Demo Parada 076", null);
    	menu_paradas.append("Demo Parada 107", null);
    	menu_paradas.append("Demo Parada 106", null);
    	menu_paradas.append("Demo Parada 072", null);
    	menu_paradas.append("Demo Parada 105", null);
    	menu_paradas.append("Demo Parada 082", null);
    	menu_paradas.append("Demo Parada 071", null);
    }
    
    public void GetZonaH() {

        menu_paradas.append("Demo Parada 027", null);
    	menu_paradas.append("Demo Parada 026", null);
    	menu_paradas.append("Demo Parada 025", null);
    	menu_paradas.append("Demo Parada 024", null);
    	menu_paradas.append("Demo Parada 042", null);
    	menu_paradas.append("Demo Parada 115", null);
    	menu_paradas.append("Demo Parada 090", null);
    	menu_paradas.append("Demo Parada 023", null);
    	menu_paradas.append("Demo Parada 034", null);
    	menu_paradas.append("Demo Parada 041", null);
    	menu_paradas.append("Demo Parada 040", null);
    	menu_paradas.append("Demo Parada 033", null);
    	menu_paradas.append("Demo Parada 049", null);
    	menu_paradas.append("Demo Parada 032", null);
    	menu_paradas.append("Demo Parada 031", null);
    	menu_paradas.append("Demo Parada 094", null);
    	
    }
    
    public void GetZonaJ() {
    	
        menu_paradas.append("Demo Parada 057", null);
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
