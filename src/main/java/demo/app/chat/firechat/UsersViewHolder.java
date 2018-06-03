package demo.app.chat.firechat;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView username;
    public TextView knownLangs;

    public UsersViewHolder(View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.usernameTextField);
        knownLangs = itemView.findViewById(R.id.knownLangsTextField);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent goToChat = new Intent(view.getContext().getApplicationContext(), ChatActivity.class);
        goToChat.putExtra("receiverUsername", this.username.getText().toString());
        view.getContext().startActivity(goToChat);
    }
}