package contract;

import entry.XmlList;

public interface ReadWriteContract {
    interface Presenter{

        void partWriteToDB(XmlList xmlList);

        void readFromXML();

        void writeToXml();
    }
    interface View{
        void getAllDB(XmlList xmlList);

        //显示XML是否成功生成
        void showResult(Boolean isSuccess);
    }
}
