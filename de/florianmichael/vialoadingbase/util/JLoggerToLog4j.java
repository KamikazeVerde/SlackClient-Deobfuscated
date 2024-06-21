/*    */ package de.florianmichael.vialoadingbase.util;
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
/*    */ public class JLoggerToLog4j
/*    */   extends Logger
/*    */ {
/*    */   private final Logger base;
/*    */   
/*    */   public JLoggerToLog4j(Logger logger) {
/* 29 */     super("logger", null);
/* 30 */     this.base = logger;
/*    */   }
/*    */   
/*    */   public void log(LogRecord record) {
/* 34 */     log(record.getLevel(), record.getMessage());
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg) {
/* 38 */     if (level == Level.FINE) {
/* 39 */       this.base.debug(msg);
/* 40 */     } else if (level == Level.WARNING) {
/* 41 */       this.base.warn(msg);
/* 42 */     } else if (level == Level.SEVERE) {
/* 43 */       this.base.error(msg);
/* 44 */     } else if (level == Level.INFO) {
/* 45 */       this.base.info(msg);
/*    */     } else {
/* 47 */       this.base.trace(msg);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(Level level, String msg, Object param1) {
/* 53 */     if (level == Level.FINE) {
/* 54 */       this.base.debug(msg, new Object[] { param1 });
/* 55 */     } else if (level == Level.WARNING) {
/* 56 */       this.base.warn(msg, new Object[] { param1 });
/* 57 */     } else if (level == Level.SEVERE) {
/* 58 */       this.base.error(msg, new Object[] { param1 });
/* 59 */     } else if (level == Level.INFO) {
/* 60 */       this.base.info(msg, new Object[] { param1 });
/*    */     } else {
/* 62 */       this.base.trace(msg, new Object[] { param1 });
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void log(Level level, String msg, Object[] params) {
/* 68 */     log(level, MessageFormat.format(msg, params));
/*    */   }
/*    */   
/*    */   public void log(Level level, String msg, Throwable params) {
/* 72 */     if (level == Level.FINE) {
/* 73 */       this.base.debug(msg, params);
/* 74 */     } else if (level == Level.WARNING) {
/* 75 */       this.base.warn(msg, params);
/* 76 */     } else if (level == Level.SEVERE) {
/* 77 */       this.base.error(msg, params);
/* 78 */     } else if (level == Level.INFO) {
/* 79 */       this.base.info(msg, params);
/*    */     } else {
/* 81 */       this.base.trace(msg, params);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbas\\util\JLoggerToLog4j.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */