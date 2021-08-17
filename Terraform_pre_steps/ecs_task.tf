
resource "aws_ecs_task_definition" "service" {
  family = "service"
  requires_compatibilities = ["EC2"]
  container_definitions = jsonencode([
    {
      name      = "first_task"
      image     =  aws_ecr_repository.foo.repository_url
//      cpu       = 10
      memory    = 500
//      essential = true
      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
        }
      ]
    }
  ])

//  volume {
//    name      = "service-storage"
//    host_path = "/ecs/service-storage"
//  }
//
//  placement_constraints {
//    type       = "memberOf"
//    expression = "attribute:ecs.availability-zone in [us-west-2a, us-west-2b]"
//  }
}