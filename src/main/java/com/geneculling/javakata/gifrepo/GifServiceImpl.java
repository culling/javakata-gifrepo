package com.geneculling.javakata.gifrepo;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;


@Named
public class GifServiceImpl implements GifService{
    private final static String DEFAULT_URL = "https://i.imgur.com/DZTbj.gif";
    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public GifServiceImpl(ActiveObjects ao)
    {
        this.ao = checkNotNull(ao);
    }

    @Override
    public Gif add(String name, String url) {
        final Gif gif = ao.create(Gif.class);
        gif.setName(name);
        gif.setUrl(url);
        gif.save();
        return gif;
    }

    @Override
    public List<Gif> all() {
        return newArrayList(ao.find(Gif.class));
    }
}
