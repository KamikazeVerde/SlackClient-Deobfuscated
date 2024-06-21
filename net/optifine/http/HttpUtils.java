/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpUtils
/*     */ {
/*  18 */   private static String playerItemsUrl = null;
/*     */   public static final String SERVER_URL = "http://s.optifine.net";
/*     */   public static final String POST_URL = "http://optifine.net";
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/*     */     byte[] abyte1;
/*  24 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  29 */       URL url = new URL(urlStr);
/*  30 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  31 */       httpurlconnection.setDoInput(true);
/*  32 */       httpurlconnection.setDoOutput(false);
/*  33 */       httpurlconnection.connect();
/*     */       
/*  35 */       if (httpurlconnection.getResponseCode() / 100 != 2) {
/*     */         
/*  37 */         if (httpurlconnection.getErrorStream() != null)
/*     */         {
/*  39 */           Config.readAll(httpurlconnection.getErrorStream());
/*     */         }
/*     */         
/*  42 */         throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
/*     */       } 
/*     */       
/*  45 */       InputStream inputstream = httpurlconnection.getInputStream();
/*  46 */       byte[] abyte = new byte[httpurlconnection.getContentLength()];
/*  47 */       int i = 0;
/*     */ 
/*     */       
/*     */       do {
/*  51 */         int j = inputstream.read(abyte, i, abyte.length - i);
/*     */         
/*  53 */         if (j < 0)
/*     */         {
/*  55 */           throw new IOException("Input stream closed: " + urlStr);
/*     */         }
/*     */         
/*  58 */         i += j;
/*     */       }
/*  60 */       while (i < abyte.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  66 */       abyte1 = abyte;
/*     */     }
/*     */     finally {
/*     */       
/*  70 */       if (httpurlconnection != null)
/*     */       {
/*  72 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return abyte1;
/*     */   }
/*     */   
/*     */   public static String post(String urlStr, Map headers, byte[] content) throws IOException {
/*     */     String s3;
/*  81 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       URL url = new URL(urlStr);
/*  87 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  88 */       httpurlconnection.setRequestMethod("POST");
/*     */       
/*  90 */       if (headers != null)
/*     */       {
/*  92 */         for (Object e : headers.keySet()) {
/*     */           
/*  94 */           String s = (String)e;
/*  95 */           String s1 = "" + headers.get(s);
/*  96 */           httpurlconnection.setRequestProperty(s, s1);
/*     */         } 
/*     */       }
/*     */       
/* 100 */       httpurlconnection.setRequestProperty("Content-Type", "text/plain");
/* 101 */       httpurlconnection.setRequestProperty("Content-Length", "" + content.length);
/* 102 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/* 103 */       httpurlconnection.setUseCaches(false);
/* 104 */       httpurlconnection.setDoInput(true);
/* 105 */       httpurlconnection.setDoOutput(true);
/* 106 */       OutputStream outputstream = httpurlconnection.getOutputStream();
/* 107 */       outputstream.write(content);
/* 108 */       outputstream.flush();
/* 109 */       outputstream.close();
/* 110 */       InputStream inputstream = httpurlconnection.getInputStream();
/* 111 */       InputStreamReader inputstreamreader = new InputStreamReader(inputstream, StandardCharsets.US_ASCII);
/* 112 */       BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 113 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s2;
/* 116 */       while ((s2 = bufferedreader.readLine()) != null) {
/*     */         
/* 118 */         stringbuffer.append(s2);
/* 119 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 122 */       bufferedreader.close();
/* 123 */       s3 = stringbuffer.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 127 */       if (httpurlconnection != null)
/*     */       {
/* 129 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/* 133 */     return s3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized String getPlayerItemsUrl() {
/* 138 */     if (playerItemsUrl == null) {
/*     */ 
/*     */       
/*     */       try {
/* 142 */         boolean flag = Config.parseBoolean(System.getProperty("player.models.local"), false);
/*     */         
/* 144 */         if (flag)
/*     */         {
/* 146 */           File file1 = (Minecraft.getMinecraft()).mcDataDir;
/* 147 */           File file2 = new File(file1, "playermodels");
/* 148 */           playerItemsUrl = file2.toURI().toURL().toExternalForm();
/*     */         }
/*     */       
/* 151 */       } catch (Exception exception) {
/*     */         
/* 153 */         Config.warn("" + exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */       
/* 156 */       if (playerItemsUrl == null)
/*     */       {
/* 158 */         playerItemsUrl = "http://s.optifine.net";
/*     */       }
/*     */     } 
/*     */     
/* 162 */     return playerItemsUrl;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\http\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */