/*     */ package net.optifine.player;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.awt.Dimension;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.CustomEntityModelParser;
/*     */ import net.optifine.util.Json;
/*     */ 
/*     */ 
/*     */ public class PlayerItemParser
/*     */ {
/*  24 */   private static JsonParser jsonParser = new JsonParser();
/*     */   
/*     */   public static final String ITEM_TYPE = "type";
/*     */   public static final String ITEM_TEXTURE_SIZE = "textureSize";
/*     */   public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
/*     */   public static final String ITEM_MODELS = "models";
/*     */   public static final String MODEL_ID = "id";
/*     */   public static final String MODEL_BASE_ID = "baseId";
/*     */   public static final String MODEL_TYPE = "type";
/*     */   public static final String MODEL_TEXTURE = "texture";
/*     */   public static final String MODEL_TEXTURE_SIZE = "textureSize";
/*     */   public static final String MODEL_ATTACH_TO = "attachTo";
/*     */   public static final String MODEL_INVERT_AXIS = "invertAxis";
/*     */   public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
/*     */   public static final String MODEL_TRANSLATE = "translate";
/*     */   public static final String MODEL_ROTATE = "rotate";
/*     */   public static final String MODEL_SCALE = "scale";
/*     */   public static final String MODEL_BOXES = "boxes";
/*     */   public static final String MODEL_SPRITES = "sprites";
/*     */   public static final String MODEL_SUBMODEL = "submodel";
/*     */   public static final String MODEL_SUBMODELS = "submodels";
/*     */   public static final String BOX_TEXTURE_OFFSET = "textureOffset";
/*     */   public static final String BOX_COORDINATES = "coordinates";
/*     */   public static final String BOX_SIZE_ADD = "sizeAdd";
/*     */   public static final String BOX_UV_DOWN = "uvDown";
/*     */   public static final String BOX_UV_UP = "uvUp";
/*     */   public static final String BOX_UV_NORTH = "uvNorth";
/*     */   public static final String BOX_UV_SOUTH = "uvSouth";
/*     */   public static final String BOX_UV_WEST = "uvWest";
/*     */   public static final String BOX_UV_EAST = "uvEast";
/*     */   public static final String BOX_UV_FRONT = "uvFront";
/*     */   public static final String BOX_UV_BACK = "uvBack";
/*     */   public static final String BOX_UV_LEFT = "uvLeft";
/*     */   public static final String BOX_UV_RIGHT = "uvRight";
/*     */   public static final String ITEM_TYPE_MODEL = "PlayerItem";
/*     */   public static final String MODEL_TYPE_BOX = "ModelBox";
/*     */   
/*     */   public static PlayerItemModel parseItemModel(JsonObject obj) {
/*  62 */     String s = Json.getString(obj, "type");
/*     */     
/*  64 */     if (!Config.equals(s, "PlayerItem"))
/*     */     {
/*  66 */       throw new JsonParseException("Unknown model type: " + s);
/*     */     }
/*     */ 
/*     */     
/*  70 */     int[] aint = Json.parseIntArray(obj.get("textureSize"), 2);
/*  71 */     checkNull(aint, "Missing texture size");
/*  72 */     Dimension dimension = new Dimension(aint[0], aint[1]);
/*  73 */     boolean flag = Json.getBoolean(obj, "usePlayerTexture", false);
/*  74 */     JsonArray jsonarray = (JsonArray)obj.get("models");
/*  75 */     checkNull(jsonarray, "Missing elements");
/*  76 */     Map<Object, Object> map = new HashMap<>();
/*  77 */     List list = new ArrayList();
/*  78 */     new ArrayList();
/*     */     
/*  80 */     int i = 0; while (true) { JsonObject jsonobject; if (i < jsonarray.size())
/*     */       
/*  82 */       { jsonobject = (JsonObject)jsonarray.get(i);
/*  83 */         String s1 = Json.getString(jsonobject, "baseId");
/*     */         
/*  85 */         if (s1 != null)
/*     */         
/*  87 */         { JsonObject jsonobject1 = (JsonObject)map.get(s1);
/*     */           
/*  89 */           if (jsonobject1 == null)
/*     */           
/*  91 */           { Config.warn("BaseID not found: " + s1); }
/*     */           
/*     */           else
/*     */           
/*  95 */           { for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject1.entrySet()) {
/*     */               
/*  97 */               if (!jsonobject.has(entry.getKey()))
/*     */               {
/*  99 */                 jsonobject.add(entry.getKey(), entry.getValue());
/*     */               }
/*     */             } 
/*     */ 
/*     */             
/* 104 */             String s2 = Json.getString(jsonobject, "id"); }  continue; }  } else { break; }  String str = Json.getString(jsonobject, "id");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       i++; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     PlayerItemRenderer[] aplayeritemrenderer = (PlayerItemRenderer[])list.toArray((Object[])new PlayerItemRenderer[list.size()]);
/* 127 */     return new PlayerItemModel(dimension, flag, aplayeritemrenderer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 133 */     if (obj == null)
/*     */     {
/* 135 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation makeResourceLocation(String texture) {
/* 141 */     int i = texture.indexOf(':');
/*     */     
/* 143 */     if (i < 0)
/*     */     {
/* 145 */       return new ResourceLocation(texture);
/*     */     }
/*     */ 
/*     */     
/* 149 */     String s = texture.substring(0, i);
/* 150 */     String s1 = texture.substring(i + 1);
/* 151 */     return new ResourceLocation(s, s1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseAttachModel(String attachModelStr) {
/* 157 */     if (attachModelStr == null)
/*     */     {
/* 159 */       return 0;
/*     */     }
/* 161 */     if (attachModelStr.equals("body"))
/*     */     {
/* 163 */       return 0;
/*     */     }
/* 165 */     if (attachModelStr.equals("head"))
/*     */     {
/* 167 */       return 1;
/*     */     }
/* 169 */     if (attachModelStr.equals("leftArm"))
/*     */     {
/* 171 */       return 2;
/*     */     }
/* 173 */     if (attachModelStr.equals("rightArm"))
/*     */     {
/* 175 */       return 3;
/*     */     }
/* 177 */     if (attachModelStr.equals("leftLeg"))
/*     */     {
/* 179 */       return 4;
/*     */     }
/* 181 */     if (attachModelStr.equals("rightLeg"))
/*     */     {
/* 183 */       return 5;
/*     */     }
/* 185 */     if (attachModelStr.equals("cape"))
/*     */     {
/* 187 */       return 6;
/*     */     }
/*     */ 
/*     */     
/* 191 */     Config.warn("Unknown attachModel: " + attachModelStr);
/* 192 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerItemRenderer parseItemRenderer(JsonObject elem, Dimension textureDim) {
/* 198 */     String s = Json.getString(elem, "type");
/*     */     
/* 200 */     if (!Config.equals(s, "ModelBox")) {
/*     */       
/* 202 */       Config.warn("Unknown model type: " + s);
/* 203 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 207 */     String s1 = Json.getString(elem, "attachTo");
/* 208 */     int i = parseAttachModel(s1);
/* 209 */     ModelBase modelbase = new ModelPlayerItem();
/* 210 */     modelbase.textureWidth = textureDim.width;
/* 211 */     modelbase.textureHeight = textureDim.height;
/* 212 */     ModelRenderer modelrenderer = parseModelRenderer(elem, modelbase, null, null);
/* 213 */     PlayerItemRenderer playeritemrenderer = new PlayerItemRenderer(i, modelrenderer);
/* 214 */     return playeritemrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ModelRenderer parseModelRenderer(JsonObject elem, ModelBase modelBase, int[] parentTextureSize, String basePath) {
/* 220 */     ModelRenderer modelrenderer = new ModelRenderer(modelBase);
/* 221 */     String s = Json.getString(elem, "id");
/* 222 */     modelrenderer.setId(s);
/* 223 */     float f = Json.getFloat(elem, "scale", 1.0F);
/* 224 */     modelrenderer.scaleX = f;
/* 225 */     modelrenderer.scaleY = f;
/* 226 */     modelrenderer.scaleZ = f;
/* 227 */     String s1 = Json.getString(elem, "texture");
/*     */     
/* 229 */     if (s1 != null)
/*     */     {
/* 231 */       modelrenderer.setTextureLocation(CustomEntityModelParser.getResourceLocation(basePath, s1, ".png"));
/*     */     }
/*     */     
/* 234 */     int[] aint = Json.parseIntArray(elem.get("textureSize"), 2);
/*     */     
/* 236 */     if (aint == null)
/*     */     {
/* 238 */       aint = parentTextureSize;
/*     */     }
/*     */     
/* 241 */     if (aint != null)
/*     */     {
/* 243 */       modelrenderer.setTextureSize(aint[0], aint[1]);
/*     */     }
/*     */     
/* 246 */     String s2 = Json.getString(elem, "invertAxis", "").toLowerCase();
/* 247 */     boolean flag = s2.contains("x");
/* 248 */     boolean flag1 = s2.contains("y");
/* 249 */     boolean flag2 = s2.contains("z");
/* 250 */     float[] afloat = Json.parseFloatArray(elem.get("translate"), 3, new float[3]);
/*     */     
/* 252 */     if (flag)
/*     */     {
/* 254 */       afloat[0] = -afloat[0];
/*     */     }
/*     */     
/* 257 */     if (flag1)
/*     */     {
/* 259 */       afloat[1] = -afloat[1];
/*     */     }
/*     */     
/* 262 */     if (flag2)
/*     */     {
/* 264 */       afloat[2] = -afloat[2];
/*     */     }
/*     */     
/* 267 */     float[] afloat1 = Json.parseFloatArray(elem.get("rotate"), 3, new float[3]);
/*     */     
/* 269 */     for (int i = 0; i < afloat1.length; i++)
/*     */     {
/* 271 */       afloat1[i] = afloat1[i] / 180.0F * MathHelper.PI;
/*     */     }
/*     */     
/* 274 */     if (flag)
/*     */     {
/* 276 */       afloat1[0] = -afloat1[0];
/*     */     }
/*     */     
/* 279 */     if (flag1)
/*     */     {
/* 281 */       afloat1[1] = -afloat1[1];
/*     */     }
/*     */     
/* 284 */     if (flag2)
/*     */     {
/* 286 */       afloat1[2] = -afloat1[2];
/*     */     }
/*     */     
/* 289 */     modelrenderer.setRotationPoint(afloat[0], afloat[1], afloat[2]);
/* 290 */     modelrenderer.rotateAngleX = afloat1[0];
/* 291 */     modelrenderer.rotateAngleY = afloat1[1];
/* 292 */     modelrenderer.rotateAngleZ = afloat1[2];
/* 293 */     String s3 = Json.getString(elem, "mirrorTexture", "").toLowerCase();
/* 294 */     boolean flag3 = s3.contains("u");
/* 295 */     boolean flag4 = s3.contains("v");
/*     */     
/* 297 */     if (flag3)
/*     */     {
/* 299 */       modelrenderer.mirror = true;
/*     */     }
/*     */     
/* 302 */     if (flag4)
/*     */     {
/* 304 */       modelrenderer.mirrorV = true;
/*     */     }
/*     */     
/* 307 */     JsonArray jsonarray = elem.getAsJsonArray("boxes");
/*     */     
/* 309 */     if (jsonarray != null)
/*     */     {
/* 311 */       for (int j = 0; j < jsonarray.size(); j++) {
/*     */         
/* 313 */         JsonObject jsonobject = jsonarray.get(j).getAsJsonObject();
/* 314 */         int[] aint1 = Json.parseIntArray(jsonobject.get("textureOffset"), 2);
/* 315 */         int[][] aint2 = parseFaceUvs(jsonobject);
/*     */         
/* 317 */         if (aint1 == null && aint2 == null)
/*     */         {
/* 319 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/*     */         
/* 322 */         float[] afloat2 = Json.parseFloatArray(jsonobject.get("coordinates"), 6);
/*     */         
/* 324 */         if (afloat2 == null)
/*     */         {
/* 326 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/*     */         
/* 329 */         if (flag)
/*     */         {
/* 331 */           afloat2[0] = -afloat2[0] - afloat2[3];
/*     */         }
/*     */         
/* 334 */         if (flag1)
/*     */         {
/* 336 */           afloat2[1] = -afloat2[1] - afloat2[4];
/*     */         }
/*     */         
/* 339 */         if (flag2)
/*     */         {
/* 341 */           afloat2[2] = -afloat2[2] - afloat2[5];
/*     */         }
/*     */         
/* 344 */         float f1 = Json.getFloat(jsonobject, "sizeAdd", 0.0F);
/*     */         
/* 346 */         if (aint2 != null) {
/*     */           
/* 348 */           modelrenderer.addBox(aint2, afloat2[0], afloat2[1], afloat2[2], afloat2[3], afloat2[4], afloat2[5], f1);
/*     */         }
/*     */         else {
/*     */           
/* 352 */           modelrenderer.setTextureOffset(aint1[0], aint1[1]);
/* 353 */           modelrenderer.addBox(afloat2[0], afloat2[1], afloat2[2], (int)afloat2[3], (int)afloat2[4], (int)afloat2[5], f1);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 358 */     JsonArray jsonarray1 = elem.getAsJsonArray("sprites");
/*     */     
/* 360 */     if (jsonarray1 != null)
/*     */     {
/* 362 */       for (int k = 0; k < jsonarray1.size(); k++) {
/*     */         
/* 364 */         JsonObject jsonobject2 = jsonarray1.get(k).getAsJsonObject();
/* 365 */         int[] aint3 = Json.parseIntArray(jsonobject2.get("textureOffset"), 2);
/*     */         
/* 367 */         if (aint3 == null)
/*     */         {
/* 369 */           throw new JsonParseException("Texture offset not specified");
/*     */         }
/*     */         
/* 372 */         float[] afloat3 = Json.parseFloatArray(jsonobject2.get("coordinates"), 6);
/*     */         
/* 374 */         if (afloat3 == null)
/*     */         {
/* 376 */           throw new JsonParseException("Coordinates not specified");
/*     */         }
/*     */         
/* 379 */         if (flag)
/*     */         {
/* 381 */           afloat3[0] = -afloat3[0] - afloat3[3];
/*     */         }
/*     */         
/* 384 */         if (flag1)
/*     */         {
/* 386 */           afloat3[1] = -afloat3[1] - afloat3[4];
/*     */         }
/*     */         
/* 389 */         if (flag2)
/*     */         {
/* 391 */           afloat3[2] = -afloat3[2] - afloat3[5];
/*     */         }
/*     */         
/* 394 */         float f2 = Json.getFloat(jsonobject2, "sizeAdd", 0.0F);
/* 395 */         modelrenderer.setTextureOffset(aint3[0], aint3[1]);
/* 396 */         modelrenderer.addSprite(afloat3[0], afloat3[1], afloat3[2], (int)afloat3[3], (int)afloat3[4], (int)afloat3[5], f2);
/*     */       } 
/*     */     }
/*     */     
/* 400 */     JsonObject jsonobject1 = (JsonObject)elem.get("submodel");
/*     */     
/* 402 */     if (jsonobject1 != null) {
/*     */       
/* 404 */       ModelRenderer modelrenderer2 = parseModelRenderer(jsonobject1, modelBase, aint, basePath);
/* 405 */       modelrenderer.addChild(modelrenderer2);
/*     */     } 
/*     */     
/* 408 */     JsonArray jsonarray2 = (JsonArray)elem.get("submodels");
/*     */     
/* 410 */     if (jsonarray2 != null)
/*     */     {
/* 412 */       for (int l = 0; l < jsonarray2.size(); l++) {
/*     */         
/* 414 */         JsonObject jsonobject3 = (JsonObject)jsonarray2.get(l);
/* 415 */         ModelRenderer modelrenderer3 = parseModelRenderer(jsonobject3, modelBase, aint, basePath);
/*     */         
/* 417 */         if (modelrenderer3.getId() != null) {
/*     */           
/* 419 */           ModelRenderer modelrenderer1 = modelrenderer.getChild(modelrenderer3.getId());
/*     */           
/* 421 */           if (modelrenderer1 != null)
/*     */           {
/* 423 */             Config.warn("Duplicate model ID: " + modelrenderer3.getId());
/*     */           }
/*     */         } 
/*     */         
/* 427 */         modelrenderer.addChild(modelrenderer3);
/*     */       } 
/*     */     }
/*     */     
/* 431 */     return modelrenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] parseFaceUvs(JsonObject box) {
/* 436 */     int[][] aint = { Json.parseIntArray(box.get("uvDown"), 4), Json.parseIntArray(box.get("uvUp"), 4), Json.parseIntArray(box.get("uvNorth"), 4), Json.parseIntArray(box.get("uvSouth"), 4), Json.parseIntArray(box.get("uvWest"), 4), Json.parseIntArray(box.get("uvEast"), 4) };
/*     */     
/* 438 */     if (aint[2] == null)
/*     */     {
/* 440 */       aint[2] = Json.parseIntArray(box.get("uvFront"), 4);
/*     */     }
/*     */     
/* 443 */     if (aint[3] == null)
/*     */     {
/* 445 */       aint[3] = Json.parseIntArray(box.get("uvBack"), 4);
/*     */     }
/*     */     
/* 448 */     if (aint[4] == null)
/*     */     {
/* 450 */       aint[4] = Json.parseIntArray(box.get("uvLeft"), 4);
/*     */     }
/*     */     
/* 453 */     if (aint[5] == null)
/*     */     {
/* 455 */       aint[5] = Json.parseIntArray(box.get("uvRight"), 4);
/*     */     }
/*     */     
/* 458 */     boolean flag = false;
/*     */     
/* 460 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 462 */       if (aint[i] != null)
/*     */       {
/* 464 */         flag = true;
/*     */       }
/*     */     } 
/*     */     
/* 468 */     if (!flag)
/*     */     {
/* 470 */       return (int[][])null;
/*     */     }
/*     */ 
/*     */     
/* 474 */     return aint;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\PlayerItemParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */