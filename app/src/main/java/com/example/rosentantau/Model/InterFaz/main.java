package com.example.rosentantau.Model.InterFaz;

import com.example.rosentantau.Model.TAB.mainTab;
import com.example.rosentantau.Tools.DAO;

import java.util.List;

public interface main extends DAO <Long, mainTab,String>{
    String send(List<mainTab> ls);
}
