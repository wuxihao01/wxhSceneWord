package util;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import entry.*;
import module.DataBaseDao;

public class XmlPullParserUtil {

    private static DataBaseDao dao;
    public static XmlList getAllWord(InputStream is) throws Exception {
        List<Part> partList=null;
        List<Chapter> chapterList=null;
        List<Scene> sceneList = null;
        List<Word> wordList=null;
        List<UsageMethod> usageMethodList=null;
        List<ExampleSentence> sentenceList=null;

        String partName="";
        String chapterName="";
        String sceneName="";
        String wordName="";
        String usageMethodName="";
        String sentenceName="";

        Part partItem=null;
        Chapter chapterItem=null;
        Scene sceneItem=null;
        Word wordItem=null;
        UsageMethod usageMethodItem=null;
        ExampleSentence exampleSentenceItem=null;

        //创建xmlPull解析器
        XmlPullParser parser = Xml.newPullParser();
        ///初始化xmlPull解析器
        parser.setInput(is, "utf-8");
        //读取文件的类型
        int type = parser.getEventType();
        //无限判断文件类型进行读取
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                //开始标签
                case XmlPullParser.START_TAG:
                    if ("english".equals(parser.getName())) {
                        partList = new ArrayList<>();
                        chapterList=new ArrayList<>();
                        sceneList=new ArrayList<>();
                        wordList=new ArrayList<>();
                        usageMethodList=new ArrayList<>();
                        sentenceList=new ArrayList<>();
                    } else if ("part".equals(parser.getName())) {
                        partItem=new Part();
                        partName = parser.getAttributeValue(null, "name");
                        partItem.setPartID(partName);
                    }else if("chapter".equals(parser.getName())){
                        chapterName=parser.getAttributeValue(null, "name");
                        chapterItem=new Chapter();
                        chapterItem.setBelongPart(partName);
                        chapterItem.setChapterName(chapterName);
                    }else if("scene".equals(parser.getName()))
                    {
                        sceneName=parser.getAttributeValue(null, "name");
                        sceneItem=new Scene();
                        sceneItem.setBelongChapter(chapterName);
                        sceneItem.setSceneName(sceneName);
                    }else if("word".equals(parser.getName()))
                    {
                        wordName=parser.getAttributeValue(null, "name");
                        String[] word=wordName.split("~");
                        wordItem=new Word();
                        wordItem.setBelongScene(sceneName);
                        wordItem.setEnglish(word[0]);
                        wordItem.setChinese(word[1]);
                    }else if("use".equals(parser.getName()))
                    {
                        usageMethodName=parser.getAttributeValue(null, "name");
                        String chinese = parser.nextText();
                        usageMethodItem=new UsageMethod();
                        usageMethodItem.setBelongWord(wordName.split("~")[0]);
                        usageMethodItem.setUseWord(usageMethodName);
                        usageMethodItem.setUseChinese(chinese);
                        usageMethodList.add(usageMethodItem);
                    }else if("sentence".equals(parser.getName()))
                    {
                        sentenceName=parser.getAttributeValue(null, "name");
                        String chinese = parser.nextText();
                        exampleSentenceItem=new ExampleSentence();
                        exampleSentenceItem.setBelongWord(wordName.split("~")[0]);
                        exampleSentenceItem.setSentenceE(sentenceName);
                        exampleSentenceItem.setSentenceC(chinese);
                        sentenceList.add(exampleSentenceItem);
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:
                    if ("part".equals(parser.getName())) {
                        partList.add(partItem);
                        partItem=null;
                        partName="";
                    }else if("chapter".equals(parser.getName())){
                        chapterList.add(chapterItem);
                        chapterName="";
                    }else if("scene".equals(parser.getName())){
                        sceneList.add(sceneItem);
                        sceneName="";
                    }else if("word".equals(parser.getName())){
                        wordList.add(wordItem);
                        wordName="";
                    }
                    break;

            }
            //继续往下读取标签类型
            type = parser.next();
        }
        XmlList xmlList=new XmlList(partList,chapterList,sceneList,wordList,usageMethodList,sentenceList);
        return xmlList;
    }

    public static Boolean writeXmlFromDB(Context context){
        List<Part> partList=null;
        List<Chapter> chapterList=null;
        List<Scene> sceneList = null;
        List<Word> wordList=null;
        List<UsageMethod> usageMethodList=null;
        List<ExampleSentence> sentenceList=null;
        FileOutputStream fos = null;
        boolean isSuccess;
        dao=DataBaseDao.getInstance(context);
        partList=dao.querryPartList();
        try {
            File file = new File(Environment.getExternalStorageDirectory(),
                    "wxh.xml");
            fos = new FileOutputStream(file);
            // 获取xml序列化器
            XmlSerializer xs = Xml.newSerializer();
            xs.setOutput(fos, "utf-8");
            //生成xml头
            xs.startDocument("utf-8", true);
            //添加xml根节点
            xs.startTag(null,"english");
            for(Part part:partList){
                xs.startTag(null,"part");
                xs.attribute(null,"name",part.getPartID());
                chapterList=dao.querryChapterList(part.getPartID());
                for(Chapter chapter:chapterList){
                    xs.startTag(null,"chapter");
                    xs.attribute(null,"name",chapter.getChapterName());
                    sceneList=dao.querrySceneList(chapter.getChapterName());
                    for(Scene scene:sceneList){
                        xs.startTag(null,"scene");
                        xs.attribute(null,"name",scene.getSceneName());
                        wordList=dao.querryWordList(scene.getSceneName());
                        for(Word word:wordList){
                            xs.startTag(null,"word");
                            String name=word.getEnglish()+"~"+word.getChinese();
                            xs.attribute(null,"name",name);
                            usageMethodList=dao.querryUsageMethodList(word.getEnglish());
                            sentenceList=dao.querrySentenceList(word.getEnglish());
                            if(usageMethodList.size()>0){
                                for(UsageMethod usageMethod:usageMethodList){
                                    xs.startTag(null,"use");
                                    xs.attribute(null,"name",usageMethod.getUseWord());
                                    xs.text(usageMethod.getUseChinese());
                                    xs.endTag(null,"use");
                                }
                            }
                            if(sentenceList.size()>0){
                                for(ExampleSentence sentence:sentenceList){
                                    xs.startTag(null,"sentence");
                                    xs.attribute(null,"name",sentence.getSentenceE());
                                    xs.text(sentence.getSentenceC());
                                    xs.endTag(null,"sentence");
                                }
                            }
                            xs.endTag(null,"word");
                        }
                        xs.endTag(null,"scene");
                    }
                    xs.endTag(null,"chapter");
                }
                xs.endTag(null,"part");
            }
            xs.endTag(null, "english");
            //生成xml头
            xs.endDocument();
            isSuccess=true;
        }catch (Exception e){
            e.printStackTrace();
            isSuccess=false;
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }
}
