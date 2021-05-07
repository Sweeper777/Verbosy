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
BACKSLASH : '\\';
SLASH : '/';
GOTO : '>';

WS : [ \n\r\t]+;
DIGIT : [0-9];
HEX_DIGIT : [a-fA-F];
LETTER : [a-zA-Z];
CHAR: ~[ \n\r\t0123456789];

compilationUnit : instructionWithTerminal+;

instructionWithTerminal : instruction (WS | EOF);

instruction :
  inputInstruction |
  outputInstruction |
  setInstruction |
  addInstruction |
  subInstruction |
  incInstruction |
  decInstruction |
  putInstruction |
  takeInstruction |
  gotoInstruction |
  gotoIf_0Instruction |
  gotoIf_negInstruction |
  labelInstruction
  ;

unsignedInt : DIGIT+;
signedInt : MINUS unsignedInt | unsignedInt;
character : hexEscape | CHAR | LETTER |
  I | O | TILDE | PLUS | MINUS | INC | DEC | BACKSLASH | SLASH | GOTO;
hexEscape : BACKSLASH (DIGIT | HEX_DIGIT)+;

labelName : (LETTER | HEX_DIGIT)+;
label: ':' labelName ':';

instructionSuffix: '*';

instructionArgument : unsignedInt;

inputInstruction : I;
outputInstruction : O;
setInstruction : TILDE character |
                  TILDE signedInt;
addInstruction : PLUS instructionArgument instructionSuffix?;
subInstruction : MINUS instructionArgument instructionSuffix?;
incInstruction : INC instructionArgument instructionSuffix?;
decInstruction : DEC instructionArgument instructionSuffix?;
putInstruction : SLASH instructionArgument instructionSuffix?;
takeInstruction : BACKSLASH instructionArgument instructionSuffix?;
gotoInstruction : GOTO labelName;
gotoIf_0Instruction : GOTO0 labelName;
gotoIf_negInstruction : GOTO_MINUS labelName;
labelInstruction : label;