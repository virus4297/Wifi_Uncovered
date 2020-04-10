package com.example.wifiuncovered.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.wifiuncovered.MyAdapter;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    //private MutableLiveData<String> mText;
    private MutableLiveData<MyAdapter> myAdapterMutableLiveData;

    public int myDatasetcount;
    public ArrayList<String> images_ArrayList = new ArrayList<>();
    public ArrayList<String> status_ArrayList = new ArrayList<>();
    public ArrayList<String> ip_ArrayList = new ArrayList<>();
    public ArrayList<String> desc_ArrayList = new ArrayList<>();
    public String ip,host,ipAddress;
    public static Context contextView;
    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    myAdapterMutableLiveData=new MutableLiveData<>();
    myAdapterMutableLiveData.postValue(new MyAdapter(contextView, ip_ArrayList, desc_ArrayList, status_ArrayList, images_ArrayList, myDatasetcount));
    }

    public static Context getHomeViewModelCurrentObject(){
        return HomeViewModel.contextView;
    }


    public void getData(SQLiteDatabase database) {
        //getData
        ip_ArrayList.clear();
        status_ArrayList.clear();
        desc_ArrayList.clear();
        images_ArrayList.clear();
        myDatasetcount=0;
        Cursor cursor = database.rawQuery("SELECT _ID,IP,DESCRIPTION,STATUS FROM DEVICES",new String[]{});

        if(cursor!=null)
            cursor.moveToFirst();

        StringBuilder stringBuilder = new StringBuilder();
        //textView.append("Row Count:"+cursor.getCount()+"\n");
        do {
            int id = cursor.getInt(0);
            String ip = cursor.getString(1);
            String desc = cursor.getString(2);
            String status = cursor.getString(3);
            stringBuilder.append("\nID:"+id+" IP"+ip+" DESC:"+desc+" STATUS:"+status);
            images_ArrayList.add("https://cdn.pixabay.com/photo/2020/03/19/04/58/coconut-trees-4946270_960_720.jpg");
            ip_ArrayList.add(ip);
            status_ArrayList.add(status);
            desc_ArrayList.add(desc);
            myDatasetcount++;
        }while (cursor.moveToNext());
        cursor.close();

    }

    public static String getIp(){
        return ipAddressStatic;
    }

//    public LiveData<String> getText() {
//        return mText;
//    }
    public LiveData<MyAdapter> getLiveAdapter(Context context){
        contextView=context;
        return myAdapterMutableLiveData;
    }

    public static String ipAddressStatic;
    public static void updateData(String ip){
        ipAddressStatic =ip;
    }


//    public MyAdapter getAdapter(Context context){
//        contextView=context;
//        MyAdapter adapter = new MyAdapter(context,ip_ArrayList, desc_ArrayList,status_ArrayList, images_ArrayList,myDatasetcount);
//        updateRecycler();
//        return adapter;
//    }

    public void updateRecycler() {
        myAdapterMutableLiveData.postValue(new MyAdapter(contextView, ip_ArrayList, desc_ArrayList, status_ArrayList, images_ArrayList, myDatasetcount));
        System.out.println("run!!, its running here too");
}
}