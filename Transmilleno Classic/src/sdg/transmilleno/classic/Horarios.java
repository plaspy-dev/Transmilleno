/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sdg.transmilleno.classic;

/**
 *
 * @author JuHU
 */
public class Horarios {

    protected int m_desde;
    protected int m_hasta;
    
    Horarios(int desde, int hasta) {
        m_desde= desde;
        m_hasta= hasta;
    }
    public int GetDesde() {
        return m_desde;
    }
    public int GetHasta() {
        return m_hasta;
    }
}
