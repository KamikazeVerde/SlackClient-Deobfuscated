/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;
/*    */ 
/*    */ import com.viaversion.viaversion.util.Key;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public final class WrappedBlockData
/*    */ {
/* 25 */   private final LinkedHashMap<String, String> blockData = new LinkedHashMap<>();
/*    */   private final String minecraftKey;
/*    */   private final int savedBlockStateId;
/*    */   
/*    */   public static WrappedBlockData fromString(String s) {
/* 30 */     String[] array = s.split("\\[");
/* 31 */     String key = array[0];
/* 32 */     WrappedBlockData wrappedBlockdata = new WrappedBlockData(key, ConnectionData.getId(s));
/* 33 */     if (array.length > 1) {
/* 34 */       String blockData = array[1];
/* 35 */       blockData = blockData.replace("]", "");
/* 36 */       String[] data = blockData.split(",");
/* 37 */       for (String d : data) {
/* 38 */         String[] a = d.split("=");
/* 39 */         wrappedBlockdata.blockData.put(a[0], a[1]);
/*    */       } 
/*    */     } 
/* 42 */     return wrappedBlockdata;
/*    */   }
/*    */   
/*    */   private WrappedBlockData(String minecraftKey, int savedBlockStateId) {
/* 46 */     this.minecraftKey = Key.namespaced(minecraftKey);
/* 47 */     this.savedBlockStateId = savedBlockStateId;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuilder sb = new StringBuilder(this.minecraftKey + "[");
/* 53 */     for (Map.Entry<String, String> entry : this.blockData.entrySet()) {
/* 54 */       sb.append(entry.getKey()).append('=').append(entry.getValue()).append(',');
/*    */     }
/* 56 */     return sb.substring(0, sb.length() - 1) + "]";
/*    */   }
/*    */   
/*    */   public String getMinecraftKey() {
/* 60 */     return this.minecraftKey;
/*    */   }
/*    */   
/*    */   public int getSavedBlockStateId() {
/* 64 */     return this.savedBlockStateId;
/*    */   }
/*    */   
/*    */   public int getBlockStateId() {
/* 68 */     return ConnectionData.getId(toString());
/*    */   }
/*    */   
/*    */   public WrappedBlockData set(String data, Object value) {
/* 72 */     if (!hasData(data))
/* 73 */       throw new UnsupportedOperationException("No blockdata found for " + data + " at " + this.minecraftKey); 
/* 74 */     this.blockData.put(data, value.toString());
/* 75 */     return this;
/*    */   }
/*    */   
/*    */   public String getValue(String data) {
/* 79 */     return this.blockData.get(data);
/*    */   }
/*    */   
/*    */   public boolean hasData(String key) {
/* 83 */     return this.blockData.containsKey(key);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\blockconnections\WrappedBlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */