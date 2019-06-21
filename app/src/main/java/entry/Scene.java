package entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Scene {
    @Id(autoincrement = true)
    Long ID;
    String sceneName;
    @NotNull
    String belongChapter;
    @Generated(hash = 1592481620)
    public Scene(Long ID, String sceneName, @NotNull String belongChapter) {
        this.ID = ID;
        this.sceneName = sceneName;
        this.belongChapter = belongChapter;
    }
    @Generated(hash = 1074887510)
    public Scene() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getSceneName() {
        return this.sceneName;
    }
    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }
    public String getBelongChapter() {
        return this.belongChapter;
    }
    public void setBelongChapter(String belongChapter) {
        this.belongChapter = belongChapter;
    }
}
