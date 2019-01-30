package com.example.sprite.dton;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
 * RecyclerView.Adapter
 * RecyclerView.ViewHolder
 */
public class ToneDefinitionAdapter extends
        RecyclerView.Adapter<ToneDefinitionAdapter.ToneDefinitionViewHolder> {
    public ToneDefinitionAdapter(Context mCtx, List<ToneDefinition> myTones) {
        this.mCtx = mCtx;
        this.myTones = myTones;
    }

    private Context mCtx;
    private List<ToneDefinition> myTones;

    @NonNull
    @Override
    public ToneDefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.presets_layout, null);

        return new ToneDefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToneDefinitionViewHolder toneDefinitionViewHolder,
                                 int i) {
        ToneDefinition toneDefinition = myTones.get(i);

        toneDefinitionViewHolder.descriptionView.setText(toneDefinition.getName());
        toneDefinitionViewHolder.frequencyView.setText(Float.toString(
                toneDefinition.getFrequency()));
    }

    @Override
    public int getItemCount() {
        return myTones.size();
    }

    class ToneDefinitionViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionView, frequencyView;

        public ToneDefinitionViewHolder(@NonNull View itemView) {
            super(itemView);

            descriptionView = itemView.findViewById(R.id.descriptionView);
            frequencyView = itemView.findViewById(R.id.frequencyView);
        }
    }
}
