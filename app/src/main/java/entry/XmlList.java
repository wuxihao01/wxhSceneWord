package entry;

import java.util.List;

public class XmlList {

    public XmlList(List<Part> partList, List<Chapter> chapterList, List<Scene> sceneList, List<Word> wordList, List<UsageMethod> usageMethodList, List<ExampleSentence> exampleSentenceList) {
        this.partList = partList;
        this.chapterList = chapterList;
        this.sceneList = sceneList;
        this.wordList = wordList;
        this.usageMethodList = usageMethodList;
        this.exampleSentenceList = exampleSentenceList;
    }

    List<Part> partList;
    List<Chapter> chapterList;
    List<Scene> sceneList;
    List<Word> wordList;
    List<UsageMethod> usageMethodList;
    List<ExampleSentence> exampleSentenceList;


    public List<Part> getPartList() {
        return partList;
    }

    public void setPartList(List<Part> partList) {
        this.partList = partList;
    }
    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }


    public List<Scene> getSceneList() {
        return sceneList;
    }

    public void setSceneList(List<Scene> sceneList) {
        this.sceneList = sceneList;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public List<UsageMethod> getUsageMethodList() {
        return usageMethodList;
    }

    public void setUsageMethodList(List<UsageMethod> usageMethodList) {
        this.usageMethodList = usageMethodList;
    }

    public List<ExampleSentence> getExampleSentenceList() {
        return exampleSentenceList;
    }

    public void setExampleSentenceList(List<ExampleSentence> exampleSentenceList) {
        this.exampleSentenceList = exampleSentenceList;
    }
}
