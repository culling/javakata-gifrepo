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

import static com.google.common.base.Preconditions.*;

public class GifServlet extends HttpServlet {
    @ComponentImport
    private final ActiveObjects ao;

    @Inject
    public GifServlet(ActiveObjects ao) {
        this.ao = checkNotNull(ao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final PrintWriter writer = response.getWriter();
        writer.write("<h1>Gifs</h1>");

        ao.executeInTransaction(new TransactionCallback<Void>()
        {
            @Override
            public Void doInTransaction()
            {
                for (Gif gif : ao.find(Gif.class))
                {
                    writer.printf("<p>%s <img src=\"%2$s\"/></p>", gif.getName(), gif.getUrl());
                }
                return null;
            }
        });
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String name = request.getParameter("name");
        final String url = request.getParameter("url");

        ao.executeInTransaction(
                new TransactionCallback<Gif>(){
            @Override
            public Gif doInTransaction()
            {
                final Gif gif = ao.create(Gif.class);
                gif.setName(name);
                gif.setUrl(url);
                gif.save();
                return gif;
            }
        });

        response.sendRedirect(request.getContextPath() + "/plugins/servlet/gifrepo");
    }
}