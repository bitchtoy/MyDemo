package com.sequoia.demo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.List;


/**
 * @author Administrator.
 * @date 2018/5/16.
 * @funtion
 */
public class CSAdapter extends RecyclerView.Adapter<CSAdapter.BaseViewHolder> {
    private static final int FAST_CHECKED = 5001;
    private static final int COMPANY_LIST = 5002;
    private Context mContext;
    private List<CLBean> mCLBeans;
    private List<String> mFCBeans;
    private LinearLayoutManager mManager;

    public CSAdapter(Context mContext, List<CLBean> mCLBeans, List<String> fcBeans, LinearLayoutManager manager) {
        this.mContext = mContext;
        this.mCLBeans = mCLBeans;
        mManager = manager;
        mFCBeans = fcBeans;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case FAST_CHECKED:
                view = LayoutInflater.from(mContext).inflate(R.layout.company_search_layout, parent, false);
                return new FCViewHolder(view);
            default:
               view = LayoutInflater.from(mContext).inflate(R.layout.company_search_list_item, parent, false);
               return new CLViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof CLViewHolder) {
            int itemPosition = holder.getAdapterPosition();
            if (itemPosition == 1) {
                ((CLViewHolder) holder).pinyin.setText(mCLBeans.get(itemPosition).getPinyin());
            } else if (!mCLBeans.get(position).pinyin.equals(mCLBeans.get(position - 1).pinyin)) {
                ((CLViewHolder) holder).pinyin.setText(mCLBeans.get(position).getPinyin());
            } else {
                ((CLViewHolder) holder).pinyin.setText("");
            }
            ((CLViewHolder) holder).companyName.setText(mCLBeans.get(position).getCompanyName());
            ((CLViewHolder) holder).companyDescribe.setText("公司简介");
        }
        if (holder instanceof FCViewHolder){
           int fcPosition = holder.getAdapterPosition();
           GridLayoutManager manager = new GridLayoutManager(mContext,3);
           ((FCViewHolder) holder).fcRV.setLayoutManager(manager);
            FastCheckedCityAdp adp = new FastCheckedCityAdp(R.layout.company_search_layout_item,mFCBeans);
            ((FCViewHolder) holder).fcRV.setAdapter(adp);
        }
    }

    @Override
    public int getItemCount() {
        return mCLBeans == null ? 0:mCLBeans.size();
    }
    static class BaseViewHolder extends RecyclerView.ViewHolder {

         BaseViewHolder(View itemView) {
            super(itemView);
        }
    }
    public static class FCViewHolder extends BaseViewHolder{
         RecyclerView fcRV;
         FCViewHolder(View itemView) {
            super(itemView);
            fcRV = itemView.findViewById(R.id.fast_checked_rv);
        }
    }
    public static class CLViewHolder extends BaseViewHolder {
        TextView pinyin;
        TextView companyName;
        TextView companyDescribe;

         CLViewHolder(View view) {
            super(view);
            pinyin = itemView.findViewById(R.id.cl_pin_yin);
            companyName = itemView.findViewById(R.id.cl_item_company_name);
            companyDescribe = itemView.findViewById(R.id.cl_item_company_introduction);
        }
    }

    public void scrollToPosition(String index) {
        int size = mCLBeans.size();
        for (int i = 1; i < size; i++) {
            if (TextUtils.equals(index, mCLBeans.get(i).pinyin)) {
                mManager.scrollToPositionWithOffset(i, 0);
                return;
            }
        }
    }



    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FAST_CHECKED;
        }
        return super.getItemViewType(position);
    }
    //    private CLAdapter clAdapter;
//    private int position;
//    private RecyclerView clRV,fcRV;
//    /**
//     * Same as QuickAdapter#QuickAdapter(Context,int) but with
//     * some initialization data.
//     *
//     * @param data A new list is created out of this one to avoid mutable list
//     */
//    public CSAdapter(List<MultipleItem> data) {
//        super(data);
//        addItemType(LayoutStyle.FAST_CHECKED, R.layout.company_search_layout);
//        addItemType(LayoutStyle.COMPANY_LIST,R.layout.company_search_list);
//
//
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, MultipleItem item) {
//
//        switch (helper.getItemViewType()) {
//            case LayoutStyle.FAST_CHECKED:
//                FCBean fcBean = (FCBean) item;
//                fcRV = helper.getView(R.id.fast_checked_rv);
//                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,3);
//                fcRV.setLayoutManager(gridLayoutManager);
//                FastCheckedCityAdp adp = new FastCheckedCityAdp(R.layout.company_search_layout_item,fcBean.mList);
//                fcRV.setAdapter(adp);
//                break;
//            case LayoutStyle.COMPANY_LIST:
//                CLBean clBean = (CLBean) item;
//                clRV = helper.getView(R.id.company_list_rv);
//                LinearLayoutManager clManager = new LinearLayoutManager(mContext);
//                clRV.setLayoutManager(clManager);
//                clAdapter = new CLAdapter(mContext,clBean.mList);
//                clAdapter.setManager(clManager);
//                clRV.setAdapter(clAdapter);
//                break;
//            default:
//                break;
//
//
//        }
//
//    }
//
//    public void scrollToSection(String index,int postion){
//           clAdapter.scrollToSection(index);
//           clRV.scrollToPosition(position);
//
//    }
}
