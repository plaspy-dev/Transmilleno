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
    
    public Ruta AddRuta(String ruta, String descripcion) {
        Ruta pRuta= new Ruta(ruta,descripcion);
        m_vRutas.addElement(pRuta);
        return pRuta;
    }
    
    public Ruta AddRutaInvertida(String ruta, Ruta original) {
        Ruta nueva= new Ruta(ruta, original.GetDescripcion());
        Vector estaciones= original.GetEstaciones();
        for(int i=estaciones.size()-1; i>=0; i--)
            nueva.AddEstacion(estaciones.elementAt(i).toString());
	  m_vRutas.addElement(nueva);
        return nueva;
    }
    
    public Rutas() {
        m_vRutas= new Vector();
        Ruta ruta=AddRuta("D01", "Recorrido ficticio D01");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 003");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 010");
        ruta.AddEstacion("Demo Parada 011");
        ruta.AddEstacion("Demo Parada 012");
        ruta.AddEstacion("Demo Parada 013");
        ruta.AddEstacion("Demo Parada 014");
        ruta.AddEstacion("Demo Parada 015");
        ruta.AddEstacion("Demo Parada 016");

        ruta= AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D02", "Recorrido ficticio D02");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 012");
        ruta.AddEstacion("Demo Parada 017");
        
        ruta= AddRuta("D03", "Recorrido ficticio D03");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 024");
        ruta.AddEstacion("Demo Parada 025");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        ruta= AddRuta("D04", "Recorrido ficticio D04");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 024");
        ruta.AddEstacion("Demo Parada 025");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D05", "Recorrido ficticio D05");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D06", "Recorrido ficticio D06");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 024");
        ruta.AddEstacion("Demo Parada 025");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D07", "Recorrido ficticio D07");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 035");
        ruta.AddEstacion("Demo Parada 003");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 037");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 038");
        ruta.AddEstacion("Demo Parada 039");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 041");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 042");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D08", "Recorrido ficticio D08");
        ruta.AddEstacion("Demo Parada 042");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 001");

        ruta= AddRuta("D09", "Recorrido ficticio D09");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 042");

        ruta= AddRuta("D10", "Recorrido ficticio D10");
        ruta.AddEstacion("Demo Parada 042");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 001");

        ruta= AddRuta("D11", "Recorrido ficticio D11");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 048");
        ruta.AddEstacion("Demo Parada 042");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D12", "Recorrido ficticio D12");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 049");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 042");

        ruta= AddRuta("D13", "Recorrido ficticio D13");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 035");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 050");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 051");
        ruta.AddEstacion("Demo Parada 052");
        ruta.AddEstacion("Demo Parada 053");
        ruta.AddEstacion("Demo Parada 054");
        ruta.AddEstacion("Demo Parada 055");
        ruta.AddEstacion("Demo Parada 056");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D14", "Recorrido ficticio D14");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 057");
        ruta.AddEstacion("Demo Parada 058");

        ruta= AddRuta("D15", "Recorrido ficticio D15");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 057");
        ruta.AddEstacion("Demo Parada 058");
        
        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D16", "Recorrido ficticio D16");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 059");
        ruta.AddEstacion("Demo Parada 060");
        ruta.AddEstacion("Demo Parada 061");
        ruta.AddEstacion("Demo Parada 062");
        ruta.AddEstacion("Demo Parada 063");
        ruta.AddEstacion("Demo Parada 064");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D17", "Recorrido ficticio D17");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 065");
        ruta.AddEstacion("Demo Parada 061");
        ruta.AddEstacion("Demo Parada 063");
        ruta.AddEstacion("Demo Parada 064");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D18", "Recorrido ficticio D18");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 003");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 050");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 066");
        ruta.AddEstacion("Demo Parada 067");
        ruta.AddEstacion("Demo Parada 068");
        ruta.AddEstacion("Demo Parada 069");
        ruta.AddEstacion("Demo Parada 070");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 072");
        ruta.AddEstacion("Demo Parada 073");
        ruta.AddEstacion("Demo Parada 074");
        ruta.AddEstacion("Demo Parada 075");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D19", "Recorrido ficticio D19");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 003");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 050");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 076");
        ruta.AddEstacion("Demo Parada 077");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D20", "Recorrido ficticio D20");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 078");
        ruta.AddEstacion("Demo Parada 079");
        ruta.AddEstacion("Demo Parada 080");
        ruta.AddEstacion("Demo Parada 068");
        ruta.AddEstacion("Demo Parada 081");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 082");
        ruta.AddEstacion("Demo Parada 083");
        ruta.AddEstacion("Demo Parada 076");
        ruta.AddEstacion("Demo Parada 084");
        ruta.AddEstacion("Demo Parada 085");
        ruta.AddEstacion("Demo Parada 077");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D21", "Recorrido ficticio D21");
        ruta.AddEstacion("Demo Parada 086");
        ruta.AddEstacion("Demo Parada 087");
        ruta.AddEstacion("Demo Parada 088");
        ruta.AddEstacion("Demo Parada 089");
        ruta.AddEstacion("Demo Parada 066");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 038");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 039");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 090");
        ruta.AddEstacion("Demo Parada 042");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D22", "Recorrido ficticio D22");
        ruta.AddEstacion("Demo Parada 056");
        ruta.AddEstacion("Demo Parada 055");
        ruta.AddEstacion("Demo Parada 091");
        ruta.AddEstacion("Demo Parada 052");
        ruta.AddEstacion("Demo Parada 089");
        ruta.AddEstacion("Demo Parada 092");
        ruta.AddEstacion("Demo Parada 093");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 037");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 094");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 024");
        ruta.AddEstacion("Demo Parada 025");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D23", "Recorrido ficticio D23");
        ruta.AddEstacion("Demo Parada 056");
        ruta.AddEstacion("Demo Parada 055");
        ruta.AddEstacion("Demo Parada 054");
        ruta.AddEstacion("Demo Parada 095");
        ruta.AddEstacion("Demo Parada 089");
        ruta.AddEstacion("Demo Parada 096");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 097");
        ruta.AddEstacion("Demo Parada 098");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 099");
        ruta.AddEstacion("Demo Parada 065");
        ruta.AddEstacion("Demo Parada 100");
        ruta.AddEstacion("Demo Parada 061");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D24", "Recorrido ficticio D24");
        ruta.AddEstacion("Demo Parada 056");
        ruta.AddEstacion("Demo Parada 055");
        ruta.AddEstacion("Demo Parada 054");
        ruta.AddEstacion("Demo Parada 091");
        ruta.AddEstacion("Demo Parada 052");
        ruta.AddEstacion("Demo Parada 089");
        ruta.AddEstacion("Demo Parada 101");
        ruta.AddEstacion("Demo Parada 051");
        ruta.AddEstacion("Demo Parada 092");
        ruta.AddEstacion("Demo Parada 079");
        ruta.AddEstacion("Demo Parada 102");
        ruta.AddEstacion("Demo Parada 080");
        ruta.AddEstacion("Demo Parada 103");
        ruta.AddEstacion("Demo Parada 104");
        ruta.AddEstacion("Demo Parada 068");
        ruta.AddEstacion("Demo Parada 069");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 105");
        ruta.AddEstacion("Demo Parada 106");
        ruta.AddEstacion("Demo Parada 107");
        ruta.AddEstacion("Demo Parada 076");
        ruta.AddEstacion("Demo Parada 074");
        ruta.AddEstacion("Demo Parada 077");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D25", "Recorrido ficticio D25");
        ruta.AddEstacion("Demo Parada 056");
        ruta.AddEstacion("Demo Parada 055");
        ruta.AddEstacion("Demo Parada 054");
        ruta.AddEstacion("Demo Parada 091");
        ruta.AddEstacion("Demo Parada 052");
        ruta.AddEstacion("Demo Parada 089");
        ruta.AddEstacion("Demo Parada 101");
        ruta.AddEstacion("Demo Parada 051");
        ruta.AddEstacion("Demo Parada 092");
        ruta.AddEstacion("Demo Parada 079");
        ruta.AddEstacion("Demo Parada 102");
        ruta.AddEstacion("Demo Parada 080");
        ruta.AddEstacion("Demo Parada 103");
        ruta.AddEstacion("Demo Parada 104");
        ruta.AddEstacion("Demo Parada 068");
        ruta.AddEstacion("Demo Parada 069");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 105");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D26", "Recorrido ficticio D26");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 012");
        ruta.AddEstacion("Demo Parada 108");
        ruta.AddEstacion("Demo Parada 109");
        ruta.AddEstacion("Demo Parada 110");
        ruta.AddEstacion("Demo Parada 093");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 049");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D27", "Recorrido ficticio D27");
        ruta.AddEstacion("Demo Parada 027");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 111");
        ruta.AddEstacion("Demo Parada 016");

        ruta= AddRuta("D28", "Recorrido ficticio D28");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 015");
        ruta.AddEstacion("Demo Parada 013");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 111");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D29", "Recorrido ficticio D29");
        ruta.AddEstacion("Demo Parada 064");
        ruta.AddEstacion("Demo Parada 046");
        ruta.AddEstacion("Demo Parada 045");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 111");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 016");

        ruta= AddRuta("D30", "Recorrido ficticio D30");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 110");
        ruta.AddEstacion("Demo Parada 066");
        ruta.AddEstacion("Demo Parada 093");
        ruta.AddEstacion("Demo Parada 044");
        ruta.AddEstacion("Demo Parada 038");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 057");
        ruta.AddEstacion("Demo Parada 058");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D31", "Recorrido ficticio D31");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 113");
        ruta.AddEstacion("Demo Parada 114");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 037");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 049");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 115");
        ruta.AddEstacion("Demo Parada 042");
        
        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D32", "Recorrido ficticio D32");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 111");
        ruta.AddEstacion("Demo Parada 110");
        ruta.AddEstacion("Demo Parada 010");
        ruta.AddEstacion("Demo Parada 079");
        ruta.AddEstacion("Demo Parada 102");
        ruta.AddEstacion("Demo Parada 068");
        ruta.AddEstacion("Demo Parada 081");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 072");
        ruta.AddEstacion("Demo Parada 076");
        ruta.AddEstacion("Demo Parada 116");
        ruta.AddEstacion("Demo Parada 075");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D33", "Recorrido ficticio D33");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 111");
        ruta.AddEstacion("Demo Parada 110");
        ruta.AddEstacion("Demo Parada 010");
        ruta.AddEstacion("Demo Parada 079");
        ruta.AddEstacion("Demo Parada 102");
        ruta.AddEstacion("Demo Parada 068");
        ruta.AddEstacion("Demo Parada 081");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D34", "Recorrido ficticio D34");
        ruta.AddEstacion("Demo Parada 064");
        ruta.AddEstacion("Demo Parada 117");
        ruta.AddEstacion("Demo Parada 061");
        ruta.AddEstacion("Demo Parada 118");
        ruta.AddEstacion("Demo Parada 119");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 120");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 057");
        ruta.AddEstacion("Demo Parada 058");
        
        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D35", "Recorrido ficticio D35");
        ruta.AddEstacion("Demo Parada 064");
        ruta.AddEstacion("Demo Parada 121");
        ruta.AddEstacion("Demo Parada 061");
        ruta.AddEstacion("Demo Parada 060");
        ruta.AddEstacion("Demo Parada 122");
        ruta.AddEstacion("Demo Parada 123");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 124");
        ruta.AddEstacion("Demo Parada 030");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D36", "Recorrido ficticio D36");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 011");
        ruta.AddEstacion("Demo Parada 110");
        ruta.AddEstacion("Demo Parada 109");
        ruta.AddEstacion("Demo Parada 111");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 016");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D37", "Recorrido ficticio D37");
        ruta.AddEstacion("Demo Parada 125");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 036");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 029");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 024");
        ruta.AddEstacion("Demo Parada 025");
        ruta.AddEstacion("Demo Parada 026");
        ruta.AddEstacion("Demo Parada 027");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D38", "Recorrido ficticio D38");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 002");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 006");
        ruta.AddEstacion("Demo Parada 008");
        ruta.AddEstacion("Demo Parada 009");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 037");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 038");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 039");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 049");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 042");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D39", "Recorrido ficticio D39");
        ruta.AddEstacion("Demo Parada 001");
        ruta.AddEstacion("Demo Parada 004");
        ruta.AddEstacion("Demo Parada 005");
        ruta.AddEstacion("Demo Parada 028");
        ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 066");
        ruta.AddEstacion("Demo Parada 079");
        ruta.AddEstacion("Demo Parada 104");
        ruta.AddEstacion("Demo Parada 126");
        ruta.AddEstacion("Demo Parada 069");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 105");
        ruta.AddEstacion("Demo Parada 072");
        ruta.AddEstacion("Demo Parada 076");
        ruta.AddEstacion("Demo Parada 073");
        ruta.AddEstacion("Demo Parada 074");
        ruta.AddEstacion("Demo Parada 075");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D40", "Recorrido ficticio D40");
        ruta.AddEstacion("Demo Parada 056");
        ruta.AddEstacion("Demo Parada 055");
        ruta.AddEstacion("Demo Parada 054");
        ruta.AddEstacion("Demo Parada 052");
        ruta.AddEstacion("Demo Parada 089");
        ruta.AddEstacion("Demo Parada 101");
        ruta.AddEstacion("Demo Parada 066");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 037");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 043");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 127");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 119");
        ruta.AddEstacion("Demo Parada 060");
        ruta.AddEstacion("Demo Parada 061");
        ruta.AddEstacion("Demo Parada 128");
        ruta.AddEstacion("Demo Parada 064");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D41", "Recorrido ficticio D41");
        ruta.AddEstacion("Demo Parada 016");
        ruta.AddEstacion("Demo Parada 041");
        ruta.AddEstacion("Demo Parada 014");
        ruta.AddEstacion("Demo Parada 112");
        ruta.AddEstacion("Demo Parada 066");
        ruta.AddEstacion("Demo Parada 018");
        ruta.AddEstacion("Demo Parada 019");
        ruta.AddEstacion("Demo Parada 020");
        ruta.AddEstacion("Demo Parada 021");
        ruta.AddEstacion("Demo Parada 022");
        ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 057");
        ruta.AddEstacion("Demo Parada 058");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D42", "Recorrido ficticio D42");
        
        ruta.AddEstacion("Demo Parada 001");
    	ruta.AddEstacion("Demo Parada 002");
    	ruta.AddEstacion("Demo Parada 035");
    	ruta.AddEstacion("Demo Parada 003");
    	ruta.AddEstacion("Demo Parada 004");
    	ruta.AddEstacion("Demo Parada 036");
    	ruta.AddEstacion("Demo Parada 005");
    	ruta.AddEstacion("Demo Parada 028");
    	ruta.AddEstacion("Demo Parada 029");
    	ruta.AddEstacion("Demo Parada 006");
    	ruta.AddEstacion("Demo Parada 050");
    	ruta.AddEstacion("Demo Parada 007");
    	ruta.AddEstacion("Demo Parada 008");
    	ruta.AddEstacion("Demo Parada 009");
    	ruta.AddEstacion("Demo Parada 017");
        ruta.AddEstacion("Demo Parada 018");
    	ruta.AddEstacion("Demo Parada 019");
    	ruta.AddEstacion("Demo Parada 037");
    	ruta.AddEstacion("Demo Parada 020");
    	ruta.AddEstacion("Demo Parada 021");
    	ruta.AddEstacion("Demo Parada 022");
    	ruta.AddEstacion("Demo Parada 043");
    	ruta.AddEstacion("Demo Parada 044");
    	ruta.AddEstacion("Demo Parada 045");
    	ruta.AddEstacion("Demo Parada 046");
    	ruta.AddEstacion("Demo Parada 038");
    	ruta.AddEstacion("Demo Parada 047");
        ruta.AddEstacion("Demo Parada 030");
        ruta.AddEstacion("Demo Parada 124");
        ruta.AddEstacion("Demo Parada 120");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 059");
        ruta.AddEstacion("Demo Parada 119");
        ruta.AddEstacion("Demo Parada 123");
        ruta.AddEstacion("Demo Parada 122");
        ruta.AddEstacion("Demo Parada 099");
        ruta.AddEstacion("Demo Parada 060");
        ruta.AddEstacion("Demo Parada 065");
        ruta.AddEstacion("Demo Parada 118");
        ruta.AddEstacion("Demo Parada 100");
        ruta.AddEstacion("Demo Parada 061");
        ruta.AddEstacion("Demo Parada 062");
        ruta.AddEstacion("Demo Parada 063");
        ruta.AddEstacion("Demo Parada 117");
        ruta.AddEstacion("Demo Parada 064");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D43", "Recorrido ficticio D43");
        ruta.AddEstacion("Demo Parada 001");
    	ruta.AddEstacion("Demo Parada 002");
    	ruta.AddEstacion("Demo Parada 035");
    	ruta.AddEstacion("Demo Parada 003");
    	ruta.AddEstacion("Demo Parada 004");
    	ruta.AddEstacion("Demo Parada 036");
    	ruta.AddEstacion("Demo Parada 005");
    	ruta.AddEstacion("Demo Parada 028");
    	ruta.AddEstacion("Demo Parada 029");
    	ruta.AddEstacion("Demo Parada 006");
    	ruta.AddEstacion("Demo Parada 050");
    	ruta.AddEstacion("Demo Parada 007");
        ruta.AddEstacion("Demo Parada 078");
    	ruta.AddEstacion("Demo Parada 079");
    	ruta.AddEstacion("Demo Parada 102");
    	ruta.AddEstacion("Demo Parada 080");
    	ruta.AddEstacion("Demo Parada 103");
    	ruta.AddEstacion("Demo Parada 104");
    	ruta.AddEstacion("Demo Parada 068");
    	ruta.AddEstacion("Demo Parada 081");
    	ruta.AddEstacion("Demo Parada 069");
    	ruta.AddEstacion("Demo Parada 070");
        ruta.AddEstacion("Demo Parada 071");
        ruta.AddEstacion("Demo Parada 082");
        ruta.AddEstacion("Demo Parada 105");
        ruta.AddEstacion("Demo Parada 072");
        ruta.AddEstacion("Demo Parada 106");
        ruta.AddEstacion("Demo Parada 107");
        ruta.AddEstacion("Demo Parada 076");
        ruta.AddEstacion("Demo Parada 073");
        ruta.AddEstacion("Demo Parada 074");
        ruta.AddEstacion("Demo Parada 116");
        ruta.AddEstacion("Demo Parada 084");
        ruta.AddEstacion("Demo Parada 085");
        ruta.AddEstacion("Demo Parada 075");

        AddRutaInvertida("D46R", ruta);

        ruta= AddRuta("D44", "Recorrido ficticio D44");
        ruta.AddEstacion("Demo Parada 016");
    	ruta.AddEstacion("Demo Parada 113");
    	ruta.AddEstacion("Demo Parada 015");
    	ruta.AddEstacion("Demo Parada 114");
    	ruta.AddEstacion("Demo Parada 013");
    	ruta.AddEstacion("Demo Parada 012");
    	ruta.AddEstacion("Demo Parada 111");
    	ruta.AddEstacion("Demo Parada 108");
    	ruta.AddEstacion("Demo Parada 109");
    	ruta.AddEstacion("Demo Parada 110");
    	ruta.AddEstacion("Demo Parada 011");
    	ruta.AddEstacion("Demo Parada 010");
    	ruta.AddEstacion("Demo Parada 066");
    	ruta.AddEstacion("Demo Parada 093");
        ruta.AddEstacion("Demo Parada 018");
    	ruta.AddEstacion("Demo Parada 019");
    	ruta.AddEstacion("Demo Parada 037");
    	ruta.AddEstacion("Demo Parada 020");
    	ruta.AddEstacion("Demo Parada 021");
    	ruta.AddEstacion("Demo Parada 022");
    	ruta.AddEstacion("Demo Parada 043");
    	ruta.AddEstacion("Demo Parada 044");
    	ruta.AddEstacion("Demo Parada 045");
    	ruta.AddEstacion("Demo Parada 046");
    	ruta.AddEstacion("Demo Parada 038");
    	ruta.AddEstacion("Demo Parada 047");
    	ruta.AddEstacion("Demo Parada 030");
    	ruta.AddEstacion("Demo Parada 039");
        ruta.AddEstacion("Demo Parada 094");
        ruta.AddEstacion("Demo Parada 031");
        ruta.AddEstacion("Demo Parada 032");
        ruta.AddEstacion("Demo Parada 049");
        ruta.AddEstacion("Demo Parada 033");
        ruta.AddEstacion("Demo Parada 040");
        ruta.AddEstacion("Demo Parada 041");
        ruta.AddEstacion("Demo Parada 034");
        ruta.AddEstacion("Demo Parada 023");
        ruta.AddEstacion("Demo Parada 090");
        ruta.AddEstacion("Demo Parada 115");
        ruta.AddEstacion("Demo Parada 042");

        AddRutaInvertida("D46R", ruta);
        
        ruta= AddRuta("D45", "Recorrido ficticio D45");
        ruta.AddEstacion("Demo Parada 056");
    	ruta.AddEstacion("Demo Parada 055");
    	ruta.AddEstacion("Demo Parada 054");
    	ruta.AddEstacion("Demo Parada 053");
    	ruta.AddEstacion("Demo Parada 095");
    	ruta.AddEstacion("Demo Parada 091");
    	ruta.AddEstacion("Demo Parada 087");
    	ruta.AddEstacion("Demo Parada 088");
    	ruta.AddEstacion("Demo Parada 052");
    	ruta.AddEstacion("Demo Parada 089");
    	ruta.AddEstacion("Demo Parada 096");
    	ruta.AddEstacion("Demo Parada 101");
    	ruta.AddEstacion("Demo Parada 051");
    	ruta.AddEstacion("Demo Parada 092");
        ruta.AddEstacion("Demo Parada 066");
    	ruta.AddEstacion("Demo Parada 093");
        ruta.AddEstacion("Demo Parada 018");
    	ruta.AddEstacion("Demo Parada 019");
    	ruta.AddEstacion("Demo Parada 037");
    	ruta.AddEstacion("Demo Parada 020");
    	ruta.AddEstacion("Demo Parada 021");
    	ruta.AddEstacion("Demo Parada 022");
    	ruta.AddEstacion("Demo Parada 043");
    	ruta.AddEstacion("Demo Parada 044");
    	ruta.AddEstacion("Demo Parada 045");
    	ruta.AddEstacion("Demo Parada 046");
    	ruta.AddEstacion("Demo Parada 038");
    	ruta.AddEstacion("Demo Parada 047");
    	ruta.AddEstacion("Demo Parada 030");
    	ruta.AddEstacion("Demo Parada 039");
        
        AddRutaInvertida("D46R", ruta);
    }
}