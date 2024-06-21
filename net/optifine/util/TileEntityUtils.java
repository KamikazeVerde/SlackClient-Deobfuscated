/*     */ package net.optifine.util;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TileEntityUtils
/*     */ {
/*     */   public static String getTileEntityName(IBlockAccess blockAccess, BlockPos blockPos) {
/*  21 */     TileEntity tileentity = blockAccess.getTileEntity(blockPos);
/*  22 */     return getTileEntityName(tileentity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTileEntityName(TileEntity te) {
/*  27 */     if (!(te instanceof IWorldNameable))
/*     */     {
/*  29 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  33 */     IWorldNameable iworldnameable = (IWorldNameable)te;
/*  34 */     updateTileEntityName(te);
/*  35 */     return !iworldnameable.hasCustomName() ? null : iworldnameable.getCommandSenderName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateTileEntityName(TileEntity te) {
/*  41 */     BlockPos blockpos = te.getPos();
/*  42 */     String s = getTileEntityRawName(te);
/*     */     
/*  44 */     if (s == null) {
/*     */       
/*  46 */       String s1 = getServerTileEntityRawName(blockpos);
/*  47 */       s1 = Config.normalize(s1);
/*  48 */       setTileEntityRawName(te, s1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getServerTileEntityRawName(BlockPos blockPos) {
/*  54 */     TileEntity tileentity = IntegratedServerUtils.getTileEntity(blockPos);
/*  55 */     return (tileentity == null) ? null : getTileEntityRawName(tileentity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTileEntityRawName(TileEntity te) {
/*  60 */     if (te instanceof net.minecraft.tileentity.TileEntityBeacon)
/*     */     {
/*  62 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName);
/*     */     }
/*  64 */     if (te instanceof net.minecraft.tileentity.TileEntityBrewingStand)
/*     */     {
/*  66 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityBrewingStand_customName);
/*     */     }
/*  68 */     if (te instanceof net.minecraft.tileentity.TileEntityEnchantmentTable)
/*     */     {
/*  70 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityEnchantmentTable_customName);
/*     */     }
/*  72 */     if (te instanceof net.minecraft.tileentity.TileEntityFurnace)
/*     */     {
/*  74 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityFurnace_customName);
/*     */     }
/*     */ 
/*     */     
/*  78 */     if (te instanceof IWorldNameable) {
/*     */       
/*  80 */       IWorldNameable iworldnameable = (IWorldNameable)te;
/*     */       
/*  82 */       if (iworldnameable.hasCustomName())
/*     */       {
/*  84 */         return iworldnameable.getCommandSenderName();
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setTileEntityRawName(TileEntity te, String name) {
/*  94 */     if (te instanceof net.minecraft.tileentity.TileEntityBeacon)
/*     */     {
/*  96 */       return Reflector.setFieldValue(te, Reflector.TileEntityBeacon_customName, name);
/*     */     }
/*  98 */     if (te instanceof net.minecraft.tileentity.TileEntityBrewingStand)
/*     */     {
/* 100 */       return Reflector.setFieldValue(te, Reflector.TileEntityBrewingStand_customName, name);
/*     */     }
/* 102 */     if (te instanceof net.minecraft.tileentity.TileEntityEnchantmentTable)
/*     */     {
/* 104 */       return Reflector.setFieldValue(te, Reflector.TileEntityEnchantmentTable_customName, name);
/*     */     }
/* 106 */     if (te instanceof net.minecraft.tileentity.TileEntityFurnace)
/*     */     {
/* 108 */       return Reflector.setFieldValue(te, Reflector.TileEntityFurnace_customName, name);
/*     */     }
/* 110 */     if (te instanceof TileEntityChest) {
/*     */       
/* 112 */       ((TileEntityChest)te).setCustomName(name);
/* 113 */       return true;
/*     */     } 
/* 115 */     if (te instanceof TileEntityDispenser) {
/*     */       
/* 117 */       ((TileEntityDispenser)te).setCustomName(name);
/* 118 */       return true;
/*     */     } 
/* 120 */     if (te instanceof TileEntityHopper) {
/*     */       
/* 122 */       ((TileEntityHopper)te).setCustomName(name);
/* 123 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\TileEntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */