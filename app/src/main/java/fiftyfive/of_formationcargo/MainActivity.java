package fiftyfive.of_formationcargo;

import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fiftyfive.cargo.Cargo;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MultiDex.install(this);

        // ADDING GTM

        TagManager tagManager = TagManager.getInstance(this);

        // Modify the log level of the logger to print out not only
        // warning and error messages, but also verbose, debug, info messages.
        tagManager.setVerboseLoggingEnabled(true);

        // Calls an API method in google play services, the result object is stored in a PendingResult
        // The ContainerHolder will be available from the returned PendingResult as soon as one of the following happens:
        // -> a saved container is loaded, or if there is no saved container,
        // -> a network container is loaded or a network error occurs, or
        // -> a timeout occurs
        PendingResult<ContainerHolder> pending =
                tagManager.loadContainerPreferNonDefault("GTM-56H2QV",  // change here with your own container ID
                        R.raw.gtm_container);                           // change here with your own container resource

        // The onResult method will be called as soon as one of the following happens: (callBack)
        //     1. a saved container is loaded
        //     2. if there is no saved container, a network container is loaded
        //     3. the request times out. The example below uses a constant to manage the timeout period.
        pending.setResultCallback(new ResultCallback<ContainerHolder>() {
            @Override
            // onResult() is called when the result is ready
            public void onResult(ContainerHolder containerHolder) {
                // Sets the container in the ContainerHolderSingleton
                ContainerHolderSingleton.setContainerHolder(containerHolder);
                // returns if unsuccessful
                if (!containerHolder.getStatus().isSuccess()) {
                    return;
                }

                // manually refresh the container for the demo (can be done each 15min or no-op)
                containerHolder.refresh();
                ContainerHolderSingleton.setContainerHolder(containerHolder);

            }
        }, 5, TimeUnit.SECONDS);


        //INit CArgo

        Cargo cargo;

        Cargo.init(this.getApplication(), ContainerHolderSingleton.getContainerHolder().getContainer());
        cargo = Cargo.getInstance();

        //Register Handlers
        if (cargo != null){
            cargo.registerHandlers();
        }
        else{
            Log.w("MainActivity", "Please instantiate cargo before calling its methods");
        }


        // Get the DataLayer
        DataLayer dataLayer = TagManager.getInstance(this).getDataLayer();


        //Push a GA Event
        dataLayer.pushEvent("fireGA", DataLayer.mapOf("eventName", "testEventGA",
                                                        "eventCategory", "testCategory",
                                                        "eventAction", "testAction",
                                                        "eventLabel", "testLabel"));
    }

}

