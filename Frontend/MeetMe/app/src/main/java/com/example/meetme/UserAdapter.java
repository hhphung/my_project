package com.example.meetme;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.model.User;
import com.example.meetme.ui.AddParticipantsPage;
import com.example.meetme.ui.DashboardPage;

import java.util.ArrayList;

/**
 * UserAdapter class allows us to manipulate Array lists of user objects to display in RecyclerViews
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    /**
     * ArrayList of User objects
     */
    private ArrayList<String> users;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * textView object
         */
        private final TextView textView;


        /**
         * constructor for new viewHolder
         * @param view
         */
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            textView = (TextView) view.findViewById(R.id.name);

        }

        /**
         *
         * @return textView from given object
         */
        public TextView getTextView() {
            return textView;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public UserAdapter(ArrayList<String> users) {
        this.users = users;
    }

    /**
     * This allows the layout manager to create new views
     * @param viewGroup
     * @param viewType
     * @return new view object
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_user, viewGroup, false);

        return new ViewHolder(view);
    }

    /**
     * allows the layout manager to replace the contents of a view
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(users.get(position));
    }

    /**
     * Allows layout manager to see size of dataset
     * @return size of dataset in arraylist
     */
    @Override
    public int getItemCount() {
        return users.size();
    }
}
