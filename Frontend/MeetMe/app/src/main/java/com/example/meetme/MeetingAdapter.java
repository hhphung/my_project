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

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Meeting> meetings;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

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

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_meeting, viewGroup, false);

        return new ViewHolder(context, view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(meetings.get(position).getName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return meetings.size();
    }
}
