.PHONY: compile run test fmt lint clean

compile:
	cd $(module) && mvn -DskipTests compile

run: compile
	mkdir -p $(module)/run
	cd $(module)/run && mvn -f ../pom.xml exec:java

test:
	cd $(module) && mvn test

fmt:
	mvn spotless:apply

lint:
	mvn checkstyle:check

clean:
	mvn clean
