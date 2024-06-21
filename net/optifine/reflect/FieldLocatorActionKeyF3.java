/*    */ package net.optifine.reflect;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class FieldLocatorActionKeyF3
/*    */   implements IFieldLocator
/*    */ {
/*    */   public Field getField() {
/* 14 */     Class<Minecraft> oclass = Minecraft.class;
/* 15 */     Field field = getFieldRenderChunksMany();
/*    */     
/* 17 */     if (field == null) {
/*    */       
/* 19 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3 (field renderChunksMany not found)");
/* 20 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 24 */     Field field1 = ReflectorRaw.getFieldAfter(Minecraft.class, field, boolean.class, 0);
/*    */     
/* 26 */     if (field1 == null) {
/*    */       
/* 28 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".actionKeyF3");
/* 29 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 33 */     return field1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getFieldRenderChunksMany() {
/* 40 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 41 */     boolean flag = minecraft.renderChunksMany;
/* 42 */     Field[] afield = Minecraft.class.getDeclaredFields();
/* 43 */     minecraft.renderChunksMany = true;
/* 44 */     Field[] afield1 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.TRUE);
/* 45 */     minecraft.renderChunksMany = false;
/* 46 */     Field[] afield2 = ReflectorRaw.getFields(minecraft, afield, boolean.class, Boolean.FALSE);
/* 47 */     minecraft.renderChunksMany = flag;
/* 48 */     Set<Field> set = new HashSet<>(Arrays.asList(afield1));
/* 49 */     Set<Field> set1 = new HashSet<>(Arrays.asList(afield2));
/* 50 */     Set<Field> set2 = new HashSet<>(set);
/* 51 */     set2.retainAll(set1);
/* 52 */     Field[] afield3 = set2.<Field>toArray(new Field[set2.size()]);
/* 53 */     return (afield3.length != 1) ? null : afield3[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\reflect\FieldLocatorActionKeyF3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */