/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ 
/*     */ public abstract class LayerArmorBase<T extends ModelBase>
/*     */   implements LayerRenderer<EntityLivingBase> {
/*  22 */   protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*     */   protected T field_177189_c;
/*     */   protected T field_177186_d;
/*     */   private final RendererLivingEntity<?> renderer;
/*  26 */   private float alpha = 1.0F;
/*  27 */   private float colorR = 1.0F;
/*  28 */   private float colorG = 1.0F;
/*  29 */   private float colorB = 1.0F;
/*     */   private boolean field_177193_i;
/*  31 */   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public LayerArmorBase(RendererLivingEntity<?> rendererIn) {
/*  35 */     this.renderer = rendererIn;
/*  36 */     initArmor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/*  41 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 4);
/*  42 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 3);
/*  43 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 2);
/*  44 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/*  49 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderLayer(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float p_177182_4_, float p_177182_5_, float p_177182_6_, float p_177182_7_, float p_177182_8_, int armorSlot) {
/*  54 */     ItemStack itemstack = getCurrentArmor(entitylivingbaseIn, armorSlot);
/*     */     
/*  56 */     if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
/*     */       int i; float f, f1, f2;
/*  58 */       ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*  59 */       T t = func_177175_a(armorSlot);
/*  60 */       t.setModelAttributes(this.renderer.getMainModel());
/*  61 */       t.setLivingAnimations(entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_4_);
/*     */       
/*  63 */       if (Reflector.ForgeHooksClient.exists())
/*     */       {
/*  65 */         t = getArmorModelHook(entitylivingbaseIn, itemstack, armorSlot, t);
/*     */       }
/*     */       
/*  68 */       func_177179_a(t, armorSlot);
/*  69 */       boolean flag = isSlotForLeggings(armorSlot);
/*     */       
/*  71 */       if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, null))
/*     */       {
/*  73 */         if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
/*     */           
/*  75 */           this.renderer.bindTexture(getArmorResource((Entity)entitylivingbaseIn, itemstack, flag ? 2 : 1, null));
/*     */         }
/*     */         else {
/*     */           
/*  79 */           this.renderer.bindTexture(getArmorResource(itemarmor, flag));
/*     */         } 
/*     */       }
/*     */       
/*  83 */       if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
/*     */         
/*  85 */         if (ReflectorForge.armorHasOverlay(itemarmor, itemstack)) {
/*     */           
/*  87 */           int j = itemarmor.getColor(itemstack);
/*  88 */           float f3 = (j >> 16 & 0xFF) / 255.0F;
/*  89 */           float f4 = (j >> 8 & 0xFF) / 255.0F;
/*  90 */           float f5 = (j & 0xFF) / 255.0F;
/*  91 */           GlStateManager.color(this.colorR * f3, this.colorG * f4, this.colorB * f5, this.alpha);
/*  92 */           t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
/*     */           
/*  94 */           if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, "overlay"))
/*     */           {
/*  96 */             this.renderer.bindTexture(getArmorResource((Entity)entitylivingbaseIn, itemstack, flag ? 2 : 1, "overlay"));
/*     */           }
/*     */         } 
/*     */         
/* 100 */         GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
/* 101 */         t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
/*     */         
/* 103 */         if (!this.field_177193_i && itemstack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entitylivingbaseIn, itemstack, (ModelBase)t, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_)))
/*     */         {
/* 105 */           func_177183_a(entitylivingbaseIn, t, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 111 */       switch (itemarmor.getArmorMaterial()) {
/*     */         
/*     */         case LEATHER:
/* 114 */           i = itemarmor.getColor(itemstack);
/* 115 */           f = (i >> 16 & 0xFF) / 255.0F;
/* 116 */           f1 = (i >> 8 & 0xFF) / 255.0F;
/* 117 */           f2 = (i & 0xFF) / 255.0F;
/* 118 */           GlStateManager.color(this.colorR * f, this.colorG * f1, this.colorB * f2, this.alpha);
/* 119 */           t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
/*     */           
/* 121 */           if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, "overlay"))
/*     */           {
/* 123 */             this.renderer.bindTexture(getArmorResource(itemarmor, flag, "overlay"));
/*     */           }
/*     */         
/*     */         case CHAIN:
/*     */         case IRON:
/*     */         case GOLD:
/*     */         case DIAMOND:
/* 130 */           GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
/* 131 */           t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
/*     */           break;
/*     */       } 
/* 134 */       if (!this.field_177193_i && itemstack.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entitylivingbaseIn, itemstack, (ModelBase)t, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_)))
/*     */       {
/* 136 */         func_177183_a(entitylivingbaseIn, t, p_177182_2_, p_177182_3_, p_177182_4_, p_177182_5_, p_177182_6_, p_177182_7_, p_177182_8_);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentArmor(EntityLivingBase entitylivingbaseIn, int armorSlot) {
/* 143 */     return entitylivingbaseIn.getCurrentArmor(armorSlot - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public T func_177175_a(int p_177175_1_) {
/* 148 */     return isSlotForLeggings(p_177175_1_) ? this.field_177189_c : this.field_177186_d;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSlotForLeggings(int armorSlot) {
/* 153 */     return (armorSlot == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_177183_a(EntityLivingBase entitylivingbaseIn, T modelbaseIn, float p_177183_3_, float p_177183_4_, float p_177183_5_, float p_177183_6_, float p_177183_7_, float p_177183_8_, float p_177183_9_) {
/* 158 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*     */       
/* 160 */       float f = entitylivingbaseIn.ticksExisted + p_177183_5_;
/* 161 */       this.renderer.bindTexture(ENCHANTED_ITEM_GLINT_RES);
/*     */       
/* 163 */       if (Config.isShaders())
/*     */       {
/* 165 */         ShadersRender.renderEnchantedGlintBegin();
/*     */       }
/*     */       
/* 168 */       GlStateManager.enableBlend();
/* 169 */       GlStateManager.depthFunc(514);
/* 170 */       GlStateManager.depthMask(false);
/* 171 */       float f1 = 0.5F;
/* 172 */       GlStateManager.color(f1, f1, f1, 1.0F);
/*     */       
/* 174 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 176 */         GlStateManager.disableLighting();
/* 177 */         GlStateManager.blendFunc(768, 1);
/* 178 */         float f2 = 0.76F;
/* 179 */         GlStateManager.color(0.5F * f2, 0.25F * f2, 0.8F * f2, 1.0F);
/* 180 */         GlStateManager.matrixMode(5890);
/* 181 */         GlStateManager.loadIdentity();
/* 182 */         float f3 = 0.33333334F;
/* 183 */         GlStateManager.scale(f3, f3, f3);
/* 184 */         GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
/* 185 */         GlStateManager.translate(0.0F, f * (0.001F + i * 0.003F) * 20.0F, 0.0F);
/* 186 */         GlStateManager.matrixMode(5888);
/* 187 */         modelbaseIn.render((Entity)entitylivingbaseIn, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_, p_177183_8_, p_177183_9_);
/*     */       } 
/*     */       
/* 190 */       GlStateManager.matrixMode(5890);
/* 191 */       GlStateManager.loadIdentity();
/* 192 */       GlStateManager.matrixMode(5888);
/* 193 */       GlStateManager.enableLighting();
/* 194 */       GlStateManager.depthMask(true);
/* 195 */       GlStateManager.depthFunc(515);
/* 196 */       GlStateManager.disableBlend();
/*     */       
/* 198 */       if (Config.isShaders())
/*     */       {
/* 200 */         ShadersRender.renderEnchantedGlintEnd();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getArmorResource(ItemArmor p_177181_1_, boolean p_177181_2_) {
/* 207 */     return getArmorResource(p_177181_1_, p_177181_2_, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getArmorResource(ItemArmor p_177178_1_, boolean p_177178_2_, String p_177178_3_) {
/* 212 */     String s = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[] { p_177178_1_.getArmorMaterial().getName(), Integer.valueOf(p_177178_2_ ? 2 : 1), (p_177178_3_ == null) ? "" : String.format("_%s", new Object[] { p_177178_3_ }) });
/* 213 */     ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s);
/*     */     
/* 215 */     if (resourcelocation == null) {
/*     */       
/* 217 */       resourcelocation = new ResourceLocation(s);
/* 218 */       ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
/*     */     } 
/*     */     
/* 221 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void initArmor();
/*     */   
/*     */   protected abstract void func_177179_a(T paramT, int paramInt);
/*     */   
/*     */   protected T getArmorModelHook(EntityLivingBase p_getArmorModelHook_1_, ItemStack p_getArmorModelHook_2_, int p_getArmorModelHook_3_, T p_getArmorModelHook_4_) {
/* 230 */     return p_getArmorModelHook_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getArmorResource(Entity p_getArmorResource_1_, ItemStack p_getArmorResource_2_, int p_getArmorResource_3_, String p_getArmorResource_4_) {
/* 235 */     ItemArmor itemarmor = (ItemArmor)p_getArmorResource_2_.getItem();
/* 236 */     String s = itemarmor.getArmorMaterial().getName();
/* 237 */     String s1 = "minecraft";
/* 238 */     int i = s.indexOf(':');
/*     */     
/* 240 */     if (i != -1) {
/*     */       
/* 242 */       s1 = s.substring(0, i);
/* 243 */       s = s.substring(i + 1);
/*     */     } 
/*     */     
/* 246 */     String s2 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", new Object[] { s1, s, Integer.valueOf(isSlotForLeggings(p_getArmorResource_3_) ? 2 : 1), (p_getArmorResource_4_ == null) ? "" : String.format("_%s", new Object[] { p_getArmorResource_4_ }) });
/* 247 */     s2 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, new Object[] { p_getArmorResource_1_, p_getArmorResource_2_, s2, Integer.valueOf(p_getArmorResource_3_), p_getArmorResource_4_ });
/* 248 */     ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s2);
/*     */     
/* 250 */     if (resourcelocation == null) {
/*     */       
/* 252 */       resourcelocation = new ResourceLocation(s2);
/* 253 */       ARMOR_TEXTURE_RES_MAP.put(s2, resourcelocation);
/*     */     } 
/*     */     
/* 256 */     return resourcelocation;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\layers\LayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */