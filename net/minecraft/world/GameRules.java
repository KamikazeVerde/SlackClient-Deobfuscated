/*     */ package net.minecraft.world;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ 
/*     */ public class GameRules
/*     */ {
/*   9 */   private TreeMap<String, Value> theGameRules = new TreeMap<>();
/*     */ 
/*     */   
/*     */   public GameRules() {
/*  13 */     addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
/*  14 */     addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
/*  15 */     addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
/*  16 */     addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
/*  17 */     addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
/*  18 */     addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
/*  19 */     addGameRule("doEntityDrops", "true", ValueType.BOOLEAN_VALUE);
/*  20 */     addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
/*  21 */     addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
/*  22 */     addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
/*  23 */     addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
/*  24 */     addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
/*  25 */     addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
/*  26 */     addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
/*  27 */     addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addGameRule(String key, String value, ValueType type) {
/*  32 */     this.theGameRules.put(key, new Value(value, type));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOrCreateGameRule(String key, String ruleValue) {
/*  37 */     Value gamerules$value = this.theGameRules.get(key);
/*     */     
/*  39 */     if (gamerules$value != null) {
/*     */       
/*  41 */       gamerules$value.setValue(ruleValue);
/*     */     }
/*     */     else {
/*     */       
/*  45 */       addGameRule(key, ruleValue, ValueType.ANY_VALUE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGameRuleStringValue(String name) {
/*  54 */     Value gamerules$value = this.theGameRules.get(name);
/*  55 */     return (gamerules$value != null) ? gamerules$value.getGameRuleStringValue() : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getGameRuleBooleanValue(String name) {
/*  63 */     Value gamerules$value = this.theGameRules.get(name);
/*  64 */     return (gamerules$value != null) ? gamerules$value.getGameRuleBooleanValue() : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(String name) {
/*  69 */     Value gamerules$value = this.theGameRules.get(name);
/*  70 */     return (gamerules$value != null) ? gamerules$value.getInt() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT() {
/*  78 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/*  80 */     for (String s : this.theGameRules.keySet()) {
/*     */       
/*  82 */       Value gamerules$value = this.theGameRules.get(s);
/*  83 */       nbttagcompound.setString(s, gamerules$value.getGameRuleStringValue());
/*     */     } 
/*     */     
/*  86 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  94 */     for (String s : nbt.getKeySet()) {
/*     */       
/*  96 */       String s1 = nbt.getString(s);
/*  97 */       setOrCreateGameRule(s, s1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getRules() {
/* 106 */     Set<String> set = this.theGameRules.keySet();
/* 107 */     return set.<String>toArray(new String[set.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasRule(String name) {
/* 115 */     return this.theGameRules.containsKey(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean areSameType(String key, ValueType otherValue) {
/* 120 */     Value gamerules$value = this.theGameRules.get(key);
/* 121 */     return (gamerules$value != null && (gamerules$value.getType() == otherValue || otherValue == ValueType.ANY_VALUE));
/*     */   }
/*     */ 
/*     */   
/*     */   static class Value
/*     */   {
/*     */     private String valueString;
/*     */     private boolean valueBoolean;
/*     */     private int valueInteger;
/*     */     private double valueDouble;
/*     */     private final GameRules.ValueType type;
/*     */     
/*     */     public Value(String value, GameRules.ValueType type) {
/* 134 */       this.type = type;
/* 135 */       setValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(String value) {
/* 140 */       this.valueString = value;
/*     */       
/* 142 */       if (value != null) {
/*     */         
/* 144 */         if (value.equals("false")) {
/*     */           
/* 146 */           this.valueBoolean = false;
/*     */           
/*     */           return;
/*     */         } 
/* 150 */         if (value.equals("true")) {
/*     */           
/* 152 */           this.valueBoolean = true;
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 157 */       this.valueBoolean = Boolean.parseBoolean(value);
/* 158 */       this.valueInteger = this.valueBoolean ? 1 : 0;
/*     */ 
/*     */       
/*     */       try {
/* 162 */         this.valueInteger = Integer.parseInt(value);
/*     */       }
/* 164 */       catch (NumberFormatException numberFormatException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 171 */         this.valueDouble = Double.parseDouble(value);
/*     */       }
/* 173 */       catch (NumberFormatException numberFormatException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getGameRuleStringValue() {
/* 181 */       return this.valueString;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getGameRuleBooleanValue() {
/* 186 */       return this.valueBoolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getInt() {
/* 191 */       return this.valueInteger;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameRules.ValueType getType() {
/* 196 */       return this.type;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ValueType
/*     */   {
/* 202 */     ANY_VALUE,
/* 203 */     BOOLEAN_VALUE,
/* 204 */     NUMERICAL_VALUE;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\world\GameRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */