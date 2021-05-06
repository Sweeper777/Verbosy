grammar Verbosy;

GOTO0 : '>0';
GOTO_MINUS : '>-';
I : 'i';
O : 'o';
TILDE : '~';
PLUS : '+';
MINUS: '-';
INC : '^';
DEC : 'v';
BACKSLASH : '\\\\';
SLASH : '/';
GOTO : '>';

WS : [ \n\r\t]+;
DIGIT : [0-9];
HEX_DIGIT : [a-fA-F];
LETTER : [a-zA-Z];
CHAR: ~[ \n\r\t0123456789];

compilation_unit : instruction_with_terminal+;

instruction_with_terminal : instruction (WS | EOF);

instruction :
  input_instruction |
  output_instruction |
  set_instruction |
  add_instruction |
  sub_instruction |
  inc_instruction |
  dec_instruction |
  put_instruction |
  take_instruction |
  goto_instruction |
  goto_if_0_instruction |
  goto_if_neg_instruction |
  label_instruction
  ;

unsigned_int : DIGIT+;
signed_int : MINUS unsigned_int | unsigned_int;
character : hex_escape | CHAR | LETTER |
  I | O | TILDE | PLUS | MINUS | INC | DEC | BACKSLASH | SLASH | GOTO;
hex_escape : BACKSLASH (DIGIT | HEX_DIGIT)+;

label_name : (LETTER | HEX_DIGIT)+;
label: ':' label_name ':';

instruction_suffix: '*';

instruction_argument : unsigned_int;
set_integer_argument : signed_int;
set_character_argument: character;

input_instruction : I;
output_instruction : O;
set_instruction : TILDE set_character_argument |
                  TILDE set_integer_argument;
add_instruction : PLUS instruction_argument instruction_suffix?;
sub_instruction : MINUS instruction_argument instruction_suffix?;
inc_instruction : INC instruction_argument instruction_suffix?;
dec_instruction : DEC instruction_argument instruction_suffix?;
put_instruction : SLASH instruction_argument instruction_suffix?;
take_instruction : BACKSLASH instruction_argument instruction_suffix?;
goto_instruction : GOTO label_name;
goto_if_0_instruction : GOTO0 label_name;
goto_if_neg_instruction : GOTO_MINUS label_name;
label_instruction : label;