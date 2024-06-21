/*    */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*    */ 
/*    */ import com.google.common.collect.ObjectArrays;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*    */ import com.viaversion.viaversion.util.GsonUtil;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
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
/*    */ public class BlockIdData
/*    */ {
/* 32 */   public static final String[] PREVIOUS = new String[0];
/*    */   
/*    */   public static Map<String, String[]> blockIdMapping;
/*    */   public static Map<String, String[]> fallbackReverseMapping;
/*    */   public static Int2ObjectMap<String> numberIdToString;
/*    */   
/*    */   public static void init() {
/* 39 */     InputStream stream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json");
/* 40 */     try (InputStreamReader reader = new InputStreamReader(stream)) {
/* 41 */       Map<String, String[]> map = (Map<String, String[]>)GsonUtil.getGson().fromJson(reader, (new TypeToken<Map<String, String[]>>()
/*    */           {
/*    */           
/* 44 */           }).getType());
/* 45 */       blockIdMapping = (Map)new HashMap<>((Map)map);
/* 46 */       fallbackReverseMapping = (Map)new HashMap<>();
/* 47 */       for (Map.Entry<String, String[]> entry : blockIdMapping.entrySet()) {
/* 48 */         for (String val : (String[])entry.getValue()) {
/* 49 */           String[] previous = fallbackReverseMapping.get(val);
/* 50 */           if (previous == null) previous = PREVIOUS; 
/* 51 */           fallbackReverseMapping.put(val, ObjectArrays.concat((Object[])previous, entry.getKey()));
/*    */         } 
/*    */       } 
/* 54 */     } catch (IOException e) {
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */ 
/*    */     
/* 59 */     InputStream blockS = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json");
/* 60 */     try (InputStreamReader blockR = new InputStreamReader(blockS)) {
/* 61 */       Map<Integer, String> map = (Map<Integer, String>)GsonUtil.getGson().fromJson(blockR, (new TypeToken<Map<Integer, String>>()
/*    */           {
/*    */           
/* 64 */           }).getType());
/*    */       
/* 66 */       numberIdToString = (Int2ObjectMap<String>)new Int2ObjectOpenHashMap(map);
/* 67 */     } catch (IOException e) {
/* 68 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\BlockIdData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */