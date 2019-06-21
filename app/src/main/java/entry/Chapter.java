package entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Chapter {
    @Id(autoincrement = true)
    Long ID;
    String chapterName;
    @NotNull
    String belongPart;
    @Generated(hash = 1312572713)
    public Chapter(Long ID, String chapterName, @NotNull String belongPart) {
        this.ID = ID;
        this.chapterName = chapterName;
        this.belongPart = belongPart;
    }
    @Generated(hash = 393170288)
    public Chapter() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getChapterName() {
        return this.chapterName;
    }
    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
    public String getBelongPart() {
        return this.belongPart;
    }
    public void setBelongPart(String belongPart) {
        this.belongPart = belongPart;
    }
}
