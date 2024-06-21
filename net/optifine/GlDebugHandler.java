/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.opengl.ARBDebugOutput;
/*     */ import org.lwjgl.opengl.ARBDebugOutputCallback;
/*     */ import org.lwjgl.opengl.ContextAttribs;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.opengl.PixelFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GlDebugHandler
/*     */   implements ARBDebugOutputCallback.Handler
/*     */ {
/*     */   public static void createDisplayDebug() throws LWJGLException {
/*  19 */     boolean flag = (GLContext.getCapabilities()).GL_ARB_debug_output;
/*  20 */     ContextAttribs contextattribs = (new ContextAttribs()).withDebug(true);
/*  21 */     Display.create((new PixelFormat()).withDepthBits(24), contextattribs);
/*  22 */     ARBDebugOutput.glDebugMessageCallbackARB(new ARBDebugOutputCallback(new GlDebugHandler()));
/*  23 */     ARBDebugOutput.glDebugMessageControlARB(4352, 4352, 4352, null, true);
/*  24 */     GL11.glEnable(33346);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMessage(int source, int type, int id, int severity, String message) {
/*  29 */     if (!message.contains("glBindFramebuffer"))
/*     */     {
/*  31 */       if (!message.contains("Wide lines"))
/*     */       {
/*  33 */         if (!message.contains("shader recompiled")) {
/*     */           
/*  35 */           Config.dbg("[LWJGL] source: " + getSource(source) + ", type: " + getType(type) + ", id: " + id + ", severity: " + getSeverity(severity) + ", message: " + message);
/*  36 */           (new Throwable("StackTrace")).printStackTrace();
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSource(int source) {
/*  44 */     switch (source) {
/*     */       
/*     */       case 33350:
/*  47 */         return "API";
/*     */       
/*     */       case 33351:
/*  50 */         return "WIN";
/*     */       
/*     */       case 33352:
/*  53 */         return "SHADER";
/*     */       
/*     */       case 33353:
/*  56 */         return "EXT";
/*     */       
/*     */       case 33354:
/*  59 */         return "APP";
/*     */       
/*     */       case 33355:
/*  62 */         return "OTHER";
/*     */     } 
/*     */     
/*  65 */     return getUnknown(source);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType(int type) {
/*  71 */     switch (type) {
/*     */       
/*     */       case 33356:
/*  74 */         return "ERROR";
/*     */       
/*     */       case 33357:
/*  77 */         return "DEPRECATED";
/*     */       
/*     */       case 33358:
/*  80 */         return "UNDEFINED";
/*     */       
/*     */       case 33359:
/*  83 */         return "PORTABILITY";
/*     */       
/*     */       case 33360:
/*  86 */         return "PERFORMANCE";
/*     */       
/*     */       case 33361:
/*  89 */         return "OTHER";
/*     */     } 
/*     */     
/*  92 */     return getUnknown(type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeverity(int severity) {
/*  98 */     switch (severity) {
/*     */       
/*     */       case 37190:
/* 101 */         return "HIGH";
/*     */       
/*     */       case 37191:
/* 104 */         return "MEDIUM";
/*     */       
/*     */       case 37192:
/* 107 */         return "LOW";
/*     */     } 
/*     */     
/* 110 */     return getUnknown(severity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getUnknown(int token) {
/* 116 */     return "Unknown (0x" + Integer.toHexString(token).toUpperCase() + ")";
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\GlDebugHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */