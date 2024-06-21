/*    */ package com.viaversion.viaversion.scheduler;
/*    */ 
/*    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*    */ import com.viaversion.viaversion.api.scheduler.Scheduler;
/*    */ import com.viaversion.viaversion.api.scheduler.Task;
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.ScheduledExecutorService;
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
/*    */ public final class TaskScheduler
/*    */   implements Scheduler
/*    */ {
/* 30 */   private final ExecutorService executorService = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setNameFormat("Via Async Task %d").build());
/* 31 */   private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, (new ThreadFactoryBuilder())
/*    */       
/* 33 */       .setNameFormat("Via Async Scheduler %d").build());
/*    */ 
/*    */ 
/*    */   
/*    */   public Task execute(Runnable runnable) {
/* 38 */     return new SubmittedTask(this.executorService.submit(runnable));
/*    */   }
/*    */ 
/*    */   
/*    */   public Task schedule(Runnable runnable, long delay, TimeUnit timeUnit) {
/* 43 */     return new ScheduledTask(this.scheduledExecutorService.schedule(runnable, delay, timeUnit));
/*    */   }
/*    */ 
/*    */   
/*    */   public Task scheduleRepeating(Runnable runnable, long delay, long period, TimeUnit timeUnit) {
/* 48 */     return new ScheduledTask(this.scheduledExecutorService.scheduleAtFixedRate(runnable, delay, period, timeUnit));
/*    */   }
/*    */ 
/*    */   
/*    */   public void shutdown() {
/* 53 */     this.executorService.shutdown();
/* 54 */     this.scheduledExecutorService.shutdown();
/*    */     
/*    */     try {
/* 57 */       this.executorService.awaitTermination(2L, TimeUnit.SECONDS);
/* 58 */       this.scheduledExecutorService.awaitTermination(2L, TimeUnit.SECONDS);
/* 59 */     } catch (InterruptedException e) {
/* 60 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\scheduler\TaskScheduler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */