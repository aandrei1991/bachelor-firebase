package demo.app.chat.firechat;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    Conversation conversation = new Conversation();
    ArrayList<Message> messages = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    User user;
    Message message;
    private EditText input;
    private FloatingActionButton floatingActionButton;
    private RecyclerView messagesView;
    private FirebaseRecyclerAdapter<Message, MessageViewHolder> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        input = (EditText) findViewById(R.id.inputChatMessageEditText);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.btnSend);
        messagesView = (RecyclerView) findViewById(R.id.messagesView);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabase.child("users").orderByChild("name").startAt(getIntent().getStringExtra("receiverUsername")).limitToFirst(1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        user = dataSnapshot.getValue(User.class);
                        if (user.getUID() != null) {
                            String messageText = input.getText().toString();
                            message = new Message(FirebaseAuth.getInstance().getCurrentUser().getUid(), user.getUID(), messageText);
                            messages.add(message);
                        } else
                            Toast.makeText(getApplicationContext(), "Error reading the user!", Toast.LENGTH_SHORT).show();
                        // Read the input field and push a new instance
                        // of ChatMessage to the Firebase database

                        conversation.setMessages(messages);
                        conversation.setConversationId(FirebaseAuth.getInstance().getCurrentUser().getUid() + ":" + message.getReceiverUID());
                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("conversations")
                                .child(conversation.getConversationId())
                                .push()
                                .setValue(conversation);

                        Query conversationQuery = FirebaseDatabase.getInstance().getReference().child("conversations").orderByChild(conversation.getConversationId()).limitToFirst(1);
                        adapter = new FirebaseRecyclerAdapter<Message, MessageViewHolder>(Message.class, R.layout.message, MessageViewHolder.class, conversationQuery) {
                            @Override
                            protected void populateViewHolder(MessageViewHolder viewHolder, Message model, int position) {
                                // Get references to the views of message.xml
                                viewHolder.messageText.setText(model.getText());
                                viewHolder.messageTimestamp.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.getTimestamp()));
                                viewHolder.senderUsername.setText(model.getSenderUID());
                            }
                        };
                        messagesView.setAdapter(adapter);
                        // Clear the input
                        input.setText("");
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

        });


    }

    private void displayChatMessages() {

    }
}
