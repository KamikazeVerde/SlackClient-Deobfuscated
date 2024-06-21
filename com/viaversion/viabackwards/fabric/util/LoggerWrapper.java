/*    */ package com.viaversion.viabackwards.fabric.util;
/*    */ 
/*    */ import java.text.MessageFormat;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
/*    */ import java.util.logging.Logger;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ public class LoggerWrapper
/*    */   extends Logger
/*    */ {
/*    */   private final Logger base;
/*    */   
/*    */   public LoggerWrapper(Logger logger) {
/* 30 */     super("logger", null);
/* 31 */     this.base = logger;
/*    */   }
/*    */   
/*    */   public void log(LogRecord record) {
/* 35 */     log(record.getLevel(), record.getMessage());
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg) {
/* 39 */     if (level == Level.FINE) {
/* 40 */       this.base.debug(msg);
/* 41 */     } else if (level == Level.WARNING) {
/* 42 */       this.base.warn(msg);
/* 43 */     } else if (level == Level.SEVERE) {
/* 44 */       this.base.error(msg);
/* 45 */     } else if (level == Level.INFO) {
/* 46 */       this.base.info(msg);
/*    */     } else {
/* 48 */       this.base.trace(msg);
/*    */     } 
/*    */   }
/*    */ 
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
/*    */   
/*    */   public void log(Level level, String msg, Object[] params) {
/* 69 */     log(level, MessageFormat.format(msg, params));
/*    */   }
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


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\fabri\\util\LoggerWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */