/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ import net.optifine.CrashReporter;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  26 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final String description;
/*     */ 
/*     */   
/*     */   private final Throwable cause;
/*     */ 
/*     */   
/*  35 */   private final CrashReportCategory theReportCategory = new CrashReportCategory(this, "System Details");
/*  36 */   private final List<CrashReportCategory> crashReportSections = Lists.newArrayList();
/*     */   
/*     */   private File crashReportFile;
/*     */   
/*     */   private boolean field_85059_f = true;
/*  41 */   private StackTraceElement[] stacktrace = new StackTraceElement[0];
/*     */   
/*     */   private boolean reported = false;
/*     */   
/*     */   public CrashReport(String descriptionIn, Throwable causeThrowable) {
/*  46 */     this.description = descriptionIn;
/*  47 */     this.cause = causeThrowable;
/*  48 */     populateEnvironment();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateEnvironment() {
/*  57 */     this.theReportCategory.addCrashSectionCallable("Minecraft Version", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  61 */             return "1.8.9";
/*     */           }
/*     */         });
/*  64 */     this.theReportCategory.addCrashSectionCallable("Operating System", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  68 */             return System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version");
/*     */           }
/*     */         });
/*  71 */     this.theReportCategory.addCrashSectionCallable("Java Version", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  75 */             return System.getProperty("java.version") + ", " + System.getProperty("java.vendor");
/*     */           }
/*     */         });
/*  78 */     this.theReportCategory.addCrashSectionCallable("Java VM Version", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  82 */             return System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor");
/*     */           }
/*     */         });
/*  85 */     this.theReportCategory.addCrashSectionCallable("Memory", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*  89 */             Runtime runtime = Runtime.getRuntime();
/*  90 */             long i = runtime.maxMemory();
/*  91 */             long j = runtime.totalMemory();
/*  92 */             long k = runtime.freeMemory();
/*  93 */             long l = i / 1024L / 1024L;
/*  94 */             long i1 = j / 1024L / 1024L;
/*  95 */             long j1 = k / 1024L / 1024L;
/*  96 */             return k + " bytes (" + j1 + " MB) / " + j + " bytes (" + i1 + " MB) up to " + i + " bytes (" + l + " MB)";
/*     */           }
/*     */         });
/*  99 */     this.theReportCategory.addCrashSectionCallable("JVM Flags", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/* 103 */             RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
/* 104 */             List<String> list = runtimemxbean.getInputArguments();
/* 105 */             int i = 0;
/* 106 */             StringBuilder stringbuilder = new StringBuilder();
/*     */             
/* 108 */             for (String s : list) {
/*     */               
/* 110 */               if (s.startsWith("-X")) {
/*     */                 
/* 112 */                 if (i++ > 0)
/*     */                 {
/* 114 */                   stringbuilder.append(" ");
/*     */                 }
/*     */                 
/* 117 */                 stringbuilder.append(s);
/*     */               } 
/*     */             } 
/*     */             
/* 121 */             return String.format("%d total; %s", new Object[] { Integer.valueOf(i), stringbuilder });
/*     */           }
/*     */         });
/* 124 */     this.theReportCategory.addCrashSectionCallable("IntCache", new Callable<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 128 */             return IntCache.getCacheSizes();
/*     */           }
/*     */         });
/*     */     
/* 132 */     if (Reflector.FMLCommonHandler_enhanceCrashReport.exists()) {
/*     */       
/* 134 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 135 */       Reflector.callString(object, Reflector.FMLCommonHandler_enhanceCrashReport, new Object[] { this, this.theReportCategory });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 144 */     return this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getCrashCause() {
/* 152 */     return this.cause;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSectionsInStringBuilder(StringBuilder builder) {
/* 160 */     if ((this.stacktrace == null || this.stacktrace.length <= 0) && this.crashReportSections.size() > 0)
/*     */     {
/* 162 */       this.stacktrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportCategory)this.crashReportSections.get(0)).getStackTrace(), 0, 1);
/*     */     }
/*     */     
/* 165 */     if (this.stacktrace != null && this.stacktrace.length > 0) {
/*     */       
/* 167 */       builder.append("-- Head --\n");
/* 168 */       builder.append("Stacktrace:\n");
/*     */       
/* 170 */       for (StackTraceElement stacktraceelement : this.stacktrace) {
/*     */         
/* 172 */         builder.append("\t").append("at ").append(stacktraceelement.toString());
/* 173 */         builder.append("\n");
/*     */       } 
/*     */       
/* 176 */       builder.append("\n");
/*     */     } 
/*     */     
/* 179 */     for (CrashReportCategory crashreportcategory : this.crashReportSections) {
/*     */       
/* 181 */       crashreportcategory.appendToStringBuilder(builder);
/* 182 */       builder.append("\n\n");
/*     */     } 
/*     */     
/* 185 */     this.theReportCategory.appendToStringBuilder(builder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCauseStackTraceOrString() {
/* 193 */     StringWriter stringwriter = null;
/* 194 */     PrintWriter printwriter = null;
/* 195 */     Throwable throwable = this.cause;
/*     */     
/* 197 */     if (throwable.getMessage() == null) {
/*     */       
/* 199 */       if (throwable instanceof NullPointerException) {
/*     */         
/* 201 */         throwable = new NullPointerException(this.description);
/*     */       }
/* 203 */       else if (throwable instanceof StackOverflowError) {
/*     */         
/* 205 */         throwable = new StackOverflowError(this.description);
/*     */       }
/* 207 */       else if (throwable instanceof OutOfMemoryError) {
/*     */         
/* 209 */         throwable = new OutOfMemoryError(this.description);
/*     */       } 
/*     */       
/* 212 */       throwable.setStackTrace(this.cause.getStackTrace());
/*     */     } 
/*     */     
/* 215 */     String s = throwable.toString();
/*     */ 
/*     */     
/*     */     try {
/* 219 */       stringwriter = new StringWriter();
/* 220 */       printwriter = new PrintWriter(stringwriter);
/* 221 */       throwable.printStackTrace(printwriter);
/* 222 */       s = stringwriter.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 226 */       IOUtils.closeQuietly(stringwriter);
/* 227 */       IOUtils.closeQuietly(printwriter);
/*     */     } 
/*     */     
/* 230 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCompleteReport() {
/* 238 */     if (!this.reported) {
/*     */       
/* 240 */       this.reported = true;
/* 241 */       CrashReporter.onCrashReport(this, this.theReportCategory);
/*     */     } 
/*     */     
/* 244 */     StringBuilder stringbuilder = new StringBuilder();
/* 245 */     stringbuilder.append("---- Minecraft Crash Report ----\n");
/* 246 */     Reflector.call(Reflector.BlamingTransformer_onCrash, new Object[] { stringbuilder });
/* 247 */     Reflector.call(Reflector.CoreModManager_onCrash, new Object[] { stringbuilder });
/* 248 */     stringbuilder.append("// ");
/* 249 */     stringbuilder.append(getWittyComment());
/* 250 */     stringbuilder.append("\n\n");
/* 251 */     stringbuilder.append("Time: ");
/* 252 */     stringbuilder.append((new SimpleDateFormat()).format(new Date()));
/* 253 */     stringbuilder.append("\n");
/* 254 */     stringbuilder.append("Description: ");
/* 255 */     stringbuilder.append(this.description);
/* 256 */     stringbuilder.append("\n\n");
/* 257 */     stringbuilder.append(getCauseStackTraceOrString());
/* 258 */     stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 260 */     for (int i = 0; i < 87; i++)
/*     */     {
/* 262 */       stringbuilder.append("-");
/*     */     }
/*     */     
/* 265 */     stringbuilder.append("\n\n");
/* 266 */     getSectionsInStringBuilder(stringbuilder);
/* 267 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile() {
/* 275 */     return this.crashReportFile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveToFile(File toFile) {
/* 283 */     if (this.crashReportFile != null)
/*     */     {
/* 285 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 289 */     if (toFile.getParentFile() != null)
/*     */     {
/* 291 */       toFile.getParentFile().mkdirs();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 296 */       FileWriter filewriter = new FileWriter(toFile);
/* 297 */       filewriter.write(getCompleteReport());
/* 298 */       filewriter.close();
/* 299 */       this.crashReportFile = toFile;
/* 300 */       return true;
/*     */     }
/* 302 */     catch (Throwable throwable) {
/*     */       
/* 304 */       logger.error("Could not save crash report to " + toFile, throwable);
/* 305 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory getCategory() {
/* 312 */     return this.theReportCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategory(String name) {
/* 320 */     return makeCategoryDepth(name, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReportCategory makeCategoryDepth(String categoryName, int stacktraceLength) {
/* 328 */     CrashReportCategory crashreportcategory = new CrashReportCategory(this, categoryName);
/*     */     
/* 330 */     if (this.field_85059_f) {
/*     */       
/* 332 */       int i = crashreportcategory.getPrunedStackTrace(stacktraceLength);
/* 333 */       StackTraceElement[] astacktraceelement = this.cause.getStackTrace();
/* 334 */       StackTraceElement stacktraceelement = null;
/* 335 */       StackTraceElement stacktraceelement1 = null;
/* 336 */       int j = astacktraceelement.length - i;
/*     */       
/* 338 */       if (j < 0)
/*     */       {
/* 340 */         System.out.println("Negative index in crash report handler (" + astacktraceelement.length + "/" + i + ")");
/*     */       }
/*     */       
/* 343 */       if (astacktraceelement != null && 0 <= j && j < astacktraceelement.length) {
/*     */         
/* 345 */         stacktraceelement = astacktraceelement[j];
/*     */         
/* 347 */         if (astacktraceelement.length + 1 - i < astacktraceelement.length)
/*     */         {
/* 349 */           stacktraceelement1 = astacktraceelement[astacktraceelement.length + 1 - i];
/*     */         }
/*     */       } 
/*     */       
/* 353 */       this.field_85059_f = crashreportcategory.firstTwoElementsOfStackTraceMatch(stacktraceelement, stacktraceelement1);
/*     */       
/* 355 */       if (i > 0 && !this.crashReportSections.isEmpty()) {
/*     */         
/* 357 */         CrashReportCategory crashreportcategory1 = this.crashReportSections.get(this.crashReportSections.size() - 1);
/* 358 */         crashreportcategory1.trimStackTraceEntriesFromBottom(i);
/*     */       }
/* 360 */       else if (astacktraceelement != null && astacktraceelement.length >= i && 0 <= j && j < astacktraceelement.length) {
/*     */         
/* 362 */         this.stacktrace = new StackTraceElement[j];
/* 363 */         System.arraycopy(astacktraceelement, 0, this.stacktrace, 0, this.stacktrace.length);
/*     */       }
/*     */       else {
/*     */         
/* 367 */         this.field_85059_f = false;
/*     */       } 
/*     */     } 
/*     */     
/* 371 */     this.crashReportSections.add(crashreportcategory);
/* 372 */     return crashreportcategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getWittyComment() {
/* 380 */     String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
/*     */ 
/*     */     
/*     */     try {
/* 384 */       return astring[(int)(System.nanoTime() % astring.length)];
/*     */     }
/* 386 */     catch (Throwable var2) {
/*     */       
/* 388 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CrashReport makeCrashReport(Throwable causeIn, String descriptionIn) {
/*     */     CrashReport crashreport;
/* 399 */     if (causeIn instanceof ReportedException) {
/*     */       
/* 401 */       crashreport = ((ReportedException)causeIn).getCrashReport();
/*     */     }
/*     */     else {
/*     */       
/* 405 */       crashreport = new CrashReport(descriptionIn, causeIn);
/*     */     } 
/*     */     
/* 408 */     return crashreport;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\crash\CrashReport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */