/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipeline
/*     */ {
/*  15 */   private static Map mapConnections = new HashMap<>();
/*     */   
/*     */   public static final String HEADER_USER_AGENT = "User-Agent";
/*     */   public static final String HEADER_HOST = "Host";
/*     */   public static final String HEADER_ACCEPT = "Accept";
/*     */   public static final String HEADER_LOCATION = "Location";
/*     */   public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
/*     */   public static final String HEADER_CONNECTION = "Connection";
/*     */   public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
/*     */   public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
/*     */   public static final String HEADER_VALUE_CHUNKED = "chunked";
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener) throws IOException {
/*  28 */     addRequest(urlStr, listener, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
/*  33 */     HttpRequest httprequest = makeRequest(urlStr, proxy);
/*  34 */     HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, listener);
/*  35 */     addRequest(httppipelinerequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
/*  40 */     URL url = new URL(urlStr);
/*     */     
/*  42 */     if (!url.getProtocol().equals("http"))
/*     */     {
/*  44 */       throw new IOException("Only protocol http is supported: " + url);
/*     */     }
/*     */ 
/*     */     
/*  48 */     String s = url.getFile();
/*  49 */     String s1 = url.getHost();
/*  50 */     int i = url.getPort();
/*     */     
/*  52 */     if (i <= 0)
/*     */     {
/*  54 */       i = 80;
/*     */     }
/*     */     
/*  57 */     String s2 = "GET";
/*  58 */     String s3 = "HTTP/1.1";
/*  59 */     Map<String, String> map = new LinkedHashMap<>();
/*  60 */     map.put("User-Agent", "Java/" + System.getProperty("java.version"));
/*  61 */     map.put("Host", s1);
/*  62 */     map.put("Accept", "text/html, image/gif, image/png");
/*  63 */     map.put("Connection", "keep-alive");
/*  64 */     byte[] abyte = new byte[0];
/*  65 */     HttpRequest httprequest = new HttpRequest(s1, i, proxy, s2, s, s3, map, abyte);
/*  66 */     return httprequest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addRequest(HttpPipelineRequest pr) {
/*  72 */     HttpRequest httprequest = pr.getHttpRequest();
/*     */     
/*  74 */     for (HttpPipelineConnection httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()); !httppipelineconnection.addRequest(pr); httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()))
/*     */     {
/*  76 */       removeConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy(), httppipelineconnection);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
/*  82 */     String s = makeConnectionKey(host, port, proxy);
/*  83 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  85 */     if (httppipelineconnection == null) {
/*     */       
/*  87 */       httppipelineconnection = new HttpPipelineConnection(host, port, proxy);
/*  88 */       mapConnections.put(s, httppipelineconnection);
/*     */     } 
/*     */     
/*  91 */     return httppipelineconnection;
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
/*  96 */     String s = makeConnectionKey(host, port, proxy);
/*  97 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  99 */     if (httppipelineconnection == hpc)
/*     */     {
/* 101 */       mapConnections.remove(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeConnectionKey(String host, int port, Proxy proxy) {
/* 107 */     String s = host + ":" + port + "-" + proxy;
/* 108 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr) throws IOException {
/* 113 */     return get(urlStr, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String urlStr, Proxy proxy) throws IOException {
/* 118 */     if (urlStr.startsWith("file:")) {
/*     */       
/* 120 */       URL url = new URL(urlStr);
/* 121 */       InputStream inputstream = url.openStream();
/* 122 */       byte[] abyte = Config.readAll(inputstream);
/* 123 */       return abyte;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     HttpRequest httprequest = makeRequest(urlStr, proxy);
/* 128 */     HttpResponse httpresponse = executeRequest(httprequest);
/*     */     
/* 130 */     if (httpresponse.getStatus() / 100 != 2)
/*     */     {
/* 132 */       throw new IOException("HTTP response: " + httpresponse.getStatus());
/*     */     }
/*     */ 
/*     */     
/* 136 */     return httpresponse.getBody();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpResponse executeRequest(HttpRequest req) throws IOException {
/* 143 */     final Map<String, Object> map = new HashMap<>();
/* 144 */     String s = "Response";
/* 145 */     String s1 = "Exception";
/* 146 */     HttpListener httplistener = new HttpListener()
/*     */       {
/*     */         public void finished(HttpRequest req, HttpResponse resp)
/*     */         {
/* 150 */           synchronized (map) {
/*     */             
/* 152 */             map.put("Response", resp);
/* 153 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */         
/*     */         public void failed(HttpRequest req, Exception e) {
/* 158 */           synchronized (map) {
/*     */             
/* 160 */             map.put("Exception", e);
/* 161 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 166 */     synchronized (map) {
/*     */       
/* 168 */       HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(req, httplistener);
/* 169 */       addRequest(httppipelinerequest);
/*     */ 
/*     */       
/*     */       try {
/* 173 */         map.wait();
/*     */       }
/* 175 */       catch (InterruptedException var10) {
/*     */         
/* 177 */         throw new InterruptedIOException("Interrupted");
/*     */       } 
/*     */       
/* 180 */       Exception exception = (Exception)map.get("Exception");
/*     */       
/* 182 */       if (exception != null) {
/*     */         
/* 184 */         if (exception instanceof IOException)
/*     */         {
/* 186 */           throw (IOException)exception;
/*     */         }
/* 188 */         if (exception instanceof RuntimeException)
/*     */         {
/* 190 */           throw (RuntimeException)exception;
/*     */         }
/*     */ 
/*     */         
/* 194 */         throw new RuntimeException(exception.getMessage(), exception);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 199 */       HttpResponse httpresponse = (HttpResponse)map.get("Response");
/*     */       
/* 201 */       if (httpresponse == null)
/*     */       {
/* 203 */         throw new IOException("Response is null");
/*     */       }
/*     */ 
/*     */       
/* 207 */       return httpresponse;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasActiveRequests() {
/* 215 */     for (Object e : mapConnections.values()) {
/*     */       
/* 217 */       HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)e;
/* 218 */       if (httppipelineconnection.hasActiveRequests())
/*     */       {
/* 220 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 224 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\http\HttpPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */