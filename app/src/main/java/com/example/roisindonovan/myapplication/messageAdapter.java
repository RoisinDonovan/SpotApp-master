package com.example.roisindonovan.myapplication;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.paypal.android.sdk.cy.i;

/**
 * Created by Roisin Donovan on 30/07/2017.
 */

public class messageAdapter extends  RecyclerView.Adapter<messageAdapter.messageViewHolder>{

    private List<messages> mMessageList;
    private FirebaseAuth mAuth;

    public messageAdapter(List<messages> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @Override
    public messageViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout ,parent, false);
        return new messageViewHolder(v);

    }


    public class messageViewHolder extends RecyclerView.ViewHolder{
        public TextView messageText;
        public CircleImageView profileImage;
       // public TextView displayName;

        public messageViewHolder(View view){
            super(view);

            messageText = (TextView)view.findViewById(R.id.message_text_layout);
            profileImage = (CircleImageView) view.findViewById(R.id.message_profile_layout);
          //  displayName = (TextView) view.findViewById(R.id.name_text_layout);
        }
    }
        //setting chat details
    @Override
    public void onBindViewHolder(final messageViewHolder viewHolder, int i) {

        mAuth = FirebaseAuth.getInstance();
        String current_user_id = mAuth.getCurrentUser().getUid();

        messages c = mMessageList.get(i);

        String from_user = c.getFrom();

        if(from_user.equals(current_user_id)){
            viewHolder.messageText.setBackgroundColor(Color.WHITE);
            viewHolder.messageText.setTextColor(Color.BLACK);

        }else {

            viewHolder.messageText.setBackgroundResource(R.drawable.message_background);
            viewHolder.messageText.setTextColor(Color.WHITE);



        //mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);



       // mUserDatabase.addValueEventListener(new ValueEventListener() {

          //  @Override

           // public void onDataChange(DataSnapshot dataSnapshot) {

              //  String name = dataSnapshot.child("name").getValue().toString();

               // String image = dataSnapshot.child("thumb_image").getValue().toString();

               // viewHolder.displayName.setText(name);

               // Picasso.with(viewHolder.profileImage.getContext()).load(image)

                      //  .placeholder(R.drawable.avatar).into(viewHolder.profileImage);

           // }
          //  @Override

           // public void onCancelled(DatabaseError databaseError) {

            }

       // });

        viewHolder.messageText.setText(c.getMessage());

    }

    @Override

    public int getItemCount() {

        return mMessageList.size();

    }







}

