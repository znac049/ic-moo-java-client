package uk.org.wookey.IC.MOOLanguage;

import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.RSyntaxUtilities;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenMap;

public class MOOTokenMaker extends AbstractTokenMaker {
	private int currentTokenType;
	private int currentTokenStart;
	
	@Override
	public void addToken(Segment segment, int start, int end, int tokenType, int startOffset) {
	   // This assumes all keywords, etc. were parsed as "identifiers."
	   if (tokenType==Token.IDENTIFIER) {
	      int value = wordsToHighlight.get(segment, start, end);
	      if (value != -1) {
	         tokenType = value;
	      }
	   }
	   super.addToken(segment, start, end, tokenType, startOffset);
	}

	@Override
	public TokenMap getWordsToHighlight() {
		TokenMap tokenMap = new TokenMap();
		   
		tokenMap.put("for",   Token.RESERVED_WORD);
		tokenMap.put("..",   Token.RESERVED_WORD);
		tokenMap.put("endfor",   Token.RESERVED_WORD);
		
		tokenMap.put("in",   Token.RESERVED_WORD);
		
		tokenMap.put("if",    Token.RESERVED_WORD);
		tokenMap.put("elseif",   Token.RESERVED_WORD);
		tokenMap.put("else",   Token.RESERVED_WORD);
		tokenMap.put("endif",   Token.RESERVED_WORD);

		tokenMap.put("while",   Token.RESERVED_WORD);
		tokenMap.put("endwhile",   Token.RESERVED_WORD);

		tokenMap.put("break",   Token.RESERVED_WORD);
		tokenMap.put("continue",   Token.RESERVED_WORD);
		
		tokenMap.put("try",   Token.RESERVED_WORD);
		tokenMap.put("except",   Token.RESERVED_WORD);
		tokenMap.put("finally",   Token.RESERVED_WORD);
		tokenMap.put("endtry",   Token.RESERVED_WORD);
		
		tokenMap.put("fork",   Token.RESERVED_WORD);
		tokenMap.put("endfork",   Token.RESERVED_WORD);
		tokenMap.put("return", Token.RESERVED_WORD);
		  
		tokenMap.put("connected_players",  Token.FUNCTION);
		tokenMap.put("pass",  Token.FUNCTION);
		tokenMap.put("players", Token.FUNCTION);
		tokenMap.put("typeof",  Token.FUNCTION);
		tokenMap.put("toobj",  Token.FUNCTION);
		tokenMap.put("tostr",  Token.FUNCTION);
		tokenMap.put("toliteral",  Token.FUNCTION);
		tokenMap.put("toint",  Token.FUNCTION);
		tokenMap.put("tonum",  Token.FUNCTION);
		tokenMap.put("tofloat",  Token.FUNCTION);
		tokenMap.put("equal",  Token.FUNCTION);
		tokenMap.put("value_bytes",  Token.FUNCTION);
		tokenMap.put("value_hash",  Token.FUNCTION);
		tokenMap.put("random",  Token.FUNCTION);
		tokenMap.put("min",  Token.FUNCTION);
		tokenMap.put("max",  Token.FUNCTION);
		tokenMap.put("abs",  Token.FUNCTION);
		tokenMap.put("floatstr",  Token.FUNCTION);
		tokenMap.put("sqrt",  Token.FUNCTION);
		tokenMap.put("sin",  Token.FUNCTION);
		tokenMap.put("cos",  Token.FUNCTION);
		tokenMap.put("tan",  Token.FUNCTION);
		tokenMap.put("asin",  Token.FUNCTION);
		tokenMap.put("acos",  Token.FUNCTION);
		tokenMap.put("atan",  Token.FUNCTION);
		tokenMap.put("sinh",  Token.FUNCTION);
		tokenMap.put("cosh",  Token.FUNCTION);
		tokenMap.put("tanh",  Token.FUNCTION);
		tokenMap.put("exp",  Token.FUNCTION);
		tokenMap.put("log",  Token.FUNCTION);
		tokenMap.put("log10",  Token.FUNCTION);
		tokenMap.put("ceil",  Token.FUNCTION);
		tokenMap.put("floor",  Token.FUNCTION);
		tokenMap.put("trunc",  Token.FUNCTION);
		tokenMap.put("length",  Token.FUNCTION);
		tokenMap.put("strsub",  Token.FUNCTION);
		tokenMap.put("index",  Token.FUNCTION);
		tokenMap.put("rindex",  Token.FUNCTION);
		tokenMap.put("strcmp",  Token.FUNCTION);
		tokenMap.put("decode_binary",  Token.FUNCTION);
		tokenMap.put("encode_binary",  Token.FUNCTION);
		tokenMap.put("match",  Token.FUNCTION);
		tokenMap.put("rmatch",  Token.FUNCTION);
		tokenMap.put("substitute",  Token.FUNCTION);
		tokenMap.put("crypt",  Token.FUNCTION);
		tokenMap.put("string_hash",  Token.FUNCTION);
		tokenMap.put("binary_hash",  Token.FUNCTION);
		tokenMap.put("length",  Token.FUNCTION);
		tokenMap.put("is_member",  Token.FUNCTION);
		tokenMap.put("listinsert",  Token.FUNCTION);
		tokenMap.put("listappend",  Token.FUNCTION);
		tokenMap.put("listdelete",  Token.FUNCTION);
		tokenMap.put("listset",  Token.FUNCTION);
		tokenMap.put("setadd",  Token.FUNCTION);
		tokenMap.put("setremove",  Token.FUNCTION);
		tokenMap.put("create",  Token.FUNCTION);
		tokenMap.put("chparent",  Token.FUNCTION);
		tokenMap.put("valid",  Token.FUNCTION);
		tokenMap.put("parent",  Token.FUNCTION);
		tokenMap.put("children",  Token.FUNCTION);
		tokenMap.put("recycle",  Token.FUNCTION);
		tokenMap.put("object_bytes",  Token.FUNCTION);
		tokenMap.put("max_object",  Token.FUNCTION);
		tokenMap.put("move",  Token.FUNCTION);
		tokenMap.put("properties",  Token.FUNCTION);
		tokenMap.put("property_info",  Token.FUNCTION);
		tokenMap.put("set_property_info",  Token.FUNCTION);
		tokenMap.put("add_property",  Token.FUNCTION);
		tokenMap.put("delete_property",  Token.FUNCTION);
		tokenMap.put("is_clear_property",  Token.FUNCTION);
		tokenMap.put("clear_property",  Token.FUNCTION);
		tokenMap.put("verbs",  Token.FUNCTION);
		tokenMap.put("verb_info",  Token.FUNCTION);
		tokenMap.put("set_verb_info",  Token.FUNCTION);
		tokenMap.put("verb_args",  Token.FUNCTION);
		tokenMap.put("set_verb_args",  Token.FUNCTION);
		tokenMap.put("add_verb",  Token.FUNCTION);
		tokenMap.put("delete_verb",  Token.FUNCTION);
		tokenMap.put("verb_code",  Token.FUNCTION);
		tokenMap.put("set_verb_code",  Token.FUNCTION);
		tokenMap.put("disassemble",  Token.FUNCTION);
		tokenMap.put("is_player",  Token.FUNCTION);
		tokenMap.put("set_player_flag",  Token.FUNCTION);
		tokenMap.put("connected_seconds",  Token.FUNCTION);
		tokenMap.put("idle_seconds",  Token.FUNCTION);
		tokenMap.put("notify",  Token.FUNCTION);
		tokenMap.put("buffered_output_length",  Token.FUNCTION);
		tokenMap.put("read",  Token.FUNCTION);
		tokenMap.put("force_input",  Token.FUNCTION);
		tokenMap.put("flush_input",  Token.FUNCTION);
		tokenMap.put("output_delimeters",  Token.FUNCTION);
		tokenMap.put("boot_player",  Token.FUNCTION);
		tokenMap.put("connection_name",  Token.FUNCTION);
		tokenMap.put("set_connection_option",  Token.FUNCTION);
		tokenMap.put("connection_options",  Token.FUNCTION);
		tokenMap.put("connection_option",  Token.FUNCTION);
		tokenMap.put("open_network_connection",  Token.FUNCTION);
		tokenMap.put("listen",  Token.FUNCTION);
		tokenMap.put("unlisten",  Token.FUNCTION);
		tokenMap.put("listeners",  Token.FUNCTION);
		tokenMap.put("time",  Token.FUNCTION);
		tokenMap.put("ctime",  Token.FUNCTION);
		tokenMap.put("raise",  Token.FUNCTION);
		tokenMap.put("call_function",  Token.FUNCTION);
		tokenMap.put("function_info",  Token.FUNCTION);
		tokenMap.put("eval",  Token.FUNCTION);
		tokenMap.put("set_task_perms",  Token.FUNCTION);
		tokenMap.put("caller_perms",  Token.FUNCTION);
		tokenMap.put("ticks_left",  Token.FUNCTION);
		tokenMap.put("seconds_left",  Token.FUNCTION);
		tokenMap.put("task_id",  Token.FUNCTION);
		tokenMap.put("suspend",  Token.FUNCTION);
		tokenMap.put("resume",  Token.FUNCTION);
		tokenMap.put("queue_info",  Token.FUNCTION);
		tokenMap.put("queued_tasks",  Token.FUNCTION);
		tokenMap.put("kill_task",  Token.FUNCTION);
		tokenMap.put("callers",  Token.FUNCTION);
		tokenMap.put("task_stack",  Token.FUNCTION);
		tokenMap.put("server_version",  Token.FUNCTION);
		tokenMap.put("server_log",  Token.FUNCTION);
		tokenMap.put("renumber",  Token.FUNCTION);
		tokenMap.put("reset_max_object",  Token.FUNCTION);
		tokenMap.put("memory_usage",  Token.FUNCTION);
		tokenMap.put("dump_database",  Token.FUNCTION);
		tokenMap.put("db_disk_size",  Token.FUNCTION);
		tokenMap.put("shutdown",  Token.FUNCTION);
		   
		return tokenMap;
	}
	
	@Override
	public Token getTokenList(Segment text, int startTokenType, int startOffset) {
	   resetTokenList();

	   char[] array = text.array;
	   int offset = text.offset;
	   int count = text.count;
	   int end = offset + count;

	   // Token starting offsets are always of the form:
	   // 'startOffset + (currentTokenStart-offset)', but since startOffset and
	   // offset are constant, tokens' starting positions become:
	   // 'newStartOffset+currentTokenStart'.
	   int newStartOffset = startOffset - offset;

	   currentTokenStart = offset;
	   currentTokenType  = startTokenType;

	   for (int i=offset; i<end; i++) {
	      char c = array[i];

	      switch (currentTokenType) {
	         case Token.NULL:
	            currentTokenStart = i;   // Starting a new token here.

	            switch (c) {
	               case ' ':
	               case '\t':
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case '"':
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               default:
	                  if (RSyntaxUtilities.isDigit(c)) {
	                     currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
	                     break;
	                  }
	                  else if (RSyntaxUtilities.isLetter(c) || c=='/' || c=='_') {
	                     currentTokenType = Token.IDENTIFIER;
	                     break;
	                  }
	                  
	                  // Anything not currently handled - mark as an identifier
	                  currentTokenType = Token.IDENTIFIER;
	                  break;
	            }
	            break;

	         case Token.WHITESPACE:
	            switch (c) {
	               case ' ':
	               case '\t':
	                  break;   // Still whitespace.

	               case '"':
	                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               default:   // Add the whitespace token and start anew.
	                  addToken(text, currentTokenStart,i-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;

	                  if (RSyntaxUtilities.isDigit(c)) {
	                     currentTokenType = Token.LITERAL_NUMBER_DECIMAL_INT;
	                     break;
	                  }
	                  else if (RSyntaxUtilities.isLetter(c) || c=='_') {
	                     currentTokenType = Token.IDENTIFIER;
	                     break;
	                  }

	                  // Anything not currently handled - mark as identifier
	                  currentTokenType = Token.IDENTIFIER;
	            }
	            break;

	         default: // Should never happen
	         case Token.IDENTIFIER:
	            switch (c) {
	               case ' ':
	               case '\t':
	                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case '"':
	                  addToken(text, currentTokenStart,i-1, Token.IDENTIFIER, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               default:
	                  if (RSyntaxUtilities.isLetterOrDigit(c) || c=='/' || c=='_') {
	                     break;   // Still an identifier of some type.
	                  }
	                  // Otherwise, we're still an identifier (?).
	            }

	            break;

	         case Token.LITERAL_NUMBER_DECIMAL_INT:
	            switch (c) {
	               case ' ':
	               case '\t':
	                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.WHITESPACE;
	                  break;

	               case '"':
	                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
	                  currentTokenStart = i;
	                  currentTokenType = Token.LITERAL_STRING_DOUBLE_QUOTE;
	                  break;

	               default:
	                  if (RSyntaxUtilities.isDigit(c)) {
	                     break;   // Still a literal number.
	                  }

	                  // Otherwise, remember this was a number and start over.
	                  addToken(text, currentTokenStart,i-1, Token.LITERAL_NUMBER_DECIMAL_INT, newStartOffset+currentTokenStart);
	                  i--;
	                  currentTokenType = Token.NULL;
	            }
	            break;

	         case Token.COMMENT_EOL:
	            i = end - 1;
	            addToken(text, currentTokenStart,i, currentTokenType, newStartOffset+currentTokenStart);
	            // We need to set token type to null so at the bottom we don't add one more token.
	            currentTokenType = Token.NULL;
	            break;

	         case Token.LITERAL_STRING_DOUBLE_QUOTE:
	            if (c=='"') {
	               addToken(text, currentTokenStart,i, Token.LITERAL_STRING_DOUBLE_QUOTE, newStartOffset+currentTokenStart);
	               currentTokenType = Token.NULL;
	            }
	            break;
	      }
	   }

	   switch (currentTokenType) {
	      // Remember what token type to begin the next line with.
	      case Token.LITERAL_STRING_DOUBLE_QUOTE:
	         addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
	         break;

	      // Do nothing if everything was okay.
	      case Token.NULL:
	         addNullToken();
	         break;

	      // All other token types don't continue to the next line...
	      default:
	         addToken(text, currentTokenStart,end-1, currentTokenType, newStartOffset+currentTokenStart);
	         addNullToken();
	   }

	   // Return the first token in our linked list.
	   return firstToken;
	}
}
