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
      menu_zonas.append("K - Calle 26", null);
      menu_zonas.append("L - Carrera 10", null);
      
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
       screen.append(new StringItem("Actualizado el 27/08/2012", ""));
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
        while((pos= descripcion.indexOf("*"))!=-1){
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
       menu_resultados.append("Rutas del 27/08/2012", null);
       menu_resultados.append("Desarrollado por SDG", null);
       menu_resultados.append("https://service.example.invalid", null);
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
    	
    	menu_paradas.append("C.C. Santafe", null);
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
    	menu_paradas.append("Granja - Carrera 77", null);
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
        menu_paradas.append("Las Aguas", null);
    }
    
    public void GetZonaK() {
    	
        menu_paradas.append("Portal Eldorado", null);
        menu_paradas.append("Modelia", null);
        menu_paradas.append("Normandia", null);
        menu_paradas.append("Av. Rojas", null);
        menu_paradas.append("El Tiempo", null);
        menu_paradas.append("Salitre - El Greco", null);
        menu_paradas.append("CAN", null);
        menu_paradas.append("Gobernacion", null);
        menu_paradas.append("Quinta paredes", null);
        menu_paradas.append("Corferias", null);
        menu_paradas.append("Ciudad universitaria", null);
        menu_paradas.append("Plaza democratica", null);
        menu_paradas.append("Centro Memoria", null);
    }
    
    public void GetZonaL() {
    	
        menu_paradas.append("Portal del 20 de Julio", null);
        menu_paradas.append("Country Sur", null);
        menu_paradas.append("Av. 1a de Mayo", null);
        menu_paradas.append("Ciudad jardin", null);
        menu_paradas.append("Policarpa", null);
        menu_paradas.append("Hospitales", null);
        menu_paradas.append("Bicentenario", null);
        menu_paradas.append("San Victorino", null);
        menu_paradas.append("Las Nieves", null);
        menu_paradas.append("San Diego", null);
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
        menu_resultados.append("Rutas del 27/08/2012", null);
        menu_resultados.append("Desarrollado por SDG", null);
        menu_resultados.append("https://service.example.invalid", null);
       
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
    	  	case 9:
    	  		letra='K';
    	  		break;
    	  	case 10:
    	  		letra='L';
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
    	  	case 9:
    	  		GetZonaK();
    	  		break;
    	  	case 10:
    	  		GetZonaL();
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
          if(m_nIDUbicacion==518) { // jimenez
              m_nIDUbicacion=13;
          } else if(m_nIDUbicacion==613) { // restrepo
              m_nIDUbicacion=515;
          }
          GetZonas();
      } else if(m_nPosicion==3) { // seleccion destino
          m_nPosicion++;
          List down = (List)display.getCurrent();
    	  m_szDestino= down.getString(down.getSelectedIndex());
          
          int index= down.getSelectedIndex()+1;
          m_nIDDestino+=index;
          if(m_nIDDestino==518) { // jimenez
              m_nIDDestino=13;
          } else if(m_nIDDestino==613) { // restrepo
              m_nIDDestino=515;
          }
          GetResultado();
      }
  }
}
