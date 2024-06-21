/*     */ package com.viaversion.viaversion.libs.opennbt.tag.builtin;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.TagCreateException;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.TagRegistry;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ListTag
/*     */   extends Tag
/*     */   implements Iterable<Tag>
/*     */ {
/*     */   public static final int ID = 9;
/*     */   private final List<Tag> value;
/*     */   private Class<? extends Tag> type;
/*     */   
/*     */   public ListTag() {
/*  28 */     this.value = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListTag(@Nullable Class<? extends Tag> type) {
/*  36 */     this.type = type;
/*  37 */     this.value = new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ListTag(List<Tag> value) throws IllegalArgumentException {
/*  48 */     this.value = new ArrayList<>(value.size());
/*  49 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Tag> getValue() {
/*  54 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(List<Tag> value) throws IllegalArgumentException {
/*  65 */     if (value == null) {
/*  66 */       throw new NullPointerException("value cannot be null");
/*     */     }
/*     */     
/*  69 */     this.type = null;
/*  70 */     this.value.clear();
/*     */     
/*  72 */     for (Tag tag : value) {
/*  73 */       add(tag);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends Tag> getElementType() {
/*  83 */     return this.type;
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
/*     */   public boolean add(Tag tag) throws IllegalArgumentException {
/*  95 */     if (tag == null) {
/*  96 */       throw new NullPointerException("tag cannot be null");
/*     */     }
/*     */ 
/*     */     
/* 100 */     if (this.type == null) {
/* 101 */       this.type = (Class)tag.getClass();
/* 102 */     } else if (tag.getClass() != this.type) {
/* 103 */       throw new IllegalArgumentException("Tag type cannot differ from ListTag type.");
/*     */     } 
/*     */     
/* 106 */     return this.value.add(tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(Tag tag) {
/* 116 */     return this.value.remove(tag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Tag> T get(int index) {
/* 127 */     return (T)this.value.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 136 */     return this.value.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<Tag> iterator() {
/* 141 */     return this.value.iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/* 146 */     this.type = null;
/*     */     
/* 148 */     tagLimiter.checkLevel(nestingLevel);
/* 149 */     tagLimiter.countBytes(5);
/* 150 */     int id = in.readByte();
/* 151 */     if (id != 0) {
/* 152 */       this.type = TagRegistry.getClassFor(id);
/* 153 */       if (this.type == null) {
/* 154 */         throw new IOException("Unknown tag ID in ListTag: " + id);
/*     */       }
/*     */     } 
/*     */     
/* 158 */     int count = in.readInt();
/* 159 */     int newNestingLevel = nestingLevel + 1;
/* 160 */     for (int index = 0; index < count; index++) {
/*     */       Tag tag;
/*     */       try {
/* 163 */         tag = TagRegistry.createInstance(id);
/* 164 */       } catch (TagCreateException e) {
/* 165 */         throw new IOException("Failed to create tag.", e);
/*     */       } 
/*     */       
/* 168 */       tag.read(in, tagLimiter, newNestingLevel);
/* 169 */       add(tag);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(DataOutput out) throws IOException {
/* 175 */     if (this.type == null) {
/* 176 */       out.writeByte(0);
/*     */     } else {
/* 178 */       int id = TagRegistry.getIdFor(this.type);
/* 179 */       if (id == -1) {
/* 180 */         throw new IOException("ListTag contains unregistered tag class.");
/*     */       }
/*     */       
/* 183 */       out.writeByte(id);
/*     */     } 
/*     */     
/* 186 */     out.writeInt(this.value.size());
/* 187 */     for (Tag tag : this.value) {
/* 188 */       tag.write(out);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public final ListTag clone() {
/* 194 */     List<Tag> newList = new ArrayList<>();
/* 195 */     for (Tag value : this.value) {
/* 196 */       newList.add(value.clone());
/*     */     }
/*     */     
/* 199 */     return new ListTag(newList);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 204 */     if (this == o) return true; 
/* 205 */     if (o == null || getClass() != o.getClass()) return false; 
/* 206 */     ListTag tags = (ListTag)o;
/* 207 */     if (!Objects.equals(this.type, tags.type)) return false; 
/* 208 */     return this.value.equals(tags.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 213 */     int result = (this.type != null) ? this.type.hashCode() : 0;
/* 214 */     result = 31 * result + this.value.hashCode();
/* 215 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagId() {
/* 220 */     return 9;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\ListTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */