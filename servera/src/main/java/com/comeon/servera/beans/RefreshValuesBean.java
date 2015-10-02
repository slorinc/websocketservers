package com.comeon.servera.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by s_lor_000 on 10/2/2015.
 */

@Component
public class RefreshValuesBean {

    @Value("${refresh.data:10}")
    private int refreshData;

    @Value("${refresh.view:10}")
    private int refreshView;

    public int getRefreshData() {
        return refreshData;
    }

    public void setRefreshData(int refreshData) {
        this.refreshData = refreshData;
    }

    public int getRefreshView() {
        return refreshView;
    }

    public void setRefreshView(int refreshView) {
        this.refreshView = refreshView;
    }
}
