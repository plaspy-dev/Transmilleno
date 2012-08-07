package sdg.transmilleno.full;

import org.apache.cordova.*;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class TransmillenoActivity extends DroidGap {
    /** Called when the activity is first created. */
	ProgressBar progreso;
	boolean agregado=false;
	LinearLayout content;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        
        progreso= new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
    	progreso.setIndeterminate(true);
        
    	super.init();
        super.appView.clearCache(true);
        super.appView.clearHistory();
          
        ViewGroup view= (ViewGroup)getWindow().getDecorView();
        FrameLayout frame= (FrameLayout)view.getChildAt(0);
        content= (LinearLayout)frame.getChildAt(0);
        progreso.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        
        // content.addView(progreso);
        
        super.setStringProperty("loadingPageDialog", "Cargando,Por favor espere");
        super.setStringProperty("loadingDialog", "Cargando,Por favor espere");
        
        super.loadUrl("file:///android_asset/www/m.html");
        // setContentView(R.layout.main);
    }
    @Override
    public Object onMessage(String mensaje, Object objeto) {
    	
	   try {
	   	if(mensaje=="onPageFinished") {
	   		// spinnerStop();
	   		if(agregado)
	   			content.removeView(progreso);
	   		agregado=false;
	   	} else if(mensaje=="onPageStarted") {
	   		// spinnerStart("Cargando", "Por favor espere");
	   		if(!agregado)
	   			content.addView(progreso);
	   		agregado=true;
	   	}
	   } catch(Exception ex) {}
    	return super.onMessage(mensaje, objeto);
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("Inicio");
		menu.add("Salir");
	    return true;
	}
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle()=="Inicio") {
			super.loadUrl("file:///android_asset/www/m.html");
			return true;
		} else if(item.getTitle()=="Salir") {
			finish();
			return true;
		}
	    return super.onOptionsItemSelected(item);
	}
}