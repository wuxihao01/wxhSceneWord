package entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Part {
    @Id(autoincrement = true)
    Long ID;
    String PartID;
    @Generated(hash = 1667861058)
    public Part(Long ID, String PartID) {
        this.ID = ID;
        this.PartID = PartID;
    }
    @Generated(hash = 130301790)
    public Part() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getPartID() {
        return this.PartID;
    }
    public void setPartID(String PartID) {
        this.PartID = PartID;
    }
}
