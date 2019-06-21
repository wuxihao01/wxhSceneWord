package presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import base.BaseMvpPresenter;
import contract.ShowFragmentContract;
import entry.Chapter;
import entry.ExampleSentence;
import entry.Part;
import entry.Scene;
import entry.UsageMethod;
import entry.Word;
import entry.XmlList;
import module.DataBaseDao;

public class ShowFragmentPresenter extends BaseMvpPresenter implements ShowFragmentContract.Presenter {
    private static Context mContext;
    DataBaseDao dao;
    public ShowFragmentPresenter(Context context){
        mContext=context;
        dao=DataBaseDao.getInstance(context);
    }


    @Override
    public void getPartList(ShowFragmentContract.PartView view) {
        view.setPartList(dao.querryPartList());
    }

    @Override
    public void getChapterList(ShowFragmentContract.ChapterView view,String part) {
        view.setChapterList(dao.querryChapterList(part));
    }

    @Override
    public void getSceneList(ShowFragmentContract.SenceView view,String chapter) {
        view.setSceneList(dao.querrySceneList(chapter));
    }

    @Override
    public void getWordList(ShowFragmentContract.WordListView view,String scene) {
        view.setWordList(dao.querryWordList(scene));
    }

    public void getWordListByChinese(ShowFragmentContract.WordListView view,String Chinese){
        view.setWordList(dao.querryWordListByChinese(Chinese));
    }

    @Override
    public void getWordDetail(ShowFragmentContract.WordView view,String word) {
        view.setUsageMethodList(dao.querryUsageMethodList(word));
        view.setSentenceList(dao.querrySentenceList(word));
        view.setWord(dao.querryWord(word));
    }

    @Override
    public void removePart(String name) {
        dao.delPart(name);
    }

    @Override
    public void removeChapter(String name) {
        dao.delChapter(name);
    }

    @Override
    public void removeScene(String name) {
        dao.delScene(name);
    }

    @Override
    public void removeWord(String name) {
        dao.delWord(name);
    }

    @Override
    public void addPart(Part part) {
        dao.addPart(part);
    }

    @Override
    public void addChapter(Chapter chapter) {
        dao.addChapter(chapter);
    }

    @Override
    public void addScene(Scene scene) {
        dao.addScene(scene);
    }

    @Override
    public void addWord(Word word) {
        dao.addWord(word);
    }

    @Override
    public void addUsageMethod(UsageMethod usageMethod) {
        dao.addUsageMethod(usageMethod);
    }

    @Override
    public void addSentence(ExampleSentence sentence) {
        dao.addSentence(sentence);
    }

    @Override
    public boolean querryPary(String name) {
        boolean flag=false;
        List<Part> List=dao.querryPart(name);
        if(List.size()>0)flag=true;
        return flag;
    }

    @Override
    public boolean querryChapter(String name) {
        boolean flag=false;
        List<Chapter> List=dao.querryChapter(name);
        if(List.size()>0)flag=true;
        return flag;
    }

    @Override
    public boolean querryScene(String name) {
        boolean flag=false;
        List<Scene> List=dao.querryScene(name);
        if(List.size()>0)flag=true;
        return flag;
    }

    @Override
    public boolean querryWord(String name) {
        boolean flag=false;
        List<Word> List=dao.querryWord(name);
        if(List.size()>0)flag=true;
        return flag;
    }

    @Override
    public boolean querryWordByChinese(String chinese) {
        boolean flag=false;
        List<Word> List=dao.querryWordByChinese(chinese);
        if(List.size()>0)flag=true;
        return flag;
    }


    @Override
    public boolean querryUsageMethod(String name) {
        boolean flag=false;
        List<UsageMethod> List=dao.querryUsageMethod(name);
        if(List.size()>0)flag=true;
        return flag;
    }

    @Override
    public boolean querrySentence(String name) {
        boolean flag=false;
        List<ExampleSentence> List=dao.querrySentence(name);
        if(List.size()>0)flag=true;
        return flag;
    }

    @Override
    public void updataPart(Part oldItem, String newItem) {
        List<Chapter> chapterList=dao.querryChapterList(oldItem.getPartID());
        for(Chapter chapter:chapterList){
            chapter.setBelongPart(newItem);
            dao.updataChapter(chapter);
        }
        oldItem.setPartID(newItem);
        dao.updataPart(oldItem);
    }

    @Override
    public void updataChapter(Chapter oldItem, String newItem) {
        List<Scene> sceneList=dao.querrySceneList(oldItem.getChapterName());
        for(Scene scene:sceneList){
            scene.setBelongChapter(newItem);
            dao.updataScene(scene);
        }
        oldItem.setChapterName(newItem);
        dao.updataChapter(oldItem);
    }

    @Override
    public void updataScene(Scene oldItem, String newItem) {
        List<Word> wordList=dao.querryWordList(oldItem.getSceneName());
        for(Word word:wordList){
            word.setBelongScene(newItem);
            dao.updataWord(word);
        }
        oldItem.setSceneName(newItem);
        dao.updataScene(oldItem);
    }

    @Override
    public void updataWord(Word oldWord, String newEnglish,String newChinese) {
        if(!oldWord.getEnglish().equals(newEnglish)){
            List<UsageMethod> usageMethodList=dao.querryUsageMethodList(oldWord.getEnglish());
            for(UsageMethod usageMethod:usageMethodList){
                usageMethod.setBelongWord(newEnglish);
                dao.updataUsageMethod(usageMethod);
            }
            List<ExampleSentence> sentenceList=dao.querrySentenceList(oldWord.getEnglish());
            for(ExampleSentence sentence:sentenceList){
                sentence.setBelongWord(oldWord.getEnglish());
                dao.updataSentence(sentence);
            }
        }
        oldWord.setChinese(newChinese);
        oldWord.setEnglish(newEnglish);
        dao.updataWord(oldWord);
    }


}
