/*     */ package net.minecraft.client.entity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.network.NetworkPlayerInfo;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.StringUtils;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.optifine.player.CapeUtils;
/*     */ import net.optifine.player.PlayerConfigurations;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public abstract class AbstractClientPlayer
/*     */   extends EntityPlayer {
/*     */   private NetworkPlayerInfo playerInfo;
/*  29 */   private ResourceLocation locationOfCape = null;
/*  30 */   private long reloadCapeTimeMs = 0L;
/*     */   private boolean elytraOfCape = false;
/*  32 */   private String nameClear = null;
/*  33 */   private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
/*     */ 
/*     */   
/*     */   public AbstractClientPlayer(World worldIn, GameProfile playerProfile) {
/*  37 */     super(worldIn, playerProfile);
/*  38 */     this.nameClear = playerProfile.getName();
/*     */     
/*  40 */     if (this.nameClear != null && !this.nameClear.isEmpty())
/*     */     {
/*  42 */       this.nameClear = StringUtils.stripControlCodes(this.nameClear);
/*     */     }
/*     */     
/*  45 */     CapeUtils.downloadCape(this);
/*  46 */     PlayerConfigurations.getPlayerConfiguration(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  54 */     NetworkPlayerInfo networkplayerinfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getGameProfile().getId());
/*  55 */     return (networkplayerinfo != null && networkplayerinfo.getGameType() == WorldSettings.GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPlayerInfo() {
/*  63 */     return (getPlayerInfo() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NetworkPlayerInfo getPlayerInfo() {
/*  68 */     if (this.playerInfo == null)
/*     */     {
/*  70 */       this.playerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(getUniqueID());
/*     */     }
/*     */     
/*  73 */     return this.playerInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSkin() {
/*  81 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  82 */     return (networkplayerinfo != null && networkplayerinfo.hasLocationSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationSkin() {
/*  90 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/*  91 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getDefaultSkin(getUniqueID()) : networkplayerinfo.getLocationSkin();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationCape() {
/*  96 */     if (!Config.isShowCapes())
/*     */     {
/*  98 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 102 */     if (this.reloadCapeTimeMs != 0L && System.currentTimeMillis() > this.reloadCapeTimeMs) {
/*     */       
/* 104 */       CapeUtils.reloadCape(this);
/* 105 */       this.reloadCapeTimeMs = 0L;
/*     */     } 
/*     */     
/* 108 */     if (this.locationOfCape != null)
/*     */     {
/* 110 */       return this.locationOfCape;
/*     */     }
/*     */ 
/*     */     
/* 114 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 115 */     return (networkplayerinfo == null) ? null : networkplayerinfo.getLocationCape();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadDownloadImageData getDownloadImageSkin(ResourceLocation resourceLocationIn, String username) {
/*     */     ThreadDownloadImageData threadDownloadImageData;
/* 122 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 123 */     ITextureObject itextureobject = texturemanager.getTexture(resourceLocationIn);
/*     */     
/* 125 */     if (itextureobject == null) {
/*     */       
/* 127 */       threadDownloadImageData = new ThreadDownloadImageData(null, String.format("http://skins.minecraft.net/MinecraftSkins/%s.png", new Object[] { StringUtils.stripControlCodes(username) }), DefaultPlayerSkin.getDefaultSkin(getOfflineUUID(username)), (IImageBuffer)new ImageBufferDownload());
/* 128 */       texturemanager.loadTexture(resourceLocationIn, (ITextureObject)threadDownloadImageData);
/*     */     } 
/*     */     
/* 131 */     return threadDownloadImageData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceLocation getLocationSkin(String username) {
/* 141 */     return new ResourceLocation("skins/" + StringUtils.stripControlCodes(username));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSkinType() {
/* 146 */     NetworkPlayerInfo networkplayerinfo = getPlayerInfo();
/* 147 */     return (networkplayerinfo == null) ? DefaultPlayerSkin.getSkinType(getUniqueID()) : networkplayerinfo.getSkinType();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFovModifier() {
/* 152 */     float f = 1.0F;
/*     */     
/* 154 */     if (this.capabilities.isFlying)
/*     */     {
/* 156 */       f *= 1.1F;
/*     */     }
/*     */     
/* 159 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
/* 160 */     f = (float)(f * (iattributeinstance.getAttributeValue() / this.capabilities.getWalkSpeed() + 1.0D) / 2.0D);
/*     */     
/* 162 */     if (this.capabilities.getWalkSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f))
/*     */     {
/* 164 */       f = 1.0F;
/*     */     }
/*     */     
/* 167 */     if (isUsingItem() && getItemInUse().getItem() == Items.bow) {
/*     */       
/* 169 */       int i = getItemInUseDuration();
/* 170 */       float f1 = i / 20.0F;
/*     */       
/* 172 */       if (f1 > 1.0F) {
/*     */         
/* 174 */         f1 = 1.0F;
/*     */       }
/*     */       else {
/*     */         
/* 178 */         f1 *= f1;
/*     */       } 
/*     */       
/* 181 */       f *= 1.0F - f1 * 0.15F;
/*     */     } 
/*     */     
/* 184 */     return Reflector.ForgeHooksClient_getOffsetFOV.exists() ? Reflector.callFloat(Reflector.ForgeHooksClient_getOffsetFOV, new Object[] { this, Float.valueOf(f) }) : f;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameClear() {
/* 189 */     return this.nameClear;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLocationOfCape() {
/* 194 */     return this.locationOfCape;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocationOfCape(ResourceLocation p_setLocationOfCape_1_) {
/* 199 */     this.locationOfCape = p_setLocationOfCape_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasElytraCape() {
/* 204 */     ResourceLocation resourcelocation = getLocationCape();
/* 205 */     return (resourcelocation == null) ? false : ((resourcelocation == this.locationOfCape) ? this.elytraOfCape : true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setElytraOfCape(boolean p_setElytraOfCape_1_) {
/* 210 */     this.elytraOfCape = p_setElytraOfCape_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isElytraOfCape() {
/* 215 */     return this.elytraOfCape;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getReloadCapeTimeMs() {
/* 220 */     return this.reloadCapeTimeMs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReloadCapeTimeMs(long p_setReloadCapeTimeMs_1_) {
/* 225 */     this.reloadCapeTimeMs = p_setReloadCapeTimeMs_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getLook(float partialTicks) {
/* 233 */     return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\entity\AbstractClientPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */