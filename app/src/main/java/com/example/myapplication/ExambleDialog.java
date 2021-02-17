package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;

public class ExambleDialog extends AppCompatDialogFragment {
    ExambleDialogListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialogTitle)
                .setMessage(R.string.choose)
                .setPositiveButton(R.string.camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(ExambleDialog.this);


                    }

                })
                .setNeutralButton("bring photo from gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    listener.onDialogNeutralClick(ExambleDialog.this);
                    }
                });
        return builder.create();


    }
    public interface ExambleDialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNeutralClick(DialogFragment dialog);



    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (ExambleDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement ExambleDialogListener");
        }
    }
}
