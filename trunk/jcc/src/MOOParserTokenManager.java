/* Generated By:JavaCC: Do not edit this line. MOOParserTokenManager.java */

/** Token Manager. */
public class MOOParserTokenManager implements MOOParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x420L) != 0L)
         {
            jjmatchedKind = 65;
            return 10;
         }
         if ((active0 & 0x8000000000L) != 0L)
            return 8;
         if ((active0 & 0x21000000000L) != 0L || (active1 & 0x10L) != 0L)
            return 1;
         if ((active1 & 0x1L) != 0L)
            return 15;
         if ((active0 & 0x3fffc0003ffebc0L) != 0L)
         {
            jjmatchedKind = 65;
            return 15;
         }
         return -1;
      case 1:
         if ((active0 & 0x3fffc0003ffebc0L) != 0L)
         {
            jjmatchedKind = 65;
            jjmatchedPos = 1;
            return 15;
         }
         if ((active0 & 0x420L) != 0L)
            return 15;
         return -1;
      case 2:
         if ((active0 & 0x1ff900001df69c0L) != 0L)
         {
            if (jjmatchedPos != 2)
            {
               jjmatchedKind = 65;
               jjmatchedPos = 2;
            }
            return 15;
         }
         if ((active0 & 0x2006c0002208200L) != 0L)
            return 15;
         return -1;
      case 3:
         if ((active0 & 0x1000001080c0L) != 0L)
            return 15;
         if ((active0 & 0x1ff800001cf6900L) != 0L)
         {
            if (jjmatchedPos != 3)
            {
               jjmatchedKind = 65;
               jjmatchedPos = 3;
            }
            return 15;
         }
         return -1;
      case 4:
         if ((active0 & 0x1fe800001cd4840L) != 0L)
         {
            jjmatchedKind = 65;
            jjmatchedPos = 4;
            return 15;
         }
         if ((active0 & 0x1000000022100L) != 0L)
            return 15;
         return -1;
      case 5:
         if ((active0 & 0x1be000001044000L) != 0L)
         {
            if (jjmatchedPos != 5)
            {
               jjmatchedKind = 65;
               jjmatchedPos = 5;
            }
            return 15;
         }
         if ((active0 & 0x40800000c90840L) != 0L)
            return 15;
         return -1;
      case 6:
         if ((active0 & 0x19c000000044000L) != 0L)
         {
            jjmatchedKind = 65;
            jjmatchedPos = 6;
            return 15;
         }
         if ((active0 & 0x22000001010000L) != 0L)
            return 15;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0, long active1)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0()
{
   switch(curChar)
   {
      case 33:
         return jjStartNfaWithStates_0(0, 68, 1);
      case 40:
         return jjStopAtPos(0, 28);
      case 41:
         return jjStopAtPos(0, 29);
      case 44:
         return jjStopAtPos(0, 34);
      case 45:
         return jjStopAtPos(0, 26);
      case 46:
         jjmatchedKind = 67;
         return jjMoveStringLiteralDfa1_0(0x1000L);
      case 58:
         return jjStopAtPos(0, 40);
      case 59:
         return jjStopAtPos(0, 35);
      case 61:
         jjmatchedKind = 36;
         return jjMoveStringLiteralDfa1_0(0x20000000000L);
      case 63:
         return jjStopAtPos(0, 38);
      case 64:
         return jjStopAtPos(0, 37);
      case 65:
         return jjMoveStringLiteralDfa1_0(0x200000000000000L);
      case 69:
         return jjMoveStringLiteralDfa1_0(0x1ffa00000000000L);
      case 76:
         return jjMoveStringLiteralDfa1_0(0x100000000000L);
      case 78:
         return jjMoveStringLiteralDfa1_0(0x40000000000L);
      case 79:
         return jjMoveStringLiteralDfa1_0(0x80000000000L);
      case 83:
         return jjMoveStringLiteralDfa1_0(0x400000000000L);
      case 91:
         return jjStopAtPos(0, 30);
      case 93:
         return jjStopAtPos(0, 31);
      case 95:
         return jjStartNfaWithStates_0(0, 64, 15);
      case 97:
         return jjMoveStringLiteralDfa1_0(0x2000000L);
      case 98:
         return jjMoveStringLiteralDfa1_0(0x20000L);
      case 99:
         return jjMoveStringLiteralDfa1_0(0x40000L);
      case 101:
         return jjMoveStringLiteralDfa1_0(0xc149c0L);
      case 102:
         return jjMoveStringLiteralDfa1_0(0x1008200L);
      case 105:
         return jjMoveStringLiteralDfa1_0(0x420L);
      case 112:
         return jjMoveStringLiteralDfa1_0(0x100000L);
      case 114:
         return jjMoveStringLiteralDfa1_0(0x80000L);
      case 116:
         return jjMoveStringLiteralDfa1_0(0x200000L);
      case 119:
         return jjMoveStringLiteralDfa1_0(0x2000L);
      case 123:
         return jjStopAtPos(0, 32);
      case 124:
         return jjStartNfaWithStates_0(0, 39, 8);
      case 125:
         return jjStopAtPos(0, 33);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0, 0L);
      return 1;
   }
   switch(curChar)
   {
      case 46:
         if ((active0 & 0x1000L) != 0L)
            return jjStopAtPos(1, 12);
         break;
      case 62:
         if ((active0 & 0x20000000000L) != 0L)
            return jjStopAtPos(1, 41);
         break;
      case 66:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000000000L);
      case 73:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000000000L);
      case 76:
         return jjMoveStringLiteralDfa2_0(active0, 0x200000000000000L);
      case 82:
         return jjMoveStringLiteralDfa2_0(active0, 0x200000000000L);
      case 84:
         return jjMoveStringLiteralDfa2_0(active0, 0x400000000000L);
      case 85:
         return jjMoveStringLiteralDfa2_0(active0, 0x40000000000L);
      case 95:
         return jjMoveStringLiteralDfa2_0(active0, 0x1ff800000000000L);
      case 97:
         return jjMoveStringLiteralDfa2_0(active0, 0x100000L);
      case 101:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000L);
      case 102:
         if ((active0 & 0x20L) != 0L)
            return jjStartNfaWithStates_0(1, 5, 15);
         break;
      case 104:
         return jjMoveStringLiteralDfa2_0(active0, 0x2000L);
      case 105:
         return jjMoveStringLiteralDfa2_0(active0, 0x1000000L);
      case 108:
         return jjMoveStringLiteralDfa2_0(active0, 0xc0L);
      case 110:
         if ((active0 & 0x400L) != 0L)
            return jjStartNfaWithStates_0(1, 10, 15);
         return jjMoveStringLiteralDfa2_0(active0, 0x2414900L);
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x48200L);
      case 114:
         return jjMoveStringLiteralDfa2_0(active0, 0x220000L);
      case 120:
         return jjMoveStringLiteralDfa2_0(active0, 0x800000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0, 0L);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0, 0L);
      return 2;
   }
   switch(curChar)
   {
      case 68:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000000000000L);
      case 73:
         return jjMoveStringLiteralDfa3_0(active0, 0x84000000000000L);
      case 74:
         if ((active0 & 0x80000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 43, 15);
         break;
      case 76:
         if ((active0 & 0x200000000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 57, 15);
         break;
      case 77:
         if ((active0 & 0x40000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 42, 15);
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000000000L);
      case 80:
         return jjMoveStringLiteralDfa3_0(active0, 0x50000000000000L);
      case 82:
         if ((active0 & 0x200000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 45, 15);
         else if ((active0 & 0x400000000000L) != 0L)
            return jjStartNfaWithStates_0(2, 46, 15);
         return jjMoveStringLiteralDfa3_0(active0, 0x2000000000000L);
      case 83:
         return jjMoveStringLiteralDfa3_0(active0, 0x100000000000L);
      case 84:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000000000L);
      case 86:
         return jjMoveStringLiteralDfa3_0(active0, 0x28000000000000L);
      case 99:
         return jjMoveStringLiteralDfa3_0(active0, 0x800000L);
      case 100:
         return jjMoveStringLiteralDfa3_0(active0, 0x414900L);
      case 101:
         return jjMoveStringLiteralDfa3_0(active0, 0x20000L);
      case 105:
         return jjMoveStringLiteralDfa3_0(active0, 0x2000L);
      case 110:
         return jjMoveStringLiteralDfa3_0(active0, 0x1040000L);
      case 114:
         if ((active0 & 0x200L) != 0L)
         {
            jjmatchedKind = 9;
            jjmatchedPos = 2;
         }
         return jjMoveStringLiteralDfa3_0(active0, 0x8000L);
      case 115:
         return jjMoveStringLiteralDfa3_0(active0, 0x1000c0L);
      case 116:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000L);
      case 121:
         if ((active0 & 0x200000L) != 0L)
            return jjStartNfaWithStates_0(2, 21, 15);
         else if ((active0 & 0x2000000L) != 0L)
            return jjStartNfaWithStates_0(2, 25, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0, 0L);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0, 0L);
      return 3;
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa4_0(active0, 0x122000000000000L);
      case 69:
         return jjMoveStringLiteralDfa4_0(active0, 0x48000000000000L);
      case 73:
         return jjMoveStringLiteralDfa4_0(active0, 0x1000000000000L);
      case 78:
         return jjMoveStringLiteralDfa4_0(active0, 0x84000000000000L);
      case 82:
         return jjMoveStringLiteralDfa4_0(active0, 0x10000000000000L);
      case 84:
         if ((active0 & 0x100000000000L) != 0L)
            return jjStartNfaWithStates_0(3, 44, 15);
         break;
      case 89:
         return jjMoveStringLiteralDfa4_0(active0, 0x800000000000L);
      case 97:
         return jjMoveStringLiteralDfa4_0(active0, 0x1020000L);
      case 101:
         if ((active0 & 0x80L) != 0L)
         {
            jjmatchedKind = 7;
            jjmatchedPos = 3;
         }
         return jjMoveStringLiteralDfa4_0(active0, 0x800040L);
      case 102:
         return jjMoveStringLiteralDfa4_0(active0, 0x10800L);
      case 105:
         return jjMoveStringLiteralDfa4_0(active0, 0x100L);
      case 107:
         if ((active0 & 0x8000L) != 0L)
            return jjStartNfaWithStates_0(3, 15, 15);
         break;
      case 108:
         return jjMoveStringLiteralDfa4_0(active0, 0x2000L);
      case 115:
         if ((active0 & 0x100000L) != 0L)
            return jjStartNfaWithStates_0(3, 20, 15);
         break;
      case 116:
         return jjMoveStringLiteralDfa4_0(active0, 0x440000L);
      case 117:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000L);
      case 119:
         return jjMoveStringLiteralDfa4_0(active0, 0x4000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0, 0L);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0, 0L);
      return 4;
   }
   switch(curChar)
   {
      case 78:
         return jjMoveStringLiteralDfa5_0(active0, 0x2000000000000L);
      case 79:
         return jjMoveStringLiteralDfa5_0(active0, 0x10000000000000L);
      case 80:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000000000L);
      case 82:
         return jjMoveStringLiteralDfa5_0(active0, 0x68000000000000L);
      case 86:
         if ((active0 & 0x1000000000000L) != 0L)
            return jjStartNfaWithStates_0(4, 48, 15);
         return jjMoveStringLiteralDfa5_0(active0, 0x84000000000000L);
      case 88:
         return jjMoveStringLiteralDfa5_0(active0, 0x100000000000000L);
      case 101:
         if ((active0 & 0x2000L) != 0L)
            return jjStartNfaWithStates_0(4, 13, 15);
         break;
      case 102:
         if ((active0 & 0x100L) != 0L)
            return jjStartNfaWithStates_0(4, 8, 15);
         break;
      case 104:
         return jjMoveStringLiteralDfa5_0(active0, 0x4000L);
      case 105:
         return jjMoveStringLiteralDfa5_0(active0, 0x40040L);
      case 107:
         if ((active0 & 0x20000L) != 0L)
            return jjStartNfaWithStates_0(4, 17, 15);
         break;
      case 108:
         return jjMoveStringLiteralDfa5_0(active0, 0x1000000L);
      case 111:
         return jjMoveStringLiteralDfa5_0(active0, 0x10800L);
      case 112:
         return jjMoveStringLiteralDfa5_0(active0, 0x800000L);
      case 114:
         return jjMoveStringLiteralDfa5_0(active0, 0x480000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0, 0L);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0, 0L);
      return 5;
   }
   switch(curChar)
   {
      case 65:
         return jjMoveStringLiteralDfa6_0(active0, 0x4000000000000L);
      case 66:
         return jjMoveStringLiteralDfa6_0(active0, 0x8000000000000L);
      case 69:
         if ((active0 & 0x800000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 47, 15);
         break;
      case 71:
         return jjMoveStringLiteralDfa6_0(active0, 0x2000000000000L);
      case 73:
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000000000L);
      case 77:
         if ((active0 & 0x40000000000000L) != 0L)
            return jjStartNfaWithStates_0(5, 54, 15);
         break;
      case 78:
         return jjMoveStringLiteralDfa6_0(active0, 0x20000000000000L);
      case 80:
         return jjMoveStringLiteralDfa6_0(active0, 0x10000000000000L);
      case 82:
         return jjMoveStringLiteralDfa6_0(active0, 0x100000000000000L);
      case 102:
         if ((active0 & 0x40L) != 0L)
            return jjStartNfaWithStates_0(5, 6, 15);
         break;
      case 105:
         return jjMoveStringLiteralDfa6_0(active0, 0x4000L);
      case 108:
         return jjMoveStringLiteralDfa6_0(active0, 0x1000000L);
      case 110:
         if ((active0 & 0x80000L) != 0L)
            return jjStartNfaWithStates_0(5, 19, 15);
         return jjMoveStringLiteralDfa6_0(active0, 0x40000L);
      case 114:
         if ((active0 & 0x800L) != 0L)
         {
            jjmatchedKind = 11;
            jjmatchedPos = 5;
         }
         return jjMoveStringLiteralDfa6_0(active0, 0x10000L);
      case 116:
         if ((active0 & 0x800000L) != 0L)
            return jjStartNfaWithStates_0(5, 23, 15);
         break;
      case 121:
         if ((active0 & 0x400000L) != 0L)
            return jjStartNfaWithStates_0(5, 22, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(4, active0, 0L);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0, 0L);
      return 6;
   }
   switch(curChar)
   {
      case 69:
         if ((active0 & 0x2000000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 49, 15);
         return jjMoveStringLiteralDfa7_0(active0, 0x100000000000000L);
      case 70:
         if ((active0 & 0x20000000000000L) != 0L)
            return jjStartNfaWithStates_0(6, 53, 15);
         break;
      case 78:
         return jjMoveStringLiteralDfa7_0(active0, 0x98000000000000L);
      case 82:
         return jjMoveStringLiteralDfa7_0(active0, 0x4000000000000L);
      case 107:
         if ((active0 & 0x10000L) != 0L)
            return jjStartNfaWithStates_0(6, 16, 15);
         break;
      case 108:
         return jjMoveStringLiteralDfa7_0(active0, 0x4000L);
      case 117:
         return jjMoveStringLiteralDfa7_0(active0, 0x40000L);
      case 121:
         if ((active0 & 0x1000000L) != 0L)
            return jjStartNfaWithStates_0(6, 24, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(5, active0, 0L);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0, 0L);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0, 0L);
      return 7;
   }
   switch(curChar)
   {
      case 67:
         if ((active0 & 0x100000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 56, 15);
         break;
      case 68:
         if ((active0 & 0x80000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 55, 15);
         break;
      case 70:
         if ((active0 & 0x8000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 51, 15);
         else if ((active0 & 0x10000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 52, 15);
         break;
      case 71:
         if ((active0 & 0x4000000000000L) != 0L)
            return jjStartNfaWithStates_0(7, 50, 15);
         break;
      case 101:
         if ((active0 & 0x4000L) != 0L)
            return jjStartNfaWithStates_0(7, 14, 15);
         else if ((active0 & 0x40000L) != 0L)
            return jjStartNfaWithStates_0(7, 18, 15);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0, 0L);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 24;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 10:
               case 15:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(15);
                  break;
               case 0:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 60)
                        kind = 60;
                     jjCheckNAddStates(0, 2);
                  }
                  else if ((0x5000ac2000000000L & l) != 0L)
                  {
                     if (kind > 27)
                        kind = 27;
                  }
                  else if (curChar == 34)
                     jjCheckNAddTwoStates(17, 18);
                  else if (curChar == 35)
                     jjCheckNAdd(13);
                  else if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 6;
                  else if (curChar == 33)
                     jjCheckNAdd(1);
                  else if (curChar == 61)
                     jjCheckNAdd(1);
                  if (curChar == 62)
                     jjCheckNAdd(1);
                  else if (curChar == 60)
                     jjCheckNAdd(1);
                  break;
               case 1:
                  if (curChar == 61 && kind > 27)
                     kind = 27;
                  break;
               case 2:
                  if (curChar == 60)
                     jjCheckNAdd(1);
                  break;
               case 3:
                  if (curChar == 62)
                     jjCheckNAdd(1);
                  break;
               case 4:
                  if (curChar == 61)
                     jjCheckNAdd(1);
                  break;
               case 5:
                  if (curChar == 33)
                     jjCheckNAdd(1);
                  break;
               case 6:
                  if (curChar == 38 && kind > 27)
                     kind = 27;
                  break;
               case 7:
                  if (curChar == 38)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 12:
                  if (curChar == 35)
                     jjCheckNAdd(13);
                  break;
               case 13:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 62)
                     kind = 62;
                  jjCheckNAdd(13);
                  break;
               case 16:
                  if (curChar == 34)
                     jjCheckNAddTwoStates(17, 18);
                  break;
               case 17:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     jjCheckNAddTwoStates(17, 18);
                  break;
               case 18:
                  if (curChar == 34 && kind > 66)
                     kind = 66;
                  break;
               case 19:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  jjCheckNAddStates(0, 2);
                  break;
               case 20:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 60)
                     kind = 60;
                  jjCheckNAdd(20);
                  break;
               case 21:
                  if ((0x3ff000000000000L & l) != 0L)
                     jjCheckNAddTwoStates(21, 22);
                  break;
               case 22:
                  if (curChar != 46)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAdd(23);
                  break;
               case 23:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 61)
                     kind = 61;
                  jjCheckNAdd(23);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 10:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 65)
                        kind = 65;
                     jjCheckNAdd(15);
                  }
                  if (curChar == 110)
                  {
                     if (kind > 27)
                        kind = 27;
                  }
                  break;
               case 0:
                  if ((0x7fffffe87fffffeL & l) != 0L)
                  {
                     if (kind > 65)
                        kind = 65;
                     jjCheckNAdd(15);
                  }
                  else if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 8;
                  else if (curChar == 94)
                  {
                     if (kind > 27)
                        kind = 27;
                  }
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 8:
                  if (curChar == 124 && kind > 27)
                     kind = 27;
                  break;
               case 9:
                  if (curChar == 124)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 11:
                  if (curChar == 105)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 14:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(15);
                  break;
               case 15:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 65)
                     kind = 65;
                  jjCheckNAdd(15);
                  break;
               case 17:
                  jjAddStates(3, 4);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 17:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     jjAddStates(3, 4);
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 24 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   20, 21, 22, 17, 18, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, "\151\146", "\145\154\163\145\151\146", 
"\145\154\163\145", "\145\156\144\151\146", "\146\157\162", "\151\156", 
"\145\156\144\146\157\162", "\56\56", "\167\150\151\154\145", "\145\156\144\167\150\151\154\145", 
"\146\157\162\153", "\145\156\144\146\157\162\153", "\142\162\145\141\153", 
"\143\157\156\164\151\156\165\145", "\162\145\164\165\162\156", "\160\141\163\163", "\164\162\171", 
"\145\156\144\164\162\171", "\145\170\143\145\160\164", "\146\151\156\141\154\154\171", "\141\156\171", 
"\55", null, "\50", "\51", "\133", "\135", "\173", "\175", "\54", "\73", "\75", 
"\100", "\77", "\174", "\72", "\75\76", "\116\125\115", "\117\102\112", 
"\114\111\123\124", "\105\122\122", "\123\124\122", "\105\137\124\131\120\105", 
"\105\137\104\111\126", "\105\137\122\101\116\107\105", "\105\137\111\116\126\101\122\107", 
"\105\137\126\105\122\102\116\106", "\105\137\120\122\117\120\116\106", "\105\137\126\101\122\116\106", 
"\105\137\120\105\122\115", "\105\137\111\116\126\111\116\104", "\105\137\115\101\130\122\105\103", 
"\101\114\114", null, null, null, null, null, null, "\137", null, null, "\56", "\41", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};
static final long[] jjtoToken = {
   0x73ffffffffffffe1L, 0x1fL, 
};
static final long[] jjtoSkip = {
   0x1eL, 0x0L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[24];
private final int[] jjstateSet = new int[48];
protected char curChar;
/** Constructor. */
public MOOParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public MOOParserTokenManager(SimpleCharStream stream, int lexState){
   this(stream);
   SwitchTo(lexState);
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream)
{
   jjmatchedPos = jjnewStateCnt = 0;
   curLexState = defaultLexState;
   input_stream = stream;
   ReInitRounds();
}
private void ReInitRounds()
{
   int i;
   jjround = 0x80000001;
   for (i = 24; i-- > 0;)
      jjrounds[i] = 0x80000000;
}

/** Reinitialise parser. */
public void ReInit(SimpleCharStream stream, int lexState)
{
   ReInit(stream);
   SwitchTo(lexState);
}

/** Switch to specified lex state. */
public void SwitchTo(int lexState)
{
   if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
   else
      curLexState = lexState;
}

protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(java.io.IOException e)
   {
      jjmatchedKind = 0;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   try { input_stream.backup(0);
      while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
         curChar = input_stream.BeginToken();
   }
   catch (java.io.IOException e1) { continue EOFLoop; }
   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

}