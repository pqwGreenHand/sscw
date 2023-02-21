package com.zhixin.zhfz.jxkp.entity;

import java.io.Serializable;

/**
 * Created by wangly on 2018/7/8.
 */
public class GdajEntity implements Serializable {
    private int id;
    private String ajbh;/*案件编号1*/
    private String jsdw;/*接收单位1*/
    private String jsdwcode;/*经收单位*/
    private String ajzt;/*案件状态1*/
    private String ajlx;/*案件类型1*/
    private String ab;/*案别1*/
    private String ajmc;/*案件名称1*/
    private String afsj;/*案发时间初值1*/
    private String afdd;/*案发地点详址1*/
    private String jyaq;/*简要案情1*/
    private String rksj;/*入库时间1*/
    private String zbmjxm;/*主办民警姓名1*/
    private String zbmjh;/*主办民警警号1*/
    private String jzbh;/*卷宗编号1*/
    private String slsj;/*受理时间*/
    private String zazt;
    private String jjslh;/*接警受理号*/
    private String  jjfs;/*接警方式*/
    private String zabs;/*专案标识*/
    private String ajh;/*案件号*/
    private String bjsj;/*报警时间*/
    private String fxsj;/*发现时间*/
    private String fasjzz;/*发案时间终值*/
    private String  faddxq;/*发案地点_区县*/
    private String faddjd;/*发案地点街道*/
    private String ssjq;/*所属警区*/
    private String sssq;/*所属社区*/
    private String fady;/*发案地域（取消）*/
    private String facs;/*发案处所*/
    private String fxxs;/*发现形式*/
    private String blyy;/*补立原因*/
    private String zasj;/*作案时机*/
    private String xzcs;/*选择处所*/
    private String xzdx;/*选择对象*/
    private String xzwp;/*选择物品*/
    private String zagj;/*作案工具*/
    private String xzbw;/*选择部位*/
    private String zars;/*作案人数*/
    private String zasd;/*作案手段*/
    private String larq;/*立案日期*/
    private String pasj;/*破案时间*/
    private String jasj;/*结案时间*/
    private String xasj;/*销案时间*/
    private String yssj;/*移送时间*/
    private String jjdw;/*接警单位*/
    private String slr;/*受理人*/
    private String tbr;/*填表人*/
    private String tbsj;/*填表时间*/
    private String fadxzgh;/*发案地政区划*/
    private String ladw;/*立案单位*/
    private String lar;/*立案人*/
    private String zbdw;/*主办单位*/
    private String ajxbr;/*案件协办人*/
    private String barlxdh;/*办案人联系电话*/
    private String lalrr;/*立案录入人*/
    private String larrsj;/*立案录入时间*/
    private String lapzr;/*立案批准人*/
    private String lapzsj;/*立案批时间*/
    private String ahxgr;/*案后修改人*/
    private String azhxgsj;/*案最后修改时间*/
    private String pszt;/*批示状态*/
    private String zbxx;/*督办信息*/
    private String ajly;/*案件来源*/
    private String bmbh;/*部门编号*/
    private String xbzcycn;/*协办侦查员中文姓名*/
    private String zj;/*证据*/
    private String kybh;/*勘验编号*/
    private String scbs;/*上传标识*/
    private String ysdw;/*移送单位*/
    private String yscbr;/*移送承办人*/
    private String zabh;/*主案编号*/
    private String ysbsblaj;/*预审标识补录案件*/
    private String fadw;/*发案单位*/
    private String ejbm;/*二级工作部门*/
    private String bylasj;/*不予立案时间*/
    private String mtzasj;/*苗头转案件时间*/
    private String jtajly;/*具体案件来源*/
    private String fzztlx;/*犯罪主体类型*/
    private String yshsj;/*一审时间*/
    private String essj;/*二审时间*/
    private String jzajbh;/*经侦案件的案件编号*/
    private String shjg;/*初查审核结果*/
    private String sjsp;/*标识是否通过统计数据审批*/
    private String jqh;/*警情号*/
    private String ysqssj;/*移送起诉时间*/
    private String ysdwnew;/*移送单位新*/
    private String yscbrnew;/*移送承办人新*/
    private String yssjnew;/*移送时间新*/
    private String yssadw;/*预审送案单位*/
    private String sldwcn;/*受理单位(中文)*/
    private String abcn;/*案别(中文)*/
    private String kscxbh;/*快速查询编号*/



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getJsdw() {
        return jsdw;
    }

    public void setJsdw(String jsdw) {
        this.jsdw = jsdw;
    }

    public String getJsdwcode() {
        return jsdwcode;
    }

    public void setJsdwcode(String jsdwcode) {
        this.jsdwcode = jsdwcode;
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public String getAjlx() {
        return ajlx;
    }

    public void setAjlx(String ajlx) {
        this.ajlx = ajlx;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }

    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    public String getAfsj() {
        return afsj;
    }

    public void setAfsj(String afsj) {
        this.afsj = afsj;
    }

    public String getAfdd() {
        return afdd;
    }

    public void setAfdd(String afdd) {
        this.afdd = afdd;
    }

    public String getJyaq() {
        return jyaq;
    }

    public void setJyaq(String jyaq) {
        this.jyaq = jyaq;
    }

    public String getRksj() {
        return rksj;
    }

    public void setRksj(String rksj) {
        this.rksj = rksj;
    }

    public String getZbmjxm() {
        return zbmjxm;
    }

    public void setZbmjxm(String zbmjxm) {
        this.zbmjxm = zbmjxm;
    }

    public String getZbmjh() {
        return zbmjh;
    }

    public void setZbmjh(String zbmjh) {
        this.zbmjh = zbmjh;
    }

    public String getJzbh() {
        return jzbh;
    }

    public void setJzbh(String jzbh) {
        this.jzbh = jzbh;
    }

    public String getSlsj() {
        return slsj;
    }

    public void setSlsj(String slsj) {
        this.slsj = slsj;
    }

    public String getJjslh() {
        return jjslh;
    }

    public void setJjslh(String jjslh) {
        this.jjslh = jjslh;
    }

    public String getJjfs() {
        return jjfs;
    }

    public void setJjfs(String jjfs) {
        this.jjfs = jjfs;
    }

    public String getZabs() {
        return zabs;
    }

    public void setZabs(String zabs) {
        this.zabs = zabs;
    }

    public String getAjh() {
        return ajh;
    }

    public void setAjh(String ajh) {
        this.ajh = ajh;
    }

    public String getBjsj() {
        return bjsj;
    }

    public void setBjsj(String bjsj) {
        this.bjsj = bjsj;
    }

    public String getFxsj() {
        return fxsj;
    }

    public void setFxsj(String fxsj) {
        this.fxsj = fxsj;
    }

    public String getFasjzz() {
        return fasjzz;
    }

    public void setFasjzz(String fasjzz) {
        this.fasjzz = fasjzz;
    }

    public String getFaddxq() {
        return faddxq;
    }

    public void setFaddxq(String faddxq) {
        this.faddxq = faddxq;
    }

    public String getFaddjd() {
        return faddjd;
    }

    public void setFaddjd(String faddjd) {
        this.faddjd = faddjd;
    }

    public String getSsjq() {
        return ssjq;
    }

    public void setSsjq(String ssjq) {
        this.ssjq = ssjq;
    }

    public String getSssq() {
        return sssq;
    }

    public void setSssq(String sssq) {
        this.sssq = sssq;
    }

    public String getFady() {
        return fady;
    }

    public void setFady(String fady) {
        this.fady = fady;
    }

    public String getFacs() {
        return facs;
    }

    public void setFacs(String facs) {
        this.facs = facs;
    }

    public String getFxxs() {
        return fxxs;
    }

    public void setFxxs(String fxxs) {
        this.fxxs = fxxs;
    }

    public String getBlyy() {
        return blyy;
    }

    public void setBlyy(String blyy) {
        this.blyy = blyy;
    }

    public String getZasj() {
        return zasj;
    }

    public void setZasj(String zasj) {
        this.zasj = zasj;
    }

    public String getXzcs() {
        return xzcs;
    }

    public void setXzcs(String xzcs) {
        this.xzcs = xzcs;
    }

    public String getXzdx() {
        return xzdx;
    }

    public void setXzdx(String xzdx) {
        this.xzdx = xzdx;
    }

    public String getXzwp() {
        return xzwp;
    }

    public void setXzwp(String xzwp) {
        this.xzwp = xzwp;
    }

    public String getZagj() {
        return zagj;
    }

    public void setZagj(String zagj) {
        this.zagj = zagj;
    }

    public String getXzbw() {
        return xzbw;
    }

    public void setXzbw(String xzbw) {
        this.xzbw = xzbw;
    }

    public String getZars() {
        return zars;
    }

    public void setZars(String zars) {
        this.zars = zars;
    }

    public String getZasd() {
        return zasd;
    }

    public void setZasd(String zasd) {
        this.zasd = zasd;
    }

    public String getLarq() {
        return larq;
    }

    public void setLarq(String larq) {
        this.larq = larq;
    }

    public String getPasj() {
        return pasj;
    }

    public void setPasj(String pasj) {
        this.pasj = pasj;
    }

    public String getJasj() {
        return jasj;
    }

    public void setJasj(String jasj) {
        this.jasj = jasj;
    }

    public String getXasj() {
        return xasj;
    }

    public void setXasj(String xasj) {
        this.xasj = xasj;
    }

    public String getYssj() {
        return yssj;
    }

    public void setYssj(String yssj) {
        this.yssj = yssj;
    }

    public String getJjdw() {
        return jjdw;
    }

    public void setJjdw(String jjdw) {
        this.jjdw = jjdw;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getTbr() {
        return tbr;
    }

    public void setTbr(String tbr) {
        this.tbr = tbr;
    }

    public String getTbsj() {
        return tbsj;
    }

    public void setTbsj(String tbsj) {
        this.tbsj = tbsj;
    }

    public String getFadxzgh() {
        return fadxzgh;
    }

    public void setFadxzgh(String fadxzgh) {
        this.fadxzgh = fadxzgh;
    }

    public String getLadw() {
        return ladw;
    }

    public void setLadw(String ladw) {
        this.ladw = ladw;
    }

    public String getLar() {
        return lar;
    }

    public void setLar(String lar) {
        this.lar = lar;
    }

    public String getZbdw() {
        return zbdw;
    }

    public void setZbdw(String zbdw) {
        this.zbdw = zbdw;
    }

    public String getAjxbr() {
        return ajxbr;
    }

    public void setAjxbr(String ajxbr) {
        this.ajxbr = ajxbr;
    }

    public String getBarlxdh() {
        return barlxdh;
    }

    public void setBarlxdh(String barlxdh) {
        this.barlxdh = barlxdh;
    }

    public String getLalrr() {
        return lalrr;
    }

    public void setLalrr(String lalrr) {
        this.lalrr = lalrr;
    }

    public String getLarrsj() {
        return larrsj;
    }

    public void setLarrsj(String larrsj) {
        this.larrsj = larrsj;
    }

    public String getLapzr() {
        return lapzr;
    }

    public void setLapzr(String lapzr) {
        this.lapzr = lapzr;
    }

    public String getLapzsj() {
        return lapzsj;
    }

    public void setLapzsj(String lapzsj) {
        this.lapzsj = lapzsj;
    }

    public String getAhxgr() {
        return ahxgr;
    }

    public void setAhxgr(String ahxgr) {
        this.ahxgr = ahxgr;
    }

    public String getAzhxgsj() {
        return azhxgsj;
    }

    public void setAzhxgsj(String azhxgsj) {
        this.azhxgsj = azhxgsj;
    }

    public String getPszt() {
        return pszt;
    }

    public void setPszt(String pszt) {
        this.pszt = pszt;
    }

    public String getZbxx() {
        return zbxx;
    }

    public void setZbxx(String zbxx) {
        this.zbxx = zbxx;
    }

    public String getAjly() {
        return ajly;
    }

    public void setAjly(String ajly) {
        this.ajly = ajly;
    }

    public String getBmbh() {
        return bmbh;
    }

    public void setBmbh(String bmbh) {
        this.bmbh = bmbh;
    }

    public String getXbzcycn() {
        return xbzcycn;
    }

    public void setXbzcycn(String xbzcycn) {
        this.xbzcycn = xbzcycn;
    }

    public String getZj() {
        return zj;
    }

    public void setZj(String zj) {
        this.zj = zj;
    }

    public String getKybh() {
        return kybh;
    }

    public void setKybh(String kybh) {
        this.kybh = kybh;
    }

    public String getScbs() {
        return scbs;
    }

    public void setScbs(String scbs) {
        this.scbs = scbs;
    }

    public String getYsdw() {
        return ysdw;
    }

    public void setYsdw(String ysdw) {
        this.ysdw = ysdw;
    }

    public String getYscbr() {
        return yscbr;
    }

    public void setYscbr(String yscbr) {
        this.yscbr = yscbr;
    }

    public String getZabh() {
        return zabh;
    }

    public void setZabh(String zabh) {
        this.zabh = zabh;
    }

    public String getYsbsblaj() {
        return ysbsblaj;
    }

    public void setYsbsblaj(String ysbsblaj) {
        this.ysbsblaj = ysbsblaj;
    }

    public String getFadw() {
        return fadw;
    }

    public void setFadw(String fadw) {
        this.fadw = fadw;
    }

    public String getEjbm() {
        return ejbm;
    }

    public void setEjbm(String ejbm) {
        this.ejbm = ejbm;
    }

    public String getBylasj() {
        return bylasj;
    }

    public void setBylasj(String bylasj) {
        this.bylasj = bylasj;
    }

    public String getMtzasj() {
        return mtzasj;
    }

    public void setMtzasj(String mtzasj) {
        this.mtzasj = mtzasj;
    }

    public String getJtajly() {
        return jtajly;
    }

    public void setJtajly(String jtajly) {
        this.jtajly = jtajly;
    }

    public String getFzztlx() {
        return fzztlx;
    }

    public void setFzztlx(String fzztlx) {
        this.fzztlx = fzztlx;
    }

    public String getYshsj() {
        return yshsj;
    }

    public void setYshsj(String yshsj) {
        this.yshsj = yshsj;
    }

    public String getEssj() {
        return essj;
    }

    public void setEssj(String essj) {
        this.essj = essj;
    }

    public String getJzajbh() {
        return jzajbh;
    }

    public void setJzajbh(String jzajbh) {
        this.jzajbh = jzajbh;
    }

    public String getShjg() {
        return shjg;
    }

    public void setShjg(String shjg) {
        this.shjg = shjg;
    }

    public String getSjsp() {
        return sjsp;
    }

    public void setSjsp(String sjsp) {
        this.sjsp = sjsp;
    }

    public String getJqh() {
        return jqh;
    }

    public void setJqh(String jqh) {
        this.jqh = jqh;
    }

    public String getYsqssj() {
        return ysqssj;
    }

    public void setYsqssj(String ysqssj) {
        this.ysqssj = ysqssj;
    }

    public String getYsdwnew() {
        return ysdwnew;
    }

    public void setYsdwnew(String ysdwnew) {
        this.ysdwnew = ysdwnew;
    }

    public String getYscbrnew() {
        return yscbrnew;
    }

    public void setYscbrnew(String yscbrnew) {
        this.yscbrnew = yscbrnew;
    }

    public String getYssjnew() {
        return yssjnew;
    }

    public void setYssjnew(String yssjnew) {
        this.yssjnew = yssjnew;
    }

    public String getYssadw() {
        return yssadw;
    }

    public void setYssadw(String yssadw) {
        this.yssadw = yssadw;
    }

    public String getSldwcn() {
        return sldwcn;
    }

    public void setSldwcn(String sldwcn) {
        this.sldwcn = sldwcn;
    }

    public String getAbcn() {
        return abcn;
    }

    public void setAbcn(String abcn) {
        this.abcn = abcn;
    }

    public String getKscxbh() {
        return kscxbh;
    }

    public void setKscxbh(String kscxbh) {
        this.kscxbh = kscxbh;
    }
    public String getZazt() {
        return zazt;
    }

    public void setZazt(String zazt) {
        this.zazt = zazt;
    }

    @Override
    public String toString() {
        return "GdajEntity{" +
                "id=" + id +
                ", ajbh='" + ajbh + '\'' +
                ", jsdw='" + jsdw + '\'' +
                ", jsdwcode='" + jsdwcode + '\'' +
                ", ajzt='" + ajzt + '\'' +
                ", ajlx='" + ajlx + '\'' +
                ", ab='" + ab + '\'' +
                ", ajmc='" + ajmc + '\'' +
                ", afsj='" + afsj + '\'' +
                ", afdd='" + afdd + '\'' +
                ", jyaq='" + jyaq + '\'' +
                ", rksj='" + rksj + '\'' +
                ", zbmjxm='" + zbmjxm + '\'' +
                ", zbmjh='" + zbmjh + '\'' +
                ", jzbh='" + jzbh + '\'' +
                ", slsj='" + slsj + '\'' +
                ", zazt='" + zazt + '\'' +
                ", jjslh='" + jjslh + '\'' +
                ", jjfs='" + jjfs + '\'' +
                ", zabs='" + zabs + '\'' +
                ", ajh='" + ajh + '\'' +
                ", bjsj='" + bjsj + '\'' +
                ", fxsj='" + fxsj + '\'' +
                ", fasjzz='" + fasjzz + '\'' +
                ", faddxq='" + faddxq + '\'' +
                ", faddjd='" + faddjd + '\'' +
                ", ssjq='" + ssjq + '\'' +
                ", sssq='" + sssq + '\'' +
                ", fady='" + fady + '\'' +
                ", facs='" + facs + '\'' +
                ", fxxs='" + fxxs + '\'' +
                ", blyy='" + blyy + '\'' +
                ", zasj='" + zasj + '\'' +
                ", xzcs='" + xzcs + '\'' +
                ", xzdx='" + xzdx + '\'' +
                ", xzwp='" + xzwp + '\'' +
                ", zagj='" + zagj + '\'' +
                ", xzbw='" + xzbw + '\'' +
                ", zars='" + zars + '\'' +
                ", zasd='" + zasd + '\'' +
                ", larq='" + larq + '\'' +
                ", pasj='" + pasj + '\'' +
                ", jasj='" + jasj + '\'' +
                ", xasj='" + xasj + '\'' +
                ", yssj='" + yssj + '\'' +
                ", jjdw='" + jjdw + '\'' +
                ", slr='" + slr + '\'' +
                ", tbr='" + tbr + '\'' +
                ", tbsj='" + tbsj + '\'' +
                ", fadxzgh='" + fadxzgh + '\'' +
                ", ladw='" + ladw + '\'' +
                ", lar='" + lar + '\'' +
                ", zbdw='" + zbdw + '\'' +
                ", ajxbr='" + ajxbr + '\'' +
                ", barlxdh='" + barlxdh + '\'' +
                ", lalrr='" + lalrr + '\'' +
                ", larrsj='" + larrsj + '\'' +
                ", lapzr='" + lapzr + '\'' +
                ", lapzsj='" + lapzsj + '\'' +
                ", ahxgr='" + ahxgr + '\'' +
                ", azhxgsj='" + azhxgsj + '\'' +
                ", pszt='" + pszt + '\'' +
                ", zbxx='" + zbxx + '\'' +
                ", ajly='" + ajly + '\'' +
                ", bmbh='" + bmbh + '\'' +
                ", xbzcycn='" + xbzcycn + '\'' +
                ", zj='" + zj + '\'' +
                ", kybh='" + kybh + '\'' +
                ", scbs='" + scbs + '\'' +
                ", ysdw='" + ysdw + '\'' +
                ", yscbr='" + yscbr + '\'' +
                ", zabh='" + zabh + '\'' +
                ", ysbsblaj='" + ysbsblaj + '\'' +
                ", fadw='" + fadw + '\'' +
                ", ejbm='" + ejbm + '\'' +
                ", bylasj='" + bylasj + '\'' +
                ", mtzasj='" + mtzasj + '\'' +
                ", jtajly='" + jtajly + '\'' +
                ", fzztlx='" + fzztlx + '\'' +
                ", yshsj='" + yshsj + '\'' +
                ", essj='" + essj + '\'' +
                ", jzajbh='" + jzajbh + '\'' +
                ", shjg='" + shjg + '\'' +
                ", sjsp='" + sjsp + '\'' +
                ", jqh='" + jqh + '\'' +
                ", ysqssj='" + ysqssj + '\'' +
                ", ysdwnew='" + ysdwnew + '\'' +
                ", yscbrnew='" + yscbrnew + '\'' +
                ", yssjnew='" + yssjnew + '\'' +
                ", yssadw='" + yssadw + '\'' +
                ", sldwcn='" + sldwcn + '\'' +
                ", abcn='" + abcn + '\'' +
                ", kscxbh='" + kscxbh + '\'' +
                '}';
    }
}
