/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class VertexFormat
/*     */ {
/*  10 */   private static final Logger LOGGER = LogManager.getLogger();
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
/*     */   public VertexFormat(VertexFormat vertexFormatIn) {
/*  22 */     this();
/*     */     
/*  24 */     for (int i = 0; i < vertexFormatIn.getElementCount(); i++)
/*     */     {
/*  26 */       func_181721_a(vertexFormatIn.getElement(i));
/*     */     }
/*     */     
/*  29 */     this.nextOffset = vertexFormatIn.getNextOffset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  34 */   private final List<VertexFormatElement> elements = Lists.newArrayList();
/*  35 */   private final List<Integer> offsets = Lists.newArrayList();
/*  36 */   private int nextOffset = 0;
/*  37 */   private int colorElementOffset = -1;
/*  38 */   private List<Integer> elementOffsetsById = Lists.newArrayList();
/*  39 */   private int normalElementOffset = -1;
/*     */   
/*     */   public VertexFormat() {}
/*     */   
/*     */   public void clear() {
/*  44 */     this.elements.clear();
/*  45 */     this.offsets.clear();
/*  46 */     this.colorElementOffset = -1;
/*  47 */     this.elementOffsetsById.clear();
/*  48 */     this.normalElementOffset = -1;
/*  49 */     this.nextOffset = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexFormat func_181721_a(VertexFormatElement p_181721_1_) {
/*  55 */     if (p_181721_1_.isPositionElement() && hasPosition()) {
/*     */       
/*  57 */       LOGGER.warn("VertexFormat error: Trying to add a position VertexFormatElement when one already exists, ignoring.");
/*  58 */       return this;
/*     */     } 
/*     */ 
/*     */     
/*  62 */     this.elements.add(p_181721_1_);
/*  63 */     this.offsets.add(Integer.valueOf(this.nextOffset));
/*     */     
/*  65 */     switch (p_181721_1_.getUsage()) {
/*     */       
/*     */       case NORMAL:
/*  68 */         this.normalElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case COLOR:
/*  72 */         this.colorElementOffset = this.nextOffset;
/*     */         break;
/*     */       
/*     */       case UV:
/*  76 */         this.elementOffsetsById.add(p_181721_1_.getIndex(), Integer.valueOf(this.nextOffset));
/*     */         break;
/*     */     } 
/*  79 */     this.nextOffset += p_181721_1_.getSize();
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNormal() {
/*  86 */     return (this.normalElementOffset >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNormalOffset() {
/*  91 */     return this.normalElementOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasColor() {
/*  96 */     return (this.colorElementOffset >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColorOffset() {
/* 101 */     return this.colorElementOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasElementOffset(int id) {
/* 106 */     return (this.elementOffsetsById.size() - 1 >= id);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getElementOffsetById(int id) {
/* 111 */     return ((Integer)this.elementOffsetsById.get(id)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     String s = "format: " + this.elements.size() + " elements: ";
/*     */     
/* 118 */     for (int i = 0; i < this.elements.size(); i++) {
/*     */       
/* 120 */       s = s + ((VertexFormatElement)this.elements.get(i)).toString();
/*     */       
/* 122 */       if (i != this.elements.size() - 1)
/*     */       {
/* 124 */         s = s + " ";
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasPosition() {
/* 133 */     int i = 0;
/*     */     
/* 135 */     for (int j = this.elements.size(); i < j; i++) {
/*     */       
/* 137 */       VertexFormatElement vertexformatelement = this.elements.get(i);
/*     */       
/* 139 */       if (vertexformatelement.isPositionElement())
/*     */       {
/* 141 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_181719_f() {
/* 150 */     return getNextOffset() / 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNextOffset() {
/* 155 */     return this.nextOffset;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VertexFormatElement> getElements() {
/* 160 */     return this.elements;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getElementCount() {
/* 165 */     return this.elements.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormatElement getElement(int index) {
/* 170 */     return this.elements.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_181720_d(int p_181720_1_) {
/* 175 */     return ((Integer)this.offsets.get(p_181720_1_)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 180 */     if (this == p_equals_1_)
/*     */     {
/* 182 */       return true;
/*     */     }
/* 184 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/* 186 */       VertexFormat vertexformat = (VertexFormat)p_equals_1_;
/* 187 */       return (this.nextOffset != vertexformat.nextOffset) ? false : (!this.elements.equals(vertexformat.elements) ? false : this.offsets.equals(vertexformat.offsets));
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     int i = this.elements.hashCode();
/* 198 */     i = 31 * i + this.offsets.hashCode();
/* 199 */     i = 31 * i + this.nextOffset;
/* 200 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\vertex\VertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */