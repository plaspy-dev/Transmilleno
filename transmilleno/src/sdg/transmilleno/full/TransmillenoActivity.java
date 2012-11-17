package sdg.transmilleno.full;

import org.apache.cordova.*;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class TransmillenoActivity extends DroidGap {
    /** Called when the activity is first created. */
	ProgressBar progreso;
	boolean agregado=false;
	LinearLayout content=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        
        progreso= new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
    	progreso.setIndeterminate(true);
        
    	super.init();
        super.appView.clearCache(true);
        super.appView.clearHistory();
        
        View viewt= findViewById(android.R.id.content);
        
        if(viewt instanceof FrameLayout) {
        	FrameLayout frame= (FrameLayout)viewt;
            content= (LinearLayout)frame.getChildAt(0);
        }
        
        progreso.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // content.addView(progreso);
        
        super.setStringProperty("loadingPageDialog", "Cargando,Por favor espere");
        super.setStringProperty("loadingDialog", "Cargando,Por favor espere");
        
        super.loadUrl("file:///android_asset/www/m/m.html");
        
        WebSettings ws = super.appView.getSettings();
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        // setContentView(R.layout.main);
    }
    @Override
    public Object onMessage(String mensaje, Object objeto) {
    	
	   try {
	   	if(mensaje=="onPageFinished") {
	   		if(content!=null && agregado) {
	   			content.removeView(progreso);
	   		}
	   		spinnerStop();
	   		agregado=false;
	   	} else if(mensaje=="onPageStarted") {
	   		// spinnerStart("Cargando", "Por favor espere");
	   		if(content!=null && !agregado)
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
			super.loadUrl("file:///android_asset/www/m/m.html");
			return true;
		} else if(item.getTitle()=="Salir") {
			finish();
			return true;
		}
	    return super.onOptionsItemSelected(item);
	}
}