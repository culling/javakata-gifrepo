package com.geneculling.javakata.gifrepo;

import net.java.ao.Entity;

public interface Gif extends Entity{
    String getName();
    String getUrl();
    void setName(String name);
    void setUrl(String s);

}
