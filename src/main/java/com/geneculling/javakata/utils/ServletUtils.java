package com.geneculling.javakata.utils;

public class ServletUtils {
    public static String getShortUrl(String basePath, String fullPath) {
        String rawShortUrl = fullPath.substring(basePath.length());
        String shortUrl = rawShortUrl.startsWith("/") ? rawShortUrl.substring(1) : rawShortUrl;
        return shortUrl;
    }
}