package entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
//词汇的词组⽤法及中⽂释义或英⽂同义表达
@Entity
public class UsageMethod {
    @Id(autoincrement = true)
    Long ID;
    String UseWord;
    String UseChinese;
    @NotNull
    String belongWord;
    @Generated(hash = 1209666272)
    public UsageMethod(Long ID, String UseWord, String UseChinese,
            @NotNull String belongWord) {
        this.ID = ID;
        this.UseWord = UseWord;
        this.UseChinese = UseChinese;
        this.belongWord = belongWord;
    }
    @Generated(hash = 412520440)
    public UsageMethod() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getUseWord() {
        return this.UseWord;
    }
    public void setUseWord(String UseWord) {
        this.UseWord = UseWord;
    }
    public String getUseChinese() {
        return this.UseChinese;
    }
    public void setUseChinese(String UseChinese) {
        this.UseChinese = UseChinese;
    }
    public String getBelongWord() {
        return this.belongWord;
    }
    public void setBelongWord(String belongWord) {
        this.belongWord = belongWord;
    }
}
