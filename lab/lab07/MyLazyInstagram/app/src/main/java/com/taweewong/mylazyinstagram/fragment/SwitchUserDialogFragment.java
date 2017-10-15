package com.taweewong.mylazyinstagram.fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.taweewong.mylazyinstagram.R;
import com.taweewong.mylazyinstagram.controller.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SwitchUserDialogFragment extends DialogFragment implements View.OnClickListener {
    CircleImageView imageSelect1;
    CircleImageView imageSelect2;
    CircleImageView imageSelect3;
    TextView usernameSelect1;
    TextView usernameSelect2;
    TextView usernameSelect3;
    LinearLayout selectUserLayout1;
    LinearLayout selectUserLayout2;
    LinearLayout selectUserLayout3;
    String currentUser = "";

    public SwitchUserDialogFragment() {
        // Required empty public constructor
    }

    public static SwitchUserDialogFragment newInstance(String currentUser) {

        Bundle args = new Bundle();

        SwitchUserDialogFragment fragment = new SwitchUserDialogFragment();
        args.putString("currentUser", currentUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentUser = getArguments().getString("currentUser");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_switch_user_dialog, container, false);

        imageSelect1 = rootView.findViewById(R.id.userImageSelect1);
        imageSelect2 = rootView.findViewById(R.id.userImageSelect2);
        imageSelect3 = rootView.findViewById(R.id.userImageSelect3);
        usernameSelect1 = rootView.findViewById(R.id.usernameSelect1);
        usernameSelect2 = rootView.findViewById(R.id.usernameSelect2);
        usernameSelect3 = rootView.findViewById(R.id.usernameSelect3);
        selectUserLayout1 = rootView.findViewById(R.id.selectUserLayout1);
        selectUserLayout2 = rootView.findViewById(R.id.selectUserLayout2);
        selectUserLayout3 = rootView.findViewById(R.id.selectUserLayout3);

        selectUserLayout1.setOnClickListener(this);
        selectUserLayout2.setOnClickListener(this);
        selectUserLayout3.setOnClickListener(this);

        String url1 = "https://lh3.googleusercontent.com/DE1k9KAl2teMTpbb1n_lWjjRnneZmgYxnUMwrSwgfcL0Y_T1AXwDGdBXkF0wSulPChEvdnhxkqtXoy7pk9e5fB0UkhcXHLrRUR9a35KJYI5gP-gO467mjMUbD0vSm4hRQGsNPPY2bzU3pOJHXsxkDhYjYZrirIhnlmAfI6Vj3LshWDD7LjQOMjx3adKm0Jqdq-3MHO0BsDKWn7pNHLtB7J0zo_79IWPslrXIIh0c5oJPKLobuB-o_Tnqu5RymL2tyuzlHR_iIlmPA6qy17ge2NXeioWfDT3vqMlVDjebjHFMbws3Mgsue7DbgAzYNhz-hdo6dNfmjqrE5Fk7cWJZGYz9Kzchlo5Vd_HsL0Qt88BSXtxjph-myzUDWZSWrsqZ0ZVdqOb9wLxn-KhVy5d5d572Wh-nt0_-ZRWXfTppm9PPEGFG9ObHUZhL5BVgGVmJGJbbNysH9mie99MM0cfNbVHY9Pxbxn_bOzZnY_UHhs3NjrI-2gBPO8tvEJYsambsfP-NlQBSEN72xsOiDi86oOjvRzlBWExQDIfoXMaFcDcZ3ZYwhqUWOWY-KSLyd_R6IEFAVPo1l7xo0gCs3OwP5qTMj0EMneq3r6tYwuv2=w800-h799-no";
        String url2 = "https://raw.githubusercontent.com/iangkub/gitdemo/master/nature/profile.jpg";
        String url3 = "https://raw.githubusercontent.com/iangkub/gitdemo/master/cartoon/profile.jpg";

        Glide.with(getActivity()).load(url1).into(imageSelect1);
        Glide.with(getActivity()).load(url2).into(imageSelect2);
        Glide.with(getActivity()).load(url3).into(imageSelect3);

        usernameSelect1.setText("android");
        usernameSelect2.setText("nature");
        usernameSelect3.setText("cartoon");

        switch (currentUser) {
            case "android":
                imageSelect1.setBorderColor(ContextCompat.getColor(getActivity(), R.color.green));
                break;
            case "nature":
                imageSelect2.setBorderColor(ContextCompat.getColor(getActivity(), R.color.green));
                break;
            case "cartoon":
                imageSelect3.setBorderColor(ContextCompat.getColor(getActivity(), R.color.green));
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectUserLayout1:
                startMainActivity("android");
                break;
            case R.id.selectUserLayout2:
                startMainActivity("nature");
                break;
            case R.id.selectUserLayout3:
                startMainActivity("cartoon");
        }
    }

    private void setCurrentUser(String currentUser) {
        switch (currentUser) {
            case "android":
                imageSelect1.setBorderColor(ContextCompat.getColor(getActivity(), R.color.green));
                break;
            case "natural":
                imageSelect2.setBorderColor(ContextCompat.getColor(getActivity(), R.color.green));
                break;
            case "cartoon":
                imageSelect3.setBorderColor(ContextCompat.getColor(getActivity(), R.color.green));
        }
    }

    private void startMainActivity(String username) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        getActivity().finish();
    }
}
