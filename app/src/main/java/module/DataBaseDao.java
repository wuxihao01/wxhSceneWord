package module;

import android.content.Context;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import entry.Chapter;
import entry.ChapterDao;
import entry.DaoMaster;
import entry.DaoSession;
import entry.ExampleSentence;
import entry.ExampleSentenceDao;
import entry.Part;
import entry.PartDao;
import entry.Scene;
import entry.SceneDao;
import entry.UsageMethod;
import entry.UsageMethodDao;
import entry.Word;
import entry.WordDao;
import entry.XmlList;

public class DataBaseDao {
    private static DataBaseDao instance=new DataBaseDao();
    private static Context mContext;
    private static String DATA_BASE_NAME="wuxihao";
    private static DaoSession mDaoSession;
    private DataBaseDao(){}

    public static DataBaseDao getInstance(Context context){
        mContext=context;
        DaoMaster.DevOpenHelper openHelper=new DaoMaster.DevOpenHelper(context,DATA_BASE_NAME);
        Database db=openHelper.getWritableDb();
        DaoMaster daoMaster=new DaoMaster(db);
        mDaoSession=daoMaster.newSession();
        return instance;
    }

    public static DaoSession getmDaoSession(){
        return mDaoSession;
    }

    public void addAll(XmlList xmlList){
        List<Part> partList=xmlList.getPartList();
        List<Chapter> chapterList=xmlList.getChapterList();
        List<Scene> sceneList=xmlList.getSceneList();
        List<Word> wordList=xmlList.getWordList();
        List<UsageMethod> usageMethodList=xmlList.getUsageMethodList();
        List<ExampleSentence> sentenceList=xmlList.getExampleSentenceList();
        mDaoSession.getPartDao().insertInTx(partList);
        mDaoSession.getChapterDao().insertInTx(chapterList);
        mDaoSession.getSceneDao().insertInTx(sceneList);
        mDaoSession.getWordDao().insertInTx(wordList);
        mDaoSession.getUsageMethodDao().insertInTx(usageMethodList);
        mDaoSession.getExampleSentenceDao().insertInTx(sentenceList);
        for(Part part:partList){
            Log.d("ummmmm","part:"+part.getPartID());
        }
        for(Chapter chapter:chapterList){
            Log.d("ummmmm","chaptername:"+chapter.getChapterName()+"   chapterbelong:"+chapter.getBelongPart());
        }
        for(Scene item:sceneList){
            Log.d("ummmmm","sencename:"+item.getSceneName()+"   sencebelong:"+item.getBelongChapter());
        }
        for(Word item:wordList){
            Log.d("ummmmm","wordname:"+item.getEnglish()+"   wordChinese:"+item.getChinese()+"    wordbelong:"+item.getBelongScene());
        }
        for(UsageMethod item:usageMethodList){
            Log.d("ummmmm","usagename:"+item.getUseWord()+"      usageChinese:"+item.getUseChinese()+"   usagebelong:"+item.getBelongWord());
        }
        for(ExampleSentence item:sentenceList){
            Log.d("ummmmm","sentencename:"+item.getSentenceE()+"      sentenceChinese:"+item.getSentenceC()+"   sentencebelong:"+item.getBelongWord());
        }
    }

    public void addPart(Part part){
        mDaoSession.getPartDao().insert(part);
    }

    public void addPartList(List<Part> partList){
        mDaoSession.getPartDao().insertInTx(partList);
    }

    public void delPart(String name){
        mDaoSession.getPartDao().queryBuilder()
                .where(PartDao.Properties.PartID.eq(name))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public void addChapter(Chapter chapter){
        mDaoSession.getChapterDao().insert(chapter);
    }

    public void addChapterList(List<Chapter> chapterListList){
        mDaoSession.getChapterDao().insertInTx(chapterListList);
    }

    public void delChapter(String name){
        mDaoSession.getChapterDao().queryBuilder()
                .where(ChapterDao.Properties.ChapterName.eq(name))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public void addScene(Scene scene){
        mDaoSession.getSceneDao().insert(scene);
    }

    public void addSceneList(List<Scene> sceneList){
        mDaoSession.getSceneDao().insertInTx(sceneList);
    }

    public void delScene(String name){
        mDaoSession.getSceneDao().queryBuilder()
                .where(SceneDao.Properties.SceneName.eq(name))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public void addWord(Word word){
        mDaoSession.getWordDao().insert(word);
    }

    public void addWordList(List<Word> wordList){
        mDaoSession.getWordDao().insertInTx(wordList);
    }

    public void delWord(String name){
        mDaoSession.getWordDao().queryBuilder()
                .where(WordDao.Properties.English.eq(name))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public void addUsageMethod(UsageMethod usageMethod){
        mDaoSession.getUsageMethodDao().insert(usageMethod);
    }

    public void addUsageMethodList(List<UsageMethod> usageMethodList){
        mDaoSession.getUsageMethodDao().insertInTx(usageMethodList);
    }

    public void delUsageMethod(String name){
        mDaoSession.getUsageMethodDao().queryBuilder()
                .where(UsageMethodDao.Properties.UseWord.eq(name))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public void addSentence(ExampleSentence sentence){
        mDaoSession.getExampleSentenceDao().insert(sentence);
    }

    public void addSentenceList(List<ExampleSentence> sentenceList){
        mDaoSession.getExampleSentenceDao().insertInTx(sentenceList);
    }

    public void delSentence(String name){
        mDaoSession.getExampleSentenceDao().queryBuilder()
                .where(ExampleSentenceDao.Properties.SentenceE.eq(name))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }

    public List<Part> querryPartList(){
        return mDaoSession.getPartDao()
                .queryBuilder()
                .orderAsc(PartDao.Properties.PartID)
                .list();
    }

    public List<Chapter> querryChapterList(String part){
        return mDaoSession.getChapterDao()
                .queryBuilder()
                .where(ChapterDao.Properties.BelongPart.eq(part))
                .orderAsc(ChapterDao.Properties.ChapterName)
                .list();
    }

    public List<Scene> querrySceneList(String chapter){
        return mDaoSession.getSceneDao()
                .queryBuilder()
                .where(SceneDao.Properties.BelongChapter.eq(chapter))
                .orderAsc(SceneDao.Properties.SceneName)
                .list();
    }

    public List<Word> querryWordList(String scene){
        return mDaoSession.getWordDao()
                .queryBuilder()
                .where(WordDao.Properties.BelongScene.eq(scene))
                .orderAsc(WordDao.Properties.English)
                .list();
    }

    public List<Word> querryWordListByChinese(String Chinese){
        return mDaoSession.getWordDao()
                .queryBuilder()
                .where(WordDao.Properties.Chinese.eq(Chinese))
                .orderAsc(WordDao.Properties.English)
                .list();
    }

    public List<ExampleSentence> querrySentenceList(String word){
        return mDaoSession.getExampleSentenceDao()
                .queryBuilder()
                .where(ExampleSentenceDao.Properties.BelongWord.eq(word))
                .orderAsc(ExampleSentenceDao.Properties.SentenceE)
                .list();
    }

    public List<UsageMethod> querryUsageMethodList(String word){
        return mDaoSession.getUsageMethodDao()
                .queryBuilder()
                .where(UsageMethodDao.Properties.BelongWord.eq(word))
                .orderAsc(UsageMethodDao.Properties.UseWord)
                .list();
    }

    public List<Part> querryPart(String name){
        return mDaoSession.getPartDao()
                .queryBuilder()
                .where(PartDao.Properties.PartID.eq(name))
                .orderAsc(PartDao.Properties.PartID)
                .list();
    }

    public List<Chapter> querryChapter(String name){
        return mDaoSession.getChapterDao()
                .queryBuilder()
                .where(ChapterDao.Properties.ChapterName.eq(name))
                .orderAsc(ChapterDao.Properties.ChapterName)
                .list();
    }

    public List<Scene> querryScene(String name){
        return mDaoSession.getSceneDao()
                .queryBuilder()
                .where(SceneDao.Properties.SceneName.eq(name))
                .orderAsc(SceneDao.Properties.SceneName)
                .list();
    }

    public List<Word> querryWord(String name){
        return mDaoSession.getWordDao()
                .queryBuilder()
                .where(WordDao.Properties.English.eq(name))
                .orderAsc(WordDao.Properties.English)
                .list();
    }

    public List<Word> querryWordByChinese(String name){
        return mDaoSession.getWordDao()
                .queryBuilder()
                .where(WordDao.Properties.Chinese.eq(name))
                .orderAsc(WordDao.Properties.English)
                .list();
    }

    public List<ExampleSentence> querrySentence(String name){
        return mDaoSession.getExampleSentenceDao()
                .queryBuilder()
                .where(ExampleSentenceDao.Properties.SentenceE.eq(name))
                .orderAsc(ExampleSentenceDao.Properties.SentenceE)
                .list();
    }

    public List<UsageMethod> querryUsageMethod(String name){
        return mDaoSession.getUsageMethodDao()
                .queryBuilder()
                .where(UsageMethodDao.Properties.UseWord.eq(name))
                .orderAsc(UsageMethodDao.Properties.UseWord)
                .list();
    }

    public void updataPart(Part part){
        mDaoSession.getPartDao()
                .update(part);
    }

    public void updataChapter(Chapter chapter){
        mDaoSession.getChapterDao()
                .update(chapter);
    }

    public void updataScene(Scene scene){
        mDaoSession.getSceneDao()
                .update(scene);
    }

    public void updataWord(Word word){
        mDaoSession.getWordDao()
                .update(word);
    }

    public void updataUsageMethod(UsageMethod usageMethod){
        mDaoSession.getUsageMethodDao()
                .update(usageMethod);
    }

    public void updataSentence(ExampleSentence sentence){
        mDaoSession.getExampleSentenceDao()
                .update(sentence);
    }
}
