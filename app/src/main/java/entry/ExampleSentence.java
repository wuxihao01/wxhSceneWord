package entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
//单词的例句中英文
@Entity
public class ExampleSentence {
    @Id(autoincrement = true)
    Long ID;
    //例句的中文意思
    String SentenceC;
    //例句的英文意思
    String SentenceE;
    @NotNull
    String belongWord;
    @Generated(hash = 47497362)
    public ExampleSentence(Long ID, String SentenceC, String SentenceE,
            @NotNull String belongWord) {
        this.ID = ID;
        this.SentenceC = SentenceC;
        this.SentenceE = SentenceE;
        this.belongWord = belongWord;
    }
    @Generated(hash = 388923778)
    public ExampleSentence() {
    }
    public Long getID() {
        return this.ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getSentenceC() {
        return this.SentenceC;
    }
    public void setSentenceC(String SentenceC) {
        this.SentenceC = SentenceC;
    }
    public String getSentenceE() {
        return this.SentenceE;
    }
    public void setSentenceE(String SentenceE) {
        this.SentenceE = SentenceE;
    }
    public String getBelongWord() {
        return this.belongWord;
    }
    public void setBelongWord(String belongWord) {
        this.belongWord = belongWord;
    }
}
