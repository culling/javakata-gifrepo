package com.geneculling.javakata.gifrepo;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

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
        response.sendError(501, "not implemented");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(501, "not implemented");
    }
}