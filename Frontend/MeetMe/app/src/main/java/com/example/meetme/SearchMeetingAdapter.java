package com.example.meetme;

import static com.example.meetme.api.apiClientFactory.GetAvailabilityApi;
import static com.example.meetme.api.apiClientFactory.GetMeetingApi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetme.api.SlimCallback;
import com.example.meetme.model.Availability;
import com.example.meetme.model.Meeting;
import com.example.meetme.model.MeetingShadow;
import com.example.meetme.ui.ViewMeetingPage;

import java.util.ArrayList;
import java.util.List;

/**
 * MeetingAdapter class allows us to manipulate Array lists of meeting objects to display in RecyclerViews
 */
public class SearchMeetingAdapter extends RecyclerView.Adapter<SearchMeetingAdapter.ViewHolder> {

    /**
     * context to provide for view
     */
    private Context context;

    /**
     *list of meeting objects to be instantiated
     */
    private ArrayList<MeetingShadow> meetings;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * textview object
         */
        private final TextView textView;
        private ImageView imageView;
        public Context context;
        public View view;

        /**
         * constructor for viewholder with given context and view
         * @param context
         * @param view
         */
        public ViewHolder(Context context, View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            this.context = context;
            this.view = view;
            textView = (TextView) view.findViewById(R.id.name);
            imageView = (ImageView) view.findViewById(R.id.search_badge);
            imageView.setVisibility(View.VISIBLE);

            //imageView.setImageDrawable(wrong);


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

        public ImageView getImageView(){ return imageView; }
    }

    /**
     * Initialize the dataset of the Adapter.
     */
    public SearchMeetingAdapter(Context context, ArrayList<MeetingShadow> meetings) {
        this.context = context;
        this.meetings = meetings;
    }

    /**
     * This allows the layout manager to create new views
     * @param viewGroup
     * @param viewType
     * @return new view object
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.search_card_meeting, viewGroup, false);

        return new ViewHolder(context, view);
    }

    /**
     * allows the layout manager to replace the contents of a view
     * @param viewHolder
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(meetings.get(position).getName());

        Drawable correct = ResourcesCompat.getDrawable(viewHolder.view.getResources(), R.drawable.correct, null);
        Drawable wrong = ResourcesCompat.getDrawable(viewHolder.view.getResources(), R.drawable.wrong, null);

        final int[] startEndTimes = new int[2];
        final GlobalClass globalVariable = (GlobalClass) context;

        //get username of current client
        final String username = globalVariable.getName();
        //imageView.setImageDrawable(wrong);
        GetMeetingApi().getMeetingByName(viewHolder.getTextView().getText().toString()).enqueue(new SlimCallback<MeetingShadow>(m->{
            int[] times = m.getStartEndOfMeeting();
            startEndTimes[0] = times[0];
            startEndTimes[1] = times[1];
            Log.i("SearchmeetingAdapter", "MeetingName: " + viewHolder.getTextView().getText().toString() +
                    ". Start: " + startEndTimes[0] + ". End: " + startEndTimes[1]);
            GetAvailabilityApi().getAvailability(username).enqueue(new SlimCallback<Availability>(availability ->{
                if (availability.isAvailableDuringRange(startEndTimes[0], startEndTimes[1])){
                    viewHolder.getImageView().setImageDrawable(correct);
                }
                else{
                    viewHolder.getImageView().setImageDrawable(wrong);
                }
            }));
        }));


        viewHolder.getImageView().setVisibility(View.VISIBLE);
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
