package com.example.yzeng.myebaycloneproject.objecgts;

public class MainCatItem {

    private String MainCatid;
    private String MainCatname;
    private String MainCatdiscription;
    private String MainCatimage;

    public MainCatItem(String cid, String cname, String cdiscription, String cimagerl){
        this.MainCatid = cid;
        this.MainCatname = cname;
        this.MainCatdiscription = cdiscription;
        this.MainCatimage = cimagerl;
    }

    public String getMainCatid() {
        return MainCatid;
    }

    public void setMainCatid(String mainCatid) {
        this.MainCatid = mainCatid;
    }

    public String getMainCatname() {
        return MainCatname;
    }

    public void setMainCatname(String mainCatname) {
        this.MainCatname = mainCatname;
    }

    public String getCdiscription() {
        return MainCatdiscription;
    }

    public void setCdiscription(String cdiscription) {
        this.MainCatdiscription = cdiscription;
    }

    public String getMainCatimage() {
        return MainCatimage;
    }

    public void setMainCatimage(String mainCatimage) {
        this.MainCatimage = mainCatimage;
    }
}
