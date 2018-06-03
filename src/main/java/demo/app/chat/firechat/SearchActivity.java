package demo.app.chat.firechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ImageButton searchButton;

    private RecyclerView searchResults;

    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        userDatabase = FirebaseDatabase.getInstance().getReference();

        searchEditText = (EditText) findViewById(R.id.searchBarTextEdit);
        searchButton = (ImageButton) findViewById(R.id.searchBtn);

        searchResults = (RecyclerView) findViewById(R.id.searchResults);
        searchResults.setHasFixedSize(true);
        searchResults.setLayoutManager(new LinearLayoutManager(this));


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = searchEditText.getText().toString();
                firebaseUserSearch(searchText);
            }
        });
    }

    private void firebaseUserSearch(String keyword) {

        Query firebaseSearchQuery = userDatabase.child("users").orderByChild("email").startAt(keyword).endAt(keyword + "\uf8ff");
        FirebaseRecyclerAdapter<User, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<User, UsersViewHolder>(
                User.class,
                R.layout.list_layout,
                UsersViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, User model, int position) {
                viewHolder.username.setText(model.getName());
                StringBuilder sb = new StringBuilder();
                sb.append("Known langs: ");
                for (int i = 0; i < model.getSpokenLangs().size(); i++) {
                    sb.append(model.getSpokenLangs().get(i));
                    if (i < model.getSpokenLangs().size() - 1) {
                        sb.append(", ");
                    }
                }
                viewHolder.knownLangs.setText(sb);
            }
        };
        searchResults.setAdapter(firebaseRecyclerAdapter);
    }
}
