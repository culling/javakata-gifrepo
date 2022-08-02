package com.geneculling.javakata.gifrepo;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static com.google.common.base.Preconditions.checkNotNull;

public class GifServlet extends HttpServlet {
    private final GifService gifService;
    @Inject
    public GifServlet(GifService gifService) {
        this.gifService = checkNotNull(gifService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PrintWriter writer = response.getWriter();
        writer.write("<h1>Gifs</h1>");
        for (Gif gif : gifService.all()) {
            writer.printf("<p>%s <img src=\"%2$s\"/></p>", gif.getName(), gif.getUrl());
        }
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String name = request.getParameter("name");
        final String url = request.getParameter("url");
        gifService.add(name, url);
        response.sendRedirect(request.getContextPath() + "/plugins/servlet/gifrepo");
    }
}