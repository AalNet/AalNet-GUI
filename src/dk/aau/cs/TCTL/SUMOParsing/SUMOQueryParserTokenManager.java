/* Generated By:JavaCC: Do not edit this line. SUMOQueryParserTokenManager.java */
package dk.aau.cs.TCTL.SUMOParsing;

/** Token Manager. */
public class SUMOQueryParserTokenManager implements SUMOQueryParserConstants
{

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0)
{
   switch (pos)
   {
      case 0:
         if ((active0 & 0x18000L) != 0L)
         {
            jjmatchedKind = 23;
            return 37;
         }
         return -1;
      case 1:
         if ((active0 & 0x10000L) != 0L)
         {
            jjmatchedKind = 23;
            jjmatchedPos = 1;
            return -1;
         }
         return -1;
      case 2:
         if ((active0 & 0x10000L) != 0L)
         {
            if (jjmatchedPos < 1)
            {
               jjmatchedKind = 23;
               jjmatchedPos = 1;
            }
            return -1;
         }
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0)
{
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
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
         jjmatchedKind = 13;
         return jjMoveStringLiteralDfa1_0(0x1000000L);
      case 34:
         return jjStopAtPos(0, 19);
      case 35:
         return jjMoveStringLiteralDfa1_0(0x80000000L);
      case 38:
         return jjStopAtPos(0, 12);
      case 40:
         return jjStopAtPos(0, 29);
      case 41:
         return jjStopAtPos(0, 30);
      case 42:
         return jjStopAtPos(0, 18);
      case 44:
         return jjStopAtPos(0, 32);
      case 60:
         return jjMoveStringLiteralDfa1_0(0x10000L);
      case 61:
         return jjMoveStringLiteralDfa1_0(0x8000L);
      case 73:
         return jjStopAtPos(0, 8);
      case 78:
         return jjStopAtPos(0, 9);
      case 80:
         return jjStopAtPos(0, 10);
      case 120:
         return jjMoveStringLiteralDfa1_0(0x4000L);
      case 124:
         return jjStopAtPos(0, 11);
      default :
         return jjMoveNfa_0(3, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0)
{
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 61:
         if ((active0 & 0x1000000L) != 0L)
            return jjStopAtPos(1, 24);
         return jjMoveStringLiteralDfa2_0(active0, 0x10000L);
      case 62:
         if ((active0 & 0x8000L) != 0L)
            return jjStopAtPos(1, 15);
         break;
      case 111:
         return jjMoveStringLiteralDfa2_0(active0, 0x4000L);
      case 116:
         return jjMoveStringLiteralDfa2_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjMoveStringLiteralDfa2_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(0, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(1, active0);
      return 2;
   }
   switch(curChar)
   {
      case 62:
         if ((active0 & 0x10000L) != 0L)
            return jjStopAtPos(2, 16);
         break;
      case 111:
         return jjMoveStringLiteralDfa3_0(active0, 0x80000000L);
      case 114:
         if ((active0 & 0x4000L) != 0L)
            return jjStopAtPos(2, 14);
         break;
      default :
         break;
   }
   return jjStartNfa_0(1, active0);
}
private int jjMoveStringLiteralDfa3_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(1, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(2, active0);
      return 3;
   }
   switch(curChar)
   {
      case 107:
         return jjMoveStringLiteralDfa4_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjStartNfa_0(2, active0);
}
private int jjMoveStringLiteralDfa4_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(2, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(3, active0);
      return 4;
   }
   switch(curChar)
   {
      case 101:
         return jjMoveStringLiteralDfa5_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjStartNfa_0(3, active0);
}
private int jjMoveStringLiteralDfa5_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(3, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(4, active0);
      return 5;
   }
   switch(curChar)
   {
      case 110:
         return jjMoveStringLiteralDfa6_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjStartNfa_0(4, active0);
}
private int jjMoveStringLiteralDfa6_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(4, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(5, active0);
      return 6;
   }
   switch(curChar)
   {
      case 115:
         return jjMoveStringLiteralDfa7_0(active0, 0x80000000L);
      default :
         break;
   }
   return jjStartNfa_0(5, active0);
}
private int jjMoveStringLiteralDfa7_0(long old0, long active0)
{
   if (((active0 &= old0)) == 0L)
      return jjStartNfa_0(5, old0);
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(6, active0);
      return 7;
   }
   switch(curChar)
   {
      case 40:
         if ((active0 & 0x80000000L) != 0L)
            return jjStopAtPos(7, 31);
         break;
      default :
         break;
   }
   return jjStartNfa_0(6, active0);
}
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 51;
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
               case 3:
                  if ((0x3ff000000000000L & l) != 0L)
                  {
                     if (kind > 22)
                        kind = 22;
                     jjCheckNAdd(35);
                  }
                  else if ((0x7000000000000000L & l) != 0L)
                  {
                     if (kind > 23)
                        kind = 23;
                  }
                  else if ((0x280000000000L & l) != 0L)
                  {
                     if (kind > 17)
                        kind = 17;
                  }
                  if (curChar == 62)
                     jjCheckNAdd(37);
                  else if (curChar == 61)
                     jjCheckNAdd(37);
                  else if (curChar == 60)
                     jjCheckNAdd(37);
                  break;
               case 34:
                  if ((0x280000000000L & l) != 0L)
                     kind = 17;
                  break;
               case 35:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  kind = 22;
                  jjCheckNAdd(35);
                  break;
               case 36:
                  if ((0x7000000000000000L & l) != 0L && kind > 23)
                     kind = 23;
                  break;
               case 37:
                  if (curChar == 61 && kind > 23)
                     kind = 23;
                  break;
               case 38:
                  if (curChar == 60)
                     jjCheckNAdd(37);
                  break;
               case 39:
                  if (curChar == 61)
                     jjCheckNAdd(37);
                  break;
               case 40:
                  if (curChar == 62)
                     jjCheckNAdd(37);
                  break;
               case 42:
                  if (curChar == 32)
                     jjAddStates(0, 1);
                  break;
               case 44:
                  if (curChar == 32)
                     jjAddStates(2, 3);
                  break;
               case 47:
                  if (curChar == 32)
                     jjAddStates(4, 5);
                  break;
               case 49:
                  if (curChar == 32)
                     jjAddStates(6, 7);
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
               case 3:
                  if (curChar == 65)
                     jjAddStates(8, 11);
                  else if (curChar == 69)
                     jjAddStates(12, 15);
                  else if (curChar == 68)
                     jjstateSet[jjnewStateCnt++] = 32;
                  else if (curChar == 100)
                     jjstateSet[jjnewStateCnt++] = 24;
                  else if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 16;
                  else if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 11;
                  else if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 6;
                  else if (curChar == 116)
                     jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 0:
                  if (curChar == 101 && kind > 1)
                     kind = 1;
                  break;
               case 1:
                  if (curChar == 117)
                     jjstateSet[jjnewStateCnt++] = 0;
                  break;
               case 2:
                  if (curChar == 114)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 4:
                  if (curChar == 69 && kind > 1)
                     kind = 1;
                  break;
               case 5:
                  if (curChar == 85)
                     jjstateSet[jjnewStateCnt++] = 4;
                  break;
               case 6:
                  if (curChar == 82)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 7:
                  if (curChar == 84)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 8:
                  if (curChar == 101 && kind > 2)
                     kind = 2;
                  break;
               case 9:
                  if (curChar == 115)
                     jjstateSet[jjnewStateCnt++] = 8;
                  break;
               case 10:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 11:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 12:
                  if (curChar == 102)
                     jjstateSet[jjnewStateCnt++] = 11;
                  break;
               case 13:
                  if (curChar == 69 && kind > 2)
                     kind = 2;
                  break;
               case 14:
                  if (curChar == 83)
                     jjstateSet[jjnewStateCnt++] = 13;
                  break;
               case 15:
                  if (curChar == 76)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 16:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 17:
                  if (curChar == 70)
                     jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 18:
                  if (curChar == 107)
                     kind = 3;
                  break;
               case 19:
                  if (curChar == 99)
                     jjstateSet[jjnewStateCnt++] = 18;
                  break;
               case 20:
                  if (curChar == 111)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 21:
                  if (curChar == 108)
                     jjstateSet[jjnewStateCnt++] = 20;
                  break;
               case 22:
                  if (curChar == 100)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 23:
                  if (curChar == 97)
                     jjstateSet[jjnewStateCnt++] = 22;
                  break;
               case 24:
                  if (curChar == 101)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 25:
                  if (curChar == 100)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 26:
                  if (curChar == 75)
                     kind = 3;
                  break;
               case 27:
                  if (curChar == 67)
                     jjstateSet[jjnewStateCnt++] = 26;
                  break;
               case 28:
                  if (curChar == 79)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 29:
                  if (curChar == 76)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 30:
                  if (curChar == 68)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 31:
                  if (curChar == 65)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 32:
                  if (curChar == 69)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 33:
                  if (curChar == 68)
                     jjstateSet[jjnewStateCnt++] = 32;
                  break;
               case 41:
                  if (curChar == 69)
                     jjAddStates(12, 15);
                  break;
               case 43:
                  if (curChar == 70 && kind > 4)
                     kind = 4;
                  break;
               case 45:
                  if (curChar == 71 && kind > 5)
                     kind = 5;
                  break;
               case 46:
                  if (curChar == 65)
                     jjAddStates(8, 11);
                  break;
               case 48:
                  if (curChar == 70 && kind > 6)
                     kind = 6;
                  break;
               case 50:
                  if (curChar == 71 && kind > 7)
                     kind = 7;
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
      if ((i = jjnewStateCnt) == (startsAt = 51 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
private int jjMoveStringLiteralDfa0_1()
{
   return jjMoveNfa_1(0, 0);
}
private int jjMoveNfa_1(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 3;
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
               case 0:
                  if ((0x2000e00100000000L & l) != 0L)
                  {
                     if (kind > 21)
                        kind = 21;
                     jjCheckNAdd(2);
                  }
                  else if (curChar == 34)
                  {
                     if (kind > 20)
                        kind = 20;
                  }
                  break;
               case 1:
                  if ((0x2000e00100000000L & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(2);
                  break;
               case 2:
                  if ((0x23ffe00100000000L & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(2);
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
               case 0:
               case 2:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 21)
                     kind = 21;
                  jjCheckNAdd(2);
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
      if ((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}
static final int[] jjnextStates = {
   42, 43, 44, 45, 47, 48, 49, 50, 47, 48, 49, 50, 42, 43, 44, 45, 
};

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, "\111", "\116", "\120", "\174", 
"\46", "\41", "\170\157\162", "\75\76", "\74\75\76", null, "\52", "\42", null, null, 
null, null, "\41\75", null, null, null, null, "\50", "\51", 
"\43\164\157\153\145\156\163\50", "\54", };

/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
   "STRING_STATE",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 0, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0x1e1ffffffL, 
};
static final long[] jjtoSkip = {
   0x1e000000L, 
};
protected SimpleCharStream input_stream;
private final int[] jjrounds = new int[51];
private final int[] jjstateSet = new int[102];
protected char curChar;
/** Constructor. */
public SUMOQueryParserTokenManager(SimpleCharStream stream){
   if (SimpleCharStream.staticFlag)
      throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
   input_stream = stream;
}

/** Constructor. */
public SUMOQueryParserTokenManager(SimpleCharStream stream, int lexState){
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
   for (i = 51; i-- > 0;)
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
   if (lexState >= 2 || lexState < 0)
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

   switch(curLexState)
   {
     case 0:
       try { input_stream.backup(0);
          while (curChar <= 32 && (0x100002600L & (1L << curChar)) != 0L)
             curChar = input_stream.BeginToken();
       }
       catch (java.io.IOException e1) { continue EOFLoop; }
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_0();
       break;
     case 1:
       jjmatchedKind = 0x7fffffff;
       jjmatchedPos = 0;
       curPos = jjMoveStringLiteralDfa0_1();
       break;
   }
     if (jjmatchedKind != 0x7fffffff)
     {
        if (jjmatchedPos + 1 < curPos)
           input_stream.backup(curPos - jjmatchedPos - 1);
        if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
        {
           matchedToken = jjFillToken();
       if (jjnewLexState[jjmatchedKind] != -1)
         curLexState = jjnewLexState[jjmatchedKind];
           return matchedToken;
        }
        else
        {
         if (jjnewLexState[jjmatchedKind] != -1)
           curLexState = jjnewLexState[jjmatchedKind];
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

}
