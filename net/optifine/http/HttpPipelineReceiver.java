/*     */ package net.optifine.http;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ public class HttpPipelineReceiver
/*     */   extends Thread {
/*  15 */   private HttpPipelineConnection httpPipelineConnection = null;
/*  16 */   private static final Charset ASCII = StandardCharsets.US_ASCII;
/*     */   
/*     */   private static final String HEADER_CONTENT_LENGTH = "Content-Length";
/*     */   private static final char CR = '\r';
/*     */   private static final char LF = '\n';
/*     */   
/*     */   public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnection) {
/*  23 */     super("HttpPipelineReceiver");
/*  24 */     this.httpPipelineConnection = httpPipelineConnection;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  29 */     while (!Thread.interrupted()) {
/*     */       
/*  31 */       HttpPipelineRequest httppipelinerequest = null;
/*     */ 
/*     */       
/*     */       try {
/*  35 */         httppipelinerequest = this.httpPipelineConnection.getNextRequestReceive();
/*  36 */         InputStream inputstream = this.httpPipelineConnection.getInputStream();
/*  37 */         HttpResponse httpresponse = readResponse(inputstream);
/*  38 */         this.httpPipelineConnection.onResponseReceived(httppipelinerequest, httpresponse);
/*     */       }
/*  40 */       catch (InterruptedException var4) {
/*     */         
/*     */         return;
/*     */       }
/*  44 */       catch (Exception exception) {
/*     */         
/*  46 */         this.httpPipelineConnection.onExceptionReceive(httppipelinerequest, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpResponse readResponse(InputStream in) throws IOException {
/*  53 */     String s = readLine(in);
/*  54 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/*  56 */     if (astring.length < 3)
/*     */     {
/*  58 */       throw new IOException("Invalid status line: " + s);
/*     */     }
/*     */ 
/*     */     
/*  62 */     String s1 = astring[0];
/*  63 */     int i = Config.parseInt(astring[1], 0);
/*  64 */     String s2 = astring[2];
/*  65 */     Map<String, String> map = new LinkedHashMap<>();
/*     */ 
/*     */     
/*     */     while (true) {
/*  69 */       String s3 = readLine(in);
/*     */       
/*  71 */       if (s3.length() <= 0) {
/*     */         
/*  73 */         byte[] abyte = null;
/*  74 */         String s6 = map.get("Content-Length");
/*     */         
/*  76 */         if (s6 != null) {
/*     */           
/*  78 */           int k = Config.parseInt(s6, -1);
/*     */           
/*  80 */           if (k > 0)
/*     */           {
/*  82 */             abyte = new byte[k];
/*  83 */             readFull(abyte, in);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  88 */           String s7 = map.get("Transfer-Encoding");
/*     */           
/*  90 */           if (Config.equals(s7, "chunked"))
/*     */           {
/*  92 */             abyte = readContentChunked(in);
/*     */           }
/*     */         } 
/*     */         
/*  96 */         return new HttpResponse(i, s, map, abyte);
/*     */       } 
/*     */       
/*  99 */       int j = s3.indexOf(":");
/*     */       
/* 101 */       if (j > 0) {
/*     */         
/* 103 */         String s4 = s3.substring(0, j).trim();
/* 104 */         String s5 = s3.substring(j + 1).trim();
/* 105 */         map.put(s4, s5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] readContentChunked(InputStream in) throws IOException {
/*     */     int i;
/* 113 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*     */ 
/*     */     
/*     */     do {
/* 117 */       String s = readLine(in);
/* 118 */       String[] astring = Config.tokenize(s, "; ");
/* 119 */       i = Integer.parseInt(astring[0], 16);
/* 120 */       byte[] abyte = new byte[i];
/* 121 */       readFull(abyte, in);
/* 122 */       bytearrayoutputstream.write(abyte);
/* 123 */       readLine(in);
/*     */     }
/* 125 */     while (i != 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return bytearrayoutputstream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFull(byte[] buf, InputStream in) throws IOException {
/*     */     int i;
/* 138 */     for (i = 0; i < buf.length; i += j) {
/*     */       
/* 140 */       int j = in.read(buf, i, buf.length - i);
/*     */       
/* 142 */       if (j < 0)
/*     */       {
/* 144 */         throw new EOFException();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String readLine(InputStream in) throws IOException {
/* 151 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 152 */     int i = -1;
/* 153 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     while (true) {
/* 157 */       int j = in.read();
/*     */       
/* 159 */       if (j < 0) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 164 */       bytearrayoutputstream.write(j);
/*     */       
/* 166 */       if (i == 13 && j == 10) {
/*     */         
/* 168 */         flag = true;
/*     */         
/*     */         break;
/*     */       } 
/* 172 */       i = j;
/*     */     } 
/*     */     
/* 175 */     byte[] abyte = bytearrayoutputstream.toByteArray();
/* 176 */     String s = new String(abyte, ASCII);
/*     */     
/* 178 */     if (flag)
/*     */     {
/* 180 */       s = s.substring(0, s.length() - 2);
/*     */     }
/*     */     
/* 183 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\http\HttpPipelineReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */