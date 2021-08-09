mvn clean install
docker build -t employees .
docker tag employees:latest 128556667010.dkr.ecr.eu-central-1.amazonaws.com/employees:latest
docker push 128556667010.dkr.ecr.eu-central-1.amazonaws.com/employees:latest

v="aws ecs list-tasks --cluster \"default\" --output text --query taskArns[0]"
if  [ "$(eval "$v")" != "None" ]; then
    aws ecs stop-task --task $(eval "$v") > stop.txt
fi
aws ecs run-task --cluster "default" --task-definition task1 > run.txt