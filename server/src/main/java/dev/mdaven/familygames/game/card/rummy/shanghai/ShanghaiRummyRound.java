package dev.mdaven.familygames.game.card.rummy.shanghai;

import dev.mdaven.familygames.game.card.rummy.RummyCardGroup;

public enum ShanghaiRummyRound {
    SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN;

    public int maxBuys() {
        if (this == THIRTEEN) return 3;
        return 2;
    }

    public int dealtCards() {
        return switch (this) {
            case SIX -> 6;
            case SEVEN -> 7;
            case EIGHT -> 8;
            case NINE -> 9;
            case TEN -> 10;
            case ELEVEN -> 11;
            case TWELVE -> 12;
            case THIRTEEN -> 13;
        };
    }

    public ShanghaiRummyRound next() {
        return switch (this) {
            case SIX -> SEVEN;
            case SEVEN -> EIGHT;
            case EIGHT -> NINE;
            case NINE -> TEN;
            case TEN -> ELEVEN;
            case ELEVEN -> TWELVE;
            case TWELVE -> THIRTEEN;
            case THIRTEEN -> null;
        };
    }

//    public RummyCardGroup.GroupType[] getTarget() {
//        return switch (this) {
//            case SIX -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.SET};
//            case SEVEN -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.RUN};
//            case EIGHT -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.RUN, RummyCardGroup.GroupType.RUN};
//            case NINE -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.SET,
//                    RummyCardGroup.GroupType.SET};
//            case TEN -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.SET,
//                    RummyCardGroup.GroupType.RUN};
//            case ELEVEN -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.RUN,
//                    RummyCardGroup.GroupType.RUN};
//            case TWELVE -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.SET,
//                    RummyCardGroup.GroupType.SET, RummyCardGroup.GroupType.SET};
//            case THIRTEEN -> new RummyCardGroup.GroupType[]{RummyCardGroup.GroupType.RUN, RummyCardGroup.GroupType.RUN,
//                    RummyCardGroup.GroupType.RUN, RummyCardGroup.GroupType.RUN};
//        };
//    }
}
