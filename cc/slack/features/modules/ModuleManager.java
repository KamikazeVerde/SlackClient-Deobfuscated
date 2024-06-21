/*     */ package cc.slack.features.modules;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.impl.combat.AntiFireball;
/*     */ import cc.slack.features.modules.impl.combat.KillAura;
/*     */ import cc.slack.features.modules.impl.exploit.Kick;
/*     */ import cc.slack.features.modules.impl.exploit.Regen;
/*     */ import cc.slack.features.modules.impl.ghost.AimAssist;
/*     */ import cc.slack.features.modules.impl.ghost.Autoclicker;
/*     */ import cc.slack.features.modules.impl.movement.Flight;
/*     */ import cc.slack.features.modules.impl.movement.InvMove;
/*     */ import cc.slack.features.modules.impl.movement.SafeWalk;
/*     */ import cc.slack.features.modules.impl.other.Killsults;
/*     */ import cc.slack.features.modules.impl.other.RemoveEffect;
/*     */ import cc.slack.features.modules.impl.other.Tweaks;
/*     */ import cc.slack.features.modules.impl.render.Projectiles;
/*     */ import cc.slack.features.modules.impl.utilties.AutoPlay;
/*     */ import cc.slack.features.modules.impl.world.Breaker;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ModuleManager {
/*  24 */   private final Map<Class<? extends Module>, Module> modules = new LinkedHashMap<>();
/*     */   
/*     */   public void initialize() {
/*     */     try {
/*  28 */       addModules(new Module[] { (Module)new AntiFireball(), (Module)new Criticals(), (Module)new Hitbox(), (Module)new KillAura(), (Module)new FastBow(), (Module)new Velocity(), (Module)new Flight(), (Module)new InvMove(), (Module)new Jesus(), (Module)new NoSlow(), (Module)new NoWeb(), (Module)new SafeWalk(), (Module)new Speed(), (Module)new Sprint(), (Module)new Step(), (Module)new AntiBot(), (Module)new AutoPlay(), (Module)new AutoRespawn(), (Module)new Performance(), (Module)new RemoveEffect(), (Module)new Killsults(), (Module)new Targets(), (Module)new Tweaks(), (Module)new AntiVoid(), (Module)new Blink(), (Module)new FastEat(), (Module)new FreeLook(), (Module)new SpeedMine(), (Module)new NoFall(), (Module)new TestBlink(), (Module)new TimerModule(), (Module)new Breaker(), (Module)new FastPlace(), (Module)new InvManager(), (Module)new Scaffold(), (Module)new Stealer(), (Module)new ChatBypass(), (Module)new Disabler(), (Module)new Kick(), (Module)new Regen(), (Module)new MultiAction(), (Module)new Phase(), (Module)new Animations(), (Module)new Ambience(), (Module)new Bobbing(), (Module)new Cape(), (Module)new ChestESP(), (Module)new ClickGUI(), (Module)new ESP(), (Module)new HUD(), (Module)new Projectiles(), (Module)new ScoreboardModule(), (Module)new TargetHUD(), (Module)new XRay(), (Module)new AimBot(), (Module)new AimAssist(), (Module)new Autoclicker(), (Module)new AutoTool(), (Module)new Backtrack(), (Module)new JumpReset(), (Module)new KeepSprint(), (Module)new LegitNofall(), (Module)new LegitScaffold(), (Module)new PearlAntiVoid(), (Module)new Reach(), (Module)new Stap(), (Module)new Wtap(), (Module)new AutoPlay(), (Module)new AutoLogin(), (Module)new LegitMode(), (Module)new PacketDebugger() });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 119 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Module> getModules() {
/* 125 */     return new ArrayList<>(this.modules.values());
/*     */   }
/*     */   
/*     */   public <T extends Module> T getInstance(Class<T> clazz) {
/* 129 */     return (T)this.modules.get(clazz);
/*     */   }
/*     */   
/*     */   public Module getModuleByName(String n) {
/* 133 */     for (Module m : this.modules.values()) {
/* 134 */       if (m.getName().equals(n)) {
/* 135 */         return m;
/*     */       }
/*     */     } 
/* 138 */     throw new IllegalArgumentException("Module not found.");
/*     */   }
/*     */   
/*     */   private void addModules(Module... mod) {
/* 142 */     for (Module m : mod)
/* 143 */       this.modules.put(m.getClass(), m); 
/*     */   }
/*     */   
/*     */   public Module[] getModulesByCategory(Category c) {
/* 147 */     List<Module> category = new ArrayList<>();
/*     */     
/* 149 */     this.modules.forEach((aClass, module) -> {
/*     */           if (module.getCategory() == c) {
/*     */             category.add(module);
/*     */           }
/*     */         });
/* 154 */     return category.<Module>toArray(new Module[0]);
/*     */   }
/*     */   
/*     */   public Module[] getModulesByCategoryABC(Category c) {
/* 158 */     Module[] modulesByCategory = getModulesByCategory(c);
/* 159 */     Arrays.sort(modulesByCategory, Comparator.comparing(Module::getName));
/* 160 */     return modulesByCategory;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\ModuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */