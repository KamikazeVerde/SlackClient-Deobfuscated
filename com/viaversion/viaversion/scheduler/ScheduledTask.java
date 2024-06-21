/*    */ package com.viaversion.viaversion.scheduler;
/*    */ 
/*    */ import com.viaversion.viaversion.api.scheduler.Task;
/*    */ import com.viaversion.viaversion.api.scheduler.TaskStatus;
/*    */ import java.util.concurrent.ScheduledFuture;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ public final class ScheduledTask
/*    */   implements Task
/*    */ {
/*    */   private final ScheduledFuture<?> future;
/*    */   
/*    */   public ScheduledTask(ScheduledFuture<?> future) {
/* 30 */     this.future = future;
/*    */   }
/*    */ 
/*    */   
/*    */   public TaskStatus status() {
/* 35 */     if (this.future.getDelay(TimeUnit.MILLISECONDS) > 0L) {
/* 36 */       return TaskStatus.SCHEDULED;
/*    */     }
/* 38 */     return this.future.isDone() ? TaskStatus.STOPPED : TaskStatus.RUNNING;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 43 */     this.future.cancel(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\scheduler\ScheduledTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */