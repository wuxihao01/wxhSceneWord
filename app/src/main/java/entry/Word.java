package entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Word {
    @Id(autoincrement = true)
    Long ID;
    String english;
    String chinese;
    @NotNull
    String belongScene;
    @Generated(hash = 645090811)
    public Word(Long ID, String english, String chinese,
            @NotNull String belongScene) {
        this.ID = ID;
        this.english = english;
        this.chinese = chinese;
        this.belongScene = belongScene;
    }
    @Generated(hash = 3342184)
    public Word() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getEnglish() {
        return this.english;
    }
    public void setEnglish(String english) {
        this.english = english;
    }
    public String getChinese() {
        return this.chinese;
    }
    public void setChinese(String chinese) {
        this.chinese = chinese;
    }
    public String getBelongScene() {
        return this.belongScene;
    }
    public void setBelongScene(String belongScene) {
        this.belongScene = belongScene;
    }
}
