
all:
	cd java00 && mvn clean install

test:
	cd java00 && mvn test

clean: 
	cd java00 && mvn clean