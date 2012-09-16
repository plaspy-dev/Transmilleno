/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sdg.transmilleno.classic;
import java.util.Vector;
/**
 *
 * @author JuHU
 */
public class Ruta {
        protected String m_szNombre;
        protected String m_szDescripcion;
        protected Vector m_vEstaciones;
        protected int m_vnIDEstaciones[];
        protected Horarios m_LV;
        protected Horarios m_S;
        protected Horarios m_F;
        boolean m_bTransbordo;
        public int m_nParadas;
        public int m_nDestino;
        public int m_nOrigen;
                
        public Ruta(String nombre,String descripcion, boolean transbordo) {
            m_szNombre = nombre;
            m_vEstaciones= new Vector();
            m_szDescripcion= descripcion;
            m_nParadas=0;
            m_vnIDEstaciones= new int[50];
            m_LV= m_S= m_F= null;
            m_nOrigen= m_nDestino=-1;
            m_bTransbordo=transbordo;
            for(int i=0; i<m_vnIDEstaciones.length; i++) 
                m_vnIDEstaciones[i]=0;
        }
        public void SetHorarioLV(int desde, int hasta) {
            m_LV= new Horarios(desde, hasta);
        }
        public void SetHorarioS(int desde, int hasta) {
            m_S= new Horarios(desde, hasta);
        }
        public void SetHorarioF(int desde, int hasta) {
            m_F= new Horarios(desde, hasta);
        }
        public Horarios GetHorarioLV() {
            return m_LV;
        }
        public Horarios GetHorarioS() {
            return m_S;
        }
        public Horarios GetHorarioF() {
            return m_F;
        }
        public String GetDescripcion() {
            return m_szDescripcion.replace("*", "\n");
        }
        public void AddEstacion(String estacion, int id) {
            m_vEstaciones.addElement(estacion);
            m_vnIDEstaciones[m_vEstaciones.size()-1]= id;
        }
        public int []GetIDEstaciones() {
            return m_vnIDEstaciones;
        }
        public String GetNombre() {
            return m_szNombre;
        }
        public Vector GetEstaciones() {
            return m_vEstaciones;
        }
        public int GetParadas() {
            return m_nParadas;
        }
        public boolean Calcular(String ubicacion, String destino) {
         
            int i=0;
            m_nOrigen= m_nDestino=-1;
            m_nParadas=0;
            for(i=0; i<m_vEstaciones.size(); i++) {
                if(ubicacion.compareTo(m_vEstaciones.elementAt(i).toString().toLowerCase())==0)
                    break;
            }
            for(;i<m_vEstaciones.size(); i++,m_nParadas++) {
                if(destino.compareTo(m_vEstaciones.elementAt(i).toString().toLowerCase())==0) {
                    return true;
                }
            }
            return false;
        }
        
        public boolean CalcularID(int ubicacion, int destino) {
         
            int i=0;
            m_nParadas=0;
            m_nOrigen= m_nDestino=-1;
            
            for(i=0; i<m_vEstaciones.size(); i++) {
                if(ubicacion==m_vnIDEstaciones[i]) {
                    if(m_bTransbordo)
                        m_nOrigen= i; // pasa por el origen
                    break;
                }
            }
            for(;i<m_vEstaciones.size(); i++,m_nParadas++) {
                if(destino==m_vnIDEstaciones[i]) {
                    if(m_bTransbordo)
                        m_nOrigen=m_nDestino=-1; // pasa por ambos
                    return true;
                }
            }
            for(i=0; i<m_vEstaciones.size(); i++) {
                if(destino==m_vnIDEstaciones[i]) {
                    if(m_bTransbordo)
                        m_nDestino= i; // llega al destino
                    break;
                }
            }
            return false;
        }
        public String GetInterseccion(Ruta destino) {
            
            String resultado="";
            m_nParadas= 0;
            for(int i=m_nOrigen+1; i<m_vEstaciones.size(); i++) {
                for( int j=0; j<destino.m_nDestino; j++) {
                    if(m_vnIDEstaciones[i]==destino.m_vnIDEstaciones[j]) {
                        resultado= m_vEstaciones.elementAt(i).toString();
                        m_nParadas=i-m_nOrigen;
                        m_nParadas+=destino.m_nDestino-j;
                        break;
                    }
                }
                if(m_nParadas>0) { // verificamos todo
                    for( int j=destino.m_nDestino-1; j>0; j--) {
                        if(m_vnIDEstaciones[i]==destino.m_vnIDEstaciones[j]) {
                            int paradas=i-m_nOrigen;
                            paradas+=destino.m_nDestino-j;
                            if(paradas<m_nParadas) {
                                resultado= m_vEstaciones.elementAt(i).toString();
                                m_nParadas=paradas;
                            }
                            break;
                        }
                    }
                }
            }
            return resultado;
        }
}
