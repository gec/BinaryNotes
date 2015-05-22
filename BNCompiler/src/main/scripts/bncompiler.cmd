@echo off
set BN_HOME=%~dp0
set CMD_LINE_ARGS=%1
if ""%1""=="""" goto doneStart
shift
:setupArgs
	if ""%1""=="""" goto doneStart
	set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
	shift
goto setupArgs
:doneStart
java -jar "%BN_HOME%/bncompiler-1.6.jar" %CMD_LINE_ARGS%
