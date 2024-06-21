/*    */ package com.viaversion.viaversion.compatibility.unsafe;
/*    */ 
/*    */ import com.viaversion.viaversion.compatibility.YamlCompat;
/*    */ import org.yaml.snakeyaml.DumperOptions;
/*    */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*    */ import org.yaml.snakeyaml.nodes.NodeId;
/*    */ import org.yaml.snakeyaml.nodes.Tag;
/*    */ import org.yaml.snakeyaml.representer.Representer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Yaml1Compat
/*    */   implements YamlCompat
/*    */ {
/*    */   public Representer createRepresenter(DumperOptions dumperOptions) {
/* 31 */     return new Representer();
/*    */   }
/*    */ 
/*    */   
/*    */   public SafeConstructor createSafeConstructor() {
/* 36 */     return new CustomSafeConstructor();
/*    */   }
/*    */   
/*    */   private static final class CustomSafeConstructor
/*    */     extends SafeConstructor {
/*    */     public CustomSafeConstructor() {
/* 42 */       this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap(this));
/* 43 */       this.yamlConstructors.put(Tag.OMAP, new SafeConstructor.ConstructYamlOmap(this));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\compatibilit\\unsafe\Yaml1Compat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */