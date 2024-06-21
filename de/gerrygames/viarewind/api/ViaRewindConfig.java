/*   */ package de.gerrygames.viarewind.api;public interface ViaRewindConfig { CooldownIndicator getCooldownIndicator();
/*   */   boolean isReplaceAdventureMode();
/*   */   boolean isReplaceParticles();
/*   */   int getMaxBookPages();
/*   */   int getMaxBookPageSize();
/* 6 */   public enum CooldownIndicator { TITLE, ACTION_BAR, BOSS_BAR, DISABLED; }
/*   */    }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\api\ViaRewindConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */