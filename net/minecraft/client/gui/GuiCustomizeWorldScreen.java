/*      */ package net.minecraft.client.gui;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.primitives.Floats;
/*      */ import java.io.IOException;
/*      */ import java.util.Random;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.gen.ChunkProviderSettings;
/*      */ 
/*      */ public class GuiCustomizeWorldScreen
/*      */   extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {
/*      */   private GuiCreateWorld field_175343_i;
/*   19 */   protected String field_175341_a = "Customize World Settings";
/*   20 */   protected String field_175333_f = "Page 1 of 3";
/*   21 */   protected String field_175335_g = "Basic Settings";
/*   22 */   protected String[] field_175342_h = new String[4]; private GuiPageButtonList field_175349_r;
/*      */   private GuiButton field_175348_s;
/*      */   private GuiButton field_175347_t;
/*      */   private GuiButton field_175346_u;
/*      */   private GuiButton field_175345_v;
/*      */   private GuiButton field_175344_w;
/*      */   private GuiButton field_175352_x;
/*      */   private GuiButton field_175351_y;
/*      */   private GuiButton field_175350_z;
/*      */   private boolean field_175338_A = false;
/*      */   private boolean field_175340_C = false;
/*   33 */   private int field_175339_B = 0;
/*      */   
/*   35 */   private Predicate<String> field_175332_D = new Predicate<String>()
/*      */     {
/*      */       public boolean apply(String p_apply_1_)
/*      */       {
/*   39 */         Float f = Floats.tryParse(p_apply_1_);
/*   40 */         return (p_apply_1_.length() == 0 || (f != null && Floats.isFinite(f.floatValue()) && f.floatValue() >= 0.0F));
/*      */       }
/*      */     };
/*   43 */   private ChunkProviderSettings.Factory field_175334_E = new ChunkProviderSettings.Factory();
/*      */   
/*      */   private ChunkProviderSettings.Factory field_175336_F;
/*      */   
/*   47 */   private Random random = new Random();
/*      */ 
/*      */   
/*      */   public GuiCustomizeWorldScreen(GuiScreen p_i45521_1_, String p_i45521_2_) {
/*   51 */     this.field_175343_i = (GuiCreateWorld)p_i45521_1_;
/*   52 */     func_175324_a(p_i45521_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initGui() {
/*   61 */     int i = 0;
/*   62 */     int j = 0;
/*      */     
/*   64 */     if (this.field_175349_r != null) {
/*      */       
/*   66 */       i = this.field_175349_r.func_178059_e();
/*   67 */       j = this.field_175349_r.getAmountScrolled();
/*      */     } 
/*      */     
/*   70 */     this.field_175341_a = I18n.format("options.customizeTitle", new Object[0]);
/*   71 */     this.buttonList.clear();
/*   72 */     this.buttonList.add(this.field_175345_v = new GuiButton(302, 20, 5, 80, 20, I18n.format("createWorld.customize.custom.prev", new Object[0])));
/*   73 */     this.buttonList.add(this.field_175344_w = new GuiButton(303, this.width - 100, 5, 80, 20, I18n.format("createWorld.customize.custom.next", new Object[0])));
/*   74 */     this.buttonList.add(this.field_175346_u = new GuiButton(304, this.width / 2 - 187, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.defaults", new Object[0])));
/*   75 */     this.buttonList.add(this.field_175347_t = new GuiButton(301, this.width / 2 - 92, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.randomize", new Object[0])));
/*   76 */     this.buttonList.add(this.field_175350_z = new GuiButton(305, this.width / 2 + 3, this.height - 27, 90, 20, I18n.format("createWorld.customize.custom.presets", new Object[0])));
/*   77 */     this.buttonList.add(this.field_175348_s = new GuiButton(300, this.width / 2 + 98, this.height - 27, 90, 20, I18n.format("gui.done", new Object[0])));
/*   78 */     this.field_175346_u.enabled = this.field_175338_A;
/*   79 */     this.field_175352_x = new GuiButton(306, this.width / 2 - 55, 160, 50, 20, I18n.format("gui.yes", new Object[0]));
/*   80 */     this.field_175352_x.visible = false;
/*   81 */     this.buttonList.add(this.field_175352_x);
/*   82 */     this.field_175351_y = new GuiButton(307, this.width / 2 + 5, 160, 50, 20, I18n.format("gui.no", new Object[0]));
/*   83 */     this.field_175351_y.visible = false;
/*   84 */     this.buttonList.add(this.field_175351_y);
/*      */     
/*   86 */     if (this.field_175339_B != 0) {
/*      */       
/*   88 */       this.field_175352_x.visible = true;
/*   89 */       this.field_175351_y.visible = true;
/*      */     } 
/*      */     
/*   92 */     func_175325_f();
/*      */     
/*   94 */     if (i != 0) {
/*      */       
/*   96 */       this.field_175349_r.func_181156_c(i);
/*   97 */       this.field_175349_r.scrollBy(j);
/*   98 */       func_175328_i();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMouseInput() throws IOException {
/*  107 */     super.handleMouseInput();
/*  108 */     this.field_175349_r.handleMouseInput();
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175325_f() {
/*  113 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry = { new GuiPageButtonList.GuiSlideEntry(160, I18n.format("createWorld.customize.custom.seaLevel", new Object[0]), true, this, 1.0F, 255.0F, this.field_175336_F.seaLevel), new GuiPageButtonList.GuiButtonEntry(148, I18n.format("createWorld.customize.custom.useCaves", new Object[0]), true, this.field_175336_F.useCaves), new GuiPageButtonList.GuiButtonEntry(150, I18n.format("createWorld.customize.custom.useStrongholds", new Object[0]), true, this.field_175336_F.useStrongholds), new GuiPageButtonList.GuiButtonEntry(151, I18n.format("createWorld.customize.custom.useVillages", new Object[0]), true, this.field_175336_F.useVillages), new GuiPageButtonList.GuiButtonEntry(152, I18n.format("createWorld.customize.custom.useMineShafts", new Object[0]), true, this.field_175336_F.useMineShafts), new GuiPageButtonList.GuiButtonEntry(153, I18n.format("createWorld.customize.custom.useTemples", new Object[0]), true, this.field_175336_F.useTemples), new GuiPageButtonList.GuiButtonEntry(210, I18n.format("createWorld.customize.custom.useMonuments", new Object[0]), true, this.field_175336_F.useMonuments), new GuiPageButtonList.GuiButtonEntry(154, I18n.format("createWorld.customize.custom.useRavines", new Object[0]), true, this.field_175336_F.useRavines), new GuiPageButtonList.GuiButtonEntry(149, I18n.format("createWorld.customize.custom.useDungeons", new Object[0]), true, this.field_175336_F.useDungeons), new GuiPageButtonList.GuiSlideEntry(157, I18n.format("createWorld.customize.custom.dungeonChance", new Object[0]), true, this, 1.0F, 100.0F, this.field_175336_F.dungeonChance), new GuiPageButtonList.GuiButtonEntry(155, I18n.format("createWorld.customize.custom.useWaterLakes", new Object[0]), true, this.field_175336_F.useWaterLakes), new GuiPageButtonList.GuiSlideEntry(158, I18n.format("createWorld.customize.custom.waterLakeChance", new Object[0]), true, this, 1.0F, 100.0F, this.field_175336_F.waterLakeChance), new GuiPageButtonList.GuiButtonEntry(156, I18n.format("createWorld.customize.custom.useLavaLakes", new Object[0]), true, this.field_175336_F.useLavaLakes), new GuiPageButtonList.GuiSlideEntry(159, I18n.format("createWorld.customize.custom.lavaLakeChance", new Object[0]), true, this, 10.0F, 100.0F, this.field_175336_F.lavaLakeChance), new GuiPageButtonList.GuiButtonEntry(161, I18n.format("createWorld.customize.custom.useLavaOceans", new Object[0]), true, this.field_175336_F.useLavaOceans), new GuiPageButtonList.GuiSlideEntry(162, I18n.format("createWorld.customize.custom.fixedBiome", new Object[0]), true, this, -1.0F, 37.0F, this.field_175336_F.fixedBiome), new GuiPageButtonList.GuiSlideEntry(163, I18n.format("createWorld.customize.custom.biomeSize", new Object[0]), true, this, 1.0F, 8.0F, this.field_175336_F.biomeSize), new GuiPageButtonList.GuiSlideEntry(164, I18n.format("createWorld.customize.custom.riverSize", new Object[0]), true, this, 1.0F, 5.0F, this.field_175336_F.riverSize) };
/*  114 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry1 = { new GuiPageButtonList.GuiLabelEntry(416, I18n.format("tile.dirt.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(165, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.dirtSize), new GuiPageButtonList.GuiSlideEntry(166, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.dirtCount), new GuiPageButtonList.GuiSlideEntry(167, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dirtMinHeight), new GuiPageButtonList.GuiSlideEntry(168, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dirtMaxHeight), new GuiPageButtonList.GuiLabelEntry(417, I18n.format("tile.gravel.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(169, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.gravelSize), new GuiPageButtonList.GuiSlideEntry(170, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.gravelCount), new GuiPageButtonList.GuiSlideEntry(171, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.gravelMinHeight), new GuiPageButtonList.GuiSlideEntry(172, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.gravelMaxHeight), new GuiPageButtonList.GuiLabelEntry(418, I18n.format("tile.stone.granite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(173, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.graniteSize), new GuiPageButtonList.GuiSlideEntry(174, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.graniteCount), new GuiPageButtonList.GuiSlideEntry(175, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.graniteMinHeight), new GuiPageButtonList.GuiSlideEntry(176, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.graniteMaxHeight), new GuiPageButtonList.GuiLabelEntry(419, I18n.format("tile.stone.diorite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(177, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.dioriteSize), new GuiPageButtonList.GuiSlideEntry(178, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.dioriteCount), new GuiPageButtonList.GuiSlideEntry(179, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dioriteMinHeight), new GuiPageButtonList.GuiSlideEntry(180, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.dioriteMaxHeight), new GuiPageButtonList.GuiLabelEntry(420, I18n.format("tile.stone.andesite.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(181, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.andesiteSize), new GuiPageButtonList.GuiSlideEntry(182, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.andesiteCount), new GuiPageButtonList.GuiSlideEntry(183, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.andesiteMinHeight), new GuiPageButtonList.GuiSlideEntry(184, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.andesiteMaxHeight), new GuiPageButtonList.GuiLabelEntry(421, I18n.format("tile.oreCoal.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(185, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.coalSize), new GuiPageButtonList.GuiSlideEntry(186, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.coalCount), new GuiPageButtonList.GuiSlideEntry(187, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.coalMinHeight), new GuiPageButtonList.GuiSlideEntry(189, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.coalMaxHeight), new GuiPageButtonList.GuiLabelEntry(422, I18n.format("tile.oreIron.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(190, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.ironSize), new GuiPageButtonList.GuiSlideEntry(191, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.ironCount), new GuiPageButtonList.GuiSlideEntry(192, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.ironMinHeight), new GuiPageButtonList.GuiSlideEntry(193, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.ironMaxHeight), new GuiPageButtonList.GuiLabelEntry(423, I18n.format("tile.oreGold.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(194, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.goldSize), new GuiPageButtonList.GuiSlideEntry(195, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.goldCount), new GuiPageButtonList.GuiSlideEntry(196, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.goldMinHeight), new GuiPageButtonList.GuiSlideEntry(197, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.goldMaxHeight), new GuiPageButtonList.GuiLabelEntry(424, I18n.format("tile.oreRedstone.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(198, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.redstoneSize), new GuiPageButtonList.GuiSlideEntry(199, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.redstoneCount), new GuiPageButtonList.GuiSlideEntry(200, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.redstoneMinHeight), new GuiPageButtonList.GuiSlideEntry(201, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.redstoneMaxHeight), new GuiPageButtonList.GuiLabelEntry(425, I18n.format("tile.oreDiamond.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(202, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.diamondSize), new GuiPageButtonList.GuiSlideEntry(203, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.diamondCount), new GuiPageButtonList.GuiSlideEntry(204, I18n.format("createWorld.customize.custom.minHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.diamondMinHeight), new GuiPageButtonList.GuiSlideEntry(205, I18n.format("createWorld.customize.custom.maxHeight", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.diamondMaxHeight), new GuiPageButtonList.GuiLabelEntry(426, I18n.format("tile.oreLapis.name", new Object[0]), false), null, new GuiPageButtonList.GuiSlideEntry(206, I18n.format("createWorld.customize.custom.size", new Object[0]), false, this, 1.0F, 50.0F, this.field_175336_F.lapisSize), new GuiPageButtonList.GuiSlideEntry(207, I18n.format("createWorld.customize.custom.count", new Object[0]), false, this, 0.0F, 40.0F, this.field_175336_F.lapisCount), new GuiPageButtonList.GuiSlideEntry(208, I18n.format("createWorld.customize.custom.center", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.lapisCenterHeight), new GuiPageButtonList.GuiSlideEntry(209, I18n.format("createWorld.customize.custom.spread", new Object[0]), false, this, 0.0F, 255.0F, this.field_175336_F.lapisSpread) };
/*  115 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry2 = { new GuiPageButtonList.GuiSlideEntry(100, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(101, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleY), new GuiPageButtonList.GuiSlideEntry(102, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.mainNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(103, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]), false, this, 1.0F, 2000.0F, this.field_175336_F.depthNoiseScaleX), new GuiPageButtonList.GuiSlideEntry(104, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]), false, this, 1.0F, 2000.0F, this.field_175336_F.depthNoiseScaleZ), new GuiPageButtonList.GuiSlideEntry(105, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]), false, this, 0.01F, 20.0F, this.field_175336_F.depthNoiseScaleExponent), new GuiPageButtonList.GuiSlideEntry(106, I18n.format("createWorld.customize.custom.baseSize", new Object[0]), false, this, 1.0F, 25.0F, this.field_175336_F.baseSize), new GuiPageButtonList.GuiSlideEntry(107, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]), false, this, 1.0F, 6000.0F, this.field_175336_F.coordinateScale), new GuiPageButtonList.GuiSlideEntry(108, I18n.format("createWorld.customize.custom.heightScale", new Object[0]), false, this, 1.0F, 6000.0F, this.field_175336_F.heightScale), new GuiPageButtonList.GuiSlideEntry(109, I18n.format("createWorld.customize.custom.stretchY", new Object[0]), false, this, 0.01F, 50.0F, this.field_175336_F.stretchY), new GuiPageButtonList.GuiSlideEntry(110, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.upperLimitScale), new GuiPageButtonList.GuiSlideEntry(111, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]), false, this, 1.0F, 5000.0F, this.field_175336_F.lowerLimitScale), new GuiPageButtonList.GuiSlideEntry(112, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]), false, this, 1.0F, 20.0F, this.field_175336_F.biomeDepthWeight), new GuiPageButtonList.GuiSlideEntry(113, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]), false, this, 0.0F, 20.0F, this.field_175336_F.biomeDepthOffset), new GuiPageButtonList.GuiSlideEntry(114, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]), false, this, 1.0F, 20.0F, this.field_175336_F.biomeScaleWeight), new GuiPageButtonList.GuiSlideEntry(115, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]), false, this, 0.0F, 20.0F, this.field_175336_F.biomeScaleOffset) };
/*  116 */     GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry3 = { new GuiPageButtonList.GuiLabelEntry(400, I18n.format("createWorld.customize.custom.mainNoiseScaleX", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(132, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.mainNoiseScaleX) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(401, I18n.format("createWorld.customize.custom.mainNoiseScaleY", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(133, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.mainNoiseScaleY) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(402, I18n.format("createWorld.customize.custom.mainNoiseScaleZ", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(134, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.mainNoiseScaleZ) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(403, I18n.format("createWorld.customize.custom.depthNoiseScaleX", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(135, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.depthNoiseScaleX) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(404, I18n.format("createWorld.customize.custom.depthNoiseScaleZ", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(136, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.depthNoiseScaleZ) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(405, I18n.format("createWorld.customize.custom.depthNoiseScaleExponent", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(137, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.depthNoiseScaleExponent) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(406, I18n.format("createWorld.customize.custom.baseSize", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(138, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.baseSize) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(407, I18n.format("createWorld.customize.custom.coordinateScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(139, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.coordinateScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(408, I18n.format("createWorld.customize.custom.heightScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(140, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.heightScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(409, I18n.format("createWorld.customize.custom.stretchY", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(141, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.stretchY) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(410, I18n.format("createWorld.customize.custom.upperLimitScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(142, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.upperLimitScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(411, I18n.format("createWorld.customize.custom.lowerLimitScale", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(143, String.format("%5.3f", new Object[] { Float.valueOf(this.field_175336_F.lowerLimitScale) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(412, I18n.format("createWorld.customize.custom.biomeDepthWeight", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(144, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeDepthWeight) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(413, I18n.format("createWorld.customize.custom.biomeDepthOffset", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(145, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeDepthOffset) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(414, I18n.format("createWorld.customize.custom.biomeScaleWeight", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(146, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeScaleWeight) }), false, this.field_175332_D), new GuiPageButtonList.GuiLabelEntry(415, I18n.format("createWorld.customize.custom.biomeScaleOffset", new Object[0]) + ":", false), new GuiPageButtonList.EditBoxEntry(147, String.format("%2.3f", new Object[] { Float.valueOf(this.field_175336_F.biomeScaleOffset) }), false, this.field_175332_D) };
/*  117 */     this.field_175349_r = new GuiPageButtonList(this.mc, this.width, this.height, 32, this.height - 32, 25, this, new GuiPageButtonList.GuiListEntry[][] { aguipagebuttonlist$guilistentry, aguipagebuttonlist$guilistentry1, aguipagebuttonlist$guilistentry2, aguipagebuttonlist$guilistentry3 });
/*      */     
/*  119 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  121 */       this.field_175342_h[i] = I18n.format("createWorld.customize.custom.page" + i, new Object[0]);
/*      */     }
/*      */     
/*  124 */     func_175328_i();
/*      */   }
/*      */ 
/*      */   
/*      */   public String func_175323_a() {
/*  129 */     return this.field_175336_F.toString().replace("\n", "");
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175324_a(String p_175324_1_) {
/*  134 */     if (p_175324_1_ != null && p_175324_1_.length() != 0) {
/*      */       
/*  136 */       this.field_175336_F = ChunkProviderSettings.Factory.jsonToFactory(p_175324_1_);
/*      */     }
/*      */     else {
/*      */       
/*  140 */       this.field_175336_F = new ChunkProviderSettings.Factory();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175319_a(int p_175319_1_, String p_175319_2_) {
/*  146 */     float f = 0.0F;
/*      */ 
/*      */     
/*      */     try {
/*  150 */       f = Float.parseFloat(p_175319_2_);
/*      */     }
/*  152 */     catch (NumberFormatException numberFormatException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  157 */     float f1 = 0.0F;
/*      */     
/*  159 */     switch (p_175319_1_) {
/*      */       
/*      */       case 132:
/*  162 */         f1 = this.field_175336_F.mainNoiseScaleX = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*      */         break;
/*      */       
/*      */       case 133:
/*  166 */         f1 = this.field_175336_F.mainNoiseScaleY = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*      */         break;
/*      */       
/*      */       case 134:
/*  170 */         f1 = this.field_175336_F.mainNoiseScaleZ = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*      */         break;
/*      */       
/*      */       case 135:
/*  174 */         f1 = this.field_175336_F.depthNoiseScaleX = MathHelper.clamp_float(f, 1.0F, 2000.0F);
/*      */         break;
/*      */       
/*      */       case 136:
/*  178 */         f1 = this.field_175336_F.depthNoiseScaleZ = MathHelper.clamp_float(f, 1.0F, 2000.0F);
/*      */         break;
/*      */       
/*      */       case 137:
/*  182 */         f1 = this.field_175336_F.depthNoiseScaleExponent = MathHelper.clamp_float(f, 0.01F, 20.0F);
/*      */         break;
/*      */       
/*      */       case 138:
/*  186 */         f1 = this.field_175336_F.baseSize = MathHelper.clamp_float(f, 1.0F, 25.0F);
/*      */         break;
/*      */       
/*      */       case 139:
/*  190 */         f1 = this.field_175336_F.coordinateScale = MathHelper.clamp_float(f, 1.0F, 6000.0F);
/*      */         break;
/*      */       
/*      */       case 140:
/*  194 */         f1 = this.field_175336_F.heightScale = MathHelper.clamp_float(f, 1.0F, 6000.0F);
/*      */         break;
/*      */       
/*      */       case 141:
/*  198 */         f1 = this.field_175336_F.stretchY = MathHelper.clamp_float(f, 0.01F, 50.0F);
/*      */         break;
/*      */       
/*      */       case 142:
/*  202 */         f1 = this.field_175336_F.upperLimitScale = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*      */         break;
/*      */       
/*      */       case 143:
/*  206 */         f1 = this.field_175336_F.lowerLimitScale = MathHelper.clamp_float(f, 1.0F, 5000.0F);
/*      */         break;
/*      */       
/*      */       case 144:
/*  210 */         f1 = this.field_175336_F.biomeDepthWeight = MathHelper.clamp_float(f, 1.0F, 20.0F);
/*      */         break;
/*      */       
/*      */       case 145:
/*  214 */         f1 = this.field_175336_F.biomeDepthOffset = MathHelper.clamp_float(f, 0.0F, 20.0F);
/*      */         break;
/*      */       
/*      */       case 146:
/*  218 */         f1 = this.field_175336_F.biomeScaleWeight = MathHelper.clamp_float(f, 1.0F, 20.0F);
/*      */         break;
/*      */       
/*      */       case 147:
/*  222 */         f1 = this.field_175336_F.biomeScaleOffset = MathHelper.clamp_float(f, 0.0F, 20.0F);
/*      */         break;
/*      */     } 
/*  225 */     if (f1 != f && f != 0.0F)
/*      */     {
/*  227 */       ((GuiTextField)this.field_175349_r.func_178061_c(p_175319_1_)).setText(func_175330_b(p_175319_1_, f1));
/*      */     }
/*      */     
/*  230 */     ((GuiSlider)this.field_175349_r.func_178061_c(p_175319_1_ - 132 + 100)).func_175218_a(f1, false);
/*      */     
/*  232 */     if (!this.field_175336_F.equals(this.field_175334_E))
/*      */     {
/*  234 */       func_181031_a(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_181031_a(boolean p_181031_1_) {
/*  240 */     this.field_175338_A = p_181031_1_;
/*  241 */     this.field_175346_u.enabled = p_181031_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getText(int id, String name, float value) {
/*  246 */     return name + ": " + func_175330_b(id, value);
/*      */   }
/*      */ 
/*      */   
/*      */   private String func_175330_b(int p_175330_1_, float p_175330_2_) {
/*  251 */     switch (p_175330_1_) {
/*      */       
/*      */       case 100:
/*      */       case 101:
/*      */       case 102:
/*      */       case 103:
/*      */       case 104:
/*      */       case 107:
/*      */       case 108:
/*      */       case 110:
/*      */       case 111:
/*      */       case 132:
/*      */       case 133:
/*      */       case 134:
/*      */       case 135:
/*      */       case 136:
/*      */       case 139:
/*      */       case 140:
/*      */       case 142:
/*      */       case 143:
/*  271 */         return String.format("%5.3f", new Object[] { Float.valueOf(p_175330_2_) });
/*      */       
/*      */       case 105:
/*      */       case 106:
/*      */       case 109:
/*      */       case 112:
/*      */       case 113:
/*      */       case 114:
/*      */       case 115:
/*      */       case 137:
/*      */       case 138:
/*      */       case 141:
/*      */       case 144:
/*      */       case 145:
/*      */       case 146:
/*      */       case 147:
/*  287 */         return String.format("%2.3f", new Object[] { Float.valueOf(p_175330_2_) });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       default:
/*  320 */         return String.format("%d", new Object[] { Integer.valueOf((int)p_175330_2_) });
/*      */       case 162:
/*      */         break;
/*  323 */     }  if (p_175330_2_ < 0.0F)
/*      */     {
/*  325 */       return I18n.format("gui.all", new Object[0]);
/*      */     }
/*  327 */     if ((int)p_175330_2_ >= BiomeGenBase.hell.biomeID) {
/*      */       
/*  329 */       BiomeGenBase biomegenbase1 = BiomeGenBase.getBiomeGenArray()[(int)p_175330_2_ + 2];
/*  330 */       return (biomegenbase1 != null) ? biomegenbase1.biomeName : "?";
/*      */     } 
/*      */ 
/*      */     
/*  334 */     BiomeGenBase biomegenbase = BiomeGenBase.getBiomeGenArray()[(int)p_175330_2_];
/*  335 */     return (biomegenbase != null) ? biomegenbase.biomeName : "?";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_175321_a(int p_175321_1_, boolean p_175321_2_) {
/*  342 */     switch (p_175321_1_) {
/*      */       
/*      */       case 148:
/*  345 */         this.field_175336_F.useCaves = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 149:
/*  349 */         this.field_175336_F.useDungeons = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 150:
/*  353 */         this.field_175336_F.useStrongholds = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 151:
/*  357 */         this.field_175336_F.useVillages = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 152:
/*  361 */         this.field_175336_F.useMineShafts = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 153:
/*  365 */         this.field_175336_F.useTemples = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 154:
/*  369 */         this.field_175336_F.useRavines = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 155:
/*  373 */         this.field_175336_F.useWaterLakes = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 156:
/*  377 */         this.field_175336_F.useLavaLakes = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 161:
/*  381 */         this.field_175336_F.useLavaOceans = p_175321_2_;
/*      */         break;
/*      */       
/*      */       case 210:
/*  385 */         this.field_175336_F.useMonuments = p_175321_2_;
/*      */         break;
/*      */     } 
/*  388 */     if (!this.field_175336_F.equals(this.field_175334_E))
/*      */     {
/*  390 */       func_181031_a(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTick(int id, float value) {
/*  396 */     switch (id) {
/*      */       
/*      */       case 100:
/*  399 */         this.field_175336_F.mainNoiseScaleX = value;
/*      */         break;
/*      */       
/*      */       case 101:
/*  403 */         this.field_175336_F.mainNoiseScaleY = value;
/*      */         break;
/*      */       
/*      */       case 102:
/*  407 */         this.field_175336_F.mainNoiseScaleZ = value;
/*      */         break;
/*      */       
/*      */       case 103:
/*  411 */         this.field_175336_F.depthNoiseScaleX = value;
/*      */         break;
/*      */       
/*      */       case 104:
/*  415 */         this.field_175336_F.depthNoiseScaleZ = value;
/*      */         break;
/*      */       
/*      */       case 105:
/*  419 */         this.field_175336_F.depthNoiseScaleExponent = value;
/*      */         break;
/*      */       
/*      */       case 106:
/*  423 */         this.field_175336_F.baseSize = value;
/*      */         break;
/*      */       
/*      */       case 107:
/*  427 */         this.field_175336_F.coordinateScale = value;
/*      */         break;
/*      */       
/*      */       case 108:
/*  431 */         this.field_175336_F.heightScale = value;
/*      */         break;
/*      */       
/*      */       case 109:
/*  435 */         this.field_175336_F.stretchY = value;
/*      */         break;
/*      */       
/*      */       case 110:
/*  439 */         this.field_175336_F.upperLimitScale = value;
/*      */         break;
/*      */       
/*      */       case 111:
/*  443 */         this.field_175336_F.lowerLimitScale = value;
/*      */         break;
/*      */       
/*      */       case 112:
/*  447 */         this.field_175336_F.biomeDepthWeight = value;
/*      */         break;
/*      */       
/*      */       case 113:
/*  451 */         this.field_175336_F.biomeDepthOffset = value;
/*      */         break;
/*      */       
/*      */       case 114:
/*  455 */         this.field_175336_F.biomeScaleWeight = value;
/*      */         break;
/*      */       
/*      */       case 115:
/*  459 */         this.field_175336_F.biomeScaleOffset = value;
/*      */         break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 157:
/*  508 */         this.field_175336_F.dungeonChance = (int)value;
/*      */         break;
/*      */       
/*      */       case 158:
/*  512 */         this.field_175336_F.waterLakeChance = (int)value;
/*      */         break;
/*      */       
/*      */       case 159:
/*  516 */         this.field_175336_F.lavaLakeChance = (int)value;
/*      */         break;
/*      */       
/*      */       case 160:
/*  520 */         this.field_175336_F.seaLevel = (int)value;
/*      */         break;
/*      */       
/*      */       case 162:
/*  524 */         this.field_175336_F.fixedBiome = (int)value;
/*      */         break;
/*      */       
/*      */       case 163:
/*  528 */         this.field_175336_F.biomeSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 164:
/*  532 */         this.field_175336_F.riverSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 165:
/*  536 */         this.field_175336_F.dirtSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 166:
/*  540 */         this.field_175336_F.dirtCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 167:
/*  544 */         this.field_175336_F.dirtMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 168:
/*  548 */         this.field_175336_F.dirtMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 169:
/*  552 */         this.field_175336_F.gravelSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 170:
/*  556 */         this.field_175336_F.gravelCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 171:
/*  560 */         this.field_175336_F.gravelMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 172:
/*  564 */         this.field_175336_F.gravelMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 173:
/*  568 */         this.field_175336_F.graniteSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 174:
/*  572 */         this.field_175336_F.graniteCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 175:
/*  576 */         this.field_175336_F.graniteMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 176:
/*  580 */         this.field_175336_F.graniteMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 177:
/*  584 */         this.field_175336_F.dioriteSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 178:
/*  588 */         this.field_175336_F.dioriteCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 179:
/*  592 */         this.field_175336_F.dioriteMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 180:
/*  596 */         this.field_175336_F.dioriteMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 181:
/*  600 */         this.field_175336_F.andesiteSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 182:
/*  604 */         this.field_175336_F.andesiteCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 183:
/*  608 */         this.field_175336_F.andesiteMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 184:
/*  612 */         this.field_175336_F.andesiteMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 185:
/*  616 */         this.field_175336_F.coalSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 186:
/*  620 */         this.field_175336_F.coalCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 187:
/*  624 */         this.field_175336_F.coalMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 189:
/*  628 */         this.field_175336_F.coalMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 190:
/*  632 */         this.field_175336_F.ironSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 191:
/*  636 */         this.field_175336_F.ironCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 192:
/*  640 */         this.field_175336_F.ironMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 193:
/*  644 */         this.field_175336_F.ironMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 194:
/*  648 */         this.field_175336_F.goldSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 195:
/*  652 */         this.field_175336_F.goldCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 196:
/*  656 */         this.field_175336_F.goldMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 197:
/*  660 */         this.field_175336_F.goldMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 198:
/*  664 */         this.field_175336_F.redstoneSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 199:
/*  668 */         this.field_175336_F.redstoneCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 200:
/*  672 */         this.field_175336_F.redstoneMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 201:
/*  676 */         this.field_175336_F.redstoneMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 202:
/*  680 */         this.field_175336_F.diamondSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 203:
/*  684 */         this.field_175336_F.diamondCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 204:
/*  688 */         this.field_175336_F.diamondMinHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 205:
/*  692 */         this.field_175336_F.diamondMaxHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 206:
/*  696 */         this.field_175336_F.lapisSize = (int)value;
/*      */         break;
/*      */       
/*      */       case 207:
/*  700 */         this.field_175336_F.lapisCount = (int)value;
/*      */         break;
/*      */       
/*      */       case 208:
/*  704 */         this.field_175336_F.lapisCenterHeight = (int)value;
/*      */         break;
/*      */       
/*      */       case 209:
/*  708 */         this.field_175336_F.lapisSpread = (int)value;
/*      */         break;
/*      */     } 
/*  711 */     if (id >= 100 && id < 116) {
/*      */       
/*  713 */       Gui gui = this.field_175349_r.func_178061_c(id - 100 + 132);
/*      */       
/*  715 */       if (gui != null)
/*      */       {
/*  717 */         ((GuiTextField)gui).setText(func_175330_b(id, value));
/*      */       }
/*      */     } 
/*      */     
/*  721 */     if (!this.field_175336_F.equals(this.field_175334_E))
/*      */     {
/*  723 */       func_181031_a(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void actionPerformed(GuiButton button) throws IOException {
/*  732 */     if (button.enabled) {
/*      */       int i;
/*  734 */       switch (button.id) {
/*      */         
/*      */         case 300:
/*  737 */           this.field_175343_i.chunkProviderSettingsJson = this.field_175336_F.toString();
/*  738 */           this.mc.displayGuiScreen(this.field_175343_i);
/*      */           break;
/*      */         
/*      */         case 301:
/*  742 */           for (i = 0; i < this.field_175349_r.getSize(); i++) {
/*      */             
/*  744 */             GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = this.field_175349_r.getListEntry(i);
/*  745 */             Gui gui = guipagebuttonlist$guientry.func_178022_a();
/*      */             
/*  747 */             if (gui instanceof GuiButton) {
/*      */               
/*  749 */               GuiButton guibutton = (GuiButton)gui;
/*      */               
/*  751 */               if (guibutton instanceof GuiSlider) {
/*      */                 
/*  753 */                 float f = ((GuiSlider)guibutton).func_175217_d() * (0.75F + this.random.nextFloat() * 0.5F) + this.random.nextFloat() * 0.1F - 0.05F;
/*  754 */                 ((GuiSlider)guibutton).func_175219_a(MathHelper.clamp_float(f, 0.0F, 1.0F));
/*      */               }
/*  756 */               else if (guibutton instanceof GuiListButton) {
/*      */                 
/*  758 */                 ((GuiListButton)guibutton).func_175212_b(this.random.nextBoolean());
/*      */               } 
/*      */             } 
/*      */             
/*  762 */             Gui gui1 = guipagebuttonlist$guientry.func_178021_b();
/*      */             
/*  764 */             if (gui1 instanceof GuiButton) {
/*      */               
/*  766 */               GuiButton guibutton1 = (GuiButton)gui1;
/*      */               
/*  768 */               if (guibutton1 instanceof GuiSlider) {
/*      */                 
/*  770 */                 float f1 = ((GuiSlider)guibutton1).func_175217_d() * (0.75F + this.random.nextFloat() * 0.5F) + this.random.nextFloat() * 0.1F - 0.05F;
/*  771 */                 ((GuiSlider)guibutton1).func_175219_a(MathHelper.clamp_float(f1, 0.0F, 1.0F));
/*      */               }
/*  773 */               else if (guibutton1 instanceof GuiListButton) {
/*      */                 
/*  775 */                 ((GuiListButton)guibutton1).func_175212_b(this.random.nextBoolean());
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           return;
/*      */ 
/*      */         
/*      */         case 302:
/*  783 */           this.field_175349_r.func_178071_h();
/*  784 */           func_175328_i();
/*      */           break;
/*      */         
/*      */         case 303:
/*  788 */           this.field_175349_r.func_178064_i();
/*  789 */           func_175328_i();
/*      */           break;
/*      */         
/*      */         case 304:
/*  793 */           if (this.field_175338_A)
/*      */           {
/*  795 */             func_175322_b(304);
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case 305:
/*  801 */           this.mc.displayGuiScreen(new GuiScreenCustomizePresets(this));
/*      */           break;
/*      */         
/*      */         case 306:
/*  805 */           func_175331_h();
/*      */           break;
/*      */         
/*      */         case 307:
/*  809 */           this.field_175339_B = 0;
/*  810 */           func_175331_h();
/*      */           break;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void func_175326_g() {
/*  817 */     this.field_175336_F.func_177863_a();
/*  818 */     func_175325_f();
/*  819 */     func_181031_a(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175322_b(int p_175322_1_) {
/*  824 */     this.field_175339_B = p_175322_1_;
/*  825 */     func_175329_a(true);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175331_h() throws IOException {
/*  830 */     switch (this.field_175339_B) {
/*      */       
/*      */       case 300:
/*  833 */         actionPerformed((GuiListButton)this.field_175349_r.func_178061_c(300));
/*      */         break;
/*      */       
/*      */       case 304:
/*  837 */         func_175326_g();
/*      */         break;
/*      */     } 
/*  840 */     this.field_175339_B = 0;
/*  841 */     this.field_175340_C = true;
/*  842 */     func_175329_a(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175329_a(boolean p_175329_1_) {
/*  847 */     this.field_175352_x.visible = p_175329_1_;
/*  848 */     this.field_175351_y.visible = p_175329_1_;
/*  849 */     this.field_175347_t.enabled = !p_175329_1_;
/*  850 */     this.field_175348_s.enabled = !p_175329_1_;
/*  851 */     this.field_175345_v.enabled = !p_175329_1_;
/*  852 */     this.field_175344_w.enabled = !p_175329_1_;
/*  853 */     this.field_175346_u.enabled = (this.field_175338_A && !p_175329_1_);
/*  854 */     this.field_175350_z.enabled = !p_175329_1_;
/*  855 */     this.field_175349_r.func_181155_a(!p_175329_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_175328_i() {
/*  860 */     this.field_175345_v.enabled = (this.field_175349_r.func_178059_e() != 0);
/*  861 */     this.field_175344_w.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
/*  862 */     this.field_175333_f = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.field_175349_r.func_178059_e() + 1), Integer.valueOf(this.field_175349_r.func_178057_f()) });
/*  863 */     this.field_175335_g = this.field_175342_h[this.field_175349_r.func_178059_e()];
/*  864 */     this.field_175347_t.enabled = (this.field_175349_r.func_178059_e() != this.field_175349_r.func_178057_f() - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  873 */     super.keyTyped(typedChar, keyCode);
/*      */     
/*  875 */     if (this.field_175339_B == 0) {
/*      */       
/*  877 */       switch (keyCode) {
/*      */         
/*      */         case 200:
/*  880 */           func_175327_a(1.0F);
/*      */           return;
/*      */         
/*      */         case 208:
/*  884 */           func_175327_a(-1.0F);
/*      */           return;
/*      */       } 
/*      */       
/*  888 */       this.field_175349_r.func_178062_a(typedChar, keyCode);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void func_175327_a(float p_175327_1_) {
/*  895 */     Gui gui = this.field_175349_r.func_178056_g();
/*      */     
/*  897 */     if (gui instanceof GuiTextField) {
/*      */       
/*  899 */       float f = p_175327_1_;
/*      */       
/*  901 */       if (GuiScreen.isShiftKeyDown()) {
/*      */         
/*  903 */         f = p_175327_1_ * 0.1F;
/*      */         
/*  905 */         if (GuiScreen.isCtrlKeyDown())
/*      */         {
/*  907 */           f *= 0.1F;
/*      */         }
/*      */       }
/*  910 */       else if (GuiScreen.isCtrlKeyDown()) {
/*      */         
/*  912 */         f = p_175327_1_ * 10.0F;
/*      */         
/*  914 */         if (GuiScreen.isAltKeyDown())
/*      */         {
/*  916 */           f *= 10.0F;
/*      */         }
/*      */       } 
/*      */       
/*  920 */       GuiTextField guitextfield = (GuiTextField)gui;
/*  921 */       Float f1 = Floats.tryParse(guitextfield.getText());
/*      */       
/*  923 */       if (f1 != null) {
/*      */         
/*  925 */         f1 = Float.valueOf(f1.floatValue() + f);
/*  926 */         int i = guitextfield.getId();
/*  927 */         String s = func_175330_b(guitextfield.getId(), f1.floatValue());
/*  928 */         guitextfield.setText(s);
/*  929 */         func_175319_a(i, s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/*  939 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*      */     
/*  941 */     if (this.field_175339_B == 0 && !this.field_175340_C)
/*      */     {
/*  943 */       this.field_175349_r.mouseClicked(mouseX, mouseY, mouseButton);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/*  952 */     super.mouseReleased(mouseX, mouseY, state);
/*      */     
/*  954 */     if (this.field_175340_C) {
/*      */       
/*  956 */       this.field_175340_C = false;
/*      */     }
/*  958 */     else if (this.field_175339_B == 0) {
/*      */       
/*  960 */       this.field_175349_r.mouseReleased(mouseX, mouseY, state);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  969 */     drawDefaultBackground();
/*  970 */     this.field_175349_r.drawScreen(mouseX, mouseY, partialTicks);
/*  971 */     drawCenteredString(this.fontRendererObj, this.field_175341_a, this.width / 2, 2, 16777215);
/*  972 */     drawCenteredString(this.fontRendererObj, this.field_175333_f, this.width / 2, 12, 16777215);
/*  973 */     drawCenteredString(this.fontRendererObj, this.field_175335_g, this.width / 2, 22, 16777215);
/*  974 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*      */     
/*  976 */     if (this.field_175339_B != 0) {
/*      */       
/*  978 */       drawRect(0, 0, this.width, this.height, -2147483648);
/*  979 */       drawHorizontalLine(this.width / 2 - 91, this.width / 2 + 90, 99, -2039584);
/*  980 */       drawHorizontalLine(this.width / 2 - 91, this.width / 2 + 90, 185, -6250336);
/*  981 */       drawVerticalLine(this.width / 2 - 91, 99, 185, -2039584);
/*  982 */       drawVerticalLine(this.width / 2 + 90, 99, 185, -6250336);
/*  983 */       float f = 85.0F;
/*  984 */       float f1 = 180.0F;
/*  985 */       GlStateManager.disableLighting();
/*  986 */       GlStateManager.disableFog();
/*  987 */       Tessellator tessellator = Tessellator.getInstance();
/*  988 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  989 */       this.mc.getTextureManager().bindTexture(optionsBackground);
/*  990 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  991 */       float f2 = 32.0F;
/*  992 */       worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/*  993 */       worldrenderer.pos((this.width / 2 - 90), 185.0D, 0.0D).tex(0.0D, 2.65625D).func_181669_b(64, 64, 64, 64).endVertex();
/*  994 */       worldrenderer.pos((this.width / 2 + 90), 185.0D, 0.0D).tex(5.625D, 2.65625D).func_181669_b(64, 64, 64, 64).endVertex();
/*  995 */       worldrenderer.pos((this.width / 2 + 90), 100.0D, 0.0D).tex(5.625D, 0.0D).func_181669_b(64, 64, 64, 64).endVertex();
/*  996 */       worldrenderer.pos((this.width / 2 - 90), 100.0D, 0.0D).tex(0.0D, 0.0D).func_181669_b(64, 64, 64, 64).endVertex();
/*  997 */       tessellator.draw();
/*  998 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirmTitle", new Object[0]), this.width / 2, 105, 16777215);
/*  999 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm1", new Object[0]), this.width / 2, 125, 16777215);
/* 1000 */       drawCenteredString(this.fontRendererObj, I18n.format("createWorld.customize.custom.confirm2", new Object[0]), this.width / 2, 135, 16777215);
/* 1001 */       this.field_175352_x.drawButton(this.mc, mouseX, mouseY);
/* 1002 */       this.field_175351_y.drawButton(this.mc, mouseX, mouseY);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiCustomizeWorldScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */