package com.geneculling.javakata.gifrepo.servlets;

import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.templaterenderer.TemplateRenderer;
import com.geneculling.javakata.gifrepo.GifService;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public class GifFormServlet extends HttpServlet {
    private final GifService gifService;
    private final TemplateRenderer renderer;

    @Inject
    public GifFormServlet(GifService gifService, @ComponentImport TemplateRenderer renderer) {
        this.gifService = checkNotNull(gifService);
        this.renderer = renderer;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("contextPath", request.getContextPath());
        renderer.render("templates/home.vm", map, response.getWriter());
        response.flushBuffer();
    }

}