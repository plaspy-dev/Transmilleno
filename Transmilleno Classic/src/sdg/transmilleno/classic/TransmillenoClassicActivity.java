package sdg.transmilleno.classic;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.google.ads.*;

public class TransmillenoClassicActivity extends ListActivity {
    
	private AdView adView;
	String MY_AD_UNIT_ID="a00000000000002";
	
	protected Timer timer;
	
	// Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	adView.loadAd(new AdRequest());
        }
    };
	
	final String[] columns = { "Ruta", "Horario" };
	final String[] matrix  = { "_id", "Ruta", "Horario" };
	final int[] layouts = new int[] { android.R.id.text1, android.R.id.text2 };
	
	int    m_nRutas=0;
    
    String      m_szZonaUbicacion;
    String      m_szZonaDestino;
    int         m_nIDDestino;
    int         m_nIDUbicacion;
    String      m_szUbicacion;
    String      m_szDestino;
    int 		m_nZona;
    int 		m_nPosicion;
    Rutas       m_pRutas;
    boolean     m_bResultados;
    Vector      m_vRutas;
    Vector      m_vTransbordos;

	public TransmillenoClassicActivity() {
		m_pRutas= null;
        m_vRutas=null;
        m_vTransbordos=null;
	}
	
	public void Inicio() {
		
		m_szZonaUbicacion="";
        m_szZonaDestino="";
        m_szUbicacion="";
        m_nIDUbicacion=0;
        m_nIDDestino=0;
        m_szDestino="";
        m_bResultados=false;
    	m_nZona=0;
    	m_nPosicion=0;
        GetZonas();
	}
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Crear la adView  
        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        
        getListView().addHeaderView(adView);
        adView.loadAd(new AdRequest());
        
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                
            	if(m_nPosicion==0 && m_nRutas==0) {
            	
            		m_nRutas=1;
                    m_nZona= position-1;
                    GetRutas(m_nZona);
                    return true;
            	}
            	return false;
            }
        });
        Inicio();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	timer.cancel();
    	timer.purge();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	TimerTask publicidad= new TimerTask() {
			  public void run() {
				Message msg = handler.obtainMessage();
		        handler.sendMessage(msg);  
			  }
	      };
	      timer= new Timer();
	      timer.schedule(publicidad, 20000, 20000);
    }
    
    public void GetZonas() {
        
        m_nRutas=0;
        if(m_nPosicion==0) {
        	setTitle("Zona origen");
        } else {
        	setTitle("Zona destino");
        }
        List<String> menu_zonas = new ArrayList<String>();
        
        menu_zonas.add("A - Caracas");
        menu_zonas.add("B - AutoNorte");
        menu_zonas.add("C - Suba");
        menu_zonas.add("D - Calle 80");
        menu_zonas.add("E - NQS Central");
        menu_zonas.add("F - Americas");
        menu_zonas.add("G - NQS Sur");
        menu_zonas.add("H - Caracas Sur");
        menu_zonas.add("J - Eje Ambiental");
        menu_zonas.add("K - Calle 26");
        menu_zonas.add("L - Carrera 10");
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_zonas);
        setListAdapter(adapter);
      }
    
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

    	menu.getItem(0).setTitle((m_nPosicion==0 && m_nRutas==0 && !m_bResultados) ? "Rutas" : "Inicio");
        return true;
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		    	
    	menu.add("Rutas");
		menu.add("Salir");
	    return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle()=="Rutas") {
			
			Toast.makeText(getApplicationContext(), "Para ver la rutas manterner presionada la zona.", Toast.LENGTH_LONG).show();
			return true;
		} else if(item.getTitle()=="Salir") {
			finish();
			return true;
		} else if(item.getTitle()=="Inicio") {
			
			Inicio();
		}
	    return super.onOptionsItemSelected(item);
	}
    
    public String GetDosDigitos(int numero) {
        
        String resultado= Integer.toString(numero);
        if(numero<10) 
            resultado= "0" + resultado;
        return resultado;
    }
    
    public void ShowResultados() {

    	setTitle("Desde " + m_szUbicacion + " Hasta " + m_szDestino);
    	// List<String> menu_resultados = new ArrayList<String>();
    	
    	int key = 0;
    	MatrixCursor  cursor = new MatrixCursor(matrix);
    	
        if(m_vRutas.size()>0 || m_vTransbordos.size()>0) {

           int resultados=10-m_vRutas.size();
           for(int i=0; i<m_vRutas.size(); i++) {

                Ruta ruta= (Ruta)m_vRutas.elementAt(i);
                if(ruta.GetParadas()==1)
                	cursor.addRow(new Object[] { key++, ruta.GetNombre() + " ("+ ruta.GetParadas() + " Parada)", ruta.GetDescripcion()});  
                else cursor.addRow(new Object[] { key++, ruta.GetNombre() + " ("+ ruta.GetParadas() + " Paradas)", ruta.GetDescripcion()});
                // PutDescripcion(menu_resultados, );
                // menu_resultados.add("");
           }
           for(int i=0; i<m_vTransbordos.size(); i++) {

                if(resultados<0)
                   break;
                resultados--;
                Ruta ruta= (Ruta)m_vTransbordos.elementAt(i);
                cursor.addRow(new Object[] { key++, ruta.GetNombre() + " ("+ ruta.GetParadas() + " Paradas)", ruta.GetDescripcion()});
                // PutDescripcion(menu_resultados, ruta.GetDescripcion());
                // menu_resultados.append("");
           }
       } else {
    	   cursor.addRow(new Object[] { key++, "No se encontraron rutas", ""});
    	   cursor.addRow(new Object[] { key++, "", ""});
       }
       cursor.addRow(new Object[] { key++, "Rutas del " + Rutas.GetFechaRutas(), ""});
       cursor.addRow(new Object[] { key++, "Desarrollado por SDG", "https://transmilleno.example.invalid"});
       m_bResultados=true;
       
       SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, columns, layouts);
       
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_resultados);
       setListAdapter(adapter);
    }
   
    public void GetResultados() {
        
        if(m_pRutas==null)
             m_pRutas=new Rutas();
        setTitle("Desde " + m_szUbicacion + " Hasta " + m_szDestino);
        m_vRutas= m_pRutas.GetRutaID(m_nIDUbicacion, m_nIDDestino);
        m_vTransbordos= m_pRutas.GetTransbordos();
        
        ShowResultados();
     }
     
     public void GetResultado() {
         
         GetResultados();
     }
     
     public List<String> GetZonaA() {
    	 
    	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("Calle 76");
     	menu_paradas.add("Calle 72");
     	menu_paradas.add("Flores");
     	menu_paradas.add("Calle 63");
     	menu_paradas.add("Calle 57");
     	menu_paradas.add("Marly");
     	menu_paradas.add("Calle 45");
     	menu_paradas.add("Avenida 39");
     	menu_paradas.add("Profamilia");
     	menu_paradas.add("Calle 26");
     	menu_paradas.add("Calle 22");
     	menu_paradas.add("Calle 19");
     	menu_paradas.add("Avenida Jiménez");
     	menu_paradas.add("Tercer Milenio");
     	return menu_paradas;
     }
   
     public List<String> GetZonaB() {
    	 
    	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("C.C. Santafé");
     	menu_paradas.add("Portal Norte");
     	menu_paradas.add("Toberin");
     	menu_paradas.add("Cardio infantil");
     	menu_paradas.add("Mazurén");
     	menu_paradas.add("Calle 146");
     	menu_paradas.add("Calle 142");
     	menu_paradas.add("Alcalá");
     	menu_paradas.add("Prado");
     	menu_paradas.add("Calle 127");
     	menu_paradas.add("Pepe sierra");
     	menu_paradas.add("Calle 106");
     	menu_paradas.add("Calle 100");
     	menu_paradas.add("Virrey");
     	menu_paradas.add("Calle 85");
     	menu_paradas.add("Héroes");
     	return menu_paradas;
     }
     
     public List<String> GetZonaC() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("Portal Suba");
     	menu_paradas.add("La campina");
     	menu_paradas.add("Transversal 91");
     	menu_paradas.add("21 Angeles");
     	menu_paradas.add("Gratamira");
     	menu_paradas.add("Avenida Boyacá");
     	menu_paradas.add("Niza Calle 127");
     	menu_paradas.add("Humedal Cordóba");
     	menu_paradas.add("Shaio");
     	menu_paradas.add("Puente largo");
     	menu_paradas.add("Suba Calle 100");
     	menu_paradas.add("Suba Calle 95");
     	menu_paradas.add("Rionegro");
     	menu_paradas.add("San Martín");
     	return menu_paradas;
     }
     
     public List<String> GetZonaD() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("Portal de la 80");
     	menu_paradas.add("Quirigua");
     	menu_paradas.add("Carrera 90");
     	menu_paradas.add("Avenida Cali");
     	menu_paradas.add("Granja - Carrera 77");
     	menu_paradas.add("Minuto de Dios");
     	menu_paradas.add("Boyacá");
     	menu_paradas.add("Ferias");
     	menu_paradas.add("Avenida 68");
     	menu_paradas.add("Carrera 53");
     	menu_paradas.add("Carrera 47");
     	menu_paradas.add("Escuela Militar");
     	menu_paradas.add("El Polo");
     	return menu_paradas;
     }
     
     public List<String> GetZonaE() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("La Castellana");
     	menu_paradas.add("NQS Calle 75");
     	menu_paradas.add("Avenida Chile");
     	menu_paradas.add("Simón Bolívar");
     	menu_paradas.add("Coliseo");
     	menu_paradas.add("El Campín");
     	menu_paradas.add("U. Nacional");
     	menu_paradas.add("Avenida Dorado");
     	menu_paradas.add("CAD");
     	menu_paradas.add("Paloquemao");
     	return menu_paradas;
     }
     
     public List<String> GetZonaF() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("Portal Américas");
     	menu_paradas.add("Patio Bonito");
     	menu_paradas.add("Biblioteca el tintal");
     	menu_paradas.add("Transversal 86");
     	menu_paradas.add("Banderas");
     	menu_paradas.add("Mandalay");
     	menu_paradas.add("Mundo Aventura");
     	menu_paradas.add("Marsella");
     	menu_paradas.add("Pradera");
     	menu_paradas.add("Américas Carrera 53A");
     	menu_paradas.add("Puente Aranda");
     	menu_paradas.add("Carrera 43");
     	menu_paradas.add("Zona Industrial");
     	menu_paradas.add("CDS Carrera 32");
     	menu_paradas.add("Ricaute");
     	menu_paradas.add("San Facon Carrera 22");
     	menu_paradas.add("De la Sabana");
     	menu_paradas.add("Avenida Jiménez");
     	return menu_paradas;
     }
     
     public List<String> GetZonaG() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
     	menu_paradas.add("Portal Sur");
     	menu_paradas.add("Perdomo");
     	menu_paradas.add("Madelena");
     	menu_paradas.add("Sevillana");
     	menu_paradas.add("Venecia");
     	menu_paradas.add("Alqueria");
     	menu_paradas.add("General Santander");
     	menu_paradas.add("NQS Calle 38A Sur");
     	menu_paradas.add("NQS Calle 30 Sur");
     	menu_paradas.add("SENA");
     	menu_paradas.add("Santa isabel");
     	menu_paradas.add("Comuneros");
     	menu_paradas.add("Ricaute");
     	return menu_paradas;
     }
     
     public List<String> GetZonaH() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
         menu_paradas.add("Portal Usme");
     	menu_paradas.add("Molinos");
     	menu_paradas.add("Consuelo");
     	menu_paradas.add("Socorro");
     	menu_paradas.add("Portal Tunal");
     	menu_paradas.add("Parque");
     	menu_paradas.add("Biblioteca");
     	menu_paradas.add("Santa Lucía");
     	menu_paradas.add("Calle 40 Sur");
     	menu_paradas.add("Quiroga");
     	menu_paradas.add("Olaya");
     	menu_paradas.add("Restrepo");
     	menu_paradas.add("Fucha");
     	menu_paradas.add("Nariño");
     	menu_paradas.add("Hortúa");
     	menu_paradas.add("Hospital");
     	return menu_paradas;     	
     }
     
     public List<String> GetZonaJ() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
         menu_paradas.add("Museo del oro");
         menu_paradas.add("Las Aguas");
      	return menu_paradas;
     }
     
     public List<String> GetZonaK() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
         menu_paradas.add("Portal Eldorado");
         menu_paradas.add("Modelia");
         menu_paradas.add("Normandía");
         menu_paradas.add("Av. Rojas");
         menu_paradas.add("El Tiempo");
         menu_paradas.add("Salitre - El Greco");
         menu_paradas.add("CAN");
         menu_paradas.add("Gobernación");
         menu_paradas.add("Quinta paredes");
         menu_paradas.add("Corferias");
         menu_paradas.add("Ciudad universitaria");
         menu_paradas.add("Plaza democratica");
         menu_paradas.add("Centro Memoria");
      	return menu_paradas;
     }
     
     public List<String> GetZonaL() {
    	 
     	List<String> menu_paradas = new ArrayList<String>();
         menu_paradas.add("Portal del 20 de Julio");
         menu_paradas.add("Country Sur");
         menu_paradas.add("Av. 1a de Mayo");
         menu_paradas.add("Ciudad jardín");
         menu_paradas.add("Policarpa");
         menu_paradas.add("Hospitales");
         menu_paradas.add("Bicentenario");
         menu_paradas.add("San Victorino");
         menu_paradas.add("Las Nieves");
         menu_paradas.add("San Diego");
      	return menu_paradas;
     }
     
     public boolean GetRuta(String szRuta) {

         if(m_pRutas==null)
             m_pRutas=new Rutas();
         Ruta ruta= m_pRutas.GetRuta(szRuta);
         if(ruta==null)
             return false;

         setTitle("Ruta " + szRuta);
         List<String> menu_resultados = new ArrayList<String>();
         
         if(ruta!=null) {
             Vector estaciones = ruta.GetEstaciones();
             menu_resultados.add(ruta.GetDescripcion());
             menu_resultados.add("");
             for(int j=0; j<estaciones.size(); j++) {
                 menu_resultados.add(estaciones.elementAt(j).toString());
             }
         }
         menu_resultados.add("");
         menu_resultados.add("Rutas del " + Rutas.GetFechaRutas());
         menu_resultados.add("Desarrollado por SDG");
         menu_resultados.add("https://transmilleno.example.invalid");
        
         m_bResultados=true;
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_resultados);
         setListAdapter(adapter);
         return true;
     }
    
     public void GetRutas(int zona) {
         
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
         setTitle("Seleccione su ruta");
         List<String> menu_paradas = new ArrayList<String>();
         
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
            menu_paradas.add(ruta.GetNombre()); 
         }
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_paradas);
         setListAdapter(adapter);
     }
     
     public void GetZona() {
         
    	 List<String> menu_paradas = null;
         if(m_nPosicion==1) {
        	 setTitle("Su ubicación");
             m_nIDUbicacion= m_nZona*100;
         } else {
        	 setTitle("Su destino"); 
             m_nIDDestino=m_nZona*100;
         }
         switch(m_nZona) {
     	  	case 0:
     	  		menu_paradas= GetZonaA();
     	  		break;
     	  	case 1:
     	  		menu_paradas= GetZonaB();
     	  		break;
     	  	case 2:
     	  		menu_paradas= GetZonaC();
     	  		break;
     	  	case 3:
     	  		menu_paradas= GetZonaD();
     	  		break;
     	  	case 4:
     	  		menu_paradas= GetZonaE();
     	  		break;
     	  	case 5:
     	  		menu_paradas= GetZonaF();
     	  		break;
     	  	case 6:
     	  		menu_paradas= GetZonaG();
     	  		break;
     	  	case 7:
     	  		menu_paradas= GetZonaH();
     	  		break;
     	  	case 8:
     	  		menu_paradas= GetZonaJ();
     	  		break;
     	  	case 9:
     	  		menu_paradas= GetZonaK();
     	  		break;
     	  	case 10:
     	  		menu_paradas= GetZonaL();
     	  		break;
     	  }
         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_paradas);
         setListAdapter(adapter);
     }
     
     public void GetRuta(ListAdapter lista, int index) {
    	 
    	 if (lista instanceof HeaderViewListAdapter) {
    	 
	    	 Cursor cursor = (Cursor) lista.getItem(index);
	    	 if(cursor.moveToPosition(index-1)) {
	             String texto= cursor.getString(1);
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
    	 }
     }
     
     @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         if ((keyCode == KeyEvent.KEYCODE_BACK)) {
        	 if(m_bResultados && m_nRutas==1) {
                 m_nRutas=0;
                 ShowResultados();
                 return false;
             }
             if(m_nPosicion>0 || m_nRutas>0) {
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
            	 return false;
             }
         }
         return super.onKeyDown(keyCode, event);
     }
     
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
    	
    	// Toast.makeText(getApplicationContext(), "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
    	if(m_bResultados) {
            if(m_nRutas==1) { // ya las esta mostrando
            } else {
                GetRuta(l.getAdapter(), position);
            }
        } else if(m_nRutas==1) {
            m_nRutas++;
            // List down = (List)display.getCurrent();
            GetRuta(l.getAdapter().getItem(position).toString());
        } else if(m_nPosicion==0) { // seleccion zona ubicacion
      	  m_nPosicion++;
      	  m_nZona= position-1;
      	  m_szZonaUbicacion= l.getAdapter().getItem(position).toString();
      	  GetZona();
        } else if(m_nPosicion==2) { // seleccion de zona destino
      	  m_nPosicion++;
      	  m_nZona= position-1;
      	  m_szZonaDestino= l.getAdapter().getItem(position).toString();
      	  GetZona();
        } else if(m_nPosicion==1) { // seleccion ubicacion
      	  	m_nPosicion++;
      	  	m_szUbicacion= l.getAdapter().getItem(position).toString();
      	  	
            int index= position;
            m_nIDUbicacion+=index;
            if(m_nIDUbicacion==518) { // jimenez
                m_nIDUbicacion=13;
            } else if(m_nIDUbicacion==613) { // Ricaurte 
                m_nIDUbicacion=515;
            }
            GetZonas();
        } else if(m_nPosicion==3) { // seleccion destino
            m_nPosicion++;
      	  	m_szDestino= l.getAdapter().getItem(position).toString();
            
            int index= position;
            m_nIDDestino+=index;
            if(m_nIDDestino==518) { // jimenez
                m_nIDDestino=13;
            } else if(m_nIDDestino==613) { // ricaurte
                m_nIDDestino=515;
            }
            GetResultado();
        }
    }
    
    @Override  
    public void onDestroy() {    
    	adView.destroy();    
    	super.onDestroy();  
    }
}