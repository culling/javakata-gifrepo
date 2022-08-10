package com.geneculling.javakata.gifrepo.servlets;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.geneculling.javakata.gifrepo.Gif;
import com.geneculling.javakata.gifrepo.GifService;
import com.geneculling.javakata.utils.ServletUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

public class GifServlet extends HttpServlet {
    private final GifService gifService;
    private final String DEFAULT_IMAGE = "FALLBACK_IMAGE.JPG";
    private final String FALLBACK_IMAGE_RELATIVE_URL = "/download/resources/com.geneculling.javakata.gif-repo:gif-repo-resources/images/" + DEFAULT_IMAGE;

    @Inject
    public GifServlet(GifService gifService) {
        this.gifService = checkNotNull(gifService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String shortUrl = ServletUtils.getShortUrl("/gifrepo/api/gif", request.getPathInfo());

        String directoryName = "javakata-gifrepo";
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }

        String fileName = shortUrl;
        Path path = Paths.get(directoryName, fileName);
        File file = path.toFile();
        if(StringUtils.isBlank(fileName) || ! file.exists() || ! file.canRead()){
            response.sendRedirect(request.getContextPath() + FALLBACK_IMAGE_RELATIVE_URL);
            return;
        }
        String mimeType = Files.probeContentType(path);
        final FileInputStream inStream = new FileInputStream(file);

        response.setContentType(mimeType);
        response.setContentLength((int) file.length());

        final OutputStream outStream = response.getOutputStream();

        final byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inStream.close();
        outStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

        // Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // Parse the request
        String destinationUrl = request.getContextPath() + "/plugins/servlet/gifrepo/api/gif";
        try {
            List<FileItem> items = upload.parseRequest(request);
            String uploadedFileName = writeFiles(items);
            destinationUrl += "/" + uploadedFileName;

            gifService.add(uploadedFileName, destinationUrl);
        } catch (FileUploadException e) {
            throw new RuntimeException("FileUploadException: ", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(destinationUrl);
    }

    public String writeFiles(List<FileItem> fileItems) throws Exception {
        boolean WRITE_TO_FILE = true;
        String uploadedFileName = "";
        if (! WRITE_TO_FILE) {
            return uploadedFileName;
        }
        String directoryName = "javakata-gifrepo";
        File directory = new File(directoryName);
        if (! directory.exists()){
            directory.mkdir();
        }

        Iterator<FileItem> fileItemIterator = fileItems.iterator();
        while (fileItemIterator.hasNext()) {
            try {
                FileItem fileItem = fileItemIterator.next();
                String fileName = fileItem.getName();
                Path path = Paths.get(directoryName, fileName);
                File file = path.toFile();
                if (file.exists()) {
                    file.delete();
                }
                fileItem.write(file);
                uploadedFileName = fileName;
            } catch (Exception e){
                System.err.println(format("Error writing file %s", e.toString()));
            }
        }
        return uploadedFileName;
    }

}