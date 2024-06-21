/*    */ package com.viaversion.viaversion.bungee.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*    */ import net.md_5.bungee.api.scheduler.ScheduledTask;
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
/*    */ public class BungeeViaTask
/*    */   implements PlatformTask<ScheduledTask>
/*    */ {
/*    */   private final ScheduledTask task;
/*    */   
/*    */   public BungeeViaTask(ScheduledTask task) {
/* 27 */     this.task = task;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScheduledTask getObject() {
/* 32 */     return this.task;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 37 */     this.task.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bungee\platform\BungeeViaTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */