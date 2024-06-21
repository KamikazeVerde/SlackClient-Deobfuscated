package net.minecraftforge.client.model;

import com.google.common.base.Optional;

public interface IModelState {
  Optional<TRSRTransformation> apply(Optional<? extends IModelPart> paramOptional);
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraftforge\client\model\IModelState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */