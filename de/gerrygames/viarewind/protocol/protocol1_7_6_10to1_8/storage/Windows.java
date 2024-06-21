/*     */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StoredObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class Windows
/*     */   extends StoredObject {
/*   9 */   public HashMap<Short, Short> types = new HashMap<>();
/*  10 */   public HashMap<Short, Furnace> furnace = new HashMap<>();
/*  11 */   public short levelCost = 0;
/*  12 */   public short anvilId = -1;
/*     */   
/*     */   public Windows(UserConnection user) {
/*  15 */     super(user);
/*     */   }
/*     */   
/*     */   public short get(short windowId) {
/*  19 */     return ((Short)this.types.getOrDefault(Short.valueOf(windowId), Short.valueOf((short)-1))).shortValue();
/*     */   }
/*     */   
/*     */   public void remove(short windowId) {
/*  23 */     this.types.remove(Short.valueOf(windowId));
/*  24 */     this.furnace.remove(Short.valueOf(windowId));
/*     */   }
/*     */   
/*     */   public static int getInventoryType(String name) {
/*  28 */     switch (name) {
/*     */       case "minecraft:container":
/*  30 */         return 0;
/*     */       case "minecraft:chest":
/*  32 */         return 0;
/*     */       case "minecraft:crafting_table":
/*  34 */         return 1;
/*     */       case "minecraft:furnace":
/*  36 */         return 2;
/*     */       case "minecraft:dispenser":
/*  38 */         return 3;
/*     */       case "minecraft:enchanting_table":
/*  40 */         return 4;
/*     */       case "minecraft:brewing_stand":
/*  42 */         return 5;
/*     */       case "minecraft:villager":
/*  44 */         return 6;
/*     */       case "minecraft:beacon":
/*  46 */         return 7;
/*     */       case "minecraft:anvil":
/*  48 */         return 8;
/*     */       case "minecraft:hopper":
/*  50 */         return 9;
/*     */       case "minecraft:dropper":
/*  52 */         return 10;
/*     */       case "EntityHorse":
/*  54 */         return 11;
/*     */     } 
/*  56 */     throw new IllegalArgumentException("Unknown type " + name);
/*     */   }
/*     */   
/*     */   public static class Furnace
/*     */   {
/*  61 */     private short fuelLeft = 0;
/*  62 */     private short maxFuel = 0;
/*  63 */     private short progress = 0;
/*  64 */     private short maxProgress = 200;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short getFuelLeft() {
/*  70 */       return this.fuelLeft;
/*     */     }
/*     */     
/*     */     public short getMaxFuel() {
/*  74 */       return this.maxFuel;
/*     */     }
/*     */     
/*     */     public short getProgress() {
/*  78 */       return this.progress;
/*     */     }
/*     */     
/*     */     public short getMaxProgress() {
/*  82 */       return this.maxProgress;
/*     */     }
/*     */     
/*     */     public void setFuelLeft(short fuelLeft) {
/*  86 */       this.fuelLeft = fuelLeft;
/*     */     }
/*     */     
/*     */     public void setMaxFuel(short maxFuel) {
/*  90 */       this.maxFuel = maxFuel;
/*     */     }
/*     */     
/*     */     public void setProgress(short progress) {
/*  94 */       this.progress = progress;
/*     */     }
/*     */     
/*     */     public void setMaxProgress(short maxProgress) {
/*  98 */       this.maxProgress = maxProgress;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 102 */       if (o == this) return true; 
/* 103 */       if (!(o instanceof Furnace))
/* 104 */         return false; 
/* 105 */       Furnace other = (Furnace)o;
/* 106 */       if (!other.canEqual(this)) return false; 
/* 107 */       if (getFuelLeft() != other.getFuelLeft()) return false; 
/* 108 */       if (getMaxFuel() != other.getMaxFuel()) return false; 
/* 109 */       if (getProgress() != other.getProgress()) return false; 
/* 110 */       return (getMaxProgress() == other.getMaxProgress());
/*     */     }
/*     */     
/*     */     protected boolean canEqual(Object other) {
/* 114 */       return other instanceof Furnace;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 118 */       int PRIME = 59;
/* 119 */       int result = 1;
/* 120 */       result = result * 59 + getFuelLeft();
/* 121 */       result = result * 59 + getMaxFuel();
/* 122 */       result = result * 59 + getProgress();
/* 123 */       result = result * 59 + getMaxProgress();
/* 124 */       return result;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 128 */       return "Windows.Furnace(fuelLeft=" + getFuelLeft() + ", maxFuel=" + getMaxFuel() + ", progress=" + getProgress() + ", maxProgress=" + getMaxProgress() + ")";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\Windows.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */