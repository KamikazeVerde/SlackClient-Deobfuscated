/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.reflect.ReflectorClass;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ import net.optifine.shaders.SVertexFormat;
/*     */ 
/*     */ public class DefaultVertexFormats
/*     */ {
/*  11 */   public static VertexFormat BLOCK = new VertexFormat();
/*  12 */   public static VertexFormat ITEM = new VertexFormat();
/*  13 */   private static final VertexFormat BLOCK_VANILLA = BLOCK;
/*  14 */   private static final VertexFormat ITEM_VANILLA = ITEM;
/*  15 */   public static ReflectorClass Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
/*  16 */   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
/*  17 */   private static final VertexFormat FORGE_BAKED = SVertexFormat.duplicate((VertexFormat)getFieldValue(Attributes_DEFAULT_BAKED_FORMAT));
/*  18 */   public static final VertexFormat field_181703_c = new VertexFormat();
/*  19 */   public static final VertexFormat field_181704_d = new VertexFormat();
/*  20 */   public static final VertexFormat field_181705_e = new VertexFormat();
/*  21 */   public static final VertexFormat field_181706_f = new VertexFormat();
/*  22 */   public static final VertexFormat POSITION_TEX = new VertexFormat();
/*  23 */   public static final VertexFormat field_181708_h = new VertexFormat();
/*  24 */   public static final VertexFormat field_181709_i = new VertexFormat();
/*  25 */   public static final VertexFormat field_181710_j = new VertexFormat();
/*  26 */   public static final VertexFormat field_181711_k = new VertexFormat();
/*  27 */   public static final VertexFormat field_181712_l = new VertexFormat();
/*  28 */   public static final VertexFormatElement field_181713_m = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3);
/*  29 */   public static final VertexFormatElement field_181714_n = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4);
/*  30 */   public static final VertexFormatElement field_181715_o = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
/*  31 */   public static final VertexFormatElement field_181716_p = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
/*  32 */   public static final VertexFormatElement field_181717_q = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3);
/*  33 */   public static final VertexFormatElement field_181718_r = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1);
/*     */ 
/*     */   
/*     */   public static void updateVertexFormats() {
/*  37 */     if (Config.isShaders()) {
/*     */       
/*  39 */       BLOCK = SVertexFormat.makeDefVertexFormatBlock();
/*  40 */       ITEM = SVertexFormat.makeDefVertexFormatItem();
/*     */       
/*  42 */       if (Attributes_DEFAULT_BAKED_FORMAT.exists())
/*     */       {
/*  44 */         SVertexFormat.setDefBakedFormat((VertexFormat)Attributes_DEFAULT_BAKED_FORMAT.getValue());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  49 */       BLOCK = BLOCK_VANILLA;
/*  50 */       ITEM = ITEM_VANILLA;
/*     */       
/*  52 */       if (Attributes_DEFAULT_BAKED_FORMAT.exists())
/*     */       {
/*  54 */         SVertexFormat.copy(FORGE_BAKED, (VertexFormat)Attributes_DEFAULT_BAKED_FORMAT.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(ReflectorField p_getFieldValue_0_) {
/*     */     try {
/*  63 */       Field field = p_getFieldValue_0_.getTargetField();
/*     */       
/*  65 */       if (field == null)
/*     */       {
/*  67 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  71 */       Object object = field.get(null);
/*  72 */       return object;
/*     */     
/*     */     }
/*  75 */     catch (Throwable throwable) {
/*     */       
/*  77 */       throwable.printStackTrace();
/*  78 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  84 */     BLOCK.func_181721_a(field_181713_m);
/*  85 */     BLOCK.func_181721_a(field_181714_n);
/*  86 */     BLOCK.func_181721_a(field_181715_o);
/*  87 */     BLOCK.func_181721_a(field_181716_p);
/*  88 */     ITEM.func_181721_a(field_181713_m);
/*  89 */     ITEM.func_181721_a(field_181714_n);
/*  90 */     ITEM.func_181721_a(field_181715_o);
/*  91 */     ITEM.func_181721_a(field_181717_q);
/*  92 */     ITEM.func_181721_a(field_181718_r);
/*  93 */     field_181703_c.func_181721_a(field_181713_m);
/*  94 */     field_181703_c.func_181721_a(field_181715_o);
/*  95 */     field_181703_c.func_181721_a(field_181717_q);
/*  96 */     field_181703_c.func_181721_a(field_181718_r);
/*  97 */     field_181704_d.func_181721_a(field_181713_m);
/*  98 */     field_181704_d.func_181721_a(field_181715_o);
/*  99 */     field_181704_d.func_181721_a(field_181714_n);
/* 100 */     field_181704_d.func_181721_a(field_181716_p);
/* 101 */     field_181705_e.func_181721_a(field_181713_m);
/* 102 */     field_181706_f.func_181721_a(field_181713_m);
/* 103 */     field_181706_f.func_181721_a(field_181714_n);
/* 104 */     POSITION_TEX.func_181721_a(field_181713_m);
/* 105 */     POSITION_TEX.func_181721_a(field_181715_o);
/* 106 */     field_181708_h.func_181721_a(field_181713_m);
/* 107 */     field_181708_h.func_181721_a(field_181717_q);
/* 108 */     field_181708_h.func_181721_a(field_181718_r);
/* 109 */     field_181709_i.func_181721_a(field_181713_m);
/* 110 */     field_181709_i.func_181721_a(field_181715_o);
/* 111 */     field_181709_i.func_181721_a(field_181714_n);
/* 112 */     field_181710_j.func_181721_a(field_181713_m);
/* 113 */     field_181710_j.func_181721_a(field_181715_o);
/* 114 */     field_181710_j.func_181721_a(field_181717_q);
/* 115 */     field_181710_j.func_181721_a(field_181718_r);
/* 116 */     field_181711_k.func_181721_a(field_181713_m);
/* 117 */     field_181711_k.func_181721_a(field_181715_o);
/* 118 */     field_181711_k.func_181721_a(field_181716_p);
/* 119 */     field_181711_k.func_181721_a(field_181714_n);
/* 120 */     field_181712_l.func_181721_a(field_181713_m);
/* 121 */     field_181712_l.func_181721_a(field_181715_o);
/* 122 */     field_181712_l.func_181721_a(field_181714_n);
/* 123 */     field_181712_l.func_181721_a(field_181717_q);
/* 124 */     field_181712_l.func_181721_a(field_181718_r);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\vertex\DefaultVertexFormats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */