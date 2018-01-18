package com.ruking.frame.library.widget.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruking.frame.library.R;
import com.ruking.frame.library.bean.Choice;
import com.ruking.frame.library.widget.RKDialog;
import com.ruking.frame.library.widget.RKDialogChoiceList;


/**
 * @author Ruking.Cheng
 * @descrilbe 自定义列表
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/12/14 16:28
 */
public class ChoiceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int mCheckedPosition;
    private RKDialog dialog;
    private RKDialogChoiceList rkDialogChoiceList;

    public ChoiceListAdapter(@NonNull RKDialog dialog, @NonNull RKDialogChoiceList rkDialogChoiceList) {
        this.dialog = dialog;
        this.mContext = dialog.getContext();
        this.rkDialogChoiceList = rkDialogChoiceList;
    }


    public void addChoice(@NonNull CharSequence charSequence) {
        rkDialogChoiceList.addChoice(charSequence);
        notifyDataSetChanged();
    }

    public void addChoice(@NonNull Choice choice) {
        rkDialogChoiceList.addChoice(choice);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rk_dialog_item_choice_desc, parent, false);
        return new DescViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DescViewHolder descHolder = (DescViewHolder) holder;
        if (rkDialogChoiceList.getProfile() != null) {
            rkDialogChoiceList.getProfile().setBackgroundProfile(mContext, descHolder.itemLayout);
            rkDialogChoiceList.getProfile().setTextProfile(mContext, descHolder.itemTitle);
            rkDialogChoiceList.getProfile().setTextProfile(mContext, descHolder.itemDescription);
            if (rkDialogChoiceList.getProfile().getTextSize() != -1) {
                descHolder.itemDescription.setTextSize(rkDialogChoiceList.getProfile().getTextSize() * 3 / 4);
            }
        }
        Choice choice = rkDialogChoiceList.getChoices().get(position);
        descHolder.itemTitle.setText(choice.getTitle());
        if (choice.getDescription() != null) {
            descHolder.itemDescription.setVisibility(View.VISIBLE);
            descHolder.itemDescription.setText(choice.getDescription());
        } else {
            descHolder.itemDescription.setVisibility(View.GONE);
        }
        if (!rkDialogChoiceList.isRadioIconHide()) {
            descHolder.itemImag.setVisibility(View.GONE);
        } else {
            boolean isChecked = choice.isChecked();
            if (isChecked) {
                mCheckedPosition = descHolder.getAdapterPosition();
                descHolder.itemImag.setImageResource(rkDialogChoiceList.getImageIconChecked());
            } else {
                descHolder.itemImag.setImageResource(rkDialogChoiceList.getImageIconNormal());
            }
            if (rkDialogChoiceList.getProfile() != null) {
                if (rkDialogChoiceList.getProfile().getItmeColor() != -1) {
                    if (isChecked)
                        descHolder.itemImag.setColorFilter(rkDialogChoiceList.getProfile().getItmeColor());
                    else
                        descHolder.itemImag.setColorFilter(null);
                } else if (rkDialogChoiceList.getProfile().getItmeColorRes() != -1) {
                    if (isChecked)
                        descHolder.itemImag.setColorFilter(ContextCompat.getColor(mContext, rkDialogChoiceList.getProfile().getItmeColorRes()));
                    else
                        descHolder.itemImag.setColorFilter(null);
                }
            }
        }
        if (choice.isClickable())
            descHolder.itemView.setVisibility(View.GONE);
        else
            descHolder.itemView.setVisibility(View.VISIBLE);
        descHolder.itemLayout.setOnClickListener(v -> {
            if (!choice.isClickable())
                return;
            refreshCheckState(descHolder.getAdapterPosition());
            if (rkDialogChoiceList.getOnSingleChoiceSelectListener() != null) {
                rkDialogChoiceList.getOnSingleChoiceSelectListener().onSelect(dialog,
                        choice,
                        descHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (rkDialogChoiceList.getChoices() != null) {
            return rkDialogChoiceList.getChoices().size();
        } else {
            return 0;
        }
    }

    private void refreshCheckState(int position) {
        if (rkDialogChoiceList.isMultiselect()) {
            rkDialogChoiceList.getChoices().get(position).setChecked(
                    !rkDialogChoiceList.getChoices().get(position).isChecked());
            notifyItemChanged(position);
        } else {
            if (mCheckedPosition != position) {
                rkDialogChoiceList.getChoices().get(mCheckedPosition).setChecked(false);
                notifyItemChanged(mCheckedPosition);
            }
            rkDialogChoiceList.getChoices().get(position).setChecked(true);
            notifyItemChanged(position);
        }
    }

    private static class DescViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImag;
        private TextView itemTitle;
        private TextView itemDescription;
        private LinearLayout itemLayout;
        private View itemView;

        @SuppressLint("CutPasteId")
        DescViewHolder(View itemView) {
            super(itemView);
            itemLayout = itemView.findViewById(R.id.item_layout);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemDescription = itemView.findViewById(R.id.item_description);
            itemImag = itemView.findViewById(R.id.item_imag);
            this.itemView = itemView.findViewById(R.id.item_view);
        }
    }

}