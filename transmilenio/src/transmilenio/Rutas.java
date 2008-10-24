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
    
    public Vector GetRutaID(String ubicacion, String destino) {

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
    
    public Ruta AddRuta(String ruta, String descripcion) {
        Ruta pRuta= new Ruta(ruta,descripcion);
        m_vRutas.addElement(pRuta);
        return pRuta;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original) {
        Ruta nueva= new Ruta(ruta, original.GetDescripcion());
        Vector estaciones= original.GetEstaciones();
        Vector ids= original.GetIDEstaciones();
        for(int i=estaciones.size()-1; i>=0; i--) {
            nueva.AddEstacion(estaciones.elementAt(i).toString(), ids.elementAt(i).toString());
        }
        if(original.GetHorarioLV()!=null)
            nueva.SetHorarioLV(original.GetHorarioLV().GetDesde(), original.GetHorarioLV().GetHasta());
        if(original.GetHorarioS()!=null)
            nueva.SetHorarioS(original.GetHorarioS().GetDesde(), original.GetHorarioS().GetHasta());
        if(original.GetHorarioF()!=null)
            nueva.SetHorarioF(original.GetHorarioF().GetDesde(), original.GetHorarioF().GetHasta());
	m_vRutas.addElement(nueva);
        return nueva;
    }
    
    public Rutas() {
        m_vRutas= new Vector();
        Ruta ruta=AddRuta("D01", "Recorrido ficticio D01");
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(5, 22);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 003", "104");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 010", "312");
        ruta.AddEstacion("Demo Parada 011", "311");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 013", "305");
        ruta.AddEstacion("Demo Parada 014", "304");
        ruta.AddEstacion("Demo Parada 015", "303");
        ruta.AddEstacion("Demo Parada 016", "301");

        ruta= AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D02", "Recorrido ficticio D02");
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 017", "115");
        
        ruta= AddRuta("D03", "Recorrido ficticio D03");
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 024", "704");
        ruta.AddEstacion("Demo Parada 025", "703");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        ruta= AddRuta("D04", "Recorrido ficticio D04");
        ruta.SetHorarioLV(4, 9);
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 024", "704");
        ruta.AddEstacion("Demo Parada 025", "703");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D05", "Recorrido ficticio D05");
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D06", "Recorrido ficticio D06");
        ruta.SetHorarioLV(9, 23);
        ruta.SetHorarioS(5, 23);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 024", "704");
        ruta.AddEstacion("Demo Parada 025", "703");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D07", "Recorrido ficticio D07");
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioS(6, 22);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 035", "103");
        ruta.AddEstacion("Demo Parada 003", "104");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 037", "3");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 038", "11");
        ruta.AddEstacion("Demo Parada 039", "14");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 041", "710");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 042", "705");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D08", "Recorrido ficticio D08");
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Demo Parada 042", "705");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 001", "101");

        ruta= AddRuta("D09", "Recorrido ficticio D09");
        ruta.SetHorarioLV(6, 9);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 042", "705");

        ruta= AddRuta("D10", "Recorrido ficticio D10");
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Demo Parada 042", "705");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 001", "101");

        ruta= AddRuta("D11", "Recorrido ficticio D11");
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioS(5, 9);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 048", "709");
        ruta.AddEstacion("Demo Parada 042", "705");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D12", "Recorrido ficticio D12");
        ruta.SetHorarioLV(5, 9);
        ruta.SetHorarioLV(16, 20);
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 049", "713");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 042", "705");

        ruta= AddRuta("D13", "Recorrido ficticio D13");
        ruta.SetHorarioLV(6, 9);
        ruta.SetHorarioLV(17, 20);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 035", "103");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 050", "111");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 051", "213");
        ruta.AddEstacion("Demo Parada 052", "209");
        ruta.AddEstacion("Demo Parada 053", "204");
        ruta.AddEstacion("Demo Parada 054", "203");
        ruta.AddEstacion("Demo Parada 055", "202");
        ruta.AddEstacion("Demo Parada 056", "201");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D14", "Recorrido ficticio D14");
        ruta.SetHorarioLV(5, 9);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 057", "801");
        ruta.AddEstacion("Demo Parada 058", "802");

        ruta= AddRuta("D15", "Recorrido ficticio D15");
        ruta.SetHorarioLV(9, 22);
        ruta.SetHorarioS(7, 21);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 057", "801");
        ruta.AddEstacion("Demo Parada 058", "802");
        
        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D16", "Recorrido ficticio D16");
        ruta.SetHorarioLV(5, 22);
        ruta.SetHorarioLV(5, 22);
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 059", "514");
        ruta.AddEstacion("Demo Parada 060", "509");
        ruta.AddEstacion("Demo Parada 061", "505");
        ruta.AddEstacion("Demo Parada 062", "504");
        ruta.AddEstacion("Demo Parada 063", "503");
        ruta.AddEstacion("Demo Parada 064", "501");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D17", "Recorrido ficticio D17");
        // VOY AQUI
        // y esto genera error
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 065", "508");
        ruta.AddEstacion("Demo Parada 061", "505");
        ruta.AddEstacion("Demo Parada 063", "503");
        ruta.AddEstacion("Demo Parada 064", "501");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D18", "Recorrido ficticio D18");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 003", "104");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 050", "111");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 067", "406");
        ruta.AddEstacion("Demo Parada 068", "407");
        ruta.AddEstacion("Demo Parada 069", "409");
        ruta.AddEstacion("Demo Parada 070", "410");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 072", "610");
        ruta.AddEstacion("Demo Parada 073", "606");
        ruta.AddEstacion("Demo Parada 074", "605");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D19", "Recorrido ficticio D19");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 003", "104");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 050", "111");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 076", "607");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D20", "Recorrido ficticio D20");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 077", "401");
        ruta.AddEstacion("Demo Parada 078", "402");
        ruta.AddEstacion("Demo Parada 079", "404");
        ruta.AddEstacion("Demo Parada 068", "407");
        ruta.AddEstacion("Demo Parada 080", "408");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 081", "612");
        ruta.AddEstacion("Demo Parada 082", "611");
        ruta.AddEstacion("Demo Parada 076", "607");
        ruta.AddEstacion("Demo Parada 083", "603");
        ruta.AddEstacion("Demo Parada 084", "602");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D21", "Recorrido ficticio D21");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 085", "207");
        ruta.AddEstacion("Demo Parada 086", "208");
        ruta.AddEstacion("Demo Parada 087", "210");
        ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 038", "11");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 039", "14");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 088", "707");
        ruta.AddEstacion("Demo Parada 042", "705");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D22", "Recorrido ficticio D22");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 055", "202");
        ruta.AddEstacion("Demo Parada 089", "206");
        ruta.AddEstacion("Demo Parada 052", "209");
        ruta.AddEstacion("Demo Parada 087", "210");
        ruta.AddEstacion("Demo Parada 090", "214");
        ruta.AddEstacion("Demo Parada 091", "314");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 037", "3");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 092", "716");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 024", "704");
        ruta.AddEstacion("Demo Parada 025", "703");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D23", "Recorrido ficticio D23");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 055", "202");
        ruta.AddEstacion("Demo Parada 054", "203");
        ruta.AddEstacion("Demo Parada 093", "205");
        ruta.AddEstacion("Demo Parada 087", "210");
        ruta.AddEstacion("Demo Parada 094", "211");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 095", "510");
        ruta.AddEstacion("Demo Parada 065", "508");
        ruta.AddEstacion("Demo Parada 096", "506");
        ruta.AddEstacion("Demo Parada 061", "505");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D24", "Recorrido ficticio D24");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 055", "202");
        ruta.AddEstacion("Demo Parada 054", "203");
        ruta.AddEstacion("Demo Parada 089", "206");
        ruta.AddEstacion("Demo Parada 052", "209");
        ruta.AddEstacion("Demo Parada 087", "210");
        ruta.AddEstacion("Demo Parada 097", "212");
        ruta.AddEstacion("Demo Parada 051", "213");
        ruta.AddEstacion("Demo Parada 090", "214");
        ruta.AddEstacion("Demo Parada 078", "402");
        ruta.AddEstacion("Demo Parada 098", "403");
        ruta.AddEstacion("Demo Parada 079", "404");
        ruta.AddEstacion("Demo Parada 099", "405");
        ruta.AddEstacion("Demo Parada 067", "406");
        ruta.AddEstacion("Demo Parada 068", "407");
        ruta.AddEstacion("Demo Parada 069", "409");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 082", "611");
        ruta.AddEstacion("Demo Parada 100", "609");
        ruta.AddEstacion("Demo Parada 101", "608");
        ruta.AddEstacion("Demo Parada 076", "607");
        ruta.AddEstacion("Demo Parada 074", "605");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D25", "Recorrido ficticio D25");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 055", "202");
        ruta.AddEstacion("Demo Parada 054", "203");
        ruta.AddEstacion("Demo Parada 089", "206");
        ruta.AddEstacion("Demo Parada 052", "209");
        ruta.AddEstacion("Demo Parada 087", "210");
        ruta.AddEstacion("Demo Parada 097", "212");
        ruta.AddEstacion("Demo Parada 051", "213");
        ruta.AddEstacion("Demo Parada 090", "214");
        ruta.AddEstacion("Demo Parada 078", "402");
        ruta.AddEstacion("Demo Parada 098", "403");
        ruta.AddEstacion("Demo Parada 079", "404");
        ruta.AddEstacion("Demo Parada 099", "405");
        ruta.AddEstacion("Demo Parada 067", "406");
        ruta.AddEstacion("Demo Parada 068", "407");
        ruta.AddEstacion("Demo Parada 069", "409");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 082", "611");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D26", "Recorrido ficticio D26");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 102", "308");
        ruta.AddEstacion("Demo Parada 103", "309");
        ruta.AddEstacion("Demo Parada 104", "310");
        ruta.AddEstacion("Demo Parada 091", "314");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 049", "713");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D27", "Recorrido ficticio D27");
        ruta.AddEstacion("Demo Parada 027", "701");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 016", "301");

        ruta= AddRuta("D28", "Recorrido ficticio D28");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 015", "303");
        ruta.AddEstacion("Demo Parada 013", "305");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D29", "Recorrido ficticio D29");
        ruta.AddEstacion("Demo Parada 064", "501");
        ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 045", "9");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 016", "301");

        ruta= AddRuta("D30", "Recorrido ficticio D30");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 104", "310");
        ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 091", "314");
        ruta.AddEstacion("Demo Parada 044", "8");
        ruta.AddEstacion("Demo Parada 038", "11");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 057", "801");
        ruta.AddEstacion("Demo Parada 058", "802");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D31", "Recorrido ficticio D31");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 106", "302");
        ruta.AddEstacion("Demo Parada 107", "304");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 037", "3");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 049", "713");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 108", "706");
        ruta.AddEstacion("Demo Parada 042", "705");
        
        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D32", "Recorrido ficticio D32");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 104", "310");
        ruta.AddEstacion("Demo Parada 010", "312");
        ruta.AddEstacion("Demo Parada 078", "402");
        ruta.AddEstacion("Demo Parada 098", "403");
        ruta.AddEstacion("Demo Parada 068", "407");
        ruta.AddEstacion("Demo Parada 080", "408");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 072", "610");
        ruta.AddEstacion("Demo Parada 076", "607");
        ruta.AddEstacion("Demo Parada 109", "604");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D33", "Recorrido ficticio D33");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 104", "310");
        ruta.AddEstacion("Demo Parada 010", "312");
        ruta.AddEstacion("Demo Parada 078", "402");
        ruta.AddEstacion("Demo Parada 098", "403");
        ruta.AddEstacion("Demo Parada 068", "407");
        ruta.AddEstacion("Demo Parada 080", "408");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D34", "Recorrido ficticio D34");
        ruta.AddEstacion("Demo Parada 064", "501");
        ruta.AddEstacion("Demo Parada 110", "502");
        ruta.AddEstacion("Demo Parada 061", "505");
        ruta.AddEstacion("Demo Parada 111", "507");
        ruta.AddEstacion("Demo Parada 112", "513");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 113", "516");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 057", "801");
        ruta.AddEstacion("Demo Parada 058", "802");
        
        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D35", "Recorrido ficticio D35");
        ruta.AddEstacion("Demo Parada 064", "501");
        ruta.AddEstacion("Demo Parada 063", "503");
        ruta.AddEstacion("Demo Parada 061", "505");
        ruta.AddEstacion("Demo Parada 060", "509");
        ruta.AddEstacion("Demo Parada 114", "511");
        ruta.AddEstacion("Demo Parada 115", "512");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 116", "517");
        ruta.AddEstacion("Demo Parada 030", "13");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D36", "Recorrido ficticio D36");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 011", "311");
        ruta.AddEstacion("Demo Parada 104", "310");
        ruta.AddEstacion("Demo Parada 103", "309");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 016", "301");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D37", "Recorrido ficticio D37");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 036", "106");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 029", "109");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 024", "704");
        ruta.AddEstacion("Demo Parada 025", "703");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D38", "Recorrido ficticio D38");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 002", "102");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 006", "110");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 037", "3");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 038", "11");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 039", "14");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 049", "713");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 042", "705");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D39", "Recorrido ficticio D39");
        ruta.AddEstacion("Demo Parada 001", "101");
        ruta.AddEstacion("Demo Parada 004", "105");
        ruta.AddEstacion("Demo Parada 005", "107");
        ruta.AddEstacion("Demo Parada 028", "108");
        ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 078", "402");
        ruta.AddEstacion("Demo Parada 067", "406");
        ruta.AddEstacion("Demo Parada 080", "408");
        ruta.AddEstacion("Demo Parada 069", "409");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 082", "611");
        ruta.AddEstacion("Demo Parada 072", "610");
        ruta.AddEstacion("Demo Parada 076", "607");
        ruta.AddEstacion("Demo Parada 073", "606");
        ruta.AddEstacion("Demo Parada 074", "605");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D40", "Recorrido ficticio D40");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 055", "202");
        ruta.AddEstacion("Demo Parada 054", "203");
        ruta.AddEstacion("Demo Parada 052", "209");
        ruta.AddEstacion("Demo Parada 087", "210");
        ruta.AddEstacion("Demo Parada 097", "212");
        ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 037", "3");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 043", "7");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 113", "516");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 112", "513");
        ruta.AddEstacion("Demo Parada 060", "509");
        ruta.AddEstacion("Demo Parada 061", "505");
        ruta.AddEstacion("Demo Parada 063", "503");
        ruta.AddEstacion("Demo Parada 064", "501");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D41", "Recorrido ficticio D41");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 041", "710");
        ruta.AddEstacion("Demo Parada 014", "304");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
        ruta.AddEstacion("Demo Parada 021","5");
        ruta.AddEstacion("Demo Parada 022","6");
        ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 057", "801");
        ruta.AddEstacion("Demo Parada 058", "802");        
        
        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D42", "Recorrido ficticio D42");
        ruta.AddEstacion("Demo Parada 016", "301");
        ruta.AddEstacion("Demo Parada 012", "306");
        ruta.AddEstacion("Demo Parada 105", "307");
        ruta.AddEstacion("Demo Parada 018", "1");
    	ruta.AddEstacion("Demo Parada 019","2");
        ruta.AddEstacion("Demo Parada 020","4");
    	ruta.AddEstacion("Demo Parada 021","5");
    	ruta.AddEstacion("Demo Parada 022","6");
    	ruta.AddEstacion("Demo Parada 043", "7");
    	ruta.AddEstacion("Demo Parada 045", "9");
    	ruta.AddEstacion("Demo Parada 046", "10");
        ruta.AddEstacion("Demo Parada 064", "501");
        
        
        ruta= AddRuta("D43", "Recorrido ficticio D43");
        ruta.AddEstacion("Demo Parada 056", "201");
        ruta.AddEstacion("Demo Parada 054", "203");
        ruta.AddEstacion("Demo Parada 017", "115");
        
        ruta= AddRuta("D44", "Recorrido ficticio D44");
        ruta.AddEstacion("Demo Parada 056", "201");
    	ruta.AddEstacion("Demo Parada 085", "207");
    	ruta.AddEstacion("Demo Parada 086", "208");
    	ruta.AddEstacion("Demo Parada 087", "210");
    	ruta.AddEstacion("Demo Parada 066", "313");
        ruta.AddEstacion("Demo Parada 018", "1");
        ruta.AddEstacion("Demo Parada 022","6");
    	ruta.AddEstacion("Demo Parada 043", "7");
    	ruta.AddEstacion("Demo Parada 044", "8");
    	ruta.AddEstacion("Demo Parada 038", "11");
    	ruta.AddEstacion("Demo Parada 047", "12");        
        
        ruta= AddRuta("D45", "Recorrido ficticio D45");
        
        ruta.AddEstacion("Demo Parada 001", "101");
    	ruta.AddEstacion("Demo Parada 002", "102");
    	ruta.AddEstacion("Demo Parada 035", "103");
    	ruta.AddEstacion("Demo Parada 003", "104");
    	ruta.AddEstacion("Demo Parada 004", "105");
    	ruta.AddEstacion("Demo Parada 036", "106");
    	ruta.AddEstacion("Demo Parada 005", "107");
    	ruta.AddEstacion("Demo Parada 028", "108");
    	ruta.AddEstacion("Demo Parada 029", "109");
    	ruta.AddEstacion("Demo Parada 006", "110");
    	ruta.AddEstacion("Demo Parada 050", "111");
    	ruta.AddEstacion("Demo Parada 007", "112");
    	ruta.AddEstacion("Demo Parada 008", "113");
    	ruta.AddEstacion("Demo Parada 009", "114");
    	ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 018", "1");
    	ruta.AddEstacion("Demo Parada 019","2");
    	ruta.AddEstacion("Demo Parada 037", "3");
    	ruta.AddEstacion("Demo Parada 020","4");
    	ruta.AddEstacion("Demo Parada 021","5");
    	ruta.AddEstacion("Demo Parada 022","6");
    	ruta.AddEstacion("Demo Parada 043", "7");
    	ruta.AddEstacion("Demo Parada 044", "8");
    	ruta.AddEstacion("Demo Parada 045", "9");
    	ruta.AddEstacion("Demo Parada 046", "10");
    	ruta.AddEstacion("Demo Parada 038", "11");
    	ruta.AddEstacion("Demo Parada 047", "12");
        ruta.AddEstacion("Demo Parada 030", "13");
        ruta.AddEstacion("Demo Parada 116", "517");
        ruta.AddEstacion("Demo Parada 113", "516");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 059", "514");
        ruta.AddEstacion("Demo Parada 112", "513");
        ruta.AddEstacion("Demo Parada 115", "512");
        ruta.AddEstacion("Demo Parada 114", "511");
        ruta.AddEstacion("Demo Parada 095", "510");
        ruta.AddEstacion("Demo Parada 060", "509");
        ruta.AddEstacion("Demo Parada 065", "508");
        ruta.AddEstacion("Demo Parada 111", "507");
        ruta.AddEstacion("Demo Parada 096", "506");
        ruta.AddEstacion("Demo Parada 061", "505");
        ruta.AddEstacion("Demo Parada 062", "504");
        ruta.AddEstacion("Demo Parada 063", "503");
        ruta.AddEstacion("Demo Parada 110", "502");
        ruta.AddEstacion("Demo Parada 064", "501");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D46", "Recorrido ficticio D46");
        ruta.AddEstacion("Demo Parada 001", "101");
    	ruta.AddEstacion("Demo Parada 002", "102");
    	ruta.AddEstacion("Demo Parada 035", "103");
    	ruta.AddEstacion("Demo Parada 003", "104");
    	ruta.AddEstacion("Demo Parada 004", "105");
    	ruta.AddEstacion("Demo Parada 036", "106");
    	ruta.AddEstacion("Demo Parada 005", "107");
    	ruta.AddEstacion("Demo Parada 028", "108");
    	ruta.AddEstacion("Demo Parada 029", "109");
    	ruta.AddEstacion("Demo Parada 006", "110");
    	ruta.AddEstacion("Demo Parada 050", "111");
    	ruta.AddEstacion("Demo Parada 007", "112");
        ruta.AddEstacion("Demo Parada 008", "113");
        ruta.AddEstacion("Demo Parada 009", "114");
        ruta.AddEstacion("Demo Parada 017", "115");
        ruta.AddEstacion("Demo Parada 091", "314");
        ruta.AddEstacion("Demo Parada 066", "313");
    	ruta.AddEstacion("Demo Parada 078", "402");
    	ruta.AddEstacion("Demo Parada 098", "403");
    	ruta.AddEstacion("Demo Parada 079", "404");
    	ruta.AddEstacion("Demo Parada 099", "405");
    	ruta.AddEstacion("Demo Parada 067", "406");
    	ruta.AddEstacion("Demo Parada 068", "407");
    	ruta.AddEstacion("Demo Parada 080", "408");
    	ruta.AddEstacion("Demo Parada 069", "409");
    	ruta.AddEstacion("Demo Parada 070", "410");
        ruta.AddEstacion("Demo Parada 071", "613");
        ruta.AddEstacion("Demo Parada 081", "612");
        ruta.AddEstacion("Demo Parada 082", "611");
        ruta.AddEstacion("Demo Parada 072", "610");
        ruta.AddEstacion("Demo Parada 100", "609");
        ruta.AddEstacion("Demo Parada 101", "608");
        ruta.AddEstacion("Demo Parada 076", "607");
        ruta.AddEstacion("Demo Parada 073", "606");
        ruta.AddEstacion("Demo Parada 074", "605");
        ruta.AddEstacion("Demo Parada 109", "604");
        ruta.AddEstacion("Demo Parada 083", "603");
        ruta.AddEstacion("Demo Parada 084", "602");
        ruta.AddEstacion("Demo Parada 075", "601");

        AddRutaInvertida("D49R", ruta);

        ruta= AddRuta("D47", "Recorrido ficticio D47");
        ruta.AddEstacion("Demo Parada 016", "301");
    	ruta.AddEstacion("Demo Parada 106", "302");
    	ruta.AddEstacion("Demo Parada 015", "303");
    	ruta.AddEstacion("Demo Parada 107", "304");
    	ruta.AddEstacion("Demo Parada 013", "305");
    	ruta.AddEstacion("Demo Parada 012", "306");
    	ruta.AddEstacion("Demo Parada 105", "307");
    	ruta.AddEstacion("Demo Parada 102", "308");
    	ruta.AddEstacion("Demo Parada 103", "309");
    	ruta.AddEstacion("Demo Parada 104", "310");
    	ruta.AddEstacion("Demo Parada 011", "311");
    	ruta.AddEstacion("Demo Parada 010", "312");
    	ruta.AddEstacion("Demo Parada 066", "313");
    	ruta.AddEstacion("Demo Parada 091", "314");
        ruta.AddEstacion("Demo Parada 018", "1");
    	ruta.AddEstacion("Demo Parada 019","2");
    	ruta.AddEstacion("Demo Parada 037", "3");
    	ruta.AddEstacion("Demo Parada 020","4");
    	ruta.AddEstacion("Demo Parada 021","5");
    	ruta.AddEstacion("Demo Parada 022","6");
    	ruta.AddEstacion("Demo Parada 043", "7");
    	ruta.AddEstacion("Demo Parada 044", "8");
    	ruta.AddEstacion("Demo Parada 045", "9");
    	ruta.AddEstacion("Demo Parada 046", "10");
    	ruta.AddEstacion("Demo Parada 038", "11");
    	ruta.AddEstacion("Demo Parada 047", "12");
    	ruta.AddEstacion("Demo Parada 030", "13");
    	ruta.AddEstacion("Demo Parada 039", "14");
        ruta.AddEstacion("Demo Parada 092", "716");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 049", "713");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 041", "710");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 088", "707");
        ruta.AddEstacion("Demo Parada 108", "706");
        ruta.AddEstacion("Demo Parada 042", "705");

        AddRutaInvertida("D49R", ruta);
        
        ruta= AddRuta("D48", "Recorrido ficticio D48");
        ruta.AddEstacion("Demo Parada 056", "201");
    	ruta.AddEstacion("Demo Parada 055", "202");
    	ruta.AddEstacion("Demo Parada 054", "203");
    	ruta.AddEstacion("Demo Parada 053", "204");
    	ruta.AddEstacion("Demo Parada 093", "205");
    	ruta.AddEstacion("Demo Parada 089", "206");
    	ruta.AddEstacion("Demo Parada 085", "207");
    	ruta.AddEstacion("Demo Parada 086", "208");
    	ruta.AddEstacion("Demo Parada 052", "209");
    	ruta.AddEstacion("Demo Parada 087", "210");
    	ruta.AddEstacion("Demo Parada 094", "211");
    	ruta.AddEstacion("Demo Parada 097", "212");
    	ruta.AddEstacion("Demo Parada 051", "213");
    	ruta.AddEstacion("Demo Parada 090", "214");
        ruta.AddEstacion("Demo Parada 066", "313");
    	ruta.AddEstacion("Demo Parada 091", "314");
        ruta.AddEstacion("Demo Parada 018", "1");
    	ruta.AddEstacion("Demo Parada 019","2");
    	ruta.AddEstacion("Demo Parada 037", "3");
    	ruta.AddEstacion("Demo Parada 020","4");
    	ruta.AddEstacion("Demo Parada 021","5");
    	ruta.AddEstacion("Demo Parada 022","6");
    	ruta.AddEstacion("Demo Parada 043", "7");
    	ruta.AddEstacion("Demo Parada 044", "8");
    	ruta.AddEstacion("Demo Parada 045", "9");
    	ruta.AddEstacion("Demo Parada 046", "10");
    	ruta.AddEstacion("Demo Parada 038", "11");
    	ruta.AddEstacion("Demo Parada 047", "12");
    	ruta.AddEstacion("Demo Parada 030", "13");
    	ruta.AddEstacion("Demo Parada 039", "14");
        ruta.AddEstacion("Demo Parada 092", "716");
        ruta.AddEstacion("Demo Parada 031", "715");
        ruta.AddEstacion("Demo Parada 032", "714");
        ruta.AddEstacion("Demo Parada 049", "713");
        ruta.AddEstacion("Demo Parada 033", "712");
        ruta.AddEstacion("Demo Parada 040", "711");
        ruta.AddEstacion("Demo Parada 041", "710");
        ruta.AddEstacion("Demo Parada 034", "709");
        ruta.AddEstacion("Demo Parada 023", "708");
        ruta.AddEstacion("Demo Parada 024", "704");
        ruta.AddEstacion("Demo Parada 025", "703");
        ruta.AddEstacion("Demo Parada 026", "702");
        ruta.AddEstacion("Demo Parada 027", "701");
                
        AddRutaInvertida("D49R", ruta);
    }
}