package com.example.piyush.flikrimage.presenter;

import com.example.piyush.flikrimage.MainActivityView;
import com.example.piyush.flikrimage.NetworkClass.MainActivityModel;
import com.google.gson.JsonArray;

public class MainActivityPresenterImpl implements MainActivityModel.OnLoginFinishedListener{

    MainActivityView mView;
    MainActivityModel mainActivityModel;

    public MainActivityPresenterImpl(MainActivityView mainActivityView, MainActivityModel mainActivityModel){
        this.mView = mainActivityView;
        this.mainActivityModel = mainActivityModel;
        }


    public void searchImage(int page,String text) {
        mView.showProgress();
        mainActivityModel.search(page,text , this);

          }

    @Override
    public void onSuccess(JsonArray jsonArray) {
        mView.hideProgress();
        mView.onSearchSuccess(jsonArray);
    }

    @Override
    public void onFailure(String message) {

        mView.hideProgress();
        mView.onSearchFailure(message);

    }
}
