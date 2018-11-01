package com.qimai.xinlingshou.fragment.left;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.adapter.CityAdapter;
import com.qimai.xinlingshou.adapter.OrderDetailAdapter2;
import com.qimai.xinlingshou.adapter.OrderListAdapter;
import com.qimai.xinlingshou.adapter.OrderListAdapter2;
import com.qimai.xinlingshou.bean.CityBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LeftOrderListFragment extends BaseFragment implements OrderListAdapter.OnViewClick {

    private static final String TAG = "LeftOrderListFragment";

    @BindView(R.id.iv_font)
    ImageView ivFont;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.rv_order_list)
    RecyclerView rvOrderList;


    OrderListAdapter orderListAdapter;
    OrderListAdapter2 orderListAdapter2;

    OrderDetailAdapter2 orderDetailAdapter2;
    CityAdapter cityAdapter;
    Unbinder unbinder;

    ArrayList<CityBean> arrayList;
    @BindView(R.id.view_verital_divider)
    View viewVeritalDivider;
    @BindView(R.id.cl_top)
    ConstraintLayout clTop;
    @BindView(R.id.view_divider)
    View viewDivider;
    @BindView(R.id.ll_rv_container)
    LinearLayout llRvContainer;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;

    @Override
    protected void initData() {


    }

    @Override
    protected void initView(View rootView) {


        arrayList = new ArrayList<>();

        CityBean cityBean;
        for (int i = 0; i < 200; i++) {

            cityBean = new CityBean();
            cityBean.setCity(i + "");
            if (i < 5) {
                cityBean.setTag("A");

            } else {
                cityBean.setTag("B");
            }
            arrayList.add(cityBean);
        }

        for (CityBean c :
                arrayList) {

            Log.d(TAG, "initView: c= " + c.toString());
        }
        Log.d(TAG, "initView: ");

        cityAdapter = new CityAdapter(arrayList, activity);

        //rvOrderList.addItemDecoration(new CustomItemDecoration(arrayList));

        orderListAdapter = new OrderListAdapter(activity);

        orderListAdapter.setOnViewClick(this);
        orderListAdapter2 = new OrderListAdapter2(activity);

        orderDetailAdapter2 = new OrderDetailAdapter2(activity);

        rvDetail.setLayoutManager(new LinearLayoutManager(activity));

        rvOrderList.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                Log.d(TAG, "onScrolled: ");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                Log.d(TAG, "onScrollStateChanged: lastVisib ity= " + linearLayoutManager
                        .findLastVisibleItemPosition() + "  newstate= " + newState
                        + "  adapter count= " + recyclerView.getAdapter().getItemCount());

                //super.onScrollStateChanged(recyclerView, newState);
            }
        });


        rvOrderList.setLayoutManager(new LinearLayoutManager(activity));
        /*rvOrderList.addItemDecoration(new DividerItemDecoration(activity
                ,DividerItemDecoration.VERTICAL));*/

        rvOrderList.setAdapter(orderListAdapter);
        //rvOrderList.setAdapter(cityAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_left_order_list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.cl_top,R.id.tv_select})
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.cl_top:

                break;
            case R.id.tv_select:

                showFilterPopWindow(view);

                break;
        }

        Log.d(TAG, "onClick: cl_top");
    }

    private void showFilterPopWindow(View view) {

        View view1 = LayoutInflater.from(activity).inflate(R.layout.popwidow_filter_view,null);

        RadioGroup radioGroup = view1.findViewById(R.id.rg_date);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: checkId= "+checkedId);
            }
        });
        //((View)view1.getParent()).getWidth();
        PopupWindow popupWindow = new PopupWindow(view1,clTop.getMeasuredWidth(),WindowManager.LayoutParams.WRAP_CONTENT,true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable(null,""));
        popupWindow.setOutsideTouchable(true);
        //popupWindow.showAtLocation(viewDivider,0,0);
       popupWindow.showAsDropDown(viewDivider,1,0);

        int[]locations = new int[2];

        viewDivider.getLocationOnScreen(locations);

        Log.d(TAG, "showFilterPopWindow: [location= ]"+locations[1]);
       // popupWindow.showAtLocation(viewDivider,Gravity.NO_GRAVITY,0,locations[1]);

        //popupWindow.showAsDropDown(view,20,0);
    }

    @Override
    public void onLoadMore() {
        Log.d(TAG, "onLoadMore:click loadmore-9");
        llRvContainer.setVisibility(View.GONE);
        rvDetail.setAdapter(orderDetailAdapter2);

    }

}
