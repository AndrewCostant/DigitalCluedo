package com.cluedo.domain.dto;

import com.cluedo.domain.*;

public sealed interface ActionResult permits ChanceAction, NormalCellAction, NormalRoomAction, SecretPassageAction, GamblingRoomAction {
    String getType();
    Cell getCell();
    Card getCard();
    int getValue();
}






