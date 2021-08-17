resource "aws_db_instance" "default" {
  allocated_storage = 20
  engine = "mysql"
  engine_version = "8.0.23"
  instance_class = "db.t2.micro"
  name = "mydb"
  username = "admin"
  password = "admin123"
  skip_final_snapshot = true
  storage_type = "gp2"
  apply_immediately = true
  identifier = "mydb"
  publicly_accessible = true
  vpc_security_group_ids = ["sg-0e3449ac9bda66de9"]

}