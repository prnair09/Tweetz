package com.rakesh.tweetz.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.rakesh.tweetz.R;
import com.rakesh.tweetz.TweetzApplication;
import com.rakesh.tweetz.TweetzClient;
import com.rakesh.tweetz.helper.DynamicHeightImageView;
import com.rakesh.tweetz.models.Tweet;
import com.rakesh.tweetz.models.User;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rparuthi on 4/2/2017.
 */

public class ComposeTweetFragment extends DialogFragment{
    public interface ComposeTweetListener {
        public void onTweetSuccess(Tweet tweet);
    }

    User currentUser;
    View currentView;

    ImageView ivClose;
    DynamicHeightImageView divComposeUserProfPic;
    EditText etTweet;
    Button btnTweet;
    TextView tvTweetLength;
    TweetzClient tweeterClient;


    private ComposeTweetListener composeTweetHandler;

    public ComposeTweetFragment(){

    }

    public static  ComposeTweetFragment newInstance(User currentUser){

        ComposeTweetFragment composeTweetFragment = new ComposeTweetFragment();

        Bundle args = new Bundle();
        args.putSerializable("currentUser", currentUser);

        composeTweetFragment.setArguments(args);
        return composeTweetFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return  dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        View view = getActivity().getLayoutInflater().inflate(R.layout.compose_tweet_fragment, container);

        ivClose = (ImageView)view.findViewById(R.id.ivClose);
        divComposeUserProfPic =(DynamicHeightImageView)view.findViewById(R.id.ivComposeUserProfilePicture);
        etTweet = (EditText) view.findViewById(R.id.etTweet);
        btnTweet = (Button)view.findViewById(R.id.btnTweet);
        tvTweetLength = (TextView)view.findViewById(R.id.tvTweetLength);

        tweeterClient = TweetzApplication.getRestClient();

        currentView = view;

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTweet();
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View  view, Bundle savedInstance){
        //currentUser = Parcels.unwrap(getArguments().getParcelable("currentUser"));
        currentUser = (User)getArguments().getSerializable("currentUser");

        setProfileImage();

        setTweetTextListener();
    }

    private void setProfileImage() {
        if(currentUser!= null && currentUser.getProfileImageUrl() != null) {
            Picasso.with(getContext()).load(currentUser.getProfileImageUrl())
                    .error(R.drawable.ic_camera)
                    .into(divComposeUserProfPic);
        }
    }

    /**
     * Attaches text change listener while updating the tweet text
     */
    private void setTweetTextListener() {
        tvTweetLength.setTextColor(Color.BLACK);
        etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long length = 0;
                if (s.length() > 140) {
                    tvTweetLength.setTextColor(Color.RED);
                    length = 140 - s.length();
                    btnTweet.setEnabled(false);
                } else {
                    tvTweetLength.setTextColor(Color.BLACK);
                    length = s.length();
                    btnTweet.setEnabled(true);
                }
                if(length == 0){
                    btnTweet.setEnabled(false);
                }

                tvTweetLength.setText(Long.toString(length));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    //Store Activities subscribed to this event
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComposeTweetListener) {
            composeTweetHandler = (ComposeTweetListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ComposeTweetFragment.ComposeTweetListener");
        }
    }


    public void onTweet(){
        String tweetText = etTweet.getText().toString();

        tweeterClient.composeATweet(tweetText, new TextHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                if(responseString != null) {

                    try{
                        JSONObject objResponse = new JSONObject(responseString);

                        Tweet tweet = Tweet.fromJson(objResponse);

                        dismiss();

                        composeTweetHandler.onTweetSuccess(tweet);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {


                Snackbar.make(currentView, "Error occured while composing a new Tweet.",
                        Snackbar.LENGTH_LONG).show();
            }


        });
    }

    public void onClose(){
        dismiss();
    }


    @Override
    public void onResume() {
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), (int) (size.y * 0.80));
        window.setGravity(Gravity.CENTER);
        super.onResume();
    }


}
