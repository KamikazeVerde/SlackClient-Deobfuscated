/*     */ package com.viaversion.viabackwards.api.data;
/*     */ 
/*     */ import com.viaversion.viabackwards.ViaBackwards;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import com.viaversion.viaversion.libs.gson.JsonSyntaxException;
/*     */ import com.viaversion.viaversion.libs.opennbt.NBTIO;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public final class VBMappingDataLoader
/*     */ {
/*     */   public static CompoundTag loadNBT(String name) {
/*  39 */     InputStream resource = getResource(name);
/*  40 */     if (resource == null) {
/*  41 */       return null;
/*     */     }
/*     */     
/*  44 */     try { InputStream stream = resource; 
/*  45 */       try { CompoundTag compoundTag = NBTIO.readTag(stream);
/*  46 */         if (stream != null) stream.close();  return compoundTag; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  47 */     { throw new RuntimeException(e); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CompoundTag loadNBTFromDir(String name) {
/*  59 */     CompoundTag packedData = loadNBT(name);
/*     */     
/*  61 */     File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
/*  62 */     if (!file.exists()) {
/*  63 */       return packedData;
/*     */     }
/*     */     
/*  66 */     ViaBackwards.getPlatform().getLogger().info("Loading " + name + " from plugin folder");
/*     */     try {
/*  68 */       CompoundTag fileData = NBTIO.readFile(file, false, false);
/*  69 */       return mergeTags(packedData, fileData);
/*  70 */     } catch (IOException e) {
/*  71 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static CompoundTag mergeTags(CompoundTag original, CompoundTag extra) {
/*  76 */     for (Map.Entry<String, Tag> entry : (Iterable<Map.Entry<String, Tag>>)extra.entrySet()) {
/*  77 */       if (entry.getValue() instanceof CompoundTag) {
/*     */         
/*  79 */         CompoundTag originalEntry = (CompoundTag)original.get(entry.getKey());
/*  80 */         if (originalEntry != null) {
/*  81 */           mergeTags(originalEntry, (CompoundTag)entry.getValue());
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*  86 */       original.put(entry.getKey(), entry.getValue());
/*     */     } 
/*  88 */     return original;
/*     */   }
/*     */   public static JsonObject loadData(String name) {
/*     */     
/*  92 */     try { InputStream stream = getResource(name); 
/*  93 */       try { if (stream == null) { JsonObject jsonObject1 = null;
/*     */           
/*  95 */           if (stream != null) stream.close();  return jsonObject1; }  JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson(new InputStreamReader(stream), JsonObject.class); if (stream != null) stream.close();  return jsonObject; } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/*  96 */     { throw new RuntimeException(e); }
/*     */   
/*     */   }
/*     */   
/*     */   public static JsonObject loadFromDataDir(String name) {
/* 101 */     File file = new File(ViaBackwards.getPlatform().getDataFolder(), name);
/* 102 */     if (!file.exists()) {
/* 103 */       return loadData(name);
/*     */     }
/*     */ 
/*     */     
/* 107 */     try { FileReader reader = new FileReader(file); 
/* 108 */       try { JsonObject jsonObject = (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
/* 109 */         reader.close(); return jsonObject; } catch (Throwable throwable) { try { reader.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (JsonSyntaxException e)
/* 110 */     { ViaBackwards.getPlatform().getLogger().warning(name + " is badly formatted!");
/* 111 */       e.printStackTrace();
/* 112 */       ViaBackwards.getPlatform().getLogger().warning("Falling back to resource's file!");
/* 113 */       return loadData(name); }
/* 114 */     catch (IOException|com.viaversion.viaversion.libs.gson.JsonIOException e)
/* 115 */     { e.printStackTrace();
/*     */       
/* 117 */       return null; }
/*     */   
/*     */   }
/*     */   public static InputStream getResource(String name) {
/* 121 */     return VBMappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viabackwards/data/" + name);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\data\VBMappingDataLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */