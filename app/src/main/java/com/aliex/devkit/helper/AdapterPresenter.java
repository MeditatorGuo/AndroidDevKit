package com.aliex.devkit.helper;

import java.util.HashMap;
import java.util.List;

import com.aliex.devkit.BaseApplication;
import com.aliex.devkit.Const;
import com.aliex.devkit.data.LocalRepository;
import com.aliex.devkit.data.RemoteRepository;
import com.aliex.devkit.utils.NetWorkUtil;

/**
 * author: Aliex <br/>
 * created on: 2017/3/18 <br/>
 * description: <br/>
 */

public class AdapterPresenter<M> {

    private RemoteRepository mRemoteRepository;
    private LocalRepository mLocalRepository;
    private HashMap<String, Object> params = new HashMap<String, Object>();
    private IAdapterView<M> mAdapterView;
    private int begin = 0;

    interface IAdapterView<M> {
        void setEmpty();

        void setRemoteData(List<M> data, int begin);

        void setLocalData(List<M> data);

        void resetEmpty();
    }

    public AdapterPresenter(IAdapterView adapterView) {
        this.mAdapterView = adapterView;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public AdapterPresenter setParams(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public AdapterPresenter setRemoteRepository(RemoteRepository repository) {
        this.mRemoteRepository = repository;
        return this;
    }

    public AdapterPresenter setLocalRepository(LocalRepository repository) {
        this.mLocalRepository = repository;
        return this;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    private void getLocalData() {
        if (mLocalRepository != null) {
            mLocalRepository.getData(params).subscribe(realmResults -> mAdapterView.setLocalData(realmResults),
                    throwable -> mAdapterView.setEmpty());
        } else {
            mAdapterView.setEmpty();
        }
    }

    public void fetch() {
        if (!NetWorkUtil.isNetConnected(BaseApplication.getAppContext())) {
            getLocalData();
            return;
        }
        begin++;
        mAdapterView.resetEmpty();
        if (mRemoteRepository == null) {
            return;
        }
        params.put(Const.PAGE, begin);
        mRemoteRepository.getData(params).subscribe(res -> mAdapterView.setRemoteData(res.results, begin),
                throwable -> mAdapterView.setEmpty());

    }

}
