package tech.hiphone.commons.web.vm;

import java.util.ArrayList;
import java.util.List;

// 导入数据
public class ImportDataVM<T> {

    private boolean isAdded;

    private List<T> dataList = new ArrayList<>();

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean isAdded) {
        this.isAdded = isAdded;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

}
