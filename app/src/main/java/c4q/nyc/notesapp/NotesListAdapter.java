package c4q.nyc.notesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import c4q.nyc.notesapp.models.Note;

/**
 * Created by justiceo on 11/23/17.
 */

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NoteViewHolder> {

    private final String TAG = getClass().getName();
    private ArrayList<Note> notesList;
    private View.OnClickListener listener;

    public NotesListAdapter(ArrayList<Note> notes, View.OnClickListener listener) {
        notesList = notes;
        this.listener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_itemview, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note n = notesList.get(position);
        holder.title.setText(n.title);
        if(n.title.isEmpty())
            holder.title.setVisibility(View.GONE);
        holder.title.setTag(n.id);
        holder.body.setText(n.body);
        if(n.body.isEmpty())
            holder.body.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView body;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_item_title);
            body = itemView.findViewById(R.id.note_item_body);
        }
    }
}
