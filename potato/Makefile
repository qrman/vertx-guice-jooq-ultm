build-all:
	mvn clean install

run:
	java -jar potato-logic/target/potato-logic-1.0-SNAPSHOT-fat.jar -conf conf/config.json
	
db-create:
	scripts/db-create.sh

db-drop:
	scripts/db-drop.sh

db-recreate:
	scripts/db-drop.sh
	scripts/db-create.sh
