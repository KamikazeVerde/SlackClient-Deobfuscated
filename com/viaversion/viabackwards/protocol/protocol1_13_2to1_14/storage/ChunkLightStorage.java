/*    */ package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashMap;
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
/*    */ public class ChunkLightStorage
/*    */   extends StoredObject
/*    */ {
/* 29 */   public static final byte[] FULL_LIGHT = new byte[2048];
/* 30 */   public static final byte[] EMPTY_LIGHT = new byte[2048];
/*    */   
/*    */   private static Constructor<?> fastUtilLongObjectHashMap;
/* 33 */   private final Map<Long, ChunkLight> storedLight = createLongObjectMap();
/*    */   
/*    */   static {
/* 36 */     Arrays.fill(FULL_LIGHT, (byte)-1);
/* 37 */     Arrays.fill(EMPTY_LIGHT, (byte)0);
/*    */     try {
/* 39 */       fastUtilLongObjectHashMap = Class.forName("com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectOpenHashMap").getConstructor(new Class[0]);
/* 40 */     } catch (ClassNotFoundException|NoSuchMethodException classNotFoundException) {}
/*    */   }
/*    */ 
/*    */   
/*    */   public ChunkLightStorage(UserConnection user) {
/* 45 */     super(user);
/*    */   }
/*    */   
/*    */   public void setStoredLight(byte[][] skyLight, byte[][] blockLight, int x, int z) {
/* 49 */     this.storedLight.put(Long.valueOf(getChunkSectionIndex(x, z)), new ChunkLight(skyLight, blockLight));
/*    */   }
/*    */   
/*    */   public ChunkLight getStoredLight(int x, int z) {
/* 53 */     return this.storedLight.get(Long.valueOf(getChunkSectionIndex(x, z)));
/*    */   }
/*    */   
/*    */   public void clear() {
/* 57 */     this.storedLight.clear();
/*    */   }
/*    */   
/*    */   public void unloadChunk(int x, int z) {
/* 61 */     this.storedLight.remove(Long.valueOf(getChunkSectionIndex(x, z)));
/*    */   }
/*    */   
/*    */   private long getChunkSectionIndex(int x, int z) {
/* 65 */     return (x & 0x3FFFFFFL) << 38L | z & 0x3FFFFFFL;
/*    */   }
/*    */   
/*    */   private Map<Long, ChunkLight> createLongObjectMap() {
/* 69 */     if (fastUtilLongObjectHashMap != null) {
/*    */       try {
/* 71 */         return (Map<Long, ChunkLight>)fastUtilLongObjectHashMap.newInstance(new Object[0]);
/* 72 */       } catch (IllegalAccessException|InstantiationException|java.lang.reflect.InvocationTargetException e) {
/* 73 */         e.printStackTrace();
/*    */       } 
/*    */     }
/* 76 */     return new HashMap<>();
/*    */   }
/*    */   
/*    */   public static class ChunkLight {
/*    */     private final byte[][] skyLight;
/*    */     private final byte[][] blockLight;
/*    */     
/*    */     public ChunkLight(byte[][] skyLight, byte[][] blockLight) {
/* 84 */       this.skyLight = skyLight;
/* 85 */       this.blockLight = blockLight;
/*    */     }
/*    */     
/*    */     public byte[][] getSkyLight() {
/* 89 */       return this.skyLight;
/*    */     }
/*    */     
/*    */     public byte[][] getBlockLight() {
/* 93 */       return this.blockLight;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_13_2to1_14\storage\ChunkLightStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */