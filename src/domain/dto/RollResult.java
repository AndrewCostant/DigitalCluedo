package domain.dto;

import java.util.*;
import domain.*;

//record, java in compilazione fa equals, hashcode, tostring, ecc.. 
public record RollResult (int value, Set<Cell> cells) {
    
}
