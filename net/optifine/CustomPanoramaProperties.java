/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.Properties;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.config.ConnectedParser;
/*    */ 
/*    */ public class CustomPanoramaProperties
/*    */ {
/*    */   private String path;
/*    */   private ResourceLocation[] panoramaLocations;
/* 11 */   private int weight = 1;
/* 12 */   private int blur1 = 64;
/* 13 */   private int blur2 = 3;
/* 14 */   private int blur3 = 3;
/* 15 */   private int overlay1Top = -2130706433;
/* 16 */   private int overlay1Bottom = 16777215;
/* 17 */   private int overlay2Top = 0;
/* 18 */   private int overlay2Bottom = Integer.MIN_VALUE;
/*    */ 
/*    */   
/*    */   public CustomPanoramaProperties(String path, Properties props) {
/* 22 */     ConnectedParser connectedparser = new ConnectedParser("CustomPanorama");
/* 23 */     this.path = path;
/* 24 */     this.panoramaLocations = new ResourceLocation[6];
/*    */     
/* 26 */     for (int i = 0; i < this.panoramaLocations.length; i++)
/*    */     {
/* 28 */       this.panoramaLocations[i] = new ResourceLocation(path + "/panorama_" + i + ".png");
/*    */     }
/*    */     
/* 31 */     this.weight = connectedparser.parseInt(props.getProperty("weight"), 1);
/* 32 */     this.blur1 = connectedparser.parseInt(props.getProperty("blur1"), 64);
/* 33 */     this.blur2 = connectedparser.parseInt(props.getProperty("blur2"), 3);
/* 34 */     this.blur3 = connectedparser.parseInt(props.getProperty("blur3"), 3);
/* 35 */     this.overlay1Top = ConnectedParser.parseColor4(props.getProperty("overlay1.top"), -2130706433);
/* 36 */     this.overlay1Bottom = ConnectedParser.parseColor4(props.getProperty("overlay1.bottom"), 16777215);
/* 37 */     this.overlay2Top = ConnectedParser.parseColor4(props.getProperty("overlay2.top"), 0);
/* 38 */     this.overlay2Bottom = ConnectedParser.parseColor4(props.getProperty("overlay2.bottom"), -2147483648);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation[] getPanoramaLocations() {
/* 43 */     return this.panoramaLocations;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeight() {
/* 48 */     return this.weight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur1() {
/* 53 */     return this.blur1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur2() {
/* 58 */     return this.blur2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlur3() {
/* 63 */     return this.blur3;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay1Top() {
/* 68 */     return this.overlay1Top;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay1Bottom() {
/* 73 */     return this.overlay1Bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay2Top() {
/* 78 */     return this.overlay2Top;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOverlay2Bottom() {
/* 83 */     return this.overlay2Bottom;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 88 */     return "" + this.path + ", weight: " + this.weight + ", blur: " + this.blur1 + " " + this.blur2 + " " + this.blur3 + ", overlay: " + this.overlay1Top + " " + this.overlay1Bottom + " " + this.overlay2Top + " " + this.overlay2Bottom;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomPanoramaProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */