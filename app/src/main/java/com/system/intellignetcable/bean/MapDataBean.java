package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by adu on 2018/12/2.
 */

public class MapDataBean implements Serializable{

    /**
     * msg : success
     * code : 0
     * list : [{"areaId":1,"areaName":"丰台区","num":0,"latitude":"39.863644","longitude":"116.286964"},{"areaId":2,"areaName":"海淀区","num":1,"latitude":"39.956074","longitude":"116.310318"},{"areaId":3,"areaName":"门头沟","num":0,"latitude":"39.937183","longitude":"116.105377"},{"areaId":4,"areaName":"房山区","num":0,"latitude":"39.735535","longitude":"116.13916"},{"areaId":5,"areaName":"通州区","num":0,"latitude":"39.902485","longitude":"116.6586"},{"areaId":6,"areaName":"顺义区","num":0,"latitude":"40.128937","longitude":"116.653526"},{"areaId":7,"areaName":"昌平区","num":0,"latitude":"40.218086","longitude":"116.235909"},{"areaId":8,"areaName":"大兴区","num":0,"latitude":"39.728909","longitude":"116.338036"},{"areaId":9,"areaName":"朝阳区","num":0,"latitude":"39.955875","longitude":"116.543398"}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * areaId : 1
         * areaName : 丰台区
         * num : 0
         * latitude : 39.863644
         * longitude : 116.286964
         */

        private int areaId;
        private String areaName;
        private int num;
        private String latitude;
        private String longitude;

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
