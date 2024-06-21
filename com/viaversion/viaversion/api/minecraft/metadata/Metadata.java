/*     */ package com.viaversion.viaversion.api.minecraft.metadata;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Objects;
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
/*     */ public final class Metadata
/*     */ {
/*     */   private int id;
/*     */   private MetaType metaType;
/*     */   private Object value;
/*     */   
/*     */   public Metadata(int id, MetaType metaType, Object value) {
/*  43 */     this.id = id;
/*  44 */     this.metaType = metaType;
/*  45 */     this.value = checkValue(metaType, value);
/*     */   }
/*     */   
/*     */   public int id() {
/*  49 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setId(int id) {
/*  53 */     this.id = id;
/*     */   }
/*     */   
/*     */   public MetaType metaType() {
/*  57 */     return this.metaType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetaType(MetaType metaType) {
/*  68 */     checkValue(metaType, this.value);
/*  69 */     this.metaType = metaType;
/*     */   }
/*     */   
/*     */   public <T> T value() {
/*  73 */     return (T)this.value;
/*     */   }
/*     */   
/*     */   public Object getValue() {
/*  77 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object value) {
/*  88 */     this.value = checkValue(this.metaType, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTypeAndValue(MetaType metaType, Object value) {
/*  99 */     this.value = checkValue(metaType, value);
/* 100 */     this.metaType = metaType;
/*     */   }
/*     */   
/*     */   private Object checkValue(MetaType metaType, Object value) {
/* 104 */     Preconditions.checkNotNull(metaType);
/* 105 */     if (value != null && !metaType.type().getOutputClass().isAssignableFrom(value.getClass())) {
/* 106 */       throw new IllegalArgumentException("Metadata value and metaType are incompatible. Type=" + metaType + ", value=" + value + " (" + value
/* 107 */           .getClass().getSimpleName() + ")");
/*     */     }
/* 109 */     return value;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setMetaTypeUnsafe(MetaType type) {
/* 114 */     this.metaType = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 119 */     if (this == o) return true; 
/* 120 */     if (o == null || getClass() != o.getClass()) return false; 
/* 121 */     Metadata metadata = (Metadata)o;
/* 122 */     if (this.id != metadata.id) return false; 
/* 123 */     if (this.metaType != metadata.metaType) return false; 
/* 124 */     return Objects.equals(this.value, metadata.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 129 */     int result = this.id;
/* 130 */     result = 31 * result + this.metaType.hashCode();
/* 131 */     result = 31 * result + ((this.value != null) ? this.value.hashCode() : 0);
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 137 */     return "Metadata{id=" + this.id + ", metaType=" + this.metaType + ", value=" + this.value + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\Metadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */