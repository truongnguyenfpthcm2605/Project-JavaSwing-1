
package fastfood.entity;

public class UserStore extends User{
    private String note;

    public UserStore(String user, String img, String name, String note, String roles) {
        super(user, name, img, roles);
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
