/*     */ package net.optifine.reflect;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemMap;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class ReflectorForge
/*     */ {
/*  21 */   public static Object EVENT_RESULT_ALLOW = Reflector.getFieldValue(Reflector.Event_Result_ALLOW);
/*  22 */   public static Object EVENT_RESULT_DENY = Reflector.getFieldValue(Reflector.Event_Result_DENY);
/*  23 */   public static Object EVENT_RESULT_DEFAULT = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
/*     */ 
/*     */   
/*     */   public static void FMLClientHandler_trackBrokenTexture(ResourceLocation loc, String message) {
/*  27 */     if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
/*     */       
/*  29 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  30 */       Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] { loc, message });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void FMLClientHandler_trackMissingTexture(ResourceLocation loc) {
/*  36 */     if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
/*     */       
/*  38 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*  39 */       Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, new Object[] { loc });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void putLaunchBlackboard(String key, Object value) {
/*  45 */     Map<String, Object> map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
/*     */     
/*  47 */     if (map != null)
/*     */     {
/*  49 */       map.put(key, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean renderFirstPersonHand(RenderGlobal renderGlobal, float partialTicks, int pass) {
/*  55 */     return !Reflector.ForgeHooksClient_renderFirstPersonHand.exists() ? false : Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, new Object[] { renderGlobal, Float.valueOf(partialTicks), Integer.valueOf(pass) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static InputStream getOptiFineResourceStream(String path) {
/*  60 */     if (!Reflector.OptiFineClassTransformer_instance.exists())
/*     */     {
/*  62 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  66 */     Object object = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
/*     */     
/*  68 */     if (object == null)
/*     */     {
/*  70 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  74 */     if (path.startsWith("/"))
/*     */     {
/*  76 */       path = path.substring(1);
/*     */     }
/*     */     
/*  79 */     byte[] abyte = (byte[])Reflector.call(object, Reflector.OptiFineClassTransformer_getOptiFineResource, new Object[] { path });
/*     */     
/*  81 */     if (abyte == null)
/*     */     {
/*  83 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  87 */     InputStream inputstream = new ByteArrayInputStream(abyte);
/*  88 */     return inputstream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean blockHasTileEntity(IBlockState state) {
/*  96 */     Block block = state.getBlock();
/*  97 */     return !Reflector.ForgeBlock_hasTileEntity.exists() ? block.hasTileEntity() : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] { state });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isItemDamaged(ItemStack stack) {
/* 102 */     return !Reflector.ForgeItem_showDurabilityBar.exists() ? stack.isItemDamaged() : Reflector.callBoolean(stack.getItem(), Reflector.ForgeItem_showDurabilityBar, new Object[] { stack });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean armorHasOverlay(ItemArmor itemArmor, ItemStack itemStack) {
/* 107 */     int i = itemArmor.getColor(itemStack);
/* 108 */     return (i != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static MapData getMapData(ItemMap itemMap, ItemStack stack, World world) {
/* 113 */     return Reflector.ForgeHooksClient.exists() ? ((ItemMap)stack.getItem()).getMapData(stack, world) : itemMap.getMapData(stack, world);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getForgeModIds() {
/* 118 */     if (!Reflector.Loader.exists())
/*     */     {
/* 120 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 124 */     Object object = Reflector.call(Reflector.Loader_instance, new Object[0]);
/* 125 */     List list = (List)Reflector.call(object, Reflector.Loader_getActiveModList, new Object[0]);
/*     */     
/* 127 */     if (list == null)
/*     */     {
/* 129 */       return new String[0];
/*     */     }
/*     */ 
/*     */     
/* 133 */     List<String> list1 = new ArrayList<>();
/*     */     
/* 135 */     for (Object object1 : list) {
/*     */       
/* 137 */       if (Reflector.ModContainer.isInstance(object1)) {
/*     */         
/* 139 */         String s = Reflector.callString(object1, Reflector.ModContainer_getModId, new Object[0]);
/*     */         
/* 141 */         if (s != null)
/*     */         {
/* 143 */           list1.add(s);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     String[] astring = list1.<String>toArray(new String[list1.size()]);
/* 149 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canEntitySpawn(EntityLiving entityliving, World world, float x, float y, float z) {
/* 156 */     Object object = Reflector.call(Reflector.ForgeEventFactory_canEntitySpawn, new Object[] { entityliving, world, Float.valueOf(x), Float.valueOf(y), Float.valueOf(z) });
/* 157 */     return (object == EVENT_RESULT_ALLOW || (object == EVENT_RESULT_DEFAULT && entityliving.getCanSpawnHere() && entityliving.isNotColliding()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean doSpecialSpawn(EntityLiving entityliving, World world, float x, int y, float z) {
/* 162 */     return Reflector.ForgeEventFactory_doSpecialSpawn.exists() ? Reflector.callBoolean(Reflector.ForgeEventFactory_doSpecialSpawn, new Object[] { entityliving, world, Float.valueOf(x), Integer.valueOf(y), Float.valueOf(z) }) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\ReflectorForge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */