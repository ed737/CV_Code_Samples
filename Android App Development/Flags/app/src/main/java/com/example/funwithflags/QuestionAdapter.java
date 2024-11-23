
/******************************************************************
 * Question adapter, takes an arraylist of questions from
 * selected country and creates a question card from each to
 * display in fragmentD (Recyclerview)
 *****************************************************************/

package com.example.funwithflags;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{

    private ArrayList<Question> questionList;
    QuestionAdapterListener parentActivity;

    // interface for connecting to activity
    public interface QuestionAdapterListener {
        void onQuestionCardClicked(int index);
        boolean getHorizontal();
        int getRowCol();
        int getOrientation();
    }

    // Question adapter constructor
    public QuestionAdapter(Context context, ArrayList<Question> inQuestionList){
        questionList = inQuestionList;
        parentActivity = (QuestionAdapter.QuestionAdapterListener) context;
    }

    // Question ViewHolder constructor
    public class ViewHolder extends RecyclerView.ViewHolder{

        // widget declarations
        TextView questionNumber;
        TextView point;
        TextView penalty;

        // View holder constructor
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            // widget linking
            questionNumber = (TextView) itemView.findViewById(R.id.text_question_number);
            point = (TextView) itemView.findViewById(R.id.point_text);
            penalty = (TextView) itemView.findViewById(R.id.penalty_text);

            // setup click listener for cards
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.onQuestionCardClicked(questionList.indexOf((Question)itemView.getTag()));
                }
            });


        }
    }


    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card_view, parent, false);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams)view.getLayoutParams();

        // This if statement creates the viewholder with a 4:3 aspect ratio for all cards
        // this was done to keep aspect ratio of flags. Repeated for this question adapter.
        // Needs improvement.
        int screenOrientation = parentActivity.getOrientation();


        if(screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (parentActivity.getHorizontal() || parentActivity.getRowCol() == 1) {
                lp.width = ((parent.getMeasuredWidth()) / parentActivity.getRowCol());
                lp.height = (int) (lp.width * 0.75);
            } else {
                lp.height = ((parent.getMeasuredHeight()) / parentActivity.getRowCol());
                lp.width = (int) (lp.height * 1.3); // 1.3 is scaler for 4:3 in this dimension
            }
        }
        else{
            switch(parentActivity.getRowCol()){
                case 1:
                case 2:
                    lp.height = ((parent.getMeasuredHeight()) / parentActivity.getRowCol());
                    lp.width = (int) (lp.height * 1.3); // 1.3 is scaler for 4:3 in this dimension
                break;
                case 3:
                    if(parentActivity.getHorizontal()) {
                        lp.width = ((parent.getMeasuredWidth()) / parentActivity.getRowCol());
                        lp.height = (int) (lp.width * 0.75);
                    }
                    else{
                        lp.height = ((parent.getMeasuredHeight()) / parentActivity.getRowCol());
                        lp.width = (int) (lp.height * 1.3); // 1.3 is scaler for 4:3 in this dimension
                    }
                    break;
            }
        }

        // set layout margins
        lp.setMargins(0,10,10,10);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }

    // each element in countryList.questions passes through this method to create question cards
    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.ViewHolder holder, int position) {

        String qNum =  "Q" + questionList.get(position).getQuestionNum();

        // add special if required
        if(questionList.get(position).isSpecial()){
            qNum += " (Special)";
        }

        String points = "Points = " + questionList.get(position).getPoint();
        String penalty = "Penalty = " + questionList.get(position).getPenalty();
        holder.itemView.setTag(questionList.get(position));

        // set the textViews
        holder.questionNumber.setText(qNum);
        holder.point.setText(points);
        holder.penalty.setText(penalty);
    }

    // get size of Question list
    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
