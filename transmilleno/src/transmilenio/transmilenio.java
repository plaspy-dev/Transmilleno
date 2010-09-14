import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import java.util.Vector;
import java.io.*;
/*import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
*/

public class transmilenio extends MIDlet implements CommandListener {

    // display manager
    Display display = null;
    // a menu with items
    List menu_zonas = null; // main menu
    List menu_paradas= null;
    List menu_resultados= null;
    Image img=null;
    private Form screen;
    int    m_nRutas=0;
        
    String      m_szZonaUbicacion;
    String      m_szZonaDestino;
    int         m_nIDDestino;
    int         m_nIDUbicacion;
    String      m_szUbicacion;
    String      m_szDestino;
    int 	m_nZona;
    int 	m_nPosicion;
    Rutas       m_pRutas;
    boolean     m_bResultados=false;
    Vector      m_vRutas;
    Vector      m_vTransbordos;
    
    // command
    static final Command backCommand = new Command("Atras", Command.BACK, 0);
    static final Command exitCommand = new Command("Salir", Command.STOP, 2);
    static final Command rutasCommand = new Command("Rutas", Command.BACK, 1);

    // constructor.
    public transmilenio() {
    	
    	m_szZonaUbicacion="";
        m_szZonaDestino="";
        m_szUbicacion="";
        m_nIDUbicacion=0;
        m_nIDDestino=0;
        m_szDestino="";
    	m_nZona=0;
    	m_nPosicion=0;
        m_pRutas= null;
        m_vRutas=null;
        m_vTransbordos=null;
    }

    public void startApp() throws MIDletStateChangeException {

      display = Display.getDisplay(this);
      // GetProgress();
      GetZonas();
    }
    
    public void GetZonas() {
       
      m_nRutas=0;
      if(m_nPosicion==0) {
         menu_zonas = new List("Zona origen", Choice.IMPLICIT);
      } else {
         menu_zonas = new List("Zona destino", Choice.IMPLICIT);
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
      
      if(m_nPosicion!=0)
        menu_zonas.addCommand(backCommand);
      else menu_zonas.addCommand(rutasCommand);
      menu_zonas.addCommand(exitCommand);
      menu_zonas.setCommandListener(this);
      display.setCurrent(menu_zonas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
      notifyDestroyed();
    }
    
    public String GetDosDigitos(int numero) {
        
        String resultado= Integer.toString(numero);
        if(numero<10) 
            resultado= "0" + resultado;
        return resultado;
    }

    public Gauge GetProgress() {
        
       display = Display.getDisplay(this);
       screen = new Form("Obteniendo Rutas");

       /*Calendar t= Calendar.getInstance();
       t.setTimeZone(TimeZone.getTimeZone("GMT-05:00"));
       String cadena= Integer.toString(t.get(Calendar.YEAR));
       cadena+="/" + GetDosDigitos(t.get(Calendar.MONTH));
       cadena+="/" + GetDosDigitos(t.get(Calendar.DAY_OF_MONTH));
       cadena+=" " + GetDosDigitos(t.get(Calendar.HOUR));
       cadena+=":" + GetDosDigitos(t.get(Calendar.MINUTE));
       cadena+=":" + GetDosDigitos(t.get(Calendar.SECOND));
       if(t.get(Calendar.AM_PM)==1) {
           cadena+=" PM";
       } else {
           cadena+=" AM";
       }*/
       screen.append(new StringItem("Actualizado el 12/08/2010", ""));
       if(img==null) {
           try {
               img= Image.createImage("/logo.png");
           } catch (IOException e) {
               e=null;
           }
       }
       screen.append(new ImageItem("", img, ImageItem.LAYOUT_DEFAULT, "SDG"));
       Gauge gauge= new Gauge("", false, 100, 0);
       screen.append(gauge);
       screen.addCommand(exitCommand);
       screen.setCommandListener(this);
       display.setCurrent(screen);
       return gauge;
    }
    
    public void PutDescripcion(List lista, String descripcion) {

        int pos=0;
        while((pos= descripcion.indexOf("\n"))!=-1){
            lista.append(descripcion.substring(0, pos), null);
            descripcion= descripcion.substring(pos+1);
        }
        lista.append(descripcion, null);
    }

    public void ShowResultados(Gauge gauge) {

        if(gauge==null) 
            menu_resultados = new List("Desde " + m_szUbicacion + " Hasta " + m_szDestino, Choice.IMPLICIT);

        if(m_vRutas.size()>0 || m_vTransbordos.size()>0) {

           int resultados=10-m_vRutas.size();
           for(int i=0; i<m_vRutas.size(); i++) {

                if(gauge!=null)
                    gauge.setValue((i+1)*100/m_vRutas.size());
                Ruta ruta= (Ruta)m_vRutas.elementAt(i);
                if(ruta.GetParadas()==1)
                    menu_resultados.append(ruta.GetNombre() + " "+ ruta.GetParadas() + " Parada.", null);
                else menu_resultados.append(ruta.GetNombre() + " "+ ruta.GetParadas() + " Paradas.", null);
                PutDescripcion(menu_resultados, ruta.GetDescripcion());
                menu_resultados.append("", null);
           }
           for(int i=0; i<m_vTransbordos.size(); i++) {

                if(resultados<0)
                   break;
                resultados--;
                if(gauge!=null)
                    gauge.setValue((i+1)*100/m_vTransbordos.size());
                Ruta ruta= (Ruta)m_vTransbordos.elementAt(i);
                if(ruta.GetParadas()==1)
                    menu_resultados.append(ruta.GetNombre() + " "+ ruta.GetParadas() + " Parada.", null);
                else menu_resultados.append(ruta.GetNombre() + " "+ ruta.GetParadas() + " Paradas.", null);
                PutDescripcion(menu_resultados, ruta.GetDescripcion());
                menu_resultados.append("", null);
           }
       } else {
           menu_resultados.append("No se encontraron rutas",null);
           menu_resultados.append("", null);
       }
       menu_resultados.append("Desarrollado por SDG", null);
       menu_resultados.append("http://www.sdg.com.co", null);
       m_bResultados=true;
       menu_resultados.addCommand(backCommand);
       menu_resultados.addCommand(exitCommand);
       menu_resultados.setCommandListener(this);
       display.setCurrent(menu_resultados);
    }

    public void GetResultados(Gauge gauge) {
        
       if(m_pRutas==null)
            m_pRutas=new Rutas();
       gauge.setLabel("Buscando rutas");
       menu_resultados = new List("Desde " + m_szUbicacion + " Hasta " + m_szDestino, Choice.IMPLICIT);
       m_vRutas= m_pRutas.GetRutaID(m_nIDUbicacion, m_nIDDestino);
       gauge.setLabel("Buscando transbordos");
       m_vTransbordos= m_pRutas.GetTransbordos();
       
       gauge.setLabel("Agregando rutas");
       ShowResultados(gauge);
    }
    
    public void GetResultado() {
        
        // GetProgress();
        new Hilo(this, GetProgress());
    }
    
    public void GetZonaA() {
    	
    	menu_paradas.append("Demo Parada 019", null);
    	menu_paradas.append("Demo Parada 020", null);
    	menu_paradas.append("Demo Parada 038", null);
    	menu_paradas.append("Demo Parada 021", null);
    	menu_paradas.append("Demo Parada 022", null);
    	menu_paradas.append("Demo Parada 023", null);
    	menu_paradas.append("Demo Parada 044", null);
    	menu_paradas.append("Demo Parada 045", null);
    	menu_paradas.append("Demo Parada 046", null);
    	menu_paradas.append("Demo Parada 047", null);
    	menu_paradas.append("Demo Parada 039", null);
    	menu_paradas.append("Demo Parada 048", null);
    	menu_paradas.append("Demo Parada 032", null);
    	menu_paradas.append("Demo Parada 040", null);
    }
  
    public void GetZonaB() {
    	
    	menu_paradas.append("Demo Parada 001", null);
    	menu_paradas.append("Demo Parada 002", null);
    	menu_paradas.append("Demo Parada 037", null);
    	menu_paradas.append("Demo Parada 003", null);
    	menu_paradas.append("Demo Parada 004", null);
    	menu_paradas.append("Demo Parada 029", null);
    	menu_paradas.append("Demo Parada 005", null);
    	menu_paradas.append("Demo Parada 030", null);
    	menu_paradas.append("Demo Parada 031", null);
    	menu_paradas.append("Demo Parada 006", null);
    	menu_paradas.append("Demo Parada 007", null);
    	menu_paradas.append("Demo Parada 008", null);
    	menu_paradas.append("Demo Parada 009", null);
    	menu_paradas.append("Demo Parada 010", null);
    	menu_paradas.append("Demo Parada 018", null);
    }
    
    public void GetZonaC() {
    	
    	menu_paradas.append("Demo Parada 056", null);
    	menu_paradas.append("Demo Parada 055", null);
    	menu_paradas.append("Demo Parada 054", null);
    	menu_paradas.append("Demo Parada 053", null);
    	menu_paradas.append("Demo Parada 093", null);
    	menu_paradas.append("Demo Parada 089", null);
    	menu_paradas.append("Demo Parada 085", null);
    	menu_paradas.append("Demo Parada 086", null);
    	menu_paradas.append("Demo Parada 052", null);
    	menu_paradas.append("Demo Parada 087", null);
    	menu_paradas.append("Demo Parada 094", null);
    	menu_paradas.append("Demo Parada 097", null);
    	menu_paradas.append("Demo Parada 051", null);
    	menu_paradas.append("Demo Parada 090", null);
    }
    
    public void GetZonaD() {
        
    	menu_paradas.append("Demo Parada 017", null);
    	menu_paradas.append("Demo Parada 106", null);
    	menu_paradas.append("Demo Parada 016", null);
    	menu_paradas.append("Demo Parada 100", null);
    	menu_paradas.append("Demo Parada 014", null);
    	menu_paradas.append("Demo Parada 013", null);
    	menu_paradas.append("Demo Parada 104", null);
    	menu_paradas.append("Demo Parada 101", null);
    	menu_paradas.append("Demo Parada 102", null);
    	menu_paradas.append("Demo Parada 103", null);
    	menu_paradas.append("Demo Parada 012", null);
    	menu_paradas.append("Demo Parada 011", null);
    	menu_paradas.append("Demo Parada 066", null);
    	menu_paradas.append("Demo Parada 091", null);
    }
    
    public void GetZonaE() {
    	
    	menu_paradas.append("Demo Parada 079", null);
    	menu_paradas.append("Demo Parada 080", null);
    	menu_paradas.append("Demo Parada 098", null);
    	menu_paradas.append("Demo Parada 081", null);
    	menu_paradas.append("Demo Parada 099", null);
    	menu_paradas.append("Demo Parada 067", null);
    	menu_paradas.append("Demo Parada 068", null);
    	menu_paradas.append("Demo Parada 082", null);
    	menu_paradas.append("Demo Parada 069", null);
    	menu_paradas.append("Demo Parada 070", null);
    }
    
    public void GetZonaF() {
        
    	menu_paradas.append("Demo Parada 105", null);
    	menu_paradas.append("Demo Parada 109", null);
    	menu_paradas.append("Demo Parada 063", null);
    	menu_paradas.append("Demo Parada 062", null);
    	menu_paradas.append("Demo Parada 061", null);
    	menu_paradas.append("Demo Parada 096", null);
    	menu_paradas.append("Demo Parada 110", null);
    	menu_paradas.append("Demo Parada 065", null);
    	menu_paradas.append("Demo Parada 060", null);
    	menu_paradas.append("Demo Parada 095", null);
    	menu_paradas.append("Demo Parada 113", null);
    	menu_paradas.append("Demo Parada 114", null);
    	menu_paradas.append("Demo Parada 111", null);
    	menu_paradas.append("Demo Parada 059", null);
    	menu_paradas.append("Demo Parada 071", null);
    	menu_paradas.append("Demo Parada 112", null);
    	menu_paradas.append("Demo Parada 115", null);
    	menu_paradas.append("Demo Parada 032", null);
    }
    
    public void GetZonaG() {
    	
    	menu_paradas.append("Demo Parada 075", null);
    	menu_paradas.append("Demo Parada 078", null);
    	menu_paradas.append("Demo Parada 077", null);
    	menu_paradas.append("Demo Parada 108", null);
    	menu_paradas.append("Demo Parada 074", null);
    	menu_paradas.append("Demo Parada 073", null);
    	menu_paradas.append("Demo Parada 076", null);
    	menu_paradas.append("Demo Parada 117", null);
    	menu_paradas.append("Demo Parada 116", null);
    	menu_paradas.append("Demo Parada 072", null);
    	menu_paradas.append("Demo Parada 084", null);
    	menu_paradas.append("Demo Parada 083", null);
    	menu_paradas.append("Demo Parada 071", null);
    }
    
    public void GetZonaH() {

        menu_paradas.append("Demo Parada 028", null);
    	menu_paradas.append("Demo Parada 027", null);
    	menu_paradas.append("Demo Parada 026", null);
    	menu_paradas.append("Demo Parada 025", null);
    	menu_paradas.append("Demo Parada 043", null);
    	menu_paradas.append("Demo Parada 107", null);
    	menu_paradas.append("Demo Parada 088", null);
    	menu_paradas.append("Demo Parada 024", null);
    	menu_paradas.append("Demo Parada 036", null);
    	menu_paradas.append("Demo Parada 042", null);
    	menu_paradas.append("Demo Parada 041", null);
    	menu_paradas.append("Demo Parada 035", null);
    	menu_paradas.append("Demo Parada 050", null);
    	menu_paradas.append("Demo Parada 034", null);
    	menu_paradas.append("Demo Parada 033", null);
    	menu_paradas.append("Demo Parada 092", null);
    	
    }
    
    public void GetZonaJ() {
    	
        menu_paradas.append("Demo Parada 057", null);
        menu_paradas.append("Demo Parada 058", null);
    }
    
    public boolean GetRuta(String szRuta) {

        if(m_pRutas==null)
            m_pRutas=new Rutas();
        Ruta ruta= m_pRutas.GetRuta(szRuta);
        if(ruta==null)
            return false;

        display = Display.getDisplay(this);
        menu_resultados = new List("Ruta " + szRuta, Choice.IMPLICIT);
        
        if(ruta!=null) {
            Vector estaciones = ruta.GetEstaciones();
            PutDescripcion(menu_resultados, ruta.GetDescripcion());
            menu_resultados.append("", null);
            for(int j=0; j<estaciones.size(); j++) {
                menu_resultados.append(estaciones.elementAt(j).toString(),null);
            }
        }
        menu_resultados.append("", null);
        menu_resultados.append("Desarrollado por SDG", null);
        menu_resultados.append("http://www.sdg.com.co", null);
       
        m_bResultados=true;
        menu_resultados.addCommand(exitCommand);
        menu_resultados.addCommand(backCommand);
    	menu_resultados.setCommandListener(this);
        display.setCurrent(menu_resultados);
        return true;
    }
    
    public void GetRutas(int zona) {
        display = Display.getDisplay(this);
        char letra=' ';
        switch(zona) {
    	  	case 0:
    	  		letra='A';
    	  		break;
    	  	case 1:
    	  		letra='B';
    	  		break;
    	  	case 2:
    	  		letra='C';
    	  		break;
    	  	case 3:
    	  		letra='D';
    	  		break;
    	  	case 4:
    	  		letra='E';
    	  		break;
    	  	case 5:
    	  		letra='F';
    	  		break;
    	  	case 6:
    	  		letra='G';
    	  		break;
    	  	case 7:
    	  		letra='H';
    	  		break;
    	  	case 8:
    	  		letra='J';
    	  		break;
    	  }
        menu_paradas = new List("Seleccione su ruta", Choice.IMPLICIT);
        
        if(m_pRutas==null)
            m_pRutas=new Rutas();
        
        Vector resultado= new Vector();
        Vector rutas= m_pRutas.GetRutas();
        for(int i=0; i<rutas.size(); i++) {
            Ruta ruta= (Ruta)rutas.elementAt(i);
            if(ruta.GetNombre().charAt(0)==letra) {
                resultado.addElement(ruta);
            }
        }
        // ordenamos las rutas
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetNombre().compareTo(ruta.GetNombre())>0) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        
        // los agregamos
        for(int i=0; i<resultado.size(); i++) {
           Ruta ruta= (Ruta)resultado.elementAt(i);
           menu_paradas.append(ruta.GetNombre(), null); 
        }
        
        menu_paradas.addCommand(exitCommand);
        menu_paradas.addCommand(backCommand);
    	menu_paradas.setCommandListener(this);
        display.setCurrent(menu_paradas);
    }
    
    public void GetZona() {
        
        display = Display.getDisplay(this);
        if(m_nPosicion==1) {
            menu_paradas = new List("Su ubicacion", Choice.IMPLICIT);
            m_nIDUbicacion= m_nZona*100;
        } else {
            menu_paradas = new List("Su destino", Choice.IMPLICIT); 
            m_nIDDestino=m_nZona*100;
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
    
    public void GetRuta(List lista, int index) {

        String texto= lista.getString(index);
        if(texto.length()==0) {
            return;
        }
        if(!GetRuta(texto.substring(0, 3))) {
            if(!GetRuta(texto.substring(0, 2))) {
                if(index>0)
                   GetRuta(lista, index-1);
                return;
            }
        }
        m_nRutas=1;
    }

    public void commandAction(Command c, Displayable d) {
      
      String label = c.getLabel();
      if (label.equals("Salir")) {
         destroyApp(true);
      } else if(label.equals("Rutas")) {
          m_nRutas=1;
          List down = (List)display.getCurrent();
          m_nZona= down.getSelectedIndex();
          GetRutas(m_nZona);
      } else if (label.equals("Atras")) {
          if(m_bResultados && m_nRutas==1) {
              m_nRutas=0;
              ShowResultados(null);
              return;
          }
          m_bResultados=false;
          if(m_nRutas>0) {
              m_nRutas--;
              if(m_nRutas==1)
                GetRutas(m_nZona);
              else if(m_nRutas==0)
                  GetZonas();
          } else {
              m_nPosicion--;
              if(m_nPosicion==0 || m_nPosicion==2) {
                  GetZonas();
              } else {
                  GetZona();
              }
          }
      } else if(m_bResultados) {
          if(m_nRutas==1) { // ya las esta mostrando

          } else {
              List down = (List)display.getCurrent();
              GetRuta(down, down.getSelectedIndex());
          }
      } else if(m_nRutas==1) {
          m_nRutas++;
          List down = (List)display.getCurrent();
          GetRuta(down.getString(down.getSelectedIndex()));
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
          int index= down.getSelectedIndex()+1;
          m_nIDUbicacion+=index;
          if(m_nIDUbicacion==518) {
              m_nIDUbicacion=13;
          } else if(m_nIDUbicacion==515) {
              m_nIDUbicacion=613;
          }
          GetZonas();
      } else if(m_nPosicion==3) { // seleccion destino
          m_nPosicion++;
          List down = (List)display.getCurrent();
    	  m_szDestino= down.getString(down.getSelectedIndex());
          
          int index= down.getSelectedIndex()+1;
          m_nIDDestino+=index;
          if(m_nIDDestino==518) {
              m_nIDDestino=13;
          } else if(m_nIDDestino==515) {
              m_nIDDestino=613;
          }
          GetResultado();
      }
  }
}
