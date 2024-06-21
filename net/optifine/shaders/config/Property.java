/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.src.Config;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ public class Property
/*     */ {
/*   9 */   private int[] values = null;
/*  10 */   private int defaultValue = 0;
/*  11 */   private String propertyName = null;
/*  12 */   private String[] propertyValues = null;
/*  13 */   private String userName = null;
/*  14 */   private String[] userValues = null;
/*  15 */   private int value = 0;
/*     */ 
/*     */   
/*     */   public Property(String propertyName, String[] propertyValues, String userName, String[] userValues, int defaultValue) {
/*  19 */     this.propertyName = propertyName;
/*  20 */     this.propertyValues = propertyValues;
/*  21 */     this.userName = userName;
/*  22 */     this.userValues = userValues;
/*  23 */     this.defaultValue = defaultValue;
/*     */     
/*  25 */     if (propertyValues.length != userValues.length)
/*     */     {
/*  27 */       throw new IllegalArgumentException("Property and user values have different lengths: " + propertyValues.length + " != " + userValues.length);
/*     */     }
/*  29 */     if (defaultValue >= 0 && defaultValue < propertyValues.length) {
/*     */       
/*  31 */       this.value = defaultValue;
/*     */     }
/*     */     else {
/*     */       
/*  35 */       throw new IllegalArgumentException("Invalid default value: " + defaultValue);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setPropertyValue(String propVal) {
/*  41 */     if (propVal == null) {
/*     */       
/*  43 */       this.value = this.defaultValue;
/*  44 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  48 */     this.value = ArrayUtils.indexOf((Object[])this.propertyValues, propVal);
/*     */     
/*  50 */     if (this.value >= 0 && this.value < this.propertyValues.length)
/*     */     {
/*  52 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  56 */     this.value = this.defaultValue;
/*  57 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  64 */     this.value++;
/*     */     
/*  66 */     if (this.value < 0 || this.value >= this.propertyValues.length)
/*     */     {
/*  68 */       this.value = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(int val) {
/*  74 */     this.value = val;
/*     */     
/*  76 */     if (this.value < 0 || this.value >= this.propertyValues.length)
/*     */     {
/*  78 */       this.value = this.defaultValue;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getValue() {
/*  84 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserValue() {
/*  89 */     return this.userValues[this.value];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyValue() {
/*  94 */     return this.propertyValues[this.value];
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUserName() {
/*  99 */     return this.userName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPropertyName() {
/* 104 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetValue() {
/* 109 */     this.value = this.defaultValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadFrom(Properties props) {
/* 114 */     resetValue();
/*     */     
/* 116 */     if (props == null)
/*     */     {
/* 118 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 122 */     String s = props.getProperty(this.propertyName);
/* 123 */     return (s == null) ? false : setPropertyValue(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveTo(Properties props) {
/* 129 */     if (props != null)
/*     */     {
/* 131 */       props.setProperty(getPropertyName(), getPropertyValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 137 */     return "" + this.propertyName + "=" + getPropertyValue() + " [" + Config.arrayToString((Object[])this.propertyValues) + "], value: " + this.value;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\Property.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */