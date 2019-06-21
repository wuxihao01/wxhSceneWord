package contract;

import entry.XmlList;

public interface ReadWriteContract {
    interface Presenter{

        void partWriteToDB(XmlList xmlList);

        void readFromXML();

    }
    interface View{
        void getAllDB(XmlList xmlList);
    }
}
