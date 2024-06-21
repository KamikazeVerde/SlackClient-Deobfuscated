/*     */ package com.viaversion.viaversion.api.minecraft.item;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.annotations.SerializedName;
/*     */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*     */ public class DataItem
/*     */   implements Item
/*     */ {
/*     */   @SerializedName(value = "identifier", alternate = {"id"})
/*     */   private int identifier;
/*     */   private byte amount;
/*     */   private short data;
/*     */   private CompoundTag tag;
/*     */   
/*     */   public DataItem() {}
/*     */   
/*     */   public DataItem(int identifier, byte amount, short data, CompoundTag tag) {
/*  41 */     this.identifier = identifier;
/*  42 */     this.amount = amount;
/*  43 */     this.data = data;
/*  44 */     this.tag = tag;
/*     */   }
/*     */   
/*     */   public DataItem(Item toCopy) {
/*  48 */     this(toCopy.identifier(), (byte)toCopy.amount(), toCopy.data(), toCopy.tag());
/*     */   }
/*     */ 
/*     */   
/*     */   public int identifier() {
/*  53 */     return this.identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIdentifier(int identifier) {
/*  58 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public int amount() {
/*  63 */     return this.amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAmount(int amount) {
/*  68 */     if (amount > 127 || amount < -128) {
/*  69 */       throw new IllegalArgumentException("Invalid item amount: " + amount);
/*     */     }
/*  71 */     this.amount = (byte)amount;
/*     */   }
/*     */ 
/*     */   
/*     */   public short data() {
/*  76 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(short data) {
/*  81 */     this.data = data;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag tag() {
/*  86 */     return this.tag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTag(CompoundTag tag) {
/*  91 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item copy() {
/*  96 */     return new DataItem(this.identifier, this.amount, this.data, this.tag);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 101 */     if (this == o) return true; 
/* 102 */     if (o == null || getClass() != o.getClass()) return false; 
/* 103 */     DataItem item = (DataItem)o;
/* 104 */     if (this.identifier != item.identifier) return false; 
/* 105 */     if (this.amount != item.amount) return false; 
/* 106 */     if (this.data != item.data) return false; 
/* 107 */     return Objects.equals(this.tag, item.tag);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 112 */     int result = this.identifier;
/* 113 */     result = 31 * result + this.amount;
/* 114 */     result = 31 * result + this.data;
/* 115 */     result = 31 * result + ((this.tag != null) ? this.tag.hashCode() : 0);
/* 116 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return "Item{identifier=" + this.identifier + ", amount=" + this.amount + ", data=" + this.data + ", tag=" + this.tag + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\item\DataItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */