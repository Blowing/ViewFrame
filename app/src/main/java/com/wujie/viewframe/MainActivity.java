package com.wujie.viewframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnChoose;
    private ListView listView;
    private ListAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private HashMap<Integer,String> checkMap = new HashMap<>();
    private chooseState isChooseAll = chooseState.adefault;

    public enum chooseState {
        adefault,
        All,
        notAll,
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHeader();
        getListInfo();
        initView();
    }

    private void initHeader() {
        ImageView btnBack = (ImageView) findViewById(R.id.image);
        btnBack.setOnClickListener(this);
        TextView headerTitle = (TextView) findViewById(R.id.header_title);
        btnChoose = (Button) findViewById(R.id.btn_choose);
        btnChoose.setOnClickListener(this);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        mAdapter = new ListAdapter();
        listView.setAdapter(mAdapter);
    }

    private void getListInfo() {
        mList.add("西游记");
        mList.add("德玛西亚");
        mList.add("爱情保卫战");
        mList.add("天天向上");
        mList.add("一路有你");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:
                isChooseAll = chooseState.adefault;
                btnChoose.setText("全选");
                checkMap.clear();

                break;
            case R.id.btn_choose:
                switch (isChooseAll) {
                    case All:
                        for (int i = 0; i < mList.size(); i++) {
                            checkMap.put(i, mList.get(i));
                        }
                        isChooseAll = chooseState.notAll;
                        btnChoose.setText("全选");
                        Log.e("全不选", "All" +chooseState.notAll);
                        break;
                    case notAll:
                    case adefault:
                        isChooseAll = chooseState.All;
                        checkMap.clear();
                        btnChoose.setText("全不选");
                        Log.e("全选", "notAll" +chooseState.All);
                        break;

                }

        }
        mAdapter.notifyDataSetChanged();

    }

    public class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Holder mHolder;
            if( view == null ) {
                view = getLayoutInflater().inflate(R.layout.item_list_view,null);
                mHolder = new Holder();
                mHolder.mTcheckBox = (CheckBox) view.findViewById(R.id.checkbox);
                mHolder.mTextView = (TextView) view.findViewById(R.id.text);
                view.setTag(mHolder);
            }
            mHolder = (Holder) view.getTag();
            mHolder.mTextView.setText(mList.get(i));

            mHolder.mTcheckBox.setOnCheckedChangeListener(null);
            if(checkMap.containsKey(i)) {
                mHolder.mTcheckBox.setChecked(true);
            } else {
                mHolder.mTcheckBox.setChecked(false);
            }
            switch (isChooseAll) {
                case All:
                    mHolder.mTcheckBox.setChecked(true);
                    break;
                case notAll:
                    mHolder.mTcheckBox.setChecked(false);
                    break;
            }

            mHolder.mTcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        checkMap.put(i,mList.get(i));
                    } else {
                        checkMap.remove(i);
                    }
                    int size = checkMap.size();
                    if(size == mList.size()) {
                        isChooseAll = chooseState.All;
                        btnChoose.setText("全不选");
                    } else {
                        isChooseAll = chooseState.notAll;
                        btnChoose.setText("全选");
                    }
                }
            });








            return view;
        }

        public  class Holder {
            TextView mTextView;
            CheckBox mTcheckBox;
        }
    }
}
