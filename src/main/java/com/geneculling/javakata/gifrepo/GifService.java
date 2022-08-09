package com.geneculling.javakata.gifrepo;

import com.atlassian.activeobjects.tx.Transactional;

import java.util.List;

@Transactional
public interface GifService {
    Gif add(String name, String url);
    List<Gif> all();
    void clear();
}
