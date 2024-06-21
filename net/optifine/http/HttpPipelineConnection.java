/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipelineConnection
/*     */ {
/*     */   private String host;
/*     */   private int port;
/*     */   private Proxy proxy;
/*     */   private List<HttpPipelineRequest> listRequests;
/*     */   private List<HttpPipelineRequest> listRequestsSend;
/*     */   private Socket socket;
/*     */   private InputStream inputStream;
/*     */   private OutputStream outputStream;
/*     */   private HttpPipelineSender httpPipelineSender;
/*     */   private HttpPipelineReceiver httpPipelineReceiver;
/*     */   private int countRequests;
/*     */   private boolean responseReceived;
/*     */   private long keepaliveTimeoutMs;
/*     */   private int keepaliveMaxCount;
/*     */   private long timeLastActivityMs;
/*     */   private boolean terminated;
/*     */   private static final String LF = "\n";
/*     */   public static final int TIMEOUT_CONNECT_MS = 5000;
/*     */   public static final int TIMEOUT_READ_MS = 5000;
/*  36 */   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String host, int port) {
/*  40 */     this(host, port, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String host, int port, Proxy proxy) {
/*  45 */     this.host = null;
/*  46 */     this.port = 0;
/*  47 */     this.proxy = Proxy.NO_PROXY;
/*  48 */     this.listRequests = new LinkedList<>();
/*  49 */     this.listRequestsSend = new LinkedList<>();
/*  50 */     this.socket = null;
/*  51 */     this.inputStream = null;
/*  52 */     this.outputStream = null;
/*  53 */     this.httpPipelineSender = null;
/*  54 */     this.httpPipelineReceiver = null;
/*  55 */     this.countRequests = 0;
/*  56 */     this.responseReceived = false;
/*  57 */     this.keepaliveTimeoutMs = 5000L;
/*  58 */     this.keepaliveMaxCount = 1000;
/*  59 */     this.timeLastActivityMs = System.currentTimeMillis();
/*  60 */     this.terminated = false;
/*  61 */     this.host = host;
/*  62 */     this.port = port;
/*  63 */     this.proxy = proxy;
/*  64 */     this.httpPipelineSender = new HttpPipelineSender(this);
/*  65 */     this.httpPipelineSender.start();
/*  66 */     this.httpPipelineReceiver = new HttpPipelineReceiver(this);
/*  67 */     this.httpPipelineReceiver.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean addRequest(HttpPipelineRequest pr) {
/*  72 */     if (isClosed())
/*     */     {
/*  74 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  78 */     addRequest(pr, this.listRequests);
/*  79 */     addRequest(pr, this.listRequestsSend);
/*  80 */     this.countRequests++;
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRequest(HttpPipelineRequest pr, List<HttpPipelineRequest> list) {
/*  87 */     list.add(pr);
/*  88 */     notifyAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setSocket(Socket s) throws IOException {
/*  93 */     if (!this.terminated) {
/*     */       
/*  95 */       if (this.socket != null)
/*     */       {
/*  97 */         throw new IllegalArgumentException("Already connected");
/*     */       }
/*     */ 
/*     */       
/* 101 */       this.socket = s;
/* 102 */       this.socket.setTcpNoDelay(true);
/* 103 */       this.inputStream = this.socket.getInputStream();
/* 104 */       this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
/* 105 */       onActivity();
/* 106 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
/* 113 */     while (this.outputStream == null) {
/*     */       
/* 115 */       checkTimeout();
/* 116 */       wait(1000L);
/*     */     } 
/*     */     
/* 119 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException, InterruptedException {
/* 124 */     while (this.inputStream == null) {
/*     */       
/* 126 */       checkTimeout();
/* 127 */       wait(1000L);
/*     */     } 
/*     */     
/* 130 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
/* 135 */     if (this.listRequestsSend.size() <= 0 && this.outputStream != null)
/*     */     {
/* 137 */       this.outputStream.flush();
/*     */     }
/*     */     
/* 140 */     return getNextRequest(this.listRequestsSend, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
/* 145 */     return getNextRequest(this.listRequests, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean remove) throws InterruptedException {
/* 150 */     while (list.size() <= 0) {
/*     */       
/* 152 */       checkTimeout();
/* 153 */       wait(1000L);
/*     */     } 
/*     */     
/* 156 */     onActivity();
/*     */     
/* 158 */     if (remove)
/*     */     {
/* 160 */       return list.remove(0);
/*     */     }
/*     */ 
/*     */     
/* 164 */     return list.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTimeout() {
/* 170 */     if (this.socket != null) {
/*     */       
/* 172 */       long i = this.keepaliveTimeoutMs;
/*     */       
/* 174 */       if (this.listRequests.size() > 0)
/*     */       {
/* 176 */         i = 5000L;
/*     */       }
/*     */       
/* 179 */       long j = System.currentTimeMillis();
/*     */       
/* 181 */       if (j > this.timeLastActivityMs + i)
/*     */       {
/* 183 */         terminate(new InterruptedException("Timeout " + i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onActivity() {
/* 190 */     this.timeLastActivityMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onRequestSent(HttpPipelineRequest pr) {
/* 195 */     if (!this.terminated)
/*     */     {
/* 197 */       onActivity();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onResponseReceived(HttpPipelineRequest pr, HttpResponse resp) {
/* 203 */     if (!this.terminated) {
/*     */       
/* 205 */       this.responseReceived = true;
/* 206 */       onActivity();
/*     */       
/* 208 */       if (this.listRequests.size() > 0 && this.listRequests.get(0) == pr) {
/*     */         
/* 210 */         this.listRequests.remove(0);
/* 211 */         pr.setClosed(true);
/* 212 */         String s = resp.getHeader("Location");
/*     */         
/* 214 */         if (resp.getStatus() / 100 == 3 && s != null && pr.getHttpRequest().getRedirects() < 5) {
/*     */           
/*     */           try
/*     */           {
/* 218 */             s = normalizeUrl(s, pr.getHttpRequest());
/* 219 */             HttpRequest httprequest = HttpPipeline.makeRequest(s, pr.getHttpRequest().getProxy());
/* 220 */             httprequest.setRedirects(pr.getHttpRequest().getRedirects() + 1);
/* 221 */             HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, pr.getHttpListener());
/* 222 */             HttpPipeline.addRequest(httppipelinerequest);
/*     */           }
/* 224 */           catch (IOException ioexception)
/*     */           {
/* 226 */             pr.getHttpListener().failed(pr.getHttpRequest(), ioexception);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 231 */           HttpListener httplistener = pr.getHttpListener();
/* 232 */           httplistener.finished(pr.getHttpRequest(), resp);
/*     */         } 
/*     */         
/* 235 */         checkResponseHeader(resp);
/*     */       }
/*     */       else {
/*     */         
/* 239 */         throw new IllegalArgumentException("Response out of order: " + pr);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String normalizeUrl(String url, HttpRequest hr) {
/* 246 */     if (patternFullUrl.matcher(url).matches())
/*     */     {
/* 248 */       return url;
/*     */     }
/* 250 */     if (url.startsWith("//"))
/*     */     {
/* 252 */       return "http:" + url;
/*     */     }
/*     */ 
/*     */     
/* 256 */     String s = hr.getHost();
/*     */     
/* 258 */     if (hr.getPort() != 80)
/*     */     {
/* 260 */       s = s + ":" + hr.getPort();
/*     */     }
/*     */     
/* 263 */     if (url.startsWith("/"))
/*     */     {
/* 265 */       return "http://" + s + url;
/*     */     }
/*     */ 
/*     */     
/* 269 */     String s1 = hr.getFile();
/* 270 */     int i = s1.lastIndexOf("/");
/* 271 */     return (i >= 0) ? ("http://" + s + s1.substring(0, i + 1) + url) : ("http://" + s + "/" + url);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResponseHeader(HttpResponse resp) {
/* 278 */     String s = resp.getHeader("Connection");
/*     */     
/* 280 */     if (s != null && !s.toLowerCase().equals("keep-alive"))
/*     */     {
/* 282 */       terminate(new EOFException("Connection not keep-alive"));
/*     */     }
/*     */     
/* 285 */     String s1 = resp.getHeader("Keep-Alive");
/*     */     
/* 287 */     if (s1 != null) {
/*     */       
/* 289 */       String[] astring = Config.tokenize(s1, ",;");
/*     */       
/* 291 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/* 293 */         String s2 = astring[i];
/* 294 */         String[] astring1 = split(s2, '=');
/*     */         
/* 296 */         if (astring1.length >= 2) {
/*     */           
/* 298 */           if (astring1[0].equals("timeout")) {
/*     */             
/* 300 */             int j = Config.parseInt(astring1[1], -1);
/*     */             
/* 302 */             if (j > 0)
/*     */             {
/* 304 */               this.keepaliveTimeoutMs = (j * 1000);
/*     */             }
/*     */           } 
/*     */           
/* 308 */           if (astring1[0].equals("max")) {
/*     */             
/* 310 */             int k = Config.parseInt(astring1[1], -1);
/*     */             
/* 312 */             if (k > 0)
/*     */             {
/* 314 */               this.keepaliveMaxCount = k;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] split(String str, char separator) {
/* 324 */     int i = str.indexOf(separator);
/*     */     
/* 326 */     if (i < 0)
/*     */     {
/* 328 */       return new String[] { str };
/*     */     }
/*     */ 
/*     */     
/* 332 */     String s = str.substring(0, i);
/* 333 */     String s1 = str.substring(i + 1);
/* 334 */     return new String[] { s, s1 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionSend(HttpPipelineRequest pr, Exception e) {
/* 340 */     terminate(e);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionReceive(HttpPipelineRequest pr, Exception e) {
/* 345 */     terminate(e);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void terminate(Exception e) {
/* 350 */     if (!this.terminated) {
/*     */       
/* 352 */       this.terminated = true;
/* 353 */       terminateRequests(e);
/*     */       
/* 355 */       if (this.httpPipelineSender != null)
/*     */       {
/* 357 */         this.httpPipelineSender.interrupt();
/*     */       }
/*     */       
/* 360 */       if (this.httpPipelineReceiver != null)
/*     */       {
/* 362 */         this.httpPipelineReceiver.interrupt();
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 367 */         if (this.socket != null)
/*     */         {
/* 369 */           this.socket.close();
/*     */         }
/*     */       }
/* 372 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       this.socket = null;
/* 378 */       this.inputStream = null;
/* 379 */       this.outputStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateRequests(Exception e) {
/* 385 */     if (this.listRequests.size() > 0) {
/*     */       
/* 387 */       if (!this.responseReceived) {
/*     */         
/* 389 */         HttpPipelineRequest httppipelinerequest = this.listRequests.remove(0);
/* 390 */         httppipelinerequest.getHttpListener().failed(httppipelinerequest.getHttpRequest(), e);
/* 391 */         httppipelinerequest.setClosed(true);
/*     */       } 
/*     */       
/* 394 */       while (this.listRequests.size() > 0) {
/*     */         
/* 396 */         HttpPipelineRequest httppipelinerequest1 = this.listRequests.remove(0);
/* 397 */         HttpPipeline.addRequest(httppipelinerequest1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isClosed() {
/* 404 */     return this.terminated ? true : ((this.countRequests >= this.keepaliveMaxCount));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCountRequests() {
/* 409 */     return this.countRequests;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean hasActiveRequests() {
/* 414 */     return (this.listRequests.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 419 */     return this.host;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 424 */     return this.port;
/*     */   }
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/* 429 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\http\HttpPipelineConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */