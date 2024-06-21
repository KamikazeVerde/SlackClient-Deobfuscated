/*    */ package com.viaversion.viaversion.velocity.util;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */ import org.slf4j.Logger;
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
/*    */ public class LoggerWrapper
/*    */   extends Logger
/*    */ {
/*    */   private final Logger base;
/*    */   
/*    */   public LoggerWrapper(Logger logger) {
/* 29 */     super("logger", null);
/* 30 */     this.base = logger;
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(LogRecord record) {
/* 35 */     log(record.getLevel(), record.getMessage());
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(Level level, String msg) {
/* 40 */     if (level == Level.FINE) {
/* 41 */       this.base.debug(msg);
/* 42 */     } else if (level == Level.WARNING) {
/* 43 */       this.base.warn(msg);
/* 44 */     } else if (level == Level.SEVERE) {
/* 45 */       this.base.error(msg);
/* 46 */     } else if (level == Level.INFO) {
/* 47 */       this.base.info(msg);
/*    */     } else {
/* 49 */       this.base.trace(msg);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg, Object param1) {
/* 54 */     if (level == Level.FINE) {
/* 55 */       this.base.debug(msg, param1);
/* 56 */     } else if (level == Level.WARNING) {
/* 57 */       this.base.warn(msg, param1);
/* 58 */     } else if (level == Level.SEVERE) {
/* 59 */       this.base.error(msg, param1);
/* 60 */     } else if (level == Level.INFO) {
/* 61 */       this.base.info(msg, param1);
/*    */     } else {
/* 63 */       this.base.trace(msg, param1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg, Object[] params) {
/* 68 */     log(level, MessageFormat.format(msg, params));
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(Level level, String msg, Throwable params) {
/* 73 */     if (level == Level.FINE) {
/* 74 */       this.base.debug(msg, params);
/* 75 */     } else if (level == Level.WARNING) {
/* 76 */       this.base.warn(msg, params);
/* 77 */     } else if (level == Level.SEVERE) {
/* 78 */       this.base.error(msg, params);
/* 79 */     } else if (level == Level.INFO) {
/* 80 */       this.base.info(msg, params);
/*    */     } else {
/* 82 */       this.base.trace(msg, params);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocit\\util\LoggerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */