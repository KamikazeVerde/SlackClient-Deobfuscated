/*     */ package com.viaversion.viaversion.update;
/*     */ 
/*     */ import com.viaversion.viaversion.api.Via;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonParseException;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Locale;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class UpdateUtil
/*     */ {
/*     */   private static final String PREFIX = "§a§l[ViaVersion] §a";
/*     */   private static final String URL = "https://api.spiget.org/v2/resources/";
/*     */   private static final int PLUGIN = 19254;
/*     */   private static final String LATEST_VERSION = "/versions/latest";
/*     */   
/*     */   public static void sendUpdateMessage(UUID uuid) {
/*  41 */     Via.getPlatform().runAsync(() -> {
/*     */           String message = getUpdateMessage(false);
/*     */           if (message != null) {
/*     */             Via.getPlatform().runSync(());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static void sendUpdateMessage() {
/*  50 */     Via.getPlatform().runAsync(() -> {
/*     */           String message = getUpdateMessage(true);
/*     */           if (message != null)
/*     */             Via.getPlatform().runSync(()); 
/*     */         });
/*     */   }
/*     */   
/*     */   private static String getUpdateMessage(boolean console) {
/*     */     Version current;
/*  59 */     if (Via.getPlatform().getPluginVersion().equals("${version}")) {
/*  60 */       return "You are using a debug/custom version, consider updating.";
/*     */     }
/*  62 */     String newestString = getNewestVersion();
/*  63 */     if (newestString == null) {
/*  64 */       if (console) {
/*  65 */         return "Could not check for updates, check your connection.";
/*     */       }
/*  67 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  72 */       current = new Version(Via.getPlatform().getPluginVersion());
/*  73 */     } catch (IllegalArgumentException e) {
/*  74 */       return "You are using a custom version, consider updating.";
/*     */     } 
/*  76 */     Version newest = new Version(newestString);
/*  77 */     if (current.compareTo(newest) < 0)
/*  78 */       return "There is a newer plugin version available: " + newest + ", you're on: " + current; 
/*  79 */     if (console && current.compareTo(newest) != 0) {
/*  80 */       String tag = current.getTag().toLowerCase(Locale.ROOT);
/*  81 */       if (tag.startsWith("dev") || tag.startsWith("snapshot")) {
/*  82 */         return "You are running a development version of the plugin, please report any bugs to GitHub.";
/*     */       }
/*  84 */       return "You are running a newer version of the plugin than is released!";
/*     */     } 
/*     */     
/*  87 */     return null;
/*     */   }
/*     */   private static String getNewestVersion() {
/*     */     try {
/*     */       JsonObject statistics;
/*  92 */       URL url = new URL("https://api.spiget.org/v2/resources/19254/versions/latest?" + System.currentTimeMillis());
/*  93 */       HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/*  94 */       connection.setUseCaches(true);
/*  95 */       connection.addRequestProperty("User-Agent", "ViaVersion " + Via.getPlatform().getPluginVersion() + " " + Via.getPlatform().getPlatformName());
/*  96 */       connection.setDoOutput(true);
/*  97 */       BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/*     */       
/*  99 */       StringBuilder builder = new StringBuilder(); String input;
/* 100 */       while ((input = br.readLine()) != null) {
/* 101 */         builder.append(input);
/*     */       }
/* 103 */       br.close();
/*     */       
/*     */       try {
/* 106 */         statistics = (JsonObject)GsonUtil.getGson().fromJson(builder.toString(), JsonObject.class);
/* 107 */       } catch (JsonParseException e) {
/* 108 */         e.printStackTrace();
/* 109 */         return null;
/*     */       } 
/* 111 */       return statistics.get("name").getAsString();
/* 112 */     } catch (IOException e) {
/* 113 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\update\UpdateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */