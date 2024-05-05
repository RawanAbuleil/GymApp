package com.project.gymapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    ProgressDialog mprogress;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mprogress=new ProgressDialog(getActivity());

    }
    public void showDialog(String msg){
        try {
            mprogress.setMessage(msg);
            mprogress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mprogress.setIndeterminate(false);
            mprogress.setCancelable(false);
            mprogress.show();

        }catch (Exception e){
            e.printStackTrace();

        }
    }
    public void ClosedDialog(){
        if (mprogress!=null)
        {
            mprogress.dismiss();
        }
    }
}
