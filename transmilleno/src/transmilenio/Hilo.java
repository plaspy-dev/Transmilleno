import javax.microedition.lcdui.*;

public class Hilo implements Runnable {
    
    transmilenio m_pParent;
    Gauge m_pGauge;
    
    Hilo(transmilenio padre, Gauge gauge) {

        m_pParent=padre;        
        m_pGauge= gauge;
        new Thread(this).start();
    }
    
    public void run() {
         
        m_pParent.GetResultados(m_pGauge);
    }
}