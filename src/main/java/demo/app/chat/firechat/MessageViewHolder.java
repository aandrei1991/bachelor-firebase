package demo.app.chat.firechat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class MessageViewHolder extends RecyclerView.ViewHolder {

    public TextView senderUsername;
    public TextView messageText;
    public TextView messageTimestamp;

    public MessageViewHolder(View itemView) {
        super(itemView);

    }

}
