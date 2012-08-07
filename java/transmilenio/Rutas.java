/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Vector;
/**
 *
 * @author JuHU
 */
public class Rutas {
    
    protected Vector m_vRutas;
    
    public Vector GetRutas() {
        return m_vRutas;
    }

    public Ruta GetRuta(String szRuta) {

        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.GetNombre().equals(szRuta)) {
                return ruta;
            }
        }
        return null;
    }

    public Vector GetRuta(String ubicacion, String destino) {

        Vector resultado= new Vector();
        ubicacion= ubicacion.toLowerCase();
        destino= destino.toLowerCase();
        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.Calcular(ubicacion, destino))
                resultado.addElement(ruta);
        }
        // ordenamos el vector resultante
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetParadas()>ruta.GetParadas()) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        return resultado;
    }
    
    public Vector GetTransbordos() {
        
        Vector resultado= new Vector();
        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.m_nOrigen>-1) { // pasa por el origen
                for(int j=0;j<m_vRutas.size(); j++) {
                    if(i==j)
                        continue;
                    Ruta rdestino= (Ruta)m_vRutas.elementAt(j);
                    if(rdestino.m_nDestino>-1) { // pasa por el destino 
                        String estacion= ruta.GetInterseccion(rdestino);
                        if(estacion.length()!=0) {
                            rdestino= new Ruta(ruta.GetNombre() + "-" + estacion + "-" + rdestino.GetNombre(), ruta.GetNombre() + ": " + ruta.GetDescripcion() + "\n" +rdestino.GetNombre() + ": " + rdestino.GetDescripcion(), false);
                            rdestino.m_nParadas= ruta.m_nParadas;
                            resultado.addElement(rdestino);
                        }
                    }
                }
            }
        }
        // ordenamos el vector resultante
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetParadas()>ruta.GetParadas()) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        return resultado;
    }
    
    public Vector GetRutaID(int ubicacion, int destino) {

        Vector resultado= new Vector();
        
        for(int i=0; i<m_vRutas.size(); i++) {
            Ruta ruta= (Ruta)m_vRutas.elementAt(i);
            if(ruta.CalcularID(ubicacion, destino))
                resultado.addElement(ruta);
        }
        // ordenamos el vector resultante
        for(int i=resultado.size()-1; i>0; i--) {
            for(int j=0;j<i; j++) {
                Ruta ruta=(Ruta)resultado.elementAt(j+1);
                Ruta ruta2= (Ruta)resultado.elementAt(j);
                if(ruta2.GetParadas()>ruta.GetParadas()) {
                    Ruta temp= ruta2;
                    resultado.setElementAt(ruta, j);
                    resultado.setElementAt(temp, j+1);
                }
            }
        }
        return resultado;
    }
    
    public Ruta AddRuta(String ruta, String descripcion, boolean transbordo) {
        Ruta pRuta= new Ruta(ruta,descripcion,transbordo);
        m_vRutas.addElement(pRuta);
        return pRuta;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original, String descripcion) {
		
		Ruta nueva= new Ruta(ruta, descripcion, original.m_bTransbordo);
        Vector estaciones= original.GetEstaciones();
        int []ids= original.GetIDEstaciones();
        for(int i=estaciones.size()-1; i>=0; i--) 
            nueva.AddEstacion(estaciones.elementAt(i).toString(), ids[i]);
        if(original.GetHorarioLV()!=null)
            nueva.SetHorarioLV(original.GetHorarioLV().GetDesde(), original.GetHorarioLV().GetHasta());
        if(original.GetHorarioS()!=null)
            nueva.SetHorarioS(original.GetHorarioS().GetDesde(), original.GetHorarioS().GetHasta());
        if(original.GetHorarioF()!=null)
            nueva.SetHorarioF(original.GetHorarioF().GetDesde(), original.GetHorarioF().GetHasta());
		m_vRutas.addElement(nueva);
        return nueva;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original) {
    
	return AddRutaInvertida(ruta, original, original.GetDescripcion());
    }
    
    public Rutas() {
        
        m_vRutas= new Vector();
        
        Ruta ruta=AddRuta("D01", "Recorrido ficticio D01", true);
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(5, 22);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 003", 104);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 011", 311);
        ruta.AddEstacion("Demo Parada 012", 310);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 014", 304);
        ruta.AddEstacion("Demo Parada 015", 303);
        ruta.AddEstacion("Demo Parada 016", 301);

        ruta= AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso"); 

        ruta= AddRuta("D02", "Recorrido ficticio D02",false);
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 017", 115);
        
        ruta= AddRuta("D03", "Recorrido ficticio D03", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 024", 704);
        ruta.AddEstacion("Demo Parada 025", 703);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        ruta= AddRuta("D04", "Recorrido ficticio D04", false);
        ruta.SetHorarioLV(4, 9);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 024", 704);
        ruta.AddEstacion("Demo Parada 025", 703);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D05", "Recorrido ficticio D05", false);
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso"); 

        ruta= AddRuta("D06", "Recorrido ficticio D06", true);
        ruta.SetHorarioLV(9, 23);
        ruta.SetHorarioS(5, 23);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 024", 704);
        ruta.AddEstacion("Demo Parada 025", 703);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso"); 

        ruta= AddRuta("D07", "Recorrido ficticio D07",true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(6, 22);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 036", 103);
        ruta.AddEstacion("Demo Parada 003", 104);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 037", 3);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 038", 11);
        ruta.AddEstacion("Demo Parada 039", 14);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 041", 710);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 042", 705);

        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D08", "Recorrido ficticio D08", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Demo Parada 042", 705);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 001", 101);

        ruta= AddRuta("D09", "Recorrido ficticio D09", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 042", 705);

        ruta= AddRuta("D10", "Recorrido ficticio D10", false); 
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Demo Parada 042", 705);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 001", 101);

        ruta= AddRuta("D11", "Recorrido ficticio D11",false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioS(5, 9);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 048", 709);
        ruta.AddEstacion("Demo Parada 042", 705);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 

        ruta= AddRuta("D12", "Recorrido ficticio D12", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 049", 713);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 042", 705);

        ruta= AddRuta("D13", "Recorrido ficticio D13", false); 
        ruta.SetHorarioLV(6, 9);
        ruta.SetHorarioLV(17, 20);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 036", 103);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 050", 213);
        ruta.AddEstacion("Demo Parada 051", 209);
        ruta.AddEstacion("Demo Parada 052", 204);
        ruta.AddEstacion("Demo Parada 053", 203);
        ruta.AddEstacion("Demo Parada 054", 202);
        ruta.AddEstacion("Demo Parada 055", 201);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso");
        
        ruta= AddRuta("D14", "Recorrido ficticio D14", false); 
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 056", 801);
        ruta.AddEstacion("Demo Parada 057", 802);

        ruta= AddRuta("D15", "Recorrido ficticio D15", true); 
        ruta.SetHorarioLV(9, 22);
        ruta.SetHorarioS(7, 21);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 056", 801);
        ruta.AddEstacion("Demo Parada 057", 802);
        
        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 

        ruta= AddRuta("D16", "Recorrido ficticio D16", true); 
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioLV(5, 22);
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 058", 514);
        ruta.AddEstacion("Demo Parada 059", 509);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 061", 504);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 063", 501);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D17", "Recorrido ficticio D17", false); 

        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 064", 508);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 063", 501);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D18", "Recorrido ficticio D18", true); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 003", 104);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 065", 312);
        ruta.AddEstacion("Demo Parada 066", 406);
        ruta.AddEstacion("Demo Parada 067", 407);
        ruta.AddEstacion("Demo Parada 068", 409);
        ruta.AddEstacion("Demo Parada 069", 410);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 072", 606);
        ruta.AddEstacion("Demo Parada 073", 605);
        ruta.AddEstacion("Demo Parada 074", 601);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D19", "Recorrido ficticio D19", false); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 003", 104);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 076", 603);
        ruta.AddEstacion("Demo Parada 077", 602);
        ruta.AddEstacion("Demo Parada 074", 601);
        
        ruta= AddRuta("D20", "Recorrido ficticio D20", false);
        ruta.AddEstacion("Demo Parada 074", 601);
        ruta.AddEstacion("Demo Parada 077", 602);
        ruta.AddEstacion("Demo Parada 076", 603);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 007", 111);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 003", 104);
                
        ruta= AddRuta("D21", "Recorrido ficticio D21", true); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 078", 401);
        ruta.AddEstacion("Demo Parada 079", 402);
        ruta.AddEstacion("Demo Parada 080", 404);
        ruta.AddEstacion("Demo Parada 067", 407);
        ruta.AddEstacion("Demo Parada 081", 408);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 082", 612);
        ruta.AddEstacion("Demo Parada 083", 611);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 076", 603);
        ruta.AddEstacion("Demo Parada 077", 602);
        ruta.AddEstacion("Demo Parada 074", 601);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 

        ruta= AddRuta("D22", "Recorrido ficticio D22", true); 
        ruta.AddEstacion("Demo Parada 055", 201);
        ruta.AddEstacion("Demo Parada 084", 207);
        ruta.AddEstacion("Demo Parada 085", 208);
        ruta.AddEstacion("Demo Parada 086", 210);
        ruta.AddEstacion("Demo Parada 065", 312);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 038", 11);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 039", 14);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 087", 707);
        ruta.AddEstacion("Demo Parada 042", 705);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D23", "Recorrido ficticio D23", false); 
        ruta.AddEstacion("Demo Parada 055", 201);
        ruta.AddEstacion("Demo Parada 054", 202);
        ruta.AddEstacion("Demo Parada 088", 206);
        ruta.AddEstacion("Demo Parada 051", 209);
        ruta.AddEstacion("Demo Parada 086", 210);
        ruta.AddEstacion("Demo Parada 089", 214);
        ruta.AddEstacion("Demo Parada 090", 313);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 037", 3);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 091", 716);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 024", 704);
        ruta.AddEstacion("Demo Parada 025", 703);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D24", "Recorrido ficticio D24", true); 
        ruta.AddEstacion("Demo Parada 055", 201);
        ruta.AddEstacion("Demo Parada 054", 202);
        ruta.AddEstacion("Demo Parada 053", 203);
        ruta.AddEstacion("Demo Parada 092", 205);
        ruta.AddEstacion("Demo Parada 086", 210);
        ruta.AddEstacion("Demo Parada 093", 211);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 094", 510);
        ruta.AddEstacion("Demo Parada 064", 508);
        ruta.AddEstacion("Demo Parada 095", 506);
        ruta.AddEstacion("Demo Parada 060", 505);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso");  
        
        ruta= AddRuta("D25", "Recorrido ficticio D25", true); 
        ruta.AddEstacion("Demo Parada 055", 201);
        ruta.AddEstacion("Demo Parada 054", 202);
        ruta.AddEstacion("Demo Parada 053", 203);
        ruta.AddEstacion("Demo Parada 088", 206);
        ruta.AddEstacion("Demo Parada 051", 209);
        ruta.AddEstacion("Demo Parada 086", 210);
        ruta.AddEstacion("Demo Parada 096", 212);
        ruta.AddEstacion("Demo Parada 050", 213);
        ruta.AddEstacion("Demo Parada 089", 214);
        ruta.AddEstacion("Demo Parada 079", 402);
        ruta.AddEstacion("Demo Parada 097", 403);
        ruta.AddEstacion("Demo Parada 080", 404);
        ruta.AddEstacion("Demo Parada 098", 405);
        ruta.AddEstacion("Demo Parada 066", 406);
        ruta.AddEstacion("Demo Parada 067", 407);
        ruta.AddEstacion("Demo Parada 068", 409);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 083", 611);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 

        ruta= AddRuta("D26", "Recorrido ficticio D26", true); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 099", 304);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 100", 307);
        ruta.AddEstacion("Demo Parada 101", 308);
        ruta.AddEstacion("Demo Parada 102", 309);
        ruta.AddEstacion("Demo Parada 090", 313);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 049", 713);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso");  
        
        ruta= AddRuta("D27", "Recorrido ficticio D27", false); 
        ruta.AddEstacion("Demo Parada 027", 701);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 016", 301);

        ruta= AddRuta("D28", "Recorrido ficticio D28", false); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 015", 303);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D29", "Recorrido ficticio D29", false); 
        ruta.AddEstacion("Demo Parada 063", 501);
        ruta.AddEstacion("Demo Parada 046", 10);
        ruta.AddEstacion("Demo Parada 045", 9);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 016", 301);
        
        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso");

        ruta= AddRuta("D30", "Recorrido ficticio D30", true); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 102", 309);
        ruta.AddEstacion("Demo Parada 065", 312);
        ruta.AddEstacion("Demo Parada 090", 313);
        ruta.AddEstacion("Demo Parada 044", 8);
        ruta.AddEstacion("Demo Parada 038", 11);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 056", 801);
        ruta.AddEstacion("Demo Parada 057", 802);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 

        ruta= AddRuta("D31", "Recorrido ficticio D31", true); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 104", 302);
        ruta.AddEstacion("Demo Parada 099", 304);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 037", 3);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 049", 713);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 105", 706);
        ruta.AddEstacion("Demo Parada 042", 705);
        
        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D32", "Recorrido ficticio D32", false); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 102", 309);
        ruta.AddEstacion("Demo Parada 011", 311);
        ruta.AddEstacion("Demo Parada 079", 402);
        ruta.AddEstacion("Demo Parada 097", 403);
        ruta.AddEstacion("Demo Parada 067", 407);
        ruta.AddEstacion("Demo Parada 081", 408);
        ruta.AddEstacion("Demo Parada 069", 410);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 106", 604);
        ruta.AddEstacion("Demo Parada 074", 601);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D33", "Recorrido ficticio D33", true); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 102", 309);
        ruta.AddEstacion("Demo Parada 011", 311);
        ruta.AddEstacion("Demo Parada 079", 402);
        ruta.AddEstacion("Demo Parada 097", 403);
        ruta.AddEstacion("Demo Parada 067", 407);
        ruta.AddEstacion("Demo Parada 081", 408);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D34", "Recorrido ficticio D34", true); 
        ruta.AddEstacion("Demo Parada 063", 501);
        ruta.AddEstacion("Demo Parada 107", 502);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 108", 507);
        ruta.AddEstacion("Demo Parada 109", 513);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 110", 516);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 056", 801);
        ruta.AddEstacion("Demo Parada 057", 802);
        
        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D35", "Recorrido ficticio D35", false); 
        ruta.AddEstacion("Demo Parada 063", 501);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 059", 509);
        ruta.AddEstacion("Demo Parada 111", 511);
        ruta.AddEstacion("Demo Parada 112", 512);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 113", 517);
        ruta.AddEstacion("Demo Parada 031", 13);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 

        ruta= AddRuta("D36", "Recorrido ficticio D36", false); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 012", 310);
        ruta.AddEstacion("Demo Parada 102", 309);
        ruta.AddEstacion("Demo Parada 101", 308);
        ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 016", 301);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D37", "Recorrido ficticio D37", false); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 028", 106);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 024", 704);
        ruta.AddEstacion("Demo Parada 025", 703);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D38", "Recorrido ficticio D38", false); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 002", 102);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 006", 110);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 037", 3);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 038", 11);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 039", 14);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 049", 713);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 042", 705);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D39", "Recorrido ficticio D39", false); 
        ruta.AddEstacion("Demo Parada 001", 101);
        ruta.AddEstacion("Demo Parada 004", 105);
        ruta.AddEstacion("Demo Parada 005", 107);
        ruta.AddEstacion("Demo Parada 029", 108);
        ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 078", 401);
        ruta.AddEstacion("Demo Parada 079", 402);
        ruta.AddEstacion("Demo Parada 066", 406);
        ruta.AddEstacion("Demo Parada 081", 408);
        ruta.AddEstacion("Demo Parada 068", 409);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 083", 611);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 072", 606);
        ruta.AddEstacion("Demo Parada 073", 605);
        ruta.AddEstacion("Demo Parada 074", 601);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D40", "Recorrido ficticio D40", false); 
        ruta.AddEstacion("Demo Parada 055", 201);
        ruta.AddEstacion("Demo Parada 054", 202);
        ruta.AddEstacion("Demo Parada 053", 203);
        ruta.AddEstacion("Demo Parada 051", 209);
        ruta.AddEstacion("Demo Parada 086", 210);
        ruta.AddEstacion("Demo Parada 096", 212);
        ruta.AddEstacion("Demo Parada 065", 312);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 037", 3);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 043", 7);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 110", 516);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 109", 513);
        ruta.AddEstacion("Demo Parada 059", 509);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 063", 501);

        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso"); 
        
        ruta= AddRuta("D41", "Recorrido ficticio D41", false); 
        ruta.AddEstacion("Demo Parada 016", 301);
        ruta.AddEstacion("Demo Parada 104", 710);
        ruta.AddEstacion("Demo Parada 014", 304);
        ruta.AddEstacion("Demo Parada 013", 305);
        ruta.AddEstacion("Demo Parada 065", 312);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 020",4);
        ruta.AddEstacion("Demo Parada 021",5);
        ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 056", 801);
        ruta.AddEstacion("Demo Parada 057", 802);        
        
        AddRutaInvertida("D60R", ruta,"Recorrido ficticio inverso");  
        
        ruta= AddRuta("D42", "Recorrido ficticio D42", false); 
        ruta.AddEstacion("Demo Parada 055", 201);
        ruta.AddEstacion("Demo Parada 053", 203);
        ruta.AddEstacion("Demo Parada 017", 115);
        
        ruta= AddRuta("D43", "Recorrido ficticio D43", false); 
        ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 085", 208);
    	ruta.AddEstacion("Demo Parada 086", 210);
    	ruta.AddEstacion("Demo Parada 065", 312);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 022",6);
    	ruta.AddEstacion("Demo Parada 043", 7);
    	ruta.AddEstacion("Demo Parada 044", 8);
    	ruta.AddEstacion("Demo Parada 038", 11);
    	ruta.AddEstacion("Demo Parada 047", 12);
    	
    	ruta= AddRuta("D44", "Recorrido ficticio D44", false);  
    	ruta.AddEstacion("Demo Parada 002", 102);
    	ruta.AddEstacion("Demo Parada 004", 105);
    	ruta.AddEstacion("Demo Parada 028", 106);
    	ruta.AddEstacion("Demo Parada 005", 107);
    	ruta.AddEstacion("Demo Parada 029", 108);
    	ruta.AddEstacion("Demo Parada 030", 109);
        ruta.AddEstacion("Demo Parada 018", 1);
    	ruta.AddEstacion("Demo Parada 019",2);
 
	ruta= AddRuta("D45", "Recorrido ficticio D45", false);
	ruta.AddEstacion("Demo Parada 001", 101);
    	ruta.AddEstacion("Demo Parada 009", 113);
    	ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 018", 1);
    	ruta.AddEstacion("Demo Parada 019",2);
    	ruta.AddEstacion("Demo Parada 037", 3);
    	ruta.AddEstacion("Demo Parada 020",4);
    	ruta.AddEstacion("Demo Parada 022",6);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 042", 705);
        
        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
        
        ruta= AddRuta("D46", "Recorrido ficticio D46", false);
        ruta.AddEstacion("Demo Parada 037", 3);
        ruta.AddEstacion("Demo Parada 019",2);
        ruta.AddEstacion("Demo Parada 018", 1);
        ruta.AddEstacion("Demo Parada 079", 402);
        ruta.AddEstacion("Demo Parada 083", 611);
        ruta.AddEstacion("Demo Parada 114", 609);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 073", 605);
        ruta.AddEstacion("Demo Parada 077", 602);
        ruta.AddEstacion("Demo Parada 074", 601);

        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
        
        ruta= AddRuta("D47", "Recorrido ficticio D47", false);
        ruta.AddEstacion("Demo Parada 001", 101);
    	ruta.AddEstacion("Demo Parada 029", 108);
    	ruta.AddEstacion("Demo Parada 006", 110);
    	ruta.AddEstacion("Demo Parada 007", 111);
    	ruta.AddEstacion("Demo Parada 079", 402);
    	ruta.AddEstacion("Demo Parada 097", 403);
    	ruta.AddEstacion("Demo Parada 080", 404);
    	ruta.AddEstacion("Demo Parada 067", 407);
    	ruta.AddEstacion("Demo Parada 068", 409);
        ruta.AddEstacion("Demo Parada 058", 514);
        ruta.AddEstacion("Demo Parada 109", 513);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 063", 501);
        
        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
        
        ruta= AddRuta("D48", "Recorrido ficticio D48", false);
        ruta.AddEstacion("Demo Parada 027", 701);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 042", 705);
        ruta.AddEstacion("Demo Parada 106", 604);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 069", 410);
        ruta.AddEstacion("Demo Parada 068", 409);
    	ruta.AddEstacion("Demo Parada 067", 407);
    	ruta.AddEstacion("Demo Parada 080", 404);
    	
        ruta= AddRuta("D49", "Recorrido ficticio D49", false);
        ruta.AddEstacion("Demo Parada 080", 404);
    	ruta.AddEstacion("Demo Parada 067", 407);
    	ruta.AddEstacion("Demo Parada 068", 409);
    	ruta.AddEstacion("Demo Parada 069", 410);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 106", 604);
        ruta.AddEstacion("Demo Parada 074", 601);
        ruta.AddEstacion("Demo Parada 042", 705);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);
        
        ruta= AddRuta("D50", "Recorrido ficticio D50", false);
        ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 054", 202);
    	ruta.AddEstacion("Demo Parada 053", 203);
    	ruta.AddEstacion("Demo Parada 019",2);
    	ruta.AddEstacion("Demo Parada 020",4);
    	ruta.AddEstacion("Demo Parada 022",6);
    	ruta.AddEstacion("Demo Parada 045", 9);
    	ruta.AddEstacion("Demo Parada 046", 10);
	ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 107", 502);
        ruta.AddEstacion("Demo Parada 063", 501);
        
        AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
        
        ruta= AddRuta("D51", "Recorrido ficticio D51", false);
       	ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 054", 202);
    	ruta.AddEstacion("Demo Parada 053", 203);
    	ruta.AddEstacion("Demo Parada 088", 206);
    	ruta.AddEstacion("Demo Parada 086", 210);
    	ruta.AddEstacion("Demo Parada 096", 212);
    	ruta.AddEstacion("Demo Parada 079", 402);
    	ruta.AddEstacion("Demo Parada 098", 405);
    	ruta.AddEstacion("Demo Parada 067", 407);
    	ruta.AddEstacion("Demo Parada 068", 409);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 083", 611);
        ruta.AddEstacion("Demo Parada 115", 608);
        ruta.AddEstacion("Demo Parada 074", 601);
    	
       	AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
       	
       	ruta= AddRuta("D52", "Recorrido ficticio D52", false);
       	ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 054", 202);
    	ruta.AddEstacion("Demo Parada 053", 203);
       	ruta.AddEstacion("Demo Parada 051", 209);
    	ruta.AddEstacion("Demo Parada 050", 213);
    	ruta.AddEstacion("Demo Parada 089", 214);
    	ruta.AddEstacion("Demo Parada 097", 403);
    	ruta.AddEstacion("Demo Parada 080", 404);
    	ruta.AddEstacion("Demo Parada 066", 406);
    	ruta.AddEstacion("Demo Parada 067", 407);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 114", 609);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 073", 605);
        ruta.AddEstacion("Demo Parada 074", 601);
    	
       	AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
       	
       	ruta= AddRuta("D53", "Recorrido ficticio D53", false);
       	ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 054", 202);
    	ruta.AddEstacion("Demo Parada 053", 203);
    	ruta.AddEstacion("Demo Parada 088", 206);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 086", 210);
    	ruta.AddEstacion("Demo Parada 089", 214);
    	ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 056", 801);
        ruta.AddEstacion("Demo Parada 057", 802);
       	
       	AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
       	
       	ruta= AddRuta("D54", "Recorrido ficticio D54", false);
       	ruta.AddEstacion("Demo Parada 016", 301);
    	ruta.AddEstacion("Demo Parada 015", 303);
    	ruta.AddEstacion("Demo Parada 013", 305);
    	ruta.AddEstacion("Demo Parada 103", 306);
        ruta.AddEstacion("Demo Parada 018", 1);
    	ruta.AddEstacion("Demo Parada 019",2);
    	
    	ruta= AddRuta("D55", "Recorrido ficticio D55", false);    	
    	ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 053", 203);
    	ruta.AddEstacion("Demo Parada 088", 206);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 051", 209);
    	ruta.AddEstacion("Demo Parada 086", 210);
    	ruta.AddEstacion("Demo Parada 093", 211);
    	ruta.AddEstacion("Demo Parada 097", 403);
    	ruta.AddEstacion("Demo Parada 098", 405);
    	ruta.AddEstacion("Demo Parada 066", 406);
    	ruta.AddEstacion("Demo Parada 068", 409);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 082", 612);
        ruta.AddEstacion("Demo Parada 083", 611);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 115", 608);
        ruta.AddEstacion("Demo Parada 073", 605);
        ruta.AddEstacion("Demo Parada 077", 602);
        ruta.AddEstacion("Demo Parada 074", 601);
        
    	AddRutaInvertida("D60R", ruta, "Recorrido ficticio inverso");
        
        ruta= AddRuta("D56", "Recorrido ficticio D56", true); 
        
        ruta.AddEstacion("Demo Parada 001", 101);
    	ruta.AddEstacion("Demo Parada 002", 102);
    	ruta.AddEstacion("Demo Parada 036", 103);
    	ruta.AddEstacion("Demo Parada 003", 104);
    	ruta.AddEstacion("Demo Parada 004", 105);
    	ruta.AddEstacion("Demo Parada 028", 106);
    	ruta.AddEstacion("Demo Parada 005", 107);
    	ruta.AddEstacion("Demo Parada 029", 108);
    	ruta.AddEstacion("Demo Parada 030", 109);
    	ruta.AddEstacion("Demo Parada 006", 110);
    	ruta.AddEstacion("Demo Parada 007", 111);
    	ruta.AddEstacion("Demo Parada 008", 112);
    	ruta.AddEstacion("Demo Parada 009", 113);
    	ruta.AddEstacion("Demo Parada 010", 114);
    	ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 018", 1);
    	ruta.AddEstacion("Demo Parada 019",2);
    	ruta.AddEstacion("Demo Parada 037", 3);
    	ruta.AddEstacion("Demo Parada 020",4);
    	ruta.AddEstacion("Demo Parada 021",5);
    	ruta.AddEstacion("Demo Parada 022",6);
    	ruta.AddEstacion("Demo Parada 043", 7);
    	ruta.AddEstacion("Demo Parada 044", 8);
    	ruta.AddEstacion("Demo Parada 045", 9);
    	ruta.AddEstacion("Demo Parada 046", 10);
    	ruta.AddEstacion("Demo Parada 038", 11);
    	ruta.AddEstacion("Demo Parada 047", 12);
        ruta.AddEstacion("Demo Parada 031", 13);
        ruta.AddEstacion("Demo Parada 113", 517);
        ruta.AddEstacion("Demo Parada 110", 516);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 058", 514);
        ruta.AddEstacion("Demo Parada 109", 513);
        ruta.AddEstacion("Demo Parada 112", 512);
        ruta.AddEstacion("Demo Parada 111", 511);
        ruta.AddEstacion("Demo Parada 094", 510);
        ruta.AddEstacion("Demo Parada 059", 509);
        ruta.AddEstacion("Demo Parada 064", 508);
        ruta.AddEstacion("Demo Parada 108", 507);
        ruta.AddEstacion("Demo Parada 095", 506);
        ruta.AddEstacion("Demo Parada 060", 505);
        ruta.AddEstacion("Demo Parada 061", 504);
        ruta.AddEstacion("Demo Parada 062", 503);
        ruta.AddEstacion("Demo Parada 107", 502);
        ruta.AddEstacion("Demo Parada 063", 501);

        AddRutaInvertida("D60R", ruta); 
        
        ruta= AddRuta("D57", "Recorrido ficticio D57", true);
        ruta.AddEstacion("Demo Parada 001", 101);
    	ruta.AddEstacion("Demo Parada 002", 102);
    	ruta.AddEstacion("Demo Parada 036", 103);
    	ruta.AddEstacion("Demo Parada 003", 104);
    	ruta.AddEstacion("Demo Parada 004", 105);
    	ruta.AddEstacion("Demo Parada 028", 106);
    	ruta.AddEstacion("Demo Parada 005", 107);
    	ruta.AddEstacion("Demo Parada 029", 108);
    	ruta.AddEstacion("Demo Parada 030", 109);
    	ruta.AddEstacion("Demo Parada 006", 110);
    	ruta.AddEstacion("Demo Parada 007", 111);
    	ruta.AddEstacion("Demo Parada 008", 112);
        ruta.AddEstacion("Demo Parada 009", 113);
        ruta.AddEstacion("Demo Parada 010", 114);
        ruta.AddEstacion("Demo Parada 017", 115);
        ruta.AddEstacion("Demo Parada 090", 313);
        ruta.AddEstacion("Demo Parada 065", 312);
    	ruta.AddEstacion("Demo Parada 079", 402);
    	ruta.AddEstacion("Demo Parada 097", 403);
    	ruta.AddEstacion("Demo Parada 080", 404);
    	ruta.AddEstacion("Demo Parada 098", 405);
    	ruta.AddEstacion("Demo Parada 066", 406);
    	ruta.AddEstacion("Demo Parada 067", 407);
    	ruta.AddEstacion("Demo Parada 081", 408);
    	ruta.AddEstacion("Demo Parada 068", 409);
    	ruta.AddEstacion("Demo Parada 069", 410);
        ruta.AddEstacion("Demo Parada 070", 613);
        ruta.AddEstacion("Demo Parada 082", 612);
        ruta.AddEstacion("Demo Parada 083", 611);
        ruta.AddEstacion("Demo Parada 071", 610);
        ruta.AddEstacion("Demo Parada 114", 609);
        ruta.AddEstacion("Demo Parada 115", 608);
        ruta.AddEstacion("Demo Parada 075", 607);
        ruta.AddEstacion("Demo Parada 072", 606);
        ruta.AddEstacion("Demo Parada 073", 605);
        ruta.AddEstacion("Demo Parada 106", 604);
        ruta.AddEstacion("Demo Parada 076", 603);
        ruta.AddEstacion("Demo Parada 077", 602);
        ruta.AddEstacion("Demo Parada 074", 601);

        AddRutaInvertida("D60R", ruta);

        ruta= AddRuta("D58", "Recorrido ficticio D58", true);
        ruta.AddEstacion("Demo Parada 016", 301);
    	ruta.AddEstacion("Demo Parada 104", 302);
    	ruta.AddEstacion("Demo Parada 015", 303);
    	ruta.AddEstacion("Demo Parada 099", 304);
    	ruta.AddEstacion("Demo Parada 013", 305);
    	ruta.AddEstacion("Demo Parada 103", 306);
    	ruta.AddEstacion("Demo Parada 100", 307);
    	ruta.AddEstacion("Demo Parada 101", 308);
    	ruta.AddEstacion("Demo Parada 102", 309);
    	ruta.AddEstacion("Demo Parada 012", 310);
    	ruta.AddEstacion("Demo Parada 011", 311);
    	ruta.AddEstacion("Demo Parada 065", 312);
    	ruta.AddEstacion("Demo Parada 090", 313);
        ruta.AddEstacion("Demo Parada 018", 1);
    	ruta.AddEstacion("Demo Parada 019",2);
    	ruta.AddEstacion("Demo Parada 037", 3);
    	ruta.AddEstacion("Demo Parada 020",4);
    	ruta.AddEstacion("Demo Parada 021",5);
    	ruta.AddEstacion("Demo Parada 022",6);
    	ruta.AddEstacion("Demo Parada 043", 7);
    	ruta.AddEstacion("Demo Parada 044", 8);
    	ruta.AddEstacion("Demo Parada 045", 9);
    	ruta.AddEstacion("Demo Parada 046", 10);
    	ruta.AddEstacion("Demo Parada 038", 11);
    	ruta.AddEstacion("Demo Parada 047", 12);
    	ruta.AddEstacion("Demo Parada 031", 13);
    	ruta.AddEstacion("Demo Parada 039", 14);
        ruta.AddEstacion("Demo Parada 091", 716);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 049", 713);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 041", 710);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 087", 707);
        ruta.AddEstacion("Demo Parada 105", 706);
        ruta.AddEstacion("Demo Parada 042", 705);

        AddRutaInvertida("D60R", ruta);
        
        ruta= AddRuta("D59", "Recorrido ficticio D59", true);
        ruta.AddEstacion("Demo Parada 055", 201);
    	ruta.AddEstacion("Demo Parada 054", 202);
    	ruta.AddEstacion("Demo Parada 053", 203);
    	ruta.AddEstacion("Demo Parada 052", 204);
    	ruta.AddEstacion("Demo Parada 092", 205);
    	ruta.AddEstacion("Demo Parada 088", 206);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 085", 208);
    	ruta.AddEstacion("Demo Parada 051", 209);
    	ruta.AddEstacion("Demo Parada 086", 210);
    	ruta.AddEstacion("Demo Parada 093", 211);
    	ruta.AddEstacion("Demo Parada 096", 212);
    	ruta.AddEstacion("Demo Parada 050", 213);
    	ruta.AddEstacion("Demo Parada 089", 214);
        ruta.AddEstacion("Demo Parada 065", 312);
    	ruta.AddEstacion("Demo Parada 090", 313);
        ruta.AddEstacion("Demo Parada 018", 1);
    	ruta.AddEstacion("Demo Parada 019",2);
    	ruta.AddEstacion("Demo Parada 037", 3);
    	ruta.AddEstacion("Demo Parada 020",4);
    	ruta.AddEstacion("Demo Parada 021",5);
    	ruta.AddEstacion("Demo Parada 022",6);
    	ruta.AddEstacion("Demo Parada 043", 7);
    	ruta.AddEstacion("Demo Parada 044", 8);
    	ruta.AddEstacion("Demo Parada 045", 9);
    	ruta.AddEstacion("Demo Parada 046", 10);
    	ruta.AddEstacion("Demo Parada 038", 11);
    	ruta.AddEstacion("Demo Parada 047", 12);
    	ruta.AddEstacion("Demo Parada 031", 13);
    	ruta.AddEstacion("Demo Parada 039", 14);
        ruta.AddEstacion("Demo Parada 091", 716);
        ruta.AddEstacion("Demo Parada 032", 715);
        ruta.AddEstacion("Demo Parada 033", 714);
        ruta.AddEstacion("Demo Parada 049", 713);
        ruta.AddEstacion("Demo Parada 034", 712);
        ruta.AddEstacion("Demo Parada 040", 711);
        ruta.AddEstacion("Demo Parada 041", 710);
        ruta.AddEstacion("Demo Parada 035", 709);
        ruta.AddEstacion("Demo Parada 023", 708);
        ruta.AddEstacion("Demo Parada 024", 704);
        ruta.AddEstacion("Demo Parada 025", 703);
        ruta.AddEstacion("Demo Parada 026", 702);
        ruta.AddEstacion("Demo Parada 027", 701);
                
        AddRutaInvertida("D60R", ruta);
    }
}