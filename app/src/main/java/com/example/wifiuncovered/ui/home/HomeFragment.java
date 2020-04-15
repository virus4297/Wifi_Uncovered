package com.example.wifiuncovered.ui.home;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wifiuncovered.MainActivity;
import com.example.wifiuncovered.MyAdapter;
import com.example.wifiuncovered.MyHelper;
import com.example.wifiuncovered.R;
import com.example.wifiuncovered.getDataNetwork;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public MainActivity mainActivity;
    public RecyclerView recyclerView;
    public HomeViewModel homeViewModel;
    public View root;
    public TextView textView;
    public MyAdapter adapter;
    public Context context;
    public Fragment fragment;
    public FragmentTransaction ft;
    public ProgressBar pb;

    public HomeFragment(MainActivity mainActivity){
        this.mainActivity=mainActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // postponeEnterTransition();

       // startPostponedEnterTransition();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        textView = root.findViewById(R.id.textView3);
        textView.append(HomeViewModel.getIp());
        //***************************button start************************************
        Button button = root.findViewById(R.id.getip_button);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          final View view=root;
                                          Intent intent =  view.getContext().getPackageManager().getLaunchIntentForPackage(getActivity().getPackageName());
                                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                          startActivity(intent);
                                      }
                                  }
        );
        //***************************button end************************************
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

//        pb=root.findViewById(R.id.pbmain);

        while(!mainActivity.getDataNetworkStatus().equals(View.GONE))
            ;//wait

        MyHelper helper = new MyHelper(getContext());
        final SQLiteDatabase database=helper.getReadableDatabase();

        //initRecyclerView
        recyclerView = root.findViewById(R.id.ip_RecyclerView);
        //recyclerView.setHasFixedSize(true);

        homeViewModel.getLiveAdapter(getContext()).observe(getViewLifecycleOwner(), new Observer<MyAdapter>() {
            @Override
            public void onChanged(MyAdapter myAdapter) {
                //recyclerView.removeAllViews();
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        Thread updateRecyclerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int j=0;
                while(j++<3){
//                    updateView();
                    System.out.println("run!!, its running");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    homeViewModel.getData(database);
                    homeViewModel.updateRecycler();
                }
            }
        });
        updateRecyclerThread.start();

        return root;
    }

}
