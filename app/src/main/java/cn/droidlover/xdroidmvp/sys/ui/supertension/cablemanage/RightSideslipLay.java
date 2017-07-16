package cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.droidlover.xdroidmvp.sys.R;
import cn.droidlover.xdroidmvp.sys.ui.common.AttrList;
import cn.droidlover.xdroidmvp.sys.ui.common.OnClickListenerWrapper;
import cn.droidlover.xdroidmvp.sys.ui.supertension.cablemanage.adapter.RightSideslipLayAdapter;
import cn.droidlover.xdroidmvp.sys.utils.AssetsJsonReader;

/**
 * 属性选择的布局及逻辑
 */
public class RightSideslipLay extends RelativeLayout {
    private Context mCtx;
    private AttrList attr;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private ImageView backBrand;
    private RelativeLayout mRelateLay;
    private RightSideslipLayAdapter slidLayFrameAdapter;
    private List<AttrList.Attr.Vals> ValsData;
    private Map<String, List> searchMap = new HashMap<>();
    private String jsonPath;

    public RightSideslipLay(Context context, String jsonPath) {
        super(context);
        mCtx = context;
        this.jsonPath = jsonPath;
        inflateView();
    }

    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);
        selectList = (ListView) findViewById(R.id.selsectFrameLV);//list列表
        backBrand = (ImageView) findViewById(R.id.select_brand_back_im);//返回
        resetBrand = (Button) findViewById(R.id.fram_reset_but);//重置
        mRelateLay = (RelativeLayout) findViewById(R.id.select_frame_lay);
        okBrand = (Button) findViewById(R.id.fram_ok_but);//确定
        resetBrand.setOnClickListener(mOnClickListener);
        okBrand.setOnClickListener(mOnClickListener);
        backBrand.setOnClickListener(mOnClickListener);
        mRelateLay.setOnClickListener(mOnClickListener);
        setUpList();
    }

    private List<AttrList.Attr> setUpBrandList(List<AttrList.Attr> mAttrList) {
        //if ("品牌".equals(mAttrList.get(0).getKey())) {
        //    ValsData = mAttrList.get(0).getVals();
        //    mAttrList.get(0).setVals(getValsDatas(mAttrList.get(0).getVals()));
        //}
        return mAttrList;
    }

    private void setUpList() {
        attr = new Gson().fromJson(AssetsJsonReader.getJsonString(mCtx, jsonPath), AttrList.class);
        if (null == attr) {
            return;
        }
        if (slidLayFrameAdapter == null) {
            slidLayFrameAdapter = new RightSideslipLayAdapter(mCtx, setUpBrandList(attr.getAttr()));
            selectList.setAdapter(slidLayFrameAdapter);
        } else {
            slidLayFrameAdapter.replaceAll(attr.getAttr());
        }
        slidLayFrameAdapter.setAttrCallBack(new RightSideslipLayAdapter.SelechDataCallBack() {
            @Override
            public void setupAttr(List mSelectData, String key) {
                searchMap.put(key, mSelectData);
            }
        });
        slidLayFrameAdapter.setMoreCallBack(new RightSideslipLayAdapter.SelechMoreCallBack() {

            @Override
            public void setupMore(List<AttrList.Attr.Vals> mSelectData) {
                getPopupWindow(mSelectData);
                mDownMenu.setOnMeanCallBack(meanCallBack);
            }
        });
    }

    //在第二个页面改变后，返回时第一个界面随之改变，使用的接口回调
    private RightSideslipChildLay.onMeanCallBack meanCallBack = new RightSideslipChildLay.onMeanCallBack() {
        @Override
        public void isDisMess(boolean isDis, List<AttrList.Attr.Vals> mBrandData, String str) {
            if (mBrandData != null) {
                if (attr.getAttr().size() > 0) {
                    ((AttrList.Attr) attr.getAttr().get(0)).setVals(getValsDatas(mBrandData));
                    ((AttrList.Attr) attr.getAttr().get(0)).setShowStr(str);
                }
                slidLayFrameAdapter.replaceAll(attr.getAttr());
            }

            dismissMenuPop();
        }
    };

    private List<AttrList.Attr.Vals> getValsDatas(List<AttrList.Attr.Vals> mBrandData) {
        List<AttrList.Attr.Vals> mVals = new ArrayList<AttrList.Attr.Vals>();
        if (mBrandData != null && mBrandData.size() > 0) {
            for (int i = 0; i < mBrandData.size(); i++) {
                if (mVals.size() >= 8) {
                    AttrList.Attr.Vals valsAdd = new AttrList.Attr.Vals();
                    valsAdd.setV("查看更多 >");
                    mVals.add(valsAdd);
                    continue;
                } else {
                    mVals.add(mBrandData.get(i));
                }
            }
            mVals = mVals.size() >= 9 ? mVals.subList(0, 9) : mVals;
            return mVals;

        }
        return null;
    }

    private OnClickListenerWrapper mOnClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.fram_reset_but:
                    slidLayFrameAdapter.replaceAll(attr.getAttr());
                    break;
                case R.id.select_brand_back_im:
                    menuCallBack.setupCloseMean();
                case R.id.fram_ok_but:
                    menuCallBack.setupCloseMean();
                    doSearchCallBack.doSearch(searchMap);
                    break;
            }
        }
    };

    /**
     * 关闭窗口
     */
    private void dismissMenuPop() {
        if (mMenuPop != null) {
            mMenuPop.dismiss();
            mMenuPop = null;
        }

    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow(List<AttrList.Attr.Vals> mSelectData) {
        if (mMenuPop != null) {
            dismissMenuPop();
            return;
        } else {
            initPopuptWindow(mSelectData);
        }
    }

    /**
     * 创建PopupWindow
     */
    private PopupWindow mMenuPop;
    public RightSideslipChildLay mDownMenu;

    protected void initPopuptWindow(List<AttrList.Attr.Vals> mSelectData) {
        mDownMenu = new RightSideslipChildLay(getContext(), ValsData, mSelectData);
        if (mMenuPop == null) {
            mMenuPop = new PopupWindow(mDownMenu, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        }
        mMenuPop.setBackgroundDrawable(new BitmapDrawable());
        mMenuPop.setAnimationStyle(R.style.popupWindowAnimRight);
        mMenuPop.setFocusable(true);
        mMenuPop.showAtLocation(RightSideslipLay.this, Gravity.TOP, 100, BarUtils.getStatusBarHeight(mCtx));
        mMenuPop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                dismissMenuPop();
            }
        });
    }

    private CloseMenuCallBack menuCallBack;//关闭回调
    private DoSearchCallBack doSearchCallBack;//执行查询回调

    public interface CloseMenuCallBack {
        void setupCloseMean();
    }

    public interface DoSearchCallBack {
        void doSearch(Map<String, List> searchMap);//查询
    }

    public void setCloseMenuCallBack(CloseMenuCallBack menuCallBack) {
        this.menuCallBack = menuCallBack;
    }

    public void setDoSearchCallBack(DoSearchCallBack doSearchCallBack) {
        this.doSearchCallBack = doSearchCallBack;
    }
}