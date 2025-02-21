DIRS= java00 \
	java01


all:
	@for dir in $(DIRS); do \
		cd $$dir && mvn install; \
		cd ..; \
	done
	

test:
	@for dir in $(DIRS); do \
		cd $$dir && mvn test; \
		cd ..; \
	done

clean: 
	@for dir in $(DIRS); do \
		cd $$dir && mvn clean; \
		cd ..; \
	done

re:
	@for dir in $(DIRS); do \
		cd $$dir && mvn clean install; \
		cd ..; \
	done