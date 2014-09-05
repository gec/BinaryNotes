call ../bncompiler.cmd -m java -o output/ldapv3 -ns ldapv3 -mp ../modules -f ldapv3.asn
javac -cp ../../../../JavaLibrary/target/binarynotes-1.5.3.jar output/ldapv3/*.java
