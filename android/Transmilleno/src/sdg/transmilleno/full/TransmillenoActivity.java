package sdg.transmilleno.full;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TransmillenoActivity extends Activity {
    private static final String ROOT = "https://transmilleno.example.invalid/";
    private WebView viewer;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        viewer = new WebView(this);
        viewer.getSettings().setJavaScriptEnabled(true);
        viewer.getSettings().setDomStorageEnabled(true);
        viewer.getSettings().setAllowFileAccess(false);
        viewer.getSettings().setAllowContentAccess(false);
        viewer.getSettings().setBlockNetworkLoads(true);
        viewer.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return !url.startsWith(ROOT);
            }

        });
        setContentView(viewer);
        showHome();
    }

    private void showHome() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getAssets().open("offline.html"), "UTF-8"))) {
            StringBuilder document = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                document.append(line).append('\n');
            }
            viewer.loadDataWithBaseURL(ROOT, document.toString(), "text/html", "UTF-8", null);
        } catch (IOException missing) {
            android.util.Log.e("Transmilleno", "Offline navigator is missing from the APK", missing);
            viewer.loadData("No se pudo abrir el visor local.", "text/plain", "UTF-8");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, Menu.NONE, "Inicio");
        menu.add(Menu.NONE, 2, Menu.NONE, "Salir");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            showHome();
            return true;
        }
        if (item.getItemId() == 2) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (viewer.canGoBack()) {
            viewer.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
