/*    */ package com.viaversion.viabackwards.protocol.protocol1_11_1to1_12.data;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntMap;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.Int2IntOpenHashMap;
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
/*    */ public class MapColorMapping
/*    */ {
/* 24 */   private static final Int2IntMap MAPPING = (Int2IntMap)new Int2IntOpenHashMap(64, 0.99F);
/*    */   
/*    */   static {
/* 27 */     MAPPING.defaultReturnValue(-1);
/* 28 */     MAPPING.put(144, 59);
/* 29 */     MAPPING.put(145, 56);
/* 30 */     MAPPING.put(146, 56);
/* 31 */     MAPPING.put(147, 45);
/* 32 */     MAPPING.put(148, 63);
/* 33 */     MAPPING.put(149, 60);
/* 34 */     MAPPING.put(150, 60);
/* 35 */     MAPPING.put(151, 136);
/* 36 */     MAPPING.put(152, 83);
/* 37 */     MAPPING.put(153, 83);
/* 38 */     MAPPING.put(154, 80);
/* 39 */     MAPPING.put(155, 115);
/* 40 */     MAPPING.put(156, 39);
/* 41 */     MAPPING.put(157, 39);
/* 42 */     MAPPING.put(158, 36);
/* 43 */     MAPPING.put(159, 47);
/* 44 */     MAPPING.put(160, 60);
/* 45 */     MAPPING.put(161, 61);
/* 46 */     MAPPING.put(162, 62);
/* 47 */     MAPPING.put(163, 137);
/* 48 */     MAPPING.put(164, 108);
/* 49 */     MAPPING.put(165, 108);
/* 50 */     MAPPING.put(166, 109);
/* 51 */     MAPPING.put(167, 111);
/* 52 */     MAPPING.put(168, 112);
/* 53 */     MAPPING.put(169, 113);
/* 54 */     MAPPING.put(170, 114);
/* 55 */     MAPPING.put(171, 115);
/* 56 */     MAPPING.put(172, 118);
/* 57 */     MAPPING.put(173, 107);
/* 58 */     MAPPING.put(174, 107);
/* 59 */     MAPPING.put(175, 118);
/* 60 */     MAPPING.put(176, 91);
/* 61 */     MAPPING.put(177, 45);
/* 62 */     MAPPING.put(178, 46);
/* 63 */     MAPPING.put(179, 47);
/* 64 */     MAPPING.put(180, 85);
/* 65 */     MAPPING.put(181, 44);
/* 66 */     MAPPING.put(182, 27);
/* 67 */     MAPPING.put(183, 84);
/* 68 */     MAPPING.put(184, 83);
/* 69 */     MAPPING.put(185, 83);
/* 70 */     MAPPING.put(186, 83);
/* 71 */     MAPPING.put(187, 84);
/* 72 */     MAPPING.put(188, 84);
/* 73 */     MAPPING.put(189, 71);
/* 74 */     MAPPING.put(190, 71);
/* 75 */     MAPPING.put(191, 87);
/* 76 */     MAPPING.put(192, 107);
/* 77 */     MAPPING.put(193, 139);
/* 78 */     MAPPING.put(194, 43);
/* 79 */     MAPPING.put(195, 107);
/* 80 */     MAPPING.put(196, 111);
/* 81 */     MAPPING.put(197, 111);
/* 82 */     MAPPING.put(198, 111);
/* 83 */     MAPPING.put(199, 107);
/* 84 */     MAPPING.put(200, 112);
/* 85 */     MAPPING.put(201, 113);
/* 86 */     MAPPING.put(202, 113);
/* 87 */     MAPPING.put(203, 115);
/* 88 */     MAPPING.put(204, 116);
/* 89 */     MAPPING.put(205, 117);
/* 90 */     MAPPING.put(206, 107);
/* 91 */     MAPPING.put(207, 119);
/*    */   }
/*    */   
/*    */   public static int getNearestOldColor(int color) {
/* 95 */     return MAPPING.getOrDefault(color, color);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_11_1to1_12\data\MapColorMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */