/*    */ package com.viaversion.viaversion.compatibility.unsafe;
/*    */ 
/*    */ import com.viaversion.viaversion.compatibility.YamlCompat;
/*    */ import org.yaml.snakeyaml.DumperOptions;
/*    */ import org.yaml.snakeyaml.LoaderOptions;
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
/*    */ public final class Yaml2Compat
/*    */   implements YamlCompat
/*    */ {
/*    */   public Representer createRepresenter(DumperOptions dumperOptions) {
/* 32 */     return new Representer(dumperOptions);
/*    */   }
/*    */ 
/*    */   
/*    */   public SafeConstructor createSafeConstructor() {
/* 37 */     return new CustomSafeConstructor();
/*    */   }
/*    */   
/*    */   private static final class CustomSafeConstructor
/*    */     extends SafeConstructor {
/*    */     public CustomSafeConstructor() {
/* 43 */       super(new LoaderOptions());
/* 44 */       this.yamlClassConstructors.put(NodeId.mapping, new SafeConstructor.ConstructYamlMap(this));
/* 45 */       this.yamlConstructors.put(Tag.OMAP, new SafeConstructor.ConstructYamlOmap(this));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\compatibilit\\unsafe\Yaml2Compat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */