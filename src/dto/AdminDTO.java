package dto;

public class AdminDTO {
    private String adminId;
    private String adminName;
    private String adminNic;
    private String adminContactNo;;
    private String adminUserName;
    private String adminPassword;
    private String adminImage;

    public AdminDTO() {
    }

    public AdminDTO(String adminId, String adminName, String adminNic, String adminContactNo, String adminUserName, String adminPassword, String adminImage) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminNic = adminNic;
        this.adminContactNo = adminContactNo;
        this.adminUserName = adminUserName;
        this.adminPassword = adminPassword;
        this.adminImage = adminImage;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminNic() {
        return adminNic;
    }

    public void setAdminNic(String adminNic) {
        this.adminNic = adminNic;
    }

    public String getAdminContactNo() {
        return adminContactNo;
    }

    public void setAdminContactNo(String adminContactNo) {
        this.adminContactNo = adminContactNo;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminImage() {
        return adminImage;
    }

    public void setAdminImage(String adminImage) {
        this.adminImage = adminImage;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminNic='" + adminNic + '\'' +
                ", adminContactNo='" + adminContactNo + '\'' +
                ", adminUserName='" + adminUserName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminImage='" + adminImage + '\'' +
                '}';
    }
}
