package sdg.transmilleno.free;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.webkit.WebSettings;
import com.google.ads.*;
import org.apache.cordova.*;

public class TransmillenoFreeActivity extends DroidGap {
    /** Called when the activity is first created. */
	private AdView adView;
	ProgressBar progreso;
	boolean agregado=false;
	LinearLayout content=null;
	String MY_AD_UNIT_ID="a00000000000001";
	
	protected Timer timer;
	
	// Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
        	adView.loadAd(new AdRequest());
        }
    };
    
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
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        progreso= new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
    	progreso.setIndeterminate(true);
        
    	super.init();
        super.appView.clearCache(true);
        super.appView.clearHistory();
          
        View viewt= findViewById(android.R.id.content);
        
        // Crear la adView    
        adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        
        if(viewt instanceof FrameLayout) {
        	FrameLayout frame= (FrameLayout)viewt;
            content= (LinearLayout)frame.getChildAt(0);
        }
        
        if(content!=null) {
        	content.addView(adView);    
        	// Iniciar una solicitud genérica para cargarla con un anuncio    
        	adView.loadAd(new AdRequest());
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
        // super.loadUrl("https://transmilleno.example.invalid/?f=a");
        // setContentView(R.layout.main);
    }
    
    @Override
    public Object onMessage(String mensaje, Object objeto) {
    	
	   try {
	   	if(mensaje=="onPageFinished") {
	   		// spinnerStop();
	   		if(content!=null && agregado) {
	   			content.removeView(progreso);
	   		}
	   		spinnerStop();
	   		agregado=false;
	   	} else if(mensaje=="onPageStarted") {
	   		// spinnerStart("Cargando", "Por favor espere");
	   		if(!agregado && content!=null)
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
			// super.loadUrl("https://pruebas-transmilleno.example.invalid/web/");
	        // super.loadUrl("https://transmilleno.example.invalid/");
			return true;
		} else if(item.getTitle()=="Salir") {
			finish();
			return true;
		}
	    return super.onOptionsItemSelected(item);
	}
    
    @Override  
    public void onDestroy() {
    	adView.destroy();    
    	super.onDestroy();  
    }
}