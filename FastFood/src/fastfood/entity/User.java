
package fastfood.entity;

public class User {
    private String user;
    private String pass;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String img;
    private String birth;
    private String roles;
    private boolean gender ;

    public User() {
    }

    
    public User(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public User(String user, String pass, String email) {
        this.user = user;
        this.pass = pass;
        this.email = email;
    }

    public User(String user, String name, String img, String roles) {
        this.user = user;
        this.name = name;
        this.img = img;
        this.roles = roles;
    }

    
    public User(String user, String pass, String name, String phone, String email, String address, String img, String birth, String roles, boolean gender) {
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.img = img;
        this.birth = birth;
        this.roles = roles;
        this.gender = gender;
    }
    

    
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    
}
