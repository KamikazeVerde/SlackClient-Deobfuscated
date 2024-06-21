/*    */ package de.florianmichael.vialoadingbase.model;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.BooleanSupplier;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.logging.Logger;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Platform
/*    */ {
/* 30 */   public static int COUNT = 0;
/* 31 */   public static final List<ProtocolVersion> TEMP_INPUT_PROTOCOLS = new ArrayList<>();
/*    */   
/*    */   private final String name;
/*    */   private final BooleanSupplier load;
/*    */   private final Runnable executor;
/*    */   private final Consumer<List<ProtocolVersion>> versionCallback;
/*    */   
/*    */   public Platform(String name, BooleanSupplier load, Runnable executor) {
/* 39 */     this(name, load, executor, null);
/*    */   }
/*    */   
/*    */   public Platform(String name, BooleanSupplier load, Runnable executor, Consumer<List<ProtocolVersion>> versionCallback) {
/* 43 */     this.name = name;
/* 44 */     this.load = load;
/* 45 */     this.executor = executor;
/* 46 */     this.versionCallback = versionCallback;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 50 */     return this.name;
/*    */   }
/*    */   
/*    */   public void createProtocolPath() {
/* 54 */     if (this.versionCallback != null) {
/* 55 */       this.versionCallback.accept(TEMP_INPUT_PROTOCOLS);
/*    */     }
/*    */   }
/*    */   
/*    */   public void build(Logger logger) {
/* 60 */     if (this.load.getAsBoolean()) {
/*    */       try {
/* 62 */         this.executor.run();
/* 63 */         logger.info("Loaded Platform " + this.name);
/* 64 */         COUNT++;
/* 65 */       } catch (Throwable t) {
/* 66 */         logger.severe("An error occurred while loading Platform " + this.name + ":");
/* 67 */         t.printStackTrace();
/*    */       } 
/*    */       return;
/*    */     } 
/* 71 */     logger.severe("Platform " + this.name + " is not present");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\model\Platform.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */