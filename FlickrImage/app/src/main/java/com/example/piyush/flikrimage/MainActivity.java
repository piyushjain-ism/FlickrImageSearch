package com.example.piyush.flikrimage;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piyush.flikrimage.Cache.ImageCache;
import com.example.piyush.flikrimage.NetworkClass.MainActivityModel;
import com.example.piyush.flikrimage.adapter.ItemAdapter;
import com.example.piyush.flikrimage.pojo.ImagePojo;
import com.example.piyush.flikrimage.presenter.MainActivityPresenterImpl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements MainActivityView {

    SearchView materialSearchView;
    Toolbar toolbar;
    RecyclerView mRecyclerview;
    LinearLayoutManager mlayoutManager;
    private ArrayList<ImagePojo> items = new ArrayList<>();
    ItemAdapter allItemAdapter;
    int page = 1;
    String mSearchString;
    boolean loading = true;
    MainActivityPresenterImpl mainActivityPresenter;
    ProgressBar mProgressbar;
    TextView mNoResult,mToolbarTitle;
    ImageCache cache = ImageCache.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cache.initializeCache();
        }

    @Override
    protected void initViews() {
        materialSearchView = findViewById(R.id.search_view);
        materialSearchView.setQueryHint("Seacrh Flickr Image");
        mProgressbar = findViewById(R.id.progressbar);
        mNoResult = findViewById(R.id.no_result);
        mToolbarTitle = findViewById(R.id.toolbar_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpAdapter();
        mainActivityPresenter = new MainActivityPresenterImpl(this , new MainActivityModel());

        materialSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbarTitle.setVisibility(View.GONE);
                getSupportActionBar().setTitle("");
            }
        });
        materialSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mToolbarTitle.setVisibility(View.VISIBLE);
                return false;
            }
        });
        materialSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(!getNetworkClass(getApplicationContext())){
                    Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                }else {
                    page = 1;
                    mSearchString = query;
                    if (query.length() > 0) {
                        if (items.size() > 0) {
                            items.clear();
                        }
                        mNoResult.setVisibility(View.GONE);
                        mainActivityPresenter.searchImage(page, mSearchString);
                    } else
                        Toast.makeText(getApplicationContext(), getString(R.string.enter_item), Toast.LENGTH_LONG).show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                getSupportActionBar().setTitle("");

                return false;
            }
        });


    }

    public void setUpAdapter(){
        mRecyclerview = findViewById(R.id.recyclerView);
        mlayoutManager = new GridLayoutManager(this, 3);
        mRecyclerview.setLayoutManager(mlayoutManager);
        allItemAdapter = new ItemAdapter(this,items,cache);
        mRecyclerview.setAdapter(allItemAdapter);
        mRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mlayoutManager.getChildCount();
                int totalItemCount = mlayoutManager.getItemCount();
                int firstVisibleItem = mlayoutManager.findFirstVisibleItemPosition();
                if (!loading) {
                    if ((visibleItemCount + firstVisibleItem) >= totalItemCount) {
                        if(!getNetworkClass(getApplicationContext())){
                            Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_LONG).show();
                        }else {
                            loading = true;
                            page++;
                            mainActivityPresenter.searchImage(page, mSearchString);
                        }
                    }
                }
            }
        });


    }

    @Override
    public void onSearchSuccess(JsonArray jsonArray) {
        if(jsonArray.size()==0 && page==1)
            onSearchFailure(getString(R.string.no_result));
        else {
            loading = false;
            int current_item_size = items.size();
            ImagePojo imagePojo = new ImagePojo();

            for (int i = 0; i < jsonArray.size(); i++) {
                imagePojo = new Gson().fromJson(jsonArray.get(i).toString(),
                        ImagePojo.class);
                items.add(current_item_size + i, imagePojo);
            }
            allItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSearchFailure(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        mProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressbar.setVisibility(View.GONE);
    }

}
