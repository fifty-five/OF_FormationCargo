package fiftyfive.of_formationcargo;

/**
 * Created by Francois on 04/10/2016.
 */


import com.google.android.gms.tagmanager.ContainerHolder;

/**
 * Author : Med
 * Created: 20/04/16
 */

public class ContainerHolderSingleton {
    private static ContainerHolder containerHolder;

    /**
     * Utility class; don't instantiate.
     */
    private ContainerHolderSingleton() {
    }


    /* ---------- getter & setter ---------- */

    public static ContainerHolder getContainerHolder() {
        return containerHolder;
    }

    public static void setContainerHolder(ContainerHolder c) {
        containerHolder = c;
    }
}