package com.fas.smash_k.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.fas.smash_k.R;
import com.fas.smash_k.TalkActivity;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        Button btn_new = (Button) root.findViewById(R.id.btn_newActivity_id); // 새 화면 열기 버튼 ID 매칭
        //ScrollView scroll_view = (ScrollView) findViewById(R.id.scroll_view_id);


        btn_new.setOnClickListener(new View.OnClickListener() // 새 화면 열기 버튼을 클릭 할시
        {
            @Override
            public void onClick(View view)
            {
                Intent send_intent = new Intent(getActivity(), TalkActivity.class);
                getActivity().startActivity(send_intent); // TalkActivity 자바 파일에 전달
            }
        });
        return root;
    }
}