/*     */ package net.optifine;
/*     */ 
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.http.FileUploadThread;
/*     */ import net.optifine.http.IFileUploadListener;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CrashReporter
/*     */ {
/*     */   public static void onCrashReport(CrashReport crashReport, CrashReportCategory category) {
/*     */     try {
/*  20 */       Throwable throwable = crashReport.getCrashCause();
/*     */       
/*  22 */       if (throwable == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  27 */       if (throwable.getClass().getName().contains(".fml.client.SplashProgress")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  32 */       extendCrashReport(category);
/*     */       
/*  34 */       if (throwable.getClass() == Throwable.class) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  39 */       GameSettings gamesettings = Config.getGameSettings();
/*     */       
/*  41 */       if (gamesettings == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  46 */       if (!gamesettings.snooperEnabled) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  51 */       String s = "http://optifine.net/crashReport";
/*  52 */       String s1 = makeReport(crashReport);
/*  53 */       byte[] abyte = s1.getBytes(StandardCharsets.US_ASCII);
/*  54 */       IFileUploadListener ifileuploadlistener = new IFileUploadListener()
/*     */         {
/*     */           public void fileUploadFinished(String url, byte[] content, Throwable exception) {}
/*     */         };
/*     */ 
/*     */       
/*  60 */       Map<Object, Object> map = new HashMap<>();
/*  61 */       map.put("OF-Version", Config.getVersion());
/*  62 */       map.put("OF-Summary", makeSummary(crashReport));
/*  63 */       FileUploadThread fileuploadthread = new FileUploadThread(s, map, abyte, ifileuploadlistener);
/*  64 */       fileuploadthread.setPriority(10);
/*  65 */       fileuploadthread.start();
/*  66 */       Thread.sleep(1000L);
/*     */     }
/*  68 */     catch (Exception exception) {
/*     */       
/*  70 */       Config.dbg(exception.getClass().getName() + ": " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeReport(CrashReport crashReport) {
/*  76 */     StringBuffer stringbuffer = new StringBuffer();
/*  77 */     stringbuffer.append("OptiFineVersion: " + Config.getVersion() + "\n");
/*  78 */     stringbuffer.append("Summary: " + makeSummary(crashReport) + "\n");
/*  79 */     stringbuffer.append("\n");
/*  80 */     stringbuffer.append(crashReport.getCompleteReport());
/*  81 */     stringbuffer.append("\n");
/*  82 */     return stringbuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeSummary(CrashReport crashReport) {
/*  87 */     Throwable throwable = crashReport.getCrashCause();
/*     */     
/*  89 */     if (throwable == null)
/*     */     {
/*  91 */       return "Unknown";
/*     */     }
/*     */ 
/*     */     
/*  95 */     StackTraceElement[] astacktraceelement = throwable.getStackTrace();
/*  96 */     String s = "unknown";
/*     */     
/*  98 */     if (astacktraceelement.length > 0)
/*     */     {
/* 100 */       s = astacktraceelement[0].toString().trim();
/*     */     }
/*     */     
/* 103 */     String s1 = throwable.getClass().getName() + ": " + throwable.getMessage() + " (" + crashReport.getDescription() + ") [" + s + "]";
/* 104 */     return s1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void extendCrashReport(CrashReportCategory cat) {
/* 110 */     cat.addCrashSection("OptiFine Version", Config.getVersion());
/* 111 */     cat.addCrashSection("OptiFine Build", Config.getBuild());
/*     */     
/* 113 */     if (Config.getGameSettings() != null) {
/*     */       
/* 115 */       cat.addCrashSection("Render Distance Chunks", "" + Config.getChunkViewDistance());
/* 116 */       cat.addCrashSection("Mipmaps", "" + Config.getMipmapLevels());
/* 117 */       cat.addCrashSection("Anisotropic Filtering", "" + Config.getAnisotropicFilterLevel());
/* 118 */       cat.addCrashSection("Antialiasing", "" + Config.getAntialiasingLevel());
/* 119 */       cat.addCrashSection("Multitexture", "" + Config.isMultiTexture());
/*     */     } 
/*     */     
/* 122 */     cat.addCrashSection("Shaders", "" + Shaders.getShaderPackName());
/* 123 */     cat.addCrashSection("OpenGlVersion", "" + Config.openGlVersion);
/* 124 */     cat.addCrashSection("OpenGlRenderer", "" + Config.openGlRenderer);
/* 125 */     cat.addCrashSection("OpenGlVendor", "" + Config.openGlVendor);
/* 126 */     cat.addCrashSection("CpuCount", "" + Config.getAvailableProcessors());
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CrashReporter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */