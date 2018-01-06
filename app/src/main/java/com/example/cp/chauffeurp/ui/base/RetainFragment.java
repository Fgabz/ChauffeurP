package com.example.cp.chauffeurp.ui.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import timber.log.Timber;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

/**
 * See http://bcorso.github.io/android-retain-fragment/
 * Create a Generic RetainFragment<T> that can be used to store any type of data.
 */
public class RetainFragment<T> extends Fragment {
    public T data;
    // Find/Create in FragmentManager
    public static <T> RetainFragment<T> findOrCreate(FragmentManager fm, String tag) {
        RetainFragment<T> retainFragment = (RetainFragment<T>) fm.findFragmentByTag(tag);

        if (retainFragment == null) {
            retainFragment = new RetainFragment<>();
            fm.beginTransaction()
                    .add(retainFragment, tag)
                    .commitAllowingStateLoss();
        }

        return retainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Keeps this Fragment alive during configuration changes
        setRetainInstance(true);
    }

    @Override
    public void onDestroy() {
        Timber.d("Retain Fragment destroyed");
        //Need to call this listener because "Don't Keep activities" is setting the data to null
        super.onDestroy();
    }

    // Remove from FragmentManager
    public void remove(FragmentManager fm) {
        if (!fm.isDestroyed()) {
            fm.beginTransaction()
                    .remove(this)
                    .commitAllowingStateLoss();
            data = null;
        }
    }
}
