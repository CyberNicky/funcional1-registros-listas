//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lf1.plp.functional1.parser;

import java.io.IOException;
import java.io.PrintStream;

public class Func1ParserTokenManager implements Func1ParserConstants {
    public PrintStream debugStream;
    static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
    static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
    static final long[] jjbitVec3 = new long[]{2301339413881290750L, -16384L, 4294967295L, 432345564227567616L};
    static final long[] jjbitVec4 = new long[]{0L, 0L, 0L, -36028797027352577L};
    static final long[] jjbitVec5 = new long[]{0L, -1L, -1L, -1L};
    static final long[] jjbitVec6 = new long[]{-1L, -1L, 65535L, 0L};
    static final long[] jjbitVec7 = new long[]{-1L, -1L, 0L, 0L};
    static final long[] jjbitVec8 = new long[]{70368744177663L, 0L, 0L, 0L};
    public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, "and", "or", "not", "length", "true", "false", "let", "var", "in", "if", "then", "else", "fun", "record", "head", "tail", "isEmpty", null, null, null, null, null, null, null, null, "(", ")", "{", "}", "[", "]", ";", ",", ".", "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "||", "&&", "++", "+", "-", "*", "/", "&", "|", "^", "%"};
    static final int[] jjnextStates = new int[]{20, 21, 23, 19, 30, 31, 15, 17, 2, 4, 5, 7, 4, 5, 9, 7, 26, 27, 29, 33, 34, 36, 6, 8, 10};
    int curLexState;
    int defaultLexState;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;
    public static final String[] lexStateNames = new String[]{"DEFAULT"};
    public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] jjtoToken = new long[]{-13824426495L, 1L};
    static final long[] jjtoSkip = new long[]{510L, 0L};
    static final long[] jjtoSpecial = new long[]{448L, 0L};
    static final long[] jjtoMore = new long[]{0L, 0L};
    protected JavaCharStream input_stream;
    private final int[] jjrounds;
    private final int[] jjstateSet;
    private final StringBuilder jjimage;
    private StringBuilder image;
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;

    public void setDebugStream(PrintStream ds) {
        this.debugStream = ds;
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1) {
        switch (pos) {
            case 0:
                if ((active0 & 1152921504606846976L) != 0L) {
                    return 19;
                } else {
                    if ((active0 & 67108352L) != 0L) {
                        this.jjmatchedKind = 31;
                        return 13;
                    }

                    return -1;
                }
            case 1:
                if ((active0 & 66714112L) != 0L) {
                    this.jjmatchedKind = 31;
                    this.jjmatchedPos = 1;
                    return 13;
                } else {
                    if ((active0 & 394240L) != 0L) {
                        return 13;
                    }

                    return -1;
                }
            case 2:
                if ((active0 & 64516096L) != 0L) {
                    this.jjmatchedKind = 31;
                    this.jjmatchedPos = 2;
                    return 13;
                } else {
                    if ((active0 & 2198016L) != 0L) {
                        return 13;
                    }

                    return -1;
                }
            case 3:
                if ((active0 & 26746880L) != 0L) {
                    return 13;
                } else {
                    if ((active0 & 37769216L) != 0L) {
                        this.jjmatchedKind = 31;
                        this.jjmatchedPos = 3;
                        return 13;
                    }

                    return -1;
                }
            case 4:
                if ((active0 & 37752832L) != 0L) {
                    this.jjmatchedKind = 31;
                    this.jjmatchedPos = 4;
                    return 13;
                } else {
                    if ((active0 & 16384L) != 0L) {
                        return 13;
                    }

                    return -1;
                }
            case 5:
                if ((active0 & 33554432L) != 0L) {
                    this.jjmatchedKind = 31;
                    this.jjmatchedPos = 5;
                    return 13;
                } else {
                    if ((active0 & 4198400L) != 0L) {
                        return 13;
                    }

                    return -1;
                }
            default:
                return -1;
        }
    }

    private final int jjStartNfa_0(int pos, long active0, long active1) {
        return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
    }

    private int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case 33:
                this.jjmatchedKind = 46;
                return this.jjMoveStringLiteralDfa1_0(9007199254740992L);
            case 34:
            case 35:
            case 36:
            case 39:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 92:
            case 95:
            case 96:
            case 98:
            case 99:
            case 100:
            case 103:
            case 106:
            case 107:
            case 109:
            case 112:
            case 113:
            case 115:
            case 117:
            case 119:
            case 120:
            case 121:
            case 122:
            default:
                return this.jjMoveNfa_0(0, 0);
            case 37:
                return this.jjStopAtPos(0, 64);
            case 38:
                this.jjmatchedKind = 61;
                return this.jjMoveStringLiteralDfa1_0(36028797018963968L);
            case 40:
                return this.jjStopAtPos(0, 34);
            case 41:
                return this.jjStopAtPos(0, 35);
            case 42:
                return this.jjStopAtPos(0, 59);
            case 43:
                this.jjmatchedKind = 57;
                return this.jjMoveStringLiteralDfa1_0(72057594037927936L);
            case 44:
                return this.jjStopAtPos(0, 41);
            case 45:
                return this.jjStopAtPos(0, 58);
            case 46:
                return this.jjStopAtPos(0, 42);
            case 47:
                return this.jjStartNfaWithStates_0(0, 60, 19);
            case 58:
                return this.jjStopAtPos(0, 49);
            case 59:
                return this.jjStopAtPos(0, 40);
            case 60:
                this.jjmatchedKind = 45;
                return this.jjMoveStringLiteralDfa1_0(2251799813685248L);
            case 61:
                this.jjmatchedKind = 43;
                return this.jjMoveStringLiteralDfa1_0(1125899906842624L);
            case 62:
                this.jjmatchedKind = 44;
                return this.jjMoveStringLiteralDfa1_0(4503599627370496L);
            case 63:
                return this.jjStopAtPos(0, 48);
            case 91:
                return this.jjStopAtPos(0, 38);
            case 93:
                return this.jjStopAtPos(0, 39);
            case 94:
                return this.jjStopAtPos(0, 63);
            case 97:
                return this.jjMoveStringLiteralDfa1_0(512L);
            case 101:
                return this.jjMoveStringLiteralDfa1_0(1048576L);
            case 102:
                return this.jjMoveStringLiteralDfa1_0(2113536L);
            case 104:
                return this.jjMoveStringLiteralDfa1_0(8388608L);
            case 105:
                return this.jjMoveStringLiteralDfa1_0(33947648L);
            case 108:
                return this.jjMoveStringLiteralDfa1_0(36864L);
            case 110:
                return this.jjMoveStringLiteralDfa1_0(2048L);
            case 111:
                return this.jjMoveStringLiteralDfa1_0(1024L);
            case 114:
                return this.jjMoveStringLiteralDfa1_0(4194304L);
            case 116:
                return this.jjMoveStringLiteralDfa1_0(17309696L);
            case 118:
                return this.jjMoveStringLiteralDfa1_0(65536L);
            case 123:
                return this.jjStopAtPos(0, 36);
            case 124:
                this.jjmatchedKind = 62;
                return this.jjMoveStringLiteralDfa1_0(18014398509481984L);
            case 125:
                return this.jjStopAtPos(0, 37);
            case 126:
                return this.jjStopAtPos(0, 47);
        }
    }

    private int jjMoveStringLiteralDfa1_0(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        } catch (IOException var4) {
            this.jjStopStringLiteralDfa_0(0, active0, 0L);
            return 1;
        }

        switch (this.curChar) {
            case 38:
                if ((active0 & 36028797018963968L) != 0L) {
                    return this.jjStopAtPos(1, 55);
                }
                break;
            case 43:
                if ((active0 & 72057594037927936L) != 0L) {
                    return this.jjStopAtPos(1, 56);
                }
                break;
            case 61:
                if ((active0 & 1125899906842624L) != 0L) {
                    return this.jjStopAtPos(1, 50);
                }

                if ((active0 & 2251799813685248L) != 0L) {
                    return this.jjStopAtPos(1, 51);
                }

                if ((active0 & 4503599627370496L) != 0L) {
                    return this.jjStopAtPos(1, 52);
                }

                if ((active0 & 9007199254740992L) != 0L) {
                    return this.jjStopAtPos(1, 53);
                }
                break;
            case 97:
                return this.jjMoveStringLiteralDfa2_0(active0, 16859136L);
            case 101:
                return this.jjMoveStringLiteralDfa2_0(active0, 12619776L);
            case 102:
                if ((active0 & 262144L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 18, 13);
                }
                break;
            case 104:
                return this.jjMoveStringLiteralDfa2_0(active0, 524288L);
            case 108:
                return this.jjMoveStringLiteralDfa2_0(active0, 1048576L);
            case 110:
                if ((active0 & 131072L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 17, 13);
                }

                return this.jjMoveStringLiteralDfa2_0(active0, 512L);
            case 111:
                return this.jjMoveStringLiteralDfa2_0(active0, 2048L);
            case 114:
                if ((active0 & 1024L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 10, 13);
                }

                return this.jjMoveStringLiteralDfa2_0(active0, 8192L);
            case 115:
                return this.jjMoveStringLiteralDfa2_0(active0, 33554432L);
            case 117:
                return this.jjMoveStringLiteralDfa2_0(active0, 2097152L);
            case 124:
                if ((active0 & 18014398509481984L) != 0L) {
                    return this.jjStopAtPos(1, 54);
                }
        }

        return this.jjStartNfa_0(0, active0, 0L);
    }

    private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
        if ((active0 = active0 & old0) == 0L) {
            return this.jjStartNfa_0(0, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(1, active0, 0L);
                return 2;
            }

            switch (this.curChar) {
                case 69:
                    return this.jjMoveStringLiteralDfa3_0(active0, 33554432L);
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
                case 80:
                case 81:
                case 82:
                case 83:
                case 84:
                case 85:
                case 86:
                case 87:
                case 88:
                case 89:
                case 90:
                case 91:
                case 92:
                case 93:
                case 94:
                case 95:
                case 96:
                case 98:
                case 102:
                case 103:
                case 104:
                case 106:
                case 107:
                case 109:
                case 111:
                case 112:
                case 113:
                default:
                    break;
                case 97:
                    return this.jjMoveStringLiteralDfa3_0(active0, 8388608L);
                case 99:
                    return this.jjMoveStringLiteralDfa3_0(active0, 4194304L);
                case 100:
                    if ((active0 & 512L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 9, 13);
                    }
                    break;
                case 101:
                    return this.jjMoveStringLiteralDfa3_0(active0, 524288L);
                case 105:
                    return this.jjMoveStringLiteralDfa3_0(active0, 16777216L);
                case 108:
                    return this.jjMoveStringLiteralDfa3_0(active0, 16384L);
                case 110:
                    if ((active0 & 2097152L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 21, 13);
                    }

                    return this.jjMoveStringLiteralDfa3_0(active0, 4096L);
                case 114:
                    if ((active0 & 65536L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 16, 13);
                    }
                    break;
                case 115:
                    return this.jjMoveStringLiteralDfa3_0(active0, 1048576L);
                case 116:
                    if ((active0 & 2048L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 11, 13);
                    }

                    if ((active0 & 32768L) != 0L) {
                        return this.jjStartNfaWithStates_0(2, 15, 13);
                    }
                    break;
                case 117:
                    return this.jjMoveStringLiteralDfa3_0(active0, 8192L);
            }

            return this.jjStartNfa_0(1, active0, 0L);
        }
    }

    private int jjMoveStringLiteralDfa3_0(long old0, long active0) {
        if ((active0 = active0 & old0) == 0L) {
            return this.jjStartNfa_0(1, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(2, active0, 0L);
                return 3;
            }

            switch (this.curChar) {
                case 100:
                    if ((active0 & 8388608L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 23, 13);
                    }
                    break;
                case 101:
                    if ((active0 & 8192L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 13, 13);
                    }

                    if ((active0 & 1048576L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 20, 13);
                    }
                case 102:
                case 104:
                case 105:
                case 106:
                case 107:
                case 112:
                case 113:
                case 114:
                default:
                    break;
                case 103:
                    return this.jjMoveStringLiteralDfa4_0(active0, 4096L);
                case 108:
                    if ((active0 & 16777216L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 24, 13);
                    }
                    break;
                case 109:
                    return this.jjMoveStringLiteralDfa4_0(active0, 33554432L);
                case 110:
                    if ((active0 & 524288L) != 0L) {
                        return this.jjStartNfaWithStates_0(3, 19, 13);
                    }
                    break;
                case 111:
                    return this.jjMoveStringLiteralDfa4_0(active0, 4194304L);
                case 115:
                    return this.jjMoveStringLiteralDfa4_0(active0, 16384L);
            }

            return this.jjStartNfa_0(2, active0, 0L);
        }
    }

    private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
        if ((active0 = active0 & old0) == 0L) {
            return this.jjStartNfa_0(2, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(3, active0, 0L);
                return 4;
            }

            switch (this.curChar) {
                case 101:
                    if ((active0 & 16384L) != 0L) {
                        return this.jjStartNfaWithStates_0(4, 14, 13);
                    }
                default:
                    return this.jjStartNfa_0(3, active0, 0L);
                case 112:
                    return this.jjMoveStringLiteralDfa5_0(active0, 33554432L);
                case 114:
                    return this.jjMoveStringLiteralDfa5_0(active0, 4194304L);
                case 116:
                    return this.jjMoveStringLiteralDfa5_0(active0, 4096L);
            }
        }
    }

    private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
        if ((active0 = active0 & old0) == 0L) {
            return this.jjStartNfa_0(3, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(4, active0, 0L);
                return 5;
            }

            switch (this.curChar) {
                case 100:
                    if ((active0 & 4194304L) != 0L) {
                        return this.jjStartNfaWithStates_0(5, 22, 13);
                    }
                    break;
                case 104:
                    if ((active0 & 4096L) != 0L) {
                        return this.jjStartNfaWithStates_0(5, 12, 13);
                    }
                    break;
                case 116:
                    return this.jjMoveStringLiteralDfa6_0(active0, 33554432L);
            }

            return this.jjStartNfa_0(4, active0, 0L);
        }
    }

    private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
        if ((active0 = active0 & old0) == 0L) {
            return this.jjStartNfa_0(4, old0, 0L);
        } else {
            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var6) {
                this.jjStopStringLiteralDfa_0(5, active0, 0L);
                return 6;
            }

            switch (this.curChar) {
                case 121:
                    if ((active0 & 33554432L) != 0L) {
                        return this.jjStartNfaWithStates_0(6, 25, 13);
                    }
                default:
                    return this.jjStartNfa_0(5, active0, 0L);
            }
        }
    }

    private int jjStartNfaWithStates_0(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;

        try {
            this.curChar = this.input_stream.readChar();
        } catch (IOException var5) {
            return pos + 1;
        }

        return this.jjMoveNfa_0(state, pos + 1);
    }

    private int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 37;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;

        while(true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }

            if (this.curChar < 64) {
                long l = 1L << this.curChar;

                do {
                    --i;
                    switch (this.jjstateSet[i]) {
                        case 0:
                            if ((287667426198290432L & l) != 0L) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddTwoStates(1, 2);
                            } else if (this.curChar == 47) {
                                this.jjAddStates(3, 5);
                            } else if (this.curChar == 48) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddStates(6, 8);
                            } else if (this.curChar == 36) {
                                if (kind > 31) {
                                    kind = 31;
                                }

                                this.jjCheckNAdd(13);
                            } else if (this.curChar == 34) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 1:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddTwoStates(1, 2);
                            }
                        case 2:
                        case 5:
                        case 15:
                        default:
                            break;
                        case 3:
                            if (this.curChar == 34) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 4:
                            if ((-17179878401L & l) != 0L) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 6:
                            if ((566935683072L & l) != 0L) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 7:
                            if (this.curChar == 34 && kind > 30) {
                                kind = 30;
                            }
                            break;
                        case 8:
                            if ((71776119061217280L & l) != 0L) {
                                this.jjCheckNAddStates(12, 15);
                            }
                            break;
                        case 9:
                            if ((71776119061217280L & l) != 0L) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 10:
                            if ((4222124650659840L & l) != 0L) {
                                this.jjstateSet[this.jjnewStateCnt++] = 11;
                            }
                            break;
                        case 11:
                            if ((71776119061217280L & l) != 0L) {
                                this.jjCheckNAdd(9);
                            }
                            break;
                        case 12:
                            if (this.curChar == 36) {
                                if (kind > 31) {
                                    kind = 31;
                                }

                                this.jjCheckNAdd(13);
                            }
                            break;
                        case 13:
                            if ((287948969894477824L & l) != 0L) {
                                if (kind > 31) {
                                    kind = 31;
                                }

                                this.jjCheckNAdd(13);
                            }
                            break;
                        case 14:
                            if (this.curChar == 48) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddStates(6, 8);
                            }
                            break;
                        case 16:
                            if ((287948901175001088L & l) != 0L) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddTwoStates(16, 2);
                            }
                            break;
                        case 17:
                            if ((71776119061217280L & l) != 0L) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddTwoStates(17, 2);
                            }
                            break;
                        case 18:
                            if (this.curChar == 47) {
                                this.jjAddStates(3, 5);
                            }
                            break;
                        case 19:
                            if (this.curChar == 42) {
                                this.jjCheckNAddTwoStates(32, 33);
                            } else if (this.curChar == 47) {
                                this.jjCheckNAddStates(0, 2);
                            }

                            if (this.curChar == 42) {
                                this.jjstateSet[this.jjnewStateCnt++] = 24;
                            }
                            break;
                        case 20:
                            if ((-9217L & l) != 0L) {
                                this.jjCheckNAddStates(0, 2);
                            }
                            break;
                        case 21:
                            if ((9216L & l) != 0L && kind > 6) {
                                kind = 6;
                            }
                            break;
                        case 22:
                            if (this.curChar == 10 && kind > 6) {
                                kind = 6;
                            }
                            break;
                        case 23:
                            if (this.curChar == 13) {
                                this.jjstateSet[this.jjnewStateCnt++] = 22;
                            }
                            break;
                        case 24:
                            if (this.curChar == 42) {
                                this.jjCheckNAddTwoStates(25, 26);
                            }
                            break;
                        case 25:
                            if ((-4398046511105L & l) != 0L) {
                                this.jjCheckNAddTwoStates(25, 26);
                            }
                            break;
                        case 26:
                            if (this.curChar == 42) {
                                this.jjCheckNAddStates(16, 18);
                            }
                            break;
                        case 27:
                            if ((-145135534866433L & l) != 0L) {
                                this.jjCheckNAddTwoStates(28, 26);
                            }
                            break;
                        case 28:
                            if ((-4398046511105L & l) != 0L) {
                                this.jjCheckNAddTwoStates(28, 26);
                            }
                            break;
                        case 29:
                            if (this.curChar == 47 && kind > 7) {
                                kind = 7;
                            }
                            break;
                        case 30:
                            if (this.curChar == 42) {
                                this.jjstateSet[this.jjnewStateCnt++] = 24;
                            }
                            break;
                        case 31:
                            if (this.curChar == 42) {
                                this.jjCheckNAddTwoStates(32, 33);
                            }
                            break;
                        case 32:
                            if ((-4398046511105L & l) != 0L) {
                                this.jjCheckNAddTwoStates(32, 33);
                            }
                            break;
                        case 33:
                            if (this.curChar == 42) {
                                this.jjCheckNAddStates(19, 21);
                            }
                            break;
                        case 34:
                            if ((-145135534866433L & l) != 0L) {
                                this.jjCheckNAddTwoStates(35, 33);
                            }
                            break;
                        case 35:
                            if ((-4398046511105L & l) != 0L) {
                                this.jjCheckNAddTwoStates(35, 33);
                            }
                            break;
                        case 36:
                            if (this.curChar == 47 && kind > 8) {
                                kind = 8;
                            }
                    }
                } while(i != startsAt);
            } else if (this.curChar < 128) {
                long l = 1L << (this.curChar & 63);

                do {
                    --i;
                    switch (this.jjstateSet[i]) {
                        case 0:
                        case 13:
                            if ((576460745995190270L & l) != 0L) {
                                if (kind > 31) {
                                    kind = 31;
                                }

                                this.jjCheckNAdd(13);
                            }
                        case 1:
                        case 3:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 14:
                        case 17:
                        case 18:
                        case 19:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 26:
                        case 29:
                        case 30:
                        case 31:
                        case 33:
                        default:
                            break;
                        case 2:
                            if ((17592186048512L & l) != 0L && kind > 26) {
                                kind = 26;
                            }
                            break;
                        case 4:
                            if ((-268435457L & l) != 0L) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 5:
                            if (this.curChar == 92) {
                                this.jjAddStates(22, 24);
                            }
                            break;
                        case 6:
                            if ((5700160604602368L & l) != 0L) {
                                this.jjCheckNAddStates(9, 11);
                            }
                            break;
                        case 15:
                            if ((72057594054705152L & l) != 0L) {
                                this.jjCheckNAdd(16);
                            }
                            break;
                        case 16:
                            if ((541165879422L & l) != 0L) {
                                if (kind > 26) {
                                    kind = 26;
                                }

                                this.jjCheckNAddTwoStates(16, 2);
                            }
                            break;
                        case 20:
                            this.jjAddStates(0, 2);
                            break;
                        case 25:
                            this.jjCheckNAddTwoStates(25, 26);
                            break;
                        case 27:
                        case 28:
                            this.jjCheckNAddTwoStates(28, 26);
                            break;
                        case 32:
                            this.jjCheckNAddTwoStates(32, 33);
                            break;
                        case 34:
                        case 35:
                            this.jjCheckNAddTwoStates(35, 33);
                    }
                } while(i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 63);
                int i2 = (this.curChar & 255) >> 6;
                long l2 = 1L << (this.curChar & 63);

                do {
                    --i;
                    switch (this.jjstateSet[i]) {
                        case 0:
                        case 13:
                            if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
                                if (kind > 31) {
                                    kind = 31;
                                }

                                this.jjCheckNAdd(13);
                            }
                            break;
                        case 1:
                        case 2:
                        case 3:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 26:
                        case 29:
                        case 30:
                        case 31:
                        case 33:
                        default:
                            if (i1 != 0 && l1 != 0L && i2 != 0 && l2 == 0L) {
                            }
                            break;
                        case 4:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjAddStates(9, 11);
                            }
                            break;
                        case 20:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjAddStates(0, 2);
                            }
                            break;
                        case 25:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddTwoStates(25, 26);
                            }
                            break;
                        case 27:
                        case 28:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddTwoStates(28, 26);
                            }
                            break;
                        case 32:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddTwoStates(32, 33);
                            }
                            break;
                        case 34:
                        case 35:
                            if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                                this.jjCheckNAddTwoStates(35, 33);
                            }
                    }
                } while(i != startsAt);
            }

            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }

            ++curPos;
            if ((i = this.jjnewStateCnt) == (startsAt = 37 - (this.jjnewStateCnt = startsAt))) {
                return curPos;
            }

            try {
                this.curChar = this.input_stream.readChar();
            } catch (IOException var13) {
                return curPos;
            }
        }
    }

    protected Token jjFillToken() {
        String im = jjstrLiteralImages[this.jjmatchedKind];
        String curTokenImage = im == null ? this.input_stream.GetImage() : im;
        int beginLine = this.input_stream.getBeginLine();
        int beginColumn = this.input_stream.getBeginColumn();
        int endLine = this.input_stream.getEndLine();
        int endColumn = this.input_stream.getEndColumn();
        Token t = Token.newToken(this.jjmatchedKind, curTokenImage);
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        return t;
    }

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0 -> {
                return (jjbitVec2[i2] & l2) != 0L;
            }
            default -> {
                return (jjbitVec0[i1] & l1) != 0L;
            }
        }
    }

    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0 -> {
                return (jjbitVec4[i2] & l2) != 0L;
            }
            case 48 -> {
                return (jjbitVec5[i2] & l2) != 0L;
            }
            case 49 -> {
                return (jjbitVec6[i2] & l2) != 0L;
            }
            case 51 -> {
                return (jjbitVec7[i2] & l2) != 0L;
            }
            case 61 -> {
                return (jjbitVec8[i2] & l2) != 0L;
            }
            default -> {
                return (jjbitVec3[i1] & l1) != 0L;
            }
        }
    }

    public Token getNextToken() {
        Token specialToken = null;
        int curPos = 0;

        while(true) {
            try {
                this.curChar = this.input_stream.BeginToken();
            } catch (Exception var9) {
                this.jjmatchedKind = 0;
                this.jjmatchedPos = -1;
                Token matchedToken = this.jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }

            try {
                this.input_stream.backup(0);

                while(this.curChar <= 32 && (4294981120L & 1L << this.curChar) != 0L) {
                    this.curChar = this.input_stream.BeginToken();
                }
            } catch (IOException var11) {
                continue;
            }

            this.jjmatchedKind = Integer.MAX_VALUE;
            this.jjmatchedPos = 0;
            curPos = this.jjMoveStringLiteralDfa0_0();
            if (this.jjmatchedKind == Integer.MAX_VALUE) {
                int error_line = this.input_stream.getEndLine();
                int error_column = this.input_stream.getEndColumn();
                String error_after = null;
                boolean EOFSeen = false;

                try {
                    this.input_stream.readChar();
                    this.input_stream.backup(1);
                } catch (IOException var10) {
                    EOFSeen = true;
                    error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
                    if (this.curChar != 10 && this.curChar != 13) {
                        ++error_column;
                    } else {
                        ++error_line;
                        error_column = 0;
                    }
                }

                if (!EOFSeen) {
                    this.input_stream.backup(1);
                    error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
                }

                throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
            }

            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }

            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
                Token matchedToken = this.jjFillToken();
                matchedToken.specialToken = specialToken;
                return matchedToken;
            }

            if ((jjtoSpecial[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
                Token matchedToken = this.jjFillToken();
                if (specialToken == null) {
                    specialToken = matchedToken;
                } else {
                    matchedToken.specialToken = specialToken;
                    specialToken = specialToken.next = matchedToken;
                }
            }
        }
    }

    void SkipLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            default -> {
            }
        }
    }

    void MoreLexicalActions() {
        this.jjimageLen += this.lengthOfMatch = this.jjmatchedPos + 1;
        switch (this.jjmatchedKind) {
            default -> {
            }
        }
    }

    void TokenLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            default -> {
            }
        }
    }

    private void jjCheckNAdd(int state) {
        if (this.jjrounds[state] != this.jjround) {
            this.jjstateSet[this.jjnewStateCnt++] = state;
            this.jjrounds[state] = this.jjround;
        }

    }

    private void jjAddStates(int start, int end) {
        do {
            this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[start];
        } while(start++ != end);

    }

    private void jjCheckNAddTwoStates(int state1, int state2) {
        this.jjCheckNAdd(state1);
        this.jjCheckNAdd(state2);
    }

    private void jjCheckNAddStates(int start, int end) {
        do {
            this.jjCheckNAdd(jjnextStates[start]);
        } while(start++ != end);

    }

    public Func1ParserTokenManager(JavaCharStream stream) {
        this.debugStream = System.out;
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.jjrounds = new int[37];
        this.jjstateSet = new int[74];
        this.jjimage = new StringBuilder();
        this.image = this.jjimage;
        this.input_stream = stream;
    }

    public Func1ParserTokenManager(JavaCharStream stream, int lexState) {
        this.debugStream = System.out;
        this.curLexState = 0;
        this.defaultLexState = 0;
        this.jjrounds = new int[37];
        this.jjstateSet = new int[74];
        this.jjimage = new StringBuilder();
        this.image = this.jjimage;
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void ReInit(JavaCharStream stream) {
        this.jjmatchedPos = this.jjnewStateCnt = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = stream;
        this.ReInitRounds();
    }

    private void ReInitRounds() {
        this.jjround = -2147483647;

        for(int i = 37; i-- > 0; this.jjrounds[i] = Integer.MIN_VALUE) {
        }

    }

    public void ReInit(JavaCharStream stream, int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState < 1 && lexState >= 0) {
            this.curLexState = lexState;
        } else {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
    }
}
