/*    */ package com.viaversion.viaversion.libs.gson.internal.sql;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.TypeAdapterFactory;
/*    */ import com.viaversion.viaversion.libs.gson.internal.bind.DefaultDateTypeAdapter;
/*    */ import java.sql.Date;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Date;
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
/*    */ public final class SqlTypesSupport
/*    */ {
/*    */   public static final boolean SUPPORTS_SQL_TYPES;
/*    */   public static final DefaultDateTypeAdapter.DateType<? extends Date> DATE_DATE_TYPE;
/*    */   public static final DefaultDateTypeAdapter.DateType<? extends Date> TIMESTAMP_DATE_TYPE;
/*    */   public static final TypeAdapterFactory DATE_FACTORY;
/*    */   public static final TypeAdapterFactory TIME_FACTORY;
/*    */   public static final TypeAdapterFactory TIMESTAMP_FACTORY;
/*    */   
/*    */   static {
/*    */     boolean sqlTypesSupport;
/*    */     try {
/* 37 */       Class.forName("java.sql.Date");
/* 38 */       sqlTypesSupport = true;
/* 39 */     } catch (ClassNotFoundException classNotFoundException) {
/* 40 */       sqlTypesSupport = false;
/*    */     } 
/* 42 */     SUPPORTS_SQL_TYPES = sqlTypesSupport;
/*    */     
/* 44 */     if (SUPPORTS_SQL_TYPES) {
/* 45 */       DATE_DATE_TYPE = (DefaultDateTypeAdapter.DateType)new DefaultDateTypeAdapter.DateType<Date>(Date.class) {
/*    */           protected Date deserialize(Date date) {
/* 47 */             return new Date(date.getTime());
/*    */           }
/*    */         };
/* 50 */       TIMESTAMP_DATE_TYPE = (DefaultDateTypeAdapter.DateType)new DefaultDateTypeAdapter.DateType<Timestamp>(Timestamp.class) {
/*    */           protected Timestamp deserialize(Date date) {
/* 52 */             return new Timestamp(date.getTime());
/*    */           }
/*    */         };
/*    */       
/* 56 */       DATE_FACTORY = SqlDateTypeAdapter.FACTORY;
/* 57 */       TIME_FACTORY = SqlTimeTypeAdapter.FACTORY;
/* 58 */       TIMESTAMP_FACTORY = SqlTimestampTypeAdapter.FACTORY;
/*    */     } else {
/* 60 */       DATE_DATE_TYPE = null;
/* 61 */       TIMESTAMP_DATE_TYPE = null;
/*    */       
/* 63 */       DATE_FACTORY = null;
/* 64 */       TIME_FACTORY = null;
/* 65 */       TIMESTAMP_FACTORY = null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\sql\SqlTypesSupport.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */