call ../bncompiler.cmd -m java -o output/ldapv3 -ns ldapv3 -f ldapv3.asn
javac -cp ../../../../JavaLibrary/target/binarynotes-1.6.jar output/ldapv3/*.java
