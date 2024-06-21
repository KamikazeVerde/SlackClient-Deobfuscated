/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.inventory.GuiContainer;
/*     */ import net.minecraft.client.model.ModelBook;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerEnchantment;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.EnchantmentNameParts;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ public class GuiEnchantment extends GuiContainer {
/*  27 */   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   private static final ModelBook MODEL_BOOK = new ModelBook();
/*     */ 
/*     */   
/*     */   private final InventoryPlayer playerInventory;
/*     */ 
/*     */   
/*  43 */   private Random random = new Random();
/*     */   
/*     */   private ContainerEnchantment container;
/*     */   public int field_147073_u;
/*     */   public float field_147071_v;
/*     */   public float field_147069_w;
/*     */   public float field_147082_x;
/*     */   public float field_147081_y;
/*     */   public float field_147080_z;
/*     */   public float field_147076_A;
/*     */   ItemStack field_147077_B;
/*     */   private final IWorldNameable field_175380_I;
/*     */   
/*     */   public GuiEnchantment(InventoryPlayer inventory, World worldIn, IWorldNameable p_i45502_3_) {
/*  57 */     super((Container)new ContainerEnchantment(inventory, worldIn));
/*  58 */     this.playerInventory = inventory;
/*  59 */     this.container = (ContainerEnchantment)this.inventorySlots;
/*  60 */     this.field_175380_I = p_i45502_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
/*  68 */     this.fontRendererObj.drawString(this.field_175380_I.getDisplayName().getUnformattedText(), 12, 5, 4210752);
/*  69 */     this.fontRendererObj.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  77 */     super.updateScreen();
/*  78 */     func_147068_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  86 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*  87 */     int i = (this.width - this.xSize) / 2;
/*  88 */     int j = (this.height - this.ySize) / 2;
/*     */     
/*  90 */     for (int k = 0; k < 3; k++) {
/*     */       
/*  92 */       int l = mouseX - i + 60;
/*  93 */       int i1 = mouseY - j + 14 + 19 * k;
/*     */       
/*  95 */       if (l >= 0 && i1 >= 0 && l < 108 && i1 < 19 && this.container.enchantItem((EntityPlayer)this.mc.thePlayer, k))
/*     */       {
/*  97 */         this.mc.playerController.sendEnchantPacket(this.container.windowId, k);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
/* 107 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 108 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 109 */     int i = (this.width - this.xSize) / 2;
/* 110 */     int j = (this.height - this.ySize) / 2;
/* 111 */     drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
/* 112 */     GlStateManager.pushMatrix();
/* 113 */     GlStateManager.matrixMode(5889);
/* 114 */     GlStateManager.pushMatrix();
/* 115 */     GlStateManager.loadIdentity();
/* 116 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 117 */     GlStateManager.viewport((scaledresolution.getScaledWidth() - 320) / 2 * scaledresolution.getScaleFactor(), (scaledresolution.getScaledHeight() - 240) / 2 * scaledresolution.getScaleFactor(), 320 * scaledresolution.getScaleFactor(), 240 * scaledresolution.getScaleFactor());
/* 118 */     GlStateManager.translate(-0.34F, 0.23F, 0.0F);
/* 119 */     Project.gluPerspective(90.0F, 1.3333334F, 9.0F, 80.0F);
/* 120 */     float f = 1.0F;
/* 121 */     GlStateManager.matrixMode(5888);
/* 122 */     GlStateManager.loadIdentity();
/* 123 */     RenderHelper.enableStandardItemLighting();
/* 124 */     GlStateManager.translate(0.0F, 3.3F, -16.0F);
/* 125 */     GlStateManager.scale(f, f, f);
/* 126 */     float f1 = 5.0F;
/* 127 */     GlStateManager.scale(f1, f1, f1);
/* 128 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 129 */     this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_BOOK_TEXTURE);
/* 130 */     GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 131 */     float f2 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * partialTicks;
/* 132 */     GlStateManager.translate((1.0F - f2) * 0.2F, (1.0F - f2) * 0.1F, (1.0F - f2) * 0.25F);
/* 133 */     GlStateManager.rotate(-(1.0F - f2) * 90.0F - 90.0F, 0.0F, 1.0F, 0.0F);
/* 134 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 135 */     float f3 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.25F;
/* 136 */     float f4 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * partialTicks + 0.75F;
/* 137 */     f3 = (f3 - MathHelper.truncateDoubleToInt(f3)) * 1.6F - 0.3F;
/* 138 */     f4 = (f4 - MathHelper.truncateDoubleToInt(f4)) * 1.6F - 0.3F;
/*     */     
/* 140 */     if (f3 < 0.0F)
/*     */     {
/* 142 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 145 */     if (f4 < 0.0F)
/*     */     {
/* 147 */       f4 = 0.0F;
/*     */     }
/*     */     
/* 150 */     if (f3 > 1.0F)
/*     */     {
/* 152 */       f3 = 1.0F;
/*     */     }
/*     */     
/* 155 */     if (f4 > 1.0F)
/*     */     {
/* 157 */       f4 = 1.0F;
/*     */     }
/*     */     
/* 160 */     GlStateManager.enableRescaleNormal();
/* 161 */     MODEL_BOOK.render(null, 0.0F, f3, f4, f2, 0.0F, 0.0625F);
/* 162 */     GlStateManager.disableRescaleNormal();
/* 163 */     RenderHelper.disableStandardItemLighting();
/* 164 */     GlStateManager.matrixMode(5889);
/* 165 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 166 */     GlStateManager.popMatrix();
/* 167 */     GlStateManager.matrixMode(5888);
/* 168 */     GlStateManager.popMatrix();
/* 169 */     RenderHelper.disableStandardItemLighting();
/* 170 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 171 */     EnchantmentNameParts.getInstance().reseedRandomGenerator(this.container.xpSeed);
/* 172 */     int k = this.container.getLapisAmount();
/*     */     
/* 174 */     for (int l = 0; l < 3; l++) {
/*     */       
/* 176 */       int i1 = i + 60;
/* 177 */       int j1 = i1 + 20;
/* 178 */       int k1 = 86;
/* 179 */       String s = EnchantmentNameParts.getInstance().generateNewRandomName();
/* 180 */       this.zLevel = 0.0F;
/* 181 */       this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
/* 182 */       int l1 = this.container.enchantLevels[l];
/* 183 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/* 185 */       if (l1 == 0) {
/*     */         
/* 187 */         drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/*     */       }
/*     */       else {
/*     */         
/* 191 */         String s1 = "" + l1;
/* 192 */         FontRenderer fontrenderer = this.mc.standardGalacticFontRenderer;
/* 193 */         int i2 = 6839882;
/*     */         
/* 195 */         if ((k < l + 1 || this.mc.thePlayer.experienceLevel < l1) && !this.mc.thePlayer.capabilities.isCreativeMode) {
/*     */           
/* 197 */           drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 185, 108, 19);
/* 198 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 239, 16, 16);
/* 199 */           fontrenderer.drawSplitString(s, j1, j + 16 + 19 * l, k1, (i2 & 0xFEFEFE) >> 1);
/* 200 */           i2 = 4226832;
/*     */         }
/*     */         else {
/*     */           
/* 204 */           int j2 = mouseX - i + 60;
/* 205 */           int k2 = mouseY - j + 14 + 19 * l;
/*     */           
/* 207 */           if (j2 >= 0 && k2 >= 0 && j2 < 108 && k2 < 19) {
/*     */             
/* 209 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 204, 108, 19);
/* 210 */             i2 = 16777088;
/*     */           }
/*     */           else {
/*     */             
/* 214 */             drawTexturedModalRect(i1, j + 14 + 19 * l, 0, 166, 108, 19);
/*     */           } 
/*     */           
/* 217 */           drawTexturedModalRect(i1 + 1, j + 15 + 19 * l, 16 * l, 223, 16, 16);
/* 218 */           fontrenderer.drawSplitString(s, j1, j + 16 + 19 * l, k1, i2);
/* 219 */           i2 = 8453920;
/*     */         } 
/*     */         
/* 222 */         fontrenderer = this.mc.MCfontRenderer;
/* 223 */         fontrenderer.drawStringWithShadow(s1, (j1 + 86 - fontrenderer.getStringWidth(s1)), (j + 16 + 19 * l + 7), i2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 233 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 234 */     boolean flag = this.mc.thePlayer.capabilities.isCreativeMode;
/* 235 */     int i = this.container.getLapisAmount();
/*     */     
/* 237 */     for (int j = 0; j < 3; j++) {
/*     */       
/* 239 */       int k = this.container.enchantLevels[j];
/* 240 */       int l = this.container.field_178151_h[j];
/* 241 */       int i1 = j + 1;
/*     */       
/* 243 */       if (isPointInRegion(60, 14 + 19 * j, 108, 17, mouseX, mouseY) && k > 0 && l >= 0) {
/*     */         
/* 245 */         List<String> list = Lists.newArrayList();
/*     */         
/* 247 */         if (l >= 0 && Enchantment.getEnchantmentById(l & 0xFF) != null) {
/*     */           
/* 249 */           String s = Enchantment.getEnchantmentById(l & 0xFF).getTranslatedName((l & 0xFF00) >> 8);
/* 250 */           list.add(ChatFormatting.WHITE.toString() + ChatFormatting.ITALIC.toString() + I18n.format("container.enchant.clue", new Object[] { s }));
/*     */         } 
/*     */         
/* 253 */         if (!flag) {
/*     */           
/* 255 */           if (l >= 0)
/*     */           {
/* 257 */             list.add("");
/*     */           }
/*     */           
/* 260 */           if (this.mc.thePlayer.experienceLevel < k) {
/*     */             
/* 262 */             list.add(ChatFormatting.RED.toString() + "Level Requirement: " + this.container.enchantLevels[j]);
/*     */           }
/*     */           else {
/*     */             
/* 266 */             String s1 = "";
/*     */             
/* 268 */             if (i1 == 1) {
/*     */               
/* 270 */               s1 = I18n.format("container.enchant.lapis.one", new Object[0]);
/*     */             }
/*     */             else {
/*     */               
/* 274 */               s1 = I18n.format("container.enchant.lapis.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 277 */             if (i >= i1) {
/*     */               
/* 279 */               list.add(ChatFormatting.GRAY.toString() + "" + s1);
/*     */             }
/*     */             else {
/*     */               
/* 283 */               list.add(ChatFormatting.RED.toString() + "" + s1);
/*     */             } 
/*     */             
/* 286 */             if (i1 == 1) {
/*     */               
/* 288 */               s1 = I18n.format("container.enchant.level.one", new Object[0]);
/*     */             }
/*     */             else {
/*     */               
/* 292 */               s1 = I18n.format("container.enchant.level.many", new Object[] { Integer.valueOf(i1) });
/*     */             } 
/*     */             
/* 295 */             list.add(ChatFormatting.GRAY.toString() + "" + s1);
/*     */           } 
/*     */         } 
/*     */         
/* 299 */         drawHoveringText(list, mouseX, mouseY);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_147068_g() {
/* 307 */     ItemStack itemstack = this.inventorySlots.getSlot(0).getStack();
/*     */     
/* 309 */     if (!ItemStack.areItemStacksEqual(itemstack, this.field_147077_B)) {
/*     */       
/* 311 */       this.field_147077_B = itemstack;
/*     */ 
/*     */       
/*     */       do {
/* 315 */         this.field_147082_x += (this.random.nextInt(4) - this.random.nextInt(4));
/*     */       }
/* 317 */       while (this.field_147071_v <= this.field_147082_x + 1.0F && this.field_147071_v >= this.field_147082_x - 1.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 324 */     this.field_147073_u++;
/* 325 */     this.field_147069_w = this.field_147071_v;
/* 326 */     this.field_147076_A = this.field_147080_z;
/* 327 */     boolean flag = false;
/*     */     
/* 329 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 331 */       if (this.container.enchantLevels[i] != 0)
/*     */       {
/* 333 */         flag = true;
/*     */       }
/*     */     } 
/*     */     
/* 337 */     if (flag) {
/*     */       
/* 339 */       this.field_147080_z += 0.2F;
/*     */     }
/*     */     else {
/*     */       
/* 343 */       this.field_147080_z -= 0.2F;
/*     */     } 
/*     */     
/* 346 */     this.field_147080_z = MathHelper.clamp_float(this.field_147080_z, 0.0F, 1.0F);
/* 347 */     float f1 = (this.field_147082_x - this.field_147071_v) * 0.4F;
/* 348 */     float f = 0.2F;
/* 349 */     f1 = MathHelper.clamp_float(f1, -f, f);
/* 350 */     this.field_147081_y += (f1 - this.field_147081_y) * 0.9F;
/* 351 */     this.field_147071_v += this.field_147081_y;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */