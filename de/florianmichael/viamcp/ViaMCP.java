/*    */ package de.florianmichael.viamcp;
/*    */ 
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*    */ import de.florianmichael.vialoadingbase.model.ComparableProtocolVersion;
/*    */ import de.florianmichael.viamcp.gui.AsyncVersionSlider;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ViaMCP
/*    */ {
/*    */   public static final int NATIVE_VERSION = 47;
/*    */   public static ViaMCP INSTANCE;
/*    */   private AsyncVersionSlider asyncVersionSlider;
/*    */   
/*    */   public static void create() {
/* 30 */     INSTANCE = new ViaMCP();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ViaMCP() {
/* 36 */     ViaLoadingBase.ViaLoadingBaseBuilder.create().runDirectory(new File("ViaMCP")).nativeVersion(47).onProtocolReload(comparableProtocolVersion -> {
/*    */           if (getAsyncVersionSlider() != null) {
/*    */             getAsyncVersionSlider().setVersion(comparableProtocolVersion.getVersion());
/*    */           }
/* 40 */         }).build();
/*    */   }
/*    */   
/*    */   public void initAsyncSlider() {
/* 44 */     initAsyncSlider(5, 5, 110, 20);
/*    */   }
/*    */   
/*    */   public void initAsyncSlider(int x, int y, int width, int height) {
/* 48 */     this.asyncVersionSlider = new AsyncVersionSlider(-1, x, y, Math.max(width, 110), height);
/*    */   }
/*    */   
/*    */   public AsyncVersionSlider getAsyncVersionSlider() {
/* 52 */     return this.asyncVersionSlider;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\viamcp\ViaMCP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */