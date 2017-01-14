package br.com.monitoratec.app.presentation.ui.followers.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.monitoratec.app.R;
import br.com.monitoratec.app.domain.entity.User;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by elder-dell on 2017-01-14.
 */

public class FollowerListAdapter extends RecyclerView.Adapter<FollowerListAdapter.MyViewHolder> {


    private final List<User> mDataSet;

    public FollowerListAdapter(List<User> followers) {
        mDataSet = followers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet.get(position).login);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        @BindView(R.id.txt_follower_name)
        public TextView mTextView;

        public MyViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }

    }


}
