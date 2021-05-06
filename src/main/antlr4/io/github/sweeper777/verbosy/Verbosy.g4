grammar Verbosy;

WS : [ \n\t]+ -> skip;

compilation_unit : instruction+ EOF;
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
  label_instruction;

INPUT_INSTRUCTION_TOKEN : 'i';
OUTPUT_INSTRUCTION_TOKEN : 'o';
SET_INSTRUCTION_START : '~';
ADD_INSTRUCTION_START : '+';
SUB_INSTRUCTION_START : '-';
INC_INSTRUCTION_START : '^';
DEC_INSTRUCTION_START : 'v';
TAKE_INSTRUCTION_START : '\\';
PUT_INSTRUCTION_START : '/';
GOTO_INSTRUCTION_START : '>';
GOTO_IF_0_INSTRUCTION_START : '>0';
GOTO_IF_NEG_INSTRUCTION_START : '>-';

INT : [0-9]+;
fragment SIGNED_INT : '-'? INT;
VERBOSY_VALUE : SIGNED_INT | CHARACTER;
fragment CHARACTER : ~[ \n\t] | HEX_ESCAPE;
fragment HEX_ESCAPE : '\\' [0-9a-fA-F]+;

LABEL_NAME : [a-zA-Z]+;
LABEL: ':' LABEL_NAME ':';

instruction_suffix: '*';

instruction_argument : INT;
set_argument : VERBOSY_VALUE;

input_instruction : INPUT_INSTRUCTION_TOKEN;
output_instruction : OUTPUT_INSTRUCTION_TOKEN;
set_instruction : SET_INSTRUCTION_START set_argument;
add_instruction : ADD_INSTRUCTION_START instruction_argument instruction_suffix?;
sub_instruction : SUB_INSTRUCTION_START instruction_argument instruction_suffix?;
inc_instruction : INC_INSTRUCTION_START instruction_argument instruction_suffix?;
dec_instruction : DEC_INSTRUCTION_START instruction_argument instruction_suffix?;
put_instruction : PUT_INSTRUCTION_START instruction_argument instruction_suffix?;
take_instruction : TAKE_INSTRUCTION_START instruction_argument instruction_suffix?;
goto_instruction : GOTO_INSTRUCTION_START LABEL_NAME;
goto_if_0_instruction : GOTO_IF_0_INSTRUCTION_START LABEL_NAME;
goto_if_neg_instruction : GOTO_IF_NEG_INSTRUCTION_START LABEL_NAME;
label_instruction : LABEL;