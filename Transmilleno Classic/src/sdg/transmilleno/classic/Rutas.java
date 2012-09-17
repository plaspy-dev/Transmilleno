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
        Ruta ruta;
        
    	ruta= this.AddRuta("D01", "Recorrido ficticio D01", true);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 002", 502);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 004", 504);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 006", 506);
    	ruta.AddEstacion("Demo Parada 007", 507);
    	ruta.AddEstacion("Demo Parada 008", 508);
    	ruta.AddEstacion("Demo Parada 009", 509);
    	ruta.AddEstacion("Demo Parada 010", 510);
    	ruta.AddEstacion("Demo Parada 011", 511);
    	ruta.AddEstacion("Demo Parada 012", 512);
    	ruta.AddEstacion("Demo Parada 013", 513);
    	ruta.AddEstacion("Demo Parada 014", 514);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 016", 516);
    	ruta.AddEstacion("Demo Parada 017", 517);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 042", 105);
    	ruta.AddEstacion("Demo Parada 043", 104);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 046", 101);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D02", "Recorrido ficticio D02", true);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 048", 706);
    	ruta.AddEstacion("Demo Parada 049", 707);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 052", 710);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 055", 713);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 058", 716);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 060", 313);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 062", 311);
    	ruta.AddEstacion("Demo Parada 063", 310);
    	ruta.AddEstacion("Demo Parada 064", 309);
    	ruta.AddEstacion("Demo Parada 065", 308);
    	ruta.AddEstacion("Demo Parada 066", 307);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 069", 304);
    	ruta.AddEstacion("Demo Parada 070", 303);
    	ruta.AddEstacion("Demo Parada 071", 302);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D03", "Recorrido ficticio D03", true);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 075", 703);
    	ruta.AddEstacion("Demo Parada 076", 704);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 052", 710);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 055", 713);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 058", 716);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 060", 313);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 077", 214);
    	ruta.AddEstacion("Demo Parada 078", 213);
    	ruta.AddEstacion("Demo Parada 079", 212);
    	ruta.AddEstacion("Demo Parada 080", 211);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 083", 208);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 086", 205);
    	ruta.AddEstacion("Demo Parada 087", 204);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D04", "Recorrido ficticio D04", true);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 092", 602);
    	ruta.AddEstacion("Demo Parada 093", 603);
    	ruta.AddEstacion("Demo Parada 094", 604);
    	ruta.AddEstacion("Demo Parada 095", 605);
    	ruta.AddEstacion("Demo Parada 096", 606);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 098", 608);
    	ruta.AddEstacion("Demo Parada 099", 609);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 102", 612);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 103", 410);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 105", 408);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 107", 406);
    	ruta.AddEstacion("Demo Parada 108", 405);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 060", 313);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 042", 105);
    	ruta.AddEstacion("Demo Parada 043", 104);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D05", "Recorrido ficticio D05", true);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	ruta.AddEstacion("Demo Parada 070", 303);
    	ruta.AddEstacion("Demo Parada 069", 304);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 063", 310);
    	ruta.AddEstacion("Demo Parada 062", 311);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 042", 105);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D06", "Recorrido ficticio D06", false);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 031", 116);

    	ruta= this.AddRuta("D07", "Recorrido ficticio D07", false);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 076", 704);
    	ruta.AddEstacion("Demo Parada 075", 703);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 073", 701);

    	ruta= this.AddRuta("D08", "Recorrido ficticio D08", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 075", 703);
    	ruta.AddEstacion("Demo Parada 076", 704);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");


    	ruta= this.AddRuta("D09", "Recorrido ficticio D09", false);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 029", 2);

    	ruta= this.AddRuta("D10", "Recorrido ficticio D10", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 045", 102);

    	ruta= this.AddRuta("D11", "Recorrido ficticio D11", true);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 075", 703);
    	ruta.AddEstacion("Demo Parada 076", 704);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D12", "Recorrido ficticio D12", true);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 052", 710);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 042", 105);
    	ruta.AddEstacion("Demo Parada 043", 104);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D13", "Recorrido ficticio D13", false);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 045", 102);

    	ruta= this.AddRuta("D14", "Recorrido ficticio D14", false);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 047", 705);

    	ruta= this.AddRuta("D15", "Recorrido ficticio D15", false);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 045", 102);

    	ruta= this.AddRuta("D16", "Recorrido ficticio D16", false);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);

    	ruta= this.AddRuta("D17", "Recorrido ficticio D17", false);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 055", 713);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 047", 705);

    	ruta= this.AddRuta("D18", "Recorrido ficticio D18", false);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D19", "Recorrido ficticio D19", false);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 047", 705);

    	ruta= this.AddRuta("D20", "Recorrido ficticio D20", false);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 073", 701);

    	ruta= this.AddRuta("D21", "Recorrido ficticio D21", false);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 113", 802);

    	ruta= this.AddRuta("D22", "Recorrido ficticio D22", true);
    	ruta.AddEstacion("Demo Parada 113", 802);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");


    	ruta= this.AddRuta("D23", "Recorrido ficticio D23", true);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 004", 504);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 009", 509);
    	ruta.AddEstacion("Demo Parada 014", 514);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");


    	ruta= this.AddRuta("D24", "Recorrido ficticio D24", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 008", 508);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D25", "Recorrido ficticio D25", true);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 095", 605);
    	ruta.AddEstacion("Demo Parada 096", 606);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 103", 410);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 107", 406);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 042", 105);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D26", "Recorrido ficticio D26", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 092", 602);
    	ruta.AddEstacion("Demo Parada 093", 603);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 042", 105);

    	ruta= this.AddRuta("D27", "Recorrido ficticio D27", false);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	ruta.AddEstacion("Demo Parada 042", 105);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 093", 603);
    	ruta.AddEstacion("Demo Parada 092", 602);
    	ruta.AddEstacion("Demo Parada 091", 601);

    	ruta= this.AddRuta("D28", "Recorrido ficticio D28", true);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 092", 602);
    	ruta.AddEstacion("Demo Parada 093", 603);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 102", 612);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 105", 408);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 114", 401);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D29", "Recorrido ficticio D29", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 092", 602);
    	ruta.AddEstacion("Demo Parada 095", 605);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 099", 609);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D30", "Recorrido ficticio D30", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 013", 513);
    	ruta.AddEstacion("Demo Parada 014", 514);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D31", "Recorrido ficticio D31", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D32", "Recorrido ficticio D32", false);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 087", 204);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 078", 213);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 035", 112);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 043", 104);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D33", "Recorrido ficticio D33", false);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	
    	ruta= this.AddRuta("D34", "Recorrido ficticio D34", false);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 103", 410);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 094", 604);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 073", 701);

    	ruta= this.AddRuta("D35", "Recorrido ficticio D35", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 094", 604);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 103", 410);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 109", 404);

    	ruta= this.AddRuta("D36", "Recorrido ficticio D36", true);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 049", 707);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 083", 208);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D37", "Recorrido ficticio D37", false);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 083", 208);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 019", 12);

    	ruta= this.AddRuta("D38", "Recorrido ficticio D38", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 075", 703);
    	ruta.AddEstacion("Demo Parada 076", 704);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 058", 716);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 060", 313);
    	ruta.AddEstacion("Demo Parada 077", 214);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D39", "Recorrido ficticio D39", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 002", 502);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D40", "Recorrido ficticio D40", true);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 006", 506);
    	ruta.AddEstacion("Demo Parada 008", 508);
    	ruta.AddEstacion("Demo Parada 010", 510);
    	ruta.AddEstacion("Demo Parada 013", 513);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 080", 211);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 086", 205);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D41", "Recorrido ficticio D41", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 098", 608);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 108", 405);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 079", 212);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D42", "Recorrido ficticio D42", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 095", 605);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 099", 609);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 107", 406);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 077", 214);
    	ruta.AddEstacion("Demo Parada 078", 213);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D43", "Recorrido ficticio D43", true);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 107", 406);
    	ruta.AddEstacion("Demo Parada 108", 405);
    	ruta.AddEstacion("Demo Parada 109", 404);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 077", 214);
    	ruta.AddEstacion("Demo Parada 078", 213);
    	ruta.AddEstacion("Demo Parada 079", 212);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D40", "Recorrido ficticio D40", false);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 077", 214);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 113", 802);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D44", "Recorrido ficticio D44", false);
    	ruta.AddEstacion("Demo Parada 113", 802);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 077", 214);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);

    	ruta= this.AddRuta("D45", "Recorrido ficticio D45", true);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 055", 713);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 060", 313);
    	ruta.AddEstacion("Demo Parada 064", 309);
    	ruta.AddEstacion("Demo Parada 065", 308);
    	ruta.AddEstacion("Demo Parada 066", 307);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 069", 304);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D46", "Recorrido ficticio D46", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 072", 301);

    	ruta= this.AddRuta("D47", "Recorrido ficticio D47", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 057", 715);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 070", 303);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D48", "Recorrido ficticio D48", false);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	ruta.AddEstacion("Demo Parada 070", 303);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 029", 2);

    	ruta= this.AddRuta("D49", "Recorrido ficticio D49", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 021", 10);
    	ruta.AddEstacion("Demo Parada 022", 9);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D50", "Recorrido ficticio D50", true);
    	ruta.AddEstacion("Demo Parada 113", 802);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 023", 8);
    	ruta.AddEstacion("Demo Parada 060", 313);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 064", 309);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D51", "Recorrido ficticio D51", true);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 048", 706);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 055", 713);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 069", 304);
    	ruta.AddEstacion("Demo Parada 071", 302);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D52", "Recorrido ficticio D52", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 094", 604);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 103", 410);
    	ruta.AddEstacion("Demo Parada 105", 408);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 062", 311);
    	ruta.AddEstacion("Demo Parada 064", 309);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D53", "Recorrido ficticio D53", true);
    	ruta.AddEstacion("Demo Parada 105", 408);
    	ruta.AddEstacion("Demo Parada 106", 407);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 062", 311);
    	ruta.AddEstacion("Demo Parada 064", 309);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D54", "Recorrido ficticio D54", true);
    	ruta.AddEstacion("Demo Parada 113", 802);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 016", 516);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 007", 507);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 002", 502);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D55", "Recorrido ficticio D55", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 009", 509);
    	ruta.AddEstacion("Demo Parada 011", 511);
    	ruta.AddEstacion("Demo Parada 012", 512);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 017", 517);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D56", "Recorrido ficticio D56", true);
    	ruta.AddEstacion("Demo Parada 115", 1001);
    	ruta.AddEstacion("Demo Parada 116", 1003);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 117", 913);
    	ruta.AddEstacion("Demo Parada 118", 911);
    	ruta.AddEstacion("Demo Parada 119", 908);
    	ruta.AddEstacion("Demo Parada 120", 907);
    	ruta.AddEstacion("Demo Parada 121", 906);
    	ruta.AddEstacion("Demo Parada 122", 903);
    	ruta.AddEstacion("Demo Parada 123", 902);
    	ruta.AddEstacion("Demo Parada 124", 901);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D57", "Recorrido ficticio D57", false);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 067", 306);
    	ruta.AddEstacion("Demo Parada 065", 308);
    	ruta.AddEstacion("Demo Parada 064", 309);
    	ruta.AddEstacion("Demo Parada 063", 310);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D58", "Recorrido ficticio D58", false);
    	ruta.AddEstacion("Demo Parada 073", 701);
    	ruta.AddEstacion("Demo Parada 074", 702);
    	ruta.AddEstacion("Demo Parada 075", 703);
    	ruta.AddEstacion("Demo Parada 076", 704);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 031", 116);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 037", 110);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 040", 107);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D59", "Recorrido ficticio D59", false);
    	ruta.AddEstacion("Demo Parada 047", 705);
    	ruta.AddEstacion("Demo Parada 050", 708);
    	ruta.AddEstacion("Demo Parada 051", 709);
    	ruta.AddEstacion("Demo Parada 053", 711);
    	ruta.AddEstacion("Demo Parada 054", 712);
    	ruta.AddEstacion("Demo Parada 055", 713);
    	ruta.AddEstacion("Demo Parada 056", 714);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 032", 115);
    	ruta.AddEstacion("Demo Parada 033", 114);
    	ruta.AddEstacion("Demo Parada 036", 111);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 044", 103);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D60", "Recorrido ficticio D60", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 095", 605);
    	ruta.AddEstacion("Demo Parada 096", 606);
    	ruta.AddEstacion("Demo Parada 097", 607);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 105", 408);
    	ruta.AddEstacion("Demo Parada 107", 406);
    	ruta.AddEstacion("Demo Parada 111", 402);
    	ruta.AddEstacion("Demo Parada 114", 401);
    	ruta.AddEstacion("Demo Parada 034", 113);
    	ruta.AddEstacion("Demo Parada 038", 109);
    	ruta.AddEstacion("Demo Parada 039", 108);
    	ruta.AddEstacion("Demo Parada 041", 106);
    	ruta.AddEstacion("Demo Parada 045", 102);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D61", "Recorrido ficticio D61", false);
    	ruta.AddEstacion("Demo Parada 001", 501);
    	ruta.AddEstacion("Demo Parada 003", 503);
    	ruta.AddEstacion("Demo Parada 005", 505);
    	ruta.AddEstacion("Demo Parada 009", 509);
    	ruta.AddEstacion("Demo Parada 013", 513);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 016", 516);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 024", 7);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 028", 3);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 079", 212);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 089", 202);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D62", "Recorrido ficticio D62", false);
    	ruta.AddEstacion("Demo Parada 091", 601);
    	ruta.AddEstacion("Demo Parada 092", 602);
    	ruta.AddEstacion("Demo Parada 095", 605);
    	ruta.AddEstacion("Demo Parada 098", 608);
    	ruta.AddEstacion("Demo Parada 100", 610);
    	ruta.AddEstacion("Demo Parada 101", 611);
    	ruta.AddEstacion("Demo Parada 102", 612);
    	ruta.AddEstacion("Demo Parada 015", 515);
    	ruta.AddEstacion("Demo Parada 104", 409);
    	ruta.AddEstacion("Demo Parada 107", 406);
    	ruta.AddEstacion("Demo Parada 108", 405);
    	ruta.AddEstacion("Demo Parada 110", 403);
    	ruta.AddEstacion("Demo Parada 080", 211);
    	ruta.AddEstacion("Demo Parada 081", 210);
    	ruta.AddEstacion("Demo Parada 082", 209);
    	ruta.AddEstacion("Demo Parada 084", 207);
    	ruta.AddEstacion("Demo Parada 085", 206);
    	ruta.AddEstacion("Demo Parada 088", 203);
    	ruta.AddEstacion("Demo Parada 090", 201);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D63", "Recorrido ficticio D63", false);
    	ruta.AddEstacion("Demo Parada 113", 802);
    	ruta.AddEstacion("Demo Parada 112", 801);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 019", 12);
    	ruta.AddEstacion("Demo Parada 025", 6);
    	ruta.AddEstacion("Demo Parada 026", 5);
    	ruta.AddEstacion("Demo Parada 027", 4);
    	ruta.AddEstacion("Demo Parada 029", 2);
    	ruta.AddEstacion("Demo Parada 030", 1);
    	ruta.AddEstacion("Demo Parada 061", 312);
    	ruta.AddEstacion("Demo Parada 066", 307);
    	ruta.AddEstacion("Demo Parada 068", 305);
    	ruta.AddEstacion("Demo Parada 069", 304);
    	ruta.AddEstacion("Demo Parada 071", 302);
    	ruta.AddEstacion("Demo Parada 072", 301);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");

    	ruta= this.AddRuta("D64", "Recorrido ficticio D64", false);
    	ruta.AddEstacion("Demo Parada 115", 1001);
    	ruta.AddEstacion("Demo Parada 116", 1003);
    	ruta.AddEstacion("Demo Parada 059", 14);
    	ruta.AddEstacion("Demo Parada 018", 13);
    	ruta.AddEstacion("Demo Parada 020", 11);
    	ruta.AddEstacion("Demo Parada 117", 913);
    	ruta.AddEstacion("Demo Parada 118", 911);
    	ruta.AddEstacion("Demo Parada 119", 908);
    	ruta.AddEstacion("Demo Parada 120", 907);
    	ruta.AddEstacion("Demo Parada 121", 906);
    	ruta.AddEstacion("Demo Parada 122", 903);
    	ruta.AddEstacion("Demo Parada 123", 902);
    	ruta.AddEstacion("Demo Parada 124", 901);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");
    	
    	ruta= this.AddRuta("D65", "Recorrido ficticio D65", true);
    	ruta.AddEstacion("Demo Parada 125", 1010);
    	ruta.AddEstacion("Demo Parada 126", 1005);
    	ruta.AddEstacion("Demo Parada 127", 1004);
    	ruta.AddEstacion("Demo Parada 116", 1003);
    	ruta.AddEstacion("Demo Parada 128", 1002);
    	ruta.AddEstacion("Demo Parada 115", 1001);
    	this.AddRutaInvertida("D66R", ruta, "Recorrido ficticio inverso");
    }
    
    public static String GetFechaRutas() {
    	
    	return "15/09/2012";
    } 
}