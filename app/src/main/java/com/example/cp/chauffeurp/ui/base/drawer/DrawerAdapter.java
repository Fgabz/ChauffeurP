package com.example.cp.chauffeurp.ui.base.drawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cp.chauffeurp.R;
import com.example.cp.chauffeurp.data.model.Address;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fanilogabaud on 07/01/2018.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private List<Address> addressList;
    private LayoutInflater layoutInflater;

    private OnClickItemDrawer callback;

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
        String placeName = addressList.get(position).getSearchField();
        holder.addressView.setText(placeName);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public void setCallback(OnClickItemDrawer callback) {
        this.callback = callback;
    }

    public interface OnClickItemDrawer {
        void onClickItem(Address address);
    }

    public class DrawerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address)
        TextView addressView;

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickItem(addressList.get(getAdapterPosition()));
                }
            }
        };

        public DrawerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(clickListener);
        }
    }
}
