package com.system.intellignetcable.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zydu on 2018/11/30.
 */

public class SignageManagementBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * signBoard : {"workOrderId":13,"userId":4,"epc":"A11801001001180800000A01","imagesUrls":"http://47.99.148.150/cableimages/1.jpg","longitude":null,"latitude":null,"detailAddress":null,"templateValues":[{"userTemplateValueId":131,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"海淀供电公司"}],"fieldName":"operatingUnit","fieldMustInput":"N","isNull":"Y","orderNum":2,"showType":"list","fieldDesc":"运行单位"},{"userTemplateValueId":132,"fieldValue":"L0001","dicts":null,"fieldName":"lineCode","fieldMustInput":"N","isNull":"Y","orderNum":3,"showType":"text","fieldDesc":"线路编码"},{"userTemplateValueId":133,"fieldValue":"测试001","dicts":null,"fieldName":"lineName","fieldMustInput":"N","isNull":"Y","orderNum":4,"showType":"text","fieldDesc":"线路名称"},{"userTemplateValueId":134,"fieldValue":"CS0001","dicts":null,"fieldName":"cableSegmentCode","fieldMustInput":"N","isNull":"Y","orderNum":5,"showType":"text","fieldDesc":"电缆段编码"},{"userTemplateValueId":135,"fieldValue":"测试开始001","dicts":null,"fieldName":"startPointName","fieldMustInput":"N","isNull":"Y","orderNum":6,"showType":"text","fieldDesc":"起点名称"},{"userTemplateValueId":136,"fieldValue":"测试开始描述001","dicts":null,"fieldName":"startPointDesc","fieldMustInput":"N","isNull":"Y","orderNum":7,"showType":"text","fieldDesc":"起点描述"},{"userTemplateValueId":137,"fieldValue":"测试结束001","dicts":null,"fieldName":"endPointName","fieldMustInput":"N","isNull":"Y","orderNum":8,"showType":"text","fieldDesc":"终点名称"},{"userTemplateValueId":138,"fieldValue":"测试结束描述001","dicts":null,"fieldName":"endPointDesc","fieldMustInput":"N","isNull":"Y","orderNum":9,"showType":"text","fieldDesc":"终点描述"},{"userTemplateValueId":139,"fieldValue":"5000","dicts":null,"fieldName":"cableSize","fieldMustInput":"N","isNull":"Y","orderNum":10,"showType":"text","fieldDesc":"电缆长度（m)"},{"userTemplateValueId":140,"fieldValue":"3","dicts":[{"dictCode":"1","dictText":"70"},{"dictCode":"2","dictText":"95"},{"dictCode":"3","dictText":"120"},{"dictCode":"4","dictText":"150"},{"dictCode":"5","dictText":"185"},{"dictCode":"6","dictText":"240"},{"dictCode":"7","dictText":"300"},{"dictCode":"8","dictText":"400"}],"fieldName":"cableArea","fieldMustInput":"N","isNull":"Y","orderNum":11,"showType":"list","fieldDesc":"电缆截面积（mm2)"},{"userTemplateValueId":141,"fieldValue":"2","dicts":[{"dictCode":"1","dictText":"YJV22"},{"dictCode":"2","dictText":"YJY22"},{"dictCode":"3","dictText":"ZLQD12"},{"dictCode":"4","dictText":"ZLQD22"}],"fieldName":"cableMoble","fieldMustInput":"N","isNull":"Y","orderNum":12,"showType":"list","fieldDesc":"电缆型号"},{"userTemplateValueId":142,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"圆型"},{"dictCode":"2","dictText":"扇形"}],"fieldName":"wireCoreShape","fieldMustInput":"N","isNull":"Y","orderNum":13,"showType":"list","fieldDesc":"线芯形状"},{"userTemplateValueId":143,"fieldValue":"2","dicts":[{"dictCode":"1","dictText":"铜芯"},{"dictCode":"2","dictText":"铝芯"}],"fieldName":"wireCoreMaterial","fieldMustInput":"N","isNull":"Y","orderNum":14,"showType":"list","fieldDesc":"线芯材质"},{"userTemplateValueId":144,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"三芯"},{"dictCode":"2","dictText":"单芯"},{"dictCode":"3","dictText":"四芯"}],"fieldName":"cableCoreCode","fieldMustInput":"N","isNull":"Y","orderNum":15,"showType":"list","fieldDesc":"电缆芯数"},{"userTemplateValueId":145,"fieldValue":"载流","dicts":null,"fieldName":"currentCarryCapacity","fieldMustInput":"N","isNull":"Y","orderNum":16,"showType":"text","fieldDesc":"载流量（A）"},{"userTemplateValueId":146,"fieldValue":"2","dicts":null,"fieldName":"joinNumber","fieldMustInput":"N","isNull":"Y","orderNum":17,"showType":"text","fieldDesc":"中间接头数（个）"},{"userTemplateValueId":147,"fieldValue":"2","dicts":[{"dictCode":"1","dictText":"隧道"},{"dictCode":"2","dictText":"排管"},{"dictCode":"3","dictText":"直埋"},{"dictCode":"4","dictText":"电缆沟"},{"dictCode":"5","dictText":"桥架"}],"fieldName":"layMethod","fieldMustInput":"N","isNull":"Y","orderNum":18,"showType":"list","fieldDesc":"敷设方式"},{"userTemplateValueId":148,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"1"},{"dictCode":"2","dictText":"2"},{"dictCode":"3","dictText":"3"}],"fieldName":"rootNumber","fieldMustInput":"N","isNull":"Y","orderNum":19,"showType":"list","fieldDesc":"并接根数"},{"userTemplateValueId":149,"fieldValue":"2","dicts":null,"fieldName":"cableNumber","fieldMustInput":"N","isNull":"Y","orderNum":20,"showType":"text","fieldDesc":"电缆条数"},{"userTemplateValueId":150,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"10KV"},{"dictCode":"2","dictText":"0.4KV"}],"fieldName":"voltageLevel","fieldMustInput":"N","isNull":"Y","orderNum":21,"showType":"list","fieldDesc":"电压等级"},{"userTemplateValueId":151,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"省（直辖市、自治区）公司"},{"dictCode":"2","dictText":"用户"}],"fieldName":"propertyNature","fieldMustInput":"N","isNull":"Y","orderNum":22,"showType":"list","fieldDesc":"资产性质"},{"userTemplateValueId":152,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"是"},{"dictCode":"2","dictText":"否"}],"fieldName":"isMaintain","fieldMustInput":"N","isNull":"Y","orderNum":23,"showType":"list","fieldDesc":"是否代维"},{"userTemplateValueId":153,"fieldValue":null,"dicts":[{"dictCode":"1","dictText":"丰台区"},{"dictCode":"2","dictText":"海淀区"},{"dictCode":"3","dictText":"门头沟"},{"dictCode":"4","dictText":"房山区"},{"dictCode":"5","dictText":"通州区"},{"dictCode":"6","dictText":"顺义区"},{"dictCode":"7","dictText":"昌平区"},{"dictCode":"8","dictText":"大兴区"},{"dictCode":"9","dictText":"朝阳区"}],"fieldName":"districtId","fieldMustInput":"N","isNull":"Y","orderNum":24,"showType":"list","fieldDesc":"所在区域"},{"userTemplateValueId":154,"fieldValue":"F0001","dicts":null,"fieldName":"factoryCode","fieldMustInput":"N","isNull":"Y","orderNum":25,"showType":"text","fieldDesc":"出厂编号"},{"userTemplateValueId":155,"fieldValue":"1543291307000","dicts":null,"fieldName":"factoryDate","fieldMustInput":"N","isNull":"Y","orderNum":26,"showType":"date","fieldDesc":"出厂日期"},{"userTemplateValueId":156,"fieldValue":"1543291309000","dicts":null,"fieldName":"completDate","fieldMustInput":"N","isNull":"Y","orderNum":27,"showType":"date","fieldDesc":"竣工日期"},{"userTemplateValueId":157,"fieldValue":"8","dicts":[{"dictCode":"8","dictText":"施工单位"}],"fieldName":"buildUnit","fieldMustInput":"N","isNull":"Y","orderNum":28,"showType":"list","fieldDesc":"施工单位"},{"userTemplateValueId":158,"fieldValue":"1543291472000","dicts":null,"fieldName":"deliveryDate","fieldMustInput":"N","isNull":"Y","orderNum":29,"showType":"date","fieldDesc":"投运日期"},{"userTemplateValueId":159,"fieldValue":"1545969874000","dicts":null,"fieldName":"returnDate","fieldMustInput":"N","isNull":"Y","orderNum":30,"showType":"date","fieldDesc":"退运日期"},{"userTemplateValueId":160,"fieldValue":"D0001","dicts":null,"fieldName":"drawCode","fieldMustInput":"N","isNull":"Y","orderNum":31,"showType":"text","fieldDesc":"图纸编号"},{"userTemplateValueId":161,"fieldValue":"2","dicts":[{"dictCode":"2","dictText":"晨光电缆"},{"dictCode":"3","dictText":"青岛汉缆"},{"dictCode":"4","dictText":"江苏上上电缆"},{"dictCode":"5","dictText":"上海飞航电线电缆有限公司"},{"dictCode":"6","dictText":"沈阳电缆"}],"fieldName":"manufacturer","fieldMustInput":"N","isNull":"Y","orderNum":32,"showType":"list","fieldDesc":"生产厂家"},{"userTemplateValueId":162,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"电缆运检一班"},{"dictCode":"2","dictText":"电缆运检二班"}],"fieldName":"team","fieldMustInput":"N","isNull":"Y","orderNum":33,"showType":"list","fieldDesc":"维护班组"},{"userTemplateValueId":163,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"在运"},{"dictCode":"2","dictText":"退运"}],"fieldName":"runStatus","fieldMustInput":"N","isNull":"Y","orderNum":34,"showType":"list","fieldDesc":"运行状态"},{"userTemplateValueId":164,"fieldValue":"1543291518000","dicts":null,"fieldName":"owtsTestDate","fieldMustInput":"N","isNull":"Y","orderNum":35,"showType":"date","fieldDesc":"OTWS试验日期"},{"userTemplateValueId":165,"fieldValue":"1543291516000","dicts":null,"fieldName":"testDate","fieldMustInput":"N","isNull":"Y","orderNum":36,"showType":"date","fieldDesc":"介损试验日期"},{"userTemplateValueId":166,"fieldValue":"1543291520000","dicts":null,"fieldName":"checkDate","fieldMustInput":"N","isNull":"Y","orderNum":37,"showType":"date","fieldDesc":"最近巡视日期"},{"userTemplateValueId":167,"fieldValue":null,"dicts":null,"fieldName":"projectNo","fieldMustInput":"N","isNull":"Y","orderNum":38,"showType":"text","fieldDesc":"工程编号"},{"userTemplateValueId":168,"fieldValue":null,"dicts":null,"fieldName":"ratedVoltage","fieldMustInput":"N","isNull":"Y","orderNum":39,"showType":"text","fieldDesc":"额定电压（KV)"},{"userTemplateValueId":169,"fieldValue":"备注","dicts":null,"fieldName":"remarks","fieldMustInput":"N","isNull":"Y","orderNum":40,"showType":"text","fieldDesc":"备注"},{"userTemplateValueId":170,"fieldValue":"A20181127001","dicts":null,"fieldName":"assetNum","fieldMustInput":"N","isNull":"Y","orderNum":41,"showType":"text","fieldDesc":"资产编号"},{"userTemplateValueId":171,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"城网"},{"dictCode":"2","dictText":"农网"}],"fieldName":"cityNetwork","fieldMustInput":"N","isNull":"Y","orderNum":42,"showType":"list","fieldDesc":"城网/农网"},{"userTemplateValueId":172,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"一般设备"},{"dictCode":"2","dictText":"重要设备"}],"fieldName":"equipmentImport","fieldMustInput":"N","isNull":"Y","orderNum":43,"showType":"list","fieldDesc":"设备重要性"},{"userTemplateValueId":173,"fieldValue":"7","dicts":[{"dictCode":"7","dictText":"海淀供电公司"}],"fieldName":"assetUnit","fieldMustInput":"N","isNull":"Y","orderNum":44,"showType":"list","fieldDesc":"资产单位"},{"userTemplateValueId":174,"fieldValue":"十字路口","dicts":null,"fieldName":"regionalism","fieldMustInput":"N","isNull":"Y","orderNum":45,"showType":"text","fieldDesc":"地区特征"},{"userTemplateValueId":175,"fieldValue":"130米","dicts":null,"fieldName":"discLength","fieldMustInput":"N","isNull":"Y","orderNum":46,"showType":"text","fieldDesc":"盘留长度"},{"userTemplateValueId":176,"fieldValue":"海淀供电公司","dicts":null,"fieldName":"department","fieldMustInput":"N","isNull":"Y","orderNum":47,"showType":"text","fieldDesc":"管理部门"},{"userTemplateValueId":177,"fieldValue":"光华路","dicts":null,"fieldName":"addressName","fieldMustInput":"N","isNull":"Y","orderNum":48,"showType":"text","fieldDesc":"定位"},{"userTemplateValueId":178,"fieldValue":"20","dicts":null,"fieldName":"distanceHead","fieldMustInput":"N","isNull":"Y","orderNum":51,"showType":"text","fieldDesc":"距首端距离（m)"},{"userTemplateValueId":179,"fieldValue":"2","dicts":null,"fieldName":"teamCode","fieldMustInput":"N","isNull":"Y","orderNum":52,"showType":"text","fieldDesc":"维护班组唯一标识"},{"userTemplateValueId":180,"fieldValue":"B0001","dicts":null,"fieldName":"factorySignCode","fieldMustInput":"N","isNull":"Y","orderNum":53,"showType":"text","fieldDesc":"生产厂家唯一标识"},{"userTemplateValueId":181,"fieldValue":"B0001","dicts":null,"fieldName":"belongFactoryCode","fieldMustInput":"N","isNull":"Y","orderNum":54,"showType":"text","fieldDesc":"所属厂家唯一标识"},{"userTemplateValueId":182,"fieldValue":"S00001","dicts":null,"fieldName":"startCode","fieldMustInput":"N","isNull":"Y","orderNum":55,"showType":"text","fieldDesc":"起点唯一标识"},{"userTemplateValueId":183,"fieldValue":"E00001","dicts":null,"fieldName":"endCode","fieldMustInput":"N","isNull":"Y","orderNum":56,"showType":"text","fieldDesc":"终点唯一标识"},{"userTemplateValueId":184,"fieldValue":"20181127001","dicts":null,"fieldName":"recordCode","fieldMustInput":"N","isNull":"Y","orderNum":57,"showType":"text","fieldDesc":"记录唯一标识"},{"userTemplateValueId":185,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"电缆段"},{"dictCode":"2","dictText":"中间头"},{"dictCode":"3","dictText":"终端头"}],"fieldName":"identifyType","fieldMustInput":"N","isNull":"Y","orderNum":58,"showType":"list","fieldDesc":"标识类型"}]}
     */

    private String msg;
    private int code;
    private SignBoardBean signBoard;

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

    public SignBoardBean getSignBoard() {
        return signBoard;
    }

    public void setSignBoard(SignBoardBean signBoard) {
        this.signBoard = signBoard;
    }

    public static class SignBoardBean {
        /**
         * workOrderId : 13
         * userId : 4
         * epc : A11801001001180800000A01
         * imagesUrls : http://47.99.148.150/cableimages/1.jpg
         * longitude : null
         * latitude : null
         * detailAddress : null
         * templateValues : [{"userTemplateValueId":131,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"海淀供电公司"}],"fieldName":"operatingUnit","fieldMustInput":"N","isNull":"Y","orderNum":2,"showType":"list","fieldDesc":"运行单位"},{"userTemplateValueId":132,"fieldValue":"L0001","dicts":null,"fieldName":"lineCode","fieldMustInput":"N","isNull":"Y","orderNum":3,"showType":"text","fieldDesc":"线路编码"},{"userTemplateValueId":133,"fieldValue":"测试001","dicts":null,"fieldName":"lineName","fieldMustInput":"N","isNull":"Y","orderNum":4,"showType":"text","fieldDesc":"线路名称"},{"userTemplateValueId":134,"fieldValue":"CS0001","dicts":null,"fieldName":"cableSegmentCode","fieldMustInput":"N","isNull":"Y","orderNum":5,"showType":"text","fieldDesc":"电缆段编码"},{"userTemplateValueId":135,"fieldValue":"测试开始001","dicts":null,"fieldName":"startPointName","fieldMustInput":"N","isNull":"Y","orderNum":6,"showType":"text","fieldDesc":"起点名称"},{"userTemplateValueId":136,"fieldValue":"测试开始描述001","dicts":null,"fieldName":"startPointDesc","fieldMustInput":"N","isNull":"Y","orderNum":7,"showType":"text","fieldDesc":"起点描述"},{"userTemplateValueId":137,"fieldValue":"测试结束001","dicts":null,"fieldName":"endPointName","fieldMustInput":"N","isNull":"Y","orderNum":8,"showType":"text","fieldDesc":"终点名称"},{"userTemplateValueId":138,"fieldValue":"测试结束描述001","dicts":null,"fieldName":"endPointDesc","fieldMustInput":"N","isNull":"Y","orderNum":9,"showType":"text","fieldDesc":"终点描述"},{"userTemplateValueId":139,"fieldValue":"5000","dicts":null,"fieldName":"cableSize","fieldMustInput":"N","isNull":"Y","orderNum":10,"showType":"text","fieldDesc":"电缆长度（m)"},{"userTemplateValueId":140,"fieldValue":"3","dicts":[{"dictCode":"1","dictText":"70"},{"dictCode":"2","dictText":"95"},{"dictCode":"3","dictText":"120"},{"dictCode":"4","dictText":"150"},{"dictCode":"5","dictText":"185"},{"dictCode":"6","dictText":"240"},{"dictCode":"7","dictText":"300"},{"dictCode":"8","dictText":"400"}],"fieldName":"cableArea","fieldMustInput":"N","isNull":"Y","orderNum":11,"showType":"list","fieldDesc":"电缆截面积（mm2)"},{"userTemplateValueId":141,"fieldValue":"2","dicts":[{"dictCode":"1","dictText":"YJV22"},{"dictCode":"2","dictText":"YJY22"},{"dictCode":"3","dictText":"ZLQD12"},{"dictCode":"4","dictText":"ZLQD22"}],"fieldName":"cableMoble","fieldMustInput":"N","isNull":"Y","orderNum":12,"showType":"list","fieldDesc":"电缆型号"},{"userTemplateValueId":142,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"圆型"},{"dictCode":"2","dictText":"扇形"}],"fieldName":"wireCoreShape","fieldMustInput":"N","isNull":"Y","orderNum":13,"showType":"list","fieldDesc":"线芯形状"},{"userTemplateValueId":143,"fieldValue":"2","dicts":[{"dictCode":"1","dictText":"铜芯"},{"dictCode":"2","dictText":"铝芯"}],"fieldName":"wireCoreMaterial","fieldMustInput":"N","isNull":"Y","orderNum":14,"showType":"list","fieldDesc":"线芯材质"},{"userTemplateValueId":144,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"三芯"},{"dictCode":"2","dictText":"单芯"},{"dictCode":"3","dictText":"四芯"}],"fieldName":"cableCoreCode","fieldMustInput":"N","isNull":"Y","orderNum":15,"showType":"list","fieldDesc":"电缆芯数"},{"userTemplateValueId":145,"fieldValue":"载流","dicts":null,"fieldName":"currentCarryCapacity","fieldMustInput":"N","isNull":"Y","orderNum":16,"showType":"text","fieldDesc":"载流量（A）"},{"userTemplateValueId":146,"fieldValue":"2","dicts":null,"fieldName":"joinNumber","fieldMustInput":"N","isNull":"Y","orderNum":17,"showType":"text","fieldDesc":"中间接头数（个）"},{"userTemplateValueId":147,"fieldValue":"2","dicts":[{"dictCode":"1","dictText":"隧道"},{"dictCode":"2","dictText":"排管"},{"dictCode":"3","dictText":"直埋"},{"dictCode":"4","dictText":"电缆沟"},{"dictCode":"5","dictText":"桥架"}],"fieldName":"layMethod","fieldMustInput":"N","isNull":"Y","orderNum":18,"showType":"list","fieldDesc":"敷设方式"},{"userTemplateValueId":148,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"1"},{"dictCode":"2","dictText":"2"},{"dictCode":"3","dictText":"3"}],"fieldName":"rootNumber","fieldMustInput":"N","isNull":"Y","orderNum":19,"showType":"list","fieldDesc":"并接根数"},{"userTemplateValueId":149,"fieldValue":"2","dicts":null,"fieldName":"cableNumber","fieldMustInput":"N","isNull":"Y","orderNum":20,"showType":"text","fieldDesc":"电缆条数"},{"userTemplateValueId":150,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"10KV"},{"dictCode":"2","dictText":"0.4KV"}],"fieldName":"voltageLevel","fieldMustInput":"N","isNull":"Y","orderNum":21,"showType":"list","fieldDesc":"电压等级"},{"userTemplateValueId":151,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"省（直辖市、自治区）公司"},{"dictCode":"2","dictText":"用户"}],"fieldName":"propertyNature","fieldMustInput":"N","isNull":"Y","orderNum":22,"showType":"list","fieldDesc":"资产性质"},{"userTemplateValueId":152,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"是"},{"dictCode":"2","dictText":"否"}],"fieldName":"isMaintain","fieldMustInput":"N","isNull":"Y","orderNum":23,"showType":"list","fieldDesc":"是否代维"},{"userTemplateValueId":153,"fieldValue":null,"dicts":[{"dictCode":"1","dictText":"丰台区"},{"dictCode":"2","dictText":"海淀区"},{"dictCode":"3","dictText":"门头沟"},{"dictCode":"4","dictText":"房山区"},{"dictCode":"5","dictText":"通州区"},{"dictCode":"6","dictText":"顺义区"},{"dictCode":"7","dictText":"昌平区"},{"dictCode":"8","dictText":"大兴区"},{"dictCode":"9","dictText":"朝阳区"}],"fieldName":"districtId","fieldMustInput":"N","isNull":"Y","orderNum":24,"showType":"list","fieldDesc":"所在区域"},{"userTemplateValueId":154,"fieldValue":"F0001","dicts":null,"fieldName":"factoryCode","fieldMustInput":"N","isNull":"Y","orderNum":25,"showType":"text","fieldDesc":"出厂编号"},{"userTemplateValueId":155,"fieldValue":"1543291307000","dicts":null,"fieldName":"factoryDate","fieldMustInput":"N","isNull":"Y","orderNum":26,"showType":"date","fieldDesc":"出厂日期"},{"userTemplateValueId":156,"fieldValue":"1543291309000","dicts":null,"fieldName":"completDate","fieldMustInput":"N","isNull":"Y","orderNum":27,"showType":"date","fieldDesc":"竣工日期"},{"userTemplateValueId":157,"fieldValue":"8","dicts":[{"dictCode":"8","dictText":"施工单位"}],"fieldName":"buildUnit","fieldMustInput":"N","isNull":"Y","orderNum":28,"showType":"list","fieldDesc":"施工单位"},{"userTemplateValueId":158,"fieldValue":"1543291472000","dicts":null,"fieldName":"deliveryDate","fieldMustInput":"N","isNull":"Y","orderNum":29,"showType":"date","fieldDesc":"投运日期"},{"userTemplateValueId":159,"fieldValue":"1545969874000","dicts":null,"fieldName":"returnDate","fieldMustInput":"N","isNull":"Y","orderNum":30,"showType":"date","fieldDesc":"退运日期"},{"userTemplateValueId":160,"fieldValue":"D0001","dicts":null,"fieldName":"drawCode","fieldMustInput":"N","isNull":"Y","orderNum":31,"showType":"text","fieldDesc":"图纸编号"},{"userTemplateValueId":161,"fieldValue":"2","dicts":[{"dictCode":"2","dictText":"晨光电缆"},{"dictCode":"3","dictText":"青岛汉缆"},{"dictCode":"4","dictText":"江苏上上电缆"},{"dictCode":"5","dictText":"上海飞航电线电缆有限公司"},{"dictCode":"6","dictText":"沈阳电缆"}],"fieldName":"manufacturer","fieldMustInput":"N","isNull":"Y","orderNum":32,"showType":"list","fieldDesc":"生产厂家"},{"userTemplateValueId":162,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"电缆运检一班"},{"dictCode":"2","dictText":"电缆运检二班"}],"fieldName":"team","fieldMustInput":"N","isNull":"Y","orderNum":33,"showType":"list","fieldDesc":"维护班组"},{"userTemplateValueId":163,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"在运"},{"dictCode":"2","dictText":"退运"}],"fieldName":"runStatus","fieldMustInput":"N","isNull":"Y","orderNum":34,"showType":"list","fieldDesc":"运行状态"},{"userTemplateValueId":164,"fieldValue":"1543291518000","dicts":null,"fieldName":"owtsTestDate","fieldMustInput":"N","isNull":"Y","orderNum":35,"showType":"date","fieldDesc":"OTWS试验日期"},{"userTemplateValueId":165,"fieldValue":"1543291516000","dicts":null,"fieldName":"testDate","fieldMustInput":"N","isNull":"Y","orderNum":36,"showType":"date","fieldDesc":"介损试验日期"},{"userTemplateValueId":166,"fieldValue":"1543291520000","dicts":null,"fieldName":"checkDate","fieldMustInput":"N","isNull":"Y","orderNum":37,"showType":"date","fieldDesc":"最近巡视日期"},{"userTemplateValueId":167,"fieldValue":null,"dicts":null,"fieldName":"projectNo","fieldMustInput":"N","isNull":"Y","orderNum":38,"showType":"text","fieldDesc":"工程编号"},{"userTemplateValueId":168,"fieldValue":null,"dicts":null,"fieldName":"ratedVoltage","fieldMustInput":"N","isNull":"Y","orderNum":39,"showType":"text","fieldDesc":"额定电压（KV)"},{"userTemplateValueId":169,"fieldValue":"备注","dicts":null,"fieldName":"remarks","fieldMustInput":"N","isNull":"Y","orderNum":40,"showType":"text","fieldDesc":"备注"},{"userTemplateValueId":170,"fieldValue":"A20181127001","dicts":null,"fieldName":"assetNum","fieldMustInput":"N","isNull":"Y","orderNum":41,"showType":"text","fieldDesc":"资产编号"},{"userTemplateValueId":171,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"城网"},{"dictCode":"2","dictText":"农网"}],"fieldName":"cityNetwork","fieldMustInput":"N","isNull":"Y","orderNum":42,"showType":"list","fieldDesc":"城网/农网"},{"userTemplateValueId":172,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"一般设备"},{"dictCode":"2","dictText":"重要设备"}],"fieldName":"equipmentImport","fieldMustInput":"N","isNull":"Y","orderNum":43,"showType":"list","fieldDesc":"设备重要性"},{"userTemplateValueId":173,"fieldValue":"7","dicts":[{"dictCode":"7","dictText":"海淀供电公司"}],"fieldName":"assetUnit","fieldMustInput":"N","isNull":"Y","orderNum":44,"showType":"list","fieldDesc":"资产单位"},{"userTemplateValueId":174,"fieldValue":"十字路口","dicts":null,"fieldName":"regionalism","fieldMustInput":"N","isNull":"Y","orderNum":45,"showType":"text","fieldDesc":"地区特征"},{"userTemplateValueId":175,"fieldValue":"130米","dicts":null,"fieldName":"discLength","fieldMustInput":"N","isNull":"Y","orderNum":46,"showType":"text","fieldDesc":"盘留长度"},{"userTemplateValueId":176,"fieldValue":"海淀供电公司","dicts":null,"fieldName":"department","fieldMustInput":"N","isNull":"Y","orderNum":47,"showType":"text","fieldDesc":"管理部门"},{"userTemplateValueId":177,"fieldValue":"光华路","dicts":null,"fieldName":"addressName","fieldMustInput":"N","isNull":"Y","orderNum":48,"showType":"text","fieldDesc":"定位"},{"userTemplateValueId":178,"fieldValue":"20","dicts":null,"fieldName":"distanceHead","fieldMustInput":"N","isNull":"Y","orderNum":51,"showType":"text","fieldDesc":"距首端距离（m)"},{"userTemplateValueId":179,"fieldValue":"2","dicts":null,"fieldName":"teamCode","fieldMustInput":"N","isNull":"Y","orderNum":52,"showType":"text","fieldDesc":"维护班组唯一标识"},{"userTemplateValueId":180,"fieldValue":"B0001","dicts":null,"fieldName":"factorySignCode","fieldMustInput":"N","isNull":"Y","orderNum":53,"showType":"text","fieldDesc":"生产厂家唯一标识"},{"userTemplateValueId":181,"fieldValue":"B0001","dicts":null,"fieldName":"belongFactoryCode","fieldMustInput":"N","isNull":"Y","orderNum":54,"showType":"text","fieldDesc":"所属厂家唯一标识"},{"userTemplateValueId":182,"fieldValue":"S00001","dicts":null,"fieldName":"startCode","fieldMustInput":"N","isNull":"Y","orderNum":55,"showType":"text","fieldDesc":"起点唯一标识"},{"userTemplateValueId":183,"fieldValue":"E00001","dicts":null,"fieldName":"endCode","fieldMustInput":"N","isNull":"Y","orderNum":56,"showType":"text","fieldDesc":"终点唯一标识"},{"userTemplateValueId":184,"fieldValue":"20181127001","dicts":null,"fieldName":"recordCode","fieldMustInput":"N","isNull":"Y","orderNum":57,"showType":"text","fieldDesc":"记录唯一标识"},{"userTemplateValueId":185,"fieldValue":"1","dicts":[{"dictCode":"1","dictText":"电缆段"},{"dictCode":"2","dictText":"中间头"},{"dictCode":"3","dictText":"终端头"}],"fieldName":"identifyType","fieldMustInput":"N","isNull":"Y","orderNum":58,"showType":"list","fieldDesc":"标识类型"}]
         */

        private int workOrderId;
        private int userId;
        private String epc;
        private String imagesUrls;
        private Object longitude;
        private Object latitude;
        private Object detailAddress;
        private List<TemplateValuesBean> templateValues;

        public int getWorkOrderId() {
            return workOrderId;
        }

        public void setWorkOrderId(int workOrderId) {
            this.workOrderId = workOrderId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getEpc() {
            return epc;
        }

        public void setEpc(String epc) {
            this.epc = epc;
        }

        public String getImagesUrls() {
            return imagesUrls;
        }

        public void setImagesUrls(String imagesUrls) {
            this.imagesUrls = imagesUrls;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(Object detailAddress) {
            this.detailAddress = detailAddress;
        }

        public List<TemplateValuesBean> getTemplateValues() {
            return templateValues;
        }

        public void setTemplateValues(List<TemplateValuesBean> templateValues) {
            this.templateValues = templateValues;
        }

        public static class TemplateValuesBean {
            /**
             * userTemplateValueId : 131
             * fieldValue : 1
             * dicts : [{"dictCode":"1","dictText":"海淀供电公司"}]
             * fieldName : operatingUnit
             * fieldMustInput : N
             * isNull : Y
             * orderNum : 2
             * showType : list
             * fieldDesc : 运行单位
             */

            private int userTemplateValueId;
            private String fieldValue;
            private String fieldName;
            private String fieldMustInput;
            private String isNull;
            private int orderNum;
            private String showType;
            private String fieldDesc;
            private List<DictsBean> dicts;

            public int getUserTemplateValueId() {
                return userTemplateValueId;
            }

            public void setUserTemplateValueId(int userTemplateValueId) {
                this.userTemplateValueId = userTemplateValueId;
            }

            public String getFieldValue() {
                return fieldValue;
            }

            public void setFieldValue(String fieldValue) {
                this.fieldValue = fieldValue;
            }

            public String getFieldName() {
                return fieldName;
            }

            public void setFieldName(String fieldName) {
                this.fieldName = fieldName;
            }

            public String getFieldMustInput() {
                return fieldMustInput;
            }

            public void setFieldMustInput(String fieldMustInput) {
                this.fieldMustInput = fieldMustInput;
            }

            public String getIsNull() {
                return isNull;
            }

            public void setIsNull(String isNull) {
                this.isNull = isNull;
            }

            public int getOrderNum() {
                return orderNum;
            }

            public void setOrderNum(int orderNum) {
                this.orderNum = orderNum;
            }

            public String getShowType() {
                return showType;
            }

            public void setShowType(String showType) {
                this.showType = showType;
            }

            public String getFieldDesc() {
                return fieldDesc;
            }

            public void setFieldDesc(String fieldDesc) {
                this.fieldDesc = fieldDesc;
            }

            public List<DictsBean> getDicts() {
                return dicts;
            }

            public void setDicts(List<DictsBean> dicts) {
                this.dicts = dicts;
            }

            public static class DictsBean {
                /**
                 * dictCode : 1
                 * dictText : 海淀供电公司
                 */

                private String dictCode;
                private String dictText;

                public String getDictCode() {
                    return dictCode;
                }

                public void setDictCode(String dictCode) {
                    this.dictCode = dictCode;
                }

                public String getDictText() {
                    return dictText;
                }

                public void setDictText(String dictText) {
                    this.dictText = dictText;
                }
            }
        }
    }
}
