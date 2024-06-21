/*    */ package com.viaversion.viaversion.libs.kyori.adventure.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Locale;
/*    */ import java.util.PropertyResourceBundle;
/*    */ import java.util.ResourceBundle;
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
/*    */ public final class UTF8ResourceBundleControl
/*    */   extends ResourceBundle.Control
/*    */ {
/* 45 */   private static final UTF8ResourceBundleControl INSTANCE = new UTF8ResourceBundleControl();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ResourceBundle.Control get() {
/* 54 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
/* 59 */     if (format.equals("java.properties")) {
/* 60 */       String bundle = toBundleName(baseName, locale);
/* 61 */       String resource = toResourceName(bundle, "properties");
/* 62 */       InputStream is = null;
/* 63 */       if (reload) {
/* 64 */         URL url = loader.getResource(resource);
/* 65 */         if (url != null) {
/* 66 */           URLConnection connection = url.openConnection();
/* 67 */           if (connection != null) {
/* 68 */             connection.setUseCaches(false);
/* 69 */             is = connection.getInputStream();
/*    */           } 
/*    */         } 
/*    */       } else {
/* 73 */         is = loader.getResourceAsStream(resource);
/*    */       } 
/*    */       
/* 76 */       if (is != null) {
/* 77 */         InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8); 
/* 78 */         try { PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(isr);
/* 79 */           isr.close(); return propertyResourceBundle; } catch (Throwable throwable) { try { isr.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }
/*    */       
/* 81 */       }  return null;
/*    */     } 
/*    */     
/* 84 */     return super.newBundle(baseName, locale, format, loader, reload);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventur\\util\UTF8ResourceBundleControl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */