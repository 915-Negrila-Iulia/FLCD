%{
#include <stdio.h>
#include <stdlib.h>
#define YYDEBUG 1
%}

%token INT
%token STRING
%token CHAR
%token IF
%token READ
%token WRITE
%token AND
%token OR

%token ID
%token CONST

%token ADD
%token SUB
%token MUL
%token DIV
%token ASIGN
%token EQ
%token NE
%token LESS
%token GREATER
%token RIGHTSHIFT
%token LEFTSHIFT

%token DO
%token DONE
%token SEMICOLON

%%

program : decllist | decllist program | stmtlist | stmtlist program
decllist : declaration | declaration decllist
declaration : type ID SEMICOLON
type : INT | CHAR | STRING
stmtlist : stmt | stmt stmtlist
stmt : assignstmt | iostmt | ifstmt
ifstmt : IF conditionlist DO stmtlist DONE SEMICOLON
conditionlist : condition | condition AND condition | condition OR condition
condition : ID | ID relation ID | CONST | CONST relation CONST | ID relation CONST | CONST relation ID
relation : EQ | NE | LESS | GREATER
iostmt : inputstmt | outputstmt
inputstmt : READ RIGHTSHIFT ID SEMICOLON
outputstmt : WRITE LEFTSHIFT ID SEMICOLON | WRITE LEFTSHIFT CONST SEMICOLON 
assignstmt : ID ASIGN expr SEMICOLON
expr : ID | CONST | ID operation CONST | CONST operation CONST | ID operation ID | CONST operation ID
operation : ADD | SUB | MUL | DIV     

%%

yyerror(char *s){
	printf("%s\n",s);
}

extern FILE *yyin;

main(int argc, char **argv){
	if(argc > 1) yyin : fopen(argv[1],"r");
	if(argc > 2 && !strcmp(argv[2],"-d")) yydebug: 1;
	if(!yyparse()) fprintf(stderr, "Syntax error\n"); 
}



