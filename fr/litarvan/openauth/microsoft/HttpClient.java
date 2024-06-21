/*     */ package fr.litarvan.openauth.microsoft;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpClient
/*     */ {
/*     */   public static final String MIME_TYPE_JSON = "application/json";
/*     */   public static final String MIME_TYPE_URLENCODED_FORM = "application/x-www-form-urlencoded";
/*     */   private final Gson gson;
/*     */   private final Proxy proxy;
/*     */   
/*     */   public HttpClient() {
/*  41 */     this(Proxy.NO_PROXY);
/*     */   }
/*     */   
/*     */   public HttpClient(Proxy proxy) {
/*  45 */     this.gson = new Gson();
/*  46 */     this.proxy = proxy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText(String url, Map<String, String> params) throws MicrosoftAuthenticationException {
/*  52 */     return readResponse(createConnection(url + '?' + buildParams(params)));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getJson(String url, String token, Class<T> responseClass) throws MicrosoftAuthenticationException {
/*  57 */     HttpURLConnection connection = createConnection(url);
/*  58 */     connection.addRequestProperty("Authorization", "Bearer " + token);
/*  59 */     connection.addRequestProperty("Accept", "application/json");
/*     */     
/*  61 */     return readJson(connection, responseClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpURLConnection postForm(String url, Map<String, String> params) throws MicrosoftAuthenticationException {
/*  66 */     return post(url, "application/x-www-form-urlencoded", "*/*", buildParams(params));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T postJson(String url, Object request, Class<T> responseClass) throws MicrosoftAuthenticationException {
/*  71 */     HttpURLConnection connection = post(url, "application/json", "application/json", this.gson.toJson(request));
/*  72 */     return readJson(connection, responseClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T postFormGetJson(String url, Map<String, String> params, Class<T> responseClass) throws MicrosoftAuthenticationException {
/*  77 */     return readJson(postForm(url, params), responseClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpURLConnection post(String url, String contentType, String accept, String data) throws MicrosoftAuthenticationException {
/*  83 */     HttpURLConnection connection = createConnection(url);
/*  84 */     connection.setDoOutput(true);
/*  85 */     connection.addRequestProperty("Content-Type", contentType);
/*  86 */     connection.addRequestProperty("Accept", accept);
/*     */     
/*     */     try {
/*  89 */       connection.setRequestMethod("POST");
/*  90 */       connection.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));
/*  91 */     } catch (IOException e) {
/*  92 */       throw new MicrosoftAuthenticationException(e);
/*     */     } 
/*     */     
/*  95 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> T readJson(HttpURLConnection connection, Class<T> responseType) throws MicrosoftAuthenticationException {
/* 100 */     return (T)this.gson.fromJson(readResponse(connection), responseType);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String readResponse(HttpURLConnection connection) throws MicrosoftAuthenticationException {
/* 105 */     String redirection = connection.getHeaderField("Location");
/* 106 */     if (redirection != null) {
/* 107 */       return readResponse(createConnection(redirection));
/*     */     }
/*     */     
/* 110 */     StringBuilder response = new StringBuilder();
/*     */ 
/*     */     
/*     */     try {
/* 114 */       InputStream inputStream = connection.getInputStream();
/*     */ 
/*     */       
/* 117 */       if (checkUrl(connection.getURL())) {
/*     */ 
/*     */         
/* 120 */         ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/*     */         
/* 122 */         byte[] data = new byte[8192];
/*     */         int n;
/* 124 */         while ((n = inputStream.read(data, 0, data.length)) != -1) {
/* 125 */           buffer.write(data, 0, n);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 132 */         byte[] patched = buffer.toString("UTF-8").replaceAll("integrity ?=", "integrity.disabled=").replaceAll("setAttribute\\(\"integrity\"", "setAttribute(\"integrity.disabled\"").getBytes(StandardCharsets.UTF_8);
/*     */         
/* 134 */         inputStream = new ByteArrayInputStream(patched);
/*     */       } 
/*     */       
/* 137 */       try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
/*     */         String line;
/* 139 */         while ((line = br.readLine()) != null) {
/* 140 */           response.append(line).append('\n');
/*     */         }
/* 142 */       } catch (IOException e) {
/* 143 */         throw new MicrosoftAuthenticationException(e);
/*     */       } 
/* 145 */     } catch (IOException e) {
/*     */       
/* 147 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 150 */     return response.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkUrl(URL url) {
/* 155 */     return (("login.microsoftonline.com".equals(url.getHost()) && url.getPath().endsWith("/oauth2/authorize")) || ("login.live.com"
/* 156 */       .equals(url.getHost()) && "/oauth20_authorize.srf".equals(url.getPath())) || ("login.live.com"
/* 157 */       .equals(url.getHost()) && "/ppsecure/post.srf".equals(url.getPath())) || ("login.microsoftonline.com"
/* 158 */       .equals(url.getHost()) && "/login.srf".equals(url.getPath())) || ("login.microsoftonline.com"
/* 159 */       .equals(url.getHost()) && url.getPath().endsWith("/login")) || ("login.microsoftonline.com"
/* 160 */       .equals(url.getHost()) && url.getPath().endsWith("/SAS/ProcessAuth")) || ("login.microsoftonline.com"
/* 161 */       .equals(url.getHost()) && url.getPath().endsWith("/federation/oauth2")) || ("login.microsoftonline.com"
/* 162 */       .equals(url.getHost()) && url.getPath().endsWith("/oauth2/v2.0/authorize")));
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpURLConnection followRedirects(HttpURLConnection connection) throws MicrosoftAuthenticationException {
/* 167 */     String redirection = connection.getHeaderField("Location");
/* 168 */     if (redirection != null) {
/* 169 */       connection = followRedirects(createConnection(redirection));
/*     */     }
/*     */     
/* 172 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String buildParams(Map<String, String> params) {
/* 177 */     StringBuilder query = new StringBuilder();
/* 178 */     params.forEach((key, value) -> {
/*     */           if (query.length() > 0) {
/*     */             query.append('&');
/*     */           }
/*     */           
/*     */           try {
/*     */             query.append(key).append('=').append(URLEncoder.encode(value, StandardCharsets.UTF_8.name()));
/* 185 */           } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 190 */     return query.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   protected HttpURLConnection createConnection(String url) throws MicrosoftAuthenticationException {
/*     */     HttpURLConnection connection;
/*     */     try {
/* 197 */       connection = (HttpURLConnection)(new URL(url)).openConnection(this.proxy);
/* 198 */     } catch (IOException e) {
/* 199 */       throw new MicrosoftAuthenticationException(e);
/*     */     } 
/*     */     
/* 202 */     String userAgent = "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     connection.setConnectTimeout(30000);
/* 208 */     connection.setReadTimeout(60000);
/* 209 */     connection.setRequestProperty("Accept-Language", "en-US");
/* 210 */     connection.setRequestProperty("Accept-Charset", "UTF-8");
/* 211 */     connection.setRequestProperty("User-Agent", userAgent);
/*     */     
/* 213 */     return connection;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\fr\litarvan\openauth\microsoft\HttpClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */