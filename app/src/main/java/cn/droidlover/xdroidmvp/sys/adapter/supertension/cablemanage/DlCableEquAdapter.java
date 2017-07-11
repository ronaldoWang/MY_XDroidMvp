package cn.droidlover.xdroidmvp.sys.adapter.supertension.cablemanage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.base.SimpleRecAdapter;
import cn.droidlover.xdroidmvp.kit.KnifeKit;
import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.model.supertension.cablemanage.DlCableEquModel;

/**
 * Created by haoxi on 2017/7/11.
 */

public class DlCableEquAdapter extends SimpleRecAdapter<DlCableEquModel.DlCableEqu, DlCableEquAdapter.ViewHolder> {
    public static final int TAG_VIEW = 0;

    public DlCableEquAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder newViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_main;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DlCableEquModel.DlCableEqu item = data.get(position);
        holder.tvItem.setText(item.getLineName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getRecItemClick() != null) {
                    getRecItemClick().onItemClick(position, item, TAG_VIEW, holder);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                getRecItemClick().onItemLongClick(position, item, TAG_VIEW, holder);
                return false;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_tv_item)
        TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            KnifeKit.bind(this, itemView);
        }
    }
}
