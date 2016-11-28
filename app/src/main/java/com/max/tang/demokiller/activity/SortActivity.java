package com.max.tang.demokiller.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.view.HistogramView;
import java.util.ArrayList;
import java.util.List;

public class SortActivity extends BaseActivity {
    private static final String TAG = "sort";
    @BindView(R.id.histogram_view)
    HistogramView mHistogramView;
    List<Integer> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);
        ButterKnife.bind(this);

        mData = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            mData.add((int) (Math.random() * 100));
        }
        mHistogramView.setData(mData);
    }


    @OnClick(R.id.reset)
    public void dataReset(View view) {
        Log.d(TAG, "dataReset: -_-!!!");
        mData.clear();
        for (int i = 0; i < 25; i++) {
            mData.add((int) (Math.random() * 100));
        }
        mHistogramView.setData(mData);
    }
}
