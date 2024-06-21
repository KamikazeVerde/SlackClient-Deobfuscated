/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorClass;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ 
/*     */ 
/*     */ public class ChunkUtils
/*     */ {
/*  16 */   private static ReflectorClass chunkClass = new ReflectorClass(Chunk.class);
/*  17 */   private static ReflectorField fieldHasEntities = findFieldHasEntities();
/*  18 */   private static ReflectorField fieldPrecipitationHeightMap = new ReflectorField(chunkClass, int[].class, 0);
/*     */ 
/*     */   
/*     */   public static boolean hasEntities(Chunk chunk) {
/*  22 */     return Reflector.getFieldValueBoolean(chunk, fieldHasEntities, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPrecipitationHeight(Chunk chunk, BlockPos pos) {
/*  27 */     int[] aint = (int[])Reflector.getFieldValue(chunk, fieldPrecipitationHeightMap);
/*     */     
/*  29 */     if (aint != null && aint.length == 256) {
/*     */       
/*  31 */       int i = pos.getX() & 0xF;
/*  32 */       int j = pos.getZ() & 0xF;
/*  33 */       int k = i | j << 4;
/*  34 */       int l = aint[k];
/*     */       
/*  36 */       if (l >= 0)
/*     */       {
/*  38 */         return l;
/*     */       }
/*     */ 
/*     */       
/*  42 */       BlockPos blockpos = chunk.getPrecipitationHeight(pos);
/*  43 */       return blockpos.getY();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  48 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ReflectorField findFieldHasEntities() {
/*     */     try {
/*  56 */       Chunk chunk = new Chunk(null, 0, 0);
/*  57 */       List<Field> list = new ArrayList();
/*  58 */       List<Object> list1 = new ArrayList();
/*  59 */       Field[] afield = Chunk.class.getDeclaredFields();
/*     */       
/*  61 */       for (int i = 0; i < afield.length; i++) {
/*     */         
/*  63 */         Field field = afield[i];
/*     */         
/*  65 */         if (field.getType() == boolean.class) {
/*     */           
/*  67 */           field.setAccessible(true);
/*  68 */           list.add(field);
/*  69 */           list1.add(field.get(chunk));
/*     */         } 
/*     */       } 
/*     */       
/*  73 */       chunk.setHasEntities(false);
/*  74 */       List<Object> list2 = new ArrayList();
/*     */       
/*  76 */       for (Field e : list) {
/*     */         
/*  78 */         Field field1 = e;
/*  79 */         list2.add(field1.get(chunk));
/*     */       } 
/*     */       
/*  82 */       chunk.setHasEntities(true);
/*  83 */       List<Object> list3 = new ArrayList();
/*     */       
/*  85 */       for (Field e : list) {
/*     */         
/*  87 */         Field field2 = e;
/*  88 */         list3.add(field2.get(chunk));
/*     */       } 
/*     */       
/*  91 */       List<Field> list4 = new ArrayList();
/*     */       
/*  93 */       for (int j = 0; j < list.size(); j++) {
/*     */         
/*  95 */         Field field3 = list.get(j);
/*  96 */         Boolean obool = (Boolean)list2.get(j);
/*  97 */         Boolean obool1 = (Boolean)list3.get(j);
/*     */         
/*  99 */         if (!obool.booleanValue() && obool1.booleanValue()) {
/*     */           
/* 101 */           list4.add(field3);
/* 102 */           Boolean obool2 = (Boolean)list1.get(j);
/* 103 */           field3.set(chunk, obool2);
/*     */         } 
/*     */       } 
/*     */       
/* 107 */       if (list4.size() == 1)
/*     */       {
/* 109 */         Field field4 = list4.get(0);
/* 110 */         return new ReflectorField(field4);
/*     */       }
/*     */     
/* 113 */     } catch (Exception exception) {
/*     */       
/* 115 */       Config.warn(exception.getClass().getName() + " " + exception.getMessage());
/*     */     } 
/*     */     
/* 118 */     Config.warn("Error finding Chunk.hasEntities");
/* 119 */     return new ReflectorField(new ReflectorClass(Chunk.class), "hasEntities");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\ChunkUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */