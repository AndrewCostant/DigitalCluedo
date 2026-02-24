package com.cluedo.domain.dto;
import java.util.*;
import com.cluedo.domain.*;

public sealed interface DoActionResult permits ChanceDoAction, NormalCellDoAction, RoomCellDoAction {
    Cell getCell();
    Boolean isGameEnded();
    ArrayList<Card> getCardsShown();
    EffectStrategy getEffect();
}