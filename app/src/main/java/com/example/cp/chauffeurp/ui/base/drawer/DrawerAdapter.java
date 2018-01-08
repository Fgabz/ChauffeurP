package com.example.cp.chauffeurp.ui.base.drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cp.chauffeurp.R;
import com.example.cp.chauffeurp.data.model.Address;

import java.util.List;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private List<Address> addressList;
    private LayoutInflater layoutInflater;

    public DrawerAdapter(Context context, List<Address> addressList) {
        this.addressList = addressList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_list_drawer, parent, false);
        return new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public interface OnClickItemDrawer {
        void onClickItem(String text, int position);
    }

    public class DrawerViewHolder extends RecyclerView.ViewHolder {
        public DrawerViewHolder(View itemView) {
            super(itemView);
        }
    }
}
