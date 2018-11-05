package milab.idc.com.exercise_02;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.FamilyMemberViewHolder> {
    List<FamilyMember> mData;

    public MembersAdapter(List<FamilyMember> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View memberView = inflater.inflate(R.layout.item_member, parent, false);
        FamilyMemberViewHolder memberViewHolder = new FamilyMemberViewHolder(memberView);
        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        FamilyMember familyMember = mData.get(position);

        TextView textView = holder.mTextView;
        textView.setText(familyMember.name);
        textView.setCompoundDrawablesWithIntrinsicBounds(null, familyMember.img, null, null);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class FamilyMemberViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public FamilyMemberViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.member_name);
        }
    }
}
