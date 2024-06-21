/*     */ package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;
/*     */ 
/*     */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*     */ import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
/*     */ import com.viaversion.viaversion.util.GsonUtil;
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
/*     */ public class RecipeData
/*     */ {
/*     */   public static Map<String, Recipe> recipes;
/*     */   
/*     */   public static void init() {
/*  33 */     InputStream stream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/itemrecipes1_12_2to1_13.json");
/*  34 */     InputStreamReader reader = new InputStreamReader(stream);
/*     */     try {
/*  36 */       recipes = (Map<String, Recipe>)GsonUtil.getGson().fromJson(reader, (new TypeToken<Map<String, Recipe>>()
/*     */           {
/*     */           
/*  39 */           }).getType());
/*     */     } finally {
/*     */       
/*     */       try {
/*  43 */         reader.close();
/*  44 */       } catch (IOException iOException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Recipe
/*     */   {
/*     */     private String type;
/*     */     private String group;
/*     */     private int width;
/*     */     private int height;
/*     */     private float experience;
/*     */     private int cookingTime;
/*     */     private DataItem[] ingredient;
/*     */     private DataItem[][] ingredients;
/*     */     private DataItem result;
/*     */     
/*     */     public String getType() {
/*  62 */       return this.type;
/*     */     }
/*     */     
/*     */     public void setType(String type) {
/*  66 */       this.type = type;
/*     */     }
/*     */     
/*     */     public String getGroup() {
/*  70 */       return this.group;
/*     */     }
/*     */     
/*     */     public void setGroup(String group) {
/*  74 */       this.group = group;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/*  78 */       return this.width;
/*     */     }
/*     */     
/*     */     public void setWidth(int width) {
/*  82 */       this.width = width;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/*  86 */       return this.height;
/*     */     }
/*     */     
/*     */     public void setHeight(int height) {
/*  90 */       this.height = height;
/*     */     }
/*     */     
/*     */     public float getExperience() {
/*  94 */       return this.experience;
/*     */     }
/*     */     
/*     */     public void setExperience(float experience) {
/*  98 */       this.experience = experience;
/*     */     }
/*     */     
/*     */     public int getCookingTime() {
/* 102 */       return this.cookingTime;
/*     */     }
/*     */     
/*     */     public void setCookingTime(int cookingTime) {
/* 106 */       this.cookingTime = cookingTime;
/*     */     }
/*     */     
/*     */     public DataItem[] getIngredient() {
/* 110 */       return this.ingredient;
/*     */     }
/*     */     
/*     */     public void setIngredient(DataItem[] ingredient) {
/* 114 */       this.ingredient = ingredient;
/*     */     }
/*     */     
/*     */     public DataItem[][] getIngredients() {
/* 118 */       return this.ingredients;
/*     */     }
/*     */     
/*     */     public void setIngredients(DataItem[][] ingredients) {
/* 122 */       this.ingredients = ingredients;
/*     */     }
/*     */     
/*     */     public DataItem getResult() {
/* 126 */       return this.result;
/*     */     }
/*     */     
/*     */     public void setResult(DataItem result) {
/* 130 */       this.result = result;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_13to1_12_2\data\RecipeData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */