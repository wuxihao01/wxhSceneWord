package contract;

import java.util.List;

import entry.Chapter;
import entry.ExampleSentence;
import entry.Part;
import entry.Scene;
import entry.UsageMethod;
import entry.Word;
import entry.XmlList;

public interface ShowFragmentContract {
    interface Presenter{

        //从数据库获得对应的数据并通过view显示

        public void getPartList(ShowFragmentContract.PartView view);

        public void getChapterList(ShowFragmentContract.ChapterView view,String part);

        public void getSceneList(ShowFragmentContract.SenceView view,String chapter);

        public void getWordList(ShowFragmentContract.WordListView view,String scene);

        public void getWordDetail(ShowFragmentContract.WordView view,String word);

        public void removePart(String name);

        public void removeChapter(String name);

        public void removeScene(String name);

        public void removeWord(String name);

        public void addPart(Part part);

        public void addChapter(Chapter chapter);

        public void addScene(Scene scene);

        public void addWord(Word word);

        public void addUsageMethod(UsageMethod usageMethod);

        public void addSentence(ExampleSentence sentence);

        public boolean querryPary(String name);

        public boolean querryChapter(String name);

        public boolean querryScene(String name);

        public boolean querryWord(String name);

        public boolean querryWordByChinese(String chinese);


        public boolean querryUsageMethod(String name);

        public boolean querrySentence(String name);

        public void updataPart(Part oldItem,String newItem);

        public void updataChapter(Chapter oldItem,String newItem);

        public void updataScene(Scene oldItem,String newItem);

        public void updataWord(Word oldWord,String newEnglish,String newChinese);
    }

    interface PartView{

        public void setPartList(List<Part> partList);

    }

    interface ChapterView{

        public void setChapterList(List<Chapter> chapterList);

    }

    interface SenceView{

        public void setSceneList(List<Scene> sceneList);

    }

    interface WordListView{
        public void setWordList(List<Word> wordList);
    }

    interface WordView{

        public void setUsageMethodList(List<UsageMethod> usageMethodList);

        public void setSentenceList(List<ExampleSentence> sentenceList);

        public void setWord(List<Word> word);
    }
}
