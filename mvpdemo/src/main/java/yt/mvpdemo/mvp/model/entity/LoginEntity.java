package yt.mvpdemo.mvp.model.entity;

/**
 * Created by ${zhangyuanchao} on 2017/12/12.
 */

public class LoginEntity {

    /**
     * mid : 182
     * avatar : /Upload/imgs/avatar/adv7861510557231.jpg
     * gender : 1
     * name : 卢志涛
     * m_number : 0094170711
     * branch_shop_number : 7230170711
     * member_level : 3
     * ispay_pwd : 1
     * encodes : /Upload/qrcode/claile692d052a427c3d983a41f570040641b6.png
     */

    private String mid;
    private String avatar;
    private String gender;
    private String name;
    private String m_number;
    private String branch_shop_number;
    private String member_level;
    private int ispay_pwd;
    private String encodes;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getM_number() {
        return m_number;
    }

    public void setM_number(String m_number) {
        this.m_number = m_number;
    }

    public String getBranch_shop_number() {
        return branch_shop_number;
    }

    public void setBranch_shop_number(String branch_shop_number) {
        this.branch_shop_number = branch_shop_number;
    }

    public String getMember_level() {
        return member_level;
    }

    public void setMember_level(String member_level) {
        this.member_level = member_level;
    }

    public int getIspay_pwd() {
        return ispay_pwd;
    }

    public void setIspay_pwd(int ispay_pwd) {
        this.ispay_pwd = ispay_pwd;
    }

    public String getEncodes() {
        return encodes;
    }

    public void setEncodes(String encodes) {
        this.encodes = encodes;
    }
}
