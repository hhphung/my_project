package com.example.meetme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.model.Meeting;
import com.example.meetme.ui.ViewMeetingPage;

import java.util.ArrayList;

/**
 * MeetingAdapter class allows us to manipulate Array lists of meeting objects to display in RecyclerViews
 */
public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    /**
     * context to provide for view
     */
    private Context context;

    /**
     *list of meeting objects to be instantiated
     */
    private ArrayList<Meeting> meetings;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * textview object
         */
        private final TextView textView;

        /**
         * constructor for viewholder with given context and view
         * @param context
         * @param view
         */
        public ViewHolder(Context context, View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    System.out.println(textView.getText().toString());

                    Intent gotoViewMeetingPage = new Intent(view.getContext(), ViewMeetingPage.class);
                    gotoViewMeetingPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    gotoViewMeetingPage.putExtra("meetingName", textView.getText().toString());

                    context.startActivity(gotoViewMeetingPage);
                }
            });
        }

        /**
         *
         * @return given textView object
         */
        public TextView getTextView() {
            return textView;
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public MeetingAdapter(Context context, ArrayList<Meeting> meetings) {
        this.context = context;
        this.meetings = meetings;
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
                .inflate(R.layout.card_meeting, viewGroup, false);

        return new ViewHolder(context, view);
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
        viewHolder.getTextView().setText(meetings.get(position).getName());
    }

    /**
     * Allows layout manager to see size of dataset
     * @return size of dataset in arraylist
     */
    @Override
    public int getItemCount() {
        return meetings.size();
    }
}
