/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*    */ 
/*    */ public class SVertexFormat
/*    */ {
/*    */   public static final int vertexSizeBlock = 14;
/*    */   public static final int offsetMidTexCoord = 8;
/*    */   public static final int offsetTangent = 10;
/*    */   public static final int offsetEntity = 12;
/* 12 */   public static final VertexFormat defVertexFormatTextured = makeDefVertexFormatTextured();
/*    */ 
/*    */   
/*    */   public static VertexFormat makeDefVertexFormatBlock() {
/* 16 */     VertexFormat vertexformat = new VertexFormat();
/* 17 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/* 18 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4));
/* 19 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2));
/* 20 */     vertexformat.func_181721_a(new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2));
/* 21 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3));
/* 22 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1));
/* 23 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 24 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 25 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 26 */     return vertexformat;
/*    */   }
/*    */ 
/*    */   
/*    */   public static VertexFormat makeDefVertexFormatItem() {
/* 31 */     VertexFormat vertexformat = new VertexFormat();
/* 32 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/* 33 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4));
/* 34 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2));
/* 35 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 36 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3));
/* 37 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1));
/* 38 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 39 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 40 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 41 */     return vertexformat;
/*    */   }
/*    */ 
/*    */   
/*    */   public static VertexFormat makeDefVertexFormatTextured() {
/* 46 */     VertexFormat vertexformat = new VertexFormat();
/* 47 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/* 48 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.PADDING, 4));
/* 49 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2));
/* 50 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 51 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3));
/* 52 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1));
/* 53 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 54 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 55 */     vertexformat.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 56 */     return vertexformat;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void setDefBakedFormat(VertexFormat vf) {
/* 61 */     if (vf != null) {
/*    */       
/* 63 */       vf.clear();
/* 64 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/* 65 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4));
/* 66 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2));
/* 67 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 68 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3));
/* 69 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1));
/* 70 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.PADDING, 2));
/* 71 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/* 72 */       vf.func_181721_a(new VertexFormatElement(0, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.PADDING, 4));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static VertexFormat duplicate(VertexFormat src) {
/* 78 */     if (src == null)
/*    */     {
/* 80 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 84 */     VertexFormat vertexformat = new VertexFormat();
/* 85 */     copy(src, vertexformat);
/* 86 */     return vertexformat;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copy(VertexFormat src, VertexFormat dst) {
/* 92 */     if (src != null && dst != null) {
/*    */       
/* 94 */       dst.clear();
/*    */       
/* 96 */       for (int i = 0; i < src.getElementCount(); i++)
/*    */       {
/* 98 */         dst.func_181721_a(src.getElement(i));
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\SVertexFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */