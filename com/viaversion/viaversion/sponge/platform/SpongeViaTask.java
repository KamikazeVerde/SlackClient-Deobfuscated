/*    */ package com.viaversion.viaversion.sponge.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*    */ import org.spongepowered.api.scheduler.ScheduledTask;
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
/*    */ public class SpongeViaTask
/*    */   implements PlatformTask<ScheduledTask>
/*    */ {
/*    */   private final ScheduledTask task;
/*    */   
/*    */   public SpongeViaTask(ScheduledTask task) {
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\sponge\platform\SpongeViaTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */