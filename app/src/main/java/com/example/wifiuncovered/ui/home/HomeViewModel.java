package com.example.wifiuncovered.ui.home;

import android.content.Context;

import com.example.wifiuncovered.MyAdapter;

import java.util.ArrayList;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public int myDatasetcount;
    public ArrayList<String> images_ArrayList = new ArrayList<>();
    public ArrayList<String> status_ArrayList = new ArrayList<>();
    public ArrayList<String> ip_ArrayList = new ArrayList<>();
    public ArrayList<String> desc_ArrayList = new ArrayList<>();
    public String ip,host,ipAddress;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        ip_ArrayList.add("192.168.1.1");
        status_ArrayList.add("UP");
        images_ArrayList.add("https://cdn.pixabay.com/photo/2020/03/19/04/58/coconut-trees-4946270_960_720.jpg");
        desc_ArrayList.add("hehe");
        myDatasetcount++;
        ip_ArrayList.add("192.168.1.1");
        status_ArrayList.add("UP");
        images_ArrayList.add("https://cdn.pixabay.com/photo/2020/03/19/04/58/coconut-trees-4946270_960_720.jpg");
        desc_ArrayList.add("hehe");
        myDatasetcount++;

    }

    public LiveData<String> getText() {
        return mText;
    }
    public MyAdapter getAdapter(Context context){
        MyAdapter adapter = new MyAdapter(context,ip_ArrayList, desc_ArrayList,status_ArrayList, images_ArrayList,myDatasetcount);
        return adapter;
    }

}