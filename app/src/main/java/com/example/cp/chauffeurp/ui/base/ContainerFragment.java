package com.example.cp.chauffeurp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import timber.log.Timber;

/**
 * ContainFragment<T> that contains the RetainFragment<T>
 * and handles it's lifecycle events
 * <p>
 * The ContainerFragment maintains an up-to-date reference to the RetainFragment in onViewCreated()
 * by using RetainFragment#findOrCreate() --
 * the ContainerFragment is destroyed on configuration changes but the RetainFragment will survive
 * inside of the FragmentManager.
 * <p>
 * In addition, the ContainerFragment keeps track of whether the system was destroyed by the
 * Android OS or the user pressing the back button.
 * If the user presses the back button, this indicates that the user is done with the Container so
 * the RetainFragment resources should be freed. However,
 * if the Container is destroyed by the Android OS,it means that it will eventually need to be
 * recreated
 * (e.g. a configuration change), so the RetainFragment resources should not be destroyed.
 */

public class ContainerFragment<T> extends Fragment {
    // Keeps track if this Fragment is being destroy by System or LoginInfo
    protected boolean destroyedBySystem;
    private RetainFragment<T> retainFragment;
    // Used as a unique tag (as long as not using multiple ContainerFragments with same type)
    private String tag = getClass().getCanonicalName();

    public ContainerFragment() {
    }

    //If called, to do it before onViewCreated(View view, @Nullable Bundle savedInstanceState)
    protected void setTag(String tag) {
        this.tag = tag;
    }

    // Convenience method to get data.
    public T getData() {
        return retainFragment.data;
    }

    // Convenience method to set data.
    public void setData(T data) {
        retainFragment.data = data;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Find or Create a RetainFragment to hold the component
        retainFragment = RetainFragment.findOrCreate(getFragManager(), tag);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reset this variable
        destroyedBySystem = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        destroyedBySystem = true;
    }

    @Override
    public void onDestroy() {
        if (destroyedBySystem) {
            Timber.d(this.tag + " destroy by system");
            onDestroyBySystem();
        } else {
            Timber.d(this.tag + " destroy by user");
            onDestroyByUser();
        }
        super.onDestroy();
    }

    // Activity destroyed By LoginInfo. Perform cleanup of retain fragment.
    public void onDestroyByUser() {
        retainFragment.remove(getFragManager());
        retainFragment.data = null;
        retainFragment = null;
        Timber.d("Retain fragment destroyed");
    }

    // Activity destroyed by System. Subclasses can override this if needed.
    public void onDestroyBySystem() {
    }

    //Retain Fragment destroyed. Subclasses can override this if needed.
    public void retainFragmentDestroyed() {
    }

    public FragmentManager getFragManager() {
        return getActivity().getSupportFragmentManager();
    }
}
