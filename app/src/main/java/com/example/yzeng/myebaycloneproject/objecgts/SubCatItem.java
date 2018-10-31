package com.example.yzeng.myebaycloneproject.objecgts;

public class SubCatItem {

   private String subCatid;
   private String subCatname;
   private String subCatDiscription;
   private String subCatimage;

    public SubCatItem(String scid, String scname, String scdiscription, String scimageurl) {
        this.subCatid = scid;
        this.subCatname = scname;
        this.subCatDiscription = scdiscription;
        this.subCatimage = scimageurl;
    }

    public String getSubCatid() {
        return subCatid;
    }

    public void setSubCatid(String subCatid) {
        this.subCatid = subCatid;
    }

    public String getSubCatname() {
        return subCatname;
    }

    public void setSubCatname(String subCatname) {
        this.subCatname = subCatname;
    }

    public String getSubCatDiscription() {
        return subCatDiscription;
    }

    public void setSubCatDiscription(String subCatDiscription) {
        this.subCatDiscription = subCatDiscription;
    }

    public String getSubCatimage() {
        return subCatimage;
    }

    public void setSubCatimage(String subCatimage) {
        this.subCatimage = subCatimage;
    }
}
