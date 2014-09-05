mkdir output\test
call ../bncompiler.cmd -m java -o output/test -ns org.bn.coders.test_asn -mp ../modules -f test.asn
javac -cp ../../../../JavaLibrary/target/binarynotes-1.5.3.jar output/test/*.java
