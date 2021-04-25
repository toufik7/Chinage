package sample;

public class ele {
    private String fait;
    private String action;
    private Integer num;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }




    public String getFait() {
        return fait;
    }

    public void setFait(String fait) {
        this.fait = fait;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public ele(String fait,String action ,int num){
        this.fait = fait;
        this.action = action;
        this.num = num;
    }
    public ele(String fait){
        this.fait = fait;
        this.action = "";
        this.num = null;
    }
}
