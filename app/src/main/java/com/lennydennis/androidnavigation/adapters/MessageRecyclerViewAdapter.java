package com.lennydennis.androidnavigation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lennydennis.androidnavigation.R;
import com.lennydennis.androidnavigation.models.Message;
import com.lennydennis.androidnavigation.models.User;
import com.lennydennis.androidnavigation.util.Messages;

import java.util.ArrayList;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder> implements Filterable {

    private ArrayList<User> mUserArrayList = new ArrayList<>();
    private ArrayList<User> filteredUser = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mListener;

    public MessageRecyclerViewAdapter(ArrayList<User> userArrayList, Context context) {
        mUserArrayList = userArrayList;
        mContext = context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredUser = mUserArrayList;
                } else {
                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User row : mUserArrayList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredUser = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUser;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredUser = (ArrayList<User>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }

    public void setListener(MessageRecyclerViewAdapter.OnItemClickListener listener) {
        this.mListener = listener;
    }

    public Message getItemAt(int position) {
        return new Message(mUserArrayList.get(position), Messages.MESSAGES[position]);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = mUserArrayList.get(position);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mContext)
                .load(user.getProfileImage())
                .circleCrop()
                .apply(requestOptions)
                .into(holder.imageView);

        holder.name.setText(user.getName());
        holder.message.setText(Messages.MESSAGES[position]);


    }

    @Override
    public int getItemCount() {
        return mUserArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView name;
        TextView message;
        TextView reply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.msgs_user_profile);
            name = itemView.findViewById(R.id.msgs_user_name);
            message = itemView.findViewById(R.id.msgs_text);
            reply = itemView.findViewById(R.id.msgs_reply);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }
}
