/*     */ package com.viaversion.viaversion.libs.opennbt.tag.builtin;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.TagCreateException;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CompoundTag
/*     */   extends Tag
/*     */   implements Iterable<Map.Entry<String, Tag>>
/*     */ {
/*     */   public static final int ID = 10;
/*     */   private Map<String, Tag> value;
/*     */   
/*     */   public CompoundTag() {
/*  30 */     this(new LinkedHashMap<>());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag(Map<String, Tag> value) {
/*  39 */     this.value = new LinkedHashMap<>(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CompoundTag(LinkedHashMap<String, Tag> value) {
/*  48 */     if (value == null) {
/*  49 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  51 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Tag> getValue() {
/*  56 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Map<String, Tag> value) {
/*  65 */     if (value == null) {
/*  66 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  68 */     this.value = new LinkedHashMap<>(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(LinkedHashMap<String, Tag> value) {
/*  77 */     if (value == null) {
/*  78 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*  80 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  89 */     return this.value.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String tagName) {
/*  99 */     return this.value.containsKey(tagName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends Tag> T get(String tagName) {
/* 111 */     return (T)this.value.get(tagName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends Tag> T put(String tagName, T tag) {
/* 124 */     return (T)this.value.put(tagName, (Tag)tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends Tag> T remove(String tagName) {
/* 136 */     return (T)this.value.remove(tagName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 145 */     return this.value.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Tag> values() {
/* 154 */     return this.value.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, Tag>> entrySet() {
/* 163 */     return this.value.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 172 */     return this.value.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 179 */     this.value.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Map.Entry<String, Tag>> iterator() {
/* 184 */     return this.value.entrySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/*     */     try {
/* 190 */       tagLimiter.checkLevel(nestingLevel);
/* 191 */       int newNestingLevel = nestingLevel + 1;
/*     */       
/*     */       while (true) {
/* 194 */         tagLimiter.countByte();
/* 195 */         int id = in.readByte();
/* 196 */         if (id == 0) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 201 */         String name = in.readUTF();
/* 202 */         tagLimiter.countBytes(2 * name.length());
/*     */         
/* 204 */         Tag tag = TagRegistry.createInstance(id);
/* 205 */         tag.read(in, tagLimiter, newNestingLevel);
/* 206 */         this.value.put(name, tag);
/*     */       } 
/* 208 */     } catch (TagCreateException e) {
/* 209 */       throw new IOException("Failed to create tag.", e);
/* 210 */     } catch (EOFException ignored) {
/* 211 */       throw new IOException("Closing tag was not found!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/* 217 */     for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
/* 218 */       Tag tag = entry.getValue();
/* 219 */       out.writeByte(tag.getTagId());
/* 220 */       out.writeUTF(entry.getKey());
/* 221 */       tag.write(out);
/*     */     } 
/*     */ 
/*     */     
/* 225 */     out.writeByte(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 230 */     if (this == o) return true; 
/* 231 */     if (o == null || getClass() != o.getClass()) return false; 
/* 232 */     CompoundTag tags = (CompoundTag)o;
/* 233 */     return this.value.equals(tags.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 238 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public final CompoundTag clone() {
/* 243 */     LinkedHashMap<String, Tag> newMap = new LinkedHashMap<>();
/* 244 */     for (Map.Entry<String, Tag> entry : this.value.entrySet()) {
/* 245 */       newMap.put(entry.getKey(), ((Tag)entry.getValue()).clone());
/*     */     }
/*     */     
/* 248 */     return new CompoundTag(newMap);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 253 */     return 10;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\CompoundTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */