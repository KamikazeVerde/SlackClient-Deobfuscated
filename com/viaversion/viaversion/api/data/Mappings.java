/*     */ package com.viaversion.viaversion.api.data;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.JsonArray;
/*     */ import com.viaversion.viaversion.libs.gson.JsonElement;
/*     */ import com.viaversion.viaversion.libs.gson.JsonObject;
/*     */ import java.util.Arrays;
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
/*     */ public interface Mappings
/*     */ {
/*     */   int getNewId(int paramInt);
/*     */   
/*     */   default int getNewIdOrDefault(int id, int def) {
/*  48 */     int mappedId = getNewId(id);
/*  49 */     return (mappedId != -1) ? mappedId : def;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default boolean contains(int id) {
/*  59 */     return (getNewId(id) != -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setNewId(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int size();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int mappedSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Mappings inverse();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Mappings> Builder<T> builder(MappingsSupplier<T> supplier) {
/*  94 */     return new Builder<>(supplier);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static class Builder<T extends Mappings>
/*     */   {
/*     */     protected final Mappings.MappingsSupplier<T> supplier;
/*     */     
/*     */     protected JsonElement unmapped;
/*     */     
/*     */     protected JsonElement mapped;
/*     */     
/*     */     protected JsonObject diffMappings;
/*     */     
/* 110 */     protected int mappedSize = -1;
/* 111 */     protected int size = -1;
/*     */     protected boolean warnOnMissing = true;
/*     */     
/*     */     protected Builder(Mappings.MappingsSupplier<T> supplier) {
/* 115 */       this.supplier = supplier;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<T> customEntrySize(int size) {
/* 125 */       this.size = size;
/* 126 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<T> customMappedSize(int size) {
/* 136 */       this.mappedSize = size;
/* 137 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Builder<T> warnOnMissing(boolean warnOnMissing) {
/* 147 */       this.warnOnMissing = warnOnMissing;
/* 148 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> unmapped(JsonArray unmappedArray) {
/* 152 */       this.unmapped = (JsonElement)unmappedArray;
/* 153 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> unmapped(JsonObject unmappedObject) {
/* 157 */       this.unmapped = (JsonElement)unmappedObject;
/* 158 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> mapped(JsonArray mappedArray) {
/* 162 */       this.mapped = (JsonElement)mappedArray;
/* 163 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> mapped(JsonObject mappedObject) {
/* 167 */       this.mapped = (JsonElement)mappedObject;
/* 168 */       return this;
/*     */     }
/*     */     
/*     */     public Builder<T> diffMappings(JsonObject diffMappings) {
/* 172 */       this.diffMappings = diffMappings;
/* 173 */       return this;
/*     */     }
/*     */     
/*     */     public T build() {
/* 177 */       int size = (this.size != -1) ? this.size : size(this.unmapped);
/* 178 */       int mappedSize = (this.mappedSize != -1) ? this.mappedSize : size(this.mapped);
/* 179 */       int[] mappings = new int[size];
/* 180 */       Arrays.fill(mappings, -1);
/*     */ 
/*     */       
/* 183 */       if (this.unmapped.isJsonArray()) {
/* 184 */         if (this.mapped.isJsonObject()) {
/* 185 */           MappingDataLoader.mapIdentifiers(mappings, toJsonObject(this.unmapped.getAsJsonArray()), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
/*     */         } else {
/* 187 */           MappingDataLoader.mapIdentifiers(mappings, this.unmapped.getAsJsonArray(), this.mapped.getAsJsonArray(), this.diffMappings, this.warnOnMissing);
/*     */         } 
/* 189 */       } else if (this.mapped.isJsonArray()) {
/* 190 */         MappingDataLoader.mapIdentifiers(mappings, this.unmapped.getAsJsonObject(), toJsonObject(this.mapped.getAsJsonArray()), this.diffMappings, this.warnOnMissing);
/*     */       } else {
/* 192 */         MappingDataLoader.mapIdentifiers(mappings, this.unmapped.getAsJsonObject(), this.mapped.getAsJsonObject(), this.diffMappings, this.warnOnMissing);
/*     */       } 
/*     */       
/* 195 */       return this.supplier.supply(mappings, mappedSize);
/*     */     }
/*     */     
/*     */     protected int size(JsonElement element) {
/* 199 */       return element.isJsonObject() ? element.getAsJsonObject().size() : element.getAsJsonArray().size();
/*     */     }
/*     */     
/*     */     protected JsonObject toJsonObject(JsonArray array) {
/* 203 */       JsonObject object = new JsonObject();
/* 204 */       for (int i = 0; i < array.size(); i++) {
/* 205 */         JsonElement element = array.get(i);
/* 206 */         object.add(Integer.toString(i), element);
/*     */       } 
/* 208 */       return object;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface MappingsSupplier<T extends Mappings> {
/*     */     T supply(int[] param1ArrayOfint, int param1Int);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\Mappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */