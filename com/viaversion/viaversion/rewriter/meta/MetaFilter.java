/*     */ package com.viaversion.viaversion.rewriter.meta;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.viaversion.viaversion.api.minecraft.entities.EntityType;
/*     */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*     */ import com.viaversion.viaversion.rewriter.EntityRewriter;
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
/*     */ public class MetaFilter
/*     */ {
/*     */   private final MetaHandler handler;
/*     */   private final EntityType type;
/*     */   private final int index;
/*     */   private final boolean filterFamily;
/*     */   
/*     */   public MetaFilter(EntityType type, boolean filterFamily, int index, MetaHandler handler) {
/*  35 */     Preconditions.checkNotNull(handler, "MetaHandler cannot be null");
/*  36 */     this.type = type;
/*  37 */     this.filterFamily = filterFamily;
/*  38 */     this.index = index;
/*  39 */     this.handler = handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int index() {
/*  48 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityType type() {
/*  57 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MetaHandler handler() {
/*  66 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean filterFamily() {
/*  75 */     return this.filterFamily;
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
/*     */   
/*     */   public boolean isFiltered(EntityType type, Metadata metadata) {
/*  88 */     return ((this.index == -1 || metadata.id() == this.index) && (this.type == null || 
/*  89 */       matchesType(type)));
/*     */   }
/*     */   
/*     */   private boolean matchesType(EntityType type) {
/*  93 */     return (type != null && (this.filterFamily ? type.isOrHasParent(this.type) : (this.type == type)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  98 */     if (this == o) return true; 
/*  99 */     if (o == null || getClass() != o.getClass()) return false; 
/* 100 */     MetaFilter that = (MetaFilter)o;
/* 101 */     if (this.index != that.index) return false; 
/* 102 */     if (this.filterFamily != that.filterFamily) return false; 
/* 103 */     if (!this.handler.equals(that.handler)) return false; 
/* 104 */     return Objects.equals(this.type, that.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 109 */     int result = this.handler.hashCode();
/* 110 */     result = 31 * result + ((this.type != null) ? this.type.hashCode() : 0);
/* 111 */     result = 31 * result + this.index;
/* 112 */     result = 31 * result + (this.filterFamily ? 1 : 0);
/* 113 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 118 */     return "MetaFilter{type=" + this.type + ", filterFamily=" + this.filterFamily + ", index=" + this.index + ", handler=" + this.handler + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Builder
/*     */   {
/*     */     private final EntityRewriter rewriter;
/*     */     
/*     */     private EntityType type;
/*     */     
/* 129 */     private int index = -1;
/*     */     private boolean filterFamily;
/*     */     private MetaHandler handler;
/*     */     
/*     */     public Builder(EntityRewriter rewriter) {
/* 134 */       this.rewriter = rewriter;
/*     */     }
/*     */     
/*     */     public Builder type(EntityType type) {
/* 138 */       Preconditions.checkArgument((this.type == null));
/* 139 */       this.type = type;
/* 140 */       return this;
/*     */     }
/*     */     
/*     */     public Builder index(int index) {
/* 144 */       Preconditions.checkArgument((this.index == -1));
/* 145 */       this.index = index;
/* 146 */       return this;
/*     */     }
/*     */     
/*     */     public Builder filterFamily(EntityType type) {
/* 150 */       Preconditions.checkArgument((this.type == null));
/* 151 */       this.type = type;
/* 152 */       this.filterFamily = true;
/* 153 */       return this;
/*     */     }
/*     */     
/*     */     public Builder handlerNoRegister(MetaHandler handler) {
/* 157 */       Preconditions.checkArgument((this.handler == null));
/* 158 */       this.handler = handler;
/* 159 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void handler(MetaHandler handler) {
/* 170 */       Preconditions.checkArgument((this.handler == null));
/* 171 */       this.handler = handler;
/* 172 */       register();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void cancel(int index) {
/* 182 */       this.index = index;
/* 183 */       handler((event, meta) -> event.cancel());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void toIndex(int newIndex) {
/* 194 */       Preconditions.checkArgument((this.index != -1));
/* 195 */       handler((event, meta) -> event.setIndex(newIndex));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addIndex(int index) {
/* 206 */       Preconditions.checkArgument((this.index == -1));
/* 207 */       handler((event, meta) -> {
/*     */             if (event.index() >= index) {
/*     */               event.setIndex(event.index() + 1);
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeIndex(int index) {
/* 222 */       Preconditions.checkArgument((this.index == -1));
/* 223 */       handler((event, meta) -> {
/*     */             int metaIndex = event.index();
/*     */             if (metaIndex == index) {
/*     */               event.cancel();
/*     */             } else if (metaIndex > index) {
/*     */               event.setIndex(metaIndex - 1);
/*     */             } 
/*     */           });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void register() {
/* 237 */       this.rewriter.registerFilter(build());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MetaFilter build() {
/* 246 */       return new MetaFilter(this.type, this.filterFamily, this.index, this.handler);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\rewriter\meta\MetaFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */