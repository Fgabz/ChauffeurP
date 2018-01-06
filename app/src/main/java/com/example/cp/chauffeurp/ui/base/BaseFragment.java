package com.example.cp.chauffeurp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cp.chauffeurp.ui.base.mvp.BasePresenter;
import com.example.cp.chauffeurp.ui.base.mvp.BaseView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

/**
 * Created by fanilogabaud on 06/01/2018.
 */

public abstract class BaseFragment<P extends BasePresenter> extends ContainerFragment<P> implements BaseView {
    @Inject
    protected P presenter;

    private int layoutId;

    private Unbinder unbinder;

    protected void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(layoutId, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeDependencyInjector();

        if (this.getData() == null) {
            Timber.d("Data is null");
            // Only set if it does not exist already
            this.setData(presenter);
        }
        presenter = this.getData();
        presenter.attachView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        presenter.detachView();

        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyByUser() {
        if (presenter != null) {
            presenter.onDestroyPresenter();
        }
        super.onDestroyByUser();
    }

    @Override
    public void onDestroyBySystem() {
        if (presenter != null) {
            presenter.onDestroyPresenter();
        }
        super.onDestroyBySystem();
    }

    protected abstract void initializeDependencyInjector();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
